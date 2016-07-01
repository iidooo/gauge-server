package com.iidooo.gauge.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iidooo.core.model.Page;
import com.iidooo.gauge.model.po.GaugeProduct;

public interface GaugeProductMapper {
    int deleteByPrimaryKey(Integer productID);

    /**
     * 插入一个新的设备对象
     * @param product 车辆对象
     * @return 插入成功与否
     */
    int insert(GaugeProduct product);
    
    /**
     * 根据主键查找设备
     * @param productID 设备ID
     * @return 设备对象
     */
    GaugeProduct selectByPrimaryKey(int productID);
    
    /**
     * 查询设备一览的数量
     * @param product 查询条件封装
     * @return 条目数
     */
    int selectCountForSearch(GaugeProduct product);
    
    /**
     * 查询设备一览
     * @param product 查询条件封装
     * @param page 分页信息
     * @return 设备一览
     */
    List<GaugeProduct> selectForSearch(@Param("product")GaugeProduct product, @Param("page")Page page);

    /**
     * 更新一个设备信息通过其ID
     * @param product 设备信息
     * @return 更新成功与否
     */
    int updateByPrimaryKey(GaugeProduct product);
}