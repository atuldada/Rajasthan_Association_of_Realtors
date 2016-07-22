package io.jaipurheights.rar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class lessor extends AppCompatActivity {
    String com;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        Button accept;
        accept=(Button)findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(lessor.this, form.class);
                i.putExtra("com", com);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        final CheckBox mCheckBox1 = (CheckBox) findViewById(R.id.onep);
        final CheckBox mCheckBox2 = (CheckBox) findViewById(R.id.twop);
        final CheckBox mCheckBox3 = (CheckBox) findViewById(R.id.threep);
        final CheckBox mCheckBox4 = (CheckBox) findViewById(R.id.fourp);


        mCheckBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCheckBox2.isChecked())
                    mCheckBox1.setChecked(false);
                mCheckBox3.setChecked(false);
                mCheckBox4.setChecked(false);
                com="15 days rental commisssion";

            }
        });

        mCheckBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCheckBox1.isChecked())
                    mCheckBox2.setChecked(false);
                mCheckBox3.setChecked(false);
                mCheckBox4.setChecked(false);
                com="1 month rental commisssion";
            }
        });
        mCheckBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCheckBox3.isChecked())
                    mCheckBox2.setChecked(false);
                mCheckBox1.setChecked(false);
                mCheckBox4.setChecked(false);
                com="3 month rental commisssion";
            }
        });
        mCheckBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCheckBox4.isChecked())
                    mCheckBox2.setChecked(false);
                mCheckBox1.setChecked(false);
                mCheckBox3.setChecked(false);

                com="6 month rental commisssion";
            }
        });


    }

}
