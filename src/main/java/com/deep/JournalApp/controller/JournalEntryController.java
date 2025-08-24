package com.deep.JournalApp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deep.JournalApp.Entity.JournalEntry;

@RestController()
@RequestMapping("/journal")
public class JournalEntryController {
	
	Map<Long,JournalEntry> journals = new HashMap<>();
	
	@GetMapping()
	public List<JournalEntry> getJournals(){
		return new ArrayList<>(journals.values());
	}
	
	@PostMapping()
	public boolean addJournal(@RequestBody() JournalEntry newEntry) {
//		journals.put(newEntry.getId(), newEntry);
		return true;
	}
	
	@GetMapping("/id/{myId}") // this syntax contain pathvariable myId
	public JournalEntry getById(@PathVariable long myId) {
		return journals.get(myId);
	}
	
	@DeleteMapping("/id/{myId}") 
	public JournalEntry deleteById(@PathVariable long myId) {
		return journals.remove(myId);
	}
	
	@PutMapping("/id/{id}")
	public JournalEntry updateJournal(@PathVariable long id, @RequestBody JournalEntry entry) {
		return journals.put(id, entry);
	}
}
