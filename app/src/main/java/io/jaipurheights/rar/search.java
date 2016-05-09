package io.jaipurheights.rar;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

public class search extends ListActivity
       implements SharedPreferences.OnSharedPreferenceChangeListener
    {
    private int SIGNATURE_ACTIVITY = 101;
    private String signaturePath = null;
    private static final int DIALOG_NEW_TASK = 1;
    private static final int DIALOG_PROGRESS = 2;
    static final String LOG_TAG = "search activity";
        static final int filter_result = 20;
    // Main data model object
    private static TasksModelsearch sTasks;
    private TaskAdapter mTaskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contentofsearch);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // Register to listen to the setting changes because replicators
        // uses information managed by shared preference.
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.registerOnSharedPreferenceChangeListener(this);


        // Protect creation of static variable.
        if (sTasks == null) {
            // Model needs to stay in existence for lifetime of app.
            this.sTasks = new TasksModelsearch(this.getApplicationContext());
        }

        // Register this activity as the listener to replication updates
        // while its active.
           this.sTasks.setReplicationListener(this);

        // Load the tasks from the model
        this.reloadTasksFromModel();

        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //   setSupportActionBar(toolbar);   // TODO: 19/2/16

        sTasks.startPullReplication();
        final Button filter=(Button) findViewById(R.id.search);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(search.this, propertysearchfilter.class);
                startActivityForResult(i, filter_result);

            }
        });
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("mypref",0);
        String username = sharedPreferences.getString("log"," ");
        if(username.equals(" ")||username.equals("false"))
        {

            startActivity(new Intent(search.this,LoginActivity.class));
            finish();
        }
        else
        {

        }


    }
    private void reloadTasksFromModel() {
        List<Task> tasks = this.sTasks.allTasks();
        this.mTaskAdapter = new TaskAdapter(this, tasks);
        this.setListAdapter(this.mTaskAdapter);
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        Log.d(LOG_TAG, "onSharedPreferenceChanged()");
        reloadReplicationSettings();
    }

    private void reloadReplicationSettings() {
        try {
          this.sTasks.reloadReplicationSettings();
        } catch (URISyntaxException e) {
            Log.e(LOG_TAG, "Unable to construct remote URI from configuration", e);
            Toast.makeText(getApplicationContext(),
                    R.string.replication_error,
                    Toast.LENGTH_LONG).show();
        }
    }
    void replicationComplete() {
        reloadTasksFromModel();
        Toast.makeText(getApplicationContext(),
                R.string.replication_completed,
                Toast.LENGTH_LONG).show();
        //  dismissDialog(DIALOG_PROGRESS);
    }

    /**
     * Called by TasksModel when it receives a replication error callback.
     * TasksModel takes care of calling this on the main thread.
     */
    void replicationError() {
        Log.i(LOG_TAG, "error()");
        reloadTasksFromModel();
        Toast.makeText(getApplicationContext(),
                R.string.replication_error,
                Toast.LENGTH_LONG).show();
        //   dismissDialog(DIALOG_PROGRESS);
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy()");
        super.onDestroy();


    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // Check which request we're responding to
            if (requestCode == filter_result) {
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {

                    String description=data.getStringExtra("description");
                    String subdescription=data.getStringExtra("subdescription");
                    String location=data.getStringExtra("location");
                    String city=data.getStringExtra("city");
                    String price=data.getStringExtra("price");
                    String Area=data.getStringExtra("Area");

                   String name=data.getStringExtra("name");
                    List<Task> tasks = this.sTasks.searchTasks(description,subdescription,location,city, price,Area);
                    this.mTaskAdapter = new TaskAdapter(this, tasks);
                    this.setListAdapter(this.mTaskAdapter);

                    // Do something name
                }
            }
        }
}
