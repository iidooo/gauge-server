package com.iidooo.gauge.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iidooo.core.model.Page;
import com.iidooo.gauge.mapper.GaugeItemMapper;
import com.iidooo.gauge.model.po.GaugeItem;
import com.iidooo.gauge.service.GaugeItemService;

@Service
public class GaugeItemServiceImpl implements GaugeItemService {

    private static final Logger logger = Logger.getLogger(GaugeItemServiceImpl.class);
    
    @Autowired
    private GaugeItemMapper gaugeItemMapper;
    
    @Override
    public List<GaugeItem> getGaugeItemRealTime(Page page) {
        List<GaugeItem> result = new ArrayList<GaugeItem>();
        try {
            result = gaugeItemMapper.selectList(page);
        } catch (Exception e) {
            logger.fatal(e);
        }
        return result;
    }

}
