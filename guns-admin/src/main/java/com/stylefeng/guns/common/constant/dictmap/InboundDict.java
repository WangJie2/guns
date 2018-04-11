package com.stylefeng.guns.common.constant.dictmap;

import com.stylefeng.guns.common.constant.dictmap.base.AbstractDictMap;

/**
 * 用户的字典
 *
 * @author fengshuonan
 * @date 2017-05-06 15:01
 */
public class InboundDict extends AbstractDictMap {

    @Override
    public void init() {
        put("inboundId", "入库单ID");
    }

    @Override
    protected void initBeWrapped() {
//        putFieldWrapperMethodName("inboundId", "getInboundno");
    }
}
