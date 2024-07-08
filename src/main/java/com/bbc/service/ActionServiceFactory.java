package com.bbc.service;

import com.bbc.service.impl.Action1ServiceImpl;
import com.bbc.service.impl.Action2ServiceImpl;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: ActionServiceFactory
 * @Author WangHaoWei
 * @Package com.bbc.service
 * @Date 2023/12/14 18:00
 * @description: 工厂类
 */
public class ActionServiceFactory {
    public ActionServiceFactory(){}
    public static class SingletonFactory{
        public static ActionServiceFactory instance(){
            return new ActionServiceFactory();
        }
    }
    public static ActionServiceFactory getInstance(){
        return SingletonFactory.instance();
    }
    private static final Map<String,ActionService> ACTION_MAP = new HashMap<String, ActionService>();
    static {
        ACTION_MAP.put("action1",new Action1ServiceImpl());
        ACTION_MAP.put("action2",new Action2ServiceImpl());
    }
    public static  ActionService getActionServiceByAction(String actionCode){
        ActionService actionService = ACTION_MAP.get(actionCode);
        if(actionService==null){
            throw new RuntimeException("非法 actionCode");
        }
        return actionService;
    }
    public void  action(String actionCode){
        getActionServiceByAction(actionCode).action();
    }
}
