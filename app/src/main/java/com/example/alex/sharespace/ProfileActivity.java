package com.example.alex.sharespace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button logOutButton;

    private DatabaseReference databaseReference;

    private EditText editTextName, editTextPhone;
    private Button saveButton;

    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = firebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //if theres no login
            finish();
            startActivity((new Intent(this, LoginActivity.class)));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextName = (EditText) findViewById(R.id.name);
        editTextPhone = (EditText) findViewById(R.id.phoneNumber);
        saveButton = (Button) findViewById(R.id.save);

        //dropdown menu spinner
        Spinner spinner = (Spinner)findViewById(R.id.spinner);

        String[] items = new String[]{"Email", "Call", "Text"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Welcome " + user.getEmail());

        logOutButton = (Button) findViewById(R.id.logOutButton);

        logOutButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        // TODO: sets hints to user information


    }

    private void saveUserInformation(){
        String name = editTextName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        if (spinner.getSelectedItem() == null) {
            Toast.makeText(this, "Please choose contact preference", Toast.LENGTH_SHORT).show();
            return;

        }
        String preference = spinner.getSelectedItem().toString();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        String email = user.getEmail();

        UserInformation userInformation = new UserInformation(name, phone , email, preference);


        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this, "Information Saved" , Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onClick(View v) {
        if (v == logOutButton){
            firebaseAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));

        }
        if (v == saveButton){
            saveUserInformation();
        }
    }
}
