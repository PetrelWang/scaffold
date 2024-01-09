package com.bbc.service.impl;

import com.bbc.service.ActionService;

/**
 * @Title: Action1ServiceImpl
 * @Author WangHaoWei
 * @Package com.bbc.service.imple
 * @Date 2023/12/14 17:58
 * @description:
 */
public class Action1ServiceImpl  implements ActionService {

    @Override
    public void action() {
        System.out.println("行为1开始干活了");
    }
}
