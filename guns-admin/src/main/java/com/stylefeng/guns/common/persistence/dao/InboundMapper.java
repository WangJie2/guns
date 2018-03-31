package com.stylefeng.guns.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.dto.InboundDto;
import com.stylefeng.guns.common.persistence.model.Inbound;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.common.persistence.model.InboundDetail;
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
public interface InboundMapper extends BaseMapper<Inbound> {

    List<Map<String, Object>> getInboundPage(@Param("page") Page<Inbound> page, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("inboundno") String inboundno, @Param("orderByField") String orderByField, @Param("isAsc") boolean asc);

    List<InboundDetail> selectDetail(@Param("inboundId") Integer inboundId);

    int insertDetail(@Param("detail") List<InboundDetail> detail);

    Inbound loadById(@Param("id") Integer id);

    int deleteDetail(@Param("inboundId") Integer inboundId);

    List<InboundDto> getInboundDtoList(Map map);
}