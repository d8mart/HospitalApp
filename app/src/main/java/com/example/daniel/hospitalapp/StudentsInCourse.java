package com.example.daniel.hospitalapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentsInCourse extends AppCompatActivity implements StInCAdapter.RecyclerClickListner {
    RecyclerView rv;
    private ProgressDialog pDialog;
    List<ParseObject> ob;
    private ArrayList keys;
    private ArrayList Ckeys;
    List<StInCList> pclist;
    private StInCAdapter viewAdapter;
    int k,pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_in_course);
        rv = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        Ckeys=getIntent().getExtras().getStringArrayList("Ckeys");
        pos=getIntent().getIntExtra("pos", 0);
        new GetData().execute();
    }

    // RemoteDataTask AsyncTask
    private class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            pDialog = new ProgressDialog(StudentsInCourse.this);
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
                query2.whereEqualTo("objectId", Ckeys.get(pos));
                ob = query2.find(); k=0;
                for (final ParseObject dato2 : ob) {

                    dato2.getRelation("Student_Register").getQuery().findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            for (final ParseObject pa : objects) {

                                ParseFile file = (ParseFile) pa.get("Photo");
                                file.getDataInBackground(new GetDataCallback() {
                                    @Override
                                    public void done(byte[] data, ParseException e) {
                                        if (k == 0) {
                                            pclist = new ArrayList<StInCList>(Arrays.asList(new StInCList(pa.get("Name").toString(), data)));
                                            k++;
                                        } else {
                                            pclist.add(new StInCList(pa.get("Name").toString(), data));
                                        }
                                        keys.add(dato2.getObjectId());
                                        if(pclist!=null){
                                            viewAdapter= new StInCAdapter(StudentsInCourse.this,pclist);
                                            viewAdapter.setRecyclerClickListner(StudentsInCourse.this);
                                            rv.setAdapter(viewAdapter);}else{ Toast.makeText(StudentsInCourse.this, "There is no student in this course", Toast.LENGTH_LONG).show();}
                                    }
                                });


                            }

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
        getMenuInflater().inflate(R.menu.menu_students_in_course, menu);
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

    }
}
