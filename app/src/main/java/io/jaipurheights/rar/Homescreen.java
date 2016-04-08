package io.jaipurheights.rar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Response;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.ResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;

import com.andexert.library.RippleView;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

public class Homescreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ResponseListener {
    private MFPPush push=null;
    private static final String TAG = Homescreen.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        try {
            //initialize SDK with IBM Bluemix application ID and route
            //TODO: Please replace <APPLICATION_ROUTE> with a valid ApplicationRoute and <APPLICATION_ID> with a valid ApplicationId
            BMSClient.getInstance().initialize(this, "http://rar.mybluemix.net", "aefdcfbc-dafa-4fe3-9a58-1e8d03abe041");
        } catch (MalformedURLException mue) {

        }
        MFPPush.getInstance().initialize(getApplicationContext());
        push = MFPPush.getInstance();


        push.register(new MFPPushResponseListener<String>() {
            @Override
            public void onSuccess(String deviceId) {
                System.out.println("Registration successful");
            }

            @Override
            public void onFailure(MFPPushException ex) {
                ex.printStackTrace();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        RippleView rippleView,info,membenifit;
        rippleView=(RippleView)findViewById(R.id.more2);
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                Intent i = new Intent(Homescreen.this, Requirement.class);
                startActivity(i);
            }

        });
        info=(RippleView)findViewById(R.id.more);
        info.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                drawer.openDrawer(GravityCompat.START);
            }

        });
        membenifit=(RippleView)findViewById(R.id.more3);
        membenifit.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                Intent i = new Intent(Homescreen.this, Postproperty.class);
                startActivity(i);
            }

        });

        final Button Search=(Button) findViewById(R.id.search);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(Homescreen.this,search.class);
                startActivity(i);

            }
        });
       final int []imageArray={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
       final ImageView imageView = (ImageView)findViewById(R.id.ad);

        final Handler handler = new Handler();
        Runnable runnable = new Runnable()
        {
            int i=0;
            public void run()
            {
                imageView.setImageResource(imageArray[i]);
                i++;
                if(i>imageArray.length-1)
                {
                    i=0;
                }
                handler.postDelayed(this, 3000);  //for interval...
            }

        };
        handler.postDelayed(runnable, 1000); //for initial delay..

        TextView t=(TextView)findViewById(R.id.textView);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Intent i=new Intent(Homescreen.this,LoginActivity.class);
                    startActivity(i);
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

      //  getMenuInflater().inflate(R.menu.homescreen, menu);
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
    public void onSuccess(Response response) {

        Log.i(TAG, "Successfully pinged Bluemix!");
    }
    @Override
    public void onFailure(Response response, Throwable throwable, JSONObject jsonObject) {
        String errorMessage = "";

        if (response != null) {
            if (response.getStatus() == 404) {
                errorMessage += "Application Route not found at:\n" + BMSClient.getInstance().getBluemixAppRoute() +
                        "\nPlease verify your Application Route and rebuild the app.";
            } else {
                errorMessage += response.toString() + "\n";
            }
        }

        if (throwable != null) {
            if (throwable.getClass().equals(UnknownHostException.class)) {
                errorMessage = "Unable to access Bluemix host!\nPlease verify internet connectivity and try again.";
            } else {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                throwable.printStackTrace(pw);
                errorMessage += "THROWN" + sw.toString() + "\n";
            }
        }

        if (errorMessage.isEmpty())
            errorMessage = "Request Failed With Unknown Error.";


        Log.e(TAG, "Get request to Bluemix failed: " + errorMessage);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent i=new Intent(Homescreen.this,Flash.class);
            startActivity(i);
            finish();

        } else if (id == R.id.nav_gallery) {
            Intent i=new Intent(Homescreen.this,Info.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            Intent i=new Intent(Homescreen.this,Why.class);
            startActivity(i);

        } else if (id == R.id.nav_manage) {
            Intent i=new Intent(Homescreen.this,Grivences.class);
            startActivity(i);


        } else if (id == R.id.nav_share) {
            Intent i=new Intent(Homescreen.this,TndC.class);
            startActivity(i);


        } else if (id == R.id.nav_send) {
            Intent i=new Intent(Homescreen.this,lessor.class);
            startActivity(i);
        }
        else if (id == R.id.sellor) {
            Intent i=new Intent(Homescreen.this,checktandc.class);
            startActivity(i);
        }
        else if (id == R.id.buyer) {
            Intent i=new Intent(Homescreen.this,buyer.class);
            startActivity(i);
        }
        else if (id == R.id.shown) {
            Intent i=new Intent(Homescreen.this,Propertyshown.class);
            startActivity(i);
        }
        else if (id == R.id.membeship) {
            Intent i=new Intent(Homescreen.this,Membership.class);
            startActivity(i);
        }
        else if (id == R.id.register) {
            Intent i=new Intent(Homescreen.this,RegistrationActivity.class);
            startActivity(i);
        }
        else if (id == R.id.logout) {
            final   SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("mypref",0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("log","false");
            editor.commit();
            Toast.makeText(getApplicationContext(),
                   "Logged Out Successfully",
                    Toast.LENGTH_LONG).show();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
