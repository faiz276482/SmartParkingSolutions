package com.nerdytech.smartparkingsolutions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    EditText name,email,password,confirm_password;
    Button signup_btn;
    ImageButton google;
    TextView login_existing;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        confirm_password=findViewById(R.id.confirm_password);
        google=findViewById(R.id.google_login_btn);
        login_existing=findViewById(R.id.login_existing);
        signup_btn=findViewById(R.id.signup_btn);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uEmail=email.getText().toString();
                String uPass=password.getText().toString();
                String uConfirmPass=confirm_password.getText().toString();
                String uName=name.getText().toString();

                boolean flag=true;

                if(TextUtils.isEmpty(uName)){
                    Toast.makeText(SignUpActivity.this, "Enter your name!", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(email.getText()) || !validateEmailAddress(uEmail) ){
                    Toast.makeText(SignUpActivity.this, "Enter Proper Email!", Toast.LENGTH_SHORT).show();
                    flag=false;
                }
                if(TextUtils.isEmpty(password.getText()) || uPass.length()<6 || !passwordValidate(uPass))
                {
                    Toast.makeText(SignUpActivity.this,"Enter password with atleast 6 characters\nIt should have one symbol\nIt should have number\nIt should have one Uppercase chraracter\nIt should have one lowercase character",Toast.LENGTH_SHORT).show();
                    flag=false;
                }
                if(!uConfirmPass.equals(uPass) || TextUtils.isEmpty(confirm_password.getText()))
                {
                    Toast.makeText(SignUpActivity.this, "Password Mismatch!", Toast.LENGTH_SHORT).show();
                    flag=false;
                }

                if(flag){
                    mAuth.createUserWithEmailAndPassword(uEmail,uPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mAuth.getCurrentUser().sendEmailVerification();
                            System.out.println("email:"+mAuth.getCurrentUser().getEmail());
                            Toast.makeText(SignUpActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private boolean passwordValidate(String uPass) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(uPass);

        return matcher.matches();
    }

    private boolean validateEmailAddress(String emailAddress){
        String  expression="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }
}
