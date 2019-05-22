package com.example.mandooe;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;

import com.example.mandooe.adapter.NoticiesAdapter;
import com.example.mandooe.adapter.PoliciesAdapter;
import com.example.mandooe.model.Noticies_model;
import com.example.mandooe.model.Policies_model;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Notices_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button fab_notice;
    private RecyclerView recyclerView;
    FirebaseDatabase database;

    List<Noticies_model> noticies_modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        noticies_modelList = fill_with_data();

        recyclerView =  findViewById(R.id.recycle_noticies);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Notices_Activity.this));
        NoticiesAdapter adapter = new NoticiesAdapter(noticies_modelList,Notices_Activity.this);
        recyclerView.setAdapter(adapter);

        fab_notice = findViewById(R.id.fab_notice);
        fab_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnotices();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private List<Noticies_model> fill_with_data() {
        DatabaseReference mRef = database.getReference("Noticies");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Noticies_model noticies_model = dataSnapshot.getValue(Noticies_model.class);
                noticies_modelList.add(noticies_model);
                NoticiesAdapter adapter = new NoticiesAdapter(noticies_modelList,Notices_Activity.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return noticies_modelList;
    }
    private void addnotices() {
        Intent intent = new Intent(this,Add_notices1.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board_, menu);
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

        if (id == R.id.dashboard) {
            // Handle the camera action
            Intent intent = new Intent(this, DashBoard_Activity.class);
            startActivity(intent);

        } else if (id == R.id.grievances) {
            Intent intent = new Intent(this,Grievances_activity.class);
            startActivity(intent);

        } else if (id == R.id.notices) {


        } else if (id == R.id.referrals) {
            Intent intent = new Intent(this,Referrals_Activity.class);
            startActivity(intent);

        } else if (id == R.id.internaljob) {
            Intent intent = new Intent(this,Internal_job_posting_Activity.class);
            startActivity(intent);

        } else if (id == R.id.employeedetails) {
            Intent intent = new Intent(this,Employee_Activity.class);
            startActivity(intent);

        } else if (id == R.id.policies) {
            Intent intent = new Intent(this,Policies_Activity.class);
            startActivity(intent);

        } else if (id == R.id.leavform) {
            Intent intent = new Intent(this,Leave_form_Activity.class);
            startActivity(intent);

        } else if (id == R.id.appraisals) {
            Intent intent = new Intent(this,Appraisal_Activity.class);
            startActivity(intent);

        } else if (id == R.id.logouts){
//            Intent intent = new Intent(this, Notices_Activity.class);
//            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
