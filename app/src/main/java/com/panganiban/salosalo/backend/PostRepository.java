package com.panganiban.salosalo.backend;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.panganiban.salosalo.models.Post;

public class PostRepository {
    private final FirebaseFirestore db;
    private final FirebaseAuth auth;
    private static final String COLLECTION_POSTS = "posts";

    public PostRepository() {
        this.db = FirebaseFirestore.getInstance();
        this.auth = FirebaseAuth.getInstance();
    }

    public Task<DocumentReference> uploadPost(String title, String description) {
        FirebaseUser currentUser = auth.getCurrentUser();
        String userId = currentUser != null ? currentUser.getUid() : "anonymous";
        String userName = "Guest";
        
        if (currentUser != null) {
            if (currentUser.getDisplayName() != null && !currentUser.getDisplayName().isEmpty()) {
                userName = currentUser.getDisplayName();
            } else if (currentUser.getEmail() != null) {
                userName = currentUser.getEmail();
            }
        }

        Post post = new Post(title, description, userId, userName);
        return db.collection(COLLECTION_POSTS).add(post);
    }

    public Query getRecentPostsQuery() {
        return db.collection(COLLECTION_POSTS)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(50);
    }

    public Query searchPostsQuery(String queryText) {
        return db.collection(COLLECTION_POSTS)
                .orderBy("title")
                .startAt(queryText)
                .endAt(queryText + "\uf8ff");
    }
}
