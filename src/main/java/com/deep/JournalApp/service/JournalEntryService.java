package com.deep.JournalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deep.JournalApp.Entry.JournalEntry;
import com.deep.JournalApp.Repository.JournalEntryRepository;


@Component()
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository journalEntryRepository;
	
	public JournalEntry saveJournalEntry(JournalEntry journalEntry) {
		
		
		return journalEntryRepository.save(journalEntry);
	}

	public List<JournalEntry> getAll() {

		return journalEntryRepository.findAll();
	}

	public Optional<JournalEntry> findById(ObjectId myId) {
		return journalEntryRepository.findById(myId);
	}

	public boolean removeEntry(ObjectId myId) {
		journalEntryRepository.deleteById(myId);
		return true;
	}

}
