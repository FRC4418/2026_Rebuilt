// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.MotorConstants;

public class ShooterSubsystem extends SubsystemBase {
  
  private final TalonFX m_shooterMotor = new TalonFX(MotorConstants.IDs.kFeederMotor);

  private final TalonFX m_feederMotor = new TalonFX(MotorConstants.IDs.kFeederMotor);

  final MotionMagicVelocityVoltage m_request = new MotionMagicVelocityVoltage(0);

  public ShooterSubsystem() {
    m_shooterMotor.getConfigurator().apply(MotorConstants.Configs.talonFXConfigs);
  }

  
  public void setVelocity(double speed){
    m_shooterMotor.setControl(m_request.withVelocity(speed));
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
