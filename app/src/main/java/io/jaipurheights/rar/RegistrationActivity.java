    package io.jaipurheights.rar;

    import android.app.Activity;
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.Spinner;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

    public class RegistrationActivity extends Activity {

        TextView registerLoginButton ;
        Button register;
        EditText registername,registerphoneno,registeremail,registerpassword;
        String[] codes = { "India  -  +91", "United States of America  -  +1"};
        String name,email,password,phoneno;
        // Declaring the Integer Array with resourse Id's of Images for the Spinners
        int[] flags = { R.drawable.f094, R.drawable.f222};
        Spinner country;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registration);
         //   Spinner countrycode=(Spinner)findViewById(R.id.registerCountryCode);
            registername=(EditText)findViewById(R.id.registerName);
            registerphoneno=(EditText)findViewById(R.id.editText1);
            registerpassword=(EditText)findViewById(R.id.registerPassword);
            registeremail=(EditText)findViewById(R.id.registerEmail);


            register=(Button)findViewById(R.id.registerButton);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = registername.getText().toString();
                    email = registeremail.getText().toString();
              //      password = registerpassword.getText().toString();
                 //// TODO: 22/7/16 encryption access denied
                    password = "(Ask Admin to get password)";
                    phoneno = registerphoneno.getText().toString();
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("mypref",0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user_name", name);
                    editor.putString("user_mob", phoneno);
                    editor.putString("user_pan", "*$##$*");
                    editor.putString("user_email", email);
                    //editor.putString("code", code);
                    editor.commit();
                    Toast.makeText(RegistrationActivity.this,
                            "Registration Successful.Please contact Admin to get authorized password",
                            Toast.LENGTH_LONG).show();
                    BackgroundMail.newBuilder(RegistrationActivity.this)
                            .withUsername("developeratuldada@gmail.com")
                            .withPassword("atul  dada")
                            .withMailto(email)
                            .withSubject("Registration Successful")
                            .withBody("Hi "+name+"! Your mobile no "+phoneno+" is registered with RAR successfully.To login use email: "+email+" and password:"+password+". For any query you can reply to this email.")
                            .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                @Override
                                public void onSuccess() {
                                    //do some magic

                                }
                            })
                            .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                                @Override
                                public void onFail() {
                                    //do some magic
                                    Toast.makeText(RegistrationActivity.this,
                                            "Check Your Internet Connection",
                                            Toast.LENGTH_LONG).show();
                                }
                            })
                            .send();
                    finish();

                }
            });
            registerLoginButton=(TextView)findViewById(R.id.registerLoginButton);

            registerLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(RegistrationActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            });
     //       countrycode.setAdapter(new MyAdapter(RegistrationActivity.this, R.layout.custom, codes));


        }

        public class MyAdapter extends ArrayAdapter<String>{

            public MyAdapter(Context context, int textViewResourceId,   String[] objects) {
                super(context, textViewResourceId, objects);
            }

            @Override
            public View getDropDownView(int position, View convertView,ViewGroup parent) {
                return getCustomView(position, convertView, parent);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return getCustomView(position, convertView, parent);
            }

            public View getCustomView(int position, View convertView, ViewGroup parent) {

                LayoutInflater inflater=getLayoutInflater();
                View row=inflater.inflate(R.layout.custom, parent, false);

                    TextView label = (TextView) row.findViewById(R.id.txt);
                    label.setText(codes[position]);

                    ImageView flag_image = (ImageView) row.findViewById(R.id.img);
                    flag_image.setImageResource(flags[position]);


                return row;
            }

        }
    }
