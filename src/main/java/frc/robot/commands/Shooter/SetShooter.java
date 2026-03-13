// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class SetShooter extends Command {
  private ShooterSubsystem m_shooterSubsystem;

  Rotation2d turretPos;
  double hoodPos;
  double shooterSpeed;

  /** Creates a new SetShooterState. */
  public SetShooter(ShooterSubsystem shooterSubsystem, Rotation2d turretPos, double hoodPos, double shooterSpeed) {
    this.m_shooterSubsystem = shooterSubsystem;
    this.turretPos = turretPos;
    this.hoodPos = hoodPos;
    this.shooterSpeed = shooterSpeed;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooterSubsystem);
  }

  public SetShooter(ShooterSubsystem shooterSubsystem, double hoodPos, double shooterSpeed){
    this(shooterSubsystem, Rotation2d.kZero, hoodPos, shooterSpeed);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooterSubsystem.setTurretPos(turretPos);
    m_shooterSubsystem.setHoodPos(hoodPos);
    m_shooterSubsystem.setShooterVel(shooterSpeed);
    // m_shooterSubsystem.setShooterPercentOut(shooterSpeed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
