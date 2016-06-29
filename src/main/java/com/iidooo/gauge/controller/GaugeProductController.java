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

import com.iidooo.core.enums.ResponseStatus;
import com.iidooo.core.model.Page;
import com.iidooo.core.model.ResponseResult;
import com.iidooo.core.util.PageUtil;
import com.iidooo.core.util.StringUtil;
import com.iidooo.core.util.ValidateUtil;
import com.iidooo.gauge.model.po.GaugeProduct;
import com.iidooo.gauge.service.GaugeProductService;

@Controller
public class GaugeProductController {

    private static final Logger logger = Logger.getLogger(GaugeProductController.class);
    
    @Autowired
    private GaugeProductService productService;
    
    @ResponseBody
    @RequestMapping(value = "/searchProductList", method = RequestMethod.POST)
    public ResponseResult searchProductList(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        try {
            String productCode = request.getParameter("productCode");
            String vehicleLicense = request.getParameter("vehicleLicense");
            String driverName = request.getParameter("driverName");
            String driverPhone = request.getParameter("driverPhone");

            GaugeProduct product = new GaugeProduct();
            product.setProductCode(productCode);
            product.getVehicle().setLicense(vehicleLicense);
            product.getDriver().setFirstName(driverName);
            product.getDriver().setPhone(driverPhone);
            int recordSum = productService.getProductListCount(product);

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
            if (StringUtil.isNotBlank(pageSize) && ValidateUtil.isNumber(pageSize)) {
                page.setPageSize(Integer.parseInt(pageSize));
            }
            String currentPage = request.getParameter("currentPage");
            if (StringUtil.isNotBlank(currentPage) && ValidateUtil.isNumber(currentPage) && Integer.parseInt(currentPage) > 0) {
                page.setCurrentPage(Integer.parseInt(currentPage));
            }
            page = PageUtil.executePage(recordSum, page);

            Map<String, Object> data = new HashMap<String, Object>();
            List<GaugeProduct> productList = productService.getProductList(product, page);
            data.put("page", page);
            data.put("productList", productList);
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
