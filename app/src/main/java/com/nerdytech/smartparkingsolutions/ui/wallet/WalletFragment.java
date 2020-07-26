package com.nerdytech.smartparkingsolutions.ui.wallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.nerdytech.smartparkingsolutions.R;
import com.nerdytech.smartparkingsolutions.model.Wallet;

import org.w3c.dom.Text;


public class WalletFragment extends Fragment {
    View view;
    Toolbar toolbar;
    Button addMoney;
    TextView amount;
    int balanace=0;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_wallet, container, false);
        toolbar=view.findViewById(R.id.toolbar);
        amount=view.findViewById(R.id.amount);
        addMoney=view.findViewById(R.id.add_money);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Wallet");

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        DocumentReference docRef= FirebaseFirestore.getInstance().collection("Wallet").document(mUser.getUid());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                {
                    Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
                try {


                    if (value.exists()) {
                        Wallet wallet = value.toObject(Wallet.class);
                        balanace = wallet.getAmount();
                        amount.setText("Rs." + balanace);
                    }
                }
                catch (Exception e)
                {
                    Log.i("Wallet",e.getMessage());
                }
            }
        });

        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Amount to be added?");

// Set up the input
                final EditText input = new EditText(view.getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //m_Text = input.getText().toString();
                        balanace+= Integer.parseInt(input.getText().toString());
                        DocumentReference docRef= FirebaseFirestore.getInstance().collection("Wallet").document(mUser.getUid());
                        docRef.set(new Wallet(balanace));
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        return view;
    }
}
