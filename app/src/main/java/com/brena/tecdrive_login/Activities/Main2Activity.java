package com.brena.tecdrive_login.Activities;

import android.annotation.SuppressLint;


import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brena.tecdrive_login.Fragments.HomeFragment;
import com.brena.tecdrive_login.Fragments.PerfilFragment;
import com.brena.tecdrive_login.Interfaces.JsonPostsApi;
import com.brena.tecdrive_login.Models.Posts;
import com.brena.tecdrive_login.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Main2Activity extends AppCompatActivity {
    //DECLARANDO COMPONENTES
    private GoogleSignInClient mGoogleSignInClient;
    private MenuView.ItemView sign_Out;
    private TextView fullNameText;
    private TextView email;
    private ImageView photoProfile;


    //DECLARANDO VARIABLES
    private String personEmail;
    private String personGivenName;
    private String personFamilyName;
    private Uri personPhoto;
    private String firstWord;
    private String fullName;


    //DECLARANDO COMPONENTES DE MATERIAL X
    private ActionBar actionBar;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavigationView nav_view;
    private View headerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //capturas de los componentes en la navegacion lateral
        navigationView = findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        fullNameText = headerView.findViewById(R.id.fullNameText);
        email = headerView.findViewById(R.id.email);
        photoProfile = headerView.findViewById(R.id.avatar_user);
        sign_Out = headerView.findViewById(R.id.nav_sign_out);


        //CODIGO DE GOOGLE
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Main2Activity.this);
        if (acct != null) {

            personEmail = acct.getEmail();
            personGivenName = acct.getGivenName();
            personFamilyName = acct.getFamilyName();
            personPhoto = acct.getPhotoUrl();
            firstWord = personGivenName.replaceAll(" .*", "");
            fullName = firstWord + " " + personFamilyName;

            fullNameText.setText(fullName);
            email.setText(personEmail);
            Glide.with(this).load(personPhoto).into(photoProfile);
        }


        initApliccation();
        initNavigationMenu(acct);

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(Main2Activity.this, "Cerrando Sesion ...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Main2Activity.this, MainActivity.class));
                        finish();

                    }
                });

    }

    //CLASES MATERIAL X

    private void initApliccation() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Inicio");
    }

    private void initNavigationMenu(final GoogleSignInAccount acct) {
        nav_view = findViewById(R.id.nav_view);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
        };
        drawer.closeDrawer(GravityCompat.START);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("ResourceType")
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home: {
                        showNavigationHome(item);
                        break;
                    }
                    case R.id.nav_profile: {
                        showNavigationProfile(item,acct);
                        break;
                    }
                    case R.id.nav_sign_out: {
                        drawer.closeDrawers();
                        Toast.makeText(getApplicationContext(), "Cerrando Sesion", Toast.LENGTH_SHORT).show();
                        signOut();
                    }
                }

                return true;
            }
        });
    }

    private void showNavigationHome(MenuItem item){
        FragmentManager fragmentManager=getSupportFragmentManager();
        HomeFragment homeFragment=new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.drawer_content,homeFragment).commit();
        actionBar.setTitle(item.getTitle());
        drawer.closeDrawers();
    }
    private void showNavigationProfile(MenuItem item,GoogleSignInAccount acct){
        FragmentManager fragmentManager=getSupportFragmentManager();
        PerfilFragment profileFragment=new PerfilFragment();
        profileFragment.PerfilFragment(acct);
        fragmentManager.beginTransaction().replace(R.id.drawer_content,profileFragment).commit();
        actionBar.setTitle(item.getTitle());
        drawer.closeDrawers();
    }


}

