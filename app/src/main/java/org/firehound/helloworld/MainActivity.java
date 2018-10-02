package org.firehound.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    public static String PREF_KEY = "org.firehound.helloworld.PREF_KEY";
    public static int REQ_CODE = 420;
    public static String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme); //Switch back to regular theme after splash screen
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_framelayout, new HomeFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
        BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.home_nav:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.results:
                        selectedFragment = new ResultsFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_framelayout, selectedFragment).commit();
                return true;
            }
        };
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);

        boolean firstStart = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PREF_KEY, true);
        if (firstStart) {
            Intent intent = new Intent(this, MainIntroActivity.class);
            //startActivityForResult(intent, REQ_CODE);
        }

        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, SignInActivity.class));
        }
        Intent signInIntent = getIntent();
        if (signInIntent.getExtras() != null) {
            userid = signInIntent.getExtras().getString("uid");
            Toast.makeText(this, "Welcome, " + userid, Toast.LENGTH_SHORT).show();
        } else if (firebaseAuth.getCurrentUser() != null) {
            userid = firebaseAuth.getCurrentUser().getUid();
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
