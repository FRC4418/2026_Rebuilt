// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.constants.ManipulatorConstants;

/** Add your docs here. */
public class ShooterState {
    private Rotation2d turretHeading;
    private Rotation2d hoodAngle;

    public ShooterState(Rotation2d turretHeading, Rotation2d hoodAngle){
        this.turretHeading = turretHeading;
        this.hoodAngle = hoodAngle;
    }

    public ShooterState(Rotation2d hoodAngle){
        this(Rotation2d.kZero, hoodAngle);
    }

    public Rotation2d getTurretHeading(){
        return turretHeading;
    }

    public Rotation2d getHoodAngle(){
        return hoodAngle;
    }

    public void setTurretHeading(Rotation2d rot){
        if(ManipulatorConstants.kMinHoodAngle.getDegrees() < rot.getDegrees()) rot = ManipulatorConstants.kMinHoodAngle;
        if(ManipulatorConstants.kMaxHoodAngle.getDegrees() > rot.getDegrees()) rot = ManipulatorConstants.kMaxHoodAngle;
        turretHeading = rot;
    }

    public void setHoodAngle(Rotation2d rot){
        hoodAngle = rot;
    }
}
