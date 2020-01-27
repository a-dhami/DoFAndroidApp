package com.akash.lens.model;

public class Lens {
    private String make;
    private double maxAperture;
    private int focalLength;

    //auto generated constructor.
    public Lens(String make, double maxAperture, int focalLength) {
        this.make = make;
        this.maxAperture = maxAperture;
        this.focalLength = focalLength;
    }

    //auto generated getter and setter
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public double getMaxAperture() {
        return maxAperture;
    }

    public void setMaxAperture(double maxAperture) {
        this.maxAperture = maxAperture;
    }

    public int getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(int focalLength) {
        this.focalLength = focalLength;
    }

    @Override
    public String toString() {
        return  make + ' '
                + focalLength + "mm  F"
                + maxAperture;
    }


}