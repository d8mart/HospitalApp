package com.example.daniel.hospitalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

public class LogInView extends AppCompatActivity {
    EditText Ucode,Upass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_view);

         Ucode = (EditText) findViewById(R.id.editText);
         Upass = (EditText) findViewById(R.id.editText2);

    }

    public void SignIn(View view){

        ParseUser.logInInBackground(Ucode.getText().toString(), Upass.getText().toString(), new LogInCallback() {
            @Override
            public void done(final ParseUser user, ParseException e) {
                if (user != null) {

                 //   Toast.makeText(LogInView.this,ParseUser.getCurrentUser().getObjectId().toString(),Toast.LENGTH_LONG).show();

                    ParseObject pr = new ParseObject("ProfessorRole");
                    ParseQuery<ParseObject> prQuery = ParseQuery.getQuery("ProfessorRole");
                    prQuery.whereEqualTo("User_Id", ParseUser.getCurrentUser());
                    prQuery.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> professorList, ParseException e) {
                            if (e != null) {
                                // There was an error
                                Toast.makeText(LogInView.this,"found err",Toast.LENGTH_LONG).show();
                            } else {
                                // results have all the Posts the current user liked.
                                for(ParseObject pl : professorList){
                                    /*if(pl.getObjectId().equals(ParseUser.getCurrentUser().getObjectId())){}*/
                                    //Toast.makeText(LogInView.this,pl.getRelation("User_Id").getQuery().toString(),Toast.LENGTH_LONG).show();
                                    pl.getRelation("User_Id").getQuery().findInBackground(new FindCallback<ParseObject>() {
                                        @Override
                                        public void done(List<ParseObject> objects, ParseException e) {
                                            for(ParseObject ui : objects){
                                               // Toast.makeText(LogInView.this,ui.getObjectId().toString(),Toast.LENGTH_LONG).show();
                                               // Toast.makeText(LogInView.this,ParseUser.getCurrentUser().getObjectId().toString(),Toast.LENGTH_LONG).show();
                                                if(ui.getObjectId().equals(ParseUser.getCurrentUser().getObjectId())){
                                                    Toast.makeText(LogInView.this,"prof user found",Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(LogInView.this, ProfessorInterface.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        }
                                    });


                                }
                            }
                        }
                    });



                    ParseObject cr = new ParseObject("CoordinatorRole");
                    ParseQuery<ParseObject> crQuery = ParseQuery.getQuery("CoordinatorRole");
                    crQuery.whereEqualTo("User_Id", ParseUser.getCurrentUser());
                    crQuery.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> coordinatorList, ParseException e) {
                            if (e != null) {
                                // There was an error
                                Toast.makeText(LogInView.this,"found err",Toast.LENGTH_LONG).show();
                            } else {
                                // results have all the Posts the current user liked.
                                for(ParseObject cl : coordinatorList){
                                    /*if(pl.getObjectId().equals(ParseUser.getCurrentUser().getObjectId())){}*/
                                    //Toast.makeText(LogInView.this,pl.getRelation("User_Id").getQuery().toString(),Toast.LENGTH_LONG).show();
                                    cl.getRelation("User_Id").getQuery().findInBackground(new FindCallback<ParseObject>() {
                                        @Override
                                        public void done(List<ParseObject> objects, ParseException e) {
                                            for(ParseObject ui : objects){
                                                // Toast.makeText(LogInView.this,ui.getObjectId().toString(),Toast.LENGTH_LONG).show();
                                                // Toast.makeText(LogInView.this,ParseUser.getCurrentUser().getObjectId().toString(),Toast.LENGTH_LONG).show();
                                                if(ui.getObjectId().equals(ParseUser.getCurrentUser().getObjectId())){
                                                    Toast.makeText(LogInView.this,"coord user found",Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(LogInView.this, CoordinatorInterface.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        }
                                    });


                                }
                            }
                        }
                    });


                    ParseObject sr = new ParseObject("StudentRole");
                    ParseQuery<ParseObject> srQuery = ParseQuery.getQuery("StudentRole");
                    srQuery.whereEqualTo("User_Id", ParseUser.getCurrentUser());
                    srQuery.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> studentList, ParseException e) {
                            if (e != null) {
                                // There was an error
                                Toast.makeText(LogInView.this,"found err",Toast.LENGTH_LONG).show();
                            } else {
                                // results have all the Posts the current user liked.
                                for(ParseObject sl : studentList){
                                    /*if(pl.getObjectId().equals(ParseUser.getCurrentUser().getObjectId())){}*/
                                    //Toast.makeText(LogInView.this,pl.getRelation("User_Id").getQuery().toString(),Toast.LENGTH_LONG).show();
                                    sl.getRelation("User_Id").getQuery().findInBackground(new FindCallback<ParseObject>() {
                                        @Override
                                        public void done(List<ParseObject> objects, ParseException e) {
                                            for(ParseObject ui : objects){
                                                // Toast.makeText(LogInView.this,ui.getObjectId().toString(),Toast.LENGTH_LONG).show();
                                                // Toast.makeText(LogInView.this,ParseUser.getCurrentUser().getObjectId().toString(),Toast.LENGTH_LONG).show();
                                                if(ui.getObjectId().equals(ParseUser.getCurrentUser().getObjectId())){
                                                    Toast.makeText(LogInView.this,"student user found",Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(LogInView.this, StudentInterface.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        }
                                    });


                                }
                            }
                        }
                    });


                    //
                   /* Intent intent = new Intent(LogInView.this, ProfessorInterface.class);
                    startActivity(intent);*/
                    //Toast.makeText(LogInView.this,pr.getObjectId().toString(),Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LogInView.this,"error user not found",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void CreateAccount(View view){
        Intent intent = new Intent(LogInView.this, CreateAccount.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in_view, menu);
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
