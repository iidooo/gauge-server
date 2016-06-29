package com.iidooo.gauge.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iidooo.core.enums.ResponseStatus;
import com.iidooo.core.model.Page;
import com.iidooo.core.model.ResponseResult;
import com.iidooo.gauge.model.po.GaugeItem;
import com.iidooo.gauge.service.GaugeItemService;

@Controller
public class GaugeItemController {
    private static final Logger logger = Logger.getLogger(GaugeItemController.class);

    @Autowired
    private GaugeItemService gaugeItemService;
    
    @ResponseBody
    @RequestMapping(value = "/getGaugeItemRealTime", method = RequestMethod.POST)
    public ResponseResult getGaugeItemRealTime(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        try {
            String pageSize = request.getParameter("pageSize");
            if (!result.checkFieldInteger("pageSize", pageSize)) {
                pageSize = "100";
            }
            
            Page page = new Page();
            page.setPageSize(Integer.parseInt(pageSize));

            List<GaugeItem> gaugeItems = gaugeItemService.getGaugeItemRealTime(page);
            // 返回找到的内容对象
            result.setStatus(ResponseStatus.OK.getCode());
            result.setData(gaugeItems);

        } catch (Exception e) {
            logger.fatal(e);
            result.checkException(e);
        }
        return result;
    }
}
