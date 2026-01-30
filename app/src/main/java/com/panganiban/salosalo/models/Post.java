package com.panganiban.salosalo.models;

import androidx.annotation.Keep;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

@Keep
public class Post {
    private String id;
    private String title;
    private String description;
    private String authorId;
    private String authorName;
    @ServerTimestamp
    private Timestamp timestamp;

    public Post() {
        // Required for Firestore
    }

    public Post(String title, String description, String authorId, String authorName) {
        this.title = title;
        this.description = description;
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public String getAuthorId() { return authorId; }

    public String getAuthorName() { return authorName; }

    public Timestamp getTimestamp() { return timestamp; }
}
