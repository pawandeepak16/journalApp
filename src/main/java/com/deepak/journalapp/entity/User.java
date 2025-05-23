package com.deepak.journalapp.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "users")
@Data
@Builder
public class User {

	@Id
	private ObjectId id;
	@Indexed(unique = true)
	@NonNull
	private String userName;
	@NonNull
	private String password;
	
	private List<String> roles;
	@DBRef
	private List<JournalEntry> journalEntries = new ArrayList<>();

}
