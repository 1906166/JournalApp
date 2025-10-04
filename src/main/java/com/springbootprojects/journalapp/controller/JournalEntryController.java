package com.springbootprojects.journalapp.controller;

import com.springbootprojects.journalapp.entity.JournalEntry;
import com.springbootprojects.journalapp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    private final Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getJournalEntries() {
        List<JournalEntry> entries = journalEntryService.getAll();
        if (entries != null && !entries.isEmpty()) {
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry journalEntry) {
        try {
            journalEntryService.saveEntry(journalEntry);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{journalId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId journalId) {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(journalId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{journalId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId journalId) {
        journalEntryService.deleteById(journalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{journalId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId journalId, @RequestBody JournalEntry newEntry) {
        Optional<JournalEntry> old = journalEntryService.findById(journalId);

        if (old != null) {
            old.get().setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.get().getTitle());
            old.get().setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.get().getContent());
            journalEntryService.saveEntry(old.get());
            return new ResponseEntity<>(old, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
