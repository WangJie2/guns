package com.stylefeng.guns.common.persistence.dto;

import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author stylefeng
 * @since 2018-03-10
 */
public class OutboundDto {

    private Integer id;

    /**
     * 出库单号
     */
    private String outboundno;
    /**
     * 客户单位名称
     */
    private String customername;
    /**
     * 客户提货
     */
    private String customerreceive;
    /**
     * 仓库发货
     */
    private String warehousedelivery;
    /**
     * 出库日期
     */
    private Date outbounddate;
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

    public String getOutboundno() {
        return outboundno;
    }

    public void setOutboundno(String outboundno) {
        this.outboundno = outboundno;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomerreceive() {
        return customerreceive;
    }

    public void setCustomerreceive(String customerreceive) {
        this.customerreceive = customerreceive;
    }

    public String getWarehousedelivery() {
        return warehousedelivery;
    }

    public void setWarehousedelivery(String warehousedelivery) {
        this.warehousedelivery = warehousedelivery;
    }

    public Date getOutbounddate() {
        return outbounddate;
    }

    public void setOutbounddate(Date outbounddate) {
        this.outbounddate = outbounddate;
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
        return "OutboundDto{" +
                "id=" + id +
                ", outboundno='" + outboundno + '\'' +
                ", customername='" + customername + '\'' +
                ", customerreceive='" + customerreceive + '\'' +
                ", warehousedelivery='" + warehousedelivery + '\'' +
                ", outbounddate=" + outbounddate +
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
