package com.iidooo.gauge.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iidooo.core.model.Page;
import com.iidooo.gauge.model.po.GaugeProduct;

public interface GaugeProductMapper {
    int deleteByPrimaryKey(Integer productID);

    int insert(GaugeProduct record);

    int insertSelective(GaugeProduct record);
    
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

    int updateByPrimaryKeySelective(GaugeProduct record);

    int updateByPrimaryKey(GaugeProduct record);
}