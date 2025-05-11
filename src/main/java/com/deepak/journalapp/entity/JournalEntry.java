package com.deepak.journalapp.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Getter;
import lombok.Setter;

@Document("journal_entries")
@Getter
@Setter
public class JournalEntry {

	@Id
	private ObjectId id;
	@NonNull
	private String title;
	private String content;
	private LocalDateTime dateTime;
	
}
