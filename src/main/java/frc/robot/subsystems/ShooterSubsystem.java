// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ManipulatorConstants;
import frc.robot.constants.MotorConstants;

public class ShooterSubsystem extends SubsystemBase {
  
  private final TalonFX m_shooterMotor = new TalonFX(MotorConstants.Shooter.kShooterMotorID);
  private final TalonFX m_shooterMotorSlave = new TalonFX(MotorConstants.Shooter.kShooterSlaveID);
  private final TalonFX m_turretMotor = new TalonFX(MotorConstants.Shooter.kTurretMotorID);

  private final SparkMax m_hoodMotor = new SparkMax(MotorConstants.Shooter.kHoodMotorID, SparkLowLevel.MotorType.kBrushless);


  final MotionMagicVelocityVoltage m_shooterRequest = new MotionMagicVelocityVoltage(0);
  final MotionMagicVelocityVoltage m_feederRequest = new MotionMagicVelocityVoltage(0);

  final MotionMagicVoltage m_turretRequest = new MotionMagicVoltage(ManipulatorConstants.Shooter.kTurretDefaultPos);
  final SparkClosedLoopController m_hoodController;

  private final DigitalInput m_limitSwitch = new DigitalInput(3);
  
  public ShooterSubsystem() {
    m_shooterMotor.getConfigurator().apply(MotorConstants.Shooter.shooterConfig);
    m_shooterMotorSlave.setControl(new Follower(MotorConstants.Shooter.kShooterMotorID, MotorAlignmentValue.Aligned));
    m_turretMotor.getConfigurator().apply(MotorConstants.Shooter.turretConfig);

    m_hoodMotor.configure(
      MotorConstants.Shooter.hoodConfig,
      ResetMode.kResetSafeParameters,
      PersistMode.kPersistParameters
    );

    m_hoodController = m_hoodMotor.getClosedLoopController();

  }
  
  
  public void setShooterVel(double vel){
    m_shooterMotor.setControl(m_shooterRequest.withVelocity(vel));
  }

  public void setHoodPos(double numRotations){
    m_hoodController.setSetpoint(numRotations, SparkBase.ControlType.kMAXMotionPositionControl);
  }

  public void setHoodPos(Rotation2d pos){
    
    Rotation2d clamped = Rotation2d.fromDegrees(Math.min(ManipulatorConstants.Shooter.minHoodDegrees, Math.min(ManipulatorConstants.Shooter.maxHoodDegrees, pos.getDegrees())));
    
    double rotations = clamped.getRotations();

    setHoodPos(rotations*ManipulatorConstants.Shooter.hoodRatio);
  }

  public void setTurretPos(Rotation2d pos){
    double clamped = Math.min(Math.max(pos.getDegrees(), ManipulatorConstants.Shooter.minTurretDegrees), ManipulatorConstants.Shooter.maxTurretDegrees);
    m_turretMotor.setControl(m_turretRequest.withPosition(clamped * (ManipulatorConstants.Shooter.turretRatio/360)));
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(m_limitSwitch.get()){
      m_hoodMotor.getEncoder().setPosition(0);
    } 
  }
}
