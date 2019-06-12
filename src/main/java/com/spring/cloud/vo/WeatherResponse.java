package com.spring.cloud.vo;

public class WeatherResponse extends BaseVo{

    private Weather data;
    private Integer code;
    private String msg;
    public Weather getData() {
        return data;
    }
    public void setData(Weather data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
