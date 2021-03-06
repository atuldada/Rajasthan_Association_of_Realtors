package io.jaipurheights.rar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;


public class Requirement extends ActionBarActivity implements SharedPreferences.OnSharedPreferenceChangeListener , PlaceSelectionListener {

    String path=null;
    ImageView img,img1;
    int column_index;
    Intent intent=null;
    EditText location;
    // Declare our Views, so we can access them later
    String logo,imagePath,Logo;
    Cursor cursor;
    //YOU CAN EDIT THIS TO WHATEVER YOU WANT
    private static final int SELECT_PICTURE = 1;
    String selectedImagePath;
    //ADDED
    String filemanagerstring;
    private int SIGNATURE_ACTIVITY = 101;
    private String signaturePath = null;
    static final String LOG_TAG = "TodoActivity";

    private static final int DIALOG_NEW_TASK = 1;
    private static final int DIALOG_PROGRESS = 2;
    ProgressBar viewProgressBar;
    static final String SETTINGS_CLOUDANT_USER = "pref_key_username";
    static final String SETTINGS_CLOUDANT_DB = "pref_key_dbname";
    static final String SETTINGS_CLOUDANT_API_KEY = "pref_key_api_key";
    static final String SETTINGS_CLOUDANT_API_SECRET = "pref_key_api_password";
    String TAG="location";
    String Formtype;
    // Main data model object
    private static TasksModel sTasks;
    private TaskAdapter mTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // Register to listen to the setting changes because replicators
        // uses information managed by shared preference.
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.registerOnSharedPreferenceChangeListener(this);



        // Protect creation of static variable.
        if (sTasks == null) {
            // Model needs to stay in existence for lifetime of app.
            this.sTasks = new TasksModel(this.getApplicationContext());
        }

        // Register this activity as the listener to replication updates
        // while its active.
        this.sTasks.setReplicationListener(this);

        // Load the tasks from the model
        this.reloadTasksFromModel();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Load default settings when we're first created.


        final CheckBox mCheckBox1 = (CheckBox) findViewById(R.id.onep);
        final CheckBox mCheckBox2 = (CheckBox) findViewById(R.id.twop);


        mCheckBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCheckBox2.isChecked())
                {      mCheckBox1.setChecked(false);
                    Formtype="To Rent-in";}

            }
        });

        mCheckBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBox1.isChecked())
                {  mCheckBox2.setChecked(false);
                    Formtype="To Buy";}
            }
        });

    /*    Button post=(Button)findViewById(R.id.lesseSubmit);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getApplicationContext(),
                        "Requirement Posted",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
        */

        final Spinner proptype = (Spinner) findViewById(R.id.lessePropType);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(Requirement.this,
                R.array.propertytype, android.R.layout.simple_spinner_dropdown_item);
        proptype.setAdapter(adapter4);
        proptype.setSelection(adapter4.getCount() - 1);
        final Spinner lesseSubTypeSpinner = (Spinner) findViewById(R.id.lessePropSubType);


        proptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String msupplier = proptype.getSelectedItem().toString();


                Log.e("Selected item : ", msupplier);
                if (msupplier.equals("Residential property")) {
                    ArrayAdapter<CharSequence> adapterc = ArrayAdapter.createFromResource(Requirement.this,
                            R.array.residential, android.R.layout.simple_spinner_dropdown_item);
                    lesseSubTypeSpinner.setAdapter(adapterc);
                    lesseSubTypeSpinner.setSelection(adapterc.getCount() - 1);
                } else if ((msupplier.equals("Commercial Property"))) {
                    ArrayAdapter<CharSequence> adapterc = ArrayAdapter.createFromResource(Requirement.this,
                            R.array.commercial, android.R.layout.simple_spinner_dropdown_item);
                    lesseSubTypeSpinner.setAdapter(adapterc);
                    lesseSubTypeSpinner.setSelection(adapterc.getCount() - 1);
                } else if ((msupplier.equals("Industrial Property"))) {
                    ArrayAdapter<CharSequence> adapterc = ArrayAdapter.createFromResource(Requirement.this,
                            R.array.industrial, android.R.layout.simple_spinner_dropdown_item);
                    lesseSubTypeSpinner.setAdapter(adapterc);
                    lesseSubTypeSpinner.setSelection(adapterc.getCount() - 1);

                } else if (msupplier.equals("Agricultural Property")) {
                    ArrayAdapter<CharSequence> adapterc = ArrayAdapter.createFromResource(Requirement.this,
                            R.array.agriculture, android.R.layout.simple_spinner_dropdown_item);
                    lesseSubTypeSpinner.setAdapter(adapterc);
                    lesseSubTypeSpinner.setSelection(adapterc.getCount() - 1);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        Spinner name2 = (Spinner) findViewById(R.id.name);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(Requirement.this,
                R.array.name, android.R.layout.simple_spinner_dropdown_item);
        name2.setAdapter(adapter2);
        name2.setSelection(adapter2.getCount() - 1);
        Spinner lesseMeasurementUnitSpinner = (Spinner) findViewById(R.id.lesseMeasurementUnit);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(Requirement.this,
                R.array.property_measurements, android.R.layout.simple_spinner_dropdown_item);
        lesseMeasurementUnitSpinner.setAdapter(adapter1);
        lesseMeasurementUnitSpinner.setSelection(adapter1.getCount() - 1);
        final   Spinner city = (Spinner) findViewById(R.id.city);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(Requirement.this,
                R.array.city, android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adapter3);
        city.setSelection(adapter3.getCount() - 1);



        final    Button sendButton = (Button) findViewById(R.id.lesseSubmit);
        final    EditText name = (EditText) findViewById(R.id.lesseName);
        final    EditText phone = (EditText) findViewById(R.id.lessePhone);
        //    EditText email = (EditText) findViewById(R.id.email);
        //    EditText add = (EditText) findViewById(R.id.lesseAddress);
        final    Spinner category = (Spinner) findViewById(R.id.lessePropType);
        final    Spinner type = (Spinner) findViewById(R.id.lessePropSubType);

        location = (EditText) findViewById(R.id.lesseLocations);
        final    EditText budget = (EditText) findViewById(R.id.lesseBudget);
        final    EditText area = (EditText) findViewById(R.id.lesseMeasureCount);
        final    Spinner unit = (Spinner) findViewById(R.id.lesseMeasurementUnit);
        final    Spinner name3 = (Spinner) findViewById(R.id.name);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        autocompleteFragment.setOnPlaceSelectedListener(this);
        autocompleteFragment.setHint("Location");
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (!(path.isEmpty()) && path != null) {
                        System.out.println("::::::ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooocheck report");

                        File f = new File(path);
                        String filename = f.getName();
                        createNewTask(category.getSelectedItem().toString(), name.getText().toString(), phone.getText().toString(), type.getSelectedItem().toString(), city.getSelectedItem().toString(), location.getText().toString(), budget.getText().toString(), area.getText().toString() + unit.getSelectedItem().toString(), filename,Formtype,name3.getSelectedItem().toString());

                        sTasks.startPushReplication();
                        Toast.makeText(getApplicationContext(),
                                "uploading please wait...",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent( getApplicationContext(),Postedproperty.class);
                        intent.putExtra("phone",phone.getText().toString());
                        intent.putExtra("description",category.getSelectedItem().toString());
                        intent.putExtra("subdescription", type.getSelectedItem().toString());
                        intent.putExtra("name", name3.getSelectedItem().toString());
                        intent.putExtra("price",budget.getText().toString());
                        intent.putExtra("area", area.getText().toString() + unit.getSelectedItem().toString());
                        intent.putExtra("city", location.getText().toString());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        v.getContext().startActivity(intent);
                        finish();

                    } else if (path.isEmpty()) {

                    }

                }
                catch (NullPointerException e)
                {
                    System.out.println("::::::>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.check report");
                    createNewTask(category.getSelectedItem().toString(), name3.getSelectedItem().toString(), phone.getText().toString(), type.getSelectedItem().toString(), city.getSelectedItem().toString(), location.getText().toString(), budget.getText().toString(), area.getText().toString() + unit.getSelectedItem().toString(), "",Formtype,name3.getSelectedItem().toString());

                    sTasks.startPushReplication();
                    Toast.makeText(getApplicationContext(),
                            "uploading please wait...",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent( getApplicationContext(),Postedproperty.class);
                    intent.putExtra("phone",phone.getText().toString());
                    intent.putExtra("description",category.getSelectedItem().toString());
                    intent.putExtra("subdescription", type.getSelectedItem().toString());
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("price", budget.getText().toString());
                    intent.putExtra("area", area.getText().toString() + unit.getSelectedItem().toString());
                    intent.putExtra("city",  location.getText().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                    finish();

                }

/*
                String mob = name.getText().toString();
                String nam = name.getText().toString();


                if (true) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                    // set the type to 'email'
                    emailIntent.setType("vnd.android.cursor.dir/email");
                    String mail = "atuldada.dada@gmail.com";
                    String to[] = {mail};
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, to);

                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "RAR Requirement form");

                        String text = "Name: " + name.getText().toString();
                        text += "\n" + "Phone: " + phone.getText().toString();


                        text += "\n" + "Category: " + category.getSelectedItem().toString();
                        text += "\n" + "Type: " + type.getSelectedItem().toString();
                        text += "\n" + "City: " + city.getSelectedItem().toString();
                        text += "\n" + "Location " + location.getText().toString();
                        text += "\n" + "Budget: " + budget.getText().toString();
                        text += "\n" + "Area: " + area.getText().toString();
                        text += "\n" + "Unit: " + unit.getSelectedItem().toString();


                        emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, text);
                        emailIntent.putExtra(Intent.EXTRA_TEXT, text);

                        startActivity(Intent.createChooser(emailIntent, "Send email..."));

                    }
                }

                else {
                    Toast.makeText(Requirement.this, "Incomplete Form", Toast.LENGTH_SHORT);
                }*/

            }
        });




    }

    /*  @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          // Inflate the menu; this adds items to the action bar if it is present.
          getMenuInflater().inflate(R.menu.menu_lesse, menu);
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
  */
    public void onPlaceSelected(Place place) {
        Log.i(TAG, "Place Selected: " + place.getName());

        // Format the returned place's details and display them in the TextView.
        location.setText((place.getAddress()).toString());

        CharSequence attributions = place.getAttributions();
       /* if (!TextUtils.isEmpty(attributions)) {
            mPlaceAttribution.setText(" ");
        } else {
            mPlaceAttribution.setText("");
        }
        */
    }

    /**
     * Callback invoked when PlaceAutocompleteFragment encounters an error.
     */
    private void reloadTasksFromModel() {
        List<Task> tasks = this.sTasks.allTasks();
        this.mTaskAdapter = new TaskAdapter(this, tasks);

    }
    private void createNewTask(String desc,String name,String phone ,String subdescription,String city,String location ,String price,String area,String imagename,String Formtype,String identity) {
        Task t = new Task(desc,name,phone,subdescription,city,location,price,area,imagename,Formtype,identity);
        sTasks.createDocument(t, path);
        reloadTasksFromModel();
    }


    void replicationComplete() {
        //   reloadTasksFromModel();

        Toast.makeText(getApplicationContext(),
                "Posted Successfully",
                Toast.LENGTH_LONG).show();

        // dismissDialog(DIALOG_PROGRESS);

    }


    /**
     * Called by TasksModel when it receives a replication error callback.
     * TasksModel takes care of calling this on the main thread.
     */
    void replicationError() {
        Log.i(LOG_TAG, "error()");
        //   reloadTasksFromModel();

        Toast.makeText(getApplicationContext(),
                "Unable To Connect To Internet",
                Toast.LENGTH_LONG).show();
        //  dismissDialog(DIALOG_PROGRESS);

    }
    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        switch (id) {
            case DIALOG_NEW_TASK:

            case DIALOG_PROGRESS:
                return createProgressDialog();
            default:
                throw new RuntimeException("No dialog defined for id: " + id);
        }
    }

    public Dialog createProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View v = this.getLayoutInflater().inflate(R.layout.dialog_loading, null);

        DialogInterface.OnClickListener negativeClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopReplication();
            }
        };

        DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Toast.makeText(getApplicationContext(),
                            R.string.replication_running, Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        };

        builder.setView(v).setNegativeButton("Stop", negativeClick).setOnKeyListener(keyListener);

        return builder.create();
    }
    void stopReplication() {
        sTasks.stopAllReplications();
        this.dismissDialog(DIALOG_PROGRESS);
        mTaskAdapter.notifyDataSetChanged();
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

        }
    }
    public void onError(Status status) {
        Log.e(TAG, "onError: Status = " + status.toString());

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SIGNATURE_ACTIVITY) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String path = data.getStringExtra("SIGNATURE_PATH");
                signaturePath = path;
                File imgFile = new  File(path);
                if(imgFile.exists()) {
                    ImageView signatureView = (ImageView) findViewById(R.id.lesseSignatureView);
                    signatureView.setImageURI(Uri.fromFile(imgFile));
                }
                //do something
            } // TODO: implement else
        }
    }


}
