package com.spring.cloud.job;

import com.spring.cloud.service.CityDataService;
import com.spring.cloud.service.WeatherDataService;
import com.spring.cloud.vo.City;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;


public class WeatherDataSyncJob extends QuartzJobBean {

    private final static Logger logger = LoggerFactory.getLogger(WeatherDataSyncJob.class);

    @Autowired
    private CityDataService cityDataService;

    @Autowired
    private WeatherDataService weatherDataService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("Weather Data Sync job");

//        // 获取城市ID列表
//        List<City> cityList = null;
//
//        try {
//            cityList = cityDataService.listCity();
//        } catch (Exception e) {
//            logger.error("Exception!", e);
//        }
//
//        // 遍历城市ID获取天气
//        for (City city : cityList) {
//            String cityId = city.getCityId();
//            logger.info("Weather Data Sync Job, cityId:" + cityId);
//
//            weatherDataService.syncDateByCityId(cityId);
//        }
    }

}
