/**
 * Alibaba.com Inc.
 * Copyright (c) 1999-2019 All Rights Reserved.
 */
package com.piggymetrics.account.repository;

import java.util.List;

import com.piggymetrics.account.domain.AccountFlow;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @author goomoon
 * @version $Id: AccountFlowMapper.java, v 0.1 2019年04月26日 11:06 goomoon(husheng.chenhs@alibaba-inc.com) Exp $
 */
public interface IAccountFlowMapper {

    @Select("select * from account_flow where id = #{id}")
    AccountFlow findById(Long id);

    @Select("select * from account_flow where name = #{name} order by id desc limit 100")
    List<AccountFlow> findUserFlows(String name);

    @Select("select * from account_flow order by id desc limit 100")
    List<AccountFlow> findAllFlows();

    @Insert(
        "INSERT INTO account_flow(gmtCreate, gmtModified, name,action,beforeValue,afterValue,deployClusterName) "
        + "VALUES(#{gmtCreate},"
        + "#{gmtModified}, "
        + "#{name}, #{action}, "
        + "#{beforeValue},#{afterValue},#{deployClusterName})")
    int insertAccountFlow(AccountFlow accountFlow);
}
