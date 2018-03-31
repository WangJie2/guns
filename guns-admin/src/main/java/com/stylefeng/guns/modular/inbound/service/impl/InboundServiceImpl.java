package com.stylefeng.guns.modular.inbound.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.dao.InboundMapper;
import com.stylefeng.guns.common.persistence.dto.InboundDto;
import com.stylefeng.guns.common.persistence.model.Inbound;
import com.stylefeng.guns.common.persistence.model.InboundDetail;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.inbound.service.IInboundService;
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
public class InboundServiceImpl extends ServiceImpl<InboundMapper, Inbound> implements IInboundService {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    @Autowired
    private InboundMapper inboundMapper;

    @Override
    public List<Map<String, Object>> getInboundPage(Page<Inbound> page, String beginTime, String endTime, String inboundno, String orderByField, boolean asc) {
        return inboundMapper.getInboundPage(page, beginTime, endTime, inboundno, page.getOrderByField(), page.isAsc());
    }

    @Override
    public List<InboundDetail> selectDetail(Integer inboundId) {
        return inboundMapper.selectDetail(inboundId);
    }

    @Override
    @Transactional
    public int insertInbound(Inbound inbound, List<InboundDetail> detail) {
        Date date = new Date();
        ShiroUser user = ShiroKit.getUser();
        String inboundno = "R" + sdf.format(date);
        inbound.setInboundno(inboundno);
        inbound.setCreatedate(date);
        inbound.setCreater(user.getId().toString());
        int result = inboundMapper.insert(inbound);
        detail.forEach(inboundDetail -> inboundDetail.setInboundid(inbound.getId()));
        inboundMapper.insertDetail(detail);
        return result;
    }

    @Override
    public Inbound loadById(Integer inboundId) {
        return inboundMapper.loadById(inboundId);
    }

    @Override
    @Transactional
    public int deleteInboundById(Integer inboundId) {
        this.deleteById(inboundId);
        return inboundMapper.deleteDetail(inboundId);
    }

    @Override
    @Transactional
    public int updateInbound(Inbound inbound, List<InboundDetail> detail) {
        int result = inboundMapper.updateById(inbound);
        inboundMapper.deleteDetail(inbound.getId());
        detail.forEach(inboundDetail -> inboundDetail.setInboundid(inbound.getId()));
        inboundMapper.insertDetail(detail);
        return result;
    }

    @Override
    public List<InboundDto> getInboundDtoList(Map map) {
        return inboundMapper.getInboundDtoList(map);
    }
}
