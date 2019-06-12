package com.spring.cloud.controller;

import com.spring.cloud.service.CityDataService;
import com.spring.cloud.service.WeatherReportService;
import com.spring.cloud.vo.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/report")
public class WeatherReportController {

    @Autowired
    private CityDataService cityDataService;

    @Autowired
    private WeatherReportService reportService;

    @GetMapping("/cityId/{cityId}")
    public ModelAndView getReportByCityId(@PathVariable("cityId") String cityId, Model model) throws Exception {
        Weather weather = reportService.getDataByCityId(cityId);
        model.addAttribute("title", "天气预报");
        model.addAttribute("cityId", cityId);
        model.addAttribute("cityList", cityDataService.listCity());
        model.addAttribute("report", weather);
        return new ModelAndView("weather/report", "reportModel", model);
    }

    @GetMapping("/cityName/{cityName}")
    public Weather getReportByCityName(@PathVariable("cityName") String cityName) {
        Weather weather = reportService.getDataByCityName(cityName);
        return weather;
    }


}
