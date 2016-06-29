package com.iidooo.gauge.mapper;

import com.iidooo.gauge.model.po.GaugeVehicle;

public interface GaugeVehicleMapper {
    int deleteByPrimaryKey(Integer vehicleID);

    int insert(GaugeVehicle record);

    int insertSelective(GaugeVehicle record);

    GaugeVehicle selectByPrimaryKey(Integer vehicleID);

    int updateByPrimaryKeySelective(GaugeVehicle record);

    int updateByPrimaryKey(GaugeVehicle record);
}