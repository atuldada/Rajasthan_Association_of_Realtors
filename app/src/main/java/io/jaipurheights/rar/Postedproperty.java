package io.jaipurheights.rar;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class Postedproperty extends AppCompatActivity {
    TextView description,subdescription,category,area,price,name,city,phoneno;
    String phone,url;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postedproperty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        description=(TextView)findViewById(R.id.sub_description);
        subdescription=(TextView)findViewById(R.id.location);
        name=(TextView)findViewById(R.id.name);
        price=(TextView)findViewById(R.id.cost);
        area=(TextView)findViewById(R.id.area);
        city=(TextView)findViewById(R.id.city);
        phoneno=(TextView)findViewById(R.id.PHONE);
        image=(ImageView)findViewById(R.id.imageofproperty);
        Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("BitmapImage");
        image.setImageBitmap(bitmap);
        phone=(getIntent().getStringExtra("phone"));

        description.setText(getIntent().getStringExtra("description"));
        subdescription.setText(getIntent().getStringExtra("subdescription"));
        name.setText("IDENTITY: "+getIntent().getStringExtra("name"));
        price.setText("PRICE: "+getIntent().getStringExtra("price"));
        area.setText("AREA: "+getIntent().getStringExtra("area"));
        city.setText("CITY: "+getIntent().getStringExtra("city"));
        phoneno.setText("PHONE: "+phone);







    }



    }

