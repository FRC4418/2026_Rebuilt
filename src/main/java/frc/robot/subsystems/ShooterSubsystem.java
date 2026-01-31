// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ManipulatorConstants;
import frc.robot.Constants.MotorConstants;

public class ShooterSubsystem extends SubsystemBase {
  
  private final TalonFX m_shooterMotor = new TalonFX(MotorConstants.Shooter.kMotorID);
  private final TalonFX m_shooterMotorSlave = new TalonFX(MotorConstants.Shooter.kSlaveID);

  private final TalonFX m_turretMotor = new TalonFX(MotorConstants.Turret.kMotorID);
  
  private final TalonFX m_feederMotor = new TalonFX(MotorConstants.Feeder.kMotorID);
  private final TalonFX m_hoodMotor = new TalonFX(MotorConstants.Hood.kMotorID);
  
  final MotionMagicVelocityVoltage m_shooterRequest = new MotionMagicVelocityVoltage(0);
  final MotionMagicVelocityVoltage m_feederRequest = new MotionMagicVelocityVoltage(0);
  final MotionMagicVoltage m_hoodRequest = new MotionMagicVoltage(ManipulatorConstants.kHoodDefaultPos);
  final MotionMagicVoltage m_turretRequest = new MotionMagicVoltage(ManipulatorConstants.kTurretDefaultPos);
  
  public ShooterSubsystem() {
    m_shooterMotor.getConfigurator().apply(MotorConstants.Shooter.config);
    m_feederMotor.getConfigurator().apply(MotorConstants.Feeder.config);
    m_hoodMotor.getConfigurator().apply(MotorConstants.Feeder.config);
    m_shooterMotorSlave.setControl(new Follower(MotorConstants.Shooter.kMotorID, MotorAlignmentValue.Aligned));
    m_turretMotor.getConfigurator().apply(MotorConstants.Turret.config);
  }
  
  
  public void setShooterVelocity(double speed){
    m_shooterMotor.setControl(m_shooterRequest.withVelocity(speed));
  }
  
  public void setFeederVelocity(double speed){
    m_feederMotor.setControl(m_feederRequest.withVelocity(speed));
  }

  public void setHoodPosition (double pos){
    m_hoodMotor.setControl(m_hoodRequest.withPosition(pos));
  }

  public void setTurretPos (Rotation2d pos){
    m_turretMotor.setControl(m_turretRequest.withPosition(pos.getDegrees() * (ManipulatorConstants.turretRatio/360)));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
