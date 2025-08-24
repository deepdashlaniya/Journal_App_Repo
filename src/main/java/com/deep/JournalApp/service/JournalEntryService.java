package com.deep.JournalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.deep.JournalApp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deep.JournalApp.Entity.JournalEntry;
import com.deep.JournalApp.Repository.JournalEntryRepository;
import org.springframework.transaction.annotation.Transactional;


@Component()
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    // making whole process a single transaction
    @Transactional
	public void saveJournalEntry(JournalEntry journalEntry, String userName) {

        try {

        journalEntry.setDate(LocalDateTime.now());
        User user = userService.findByUserName(userName);
        JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(savedEntry);
        userService.addUser(user);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("error while saving journal entry...");
        }
    }

    public void saveJournalEntry(JournalEntry journalEntry) {

        journalEntryRepository.save(journalEntry);
    }

	public List<JournalEntry> getAll() {

		return journalEntryRepository.findAll();
	}

	public Optional<JournalEntry> findById(ObjectId myId) {
		return journalEntryRepository.findById(myId);
	}

	public boolean removeEntry(ObjectId myId, String userName) {

        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId() == myId);
        userService.addUser(user);
		journalEntryRepository.deleteById(myId);
		return true;
	}

}
