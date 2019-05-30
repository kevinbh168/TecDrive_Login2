package com.brena.tecdrive_login.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brena.tecdrive_login.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilFragment extends Fragment {

    private TextView nameText,namepostText;
    private ImageView imageCircle;
    private ImageView imageView;
    private GoogleSignInAccount acct;

    public void PerfilFragment (GoogleSignInAccount acct) {
        this.acct = acct;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_perfil, container, false);
        nameText=v.findViewById(R.id.nombreText);
        namepostText=v.findViewById(R.id.namePostText);
        imageCircle=v.findViewById(R.id.profileImage);
        imageView=v.findViewById(R.id.profilePostImage);

        if (acct != null)
        {
            Uri personPhoto = acct.getPhotoUrl();
            String firsName=acct.getGivenName();
            String lastName=acct.getFamilyName();

            String firstName = firsName.replaceAll(" .*", "");
            String firstLastName = lastName.replaceAll(" .*", "");

            Glide.with(this).load(personPhoto).into(imageView);
            Glide.with(this).load(personPhoto).into(imageCircle);
            nameText.setText(firsName+" "+lastName);
            namepostText.setText(firstName+" "+firstLastName);
        }
        return v;
    }







}
