package io.jaipurheights.rar;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class LesseActivity extends ActionBarActivity {
    private int SIGNATURE_ACTIVITY = 101;
    private String signaturePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nextform);
        final Spinner proptype = (Spinner) findViewById(R.id.lessePropType);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(LesseActivity.this,
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
                    ArrayAdapter<CharSequence> adapterc = ArrayAdapter.createFromResource(LesseActivity.this,
                            R.array.residential, android.R.layout.simple_spinner_dropdown_item);
                    lesseSubTypeSpinner.setAdapter(adapterc);
                    lesseSubTypeSpinner.setSelection(adapterc.getCount() - 1);
                }
                else if((msupplier.equals("Commercial Property")))
                {ArrayAdapter<CharSequence> adapterc = ArrayAdapter.createFromResource(LesseActivity.this,
                        R.array.commercial, android.R.layout.simple_spinner_dropdown_item);
                    lesseSubTypeSpinner.setAdapter(adapterc);
                    lesseSubTypeSpinner.setSelection(adapterc.getCount() - 1);}
                else if ((msupplier.equals("Industrial Property"))){
                    ArrayAdapter<CharSequence> adapterc = ArrayAdapter.createFromResource(LesseActivity.this,
                            R.array.industrial, android.R.layout.simple_spinner_dropdown_item);
                    lesseSubTypeSpinner.setAdapter(adapterc);
                    lesseSubTypeSpinner.setSelection(adapterc.getCount() - 1);

                }
                else if (msupplier.equals("Agricultural Property"))
                {
                    ArrayAdapter<CharSequence> adapterc = ArrayAdapter.createFromResource(LesseActivity.this,
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
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(LesseActivity.this,
                R.array.property_measurements, android.R.layout.simple_spinner_dropdown_item);
        lesseMeasurementUnitSpinner.setAdapter(adapter1);
        lesseMeasurementUnitSpinner.setSelection(adapter1.getCount() - 1);
        Spinner city = (Spinner) findViewById(R.id.city);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(LesseActivity.this,
                R.array.city, android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adapter3);
        city.setSelection(adapter3.getCount() - 1);



        Button addSignatureButton = (Button) findViewById(R.id.addSig);
        addSignatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LesseActivity.this, SignatureActivity.class);
                startActivityForResult(i, SIGNATURE_ACTIVITY);
            }
        });
        ImageView sign = (ImageView) findViewById(R.id.lesseSignatureView);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LesseActivity.this, SignatureActivity.class);
                startActivityForResult(i, SIGNATURE_ACTIVITY);
            }
        });
        Button sendButton = (Button) findViewById(R.id.lesseSubmit);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email=(EditText) findViewById(R.id.email);
                EditText add=(EditText) findViewById(R.id.lesseAddress);
                Spinner category = (Spinner) findViewById(R.id.lessePropType);
                Spinner type = (Spinner) findViewById(R.id.lessePropSubType);
                Spinner city = (Spinner) findViewById(R.id.city);
                EditText location=(EditText) findViewById(R.id.lesseLocations);
                EditText budget=(EditText) findViewById(R.id.lesseBudget);
                EditText area=(EditText) findViewById(R.id.lesseMeasureCount);
                Spinner unit = (Spinner) findViewById(R.id.lesseMeasurementUnit);
                File imgFile = new  File(signaturePath);
                if(imgFile.exists()) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                    // set the type to 'email'
                    emailIntent.setType("vnd.android.cursor.dir/email");
                    String mail=email.getText().toString();
                    String to[] = {mail};

                    emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                    // the attachment
                    ArrayList<Uri> uris = new ArrayList<Uri>();
                    uris.add(Uri.fromFile(imgFile));
                    try {
                        InputStream is = getAssets().open("tandc.txt");
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
                        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                        // the mail subject
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                        EditText name = (EditText) findViewById(R.id.lesseName);
                        String text = "Name: " + name.getText().toString();
                        EditText phone = (EditText) findViewById(R.id.lessePhone);
                        text += "\n" + "Phone: " + phone.getText().toString();
                        text += "\n" + "Email Id: " + email.getText().toString();
                        text += "\n" + "Address: " + add.getText().toString();
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    @Override
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
