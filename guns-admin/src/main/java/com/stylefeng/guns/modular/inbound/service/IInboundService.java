package com.stylefeng.guns.modular.inbound.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.Inbound;
import com.stylefeng.guns.common.persistence.model.InboundDetail;

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
public interface IInboundService extends IService<Inbound> {


    List<Map<String, Object>> getInboundPage(Page<Inbound> page, String beginTime, String endTime, String inboundno, String orderByField, boolean asc);

    List<InboundDetail> selectDetail(Integer inboundId);

    int insertInbound(Inbound inbound, List<InboundDetail> detail);

    Inbound loadById(Integer inboundId);

    int deleteInboundById(Integer inboundId);

    int updateInbound(Inbound inbound, List<InboundDetail> detail);
}
