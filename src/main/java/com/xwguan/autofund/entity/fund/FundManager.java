package com.xwguan.autofund.entity.fund;

/**
 * 基金经理
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-10
 */
public class FundManager {

    private Integer id;

    /**
     * 经理代码
     */
    private String code;

    /**
     * 经理姓名
     */
    private String name;

    /**
     * 所属公司代码
     */
    private String companyCode;

    /**
     * 所属公司名称
     */
    private String companyName;

    /**
     * 当前管理基金代码
     */
    private String currentFundCodes;

    /**
     * 当前管理基金名称
     */
    private String currentFundNames;

    /**
     * 从业天数
     */
    private String careerDays;

    /**
     * 当前管理的基金总净值
     */
    private String currentTotalAsset;

    public FundManager() {
        super();
    }

    public FundManager(String code, String name, String companyCode, String companyName, String currentFundCodes,
        String currentFundNames, String careerDays, String currentTotalAsset) {
        this.code = code;
        this.name = name;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.currentFundCodes = currentFundCodes;
        this.currentFundNames = currentFundNames;
        this.careerDays = careerDays;
        this.currentTotalAsset = currentTotalAsset;
    }

    public FundManager(Integer id, String code, String name, String companyCode, String companyName,
        String currentFundCodes, String currentFundNames, String careerDays, String currentTotalAsset) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.currentFundCodes = currentFundCodes;
        this.currentFundNames = currentFundNames;
        this.careerDays = careerDays;
        this.currentTotalAsset = currentTotalAsset;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCurrentFundCodes() {
        return currentFundCodes;
    }

    public void setCurrentFundCodes(String currentFundCodes) {
        this.currentFundCodes = currentFundCodes;
    }

    public String getCurrentFundNames() {
        return currentFundNames;
    }

    public void setCurrentFundNames(String currentFundNames) {
        this.currentFundNames = currentFundNames;
    }

    public String getCareerDays() {
        return careerDays;
    }

    public void setCareerDays(String careerDays) {
        this.careerDays = careerDays;
    }

    public String getCurrentTotalAsset() {
        return currentTotalAsset;
    }

    public void setCurrentTotalAsset(String currentTotalAsset) {
        this.currentTotalAsset = currentTotalAsset;
    }

    @Override
    public String toString() {
        return "FundManager [id=" + id + ", code=" + code + ", name=" + name + ", companyCode=" + companyCode
            + ", companyName=" + companyName + ", currentFundCodes=" + currentFundCodes + ", currentFundNames="
            + currentFundNames + ", careerDays=" + careerDays + ", currentTotalAsset=" + currentTotalAsset + "]";
    }

}
