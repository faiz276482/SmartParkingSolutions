package com.nerdytech.smartparkingsolutions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nerdytech.smartparkingsolutions.ui.history.HistoryFragment;
import com.nerdytech.smartparkingsolutions.ui.profile.ProfileFragment;
import com.nerdytech.smartparkingsolutions.ui.search.SearchFragment;
import com.nerdytech.smartparkingsolutions.ui.wallet.WalletFragment;

public class MainActivity extends AppCompatActivity {

//    TextView emailId;
//    Button logout;'
//widgets
    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        emailId=findViewById(R.id.email_textView);
//        logout=findViewById(R.id.logout_btn);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_history: {
                        selectorFragment = new HistoryFragment();
                        break;
                    }
                    case R.id.nav_search: {
                        selectorFragment = new SearchFragment();
                        break;
                    }
                    case R.id.nav_wallet: {
                        selectorFragment = new WalletFragment();
                        break;
                    }
                    case R.id.nav_profile: {
                        selectorFragment = new ProfileFragment();

                        break;
                    }
                }

                if(selectorFragment!=null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectorFragment).commit();
                }
                return true;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SearchFragment()).commit();


//        try{
//            if(mUser!=null) {//Checking if user is actually present
//                emailId.setText(mUser.getEmail());
//            }
//        }
//        catch (Exception e)
//        {
//            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
//        }
//
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.signOut();
//                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
//            }
//        });


    }
}
