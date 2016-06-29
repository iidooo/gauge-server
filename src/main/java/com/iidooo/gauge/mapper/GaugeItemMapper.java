package com.iidooo.gauge.mapper;

import java.util.List;

import com.iidooo.core.model.Page;
import com.iidooo.gauge.model.po.GaugeItem;

public interface GaugeItemMapper {
    int deleteByPrimaryKey(Integer itemID);

    int insert(GaugeItem record);

    int insertSelective(GaugeItem record);

    GaugeItem selectByPrimaryKey(Integer itemID);
    
    /**
     * 查询获得监控项目一览
     * @param page 分页参数
     * @return 监控项目一览列表
     */
    List<GaugeItem> selectList(Page page);

    int updateByPrimaryKeySelective(GaugeItem record);

    int updateByPrimaryKey(GaugeItem record);
}