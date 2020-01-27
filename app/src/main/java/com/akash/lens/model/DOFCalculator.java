package com.akash.lens.model;

public class DOFCalculator {

    private static double calcHyperfocal(double focalLength, double aperture, double COC)
    {
        return Math.pow(focalLength, 2) / (aperture * COC);
    }


    public static double[] calcDOF(double focalLength, double aperture, double distance, double COC) {
        distance = distance * 1000;
        double hyperFocal = calcHyperfocal(focalLength, aperture, COC);
        double nearFocal = ((hyperFocal * distance) / (hyperFocal + (distance - focalLength)));


        double farFocal;
        if(distance > hyperFocal) {
            farFocal = Double.POSITIVE_INFINITY;
        }
        else {

            farFocal = ((hyperFocal * distance) / (hyperFocal - (distance - focalLength)));
        }

        double depthOfField = (farFocal - nearFocal);

        return new double[] {nearFocal, farFocal, depthOfField, hyperFocal};

    }
}