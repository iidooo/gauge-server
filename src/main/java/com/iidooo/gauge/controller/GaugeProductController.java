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
import com.iidooo.gauge.model.po.GaugeProduct;
import com.iidooo.gauge.service.GaugeProductService;

@Controller
public class GaugeProductController {

    private static final Logger logger = Logger.getLogger(GaugeProductController.class);

    @Autowired
    private GaugeProductService productService;

    @ResponseBody
    @RequestMapping(value = "/getProduct", method = RequestMethod.POST)
    public ResponseResult getProduct(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        try {
            String productID = request.getParameter("productID");

            result.checkFieldRequired("productID", productID);
            result.checkFieldInteger("productID", productID);

            if (result.getMessages().size() > 0) {
                // 验证失败，返回message
                result.setStatus(ResponseStatus.Failed.getCode());
                return result;
            }

            GaugeProduct product = productService.getProduct(Integer.parseInt(productID));
            if (product == null) {
                result.setStatus(ResponseStatus.Failed.getCode());
            } else {
                // 返回找到的内容对象
                result.setStatus(ResponseStatus.OK.getCode());
                result.setData(product);
            }

        } catch (Exception e) {
            logger.fatal(e);
            result.checkException(e);
        }
        return result;
    }

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
            if (StringUtil.isNotBlank(pageSize) && ValidateUtil.isMatch(pageSize, RegularConstant.REGEX_NUMBER)) {
                page.setPageSize(Integer.parseInt(pageSize));
            }
            String currentPage = request.getParameter("currentPage");
            if (StringUtil.isNotBlank(currentPage) && ValidateUtil.isMatch(currentPage, RegularConstant.REGEX_NUMBER)
                    && Integer.parseInt(currentPage) > 0) {
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

    @ResponseBody
    @RequestMapping(value = "/createProduct", method = RequestMethod.POST)
    public ResponseResult createProduct(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        try {
            String userID = request.getParameter("userID");
            String productModel = request.getParameter("productModel");
            String productCode = request.getParameter("productCode");
            String productCity = request.getParameter("productCity");
            String vehicleLicense = request.getParameter("vehicleLicense");
            String vehicleNo = request.getParameter("vehicleNo");
            String vehicleType = request.getParameter("vehicleType");
            String vehicleVolumn = request.getParameter("vehicleVolumn");
            String vehicleMaker = request.getParameter("vehicleMaker");
            String vehicleModel = request.getParameter("vehicleModel");
            String driverFirstName = request.getParameter("driverFirstName");
            String driverLastName = request.getParameter("driverLastName");
            String driverBirthday = request.getParameter("driverBirthday");
            String driverPhone = request.getParameter("driverPhone");
            String driverLicense = request.getParameter("driverLicense");
            String driverLicenseType = request.getParameter("driverLicenseType");
            String driverLicenseStart = request.getParameter("driverLicenseStart");
            String driverLicenseEnd = request.getParameter("driverLicenseEnd");

            result.checkFieldRequired("userID", userID);
            result.checkFieldInteger("userID", userID);
            result.checkFieldRequired("productCode", productCode);
            result.checkFieldRequired("vehicleLicense", vehicleLicense);
            result.checkFieldRequired("driverFirstName", driverFirstName);
            result.checkFieldRequired("driverLastName", driverLastName);
            result.checkFieldRequired("driverPhone", driverPhone);
            result.checkFieldRequired("driverLicense", driverLicense);

            // 产品编号不能重复
            result.checkFieldUnique("productCode", productService.getProductByCode(productCode));

            if (result.getMessages().size() > 0) {
                // 验证失败，返回message
                result.setStatus(ResponseStatus.ValidateFailed.getCode());
                return result;
            }

            GaugeProduct product = new GaugeProduct();
            product.setProductModel(productModel);
            product.setProductCode(productCode);
            product.setCity(productCity);
            product.setCreateUserID(Integer.parseInt(userID));
            product.getVehicle().setLicense(vehicleLicense);
            product.getVehicle().setVehicleNo(vehicleNo);
            product.getVehicle().setType(vehicleType);
            if (ValidateUtil.isMatch(vehicleVolumn, RegularConstant.REGEX_FLOAT)) {
                product.getVehicle().setVolume(Float.parseFloat(vehicleVolumn));
            }
            product.getVehicle().setMaker(vehicleMaker);
            product.getVehicle().setModel(vehicleModel);
            product.getDriver().setFirstName(driverFirstName);
            product.getDriver().setLastName(driverLastName);
            if (StringUtil.isNotBlank(driverBirthday)) {
                product.getDriver().setBirthday(DateUtil.getDate(driverBirthday, DateUtil.DATE_HYPHEN));
            }
            product.getDriver().setPhone(driverPhone);
            product.getDriver().setLicense(driverLicense);
            product.getDriver().setLicenseType(driverLicenseType);
            if (StringUtil.isNotBlank(driverLicenseStart)) {
                product.getDriver().setValidStart(DateUtil.getDate(driverLicenseStart, DateUtil.DATE_HYPHEN));
            }
            if (StringUtil.isNotBlank(driverLicenseEnd)) {
                product.getDriver().setValidEnd(DateUtil.getDate(driverLicenseEnd, DateUtil.DATE_HYPHEN));
            }

            product = productService.createProductInfo(product);
            if (product == null || product.getProductID() == null || product.getProductID() <= 0) {
                result.setStatus(ResponseStatus.InsertFailed.getCode());
            } else {
                // 返回找到的内容对象
                result.setStatus(ResponseStatus.OK.getCode());
                result.setData(product);
            }

        } catch (Exception e) {
            logger.fatal(e);
            result.checkException(e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    public ResponseResult updateProduct(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        try {
            String userID = request.getParameter("userID");
            String productID = request.getParameter("productID");
            String productModel = request.getParameter("productModel");
            String productCode = request.getParameter("productCode");
            String productCity = request.getParameter("productCity");

            String vehicleID = request.getParameter("vehicleID");
            String vehicleLicense = request.getParameter("vehicleLicense");
            String vehicleNo = request.getParameter("vehicleNo");
            String vehicleType = request.getParameter("vehicleType");
            String vehicleVolumn = request.getParameter("vehicleVolumn");
            String vehicleMaker = request.getParameter("vehicleMaker");
            String vehicleModel = request.getParameter("vehicleModel");

            String driverID = request.getParameter("driverID");
            String driverFirstName = request.getParameter("driverFirstName");
            String driverLastName = request.getParameter("driverLastName");
            String driverBirthday = request.getParameter("driverBirthday");
            String driverPhone = request.getParameter("driverPhone");
            String driverLicense = request.getParameter("driverLicense");
            String driverLicenseType = request.getParameter("driverLicenseType");
            String driverLicenseStart = request.getParameter("driverLicenseStart");
            String driverLicenseEnd = request.getParameter("driverLicenseEnd");

            result.checkFieldRequired("userID", userID);
            result.checkFieldInteger("userID", userID);
            result.checkFieldRequired("productID", productID);
            result.checkFieldInteger("productID", productID);
            result.checkFieldRequired("productModel", productModel);
            result.checkFieldRequired("productCode", productCode);

            result.checkFieldRequired("vehicleID", vehicleID);
            result.checkFieldInteger("vehicleID", vehicleID);
            result.checkFieldRequired("vehicleLicense", vehicleLicense);

            result.checkFieldRequired("driverID", driverID);
            result.checkFieldInteger("driverID", driverID);
            result.checkFieldRequired("driverFirstName", driverFirstName);
            result.checkFieldRequired("driverLastName", driverLastName);
            result.checkFieldRequired("driverPhone", driverPhone);
            result.checkFieldRequired("driverLicense", driverLicense);

            if (result.getMessages().size() > 0) {
                // 验证失败，返回message
                result.setStatus(ResponseStatus.Failed.getCode());
                return result;
            }

            GaugeProduct product = new GaugeProduct();
            product.setProductID(Integer.parseInt(productID));
            product.setProductModel(productModel);
            product.setProductCode(productCode);
            product.setCity(productCity);
            product.setUpdateUserID(Integer.parseInt(userID));

            product.getVehicle().setVehicleID(Integer.parseInt(vehicleID));
            product.getVehicle().setLicense(vehicleLicense);
            product.getVehicle().setVehicleNo(vehicleNo);
            product.getVehicle().setType(vehicleType);
            if (ValidateUtil.isMatch(vehicleVolumn, RegularConstant.REGEX_FLOAT)) {
                product.getVehicle().setVolume(Float.parseFloat(vehicleVolumn));
            }
            product.getVehicle().setMaker(vehicleMaker);
            product.getVehicle().setModel(vehicleModel);

            product.getDriver().setDriverID(Integer.parseInt(driverID));
            product.getDriver().setFirstName(driverFirstName);
            product.getDriver().setLastName(driverLastName);
            product.getDriver().setBirthday(DateUtil.getDate(driverBirthday, DateUtil.DATE_HYPHEN));
            product.getDriver().setPhone(driverPhone);
            product.getDriver().setLicense(driverLicense);
            product.getDriver().setLicenseType(driverLicenseType);
            product.getDriver().setValidStart(DateUtil.getDate(driverLicenseStart, DateUtil.DATE_HYPHEN));
            product.getDriver().setValidEnd(DateUtil.getDate(driverLicenseEnd, DateUtil.DATE_HYPHEN));

            product = productService.updateProductInfo(product);
            if (product == null || product.getProductID() == null || product.getProductID() <= 0) {
                result.setStatus(ResponseStatus.InsertFailed.getCode());
            } else {
                // 返回找到的内容对象
                result.setStatus(ResponseStatus.OK.getCode());
                result.setData(product);
            }

        } catch (Exception e) {
            logger.fatal(e);
            result.checkException(e);
        }
        return result;
    }
}
