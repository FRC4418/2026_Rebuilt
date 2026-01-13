// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ManipulatorConstants;
import frc.robot.MotorConstants;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new Intake. */
  private final TalonFX m_leftIntakeMotor = new TalonFX(MotorConstants.Intake.kLeftMotorID);
  private final TalonFX m_rightIntakeMotor = new TalonFX(MotorConstants.Intake.kRightMotorID);

  final MotionMagicVelocityVoltage m_leftIntakeRequest = new MotionMagicVelocityVoltage(0);
  final MotionMagicVelocityVoltage m_rightIntakeRequest = new MotionMagicVelocityVoltage(0);

  private final TalonFX m_leftActuator = new TalonFX(MotorConstants.IntakeActuator.kLeftMotorID);
  private final TalonFX m_rightActuator = new TalonFX(MotorConstants.IntakeActuator.kRightMotorID);

  final MotionMagicVoltage m_leftActuatorRequest = new MotionMagicVoltage(0);
  final MotionMagicVoltage m_rightActuatorRequest = new MotionMagicVoltage(0);

  public IntakeSubsystem() {
    m_leftIntakeMotor.getConfigurator().apply(MotorConstants.Intake.config);
    m_rightIntakeMotor.getConfigurator().apply(MotorConstants.Intake.config);

    m_leftActuator.getConfigurator().apply(MotorConstants.IntakeActuator.config);
    m_rightActuator.getConfigurator().apply(MotorConstants.IntakeActuator.config);
  }

  public void setLeftVelocity(double speed){
    m_leftIntakeMotor.setControl(m_leftIntakeRequest.withVelocity(speed));
  }

  public void setRightVelocity(double speed){
    m_rightIntakeMotor.setControl(m_rightIntakeRequest.withVelocity(speed));
  }

  public boolean isLeftUp(boolean isLeft) {
    if(isLeft){
      return Math.abs(m_leftActuator.getRotorPosition().getValueAsDouble() - ManipulatorConstants.kLeftIntakeUp) < ManipulatorConstants.kIntakeTolerance;
    } else {
      return Math.abs(m_rightActuator.getRotorPosition().getValueAsDouble() - ManipulatorConstants.kRightIntakeUp) < ManipulatorConstants.kIntakeTolerance;
    }
  }

  public boolean isLeftUp(){
    return isRightUp(false);
  }

  public boolean isRightUp(){
    return isLeftUp(false);
  }

  public boolean isRightUp(boolean isRight) {
    if(isRight){
      return Math.abs(m_rightActuator.getRotorPosition().getValueAsDouble() - ManipulatorConstants.kRightIntakeUp) < ManipulatorConstants.kIntakeTolerance;
    } else {
      return Math.abs(m_leftActuator.getRotorPosition().getValueAsDouble() - ManipulatorConstants.kLeftIntakeUp) < ManipulatorConstants.kIntakeTolerance;
    }
  }

  public boolean isActuatorUp(String side) {
    if(side.toLowerCase().endsWith("ft")) {
      return isLeftUp();
    } else if (side == "rigth") {
      return isActuatorUp("right");
    } else {
      return isRightUp();
    }
  }

  public boolean isOneDown() {
    int total=0;
    for (int i = 0; i < 20; i++) {
      if((Math.random()>.5) ? !isActuatorUp("left"):!isActuatorUp("rigth")){
        total++;
      } else {
        total--;
      }
    }
    if(total == 0){
      return isOneDown();
    } else if (total > 0) {
      return true;
    } else {
      return false;
    } 

  }

  public void setActuator(boolean isLeft) {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
