package com.piggymetrics.account.service;

import com.piggymetrics.account.client.AuthServiceClient;
import com.piggymetrics.account.client.StatisticsServiceClient;
import com.piggymetrics.account.domain.Account;
import com.piggymetrics.account.domain.AccountFlow;
import com.piggymetrics.account.domain.Currency;
import com.piggymetrics.account.domain.Saving;
import com.piggymetrics.account.domain.User;
import com.piggymetrics.account.repository.AccountRepository;
import com.piggymetrics.account.repository.IAccountFlowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

@Service
public class AccountServiceImpl implements AccountService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private StatisticsServiceClient statisticsClient;

    @Autowired
    private AuthServiceClient authClient;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private IAccountFlowMapper iAccountFlowMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Account findByName(String accountName) {
        Assert.hasLength(accountName);
        Account account = repository.findByName(accountName);

        AccountFlow accountFlow = new AccountFlow();
        accountFlow.setGmtCreate(new Date());
        accountFlow.setGmtModified(new Date());
        accountFlow.setName(account.getName());
        accountFlow.setAction("findByName");
        accountFlow.setBeforeValue(JSONObject.toJSONString(account));
        iAccountFlowMapper.insertAccountFlow(accountFlow);

        return account;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account create(User user) {

        Account existing = repository.findByName(user.getUsername());
        Assert.isNull(existing, "account already exists: " + user.getUsername());

        authClient.createUser(user);

        Saving saving = new Saving();
        saving.setAmount(new BigDecimal(0));
        saving.setCurrency(Currency.getDefault());
        saving.setInterest(new BigDecimal(0));
        saving.setDeposit(false);
        saving.setCapitalization(false);

        Account account = new Account();
        account.setName(user.getUsername());
        account.setLastSeen(new Date());
        account.setSaving(saving);

        repository.save(account);

        AccountFlow accountFlow = new AccountFlow();
        accountFlow.setGmtCreate(new Date());
        accountFlow.setGmtModified(new Date());
        accountFlow.setName(account.getName());
        accountFlow.setAction("createAccount");
        accountFlow.setBeforeValue(null);
        accountFlow.setAfterValue(JSONObject.toJSONString(account));
        iAccountFlowMapper.insertAccountFlow(accountFlow);

        log.info("new account has been created: " + account.getName());

        return account;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveChanges(String name, Account update) {

        Account account = repository.findByName(name);
        Assert.notNull(account, "can't find account with name " + name);

        String beforeValue = JSONObject.toJSONString(account);
        account.setIncomes(update.getIncomes());
        account.setExpenses(update.getExpenses());
        account.setSaving(update.getSaving());
        account.setNote(update.getNote());
        account.setLastSeen(new Date());
        repository.save(account);

        AccountFlow accountFlow = new AccountFlow();
        accountFlow.setGmtCreate(new Date());
        accountFlow.setGmtModified(new Date());
        accountFlow.setName(name);
        accountFlow.setAction("saveChanges");
        accountFlow.setBeforeValue(beforeValue);
        accountFlow.setAfterValue(JSONObject.toJSONString(account));
        iAccountFlowMapper.insertAccountFlow(accountFlow);

        log.debug("account {} changes has been saved", name);

        statisticsClient.updateStatistics(name, account);
    }
}
