package com.iidooo.gauge.service;

import java.util.List;

import com.iidooo.core.model.Page;
import com.iidooo.gauge.model.po.GaugeProduct;

public interface GaugeProductService {
    int getProductListCount(GaugeProduct product);
    
    List<GaugeProduct> getProductList(GaugeProduct product, Page page);
}
