package com.example.dell.findbus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.findbus.helper.College;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.dell.findbus.R.drawable.user;

public class sign_up extends AppCompatActivity {
    private EditText mEmail;
    private EditText mName;
    private EditText mPassword;
    private EditText mContact;
    private EditText mLocation;
    private EditText mConfirmPassword;

    DatabaseReference databaseUser;

    private Button mSignUp;

    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        mEmail = (EditText)findViewById(R.id.email1);
        mName = (EditText)findViewById(R.id.name1);
        mPassword = (EditText)findViewById(R.id.pswrd1);
        mConfirmPassword = (EditText)findViewById(R.id.cpswrd1);
        mContact = (EditText)findViewById(R.id.mobile1);
        mLocation = (EditText)findViewById(R.id.location1);

        mSignUp = (Button)findViewById(R.id.register);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(mName.getText().toString(),mEmail.getText().toString(),mPassword.getText().toString(),
                        mContact.getText().toString(),mLocation.getText().toString());
            }
        });
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    private void createAccount(final String name,final String email,final String password,final String contact, final String location) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(sign_up.this, "User successfully registered", Toast.LENGTH_SHORT).show();
                            addUser(name,email,contact,location);
                            startActivity(new Intent(sign_up.this,login.class));
                            finish();

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(sign_up.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
    }

    private void sendEmailVerification() {

        final FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(sign_up.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(sign_up.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String contact = mContact.getText().toString();
        if (TextUtils.isEmpty(contact)) {
            mContact.setError("Required.");
            valid = false;
        } else {
            mContact.setError(null);
        }

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Required.");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Required.");
            valid = false;
        } else {
            mPassword.setError(null);
        }

        String confirmPassword = mConfirmPassword.getText().toString();
        if (TextUtils.isEmpty(confirmPassword)) {
            mConfirmPassword.setError("Required.");
            valid = false;
        } else {
            mConfirmPassword.setError(null);
        }

        if(!(password.equals(confirmPassword))){
            valid = false;
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }

        return valid;
    }

    public void addUser(String name,String email, String contact, String location){
        Log.i("check: " , "inside addUser");
        databaseUser = FirebaseDatabase.getInstance().getReference("College");
        String id = databaseUser.push().getKey();
        Log.i("userid:" , id + "");
        College college = new College(id,name,email,location,contact);
        databaseUser.child(id).setValue(college);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
