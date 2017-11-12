package com.example.dell.findbus;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.findbus.helper.bus_slot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class bus_slot_add extends Activity {

    EditText busNo, driverName, driverContact, startTime, driverEmail, from, to, duration;
    Button submit;

    DatabaseReference databaseReference;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_slot_add);

        mAuth = FirebaseAuth.getInstance();

        busNo = (EditText)findViewById(R.id.bus_no1);
        driverName = (EditText)findViewById(R.id.dr_name1);
        driverContact = (EditText)findViewById(R.id.contact1);
        startTime = (EditText)findViewById(R.id.start_time1);
        driverEmail = (EditText)findViewById(R.id.driver_email1);
        from = (EditText)findViewById(R.id.from1);
        to = (EditText)findViewById(R.id.to1);
        duration = (EditText)findViewById(R.id.tr_time1);

        submit = (Button) findViewById(R.id.add);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSlot();
            }
        });

    }

    public void addSlot(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Slot");
        String id = databaseReference.push().getKey();
        bus_slot b1 = new bus_slot(startTime.getText().toString(),driverName.getText().toString(),driverContact.getText().toString(),
                busNo.getText().toString(),from.getText().toString(),to.getText().toString(),"--",driverEmail.getText().toString(),
                mAuth.getCurrentUser().getEmail());
        databaseReference.child(id).setValue(b1);
        }
}
