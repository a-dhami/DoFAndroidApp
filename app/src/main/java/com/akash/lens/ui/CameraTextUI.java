package com.akash.lens.ui;
import com.akash.lens.model.DOFCalculator;
import com.akash.lens.model.Lens;
import com.akash.lens.model.LensManager;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class CameraTextUI {

    private static final double COC = 0.029;
    private LensManager manager;

    public CameraTextUI(LensManager manager) {
        this.manager = manager;
        manager.add(new Lens("Canon", 1.8, 50));
        manager.add(new Lens("Tamron", 2.8, 90));
        manager.add(new Lens("Sigma", 2.8, 200));
        manager.add(new Lens("Nikon", 4, 200));
    }

    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        distanceInM = distanceInM / 1000;
        return df.format(distanceInM);
    }


    public void show() {
        boolean isDone = false;
        while (!isDone) {
            System.out.println("Lenses to pick from: ");

            int counter = 0;
            for (Object l : manager) {
                System.out.println("  " + counter + ".  " + l);
                counter++;
            }
            System.out.println("(-1 to exit)\n");

            Scanner in = new Scanner(System.in);
            int choice = in.nextInt();

            if (choice == -1) {
                isDone = true;
                break;
            }
            else if (choice >= counter || choice < 0) {
                System.out.println("Error: Invalid lens index.");
                break;
            }

            Lens temp = manager.get(choice);
            System.out.println("Aperture [the F number]: ");
            double aperture = in.nextDouble();
            if (aperture < temp.getMaxAperture()) {
                System.out.println("ERROR: This aperture is not possible with this lens");
                break;
            }

            System.out.println("Distance to subject [m]: ");
            double subjectDistance = in.nextDouble();

            double focalLength = temp.getFocalLength();
            double[] calcValues = DOFCalculator.calcDOF(focalLength, aperture, subjectDistance, COC);

            System.out.println("  In focus: " +  formatM(calcValues[0])
                    + " to " + formatM(calcValues[1])
                    + " [DoF = " + formatM(calcValues[2]) + "]" );
            System.out.println("  Hyperfocal point: " + formatM(calcValues[3]));
        }
    }
}