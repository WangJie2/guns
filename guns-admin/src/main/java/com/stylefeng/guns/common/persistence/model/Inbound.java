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
 * @since 2018-03-10
 */
public class Inbound extends Model<Inbound> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    private String creater;

    private String createrName;

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

    public String getInbounddateStr2() {
        return GunDateUtil.getDateString2(inbounddate);
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

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreateName(String createrName) {
        this.createrName = createrName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Inbound{" +
                "id=" + id +
                ", inboundno=" + inboundno +
                ", charger='" + charger + '\'' +
                ", agent='" + agent + '\'' +
                ", receiver='" + receiver + '\'' +
                ", inbounddate=" + inbounddate +
                ", createdate=" + createdate +
                ", creater='" + creater + '\'' +
                ", createrName='" + createrName + '\'' +
                '}';
    }
}
