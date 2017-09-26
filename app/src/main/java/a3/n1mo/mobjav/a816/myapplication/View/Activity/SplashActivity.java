package a3.n1mo.mobjav.a816.myapplication.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import a3.n1mo.mobjav.a816.myapplication.R;

public class SplashActivity extends AppCompatActivity {

    private String TAG = "SplashActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final int SPLASH_DISPLAY_LENGTH = 1200;
        final int SPLASH_DISPLAY_OFFSET = getResources().getInteger(android.R.integer.config_shortAnimTime);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.d(TAG, "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                final LinearLayout img = (LinearLayout) SplashActivity.this.findViewById(R.id.splashscreen);

                Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setInterpolator(new AccelerateInterpolator());
                fadeOut.setDuration(getResources().getInteger(android.R.integer.config_longAnimTime));

                fadeOut.setAnimationListener(new Animation.AnimationListener()
                {
                    public void onAnimationEnd(Animation animation)
                    {
                        img.setVisibility(View.GONE);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Create an intent that will start the main activity.
                                Intent mainIntent = new Intent(SplashActivity.this, ActivityLogIn.class);
                                SplashActivity.this.startActivity(mainIntent);

                                //Apply splash exit (fade out) and main entry (fade in) animation transitions.
                                //overridePendingTransition(R.anim.zoom_enter, android.R.anim.fade_out);

                                //Finish splash activity so user cant go back to it.
                                SplashActivity.this.finish();

                            }
                        }, SPLASH_DISPLAY_OFFSET);

                    }
                    public void onAnimationRepeat(Animation animation) {}
                    public void onAnimationStart(Animation animation) {}
                });

                img.startAnimation(fadeOut);

                Log.d(TAG, "finish SplashScreen: " + SPLASH_DISPLAY_LENGTH);

            }
        }, SPLASH_DISPLAY_LENGTH);

    }

}
