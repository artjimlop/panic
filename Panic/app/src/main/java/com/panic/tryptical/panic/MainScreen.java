package com.panic.tryptical.panic;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;


public class MainScreen extends Activity {

    private ImageView mFloor;
    private ImageView mPacman;
    private ImageView mGoToLeft;
    private ImageView mGoToRight;
    private ViewPropertyAnimator mPacmanAnimator;
    private static Integer PACMAN_HEIGHT = 100;
    private static Integer FLOOR_HEIGHT = 184;
    private Integer mScreenWidth;
    private Integer mScreenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        // Initialize views
        mFloor = (ImageView) findViewById(R.id.floor);
        mPacman = (ImageView) findViewById(R.id.pacman);
        mGoToLeft = (ImageView) findViewById(R.id.goToLeft);
        mGoToRight = (ImageView) findViewById(R.id.goToRight);

        // Starting
        getScreenMeasures();
        startingAnimation();

    }

    private void getScreenMeasures(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mScreenWidth = size.x;
        mScreenHeight = size.y;
    }

    private void startingAnimation(){

        mPacmanAnimator = mPacman.animate().y(mScreenHeight-FLOOR_HEIGHT-PACMAN_HEIGHT);
        mPacmanAnimator.setDuration(1000);
        mPacmanAnimator.start();

    }


}
