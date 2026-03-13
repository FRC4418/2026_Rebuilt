package frc.robot.utils;

import edu.wpi.first.math.geometry.Rotation2d;
import limelight.Limelight;

public class TrajectoryCalculator{
    // private static Limelight limelight = new Limelight("left");

    // private static final double[][] optimalPoses = {
    //     // numbers from https://github.com/LukeG65536/TrajectoryCalculator in findline.py
    //     //dist(m),theta(rad),vel(m/x)double[][] optimalPoses = {
    //     {0.34967704029203567,1.4996,5.892},
    //     {0.5432070166537415,1.4596,5.9},
    //     {0.7347247384932435,1.4196,5.916},
    //     {0.928524688936229,1.3796,5.948},
    //     {1.1245443188870148,1.3396,5.992},
    //     {1.323794858579927,1.2996,6.048},
    //     {1.530547130243833,1.2596,6.12},
    //     {1.7426736935407168,1.2196,6.204},
    //     {1.9690019043143145,1.1796,6.308},
    //     {2.2164929633592623,1.1396000000000002,6.436},
    //     {2.513570595361052,1.0996000000000001,6.6080000000000005},
    //     {2.9005732664613344,1.0596,6.848},
    //     {3.4018739135259652,1.0196,7.164},
    //     {4.8365241187109165,0.9816,8.072},
    //     {5.542080347634744,0.9524,8.472},
    //     {6.267281698375618,0.9296,8.872},
    //     {7.0160237771441745,0.912,9.272},
    //     {7.791965336650913,0.8976,9.672},
    //     {8.596524891673093,0.8855999999999999,10.072},
    //     {9.430471937758456,0.8756,10.472000000000001},
    //     {10.29443704973485,0.8672,10.872},
    //     {11.188926083229953,0.8600000000000001,11.272},
    //     {12.114344255295402,0.8536,11.672},
    //     {13.07079076721791,0.8480000000000001,12.072},
    //     {14.058367753945848,0.8432,12.472000000000001},
    //     {15.077168401022712,0.8392,12.872},
    //     {16.127643062855206,0.8351999999999999,13.272},
    //     {17.20962803435578,0.8316,13.672},
    //     {18.323017358689984,0.8288,14.072000000000001},
    //     {19.468174662075285,0.8260000000000001,14.472},
    //     {20.64497153637478,0.8236,14.872},
    // };

    private static final double optimalPoses[][] = {
        {1.65, 2.9, 50},
        {2.09, 3.35, 53.67},
        {2.36, 4.1, 49},
        {2.88, 4.5, 53},
        {3.33, 5.05, 57},
        {3.5, 5.05, 58},
        {3.8, 5.1, 60},
        {4.5, 5.3, 67},
        {6.1, 5.3, 73},
    };

    public static double getAngle(double dist){
        int i;
        for(i = 0; i < optimalPoses.length-2; i++){
            if(optimalPoses[i][0] > dist) break;//have dist be inbetween i and i+1
        }//then to linear aproximation between i and i + 1
        return interpolate(dist, optimalPoses[i][0],optimalPoses[i+1][0],optimalPoses[i][1],optimalPoses[i+1][1]);
    }

    public static double getVelocity(double dist){
        int i;
        for(i = 0; i < optimalPoses.length-2; i++){
            if(optimalPoses[i][0] > dist) break;
        }
        return interpolate(dist, optimalPoses[i][0],optimalPoses[i+1][0],optimalPoses[i][2],optimalPoses[i+1][2]);
    }


    private static double interpolate(double xTarget, double x1, double x2, double y1, double y2){
        return ((y2-y1)/(x2-x1))*(xTarget-x1)+y1;//point slope
    }
}