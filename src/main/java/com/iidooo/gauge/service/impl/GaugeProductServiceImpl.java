package com.iidooo.gauge.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iidooo.core.model.Page;
import com.iidooo.gauge.mapper.GaugeDriverMapper;
import com.iidooo.gauge.mapper.GaugeProductMapper;
import com.iidooo.gauge.mapper.GaugeVehicleMapper;
import com.iidooo.gauge.model.po.GaugeProduct;
import com.iidooo.gauge.service.GaugeProductService;

@Service
public class GaugeProductServiceImpl implements GaugeProductService {

    private static final Logger logger = Logger.getLogger(GaugeProductServiceImpl.class);
    
    @Autowired
    private GaugeProductMapper productMapper;
    
    @Autowired
    private GaugeDriverMapper driverMapper;
    
    @Autowired
    private GaugeVehicleMapper vehicleMapper;

    @Transactional
    @Override
    public GaugeProduct createProductInfo(GaugeProduct product) {
        try {
            product.getDriver().setCreateTime(new Date());
            product.getDriver().setUpdateUserID(product.getCreateUserID());            
            int driverID = driverMapper.insert(product.getDriver());
            
            product.getVehicle().setCreateTime(new Date());
            product.getVehicle().setUpdateUserID(product.getCreateUserID());
            int vehicleID = vehicleMapper.insert(product.getVehicle());

            product.setDriverID(driverID);
            product.setVehicleID(vehicleID);
            product.setCreateTime(new Date());
            product.setUpdateUserID(product.getCreateUserID());
            int productID = productMapper.insert(product);
            product = productMapper.selectByPrimaryKey(productID);
            return product;
        } catch (Exception e) {
            logger.fatal(e);
            throw e;
        }
    }

    @Transactional
    @Override
    public GaugeProduct updateProductInfo(GaugeProduct product) {
        try {         
            driverMapper.updateByPrimaryKey(product.getDriver());
            vehicleMapper.updateByPrimaryKey(product.getVehicle());
            productMapper.updateByPrimaryKey(product);
            return product;
        } catch (Exception e) {
            logger.fatal(e);
            throw e;
        }
    }

    @Override
    public GaugeProduct getProduct(Integer productID) {
        try {
            GaugeProduct product = productMapper.selectByPrimaryKey(productID);
            return product;
        } catch (Exception e) {
            logger.fatal(e);
            throw e;
        }
    }

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
