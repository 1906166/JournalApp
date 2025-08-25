package com.springbootprojects.journalapp.controller;

import com.springbootprojects.journalapp.entity.JournalEntry;
import com.springbootprojects.journalapp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    private final Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getJournalEntries() {
        return journalEntryService.getAllJournalEntry();
    }

    @PostMapping
    public JournalEntry createJournalEntry(@RequestBody JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        return journalEntryService.saveEntry(journalEntry);
    }

    @GetMapping("/id/{journalId}")
    public JournalEntry getJournalEndBytryById(@PathVariable ObjectId journalId) {
        return journalEntryService.findById(journalId);
    }

    @DeleteMapping("id/{journalId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId journalId) {
        return journalEntryService.deleteById(journalId);
    }

    @PutMapping("id/{journalId}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId journalId, @RequestBody JournalEntry newEntry) {
        JournalEntry old = journalEntryService.findById(journalId);

        if (old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
        }

        journalEntryService.saveEntry(old);
        return old;
    }
}
