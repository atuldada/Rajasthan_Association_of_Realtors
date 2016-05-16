package io.jaipurheights.rar;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class Requirement extends ActionBarActivity implements PlaceSelectionListener {
    private int SIGNATURE_ACTIVITY = 101;
    private String signaturePath = null;
    EditText location;
    String TAG="location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        autocompleteFragment.setHint("Location");
        autocompleteFragment.setOnPlaceSelectedListener(this);
        final CheckBox mCheckBox1 = (CheckBox) findViewById(R.id.onep);
        final CheckBox mCheckBox2 = (CheckBox) findViewById(R.id.twop);


        mCheckBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCheckBox2.isChecked())
                    mCheckBox1.setChecked(false);

            }
        });

        mCheckBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBox1.isChecked())
                    mCheckBox2.setChecked(false);

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

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String mob = name.getText().toString();
                String nam = name.getText().toString();


                if (true) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                    // set the type to 'email'
                    emailIntent.setType("vnd.android.cursor.dir/email");
                    String mail = "atuldada.dada@gmail.com";
                    String to[] = {mail};
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                    // the attachment
                //    ArrayList<Uri> uris = new ArrayList<Uri>();
                //    uris.add(Uri.fromFile(imgFile));

                    {
                     /*   InputStream is = getAssets().open("lessor.txt");
                        byte[] buffer = new byte[is.available()];
                        is.read(buffer);
                        String xpath = Environment.getExternalStorageDirectory().toString();
                        File targetFile = new File(xpath + "/terms_and_conditions.txt");
                        OutputStream os = new FileOutputStream(targetFile);
                        os.write(buffer);
                        is.close();
                        //flush OutputStream to write any buffered data to file
                        os.flush();
                        os.close();

                        targetFile = new File(xpath + "/terms_and_conditions.txt"); //re read
                        uris.add(Uri.fromFile(targetFile));
                        */
                     //   emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                        // the mail subject
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
              /*  else if(mob.length()>9)
                {
                    Toast.makeText(form.this, "Invalid Mobile No.", Toast.LENGTH_SHORT);
                }*/
                else {
                    Toast.makeText(Requirement.this, "Incomplete Form", Toast.LENGTH_SHORT);
                }

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
