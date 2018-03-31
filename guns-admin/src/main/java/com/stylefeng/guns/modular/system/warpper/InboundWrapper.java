package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.util.GunDateUtil;

import java.util.Date;
import java.util.Map;

/**
 * 部门列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class InboundWrapper extends BaseControllerWarpper {

    public InboundWrapper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        Integer creater = Integer.parseInt(map.get("creater").toString());
        map.put("createrName", ConstantFactory.me().getUserNameById(creater));
        map.put("inbounddateStr", GunDateUtil.getDateString((Date) map.get("inbounddate")));
    }

}
