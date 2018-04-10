package com.xwguan.autofund.entity.fund;

import java.util.List;

/**
 * 基金
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-24
 */
public class Fund {

    private Integer id;

    /**
     * 基金代码, 唯一
     */
    private String code;

    /**
     * 基金名称, 可能相同
     */
    private String name;

    /**
     * 基金名称拼音
     */
    private String pinyinName;

    /**
     * 基金名称拼音首字母
     */
    private String abbrPinyinName;

    /**
     * 基金类型
     */
    private String type;

    /**
     * 基金信息, 包括基金经理, 资产配置等
     */
    private FundInfo fundInfo;

    /**
     * 资产配置
     */
    private AssetAllocation assetAllocation;

    /**
     * 基金历史数据, 包含净值等
     */
    private List<FundHistory> fundHistoryList;

    public Fund() {
        super();
    }

    public Fund(String code, String name, String pinyinName, String abbrPinyinName, String type) {
        this.code = code;
        this.name = name;
        this.pinyinName = pinyinName;
        this.abbrPinyinName = abbrPinyinName;
        this.type = type;
    }

    public Fund(Integer id, String code, String name, String pinyinName, String abbrPinyinName, String type) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.pinyinName = pinyinName;
        this.abbrPinyinName = abbrPinyinName;
        this.type = type;
    }

    public Fund(String code, FundInfo fundInfo, AssetAllocation assetAllocation, List<FundHistory> fundHistoryList) {
        this.code = code;
        this.fundInfo = fundInfo;
        this.assetAllocation = assetAllocation;
        this.fundHistoryList = fundHistoryList;
    }

    /**
     * 判断本基金与传入基金是否为同一支. 由于基金可由基金代码唯一确定, 所以只要基金代码相同就认为是相同的
     * 
     * @param fund
     * @return
     */
    public boolean isTheSameFundAs(Fund fund) {
        return this.code.equals(fund.code);
    }

    /**
     * 判断本基金与传入基金的基本信息(名称, 拼音, 类型)是否相同
     * 
     * @param other
     * @return true: 相同, false: 不相同
     */
    public boolean isBasicInfoEqual(Fund other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (pinyinName == null) {
            if (other.pinyinName != null) {
                return false;
            }
        } else if (!pinyinName.equals(other.pinyinName)) {
            return false;
        }
        if (abbrPinyinName == null) {
            if (other.abbrPinyinName != null) {
                return false;
            }
        } else if (!abbrPinyinName.equals(other.abbrPinyinName)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }

    /**
     * 判断本基金与传入基金的基本信息(名称, 拼音, 类型)是否不相同
     * 
     * @param other
     * @return true: 不相同, false: 相同
     */
    public boolean isBasicInfoNotEqual(Fund other) {
        return !isBasicInfoEqual(other);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Fund other = (Fund) obj;
        if (abbrPinyinName == null) {
            if (other.abbrPinyinName != null)
                return false;
        } else if (!abbrPinyinName.equals(other.abbrPinyinName))
            return false;
        if (assetAllocation == null) {
            if (other.assetAllocation != null)
                return false;
        } else if (!assetAllocation.equals(other.assetAllocation))
            return false;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (fundHistoryList == null) {
            if (other.fundHistoryList != null)
                return false;
        } else if (!fundHistoryList.equals(other.fundHistoryList))
            return false;
        if (fundInfo == null) {
            if (other.fundInfo != null)
                return false;
        } else if (!fundInfo.equals(other.fundInfo))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (pinyinName == null) {
            if (other.pinyinName != null)
                return false;
        } else if (!pinyinName.equals(other.pinyinName))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
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

    public String getPinyinName() {
        return pinyinName;
    }

    public void setPinyinName(String pinyinName) {
        this.pinyinName = pinyinName;
    }

    public String getAbbrPinyinName() {
        return abbrPinyinName;
    }

    public void setAbbrPinyinName(String abbrPinyinName) {
        this.abbrPinyinName = abbrPinyinName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FundInfo getFundInfo() {
        return fundInfo;
    }

    public void setFundInfo(FundInfo fundInfo) {
        this.fundInfo = fundInfo;
    }

    public AssetAllocation getAssertAllocation() {
        return assetAllocation;
    }

    public void setAssertAllocation(AssetAllocation assetAllocation) {
        this.assetAllocation = assetAllocation;
    }

    public List<FundHistory> getFundHistoryList() {
        return fundHistoryList;
    }

    public void setFundHistoryList(List<FundHistory> fundHistoryList) {
        this.fundHistoryList = fundHistoryList;
    }

    @Override
    public String toString() {
        return "Fund [id=" + id + ", code=" + code + ", name=" + name + ", pinyinName=" + pinyinName
            + ", abbrPinyinName=" + abbrPinyinName + ", type=" + type + ", fundInfo=" + fundInfo + ", assetAllocation="
            + assetAllocation + ", fundHistoryList=" + fundHistoryList + "]";
    }

}
