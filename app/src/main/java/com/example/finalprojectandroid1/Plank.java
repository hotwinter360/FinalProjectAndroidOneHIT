package com.example.finalprojectandroid1;

import android.graphics.RectF;

public class Plank {

    // RectF is an object that holds four coordinates - just what we need
    private RectF rect;

    // How long and high our plank will be
    private float length;
    private float height;

    // X is the far left of the rectangle which forms our plank
    private float x;

    // Y is the top coordinate
    private float y;

    // This will hold the pixels per second speed that the plank will move
    private float plankSpeed;

    // Which ways can the plank move
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    // Is the plank moving and in which direction
    private int plankMoving = STOPPED;

    // This the the constructor method
    // When we create an object from this class we will pass
    // in the screen width and height
    public Plank(int screenX, int screenY){
        // 130 pixels wide and 20 pixels high
        length = 130;
        height = 20;

        // Start plank in roughly the screen centre
        x = screenX / 2;
        y = screenY - 20;

        // Initialize rectangle
        rect = new RectF(x, y, x + length, y + height);

        // How fast is the plank in pixels per second
        plankSpeed = 1250;
    }


    // This is a getter method to make the rectangle that
    // defines our plank available in BreakoutView class
    public RectF getRect(){
        return rect;
    }

    // This method will be used to change/set if the plank is going left, right or nowhere
    public void setMovementState(int state){
        plankMoving = state;
    }

    // This update method will be called from update in BreakoutView
    // It determines if the plank needs to move and changes the coordinates
    // contained in rect if necessary
    public void update(long fps){
        if (plankMoving == LEFT){
            x = x - plankSpeed / fps;
        }

        if (plankMoving == RIGHT){
            x = x + plankSpeed / fps;
        }

        rect.left = x;
        rect.right = x + length;
    }

}

