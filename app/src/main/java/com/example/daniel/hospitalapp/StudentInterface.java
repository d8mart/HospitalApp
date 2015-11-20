package com.example.daniel.hospitalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class StudentInterface extends AppCompatActivity {
    private ProgressDialog pDialog;
    private ListView listView;
    TextView tv,tv2;
    ImageView iv;
    List<ParseObject> ob;
    private ArrayList values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_interface);

        tv=(TextView) findViewById(R.id.textView8);
        tv2=(TextView) findViewById(R.id.textView10);
        iv=(ImageView) findViewById(R.id.imageView2);

        new GetData().execute();
    }

    public void LogOut(View view){
        ParseUser.getCurrentUser().logOut();
        Intent intent = new Intent(StudentInterface.this, MainActivity.class);
        startActivity(intent);
    }

    public void StCourseRegister(View view){
        Intent intent = new Intent(StudentInterface.this, CourseRegister.class);
        startActivity(intent);
    }

    public void MyStCourses(View view){
        Intent intent = new Intent(StudentInterface.this, StCourses.class);
        startActivity(intent);
    }


    // RemoteDataTask AsyncTask
    private class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            pDialog = new ProgressDialog(StudentInterface.this);
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
                        "StudentRole");
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
            // Locate the listview in listview_main.xml
            // Pass the results into ListViewAdapter.java

           /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(StudentInterface.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, values);

            listView.setAdapter(adapter);*/

            tv.setText(values.get(0).toString());

            tv2.setText(values.get(1).toString());

           // iv.setImageBitmap(BitmapFactory.decodeFile(values.get(2).toString()));

            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "StudentRole");

            query.whereEqualTo("User_Id", ParseUser.getCurrentUser());
            query.findInBackground(new FindCallback<ParseObject>() {


                @Override
                public void done(List<ParseObject> studentList, com.parse.ParseException e) {
                    //ParseObject parseObject = new ParseObject("StudentRole");
                    for (ParseObject sl : studentList) {
                        ParseFile file = (ParseFile) sl.get("Photo");
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, com.parse.ParseException e) {
                                if (e == null) {

                                    iv.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));

                                } else {
                                    Toast.makeText(StudentInterface.this, "error", Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                        });
                    }
                  }
                }

                );


                // Close the progressdialog
                pDialog.dismiss();
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_interface, menu);
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
