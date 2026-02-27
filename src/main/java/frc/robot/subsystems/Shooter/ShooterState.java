// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.Vector;
import frc.robot.constants.ManipulatorConstants;
import frc.robot.utils.TrajectoryCalculator;

/** Add your docs here. */
public class ShooterState {
    private Rotation2d turretHeading;
    private Rotation2d hoodAngle;
    private double shooterVel;

    public ShooterState(Rotation2d turretHeading, Rotation2d hoodAngle, double shooterVel){
        this.turretHeading = turretHeading;
        this.hoodAngle = hoodAngle;
        this.shooterVel = shooterVel;
        
    }

    public Rotation2d getTurretHeading(){
        return turretHeading;
    }

    public Rotation2d getHoodAngle(){
        return hoodAngle;
    }

    public double getShooterVel(){
        return shooterVel;
    }

    public void setShooterVel(double vel){
        shooterVel = vel;
    }

    public void setTurretHeading(Rotation2d rot){
        if(ManipulatorConstants.kMinHoodAngle.getDegrees() < rot.getDegrees()) rot = ManipulatorConstants.kMinHoodAngle;
        if(ManipulatorConstants.kMaxHoodAngle.getDegrees() > rot.getDegrees()) rot = ManipulatorConstants.kMaxHoodAngle;
        turretHeading = rot;
    }

    public void setHoodAngle(Rotation2d rot){
        hoodAngle = rot;
    }

    public void setTarget(Transform2d globalRobotPos, Transform2d globalTargetPos, ChassisSpeeds robotRelativeSpeeds){
        Transform2d localTargetPose = globalTargetPos.plus(globalTargetPos.inverse());

        double dist = localTargetPose.getTranslation().getNorm();
        Rotation2d angle = localTargetPose.getTranslation().getAngle();

        double timeOfFlight = 0;
        
    }

}
