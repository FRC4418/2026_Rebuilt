// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ManipulatorConstants.IntakeConstants;
import frc.robot.subsystems.IntakeSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ToggleIntake extends Command {
  IntakeSubsystem m_intakeSubsystem;
  boolean up;
  /** Creates a new ToggleIntake. */
  public ToggleIntake(IntakeSubsystem intakeSubsystem) {
    m_intakeSubsystem = intakeSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intakeSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    up = m_intakeSubsystem.getIntakePos() < IntakeConstants.kIntakeMiddlePos;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(up) {
      m_intakeSubsystem.setActuatorPos(IntakeConstants.kIntakeDownPos);
    } else {
      m_intakeSubsystem.setActuatorPos(IntakeConstants.kIntakeUpPos);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
