package com.jgutierrez.studentorg.firebaseService;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseService {
    static DatabaseReference instance;
    public static DatabaseReference getInstance() {
        if(instance == null) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            instance = FirebaseDatabase.getInstance().getReference(String.format("/users/%s", FirebaseAuth.getInstance().getUid()));
        }
        return instance;
    }
}
