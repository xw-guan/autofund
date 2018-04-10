package com.xwguan.autofund.entity.user;

import java.util.List;

import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.account.Debit;
import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.enums.UserTypeEnum;

/**
 * 用户
 * 
 * @author XWGuan
 * @version 0.1 2017-10-09
 */
public class User {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号, 唯一或null
     */
    private String phoneNumber;

    /**
     * 电子邮箱, 唯一或null
     */
    private String email;

    /**
     * 用户信息
     */
    private UserInfo userInfo;

    /**
     * 用户类型
     */
    private UserTypeEnum userType;

    /**
     * 借记卡列表
     */
    private List<Debit> debitList;

    /**
     * 定投计划列表
     */
    private List<Plan> planList;

    /**
     * 账户
     */
    private Account account;

    public User() {
        super();
    }

    public User(Integer id, String name, String password, String phoneNumber, String email, UserInfo userInfo,
        UserTypeEnum userType, List<Debit> debitList, List<Plan> planList, Account account) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userInfo = userInfo;
        this.userType = userType;
        this.debitList = debitList;
        this.planList = planList;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<Debit> getDebitList() {
        return debitList;
    }

    public void setDebitList(List<Debit> debitList) {
        this.debitList = debitList;
    }

    public UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(UserTypeEnum userType) {
        this.userType = userType;
    }

    public List<Plan> getPlanList() {
        return planList;
    }

    public void setPlanList(List<Plan> planList) {
        this.planList = planList;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password + ", phoneNumber=" + phoneNumber
            + ", email=" + email + ", userInfo=" + userInfo + ", debitList=" + debitList + ", userType=" + userType
            + ", planList=" + planList + ", account=" + account + "]";
    }

}
