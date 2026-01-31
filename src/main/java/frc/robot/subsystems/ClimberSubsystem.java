// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ManipulatorConstants;
import frc.robot.Constants.MotorConstants;

public class ClimberSubsystem extends SubsystemBase {
  /** Creates a new ClimberSubsystem. */
  private final TalonFX m_climbMotor = new TalonFX(MotorConstants.Climber.kMotorID);

  final MotionMagicVoltage m_climbRequest = new MotionMagicVoltage(ManipulatorConstants.kClimberDownPos);

  public ClimberSubsystem() {
    m_climbMotor.getConfigurator().apply(MotorConstants.Climber.config);
  }

  public void setClimber (double pos){
    m_climbMotor.setControl(m_climbRequest.withPosition(pos));
  }

  @Override
  public void periodic() {
    
    // This method will be called once per scheduler run
  }
}
