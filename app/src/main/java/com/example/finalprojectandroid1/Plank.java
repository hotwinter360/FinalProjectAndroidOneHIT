package com.example.finalprojectandroid1;

import android.graphics.RectF;

public class Plank {

    // RectF is an object that holds four coordinates - just what we need
    private RectF rect;

    // How long and high our Plank will be
    private float length;
    private float height;

    // X is the far left of the rectangle which forms our Plank
    private float x;

    // Y is the top coordinate
    private float y;

    // This will hold the pixels per second speedthat the Plank will move
    private float PlankSpeed;

    // Which ways can the Plank move
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    // Is the Plank moving and in which direction
    private int PlankMoving = STOPPED;

    // This the the constructor method
    // When we create an object from this class we will pass
    // in the screen width and height
    public Plank(int screenX, int screenY){
        // 130 pixels wide and 20 pixels high
        length = 130;
        height = 20;

        // Start Plank in roughly the sceen centre
        x = screenX / 2;
        y = screenY - 20;

        // Initialize rectangle
        rect = new RectF(x, y, x + length, y + height);

        // How fast is the Plank in pixels per second
        PlankSpeed = 350;
    }


    // This is a getter method to make the rectangle that
    // defines our Plank available in BreakoutView class
    public RectF getRect(){
        return rect;
    }

    // This method will be used to change/set if the Plank is going left, right or nowhere
    public void setMovementState(int state){
        PlankMoving = state;
    }

    // This update method will be called from update in BreakoutView
    // It determines if the Plank needs to move and changes the coordinates
    // contained in rect if necessary
    public void update(long fps){
        if(PlankMoving == LEFT){
            x = x - PlankSpeed / fps;
        }

        if(PlankMoving == RIGHT){
            x = x + PlankSpeed / fps;
        }

        rect.left = x;
        rect.right = x + length;
    }

}
