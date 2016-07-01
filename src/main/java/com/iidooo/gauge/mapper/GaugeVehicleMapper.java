package com.iidooo.gauge.mapper;

import com.iidooo.gauge.model.po.GaugeVehicle;

public interface GaugeVehicleMapper {
    int deleteByPrimaryKey(Integer vehicleID);

    /**
     * 插入一个新的车辆对象
     * @param vehicle 车辆对象
     * @return 插入成功与否
     */
    int insert(GaugeVehicle vehicle);

    GaugeVehicle selectByPrimaryKey(Integer vehicleID);

    /**
     * 更新一个车辆信息通过其ID
     * @param vehicle 车辆信息
     * @return 更新成功与否
     */
    int updateByPrimaryKey(GaugeVehicle vehicle);
}