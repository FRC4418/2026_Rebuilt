// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ManipulatorConstants;
import frc.robot.constants.ManipulatorConstants.ClimberConstants;
import frc.robot.constants.MotorConstants;

public class ClimberSubsystem extends SubsystemBase {
  /** Creates a new ClimberSubsystem. */
  private final TalonFX m_climbMotor = new TalonFX(MotorConstants.Climber.kMotorID);

  final MotionMagicVoltage m_climbRequest = new MotionMagicVoltage(ClimberConstants.kClimberDownPos);

  public ClimberSubsystem() {
    m_climbMotor.getConfigurator().apply(MotorConstants.Climber.config);
  }

  public void setClimberPos (double pos){
    m_climbMotor.setControl(m_climbRequest.withPosition(pos));
  }

  public void setClimberPercentOut (double out){
    m_climbMotor.setControl(new DutyCycleOut(out));
  }

  @Override
  public void periodic() {
    
    // This method will be called once per scheduler run
  }
}
