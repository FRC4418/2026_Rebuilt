// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ManipulatorConstants;
import frc.robot.constants.ManipulatorConstants.ShooterConstants;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.utils.TrajectoryCalculator;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AutoAim extends Command {
  private SwerveSubsystem m_swerveSubsystem;
  private ShooterSubsystem m_shooterSubsystem;
  private Pose2d targetPose;
  /** Creates a new AutoAim. */
  public AutoAim(SwerveSubsystem swerveSubsystem, ShooterSubsystem shooterSubsystem, Pose2d targetPose) {
    this.m_swerveSubsystem = swerveSubsystem;
    this.m_shooterSubsystem = shooterSubsystem;
    this.targetPose = targetPose;
    addRequirements(swerveSubsystem, shooterSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Pose2d globalRobotPose = m_swerveSubsystem.getPose().transformBy(new Transform2d(ShooterConstants.turretPose.toTranslation2d(), Rotation2d.kZero));
    // Transform2d localTagPose = targetPose.minus(globalRobotPose);

    Pose2d localTagPos = targetPose.relativeTo(globalRobotPose);

    Rotation2d turretRot = localTagPos.getTranslation().getAngle();

    double dist = localTagPos.getTranslation().getNorm();

    Rotation2d hoodRot = TrajectoryCalculator.getAngle(dist);

    double shooterExitVelMPS = TrajectoryCalculator.getVelocity(dist);

    double shooterTargetRPS = (ManipulatorConstants.ShooterConstants.tipSpeedToBallSpeed*shooterExitVelMPS) / (2 * Math.PI * ManipulatorConstants.ShooterConstants.wheelRadius);

    m_shooterSubsystem.setHoodPos(hoodRot);

    m_shooterSubsystem.setTurretPos(turretRot);

    m_shooterSubsystem.setShooterVel(shooterTargetRPS);

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
