package org.firehound.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private String uid;
    private FirebaseAuth firebaseAuth;
    public static String PREF_KEY = "org.firehound.helloworld.PREF_KEY";
    public static int REQ_CODE = 420;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme); //Switch back to regular theme after splash screen
        setContentView(R.layout.activity_main);

        boolean firstStart = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PREF_KEY, true);
        if (firstStart) {
            Intent intent = new Intent(this, MainIntroActivity.class);
            //startActivityForResult(intent, REQ_CODE);
        }

        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, SignInActivity.class));
        }

        //If it's the app's first start, start the intro activity

        Intent signInIntent = getIntent();
        if (signInIntent.getExtras() != null) {
            uid = signInIntent.getExtras().getString("uid");
            Toast.makeText(this, "Welcome, " + uid, Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(PREF_KEY, false).apply();
            } else {
                PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(PREF_KEY,true).apply();
            }
        }
    }
}
