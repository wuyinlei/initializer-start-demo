package com.spring.cloud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.cloud.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class WeatherDataServiceImpl implements WeatherDataService{

    private static final String WEATHER_URI = "https://www.apiopen.top/weatherApi?";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public WeatherResponse getDataByCityId(String cityId) {
        //http://wthrcdn.etouch.cn/weather_mini?citykey=101280601
        String uri =  WEATHER_URI + "citykey=" + cityId;
        WeatherResponse resp = doGetWeahter(uri);

        return resp;
    }

    private static final String WEATHER_DATA= "{\"data\":{\"yesterday\":{\"date\":\"11日星期二\",\"high\":\"高温 29℃\",\"fx\":\"无持续风向\",\"low\":\"低温 26℃\",\"fl\":\"<![CDATA[<3级]]>\",\"type\":\"中雨\"},\"city\":\"深圳\",\"forecast\":[{\"date\":\"12日星期三\",\"high\":\"高温 30℃\",\"fengli\":\"<![CDATA[<3级]]>\",\"low\":\"低温 24℃\",\"fengxiang\":\"无持续风向\",\"type\":\"雷阵雨\"},{\"date\":\"13日星期四\",\"high\":\"高温 29℃\",\"fengli\":\"<![CDATA[3-4级]]>\",\"low\":\"低温 24℃\",\"fengxiang\":\"西南风\",\"type\":\"大暴雨\"},{\"date\":\"14日星期五\",\"high\":\"高温 30℃\",\"fengli\":\"<![CDATA[3-4级]]>\",\"low\":\"低温 24℃\",\"fengxiang\":\"南风\",\"type\":\"大雨\"},{\"date\":\"15日星期六\",\"high\":\"高温 31℃\",\"fengli\":\"<![CDATA[<3级]]>\",\"low\":\"低温 27℃\",\"fengxiang\":\"无持续风向\",\"type\":\"阵雨\"},{\"date\":\"16日星期天\",\"high\":\"高温 32℃\",\"fengli\":\"<![CDATA[<3级]]>\",\"low\":\"低温 27℃\",\"fengxiang\":\"无持续风向\",\"type\":\"阵雨\"}],\"ganmao\":\"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。\",\"wendu\":\"30\"},\"status\":1000,\"desc\":\"OK\"}";

    private WeatherResponse doGetWeahter(String uri)  {

        ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);
        try {
            System.out.println(" respString " + respString);
        }catch (Exception e){

        }
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse resp = null;
        String strBody = null;

        if (respString.getStatusCodeValue() == 200) {
            strBody = respString.getBody();
        }

        try {
            resp = mapper.readValue(strBody, WeatherResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resp;
    }


    @Override
    public WeatherResponse getDataByCityName(String cityName) {
        //http://wthrcdn.etouch.cn/weather_mini?city=深圳
        String uri = WEATHER_URI + "city="+ cityName;
        WeatherResponse resp = doGetWeahter(uri);
        return resp;
    }
}
