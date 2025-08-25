package com.springbootprojects.journalapp.service;

import com.springbootprojects.journalapp.Repository.JournalEntryRepository;
import com.springbootprojects.journalapp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public JournalEntry saveEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllJournalEntry() {
        return journalEntryRepository.findAll();
    }

    public JournalEntry findById(ObjectId id) {
        return journalEntryRepository.findById(id).orElse(null);
    }

    public boolean deleteById(ObjectId id) {
        journalEntryRepository.deleteById(id);
        return true;
    }
}
