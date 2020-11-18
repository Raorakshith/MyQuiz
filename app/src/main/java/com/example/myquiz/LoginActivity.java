package com.example.myquiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
SignInButton loginbtn;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN=1;
    private FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference mRef;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        loginbtn=findViewById(R.id.login);
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
    private void signIn(){
        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       // mCallbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount acc=completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }catch (ApiException e){
            Toast.makeText(this, "Signed In Failed", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }
    private void FirebaseGoogleAuth(GoogleSignInAccount acct){
        AuthCredential authCredential= GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(LoginActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user=mAuth.getCurrentUser();
                    updateUI(user);
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }
    private void updateUI(FirebaseUser firebaseUser){
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account!=null){
            mRef= FirebaseDatabase.getInstance().getReference("userdata").child(mAuth.getCurrentUser().getUid());
            String personName=account.getDisplayName();
            String personGivenName=account.getGivenName();
            String personFamilyName=account.getFamilyName();
            String personEmail=account.getEmail();
            String personId=account.getId();
            String personNum="N/A";
            Uri personPhoto=account.getPhotoUrl();
            //String perphoto=personPhoto;
            ProfileDetails profde= new ProfileDetails(personName,personEmail,String.valueOf(personPhoto),personNum);
            mRef.child("Profile").setValue(profde);
            Toast.makeText(this, "The user details are:"+personName+","+personGivenName+","+personFamilyName+","+personEmail+","+personId, Toast.LENGTH_SHORT).show();
        }
    }
}