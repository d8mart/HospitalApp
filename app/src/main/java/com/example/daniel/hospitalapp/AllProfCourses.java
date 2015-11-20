package com.example.daniel.hospitalapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllProfCourses extends AppCompatActivity implements ViewAdapter.RecyclerClickListner {
    private ProgressDialog pDialog;
    List<ParseObject> ob;
    private ArrayList keys;
    RecyclerView rv;
    private ViewAdapter viewAdapter;
    List<ProfCoursesList> pclist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_prof_courses);
         rv = (RecyclerView) findViewById(R.id.recycler);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        new GetData().execute();

    }

    @Override
    public void itemClick(View view, final int position) {
        //Log.d("Recyclerview", "Click position " + position);
        new AlertDialog.Builder(this)  //interaccion con el usuario
                .setMessage("Do you wish to see your students in this course?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(AllProfCourses.this, StudentsInCourse.class);
                        intent.putStringArrayListExtra("Ckeys",keys);
                        intent.putExtra("pos",position);
                        startActivity(intent);



                        //Toast.makeText(CourseRegister.this, "Curso Registrado", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    // RemoteDataTask AsyncTask
    private class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            pDialog = new ProgressDialog(AllProfCourses.this);
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
            keys = new ArrayList<String>();
            try {


                ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>(
                        "ProfessorRole");
                query1.whereEqualTo("User_Id", ParseUser.getCurrentUser());
                ob = query1.find();
                for (ParseObject dato : ob) {

                    ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>(
                            "Courses");
                    query2.whereEqualTo("Professor_Assigned", dato.getObjectId()); int k=0;
                    ob = query2.find();
                    for (ParseObject dato2 : ob) {

                        if(k==0){pclist = new ArrayList<ProfCoursesList>(Arrays.asList(new ProfCoursesList(dato2.get("Course_Name").toString(),dato2.get("Start_Date").toString(),dato2.get("End_Date").toString())));k++;}else{
                        pclist.add(new ProfCoursesList(dato2.get("Course_Name").toString(),dato2.get("Start_Date").toString(),dato2.get("End_Date").toString()));}
                        keys.add(dato2.getObjectId());
                    }
                }


            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            //ViewAdapter adapter=new ViewAdapter(AllProfCourses.this,pclist);
            if(pclist!=null){viewAdapter= new ViewAdapter(AllProfCourses.this,pclist);
            viewAdapter.setRecyclerClickListner(AllProfCourses.this);
            rv.setAdapter(viewAdapter);}else{ Toast.makeText(AllProfCourses.this, "You have not created a course yet", Toast.LENGTH_LONG).show();}
            pDialog.dismiss();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_prof_courses, menu);
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
