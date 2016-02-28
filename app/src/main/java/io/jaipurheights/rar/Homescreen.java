package io.jaipurheights.rar;

import android.content.Intent;
import android.os.Bundle;
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

import com.andexert.library.RippleView;

public class Homescreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
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
                Intent i=new Intent(Homescreen.this,Postproperty.class);
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
        getMenuInflater().inflate(R.menu.homescreen, menu);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
