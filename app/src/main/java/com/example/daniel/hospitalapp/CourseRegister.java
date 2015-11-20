package com.example.daniel.hospitalapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseRegister extends AppCompatActivity implements StViewAdapter.RecyclerClickListner{
    RecyclerView rv;
    private ProgressDialog pDialog;
    List<ParseObject> ob;
    private ArrayList keys;
    List<StAvCourseList> pclist;
    private StViewAdapter viewAdapter;
    int k;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_register);

        rv = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        new GetData().execute();
        Toast.makeText(CourseRegister.this, "Touch a course for register", Toast.LENGTH_LONG).show();
    }

    // RemoteDataTask AsyncTask
    private class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            pDialog = new ProgressDialog(CourseRegister.this);
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




                    ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>(
                            "Courses");
                    query2.whereEqualTo("Avaliable", true);
                    ob = query2.find(); k=0;
                    for (final ParseObject dato2 : ob) {

                        dato2.getRelation("Professor_Assigned").getQuery().findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                for (ParseObject pa : objects) {
                                    if (k == 0) {
                                        pclist = new ArrayList<StAvCourseList>(Arrays.asList(new StAvCourseList(dato2.get("Course_Name").toString(), dato2.get("Start_Date").toString(), dato2.get("End_Date").toString(),pa.get("Name").toString())));
                                        k++;
                                    } else {
                                        pclist.add(new StAvCourseList(dato2.get("Course_Name").toString(), dato2.get("Start_Date").toString(), dato2.get("End_Date").toString(),pa.get("Name").toString()));
                                    }
                                    keys.add(dato2.getObjectId());
                                }
                                viewAdapter= new StViewAdapter(CourseRegister.this,pclist);
                                viewAdapter.setRecyclerClickListner(CourseRegister.this);
                                rv.setAdapter(viewAdapter);
                            }
                        });

                        //




                    }



            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            //ViewAdapter adapter=new ViewAdapter(AllProfCourses.this,pclist);
          /*  if(pclist!=null){
            viewAdapter= new StViewAdapter(CourseRegister.this,pclist);
            viewAdapter.setRecyclerClickListner(CourseRegister.this);
            rv.setAdapter(viewAdapter);}*/
            pDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_register, menu);
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

    @Override
    public void itemClick(View view, final int position) {
        new AlertDialog.Builder(this)  //interaccion con el usuario
                .setMessage("Are you sure you want to regist in this course?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        ParseQuery<ParseObject> prQuery = ParseQuery.getQuery("StudentRole");
                        prQuery.whereEqualTo("User_Id", ParseUser.getCurrentUser());
                        prQuery.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> stobjects, ParseException e) {
                                for (final ParseObject ob : stobjects) {
                                    ParseQuery<ParseObject> prQuery = ParseQuery.getQuery("Courses");
                                    prQuery.getInBackground(keys.get(position).toString(), new GetCallback<ParseObject>() {//update
                                        @Override
                                        public void done(ParseObject object, ParseException e) {
                                            ParseRelation pcrelation = object.getRelation("Student_Register");
                                            pcrelation.add(ob);
                                            object.saveInBackground();
                                        }
                                    });
                                }
                            }
                        });

                        Toast.makeText(CourseRegister.this, "Success regist", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
