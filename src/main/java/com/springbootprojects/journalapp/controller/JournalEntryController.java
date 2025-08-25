package com.springbootprojects.journalapp.controller;

import com.springbootprojects.journalapp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    private final Map<Long, JournalEntry> journalEntries = new HashMap<>();


    @GetMapping
    public List<JournalEntry> getJournalEntries() {
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createJournalEntry(@RequestBody JournalEntry journalEntry) {
        journalEntries.put(journalEntry.getId(), journalEntry);
        return true;
    }

    @GetMapping("/id/{journalId}")
    public JournalEntry getJournalEntryById(@PathVariable Long journalId) {
        return journalEntries.get(journalId);
    }

    @DeleteMapping("id/{journalId}")
    public JournalEntry deleteJournalEntryById(@PathVariable Long journalId) {
        return journalEntries.remove(journalId);
    }

    @PutMapping("id/{journalId}")
    public JournalEntry updateJournalEntryById(@PathVariable Long journalId, @RequestBody JournalEntry journalEntry) {
        return journalEntries.put(journalId, journalEntry);
    }
}
