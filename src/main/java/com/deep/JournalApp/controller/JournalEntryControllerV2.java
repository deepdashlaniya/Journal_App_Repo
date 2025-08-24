package com.deep.JournalApp.controller;

import java.util.List;
import java.util.Optional;

import com.deep.JournalApp.Entity.User;
import com.deep.JournalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deep.JournalApp.Entity.JournalEntry;
import com.deep.JournalApp.service.JournalEntryService;

@RestController()
@RequestMapping("/journalV2")
public class JournalEntryControllerV2 {

	@Autowired()
	private JournalEntryService journalEntryService;

    @Autowired()
    private UserService userService;
	
	@PostMapping("/{userName}")
	public ResponseEntity<JournalEntry> saveJournalEntry(@RequestBody JournalEntry journalEntry, @PathVariable String userName) {
		try {
			journalEntryService.saveJournalEntry(journalEntry, userName);
			return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}
		 
	}
	
	
	@GetMapping("/{userName}")
	public ResponseEntity<JournalEntry> getAllJournalEntriesOfUser(@PathVariable String userName){

        User user = userService.findByUserName(userName);

        List<JournalEntry> all = user.getJournalEntries();
		 if(all != null && !all.isEmpty()) {
			 return new ResponseEntity(all, HttpStatus.OK);
		 }
		 return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	
	@GetMapping("/id/{myId}") // this syntax contain pathvariable myId
	public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myId) {
		Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
		if(journalEntry.isPresent()){
			return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}	
	
	@DeleteMapping("/id/{userName}/{myId}")
	public ResponseEntity<?> deleteById(@PathVariable ObjectId myId, @PathVariable String userName) {
		
			journalEntryService.removeEntry(myId, userName);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	
	@PutMapping("/id/{userName}/{id}")
	public ResponseEntity<?> updateJournal(@PathVariable String userName,@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {

		JournalEntry old = journalEntryService.findById(id).orElse(null);
		if(old != null) {
			old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("")?newEntry.getTitle(): old.getTitle());
			old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")?newEntry.getContent() : old.getContent());
			journalEntryService.saveJournalEntry(old);
			return new ResponseEntity(old,HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
}
