package org.firehound.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainIntroActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Slide 1
        addSlide(new SimpleSlide.Builder()
        .title("Hello World 2.0")
        .background(R.color.colorAccent)
        .backgroundDark(R.color.colorAccent)
        .description("Sample Text 420")
        .image(R.drawable.ic_launcher_web)
        .build());
    }
}
