package com.example.bdproperties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseAuth loginAuth;
    TextView emailViewText;
    FirebaseUser currentUser;
    Menu menu1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerLayout = navigationView.getHeaderView(0);
        emailViewText =headerLayout.findViewById(R.id.idEmail);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.ownerRegistrationForm, R.id.saleRegistrationFrom,R.id.uploadActivity,R.id.loginFragment)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

            currentUser =  FirebaseAuth.getInstance().getCurrentUser();
            updateUI(currentUser);



            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    if (item.getItemId()==R.id.saleRegistrationFromMenu){
                        if (currentUser!=null){
                            NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                            navController.navigate(R.id.saleRegistrationFrom);
                        }else{
                            NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                            navController.navigate(R.id.loginFragment);
                        }
                    }
                    NavigationUI.onNavDestinationSelected(item,navController);
                    //This is for closing the drawer after acting on it
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
            });
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {MenuItem register = menu.findItem(R.id.action_settings);
//        if(currentUser!=null)
//        {
//            register.setVisible(true);
//        }
//        else
//        {
//            register.setVisible(false);
//        }
//        return true;
//    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser!= null) {
            emailViewText.setText(currentUser.getEmail());
            Toast.makeText(this, ""+currentUser.getEmail(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Not Loging", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


            if (item.getItemId() == R.id.action_settings) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = getIntent();
                finish();
                startActivity(intent);

                Toast.makeText(this, "Selection", Toast.LENGTH_SHORT).show();
                return true;
            }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem itemSearch = menu.findItem(R.id.action_settings);
        if (currentUser!=null)
        {
            itemSearch.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}