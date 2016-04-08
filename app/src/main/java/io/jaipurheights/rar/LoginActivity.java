package io.jaipurheights.rar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView signup;
    EditText email,password;
    String semail,spassword,username,tpassword;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup=(TextView)findViewById(R.id.loginSingUpLink);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);


      final   SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("mypref", 0);
        username = sharedPreferences.getString("user_email", " ");
        tpassword = sharedPreferences.getString("user_pan"," ");
        login=(Button)findViewById(R.id.email_sign_in_button);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                semail = email.getText().toString();
                spassword = password.getText().toString();
                if(username.equals(" "))
                {

                    Toast.makeText(getApplicationContext(),
                            "You have not registered till now.",
                            Toast.LENGTH_LONG).show();

                }

                else
                {
                    if(tpassword.equals(spassword)&&username.equals(semail))
                    {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("log","true");
                        editor.commit();
                        Toast.makeText(LoginActivity.this,
                                "Logged in Successfully",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                    {Toast.makeText(LoginActivity.this,
                            "login failed",
                            Toast.LENGTH_LONG).show();

                    }
                }

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

