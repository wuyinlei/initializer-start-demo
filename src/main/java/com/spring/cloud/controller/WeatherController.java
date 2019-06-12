package com.spring.cloud.controller;

import com.spring.cloud.service.WeatherDataService;
import com.spring.cloud.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherDataService weatherDataService;

    @GetMapping("/cityId/{cityId}")
    public WeatherResponse getWeatherByCityId(@PathVariable("cityId") String cityId){
        return weatherDataService.getDataByCityId(cityId);
    }

    private static final String WEATHER_DATA= "{\"data\":{\"yesterday\":{\"date\":\"11日星期二\",\"high\":\"高温 29℃\",\"fx\":\"无持续风向\",\"low\":\"低温 26℃\",\"fl\":\"<![CDATA[<3级]]>\",\"type\":\"中雨\"},\"city\":\"深圳\",\"forecast\":[{\"date\":\"12日星期三\",\"high\":\"高温 30℃\",\"fengli\":\"<![CDATA[<3级]]>\",\"low\":\"低温 24℃\",\"fengxiang\":\"无持续风向\",\"type\":\"雷阵雨\"},{\"date\":\"13日星期四\",\"high\":\"高温 29℃\",\"fengli\":\"<![CDATA[3-4级]]>\",\"low\":\"低温 24℃\",\"fengxiang\":\"西南风\",\"type\":\"大暴雨\"},{\"date\":\"14日星期五\",\"high\":\"高温 30℃\",\"fengli\":\"<![CDATA[3-4级]]>\",\"low\":\"低温 24℃\",\"fengxiang\":\"南风\",\"type\":\"大雨\"},{\"date\":\"15日星期六\",\"high\":\"高温 31℃\",\"fengli\":\"<![CDATA[<3级]]>\",\"low\":\"低温 27℃\",\"fengxiang\":\"无持续风向\",\"type\":\"阵雨\"},{\"date\":\"16日星期天\",\"high\":\"高温 32℃\",\"fengli\":\"<![CDATA[<3级]]>\",\"low\":\"低温 27℃\",\"fengxiang\":\"无持续风向\",\"type\":\"阵雨\"}],\"ganmao\":\"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。\",\"wendu\":\"30\"},\"status\":1000,\"desc\":\"OK\"}";

    @GetMapping("")
    public String testWeather(){
        return WEATHER_DATA;
    }

    @GetMapping("/cityName/{cityName}")
    public WeatherResponse getWeatherByCityName(@PathVariable("cityName") String cityName){
        return weatherDataService.getDataByCityName(cityName);
    }


}
