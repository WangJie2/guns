package com.stylefeng.guns.modular.outbound.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.Outbound;
import com.stylefeng.guns.common.persistence.model.OutboundDetail;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stylefeng
 * @since 2018-03-10
 */
public interface IOutboundService extends IService<Outbound> {


    List<Map<String, Object>> getOutboundPage(Page<Outbound> page, String beginTime, String endTime, String outboundno, String orderByField, boolean asc);

    List<OutboundDetail> selectDetail(Integer outboundId);

    int insertOutbound(Outbound outbound, List<OutboundDetail> detail);

    Outbound loadById(Integer outboundId);

    int deleteOutboundById(Integer outboundId);

    int updateOutbound(Outbound outbound, List<OutboundDetail> detail);
}
