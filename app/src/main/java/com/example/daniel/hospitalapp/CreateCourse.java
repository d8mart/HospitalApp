package com.example.daniel.hospitalapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

public class CreateCourse extends AppCompatActivity {
    EditText Cname,sd,ed,cr;
    RadioButton av;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        Cname=(EditText) findViewById(R.id.editText6);
        sd=(EditText) findViewById(R.id.editText7);
        ed=(EditText) findViewById(R.id.editText8);
        cr=(EditText) findViewById(R.id.editText9);
        av=(RadioButton) findViewById(R.id.radioButton);
    }

    public void CourseRegister(View view){
        if (isNetworkAvailable()){
            new SendData().execute();
        }
    }

    private class SendData extends AsyncTask<Void, Void, Void> {
        String courseName=Cname.getText().toString();
        String startDate=sd.getText().toString();
        String endDate=ed.getText().toString();
        String credit=cr.getText().toString();
        Boolean avaliable=av.isChecked();
        protected Void doInBackground(Void... params) {

            /* ParseObject courses = new ParseObject("Courses");

            courses.put("Course_Name", courseName);
            courses.put("Start_Date", startDate);
            courses.put("End_Date", endDate);
            courses.put("Credits", credit);
            courses.put("Avaliable", avaliable);*/

            ParseQuery<ParseObject> prQuery = ParseQuery.getQuery("ProfessorRole");
            prQuery.whereEqualTo("User_Id", ParseUser.getCurrentUser());

            prQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    ParseObject courses = new ParseObject("Courses");

                    courses.put("Course_Name", courseName);
                    courses.put("Start_Date", startDate);
                    courses.put("End_Date", endDate);
                    courses.put("Credits", credit);
                    courses.put("Avaliable", avaliable);
                    ParseRelation relation = courses.getRelation("Professor_Assigned");
                    if (e != null) {
                        // There was an error
                        Toast.makeText(CreateCourse.this, "found err", Toast.LENGTH_LONG).show();
                    } else {
                        for (ParseObject pl : objects) {

                            relation.add(pl);
                            ParseRelation pcrelation = pl.getRelation("Course_Id");
                            pcrelation.add(courses);
                            pl.saveInBackground();
                        }
                    }
                    courses.saveInBackground();
                    Toast.makeText(CreateCourse.this, "curso registrado con exito", Toast.LENGTH_LONG).show();
                }

            });

            return null;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_course, menu);
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
