package com.stylefeng.guns.common.constant.dictmap.factory;

import com.stylefeng.guns.common.constant.dictmap.base.AbstractDictMap;

/**
 * 用户的字典
 *
 * @author fengshuonan
 * @date 2017-05-06 15:01
 */
public class OutboundDict extends AbstractDictMap {

    @Override
    public void init() {
        put("outboundId", "出库单ID");
    }

    @Override
    protected void initBeWrapped() {
//        putFieldWrapperMethodName("inboundId", "getInboundno");
    }
}
