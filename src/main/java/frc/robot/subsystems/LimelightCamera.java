// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.units.Units.DegreesPerSecond;

import java.util.Optional;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.constants.DriveConstants;
import frc.robot.utils.LimelightHelpers;
import frc.robot.utils.LimelightHelpers.PoseEstimate;
import limelight.Limelight;



/** Add your docs here. */
public class LimelightCamera {
    public String name;
    
    public LimelightCamera(String name) {
        this.name = name;
    }

    public PoseEstimate getLLHPose(double yaw, double yawRate){
        LimelightHelpers.SetRobotOrientation(name, yaw, yawRate, 0, 0, 0, 0);

        // System.out.println("Set the yaw of " + name + " to " + yaw);

        LimelightHelpers.PoseEstimate est = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(name);
        
        return est;
    }

    public PoseEstimate getLLHPoseMT1(){
        return LimelightHelpers.getBotPoseEstimate_wpiBlue(name);
    }

    public void setPipeline(int pipeline){
        LimelightHelpers.setPipelineIndex(name, pipeline);
    }

    public void setIMUMode(int mode){
        LimelightHelpers.SetIMUMode(name, mode);
    }

    public void setCameraPoseRobotSpace(Pose3d pos){
        Rotation3d rot = pos.getRotation();
        LimelightHelpers.setCameraPose_RobotSpace(name, pos.getX(), pos.getY(), pos.getZ(), rot.getX(), rot.getY(), rot.getZ());
    }
}
