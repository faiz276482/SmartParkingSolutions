package com.nerdytech.smartparkingsolutions.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nerdytech.smartparkingsolutions.LoginActivity;
import com.nerdytech.smartparkingsolutions.R;


public class ProfileFragment extends Fragment {

    View view;
    Toolbar toolbar;

    TextView emailId;
    Button logout;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        emailId=view.findViewById(R.id.email_textView);
        logout=view.findViewById(R.id.logout_btn);
        toolbar=view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");

        try{
            if(mUser!=null) {//Checking if user is actually present
                emailId.setText(mUser.getEmail());
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                // Configure Google Sign In
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                // [END config_signin]

                mGoogleSignInClient = GoogleSignIn.getClient(view.getContext(), gso);
                mGoogleSignInClient.signOut();
                Intent intent=new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }


}
