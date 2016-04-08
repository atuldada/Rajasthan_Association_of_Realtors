package io.jaipurheights.rar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class Propertyshown extends ActionBarActivity {
    private int SIGNATURE_ACTIVITY = 101;
    private String signaturePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propertyshown);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button addSignatureButton = (Button) findViewById(R.id.addSig);
        addSignatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Propertyshown.this, SignatureActivity.class);
                startActivityForResult(i, SIGNATURE_ACTIVITY);
            }
        });
        ImageView sign = (ImageView) findViewById(R.id.lesseSignatureView);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Propertyshown.this, SignatureActivity.class);
                startActivityForResult(i, SIGNATURE_ACTIVITY);
            }
        });

        Button sendButton = (Button) findViewById(R.id.lesseSubmit);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //    File imgFile = new  File(signaturePath);
                EditText name = (EditText) findViewById(R.id.lesseName);
                EditText phone = (EditText) findViewById(R.id.lessePhone);
                EditText email=(EditText) findViewById(R.id.email);
                EditText add=(EditText) findViewById(R.id.lesseAddress2);

                EditText budget=(EditText) findViewById(R.id.lesseBudget2);


                EditText budget2=(EditText) findViewById(R.id.lesseBudget3);
                EditText add2=(EditText) findViewById(R.id.lesseAddress3);

                EditText budget4=(EditText) findViewById(R.id.lesseBudget4);
                EditText add4=(EditText) findViewById(R.id.lesseAddress4);

                EditText budget5=(EditText) findViewById(R.id.lesseBudget5);
                EditText add5=(EditText) findViewById(R.id.lesseAddress5);

                String mob=name.getText().toString();
                String nam=name.getText().toString();

                if(true/*imgFile.exists()*/) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                    // set the type to 'email'
                    emailIntent.setType("vnd.android.cursor.dir/email");
                    String mail=email.getText().toString();
                    String to[] = {mail};
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                    // the attachment
                    ArrayList<Uri> uris = new ArrayList<Uri>();
                 //   uris.add(Uri.fromFile(imgFile));
                    try {
                        InputStream is = getAssets().open("tandc.txt");
                        byte[] buffer = new byte[is.available()];
                        is.read(buffer);
                        String xpath = Environment.getExternalStorageDirectory().toString();
                        File targetFile = new File(xpath + "/terms_and_conditions.html");
                        OutputStream os = new FileOutputStream(targetFile);
                        os.write(buffer);
                        is.close();
                        //flush OutputStream to write any buffered data to file
                        os.flush();
                        os.close();
                        targetFile = new File(xpath + "/terms_and_conditions.html"); //re read
                        uris.add(Uri.fromFile(targetFile));
                        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                        // the mail subject
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "RAR recieved your form");

                        String text = "Name: " + name.getText().toString();

                        text += "\n" + "Phone: " + phone.getText().toString();

                        text += "\n" + "Email Id: " + email.getText().toString();
                        text += "\n" + "Address: " + "                Rate: " ;

                        text += "\n\n" + add.getText().toString() + budget.getText().toString();
                        String a=add2.getText().toString() + budget2.getText().toString()+" ";
                        text += "\n\n" +a ;
                        a=add4.getText().toString() + budget4.getText().toString()+" ";
                        text += "\n\n" + a;
                         a=add5.getText().toString() + budget5.getText().toString()+" ";
                        text += "\n\n" + a;

                        emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, text);
                        emailIntent.putExtra(Intent.EXTRA_TEXT, text);
                        startActivity(Intent.createChooser(emailIntent, "Send email..."));

                    }

                    catch (IOException e) {

                    }
                }
              /*  else if(mob.length()>9)
                {
                    Toast.makeText(form.this, "Invalid Mobile No.", Toast.LENGTH_SHORT);
                }*/
                else {
                    Toast.makeText(Propertyshown.this, "Incomplete Form", Toast.LENGTH_SHORT);
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
