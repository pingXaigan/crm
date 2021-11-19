package com.aigan.crm.workbench.service.impl;

import com.aigan.crm.utils.DateTimeUtil;
import com.aigan.crm.utils.SqlSessionUtil;
import com.aigan.crm.utils.UUIDUtil;
import com.aigan.crm.workbench.dao.*;
import com.aigan.crm.workbench.domain.*;
import com.aigan.crm.workbench.service.ClueService;

import java.util.List;

/**
 * @author aigan
 * @date 2021/11/8 13:41
 */
public class ClueServiceImpl implements ClueService {

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);

    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);

    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);

    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    @Override
    public boolean convert(String clueId, Tran t, String createBy) {
        boolean flag = true;
        String createTime = DateTimeUtil.getSysTime();

        // 1 根据 id 查单条
        Clue c = clueDao.getById(clueId);

        // 2 通多线索对象提取出客户信息，当客户不存在时，新建客户（根据公司名称精确匹配，判断客户是否存在）
        String company = c.getCompany();
        Customer customer = customerDao.getCustomerByName(company);

        if(customer == null) {
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(c.getAddress());
            customer.setWebsite(c.getWebsite());
            customer.setPhone(c.getPhone());
            customer.setOwner(c.getOwner());
            customer.setNextContactTime(c.getNextContactTime());
            customer.setName(company);
            customer.setDescription(c.getDescription());
            customer.setCreateTime(createTime);
            customer.setCreateBy(createBy);
            customer.setContactSummary(c.getContactSummary());

            // 添加客户
            int count = customerDao.save(customer);
            if(count != 1) flag = false;
        }

            /**
             * 经过第二步处理后，客户的信息我们已经有了，将来在处理其他表时，如果要使用到客户的id，可以直接使用 customer.getId();
             */

        // 3 通过线索对象提取联系人信息，保存联系人
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setAddress(c.getAddress());
        contacts.setAppellation(c.getAppellation());
        contacts.setContactSummary(c.getContactSummary());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setSource(c.getSource());
        contacts.setOwner(c.getOwner());
        contacts.setNextContactTime(c.getNextContactTime());
        contacts.setMphone(c.getMphone());
        contacts.setJob(c.getJob());
        contacts.setEmail(c.getEmail());
        contacts.setFullname(c.getFullname());
        contacts.setDescription(c.getDescription());
        contacts.setCustomerId(customer.getId());

        // 添加联系人
        int count2 = contactsDao.save(contacts);
        if(count2 != 1) flag = false;

            /**
             * 经过第三步处理后，客户的信息我们已经有了，将来在处理其他表时，如果要使用到联系人的id，可以直接使用 contacts.getId();
             */

        //  4 线索备注转换到客户备注以及联系人备注
            // 查询出与该线索关联的备注信息列表
        List<ClueRemark> clueRemarkList = clueRemarkDao.getListByClueId(clueId);
        for(ClueRemark clueRemark : clueRemarkList){
            // 取出备注信息
            String noteContent = clueRemark.getNoteContent();

            // 创建客户备注对象，添加客户备注
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(noteContent);
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(customer.getId());

            int count3 = customerRemarkDao.save(customerRemark);
            if(count3 != 1) flag = false;

            // 创建联系人备注对象，添加联系人备注
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setEditFlag("0");
            contactsRemark.setContactsId(customer.getId());

            int count4 = contactsRemarkDao.save(contactsRemark);
            if(count4 != 1) flag = false;

        }

        // 5 "线索和市场活动" 的关系转换到 “联系人和市场活动“的关系
        // 查询出与该线索关联的市场活动
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getListByClueId(clueId);
        // 遍历出每一条与市场活动关联的关联关系记录
        for(ClueActivityRelation clueActivityRelation : clueActivityRelationList){
            // 从每一条遍历出来的记录中取出关联的市场活动的id
            String activityId = clueActivityRelation.getActivityId();

            // 创建 联系人与市场活动的关联对象，让第三步生成的联系人与市场活动关联
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(contacts.getId());

            // 添加联系人与市场活动的关联关系表
            int count5 = contactsActivityRelationDao.save(contactsActivityRelation);
            if(count5 != 1) flag = false;

        }

        // 如果有交易的需求，创建一条交易
        if(t != null){
            t.setSource(c.getSource());
            t.setOwner(c.getOwner());
            t.setNextContactTime(c.getNextContactTime());
            t.setDescription(c.getDescription());
            t.setCustomerId(customer.getId());
            t.setContactSummary(c.getContactSummary());
            t.setContactsId(contacts.getId());

            // 添加交易
            int count6 = tranDao.save(t);
            if(count6 != 1) flag = false;

            // 7 如果创建了交易，则创建一条该交易的交易历史
            TranHistory th = new TranHistory();
            th.setId(UUIDUtil.getUUID());
            th.setTranId(t.getId());
            th.setStage(t.getStage());
            th.setMoney(t.getMoney());
            th.setExpectedDate(t.getExpectedDate());
            th.setCreateTime(createTime);
            th.setCreateBy(createBy);

            // 添加交易历史
            int count7 = tranHistoryDao.save(th);
            if(count7 != 1) flag = false;


        }

        // 8 删除线索备注
        for(ClueRemark clueRemark : clueRemarkList){
            int count8 = clueRemarkDao.delete(clueRemark);
            if(count8 != 1) flag = false;
        }

        // 9 删除线索和市场活动的关联关系
        for(ClueActivityRelation clueActivityRelation : clueActivityRelationList){
            int count9 = clueActivityRelationDao.delete(clueActivityRelation);
            if(count9 != 1) flag = false;
        }

        // 10 删除线索
        int count10 = clueDao.delete(clueId);
        if(count10 != 1) flag = false;


        return flag;
    }

    @Override
    public boolean bund(String clueId, String[] activityIds) {
        System.out.println("11111111111111111111");
        boolean flag = true;

        // 用list传入SQL中，用foreach循环，一次添加也行

        for(String aid : activityIds){
            // 取得每一个 aid 与 clueID 做关联

            System.out.println(clueId);
            System.out.println(aid);
            System.out.println("---------------");
            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setClueId(clueId);
            car.setActivityId(aid);

            // 添加
            int count = clueActivityRelationDao.bund(car);
            if(count != 1) flag = false;
        }

        return flag;
    }

    @Override
    public boolean unbund(String id) {
        boolean flag = true;
        int count = clueActivityRelationDao.unbund(id);

        if(count != 1) flag = false;

        return flag;
    }

    @Override
    public Clue detail(String id) {

        Clue c = clueDao.detail(id);

        return c;
    }

    @Override
    public boolean save(Clue c) {

        boolean flag = true;

        int count = clueDao.save(c);

        if(count != 1) flag = false;

        return flag;
    }
}
