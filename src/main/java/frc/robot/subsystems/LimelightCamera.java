// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Optional;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation3d;
import limelight.Limelight;

import limelight.networktables.AngularVelocity3d;
import limelight.networktables.LimelightResults;
import limelight.networktables.Orientation3d;


/** Add your docs here. */
public class LimelightCamera {
    Limelight limelight;
    public LimelightCamera(String name) {
        limelight = new Limelight(name);
    }

public void updateRobotOrientation(Rotation3d rotation, 
                                   double pitchVel, 
                                   double rollVel, 
                                   double yawVel) {

    limelight.getSettings()
        .withRobotOrientation(
            new Orientation3d(
                rotation,
                new AngularVelocity3d(
                    Units.DegreesPerSecond.of(pitchVel),
                    Units.DegreesPerSecond.of(rollVel),
                    Units.DegreesPerSecond.of(yawVel)
                )
            )
        )
        .save();
}
    
    public Optional<Pose2d> getVisonPos() {
         // Get MegaTag2 pose
        Optional<PoseEstimate> visionEstimate = limelight.createPoseEstimator(EstimationMode.MEGATAG2).getPoseEstimate();
        // If the pose is present
        return visionEstimate;
    }

    public int getNumTags() {
        return limelight.getLatestResults().botpose_tagcount;
    }

    public double getAvgArea() {
        return limelight.getLatestResults().botpose_avgarea;
    }




        // visionEstimate.ifPresent((PoseEstimate poseEstimate) -> {
        // // Add it to the pose estimator.
        //     poseEstimator.addVisionMeasurement(poseEstimate.pose.toPose2d(), poseEstimate.timestampSeconds);
        // });

}
