package com.example.daniel.hospitalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfessorInterface extends AppCompatActivity {
    TextView tv,tv2;
    ImageView iv;
    private ProgressDialog pDialog;
    List<ParseObject> ob;
    private ArrayList values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_interface);
        iv=(ImageView) findViewById(R.id.imageView3);
        iv.setImageResource(R.drawable.medicina);

        tv=(TextView) findViewById(R.id.textView15);
        tv2=(TextView) findViewById(R.id.textView16);

        new GetData().execute();
    }

    public void AllCourses(View view){
        Intent intent = new Intent(ProfessorInterface.this, AllProfCourses.class);
        startActivity(intent);
    }

    public void CreateCourse(View view){
        Intent intent = new Intent(ProfessorInterface.this, CreateCourse.class);
        startActivity(intent);
    }


    // RemoteDataTask AsyncTask
    private class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            pDialog = new ProgressDialog(ProfessorInterface.this);
            // Set progressdialog title
            pDialog.setTitle("Cargando datos de Parse");
            // Set progressdialog message
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            // Show progressdialog
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            values = new ArrayList<String>();
            try {


                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "ProfessorRole");
                query.whereEqualTo("User_Id", ParseUser.getCurrentUser());

                ob = query.find();
                for (ParseObject dato : ob) {

                    values.add(dato.get("Name"));
                    values.add(dato.get("Rol"));
                    //values.add(dato.get("Photo"));
                }

            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            tv.setText(values.get(0).toString());

            tv2.setText(values.get(1).toString());

            pDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_professor_interface, menu);
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
