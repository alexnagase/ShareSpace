package com.example.alex.sharespace;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import static com.example.alex.sharespace.R.id.imageView2;

public class UploadActivity extends AppCompatActivity {

    EditText editTitle, editDescription, editLocation;
    ImageView imageView;
    Uri uri = null;
    Menu menu;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        firebaseAuth = firebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //if theres no login
            finish();
            startActivity((new Intent(this, LoginActivity.class)));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTitle = (EditText) findViewById(R.id.editTitle);
        editLocation = (EditText) findViewById(R.id.editLocation);
        editDescription = (EditText) findViewById(R.id.editDescription);
        imageView = (ImageView) findViewById(imageView2);

        imageView.setImageURI(uri);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,""), 2);

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.shareButton) {
            saveUserInformation();
        }
        return super.onOptionsItemSelected(item);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                uri = data.getData();
                imageView = (ImageView) findViewById(imageView2);
                imageView.setImageURI(uri);
            }

        }
    }
    private void saveUserInformation(){
        String title = editTitle.getText().toString().trim();
        String location = editLocation.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        Listing listing = new Listing(title, location , description);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).push().setValue(listing);

        Toast.makeText(this, "Space Shared!" , Toast.LENGTH_SHORT).show();

        //todo: add checks for conditions
    }
}
