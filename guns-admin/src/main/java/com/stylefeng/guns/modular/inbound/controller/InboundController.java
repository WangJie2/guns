package com.stylefeng.guns.modular.inbound.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.model.Inbound;
import com.stylefeng.guns.common.persistence.model.InboundDetail;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.inbound.service.IInboundService;
import com.stylefeng.guns.modular.system.warpper.InboundWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 入库单控制器
 *
 * @author fengshuonan
 * @Date 2018-03-10 16:49:05
 */
@Controller
@RequestMapping("/inbound")
public class InboundController extends BaseController {

    private String PREFIX = "/inbound/inbound/";

    @Autowired
    private IInboundService inboundService;

    /**
     * 跳转到入库单首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "inbound.html";
    }

    /**
     * 跳转到添加入库单
     */
    @RequestMapping("/inbound_add")
    public String inboundAdd() {
        return PREFIX + "inbound_add.html";
    }

    /**
     * 跳转到修改入库单
     */
    @RequestMapping("/inbound_update/{inboundId}")
    public String inboundUpdate(@PathVariable Integer inboundId, Model model) {
        Inbound inbound = inboundService.loadById(inboundId);
        model.addAttribute("inbound", inbound);
        List<InboundDetail> inboundDetail = inboundService.selectDetail(inboundId);
        model.addAttribute("inboundDetail", inboundDetail);
        LogObjectHolder.me().set(inbound);
        return PREFIX + "inbound_edit.html";
    }

    /**
     * 获取入库单列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String inboundno) {
        Page<Inbound> page = new PageFactory<Inbound>().defaultPage();
        List<Map<String, Object>> result = inboundService.getInboundPage(page, beginTime, endTime, inboundno, page.getOrderByField(), page.isAsc());
        page.setRecords((List<Inbound>) new InboundWrapper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 新增入库单
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(String inboundStr, String detailStr) {
        if (ToolUtil.isOneEmpty(inboundStr)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Inbound inbound = (Inbound) JSONObject.toJavaObject(JSONObject.parseObject(inboundStr), Inbound.class);
        List<InboundDetail> detail = JSONObject.parseArray(detailStr, InboundDetail.class);
        if (CollectionUtils.isEmpty(detail)) {
            throw new BussinessException(BizExceptionEnum.DETAIL_EMPTY);
        }
        this.inboundService.insertInbound(inbound, detail);
        return SUCCESS_TIP;
    }

    /**
     * 删除入库单
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Integer inboundId) {
        inboundService.deleteInboundById(inboundId);
        return SUCCESS_TIP;
    }

    /**
     * 修改入库单
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(String inboundStr, String detailStr) {
        if (ToolUtil.isOneEmpty(inboundStr)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Inbound inbound = (Inbound) JSONObject.toJavaObject(JSONObject.parseObject(inboundStr), Inbound.class);
        if (ToolUtil.isOneEmpty(inbound.getId())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        List<InboundDetail> detail = JSONObject.parseArray(detailStr, InboundDetail.class);
        if (CollectionUtils.isEmpty(detail)) {
            throw new BussinessException(BizExceptionEnum.DETAIL_EMPTY);
        }
        this.inboundService.updateInbound(inbound, detail);
        return SUCCESS_TIP;
    }

    /**
     * 入库单详情
     */
    @RequestMapping(value = "/detail/{inboundId}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("inboundId") Integer inboundId) {
        return inboundService.selectById(inboundId);
    }
}
