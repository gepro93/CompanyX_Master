package com.example.gergo.companyx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class SplashScreen extends AppCompatActivity {

    private ImageView iwLogo;
    private TextView twLogo, twPrecentage;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();

        DataAdapter mDbHelper = new DataAdapter(SplashScreen.this);
        mDbHelper.createDatabase();
        mDbHelper.open();

        mDbHelper.close();

        iwLogo.animate().alpha(1f).setDuration(1500);
        twLogo.animate().alpha(1f).setDuration(1500);
        twPrecentage.animate().alpha(1f).setDuration(1500);
        progressBar.animate().alpha(1f).setDuration(1500);

        progressBar.setMax(100);

        progressBarAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Animatoo.animateCard(SplashScreen.this);
        finish();
    }

    public void init(){
        iwLogo = (ImageView) findViewById(R.id.iwLogo);
        twLogo = (TextView) findViewById(R.id.twLogo);
        twPrecentage = (TextView) findViewById(R.id.twPrecentage);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void progressBarAnimation(){
        AnimationLogin anim = new AnimationLogin(this,progressBar,twPrecentage,0f,100f);
        anim.setDuration(2000);
        progressBar.setAnimation(anim);
    }

}
