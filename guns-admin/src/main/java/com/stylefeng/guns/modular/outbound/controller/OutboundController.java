package com.stylefeng.guns.modular.outbound.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.stylefeng.guns.common.annotion.BussinessLog;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.dictmap.factory.OutboundDict;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dto.OutboundDto;
import com.stylefeng.guns.common.persistence.model.Outbound;
import com.stylefeng.guns.common.persistence.model.OutboundDetail;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.core.util.office.FileUtils;
import com.stylefeng.guns.modular.outbound.service.IOutboundService;
import com.stylefeng.guns.modular.system.warpper.OutboundWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 出库单控制器
 *
 * @author fengshuonan
 * @Date 2018-03-10 16:49:05
 */
@Controller
@RequestMapping("/outbound")
public class OutboundController extends BaseController {

    private String PREFIX = "/outbound/outbound/";

    @Autowired
    private IOutboundService outboundService;

    /**
     * 跳转到出库单首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "outbound.html";
    }

    /**
     * 跳转到添加出库单
     */
    @RequestMapping("/outbound_add")
    public String outboundAdd() {
        return PREFIX + "outbound_add.html";
    }

    /**
     * 跳转到修改出库单
     */
    @RequestMapping("/outbound_update/{outboundId}")
    public String outboundUpdate(@PathVariable Integer outboundId, Model model) {
        Outbound outbound = outboundService.loadById(outboundId);
        model.addAttribute("outbound", outbound);
        List<OutboundDetail> outboundDetail = outboundService.selectDetail(outboundId);
        model.addAttribute("outboundDetail", outboundDetail);
        LogObjectHolder.me().set(outbound);
        return PREFIX + "outbound_edit.html";
    }

    /**
     * 跳转到入库单打印
     */
    @RequestMapping("/outbound_print/{outboundId}")
    public String inboundPrint(@PathVariable Integer outboundId, Model model) {
        Outbound outbound = outboundService.loadById(outboundId);
        model.addAttribute("outbound", outbound);
        List<OutboundDetail> outboundDetail = outboundService.selectDetail(outboundId);
        model.addAttribute("outboundDetail", outboundDetail);
        LogObjectHolder.me().set(outbound);
        return PREFIX + "outbound_print.html";
    }

    /**
     * 获取出库单列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String outboundno) {
        Page<Outbound> page = new PageFactory<Outbound>().defaultPage();
        List<Map<String, Object>> result = outboundService.getOutboundPage(page, beginTime, endTime, outboundno, page.getOrderByField(), page.isAsc());
        page.setRecords((List<Outbound>) new OutboundWrapper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 导出入库单列表
     */
    @RequestMapping(value = "/export")
    @Permission
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String outboundno) {
        Map<String, Object> param = new HashMap();
        param.put("beginTime", beginTime);
        param.put("endTime", endTime);
        param.put("outboundno", outboundno);
        List<OutboundDto> result = outboundService.getOutboundDtoList(param);
        String[] headers = {"出库单号", "客户单位名称", "客户提货", "仓库发货", "出库日期", "品号", "品名", "计量单位", "数量", "匹数", "件数"};
        String[] fieldNames = {"outboundno", "customername", "customerreceive", "warehousedelivery", "outbounddate", "materialno", "materialname", "unit", "number", "pinumber", "jiannumber"};
        FileUtils.exportTableWithData("出库单", "出库单", headers, fieldNames, result, request, response);
    }

    /**
     * 新增出库单
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(String outboundStr, String detailStr) {
        if (ToolUtil.isOneEmpty(outboundStr)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Outbound outbound = (Outbound) JSONObject.toJavaObject(JSONObject.parseObject(outboundStr), Outbound.class);
        List<OutboundDetail> detail = JSONObject.parseArray(detailStr, OutboundDetail.class);
        if (CollectionUtils.isEmpty(detail)) {
            throw new BussinessException(BizExceptionEnum.DETAIL_EMPTY);
        }
        this.outboundService.insertOutbound(outbound, detail);
        return SUCCESS_TIP;
    }

    /**
     * 删除出库单
     */
    @BussinessLog(value = "删除出库单", key = "outboundId", dict = OutboundDict.class)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Integer outboundId) {
        outboundService.deleteOutboundById(outboundId);
        return SUCCESS_TIP;
    }

    /**
     * 修改出库单
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(String outboundStr, String detailStr) {
        if (ToolUtil.isOneEmpty(outboundStr)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Outbound outbound = (Outbound) JSONObject.toJavaObject(JSONObject.parseObject(outboundStr), Outbound.class);
        if (ToolUtil.isOneEmpty(outbound.getId())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        List<OutboundDetail> detail = JSONObject.parseArray(detailStr, OutboundDetail.class);
        if (CollectionUtils.isEmpty(detail)) {
            throw new BussinessException(BizExceptionEnum.DETAIL_EMPTY);
        }
        this.outboundService.updateOutbound(outbound, detail);
        return SUCCESS_TIP;
    }

    /**
     * 出库单详情
     */
    @RequestMapping(value = "/detail/{outboundId}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("outboundId") Integer outboundId) {
        return outboundService.selectById(outboundId);
    }
}
