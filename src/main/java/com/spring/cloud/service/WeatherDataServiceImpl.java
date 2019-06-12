package com.spring.cloud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.cloud.vo.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

    private static Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);

    private static final String WEATHER_URI = "https://www.apiopen.top/weatherApi?";
    private static final String WEATHER_URI_BY_CITY_ID = "http://wthrcdn.etouch.cn/weather_mini?";

    private static final long TIME_OUT = 1800L;  //半个小时

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public WeatherResponse getDataByCityId(String cityId) {
        //http://wthrcdn.etouch.cn/weather_mini?citykey=101280601
        String uri = WEATHER_URI_BY_CITY_ID + "citykey=" + cityId;
        logger.info(" getDataByCityId " + cityId);
        logger.info(" WEATHER_URI " + uri);
        WeatherResponse resp = doGetWeahter(uri);
        logger.info(" WeatherResponse " + resp.getData().toString());
        return resp;
    }

    private static final String WEATHER_DATA = "{\"data\":{\"yesterday\":{\"date\":\"11日星期二\",\"high\":\"高温 29℃\",\"fx\":\"无持续风向\",\"low\":\"低温 26℃\",\"fl\":\"<![CDATA[<3级]]>\",\"type\":\"中雨\"},\"city\":\"深圳\",\"forecast\":[{\"date\":\"12日星期三\",\"high\":\"高温 30℃\",\"fengli\":\"<![CDATA[<3级]]>\",\"low\":\"低温 24℃\",\"fengxiang\":\"无持续风向\",\"type\":\"雷阵雨\"},{\"date\":\"13日星期四\",\"high\":\"高温 29℃\",\"fengli\":\"<![CDATA[3-4级]]>\",\"low\":\"低温 24℃\",\"fengxiang\":\"西南风\",\"type\":\"大暴雨\"},{\"date\":\"14日星期五\",\"high\":\"高温 30℃\",\"fengli\":\"<![CDATA[3-4级]]>\",\"low\":\"低温 24℃\",\"fengxiang\":\"南风\",\"type\":\"大雨\"},{\"date\":\"15日星期六\",\"high\":\"高温 31℃\",\"fengli\":\"<![CDATA[<3级]]>\",\"low\":\"低温 27℃\",\"fengxiang\":\"无持续风向\",\"type\":\"阵雨\"},{\"date\":\"16日星期天\",\"high\":\"高温 32℃\",\"fengli\":\"<![CDATA[<3级]]>\",\"low\":\"低温 27℃\",\"fengxiang\":\"无持续风向\",\"type\":\"阵雨\"}],\"ganmao\":\"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。\",\"wendu\":\"30\"},\"status\":1000,\"desc\":\"OK\"}";

    private WeatherResponse doGetWeahter(String uri) {

        //先查缓存  有就使用缓存的数据
        //缓存没有  在请求数据
        String key = uri;
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse resp = null;
        String strBody = null;
        ValueOperations<String, String> localValue = stringRedisTemplate.opsForValue();
        if (stringRedisTemplate.hasKey(key)) {
            //从缓存中获取数据
            strBody = localValue.get(key);
        } else {
            ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);

            if (respString.getStatusCodeValue() == 200) {
                strBody = respString.getBody();
            }

            //数据写入缓存
            localValue.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);
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
        String uri = WEATHER_URI + "city=" + cityName;
        WeatherResponse resp = doGetWeahter(uri);
        return resp;
    }

    @Override
    public void syncDateByCityId(String cityId) {
        String uri = WEATHER_URI_BY_CITY_ID + "citykey=" + cityId;
        this.saveWeatherData(uri);
    }

    /**
     * 把天气数据放在缓存
     * @param uri
     */
    private void saveWeatherData(String uri) {
        String key = uri;
        String strBody = null;
        ValueOperations<String, String>  ops = stringRedisTemplate.opsForValue();

        // 调用服务接口来获取
        ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);

        if (respString.getStatusCodeValue() == 200) {
            strBody = respString.getBody();
        }
        // 数据写入缓存
        ops.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);

    }
}
