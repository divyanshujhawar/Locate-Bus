package com.example.dell.findbus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    EditText mEmail, mPassword;

    public ProgressDialog dialog;

    String userContact;
    String userName;
    String userLocation;
    int flag = 0;

    Button login;
    Button register;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mEmail = (EditText) findViewById(R.id.tvEmail);
        mPassword = (EditText) findViewById(R.id.tvPassword);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(login.this);
                dialog.setMessage("Please wait !!");
                dialog.show();
                signIn();
            }
        });

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, sign_up.class));
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            //Toast.makeText(this, "Status " + currentUser.isEmailVerified(), Toast.LENGTH_SHORT).show();
            //currentUser.sendEmailVerification();
            /*
            if (currentUser.isEmailVerified()) {
                Intent intent = new Intent(LogInActivity.this, MapsActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }
            */
        }
    }

    private void signIn() {
        Log.i("Check: ", "Inside sign In");
        flag = 0;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("College");
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if (!validateForm()) {
            return;
        }
        Log.i("check:", "Form Validated");
        assert currentUser != null;

        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        Log.i("Email", email + "\n");
        Log.i("Password", password + "\n");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    College us = snapshot.getValue(College.class);
                    assert us != null;

                    if (us.getEmail().equals(email)) {
                        //Toast.makeText(SignInActivity.this, currentUser.isEmailVerified() + "", Toast.LENGTH_SHORT).show();
                        //Log.i("check:", "going inside log in");
                        logIn(email, password);
                        flag = 1;
                        break;
                    }

                }

                if (flag == 0) {
                    dialog.dismiss();
                    Toast.makeText(login.this, "Email id is not registered!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void logIn(final String email, String password) {
        Log.i("check: ", "inside log in");

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final FirebaseUser currentUser = mAuth.getCurrentUser();
                        if (task.isSuccessful()) {
                            Log.i("check: ", "task is successful");
                            assert currentUser != null;
                            Log.i("check: ", "email verified");
                            fetchUserDetails();
                            Intent intent = new Intent(login.this, bus_slots_manage.class);
                            intent.putExtra("flag","0");
                            startActivity(intent);
                            finish();
                            dialog.dismiss();


                        } else {

                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                        if (!task.isSuccessful()) {

                        }
                        //hideProgressDialog();
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email address", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            mEmail.setError(null);
        }

        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            mPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        finish();
    }

    public boolean fetchUserDetails() {
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("College");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    College user = snapshot.getValue(College.class);
                    if (user.getEmail().equals(currentUser.getEmail())) {
                        userContact = user.getContact();
                        userName = user.getName();
                        userLocation = user.getAddress();
                        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("contact", userContact);
                        editor.putString("name", userName);
                        editor.putString("location", userLocation);
                        editor.putString("email",user.getEmail());
                        editor.commit();

                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return false;
    }

}
