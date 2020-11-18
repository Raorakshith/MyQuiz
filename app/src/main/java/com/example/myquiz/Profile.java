package com.example.myquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
CircleImageView profileim;
TextView name,email;
FirebaseAuth mAuth;
DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth=FirebaseAuth.getInstance();
        mRef= FirebaseDatabase.getInstance().getReference("userdata").child(mAuth.getCurrentUser().getUid()).child("Profile");
        name=findViewById(R.id.profilename);
        email=findViewById(R.id.profileemail);
        profileim=findViewById(R.id.profileimg);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("profileimage")) {
                    String imgu = snapshot.child("profileimage").getValue().toString();
                    Picasso.with(Profile.this).load(imgu).into(profileim);
                }
                if(snapshot.hasChild("useremail")) {
                    String emails = snapshot.child("useremail").getValue().toString();
                    email.setText(emails);
                }
                if(snapshot.hasChild("username")) {
                    String names = snapshot.child("username").getValue().toString();
                    name.setText(names);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}