package org.firehound.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity{
    private EditText email, password;
    private FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    private static final String TAG = "SignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_sign_in);
        email = findViewById(R.id.email_edittext);
        password = findViewById(R.id.pass_edittext);
        Button signIn = findViewById(R.id.sign_in);
        Button register = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        signIn.setOnClickListener(listener);
        register.setOnClickListener(listener);

    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sign_in:
                    startSignIn();
                case R.id.register:
                    startRegister();
            }
        }
    };

    private void startRegister() {
        String emailText = email.getText().toString().trim();
        String passText = password.getText().toString().trim();
        if(sanitizeInput(emailText, passText)) {
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(emailText, passText).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("uid", firebaseAuth.getCurrentUser().getUid());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignInActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, task.getException().toString());
                    }
                }
            });
        }
    }

    private void startSignIn() {
        String emailText = email.getText().toString().trim();
        String passText = password.getText().toString().trim();
        if (sanitizeInput(emailText, passText)) {
            firebaseAuth.signInWithEmailAndPassword(emailText, passText).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("uid", firebaseAuth.getCurrentUser().getUid());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignInActivity.this, "Sign in failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean sanitizeInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email must not be empty.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password must not be empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
