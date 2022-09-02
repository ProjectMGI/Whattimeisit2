package mgi.gabor.uk.whattimeisit;

import android.content.Intent;
import android.graphics.Point;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import maes.tech.intentanim.CustomIntent;

public class Splash extends AppCompatActivity {

    static int screenHeight, screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ImageView splash_logo = findViewById(R.id.ivLogo);
        ImageView loading = findViewById(R.id.iv_loading);

        ScreenSize();

        splash_logo.getLayoutParams().height = screenHeight / 4;
        splash_logo.getLayoutParams().width = screenWidth / 4;
        splash_logo.requestLayout();

        loading.getLayoutParams().height = screenHeight / 8;
        loading.getLayoutParams().width = screenWidth / 5;
        loading.requestLayout();

        Animation loading_text_anim = AnimationUtils.loadAnimation(this, R.anim.loading);
        loading.startAnimation(loading_text_anim);
        
        loading_text_anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                
            }

            @Override
            public void onAnimationEnd(Animation animation) {



                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                CustomIntent.customType(Splash.this, "bottom-to-up");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
    private void ScreenSize() {
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }


}
