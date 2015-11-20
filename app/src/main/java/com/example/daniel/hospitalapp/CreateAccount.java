package com.example.daniel.hospitalapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;

public class CreateAccount extends AppCompatActivity {
    private static final int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    EditText Ucode,Upass,Uname;
    byte[] photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Ucode = (EditText) findViewById(R.id.editText4);
        Upass = (EditText) findViewById(R.id.editText5);
        Uname = (EditText) findViewById(R.id.editText3);
    }

    public void SignUp(View view){
        if (isNetworkAvailable()){
            new SendData().execute();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isNetworkAvaible = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isNetworkAvaible = true;
            Toast.makeText(this, "Network is available ", Toast.LENGTH_LONG)
                    .show();
        } else {
            Toast.makeText(this, "Network not available ", Toast.LENGTH_LONG)
                    .show();
        }
        return isNetworkAvaible;
    }

   public void GetPhoto(View view){
       // Create intent to Open Image applications like Gallery, Google Photos
       Intent galleryIntent = new Intent(Intent.ACTION_PICK,
               android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
       startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.imageView);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString); // Locate the image
                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                photo = stream.toByteArray();
            }

        }catch (Exception e) {
            Toast.makeText(this, "Something went wrong "+e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    private class SendData extends AsyncTask<Void, Void, Void> {
        String code=Ucode.getText().toString();String pass=Upass.getText().toString();
        String name=Uname.getText().toString();
        protected Void doInBackground(Void... params) {
            final ParseUser user = new ParseUser();
            user.setUsername(code);
            user.setPassword(pass);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        // Hooray! Let them use the app now.
                        ParseObject sr = new ParseObject("StudentRole");
                        sr.put("Name", name);
                        sr.put("Rol", "Student");
                        //
                        try {
                            // Create the ParseFile
                            ParseFile file = new ParseFile("androidbegin.png", photo);
                            // Upload the image into Parse Cloud
                            file.saveInBackground();
                            sr.put("Photo", file);
                            sr.saveInBackground();
                            Toast.makeText(CreateAccount.this, "Image Uploaded",
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            Toast.makeText(CreateAccount.this, ex.getMessage(), Toast.LENGTH_LONG)
                                    .show();
                        }
                        //
                        ParseRelation relation = sr.getRelation("User_Id");
                        relation.add(user);
                        sr.saveInBackground();
                        Toast.makeText(CreateAccount.this, "user signed up", Toast.LENGTH_LONG).show();
                    } else {
                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong
                        Toast.makeText(CreateAccount.this, "failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_account, menu);
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
}
