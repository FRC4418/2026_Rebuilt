// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.MotorConstants;

public class IndexerSubsystem extends SubsystemBase {
  private final TalonFX m_kickerMotor = new TalonFX(MotorConstants.Indexer.kKickerMotorID);
  private final TalonFX m_spindexerMotor = new TalonFX(MotorConstants.Indexer.kSpindexerMotorID);

  final MotionMagicVelocityVoltage m_spindexerRequest = new MotionMagicVelocityVoltage(0);
  final MotionMagicVelocityVoltage m_kickerRequest = new MotionMagicVelocityVoltage(0);

  public DigitalInput m_beamBreak = new DigitalInput(8);
  /** Creates a new IndexerSubsystem. */
  public IndexerSubsystem() {
    m_kickerMotor.getConfigurator().apply(MotorConstants.Indexer.kickerConfig);
    m_spindexerMotor.getConfigurator().apply(MotorConstants.Indexer.spindexerConfig);
  }

  public boolean getBeamBreak(){
    return m_beamBreak.get();
  }



  public void setSpindexerVel(double vel) {
    m_spindexerMotor.setControl(m_spindexerRequest.withVelocity(vel));
  }

  public void setKickerVel(double vel){
    m_kickerMotor.setControl(m_kickerRequest.withVelocity(vel));
  }

  public void setSpindexerPercent(double percent){
    m_spindexerMotor.setControl(new DutyCycleOut(percent));
  }

  public void setKickerPercent(double percent){
    m_kickerMotor.setControl(new DutyCycleOut(percent));
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Spindexer Current", m_spindexerMotor.getSupplyCurrent().getValueAsDouble());
    SmartDashboard.putNumber("Kicker Current", m_kickerMotor.getSupplyCurrent().getValueAsDouble());
    // This method will be called once per scheduler run
  }
}
