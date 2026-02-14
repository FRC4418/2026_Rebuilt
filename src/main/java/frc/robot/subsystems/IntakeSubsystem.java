// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ManipulatorConstants;
import frc.robot.constants.MotorConstants;

public class IntakeSubsystem extends SubsystemBase {

  private final TalonFX m_intakeMotor;
  private final TalonFX m_actuatorMotor;

  private final MotionMagicVelocityVoltage m_intakeRequest = new MotionMagicVelocityVoltage(0);
  private final MotionMagicVoltage m_actuatorRequest = new MotionMagicVoltage(0);

  public double downPos;
  public double upPos;
  /** Creates a new Intake. */
  // private final TalonFX m_leftIntakeMotor = new TalonFX(MotorConstants.Intake.kLeftMotorID);
  // private final TalonFX m_rightIntakeMotor = new TalonFX(MotorConstants.Intake.kRightMotorID);

  // final MotionMagicVelocityVoltage m_leftIntakeRequest = new MotionMagicVelocityVoltage(0);
  // final MotionMagicVelocityVoltage m_rightIntakeRequest = new MotionMagicVelocityVoltage(0);

  // private final TalonFX m_leftActuator = new TalonFX(MotorConstants.IntakeActuator.kLeftMotorID);
  // private final TalonFX m_rightActuator = new TalonFX(MotorConstants.IntakeActuator.kRightMotorID);

  // final MotionMagicVoltage m_leftActuatorRequest = new MotionMagicVoltage(0);
  // final MotionMagicVoltage m_rightActuatorRequest = new MotionMagicVoltage(0);
  /**
   * ---
   * <p>
   * ---
   * <p>
   * ---
   *
   * @param IntakeSide "L" = left or "R" = right
   * @return StatusCode of the set command
   */
  public IntakeSubsystem(int IntakeID, int ActuatorID, char IntakeSide) {
    m_intakeMotor = new TalonFX(IntakeID);
    m_actuatorMotor = new TalonFX(ActuatorID); 

    downPos = ManipulatorConstants.kIntakeDownPos;
    upPos = ManipulatorConstants.kIntakeUpPos;

    // if (IntakeSide == 'L' || IntakeSide == 'l') {
    //   downPos = ManipulatorConstants.kLeftIntakeDownPos;
    //   upPos = ManipulatorConstants.kLeftIntakeUpPos;
    // } else if (IntakeSide == 'R' || IntakeSide == 'r'){
    //   downPos = ManipulatorConstants.kRightIntakeDownPos;
    //   upPos = ManipulatorConstants.kRightIntakeUpPos;
    // }

    m_intakeMotor.getConfigurator().apply(MotorConstants.Intake.config);
    m_actuatorMotor.getConfigurator().apply(MotorConstants.IntakeActuator.config);

    // m_leftIntakeMotor.getConfigurator().apply(MotorConstants.Intake.config);
    // m_rightIntakeMotor.getConfigurator().apply(MotorConstants.Intake.config);

    // m_leftActuator.getConfigurator().apply(MotorConstants.IntakeActuator.config);
    // m_rightActuator.getConfigurator().apply(MotorConstants.IntakeActuator.config);
  }

  public void setIntakeVelocity(double speed){
    m_intakeMotor.setControl(m_intakeRequest.withVelocity(speed));
  }

  public void setActuator(double position) {
    m_actuatorMotor.setControl(m_actuatorRequest.withPosition(position));
  }

  public boolean checkActuator(double target) {
    return Math.abs(m_actuatorMotor.getRotorPosition().getValueAsDouble() - target) < ManipulatorConstants.kIntakeTolerance;
  }

  // public void setLeftVelocity(double speed){
  //   m_leftIntakeMotor.setControl(m_leftIntakeRequest.withVelocity(speed));
  // }

  // public void setRightVelocity(double speed){
  //   m_rightIntakeMotor.setControl(m_rightIntakeRequest.withVelocity(speed));
  // }

  // public void setLeft(double pos){
  //   m_leftActuator.setControl(m_leftActuatorRequest.withPosition(pos));
  // }

  // public void setRight(double pos){
  //   m_rightActuator.setControl(m_rightActuatorRequest.withPosition(pos));
  // }

  // public boolean isLeftUp(boolean isLeft) {
  //   if(isLeft){
  //     return Math.abs(m_leftActuator.getRotorPosition().getValueAsDouble() - ManipulatorConstants.kLeftIntakeUp) < ManipulatorConstants.kIntakeTolerance;
  //   } else {
  //     return Math.abs(m_rightActuator.getRotorPosition().getValueAsDouble() - ManipulatorConstants.kRightIntakeUp) < ManipulatorConstants.kIntakeTolerance;
  //   }
  // }

  // public boolean isLeftUp(){
  //   return isRightUp(false);
  // }

  // public boolean isRightUp(){
  //   return isLeftUp(false);
  // }

  // public boolean isRightUp(boolean isRight) {
  //   if(isRight){
  //     return Math.abs(m_rightActuator.getRotorPosition().getValueAsDouble() - ManipulatorConstants.kRightIntakeUp) < ManipulatorConstants.kIntakeTolerance;
  //   } else {
  //     return Math.abs(m_leftActuator.getRotorPosition().getValueAsDouble() - ManipulatorConstants.kLeftIntakeUp) < ManipulatorConstants.kIntakeTolerance;
  //   }
  // }

  // public boolean isActuatorUp(String side) {
  //   if(side.toLowerCase().endsWith("ft")) {
  //     return isLeftUp();
  //   } else if (side == "rigth") {
  //     return isActuatorUp("right");
  //   } else {
  //     return isRightUp();
  //   }
  // }

  // public boolean isOneDown() {
  //   int total=0;
  //   for (int i = 0; i < 20; i++) {
  //     if((Math.random()>.5) ? !isActuatorUp("left"):!isActuatorUp("rigth")){
  //       total++;
  //     } else {
  //       total--;
  //     }
  //   }
  //   if(total == 0){
  //     return isOneDown();
  //   } else if (total > 0) {
  //     return true;
  //   } else {
  //     return false;
  //   } 
  //}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
