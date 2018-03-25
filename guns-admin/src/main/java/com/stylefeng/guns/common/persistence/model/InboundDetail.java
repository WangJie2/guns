package com.stylefeng.guns.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author stylefeng
 * @since 2018-03-10
 */
public class InboundDetail extends Model<InboundDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 入库单id
     */
    private Integer inboundid;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getInboundid() {
        return inboundid;
    }

    public void setInboundid(Integer inboundid) {
        this.inboundid = inboundid;
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
        return "InboundDetail{" +
                "id=" + id +
                ", inboundid=" + inboundid +
                ", materialno='" + materialno + '\'' +
                ", materialname='" + materialname + '\'' +
                ", unit='" + unit + '\'' +
                ", number=" + number +
                ", pinumber=" + pinumber +
                ", jiannumber=" + jiannumber +
                '}';
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
