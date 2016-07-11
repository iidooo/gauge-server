package com.iidooo.gauge.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iidooo.core.constant.RegularConstant;
import com.iidooo.core.enums.ResponseStatus;
import com.iidooo.core.model.Page;
import com.iidooo.core.model.ResponseResult;
import com.iidooo.core.util.DateUtil;
import com.iidooo.core.util.PageUtil;
import com.iidooo.core.util.StringUtil;
import com.iidooo.core.util.ValidateUtil;
import com.iidooo.gauge.model.po.GaugeItem;
import com.iidooo.gauge.model.vo.SearchCondition;
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
    
    @ResponseBody
    @RequestMapping(value = "/searchGaugeItemList", method = RequestMethod.POST)
    public ResponseResult searchGaugeItemList(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        try {
            String productCode = request.getParameter("productCode");
            String vehicleLicense = request.getParameter("vehicleLicense");
            String temperature = request.getParameter("temperature");
            String tempCompareLabel = request.getParameter("tempCompareLabel");
            String pressure = request.getParameter("pressure");
            String pressureCompareLabel = request.getParameter("pressureCompareLabel");
            String startDateTime = request.getParameter("startDateTime");
            String endDateTime = request.getParameter("endDateTime");

            SearchCondition condition = new SearchCondition();
            condition.setProductCode(productCode);
            condition.setVehicleLicense(vehicleLicense);
            if (ValidateUtil.isMatch(temperature, RegularConstant.REGEX_FLOAT)) {
                condition.setTemperature(Float.parseFloat(temperature));
            }
            if (tempCompareLabel != null && (tempCompareLabel.equals(">=") || tempCompareLabel.equals("<="))) {
                condition.setTempCompareLabel(tempCompareLabel);
            }
            if (ValidateUtil.isMatch(pressure, RegularConstant.REGEX_FLOAT)) {
                condition.setPressure(Float.parseFloat(pressure));
            }
            if (pressureCompareLabel != null && (pressureCompareLabel.equals(">=") || pressureCompareLabel.equals("<="))) {
                condition.setPressureCompareLabel(pressureCompareLabel);
            }
            if (ValidateUtil.validateDateFormat(startDateTime, DateUtil.DATE_TIME_HYPHEN)) {
                condition.setStartDateTime(DateUtil.getDate(startDateTime, DateUtil.DATE_TIME_HYPHEN));
            }
            if (ValidateUtil.validateDateFormat(endDateTime, DateUtil.DATE_TIME_HYPHEN)) {
                condition.setEndDateTime(DateUtil.getDate(endDateTime, DateUtil.DATE_TIME_HYPHEN));
            }
            int recordSum = gaugeItemService.getGaugeItemListCount(condition);

            Page page = new Page();
            String sortField = request.getParameter("sortField");
            if (StringUtil.isNotBlank(sortField)) {
                page.setSortField(sortField);
            }
            String sortType = request.getParameter("sortType");
            if (StringUtil.isNotBlank(sortType)) {
                page.setSortType(sortType);
            }
            String pageSize = request.getParameter("pageSize");
            if (StringUtil.isNotBlank(pageSize) && ValidateUtil.isMatch(pageSize, RegularConstant.REGEX_NUMBER)) {
                page.setPageSize(Integer.parseInt(pageSize));
            }
            String currentPage = request.getParameter("currentPage");
            if (StringUtil.isNotBlank(currentPage) && 
                    ValidateUtil.isMatch(currentPage, RegularConstant.REGEX_NUMBER) && 
                    Integer.parseInt(currentPage) > 0) {
                page.setCurrentPage(Integer.parseInt(currentPage));
            }
            page = PageUtil.executePage(recordSum, page);

            Map<String, Object> data = new HashMap<String, Object>();
            List<GaugeItem> itemList = gaugeItemService.getGaugeItemList(condition, page);
            data.put("page", page);
            data.put("itemList", itemList);
            // 返回找到的内容对象
            result.setStatus(ResponseStatus.OK.getCode());
            result.setData(data);

        } catch (Exception e) {
            logger.fatal(e);
            result.checkException(e);
        }
        return result;
    }
}
