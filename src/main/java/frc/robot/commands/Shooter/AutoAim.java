// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.ManipulatorConstants.LEDConstants;
import frc.robot.constants.ManipulatorConstants.ShooterConstants;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.utils.TrajectoryCalculator;
import swervelib.SwerveInputStream;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AutoAim extends Command {
  private SwerveSubsystem m_swerveSubsystem;
  private ShooterSubsystem m_shooterSubsystem;
  private Pose2d targetPose;
  private SwerveInputStream input;
  private DoubleSupplier x;
  private DoubleSupplier y;
  private CommandXboxController controller;
  private LEDSubsystem LEDs;

  private PIDController rotationPID = new PIDController(4, 1.2, 0.2);

  /** Creates a new AutoAim. */
  public AutoAim(SwerveSubsystem swerveSubsystem, DoubleSupplier x, DoubleSupplier y, ShooterSubsystem shooterSubsystem, Supplier<Pose2d> targetPose, CommandXboxController controller) {
    this.m_swerveSubsystem = swerveSubsystem;
    this.m_shooterSubsystem = shooterSubsystem;
    this.targetPose = targetPose.get();
    this.x = x;
    this.y = y;
    // this.LEDs = LEDs;

    this.controller = controller;

    this.input = SwerveInputStream.of(m_swerveSubsystem.getSwerveDrive(), x, y);

    
    addRequirements(swerveSubsystem, shooterSubsystem, LEDs);
    // Use addRequirements() here to declare subsystem dependencies.
    if(DriverStation.getAlliance().isEmpty()) return;

    if(DriverStation.getAlliance().get().equals(Alliance.Red)){

    }
  }

  public AutoAim(SwerveSubsystem swerveSubsystem, ShooterSubsystem shooterSubsystem, Supplier<Pose2d> targetPose){
    this(swerveSubsystem, () -> 0, () -> 0, shooterSubsystem, targetPose, null);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    rotationPID.setSetpoint(SmartDashboard.getNumber("shooting angle offset", 0));

    targetPose = (DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get().equals(Alliance.Red)) ? new Pose2d(ShooterConstants.redHub, Rotation2d.k180deg) : new Pose2d(ShooterConstants.blueHub, Rotation2d.kZero);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {



    
    
    Pose2d globalTurretPose = m_swerveSubsystem.getPose().transformBy(new Transform2d(ShooterConstants.turretPose.toTranslation2d(), Rotation2d.k180deg));
    // Transform2d localTagPose = targetPose.minus(globalRobotPose);
    // Pose2d globalRobotPose = m_shooterSubsystem.turretCamera.getLLHPose(m_swerveSubsystem.getYaw(), m_swerveSubsystem.getYawRate()).pose;
    
    // var globalRobotEstimate = LimelightHelpers.getBotPoseEstimate_wpiBlue("limelight-turret");
    // if(globalRobotEstimate.tagCount < 1){
    //   return;
    // }

    // Pose2d globalRobotPose = globalRobotEstimate.pose;

    if(globalTurretPose == null){
      return;
    }

    Pose2d localTargetPos = targetPose.relativeTo(globalTurretPose);
    
    // Rotation2d turretRot = localTagPos.getTranslation().getAngle();
    
    Logger.recordOutput("Shooting/angle", localTargetPos.getTranslation().getAngle());

  

    // m_swerveSubsystem.driveFieldOriented(input.withControllerRotationAxis(() -> -rotationPID.calculate(localTargetPos.getTranslation().getAngle().getRadians())));
    
    m_swerveSubsystem.drive(new Translation2d(x.getAsDouble()*DriveConstants.AutoAimSpeedScale, y.getAsDouble()*DriveConstants.AutoAimSpeedScale), -rotationPID.calculate(localTargetPos.getTranslation().getAngle().getRadians()), true);


    double dist = localTargetPos.getTranslation().getNorm();

    
    double shooterPos = TrajectoryCalculator.getAngle(dist);
    double shooterVel = TrajectoryCalculator.getVelocity(dist);
    
    
    Logger.recordOutput("Shooting/Target Pos", targetPose);
    Logger.recordOutput("Shooting/Turret Pos", globalTurretPose);
    Logger.recordOutput("Shooting/dist", dist);

    // Rotation2d hoodRot = TrajectoryCalculator.getAngle(dist);

    // double shooterExitVelMPS = TrajectoryCalculator.getVelocity(dist);

    // double shooterTargetRPS = (ManipulatorConstants.ShooterConstants.tipSpeedToBallSpeed*shooterExitVelMPS) / (2 * Math.PI * ManipulatorConstants.ShooterConstants.wheelRadius);

    //m_shooterSubsystem.setHoodPos(SmartDashboard.getNumber("Shooter Pos Auto", 0));
    //m_shooterSubsystem.setShooterVel(SmartDashboard.getNumber("Shooter Vel Auto", 0));
    m_shooterSubsystem.setHoodPos(shooterPos + SmartDashboard.getNumber("hood offset", 0));
    m_shooterSubsystem.setShooterVel(shooterVel + SmartDashboard.getNumber("speed offset", 0));

    // Logger.recordOutput("target shooter pos", shooterPos);
    // Logger.recordOutput("target shooter vel", shooterVel);

    // m_shooterSubsystem.setTurretPos(0);

    // if(!(controller == null)){
    //   if(withinRange(m_shooterSubsystem.getShooterVel(), shooterVel, 5) && withinRange(localTargetPos.getTranslation().getAngle().getRadians(), rotationPID.getSetpoint(), 0.067)){
    //     controller.setRumble(RumbleType.kBothRumble, 1);
    //     LEDs.setPattern(LEDConstants.green);
    //   } else {
    //     controller.setRumble(RumbleType.kBothRumble, 0);
    //     LEDs.setPattern(LEDConstants.red);
    //   }
    // }

  }

  private boolean withinRange(double value, double targetValue, double range){
    if(Math.abs(value-targetValue) <= range){
      return true;
    }
    return false;
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // LEDs.setEnabledPattern(LEDConstants.enabledPattern); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
