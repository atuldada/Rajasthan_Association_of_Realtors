package io.jaipurheights.rar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class TndC extends AppCompatActivity {
String com;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnd_c);
        Button accept;
        accept=(Button)findViewById(R.id.accept);
        final CheckBox mCheckBox1 = (CheckBox) findViewById(R.id.onep);
        final CheckBox mCheckBox2 = (CheckBox) findViewById(R.id.twop);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mCheckBox1.isChecked()&&!mCheckBox2.isChecked())
                {  Toast.makeText(TndC.this,
                        "Please select a option",
                        Toast.LENGTH_LONG).show();}

                else

                {
                    Intent i = new Intent(TndC.this, LesseActivity.class);
                    i.putExtra("com", com);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }
            }
        });



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
