package com.deepak.journalapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.deepak.journalapp.entity.JournalEntry;
import com.deepak.journalapp.entity.User;
import com.deepak.journalapp.repository.JournalEntryRepository;

@Component
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository journalEntryRepository;

	@Autowired
	private UserService userService;//HNwyNP5AVz4Pjafk

	@Transactional
	public void createJournal(JournalEntry journalEntry, String userName) {
		try {
			User user = userService.findByUserName(userName);
			journalEntry.setDateTime(LocalDateTime.now());
			JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
			user.getJournalEntries().add(savedEntry);
			userService.createUser(user);
		} catch (Exception e) {
			throw new RuntimeException("An error has been occurred...");
		}
	}

	public void createJournal(JournalEntry journalEntry) {
		journalEntry.setDateTime(LocalDateTime.now());
		journalEntryRepository.save(journalEntry);

	}

	public List<JournalEntry> getAllEntires(){
		return journalEntryRepository.findAll();
	}

	public Optional<JournalEntry> getJournalById(ObjectId journalId){
		return journalEntryRepository.findById(journalId);
	}

	public JournalEntry updateJournalById(ObjectId journalId){
		return journalEntryRepository.findById(journalId).orElse(null);
	}

	@Transactional
	public boolean deleteJournalById(ObjectId journalId, String userName){
		boolean removed = false;
		try {
			User user = userService.findByUserName(userName);
			removed = user.getJournalEntries().removeIf(x -> x.getId().equals(journalId));
			if(removed) {
				userService.createUser(user);
				journalEntryRepository.deleteById(journalId);
			}
		}catch (Exception e) {
			throw new RuntimeException("An error occured while deleting the entires...");
		}
		return removed;
	}

}
