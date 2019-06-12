package com.spring.cloud.service;

import com.spring.cloud.vo.Weather;
import com.spring.cloud.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherReportServiceImpl implements WeatherReportService {

    @Autowired
    private WeatherDataService weatherDataService;

    @Override
    public Weather getDataByCityId(String cityId) {
        WeatherResponse weatherResponse = weatherDataService.getDataByCityId(cityId);
        return weatherResponse.getData();
    }

    @Override
    public Weather getDataByCityName(String cityName) {
        WeatherResponse weatherResponse = weatherDataService.getDataByCityName(cityName);
        return weatherResponse.getData();
    }
}
