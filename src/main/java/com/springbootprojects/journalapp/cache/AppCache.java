package com.springbootprojects.journalapp.cache;

import com.springbootprojects.journalapp.entity.ConfigJournalAppEntity;
import com.springbootprojects.journalapp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys {
        WEATHER_API;
    }
    
    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    @Getter
    public Map<String, String> cacheMap;

    @PostConstruct
    public void init() {
        cacheMap = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : all) {
            cacheMap.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}
