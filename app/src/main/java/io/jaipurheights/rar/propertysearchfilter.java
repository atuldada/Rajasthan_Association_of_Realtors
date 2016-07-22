package io.jaipurheights.rar;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.TableLayout;
import android.widget.Toast;
/*import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
*/
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.File;


public class propertysearchfilter extends ActionBarActivity implements PlaceSelectionListener {
    private int SIGNATURE_ACTIVITY = 101;
    private String signaturePath = null;
    String TAG="location";
    EditText location;
    String Formtype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        final Spinner proptype = (Spinner) findViewById(R.id.lessePropType);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(propertysearchfilter.this,
                R.array.propertytype, android.R.layout.simple_spinner_dropdown_item);
        proptype.setAdapter(adapter4);
        proptype.setSelection(adapter4.getCount() - 1);
        final Button filter=(Button) findViewById(R.id.lessePropertyAddButton);
        final Spinner category = (Spinner) findViewById(R.id.lessePropType);
        final Spinner type = (Spinner) findViewById(R.id.lessePropSubType);
        final Spinner city = (Spinner) findViewById(R.id.city);
         location=(EditText) findViewById(R.id.lesseLocations);
        final EditText budget=(EditText) findViewById(R.id.lesseBudget);
        final EditText area=(EditText) findViewById(R.id.lesseMeasureCount);
        final Spinner unit = (Spinner) findViewById(R.id.lesseMeasurementUnit);
        final Spinner name = (Spinner) findViewById(R.id.name);
        ArrayAdapter<CharSequence> adapterc2 = ArrayAdapter.createFromResource(propertysearchfilter.this,
                R.array.name, android.R.layout.simple_spinner_dropdown_item);
        name.setAdapter(adapterc2);
        name.setSelection(adapterc2.getCount() - 1);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        autocompleteFragment.setHint("Location");
        autocompleteFragment.setOnPlaceSelectedListener(this);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           //     final EditText description = (EditText) findViewById(R.id.c);
             //   final EditText name = (EditText) findViewById(R.id.company);
             //   final EditText phone = (EditText) findViewById(R.id.lessePhone);
                // EditText email=(EditText) findViewById(R.id.email);
                EditText add=(EditText) findViewById(R.id.lesseAddress);

                Intent intent = new Intent();

                intent.putExtra("description",category.getSelectedItem().toString());
                intent.putExtra("subdescription",type.getSelectedItem().toString());
                intent.putExtra("location",location.getText().toString());
                intent.putExtra("city",city.getSelectedItem().toString());
                intent.putExtra("price",budget.getText().toString());
                intent.putExtra("Area",area.getText().toString()+unit.getSelectedItem().toString());
                intent.putExtra("Formtype",Formtype);
                intent.putExtra("name", name.getSelectedItem().toString());
                setResult(RESULT_OK, intent);
              /*  Toast.makeText(getApplicationContext(),
                            "Comming Soon...",
                        Toast.LENGTH_LONG).show();*/
                finish();

            }
        });
        final Spinner lesseSubTypeSpinner = (Spinner) findViewById(R.id.lessePropSubType);


        proptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String msupplier = proptype.getSelectedItem().toString();


                Log.e("Selected item : ", msupplier);
                if (msupplier.equals("Residential property")) {
                    ArrayAdapter<CharSequence> adapterc = ArrayAdapter.createFromResource(propertysearchfilter.this,
                            R.array.residential, android.R.layout.simple_spinner_dropdown_item);
                    lesseSubTypeSpinner.setAdapter(adapterc);
                    lesseSubTypeSpinner.setSelection(adapterc.getCount() - 1);
                } else if ((msupplier.equals("Commercial Property"))) {
                    ArrayAdapter<CharSequence> adapterc = ArrayAdapter.createFromResource(propertysearchfilter.this,
                            R.array.commercial, android.R.layout.simple_spinner_dropdown_item);
                    lesseSubTypeSpinner.setAdapter(adapterc);
                    lesseSubTypeSpinner.setSelection(adapterc.getCount() - 1);
                } else if ((msupplier.equals("Industrial Property"))) {
                    ArrayAdapter<CharSequence> adapterc = ArrayAdapter.createFromResource(propertysearchfilter.this,
                            R.array.industrial, android.R.layout.simple_spinner_dropdown_item);
                    lesseSubTypeSpinner.setAdapter(adapterc);
                    lesseSubTypeSpinner.setSelection(adapterc.getCount() - 1);

                } else if (msupplier.equals("Agricultural Property")) {
                    ArrayAdapter<CharSequence> adapterc = ArrayAdapter.createFromResource(propertysearchfilter.this,
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
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(propertysearchfilter.this,
                R.array.property_measurements, android.R.layout.simple_spinner_dropdown_item);
        lesseMeasurementUnitSpinner.setAdapter(adapter1);
        lesseMeasurementUnitSpinner.setSelection(adapter1.getCount() - 1);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(propertysearchfilter.this,
                R.array.city, android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adapter3);
        city.setSelection(adapter3.getCount() - 1);

        final TableLayout tl = (TableLayout) findViewById(R.id.lessePropertiesTable);


        final CheckBox mCheckBox1 = (CheckBox) findViewById(R.id.onep);
        final CheckBox mCheckBox2 = (CheckBox) findViewById(R.id.twop);
        final CheckBox mCheckBox3 = (CheckBox) findViewById(R.id.threep);
        final CheckBox mCheckBox4 = (CheckBox) findViewById(R.id.fourp);

        mCheckBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCheckBox1.isChecked()) {
                    mCheckBox3.setChecked(false);
                    mCheckBox2.setChecked(false);
                    mCheckBox4.setChecked(false);
                    Formtype="For Sale";
                }
            }
        });

        mCheckBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBox2.isChecked()) {
                    mCheckBox1.setChecked(false);
                    mCheckBox3.setChecked(false);
                    mCheckBox4.setChecked(false);
                    Formtype="For Rent-Out";
                }
            }
        });

        mCheckBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBox3.isChecked()) {
                    mCheckBox1.setChecked(false);
                    mCheckBox2.setChecked(false);
                    mCheckBox4.setChecked(false);
                    Formtype="To Buy";
                }
            }
        });
        mCheckBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBox4.isChecked()) {
                    mCheckBox1.setChecked(false);
                    mCheckBox2.setChecked(false);
                    mCheckBox3.setChecked(false);
                    Formtype="To Rent-in";
                }
            }
        });





    }

/*    @Override
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

            }
        }
    }


}
