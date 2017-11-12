package com.example.dell.findbus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.dell.findbus.helper.RecyclerItemClickListener;
import com.example.dell.findbus.helper.SpacesItemDecoration;
import com.example.dell.findbus.helper.bus_slot;
import com.example.dell.findbus.helper.myAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.example.dell.findbus.R.drawable.user;
import static com.example.dell.findbus.R.id.tvEmail;
import static com.example.dell.findbus.R.id.userAddress;
import static java.security.AccessController.getContext;

public class bus_slots_manage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView userName, userContact, userLocation;
    private FirebaseAuth mAuth;

    myAdapter adapter;
    public RecyclerView recyclerView;

    static ArrayList<bus_slot> slotList;

    String flag;

    String collegeEmail;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_slots_manage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        Intent i = getIntent();

        slotList = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(bus_slots_manage.this,bus_slot_add.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
        collegeEmail = sp.getString("email",null);

        recyclerView=(RecyclerView)findViewById(R.id.rview);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent i = new Intent(bus_slots_manage.this,MapsActivity.class);
                        i.putExtra("driver_email",slotList.get(position).getDriver_email());
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Intent i = new Intent(bus_slots_manage.this,MapsActivity.class);
                        i.putExtra("driver_email",slotList.get(position).getDriver_email());
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                }));

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        userContact = (TextView) headerLayout.findViewById(R.id.userContact);
        userContact.setText(sp.getString("contact",null));
        userName = (TextView) headerLayout.findViewById(R.id.userName);
        userName.setText(sp.getString("name",null));
        userLocation = (TextView) headerLayout.findViewById(R.id.userAddress);
        userLocation.setText(sp.getString("location",null));
        navigationView.setNavigationItemSelectedListener(this);

        flag = i.getStringExtra("flag");
        if(flag.equals("1")){
            fab.setVisibility(View.GONE);
        }

        fetchSlots();
    }

    public void fetchSlots(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Slot");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    bus_slot b1 = snapshot.getValue(bus_slot.class);
                    if(b1.getCollegeId().equals(collegeEmail)){
                        slotList.add(b1);
                    }
                }

                adapter=new myAdapter(bus_slots_manage.this,slotList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(bus_slots_manage.this));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bus_slots_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sign_out) {
           mAuth.signOut();
            finish();
            startActivity(new Intent(bus_slots_manage.this,MainActivity1.class));
        }

        /*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
