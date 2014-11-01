package com.panic.tryptical.panic;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


public class MainScreen extends Activity {

    private RelativeLayout mRootLayout;
    private ImageView mFloor;
    private ImageView mPacman;
    private ImageView mGoToLeft;
    private ImageView mGoToRight;
    private ViewPropertyAnimator mPacmanAnimator;
    private static Integer PACMAN_HEIGHT = 100;
    private static Integer PACMAN_WIDHT = 100;
    private static Integer FLOOR_HEIGHT = 184;
    private Integer mScreenWidth;
    private Integer mScreenHeight;
    private Boolean mLookingAtRight;
    private List<ImageView> ghosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        // Initialize views
        mRootLayout = (RelativeLayout) findViewById(R.id.relative_game_layout);
        mFloor = (ImageView) findViewById(R.id.floor);
        mPacman = (ImageView) findViewById(R.id.pacman);
        mGoToLeft = (ImageView) findViewById(R.id.goToLeft);
        mGoToRight = (ImageView) findViewById(R.id.goToRight);

        //Initialize list
        ghosts = new ArrayList<ImageView>();

        // Starting
        getScreenMeasures();
        startingAnimation();
        mLookingAtRight = true;

        // Listeners to move pacman
        pacmanListeners();

        generateGhost();

    }

    private void getScreenMeasures(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mScreenWidth = size.x;
        mScreenHeight = size.y;
    }

    private void startingAnimation(){
        mPacmanAnimator = mPacman.animate();
        mPacmanAnimator.y(mScreenHeight - FLOOR_HEIGHT - PACMAN_HEIGHT);
        mPacmanAnimator.setDuration(1000);
        mPacmanAnimator.start();
    }

    /*
        Face the side, move and shot
     */
    private void pacmanListeners(){
        // TODO: new image to flip left and right
        mGoToLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLookingAtRight.equals(true)) {
                    mLookingAtRight = false;
                    mPacman.setImageResource(R.drawable.pacman_flipped);
                }
                mPacmanAnimator.x(mPacman.getX()-5);
                mPacmanAnimator.setDuration(10);
                mPacmanAnimator.start();
                generateBullets(false);
            }
        });

        mGoToRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLookingAtRight.equals(false)) {
                    mLookingAtRight = true;
                    mPacman.setImageResource(R.drawable.pacman);
                }
                mPacmanAnimator.x(mPacman.getX()+5);
                mPacmanAnimator.setDuration(10);
                mPacmanAnimator.start();
                generateBullets(true);
            }
        });
    }

    private void generateBullets(Boolean lookingAtRight){
        final ImageView imgView = new ImageView(this);
        // Setting its length and width
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(25, 25);
        // Setting its position
        Float startingX;
        if(lookingAtRight.equals(true))
            startingX = mPacman.getX()+PACMAN_WIDHT;
        else
            startingX = mPacman.getX() - PACMAN_WIDHT/2;
        Float startingY = mPacman.getY() +PACMAN_HEIGHT/3;
        Integer startInX = startingX.intValue();
        Integer startInY = startingY.intValue();
        layoutParams.setMargins(startInX,startInY, 0,0);
        // Applying bullet drawable to the ImageView
        imgView.setBackground(getResources().getDrawable(R.drawable.bullet));
        // Applying the size and position to the ImageView
        imgView.setLayoutParams(layoutParams);
        // Adding to the view
        mRootLayout.addView(imgView);
        animateBullet(imgView,lookingAtRight);
    }

    private void animateBullet(ImageView imgView, Boolean lookingAtRight){
        ViewPropertyAnimator bulletAnimator = imgView.animate();
        Integer location;
        if(lookingAtRight.equals(true)){
            location = mScreenWidth;
        }else{
            location = -mScreenWidth;
        }
        bulletAnimator.translationX((float)location);
        bulletAnimator.setDuration(2000);
        bulletAnimator.start();
    }

    private void generateGhost(){
        final ImageView imgView = new ImageView(this);
        // Setting its length and width
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(75, 100);
        // Setting its position
        Integer startInX;

        Boolean appear = Math.random() < 0.5;

        if(appear.equals(true))
            startInX = mScreenWidth/100;
        else
            startInX = 3*(mScreenWidth/4);
        Integer startInY = mScreenHeight - FLOOR_HEIGHT - PACMAN_HEIGHT;
        layoutParams.setMargins(startInX,startInY, 0,0);
        // Applying bullet drawable to the ImageView
        imgView.setBackground(getResources().getDrawable(R.drawable.ghost));
        // Applying the size and position to the ImageView
        imgView.setLayoutParams(layoutParams);
        // Adding to the view
        mRootLayout.addView(imgView);

        ghosts.add(imgView);

        animateGhost(imgView, appear);
    }

    private void animateGhost(ImageView imageView, Boolean appear){
        ViewPropertyAnimator ghostAnimator = imageView.animate();
        if(appear.equals(true))
            ghostAnimator.translationX(mScreenWidth);
        else
            ghostAnimator.translationX(-mScreenWidth);
        ghostAnimator.setDuration(15000);
        ghostAnimator.start();

        /*while(ghosts.contains(imageView)){
            checkCollisionGhostPacman(imageView);
        }*/
    }

    private void checkCollisionGhostPacman(ImageView ghost){
        // Properties of the pacman:
        int[] img_coordinates = new int[2];
        ghost.getLocationOnScreen(img_coordinates);
        // If a user finishes a drag with a square overlapping the black circle it should be “sucked in” to the circle and leave the scene.
        if(ghost.getX()>= img_coordinates[0] && ghost.getX()<= img_coordinates[0]+mPacman.getWidth()){

            System.out.println("COLISION");
            //ghosts.remove(ghost);
        }
    }


}
