package com.iidooo.gauge.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iidooo.core.model.Page;
import com.iidooo.gauge.model.po.GaugeItem;
import com.iidooo.gauge.model.vo.SearchCondition;

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
    
    /**
     * 查询监控项目一览数目
     * @param condition 查询条件封装对象
     * @return 查询所获的条目数
     */
    int selectCountForSearch(SearchCondition condition);
    
    /**
     * 查询监控项目一览
     * @param condition 查询条件封装对象
     * @param page 分页对象
     * @return 查询所获的一览
     */
    List<GaugeItem> selectForSearch(@Param("condition")SearchCondition condition, @Param("page")Page page);

    int updateByPrimaryKeySelective(GaugeItem record);

    int updateByPrimaryKey(GaugeItem record);
}