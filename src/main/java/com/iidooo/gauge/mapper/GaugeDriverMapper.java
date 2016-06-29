package com.iidooo.gauge.mapper;

import com.iidooo.gauge.model.po.GaugeDriver;

public interface GaugeDriverMapper {
    int deleteByPrimaryKey(Integer driverID);

    int insert(GaugeDriver record);

    int insertSelective(GaugeDriver record);

    GaugeDriver selectByPrimaryKey(Integer driverID);

    int updateByPrimaryKeySelective(GaugeDriver record);

    int updateByPrimaryKey(GaugeDriver record);
}