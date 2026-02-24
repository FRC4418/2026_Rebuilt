// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.MotorConstants;

public class IndexerSubsystem extends SubsystemBase {
  private final TalonFX m_indexerMotor = new TalonFX(MotorConstants.Indexer.kMotorID);

  final MotionMagicVelocityVoltage m_indexerRequest = new MotionMagicVelocityVoltage(0);

  public DigitalInput m_beamBreak = new DigitalInput(8);
  /** Creates a new IndexerSubsystem. */
  public IndexerSubsystem() {
    m_indexerMotor.getConfigurator().apply(MotorConstants.Shooter.config);
  }

  public boolean getBeamBreak(){
    return m_beamBreak.get();
  }

  public void setIndexerVelocity(double speed) {
    m_indexerMotor.setControl(m_indexerRequest.withVelocity(speed));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
