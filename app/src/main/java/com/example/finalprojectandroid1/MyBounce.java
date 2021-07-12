package com.example.finalprojectandroid1;

import android.view.animation.Interpolator;

public class MyBounce implements Interpolator {
    private double myAmplitude = 1;
    private  double myFrequency = 10;

    MyBounce(double amplitude, double frequency){
        myAmplitude =amplitude;
        myFrequency= frequency;
    }
    @Override
    public float getInterpolation(float time) {
        return (float)(-1*Math.pow(Math.E, -time/myAmplitude)*Math.cos(myFrequency*time)+1);
    }
}
