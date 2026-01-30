package com.panganiban.salosalo.backend;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Map;

public class FirestoreHelper {
    private final FirebaseFirestore db;

    public FirestoreHelper() {
        db = FirebaseFirestore.getInstance();
    }

    // CREATE - Add a new document with a generated ID
    public Task<DocumentReference> addDocument(String collection, Map<String, Object> data) {
        return db.collection(collection).add(data);
    }

    // READ - Get all documents from a collection
    public Task<QuerySnapshot> getAllDocuments(String collection) {
        return db.collection(collection).get();
    }

    // UPDATE - Update a specific document using its ID
    public Task<Void> updateDocument(String collection, String docId, Map<String, Object> data) {
        return db.collection(collection).document(docId).update(data);
    }

    // DELETE - Remove a document
    public Task<Void> deleteDocument(String collection, String docId) {
        return db.collection(collection).document(docId).delete();
    }

    //Checkign database connection
    public Task<QuerySnapshot> checkConnection() {
        // We just try to get 1 document to see if the server responds
        return db.collection("connection_test").limit(1).get();
    }
}
