package com.iidooo.gauge.mapper;

import com.iidooo.gauge.model.po.GaugeDriver;

public interface GaugeDriverMapper {
    int deleteByPrimaryKey(Integer driverID);

    /**
     * 插入一个新的驾驶员对象
     * @param driver 驾驶员信息
     * @return 插入成功与否
     */
    int insert(GaugeDriver driver);

    GaugeDriver selectByPrimaryKey(Integer driverID);

    /**
     * 更新一个驾驶员信息通过其ID
     * @param driver 驾驶员信息
     * @return 更新成功与否
     */
    int updateByPrimaryKey(GaugeDriver driver);
}