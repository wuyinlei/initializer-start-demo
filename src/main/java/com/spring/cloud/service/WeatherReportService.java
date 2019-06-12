package com.spring.cloud.service;

import com.spring.cloud.vo.Weather;

public interface WeatherReportService {

    Weather getDataByCityId(String cityId);

    Weather getDataByCityName(String cityName);

}
