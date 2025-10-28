package com.springbootprojects.journalapp.scheduler;

import com.springbootprojects.journalapp.cache.AppCache;
import com.springbootprojects.journalapp.entity.JournalEntry;
import com.springbootprojects.journalapp.entity.User;
import com.springbootprojects.journalapp.enums.Sentiment;
import com.springbootprojects.journalapp.repository.UserRepositoryImpl;
import com.springbootprojects.journalapp.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class UserScheduler {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

    @Autowired
    private EmailService emailService;

//    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail(){
        List<User> allUsers = userRepository.getUserForSA();
        for (User user : allUsers) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).toList();
            Map<Sentiment, Integer> sentimentsCount = new HashMap<>();
            for (Sentiment sentiment : filteredEntries) {
                if (sentiment != null) {
                    sentimentsCount.put(sentiment, sentimentsCount.getOrDefault(sentiment, 0) + 1);
                }
            }

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentsCount.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if (mostFrequentSentiment != null) {
                emailService.sendMail(user.getEmail(), "Weekly Sentiment Analysis", mostFrequentSentiment.toString());
                log.info(mostFrequentSentiment.toString());
            }

        }
    }

//    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }
}
