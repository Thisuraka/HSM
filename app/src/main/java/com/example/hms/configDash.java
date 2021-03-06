package com.example.hms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class configDash extends AppCompatActivity {

    DatabaseReference dbRef;
    Button update, confirm, clear;
    String sessionID, source, test;
    Switch switch1, switch2, switch3, switch4, switch5, switch6;
    Integer tot = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_dash);

        confirm = findViewById(R.id.confDashBtn2);
            update = findViewById(R.id.confDashBtn1);
            clear = findViewById(R.id.confDashBtn3);

            switch1 = findViewById(R.id.switch2);
            switch2 = findViewById(R.id.switch2);
            switch3 = findViewById(R.id.switch3);
            switch4 = findViewById(R.id.switch4);
            switch5 = findViewById(R.id.switch5);
            switch6 = findViewById(R.id.switch6);

            sessionID = getIntent().getStringExtra("SESSION_ID");
            source = getIntent().getStringExtra("SOURCE");
        Log.i("sessionID", sessionID);
        Log.i("source", source);

            DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child(source).child(sessionID);
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    test = Objects.requireNonNull(dataSnapshot.child("Ref").getValue()).toString();
                    String[] str = test.split(",");
                    List<String> al;
                    al = Arrays.asList(str);

                    Log.i("al", String.valueOf(al));

                    for (int i = 0; i < al.size(); i++) {
//                        System.out.println(al.get(i));
                        String b = al.get(i);
                        switch (b) {
                            case "Item1":
                                switch1.setChecked(true);
                                tot = tot + 1;
                                break;
                            case "Item2":
                                switch2.setChecked(true);
                                tot = tot + 1;
                                break;
                            case "Item3":
                                switch3.setChecked(true);
                                tot = tot + 1;
                                break;
                            case "Item4":
                                switch4.setChecked(true);
                                tot = tot + 1;
                                break;
                            case "Item5":
                                switch5.setChecked(true);
                                tot = tot + 1;
                                break;
                            case "Item6":
                                switch6.setChecked(true);
                                tot = tot + 1;
                                break;
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Please Confirm", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //acts as the delete function
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child(source).child(sessionID);
                dbRef.removeValue();
                startActivity(new Intent(configDash.this, mainPage.class));
            }
        });

        //navigates to update function
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), updateMenu.class);
                intent.putExtra("SESSION_ID", sessionID);
                intent.putExtra("SOURCE", source);
                startActivity(intent);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), orderSuccess.class);
                intent.putExtra("TOT", tot);
                intent.putExtra("SOURCE", source);
                startActivity(intent);
            }
        });
    }
}