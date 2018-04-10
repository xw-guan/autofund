package com.xwguan.autofund.entity.fund;

import java.util.List;

/**
 * 基金公司
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-24
 */
public class FundCompany {

    private Integer id;

    /**
     * 基金公司代码, eg. 80000231
     */
    private String code;

    /**
     * 基金公司简称, eg. 融通基金
     */
    private String sname;

    /**
     * 基金公司全称, eg. 融通基金管理有限公司
     */
    private String fname;

    /**
     * 基金公司简称拼音
     */
    private String pinyinSname;

    /**
     * 基金公司简称拼音首字母缩写, eg. RTJJ
     */
    private String abbrPinyinSname;

    /**
     * 经理列表
     */
    private List<FundManager> managerList;

    /**
     * 基金列表
     */
    private List<Fund> fundList;

    public FundCompany() {
        super();
    }

    public FundCompany(String code, String sname, String fname, String pinyinSname, String abbrPinyinSname) {
        this.code = code;
        this.sname = sname;
        this.fname = fname;
        this.pinyinSname = pinyinSname;
        this.abbrPinyinSname = abbrPinyinSname;
    }

    public FundCompany(Integer id, String code, String sname, String fname, String pinyinSname, String abbrPinyinSname) {
        this.id = id;
        this.code = code;
        this.sname = sname;
        this.fname = fname;
        this.pinyinSname = pinyinSname;
        this.abbrPinyinSname = abbrPinyinSname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPinyinSname() {
        return pinyinSname;
    }

    public void setPinyinSname(String pinyinSname) {
        this.pinyinSname = pinyinSname;
    }

    public String getAbbrPinyinSname() {
        return abbrPinyinSname;
    }

    public void setAbbrPinyinSname(String abbrPinyinSname) {
        this.abbrPinyinSname = abbrPinyinSname;
    }

    public List<FundManager> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<FundManager> managerList) {
        this.managerList = managerList;
    }

    public List<Fund> getFundList() {
        return fundList;
    }

    public void setFundList(List<Fund> fundList) {
        this.fundList = fundList;
    }

    @Override
    public String toString() {
        return "FundCompany [id=" + id + ", code=" + code + ", sname=" + sname + ", fname=" + fname + ", pinyinSname="
            + pinyinSname + ", abbrPinyinSname=" + abbrPinyinSname + ", managerList=" + managerList + ", fundList="
            + fundList + "]";
    }

}
