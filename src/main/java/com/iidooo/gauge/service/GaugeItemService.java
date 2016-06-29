package com.iidooo.gauge.service;

import java.util.List;

import com.iidooo.core.model.Page;
import com.iidooo.gauge.model.po.GaugeItem;

public interface GaugeItemService {
    List<GaugeItem> getGaugeItemRealTime(Page page);
}
