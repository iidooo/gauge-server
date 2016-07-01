package com.iidooo.gauge.service;

import java.util.List;

import com.iidooo.core.model.Page;
import com.iidooo.gauge.model.po.GaugeItem;
import com.iidooo.gauge.model.vo.SearchCondition;

public interface GaugeItemService {
    List<GaugeItem> getGaugeItemRealTime(Page page);
    
    int getGaugeItemListCount(SearchCondition condition);
    
    List<GaugeItem> getGaugeItemList(SearchCondition condition, Page page);
}
