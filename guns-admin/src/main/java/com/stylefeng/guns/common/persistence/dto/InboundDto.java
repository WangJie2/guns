package com.stylefeng.guns.common.persistence.dto;

import com.stylefeng.guns.core.util.GunDateUtil;

import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author stylefeng
 * @since 2018-03-10
 */
public class InboundDto {

    private Integer id;

    /**
     * 入库单号
     */
    private String inboundno;
    /**
     * 负责人
     */
    private String charger;
    /**
     * 进货经办人
     */
    private String agent;
    /**
     * 仓库收货人
     */
    private String receiver;
    /**
     * 入库日期
     */
    private Date inbounddate;
    /**
     * 创建日期
     */
    private Date createdate;

    /**
     * 创建人
     */
    private String createrName;
    /**
     * 品号
     */
    private String materialno;
    /**
     * 品名
     */
    private String materialname;
    /**
     * 计量单位
     */
    private String unit;
    /**
     * 数量
     */
    private Double number;
    /**
     * 匹数
     */
    private Double pinumber;
    /**
     * 件数
     */
    private Double jiannumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInboundno() {
        return inboundno;
    }

    public void setInboundno(String inboundno) {
        this.inboundno = inboundno;
    }

    public String getCharger() {
        return charger;
    }

    public void setCharger(String charger) {
        this.charger = charger;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getInbounddate() {
        return inbounddate;
    }

    public String getInbounddateStr() {
        return GunDateUtil.getDateString(inbounddate);
    }

    public void setInbounddate(Date inbounddate) {
        this.inbounddate = inbounddate;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }


    public String getCreaterName() {
        return createrName;
    }

    public void setCreateName(String createrName) {
        this.createrName = createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public String getMaterialno() {
        return materialno;
    }

    public void setMaterialno(String materialno) {
        this.materialno = materialno;
    }

    public String getMaterialname() {
        return materialname;
    }

    public void setMaterialname(String materialname) {
        this.materialname = materialname;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public Double getPinumber() {
        return pinumber;
    }

    public void setPinumber(Double pinumber) {
        this.pinumber = pinumber;
    }

    public Double getJiannumber() {
        return jiannumber;
    }

    public void setJiannumber(Double jiannumber) {
        this.jiannumber = jiannumber;
    }

    @Override
    public String toString() {
        return "InboundDto{" +
                "inboundno='" + inboundno + '\'' +
                ", charger='" + charger + '\'' +
                ", agent='" + agent + '\'' +
                ", receiver='" + receiver + '\'' +
                ", inbounddate=" + inbounddate +
                ", createdate=" + createdate +
                ", createrName='" + createrName + '\'' +
                ", materialno='" + materialno + '\'' +
                ", materialname='" + materialname + '\'' +
                ", unit='" + unit + '\'' +
                ", number=" + number +
                ", pinumber=" + pinumber +
                ", jiannumber=" + jiannumber +
                '}';
    }
}
