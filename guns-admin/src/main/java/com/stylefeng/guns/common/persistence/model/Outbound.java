package com.stylefeng.guns.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.stylefeng.guns.core.util.GunDateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author stylefeng
 * @since 2018-03-24
 */
public class Outbound extends Model<Outbound> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    private String creater;


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

    public String getOutbounddateStr() {
        return GunDateUtil.getDateString(outbounddate);
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

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Outbound{" +
                "id=" + id +
                ", outboundno=" + outboundno +
                ", customername=" + customername +
                ", customerreceive=" + customerreceive +
                ", warehousedelivery=" + warehousedelivery +
                ", outbounddate=" + outbounddate +
                ", createdate=" + createdate +
                ", creater=" + creater +
                "}";
    }
}
