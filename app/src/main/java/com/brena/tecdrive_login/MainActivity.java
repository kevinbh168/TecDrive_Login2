package com.brena.tecdrive_login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static com.google.android.gms.auth.api.signin.GoogleSignIn.*;

public class MainActivity extends AppCompatActivity  {

//    private RelativeLayout form;
//    private Button singOut;
//    private SignInButton singIn;
//    private TextView fullName;
//    private TextView email;
//    private ImageView photoProfile;
//    private GoogleApiClient googleApiClient;
//    private static final int REQ_CODE=9001;
//        form= (RelativeLayout) findViewById(R.id.form_info);
//        singOut = findViewById(R.id.button_sign_out);
//        singIn = (SignInButton) findViewById(R.id.button_sign_in);
//        fullName = (TextView) findViewById(R.id.text_nom_ape);
//        email = (TextView) findViewById(R.id.text_email);
//        photoProfile = (ImageView) findViewById(R.id.foto_id);

    int RC_SIGN_IN=0;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInButton=findViewById(R.id.button_sign_in);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
           // startActivity(new Intent(MainActivity.this, Main2Activity.class));

            if(account.getEmail().contains("@tecsup.edu.pe")==true) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));

            }
            else if(account.getEmail().contains("@tecsup.edu.pe")==false){
                Toast.makeText(this,"No es un correo institucional",Toast.LENGTH_SHORT).show();
                signOut();
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.

        String validarCorreo;
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(account!=null) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
        }
        super.onStart();
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "No es una cuenta de Tecsup. Lo siento", Toast.LENGTH_SHORT).show();
                       return;

                    }
                });

    }

}
