/**
 * Alibaba.com Inc.
 * Copyright (c) 1999-2019 All Rights Reserved.
 */
package com.piggymetrics.account.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author goomoon
 * @version $Id: AccountFlow.java, v 0.1 2019年04月26日 10:53 goomoon(husheng.chenhs@alibaba-inc.com) Exp $
 */
@Entity
public class AccountFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Date gmtCreate;

    @Column
    private Date gmtModified;

    @Column
    private String name;

    @Column
    private String action;

    @Column
    private String beforeValue;

    @Column
    private String afterValue;

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>gmtCreate</tt>.
     *
     * @return property value of gmtCreate
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * Setter method for property <tt>gmtCreate</tt>.
     *
     * @param gmtCreate value to be assigned to property gmtCreate
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * Getter method for property <tt>gmtModified</tt>.
     *
     * @return property value of gmtModified
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * Setter method for property <tt>gmtModified</tt>.
     *
     * @param gmtModified value to be assigned to property gmtModified
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>action</tt>.
     *
     * @return property value of action
     */
    public String getAction() {
        return action;
    }

    /**
     * Setter method for property <tt>action</tt>.
     *
     * @param action value to be assigned to property action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Getter method for property <tt>beforeValue</tt>.
     *
     * @return property value of beforeValue
     */
    public String getBeforeValue() {
        return beforeValue;
    }

    /**
     * Setter method for property <tt>beforeValue</tt>.
     *
     * @param beforeValue value to be assigned to property beforeValue
     */
    public void setBeforeValue(String beforeValue) {
        this.beforeValue = beforeValue;
    }

    /**
     * Getter method for property <tt>afterValue</tt>.
     *
     * @return property value of afterValue
     */
    public String getAfterValue() {
        return afterValue;
    }

    /**
     * Setter method for property <tt>afterValue</tt>.
     *
     * @param afterValue value to be assigned to property afterValue
     */
    public void setAfterValue(String afterValue) {
        this.afterValue = afterValue;
    }
}
