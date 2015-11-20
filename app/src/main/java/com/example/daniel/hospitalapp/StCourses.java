package com.example.daniel.hospitalapp;

import android.app.ProgressDialog;
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
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StCourses extends AppCompatActivity implements StViewAdapter.RecyclerClickListner{
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
        setContentView(R.layout.activity_st_courses);

        rv = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        new GetData().execute();
    }

    // RemoteDataTask AsyncTask
    private class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            pDialog = new ProgressDialog(StCourses.this);
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
                    dato2.getRelation("Student_Register").getQuery().findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            for (ParseObject pa : objects) {
                                //
                                ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("StudentRole");
                                query2.whereEqualTo("User_Id", ParseUser.getCurrentUser());
                                try {
                                    ob = query2.find();
                                    for ( ParseObject dato3 : ob) { //falta query para professor name
                                        if(pa.getObjectId().equals(dato3.getObjectId())){
                                            if (k == 0) {
                                                pclist = new ArrayList<StAvCourseList>(Arrays.asList(new StAvCourseList(dato2.get("Course_Name").toString(), dato2.get("Start_Date").toString(), dato2.get("End_Date").toString(),pa.get("Name").toString())));
                                                k++;
                                            } else {
                                                pclist.add(new StAvCourseList(dato2.get("Course_Name").toString(), dato2.get("Start_Date").toString(), dato2.get("End_Date").toString(),pa.get("Name").toString()));
                                            }
                                            keys.add(dato2.getObjectId());
                                        }
                                    }
                                    if(pclist!=null){
                                    viewAdapter= new StViewAdapter(StCourses.this,pclist);
                                    viewAdapter.setRecyclerClickListner(StCourses.this);
                                    rv.setAdapter(viewAdapter);}else{ Toast.makeText(StCourses.this, "No Hay Cursos Registrados", Toast.LENGTH_LONG).show();}
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }

                                //

                            }

                        }
                    });
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
        getMenuInflater().inflate(R.menu.menu_st_courses, menu);
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
    public void itemClick(View view, int position) {
        Log.d("Recyclerview", "Click position " + position);
    }
}
