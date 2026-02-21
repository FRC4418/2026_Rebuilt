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
    // public Limelight limelight;
    public String name;
    
    public LimelightCamera(String name) {
        this.name = name;
        // limelight = new Limelight(name);
        // limelight.getSettings().withImuMode(ImuMode.ExternalImu).save();
        // limelight.getSettings().
        // estimator = limelight.createPoseEstimator(EstimationMode.MEGATAG1);
    }

    public PoseEstimate getLLHPose(double yaw, double yawRate){
        LimelightHelpers.SetRobotOrientation(name, yaw, yawRate, 0, 0, 0, 0);

        LimelightHelpers.PoseEstimate est = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(name);
        
        return est;
    }

    public void setIMUMode(int mode){
        LimelightHelpers.SetIMUMode(name, mode);
    }

    public void setCameraPoseRobotSpace(Pose3d pos){
        Rotation3d rot = pos.getRotation();
        LimelightHelpers.setCameraPose_RobotSpace(name, pos.getX(), pos.getY(), pos.getZ(), rot.getX(), rot.getY(), rot.getZ());
    }


    // public void updateRobotOrientation(Rotation3d rotation, double pitchVel, double rollVel, double yawVel) {
    //     limelight.getSettings().withRobotOrientation(
    //         new Orientation3d(
    //             rotation,
    //             new AngularVelocity3d(
    //                 DegreesPerSecond.of(pitchVel),
    //                 DegreesPerSecond.of(rollVel),
    //                 DegreesPerSecond.of(yawVel)
    //             )
    //         )
    //     )
    //     .save();
    // }
    
    // public Optional<PoseEstimate> getVisonPos() {
    //      // Get MegaTag2 pose
    //     // If the pose is present
    //     // return estimator.getAlliancePoseEstimate();
    //     // if(DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get().equals(Alliance.Blue)){
    //     //     return BotPose.BLUE.get(limelight);
    //     // }

    //     return BotPose.BLUE_MEGATAG2.get(limelight);
        
    // }

    // public void setIMUMode(ImuMode mode){
    //     limelight.getSettings().withImuMode(mode).save();
    // }

    // public int getNumTags() {
    //     // return (limelight.getLatestResults().isEmpty() ? 0 : limelight.getLatestResults().get().botpose_tagcount);
    //     var curPos = this.getVisonPos();
    //     if(curPos.isEmpty()) return 0;
    //     return curPos.get().tagCount;
    // }

    // public double getAvgArea() {
    //     // return (limelight.getLatestResults().isEmpty() ? 0 : limelight.getLatestResults().get().botpose_avgarea);
    //     var curPos = this.getVisonPos();
    //     if(curPos.isEmpty()) return 0;
    //     return curPos.get().avgTagArea;
    // }

    // public double getTotalArea(){
    //     return this.getAvgArea() * this.getNumTags();
    // }

}
