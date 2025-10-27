package com.springbootprojects.journalapp.service;

import com.springbootprojects.journalapp.api.response.WeatherResponse;
import com.springbootprojects.journalapp.cache.AppCache;
import com.springbootprojects.journalapp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    public String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city) {
        String finalApi = appCache.cacheMap.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.apiKey, apiKey).replace(Placeholders.city, city);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}
