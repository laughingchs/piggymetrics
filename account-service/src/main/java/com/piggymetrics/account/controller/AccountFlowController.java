/**
 * Alibaba.com Inc.
 * Copyright (c) 1999-2019 All Rights Reserved.
 */
package com.piggymetrics.account.controller;

import java.util.Date;
import java.util.List;

import com.piggymetrics.account.domain.AccountFlow;
import com.piggymetrics.account.repository.IAccountFlowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author goomoon
 * @version $Id: AccountFlowController.java, v 0.1 2019年04月26日 11:24 goomoon(husheng.chenhs@alibaba-inc.com) Exp $
 */
@RestController
public class AccountFlowController {

    @Autowired
    private IAccountFlowMapper iAccountFlowMapper;

    @GetMapping("/simple/list")
    public List<AccountFlow> findAccountFlowList() {
        return this.iAccountFlowMapper.findAllFlows();
    }

    /**
     * 添加一个student,使用postMapping接收post请求
     *
     * http://localhost:8310/simple/addUser?username=user11&age=11&balance=11
     *
     * @return
     */
    @GetMapping("/simple/addAccountFlow")
    public AccountFlow addAccountFlow(@RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "action", required = false) String action,
                                      @RequestParam(value = "beforeValue", required = false) String beforeValue,
                                      @RequestParam(value = "afterValue", required = false) String afterValue) {
        AccountFlow user = new AccountFlow();

        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        user.setName(name);
        user.setAction(action);
        user.setBeforeValue(beforeValue);
        user.setAfterValue(afterValue);

        int result = iAccountFlowMapper.insertAccountFlow(user);
        if (result > 0) {
            return user;
        }

        user.setId(0L);
        user.setName(null);

        return user;
    }
}
