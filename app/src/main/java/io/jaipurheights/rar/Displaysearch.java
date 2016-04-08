package io.jaipurheights.rar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class Displaysearch extends AppCompatActivity {
TextView description,subdescription,category,area,price,name,city;
    String phone,url;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaysearch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        description=(TextView)findViewById(R.id.sub_description);
        subdescription=(TextView)findViewById(R.id.location);
        name=(TextView)findViewById(R.id.name);
        price=(TextView)findViewById(R.id.cost);
        area=(TextView)findViewById(R.id.area);
        city=(TextView)findViewById(R.id.city);
        image=(ImageView)findViewById(R.id.imageofproperty);

        phone=(getIntent().getStringExtra("phone"));
        url=(getIntent().getStringExtra("url"));
        description.setText(getIntent().getStringExtra("description"));
        subdescription.setText(getIntent().getStringExtra("subdescription"));
        name.setText("NAME: "+getIntent().getStringExtra("name"));
        price.setText("PRICE: "+getIntent().getStringExtra("price"));
        area.setText("AREA: "+getIntent().getStringExtra("area"));
        city.setText("CITY: "+getIntent().getStringExtra("city"));



        new DownloadImageTask((ImageView)findViewById(R.id.imageofproperty))
                 .execute(url);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Message "+getIntent().getStringExtra("name")+" ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "sms:" + phone));
                intent.putExtra("sms_body", " ");
               startActivity(intent);


            }
        });




    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
