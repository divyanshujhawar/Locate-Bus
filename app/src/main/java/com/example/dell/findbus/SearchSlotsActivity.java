package com.example.dell.findbus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dell.findbus.helper.College;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.dell.findbus.R.drawable.user;

public class SearchSlotsActivity extends AppCompatActivity {

    EditText collegeName;
    Button find_bus;

    DatabaseReference databaseReference;
    DatabaseReference databaseDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bus);

        collegeName = (EditText)findViewById(R.id.college_name);
        find_bus = (Button)findViewById(R.id.find_bus);
        find_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForSearch();
            }
        });
    }

    public void checkForSearch(){
        databaseReference = FirebaseDatabase.getInstance().getReference("College");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    College college = snapshot.getValue(College.class);
                    if(college.getName().equals(collegeName.getText().toString())){
                        //fetchSlots(college.getEmail());
                        Intent intent = new Intent(SearchSlotsActivity.this,bus_slots_manage.class);
                        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("contact", college.getContact());
                        editor.putString("name", college.getName());
                        editor.putString("location", college.getAddress());
                        editor.putString("email",college.getEmail());
                        editor.commit();
                        intent.putExtra("flag","1");
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
