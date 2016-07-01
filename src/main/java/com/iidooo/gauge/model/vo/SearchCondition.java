package com.iidooo.gauge.model.vo;

import java.util.Date;

public class SearchCondition {
    private String productCode;
    
    private String vehicleLicense;
    
    private Float temperature;
    
    private String tempCompareLabel;
    
    private Float pressure;
    
    private String pressureCompareLabel;
    
    private Date startDateTime;
    
    private Date endDateTime;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getVehicleLicense() {
        return vehicleLicense;
    }

    public void setVehicleLicense(String vehicleLicense) {
        this.vehicleLicense = vehicleLicense;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public String getTempCompareLabel() {
        return tempCompareLabel;
    }

    public void setTempCompareLabel(String tempCompareLabel) {
        this.tempCompareLabel = tempCompareLabel;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public String getPressureCompareLabel() {
        return pressureCompareLabel;
    }

    public void setPressureCompareLabel(String pressureCompareLabel) {
        this.pressureCompareLabel = pressureCompareLabel;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }
    
    
}
