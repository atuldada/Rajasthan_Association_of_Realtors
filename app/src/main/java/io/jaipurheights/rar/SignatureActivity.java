package io.jaipurheights.rar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.kyanogen.signatureview.SignatureView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


public class SignatureActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        final SignatureView signatureView = (SignatureView) findViewById(R.id.signature_view);

        Button clearButton = (Button) findViewById(R.id.sigClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });

        Button saveButton = (Button) findViewById(R.id.sigSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bitmap bitmap = signatureView.getSignatureBitmap();
                    String path = Environment.getExternalStorageDirectory().toString();
                    OutputStream fOut = null;
                    String name = UUID.randomUUID().toString();
                    File file = new File(path, name + ".jpg"); // the File to save to
                    fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                    fOut.flush();
                    fOut.close(); // do not forget to close the stream

                    Intent intent = new Intent();
                    intent.putExtra("SIGNATURE_PATH", file.getAbsolutePath());
                    setResult(RESULT_OK, intent);
                } catch (FileNotFoundException e) {
                    setResult(RESULT_CANCELED);
                } catch (IOException e) {
                    setResult(RESULT_CANCELED);
                }
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signature, menu);
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
