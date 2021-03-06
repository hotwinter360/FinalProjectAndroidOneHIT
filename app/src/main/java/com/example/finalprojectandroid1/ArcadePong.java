package com.example.finalprojectandroid1;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ArcadePong extends LevelsActivity {

    // gameView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    BreakoutView breakoutView;
    Bitmap iBackground;
    SharedPreferences sp;

 //   Bitmap Ball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize gameView and set it as the view
        breakoutView = new BreakoutView(this);
        setContentView(breakoutView);
    }

    // Here is our implementation of BreakoutView
    // It is an inner class.
    // Note how the final closing curly brace }
    // is inside the ArcadePong class

    // Notice we implement runnable so we have
    // A thread and can override the run method.
    class BreakoutView extends SurfaceView implements Runnable {

        // This is our thread
        Thread gameThread = null;

        // This is new. We need a SurfaceHolder
        // When we use Paint and Canvas in a thread
        // We will see it in action in the draw method soon.
        SurfaceHolder ourHolder;

        // A boolean which we will set and unset
        // when the game is running- or not.
        volatile boolean playing;

        // Game is paused at the start
        boolean paused = true;

        // A Canvas and a Paint object
        Canvas canvas;
        Paint paint;

        // This variable tracks the game frame rate
        long fps;

        // This is used to help calculate the fps
        private long timeThisFrame;

        // The size of the screen in pixels
        int screenX;
        int screenY;

        // The players plank
        Plank plank;

        // A pong
        Pong pong;

        // Up to 200 bricks
        Bricks[] bricks = new Bricks[200];
        int numBricks = 0;

        // For sound FX
        SoundPool soundPool;
        int beep1ID = -1;
        int beep2ID = -1;
        int beep3ID = -1;
        int loseLifeID = -1;
        int explodeID = -1;

        // The score
        int score = 0;
        // Lives
        int lives = 3;

        // When the we initialize (call new()) on gameView
        // This special constructor method runs
        public BreakoutView(Context context) {
            // The next line of code asks the
            // SurfaceView class to set up our object.
            // How kind.
            super(context);
            iBackground = BitmapFactory.decodeResource(getResources(),R.drawable.gameimage);
           // Ball = BitmapFactory.decodeResource(getResources(),R.drawable.ball);

            // Initialize ourHolder and paint objects
            ourHolder = getHolder();
            paint = new Paint();


            // Get a Display object to access screen details
            Display display = getWindowManager().getDefaultDisplay();
            // Load the resolution into a Point object
            Point size = new Point();
            display.getSize(size);

            screenX = size.x;
            screenY = size.y;

            plank = new Plank(screenX, screenY);

            // Create a pong
            pong = new Pong(screenX, screenY);

            // Load the sounds

            // This SoundPool is deprecated
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);

            try{
                // Create objects of the 2 required classes
                AssetManager assetManager = context.getAssets();
                AssetFileDescriptor descriptor;

                // Load our fx in memory ready for use
                descriptor = assetManager.openFd("beep1.ogg");
                beep1ID = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("beep2.ogg");
                beep2ID = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("beep3.ogg");
                beep3ID = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("loseLife.ogg");
                loseLifeID = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("explode.ogg");
                explodeID = soundPool.load(descriptor, 0);

            }catch(IOException e){
                // Print an error message to the console
                Log.e("error", "failed to load sound files");
            }

            createBricksAndRestart();

        }

        public void createBricksAndRestart(){

            // Put the pong back to the start
            pong.reset(screenX, screenY);

            int brickWidth = screenX / 8;
            int brickHeight = screenY / 10;

            // Build a wall of bricks
            numBricks = 0;
            for(int column = 0; column < 8; column ++ ){
                for(int row = 0; row < level; row ++ ){
                    bricks[numBricks] = new Bricks(row, column, brickWidth, brickHeight);
                    numBricks ++;
                }
            }

            // if game over reset scores and lives
            if(lives == 0) {
                score = 0;
                lives = 3;
            }

        }

        @Override
        public void run() {
            while (playing) {

                // Capture the current time in milliseconds in startFrameTime
                long startFrameTime = System.currentTimeMillis();

                // Update the frame
                // Update the frame
                if(!paused){
                    update();
                }

                // Draw the frame
                draw();

                // Calculate the fps this frame
                // We can then use the result to
                // time animations and more.
                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame >= 1) {
                    fps = 500 / timeThisFrame;
                }
            }
        }

        // Everything that needs to be updated goes in here
        // Movement, collision detection etc.
        public void update() {

            // Move the plank if required
            plank.update(fps,screenX);

            pong.update(fps);

            // Check for pong colliding with a brick
            for(int i = 0; i < numBricks; i++){

                if (bricks[i].getVisibility()){


                    if(RectF.intersects(bricks[i].getRect(), pong.getRect())) {
                        bricks[i].setInvisible();
                        pong.reverseYVelocity();
                        score = score + 10;

                        soundPool.play(explodeID, 1, 1, 0, 0, 1);
                    }
                }
            }

            // Check for pong colliding with plank
            if(RectF.intersects(plank.getRect(),pong.getRect())) {
                pong.setRandomXVelocity();
                pong.reverseYVelocity();
                pong.clearObstacleY(plank.getRect().top - 2);

                soundPool.play(beep1ID, 1, 1, 0, 0, 1);
            }

            // Bounce the pong back when it hits the bottom of screen
            if(pong.getRect().bottom > screenY){
                pong.reverseYVelocity();
                pong.clearObstacleY(screenY - 2);

                // Lose a life
                lives --;
                soundPool.play(loseLifeID, 1, 1, 0, 0, 1);

                if(lives == 0){
                    paused = true;
                    sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("Score",score);
                    editor.apply();
                    createBricksAndRestart();

                    Intent intent = new Intent(ArcadePong.this, Score.class);
                    intent.putExtra("SCORE",score);
                    startActivity(intent);
                }
            }

            // Bounce the pong back when it hits the top of screen
            if(pong.getRect().top < 0){
                pong.reverseYVelocity();
                pong.clearObstacleY(12);

                soundPool.play(beep2ID, 1, 1, 0, 0, 1);
            }

            // If the pong hits left wall bounce
            if(pong.getRect().left < 0){
                pong.reverseXVelocity();
                pong.clearObstacleX(2);

                soundPool.play(beep3ID, 1, 1, 0, 0, 1);
            }

            // If the pong hits right wall bounce
            if(pong.getRect().right > screenX - 10){
                pong.reverseXVelocity();
                pong.clearObstacleX(screenX - 22);

                soundPool.play(beep3ID, 1, 1, 0, 0, 1);
            }


            // Pause if cleared screen
            if(score == numBricks * 10){
                paused = true;

                sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("exc_score",score);
                editor.apply();
                createBricksAndRestart();

                Intent intent = new Intent(ArcadePong.this, Score.class);
                intent.putExtra("SCORE",score);
                startActivity(intent);
            }

        }

        // Draw the newly updated scene
        public void draw() {

            String appName=getResources().getString(R.string.app_name);
            String play=getResources().getString(R.string.Play);
            String scores=getResources().getString(R.string.Scores);
            String live=getResources().getString(R.string.Lives);
            String lose=getResources().getString(R.string.Lose);
            String win=getResources().getString(R.string.Win);

            // Make sure our drawing surface is valid or we crash
            if (ourHolder.getSurface().isValid()) {
                // Lock the canvas ready to draw
                canvas = ourHolder.lockCanvas();
                // Draw the background color
                canvas.drawBitmap(iBackground,0,0,null);


                // Draw the plank
                canvas.drawRect(plank.getRect(), paint);

                // Draw the pong
                canvas.drawRect(pong.getRect(), paint);



                // Change the brush color for drawing
                paint.setColor(Color.argb(252,  150, 0, 150));

                // Draw the bricks if visible
                for(int i = 0; i < numBricks; i++){
                    if(bricks[i].getVisibility()) {
                        canvas.drawRect(bricks[i].getRect(), paint);
                    }
                }

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255,  255, 255, 255));

                // Draw the score
                paint.setTextSize(40);
                canvas.drawText("  " + getString(R.string.Scores) + " : " + score +"  " + getString(R.string.Lives) + " : " + lives, 10,50, paint);


                // Has the player lost?
                if(lives <= 0){
                    paint.setTextSize(90);
                    canvas.drawText(getString(R.string.Lose), 10,screenY/2, paint);
                }

                // Draw everything to the screen
                ourHolder.unlockCanvasAndPost(canvas);
            }

        }

        // If SimpleGameEngine Activity is paused/stopped
        // shutdown our thread.
        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }

        }

        // If SimpleGameEngine Activity is started then start our thread.
        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        // The SurfaceView class implements onTouchListener
        // So we can override this method and detect screen touches.
        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                // Player has touched the screen
                case MotionEvent.ACTION_DOWN:

                    paused = false;

                    if(motionEvent.getX() > screenX / 2){
                        plank.setMovementState(plank.RIGHT);
                    }
                    else{
                        plank.setMovementState(plank.LEFT);
                    }
                    break;


                // Player has removed finger from screen
                case MotionEvent.ACTION_UP:

                    plank.setMovementState(plank.STOPPED);
                    break;
            }
            return true;
        }

    }
    // This is the end of our BreakoutView inner class

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        breakoutView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        breakoutView.pause();
    }

}
// This is the end of the ArcadePong class