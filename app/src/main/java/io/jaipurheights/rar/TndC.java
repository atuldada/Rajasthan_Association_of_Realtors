package io.jaipurheights.rar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class TndC extends AppCompatActivity {
String com;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnd_c);
        Button accept;
        accept=(Button)findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TndC.this, LesseActivity.class);
                i.putExtra("com", com);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        final CheckBox mCheckBox1 = (CheckBox) findViewById(R.id.onep);
        final CheckBox mCheckBox2 = (CheckBox) findViewById(R.id.twop);


        mCheckBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCheckBox2.isChecked())
                    mCheckBox1.setChecked(false);
                com="15 days rental commisssion";

            }
        });

        mCheckBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCheckBox1.isChecked())
                    mCheckBox2.setChecked(false);
                com="1 month rental commisssion";
            }
        });


    }

}
