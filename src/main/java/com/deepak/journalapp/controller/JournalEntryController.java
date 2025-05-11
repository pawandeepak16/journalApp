package com.deepak.journalapp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.journalapp.entity.JournalEntry;
import com.deepak.journalapp.entity.User;
import com.deepak.journalapp.service.JournalEntryService;
import com.deepak.journalapp.service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

	//	private Map<Long, JournalEntry> journalEntries = new HashMap<>();

	@Autowired
	private JournalEntryService journalEntryService;

	@Autowired
	private UserService userService;


	@GetMapping
	public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user = userService.findByUserName(userName);
		List<JournalEntry> journalEntries = user.getJournalEntries();
		if(journalEntries != null && !journalEntries.isEmpty()) {
			return new ResponseEntity<>(journalEntries, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@PostMapping
	public ResponseEntity<JournalEntry> createJournal(@RequestBody JournalEntry journalEntry) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		try{
			journalEntryService.createJournal(journalEntry, userName);
			return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
		}catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user = userService.findByUserName(userName);
		List<JournalEntry> list = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
		if(!list.isEmpty()) {
			Optional<JournalEntry> optional = journalEntryService.getJournalById(id);
			if(optional.isPresent()) {
				return new ResponseEntity<>(optional.get(), HttpStatus.OK);
			}	
		}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		@PutMapping("/id/{id}")
		public ResponseEntity<?> updateJournalById(
				@PathVariable ObjectId id, 
				@RequestBody JournalEntry newEntry) {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			User user = userService.findByUserName(userName);
			List<JournalEntry> list = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
			if(!list.isEmpty()) {
				Optional<JournalEntry> optional = journalEntryService.getJournalById(id);
				if(optional.isPresent()) {
					JournalEntry oldEntry = optional.get();
					if(oldEntry != null) {
						oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldEntry.getTitle());
						oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
						journalEntryService.createJournal(oldEntry);  
						return new ResponseEntity<>(oldEntry, HttpStatus.OK);
					}
				}	
			}
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		@DeleteMapping("/id/{id}")
		public ResponseEntity<String> deleteJournalById(@PathVariable ObjectId id) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			boolean removed = journalEntryService.deleteJournalById(id, userName);
			if(removed) {
				String response = "User deleted....";
				return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
