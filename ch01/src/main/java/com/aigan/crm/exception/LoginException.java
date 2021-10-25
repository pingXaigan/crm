package com.aigan.crm.exception;

/**
 * @author aigan
 * @date 2021/10/19 23:21
 */
public class LoginException extends Exception {

    public LoginException(){ super();}

    public LoginException(String msg){
        super(msg);
    }
}
