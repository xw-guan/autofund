package com.xwguan.autofund.entity.fund;

import com.xwguan.autofund.enums.SecurityTypeEnum;

/**
 * 股票持仓
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-29
 */
public class FundPosition {

    private Long id;

    /**
     * 所属资产分配id
     */
    private Integer assetAllocationId;

    /**
     * 证券类型(股票, 债券, 基金)
     */
    private SecurityTypeEnum securityType;

    /**
     * 持仓证券代码
     */
    private String securityCode;

    /**
     * 持仓占比
     */
    private Double positionPct;

    public FundPosition() {
        super();
    }

    public FundPosition(SecurityTypeEnum securityType, String code, Double position) {
        this.securityType = securityType;
        this.securityCode = code;
        this.positionPct = position;
    }

    public FundPosition(Integer assetAllocationId, SecurityTypeEnum securityType, String securityCode,
        Double positionPct) {
        this.assetAllocationId = assetAllocationId;
        this.securityType = securityType;
        this.securityCode = securityCode;
        this.positionPct = positionPct;
    }

    public FundPosition(Long id, Integer assetAllocationId, SecurityTypeEnum securityType, String securityCode,
        Double positionPct) {
        this.id = id;
        this.assetAllocationId = assetAllocationId;
        this.securityType = securityType;
        this.securityCode = securityCode;
        this.positionPct = positionPct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAssetAllocationId() {
        return assetAllocationId;
    }

    public void setAssetAllocationId(Integer assetAllocationId) {
        this.assetAllocationId = assetAllocationId;
    }

    public SecurityTypeEnum getSecurityType() {
        return securityType;
    }

    public void setSecurityType(SecurityTypeEnum securityType) {
        this.securityType = securityType;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public Double getPositionPct() {
        return positionPct;
    }

    public void setPositionPct(Double positionPct) {
        this.positionPct = positionPct;
    }

    @Override
    public String toString() {
        return "FundPosition [id=" + id + ", assetAllocationId=" + assetAllocationId + ", securityType=" + securityType
            + ", securityCode=" + securityCode + ", positionPct=" + positionPct + "]";
    }

}
