package com.stylefeng.guns.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.dto.OutboundDto;
import com.stylefeng.guns.common.persistence.model.Outbound;
import com.stylefeng.guns.common.persistence.model.OutboundDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-03-10
 */
public interface OutboundMapper extends BaseMapper<Outbound> {

    List<Map<String, Object>> getOutboundPage(@Param("page") Page<Outbound> page, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("outboundno") String outboundno, @Param("orderByField") String orderByField, @Param("isAsc") boolean asc);

    List<OutboundDetail> selectDetail(@Param("outboundId") Integer outboundId);

    int insertDetail(@Param("detail") List<OutboundDetail> detail);

    Outbound loadById(@Param("id") Integer id);

    int deleteDetail(@Param("outboundId") Integer outboundId);

    List<OutboundDto> getOutboundDtoList(Map map);
}