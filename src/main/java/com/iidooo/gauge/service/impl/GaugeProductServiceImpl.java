package com.iidooo.gauge.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iidooo.core.model.Page;
import com.iidooo.gauge.mapper.GaugeProductMapper;
import com.iidooo.gauge.model.po.GaugeProduct;
import com.iidooo.gauge.service.GaugeProductService;

@Service
public class GaugeProductServiceImpl implements GaugeProductService {

    private static final Logger logger = Logger.getLogger(GaugeProductServiceImpl.class);
    
    @Autowired
    private GaugeProductMapper productMapper;
    
    @Override
    public int getProductListCount(GaugeProduct product) {
        int result = 0;
        try {
            result = productMapper.selectCountForSearch(product);
        } catch (Exception e) {
            logger.fatal(e);
            throw e;
        }
        return result;
    }

    @Override
    public List<GaugeProduct> getProductList(GaugeProduct product, Page page) {
        List<GaugeProduct> result = new ArrayList<GaugeProduct>();
        try {
            result = productMapper.selectForSearch(product, page);
        } catch (Exception e) {
            logger.fatal(e);
            throw e;
        }
        return result;
    }

}
