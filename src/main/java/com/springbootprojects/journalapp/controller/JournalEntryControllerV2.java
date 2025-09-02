package com.springbootprojects.journalapp.controller;

import com.springbootprojects.journalapp.entity.JournalEntry;
import com.springbootprojects.journalapp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    private final Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getJournalEntries() {
        List<JournalEntry> entries = journalEntryService.getAllJournalEntry();
        return ResponseEntity.ok(entries);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry savedEntry =  journalEntryService.saveEntry(journalEntry);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedEntry);
    }

    @GetMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId journalId) {
        return ResponseEntity.of(journalEntryService.findById(journalId));
    }

    @DeleteMapping("id/{journalId}")
    public ResponseEntity<Void> deleteJournalEntryById(@PathVariable ObjectId journalId) {
        boolean deleted =  journalEntryService.deleteById(journalId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("id/{journalId}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId journalId, @RequestBody JournalEntry newEntry) {
        Optional<JournalEntry> old = journalEntryService.findById(journalId);

        if (old.isPresent()) {
            old.get().setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.get().getTitle());
            old.get().setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.get().getContent());

            JournalEntry updated = journalEntryService.saveEntry(old.orElse(null));
            return ResponseEntity.ok(updated);
        }


        return ResponseEntity.notFound().build();
    }
}
