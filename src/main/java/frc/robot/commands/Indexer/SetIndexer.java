// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Indexer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ManipulatorConstants.IndexerConstants;
import frc.robot.subsystems.IndexerSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class SetIndexer extends Command {
  private IndexerSubsystem m_indexerSubsystem;
  private double kickerPercent;
  private double spindexerPercent;
  /** Creates a new SetIndexer. */
  public SetIndexer(IndexerSubsystem indexerSubsystem, double kickerPercent, double spindexerPercent) {
    m_indexerSubsystem = indexerSubsystem;
    this.kickerPercent = kickerPercent;
    this.spindexerPercent = spindexerPercent;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(indexerSubsystem);
  }

  public SetIndexer(IndexerSubsystem indexerSubsystem){
    this(indexerSubsystem, IndexerConstants.kKickerSpeed, IndexerConstants.kSpindexerSpeed);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // m_indexerSubsystem.setKickerVel(kickerVel);
    // m_indexerSubsystem.setSpindexerVel(spindexerVel);
    m_indexerSubsystem.setSpindexerPercent(SmartDashboard.getNumber("spindexer speed", -1));
    m_indexerSubsystem.setKickerPercent(kickerPercent);
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
