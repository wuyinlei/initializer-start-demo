package com.spring.cloud.vo;

public class WeatherResponse extends BaseVo{

    private Weather data;
    private Integer status;
    private String desc;
    public Weather getData() {
        return data;
    }
    public void setData(Weather data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
