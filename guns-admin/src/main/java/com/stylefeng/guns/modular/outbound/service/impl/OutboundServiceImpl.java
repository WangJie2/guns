package com.stylefeng.guns.modular.outbound.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.dao.OutboundMapper;
import com.stylefeng.guns.common.persistence.dto.OutboundDto;
import com.stylefeng.guns.common.persistence.model.Outbound;
import com.stylefeng.guns.common.persistence.model.OutboundDetail;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.outbound.service.IOutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-03-10
 */
@Service
public class OutboundServiceImpl extends ServiceImpl<OutboundMapper, Outbound> implements IOutboundService {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    @Autowired
    private OutboundMapper outboundMapper;

    @Override
    public List<Map<String, Object>> getOutboundPage(Page<Outbound> page, String beginTime, String endTime, String outboundno, String orderByField, boolean asc) {
        return outboundMapper.getOutboundPage(page, beginTime, endTime, outboundno, page.getOrderByField(), page.isAsc());
    }

    @Override
    public List<OutboundDetail> selectDetail(Integer outboundId) {
        return outboundMapper.selectDetail(outboundId);
    }

    @Override
    @Transactional
    public int insertOutbound(Outbound outbound, List<OutboundDetail> detail) {
        Date date = new Date();
        ShiroUser user = ShiroKit.getUser();
        String outboundno = "C" + sdf.format(date);
        outbound.setOutboundno(outboundno);
        outbound.setCreatedate(date);
        outbound.setCreater(user.getId().toString());
        int result = outboundMapper.insert(outbound);
        detail.forEach(outboundDetail -> outboundDetail.setOutboundid(outbound.getId()));
        outboundMapper.insertDetail(detail);
        return result;
    }

    @Override
    public Outbound loadById(Integer outboundId) {
        return outboundMapper.loadById(outboundId);
    }

    @Override
    @Transactional
    public int deleteOutboundById(Integer outboundId) {
        this.deleteById(outboundId);
        return outboundMapper.deleteDetail(outboundId);
    }

    @Override
    @Transactional
    public int updateOutbound(Outbound outbound, List<OutboundDetail> detail) {
        int result = outboundMapper.updateById(outbound);
        outboundMapper.deleteDetail(outbound.getId());
        detail.forEach(outboundDetail -> outboundDetail.setOutboundid(outbound.getId()));
        outboundMapper.insertDetail(detail);
        return result;
    }

    @Override
    public List<OutboundDto> getOutboundDtoList(Map map) {
        return outboundMapper.getOutboundDtoList(map);
    }
}
