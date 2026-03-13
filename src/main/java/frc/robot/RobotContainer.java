// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;
import java.io.IOException;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;
import org.json.simple.parser.ParseException;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.Climber.SetClimber;
import frc.robot.commands.Climber.SetClimberPercent;
import frc.robot.commands.Indexer.IndexerDefault;
import frc.robot.commands.Indexer.SetIndexer;
import frc.robot.commands.Intake.IntakeDefault;
import frc.robot.commands.Intake.SetIntake;
import frc.robot.commands.Shooter.SetShooter;
import frc.robot.commands.Shooter.ShooterDefault;
import frc.robot.commands.Shooter.TestHood;
import frc.robot.constants.ManipulatorConstants;
import frc.robot.constants.ManipulatorConstants.ClimberConstants;
import frc.robot.constants.ManipulatorConstants.IndexerConstants;
import frc.robot.constants.ManipulatorConstants.IntakeConstants;
import frc.robot.constants.ManipulatorConstants.ShooterConstants;
import frc.robot.commands.Shooter.AutoAim;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import swervelib.SwerveInputStream;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
  private IndexerSubsystem m_indexerSubsystem = new IndexerSubsystem();
  private ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
  private ClimberSubsystem m_climberSubsystem = new ClimberSubsystem();

  private SendableChooser<Command> chooser = new SendableChooser<Command>();

  private final CommandXboxController m_driverController = new CommandXboxController(0);

  // ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem(); 

  SwerveSubsystem m_swerveSubsystem = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),"swerve"));

  SwerveInputStream driveAngularVelocity = SwerveInputStream.of(m_swerveSubsystem.getSwerveDrive(),
                                                                () -> m_driverController.getLeftY() * -1,
                                                                () -> m_driverController.getLeftX() * -1)
                                                            .withControllerRotationAxis(() -> m_driverController.getRightX())
                                                            .deadband(0.1)
                                                            .scaleTranslation(0.8)
                                                            .allianceRelativeControl(true);

  Command driveFieldOrientedAnglularVelocity = m_swerveSubsystem.driveFieldOriented(driveAngularVelocity);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    setDefaultCommands(); 
    configureBindings();
    addAutoOptions();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */

  private void setDefaultCommands(){
    m_swerveSubsystem.setDefaultCommand(driveFieldOrientedAnglularVelocity);
    m_intakeSubsystem.setDefaultCommand(new IntakeDefault(m_intakeSubsystem));
    m_indexerSubsystem.setDefaultCommand(new IndexerDefault(m_indexerSubsystem));
    m_shooterSubsystem.setDefaultCommand(new ShooterDefault(m_shooterSubsystem));
    m_climberSubsystem.setDefaultCommand(new SetClimberPercent(m_climberSubsystem, 0));

  }

  private void configureBindings() {


    
    SmartDashboard.putNumber("Shooter Vel Auto", 0);
    SmartDashboard.putNumber("Shooter Pos Auto", 0);
    DriverStation.silenceJoystickConnectionWarning(true);

    m_driverController.leftTrigger().whileTrue(new SetIntake(m_intakeSubsystem, 1, 3.6));
    m_driverController.b().toggleOnTrue(new SetIntake(m_intakeSubsystem, 0, IntakeConstants.kIntakeDownPos));

    m_driverController.y().whileTrue(new SetShooter(m_shooterSubsystem, 4.2, 70));
    m_driverController.x().whileTrue(new AutoAim(m_swerveSubsystem, m_shooterSubsystem, new Pose2d(ShooterConstants.blueHub, Rotation2d.kZero)));

    m_driverController.rightTrigger().whileTrue(new SetIndexer(m_indexerSubsystem, IndexerConstants.kKickerSpeed, IndexerConstants.kSpindexerSpeed));

    m_driverController.povUp().whileTrue(new TestHood(m_shooterSubsystem, 0.3));
    m_driverController.povDown().whileTrue(new TestHood(m_shooterSubsystem, -.3));

    m_driverController.a().onTrue(new InstantCommand(() -> m_swerveSubsystem.zeroGyro()));

    SmartDashboard.putData("Zero Gyro", new InstantCommand( () -> m_swerveSubsystem.zeroGyro() ));
  }

  public void addAutoOptions(){
    chooser.setDefaultOption("Nothing", new InstantCommand());
    chooser.addOption("New Path", getTestCommand());
    chooser.addOption("basic auto", basicShoot());

    SmartDashboard.putData("Auto Selector", chooser);
  }

  public PathPlannerPath getPath(String name){
    try {
      var path =  PathPlannerPath.fromPathFile(name);


      var alliance = DriverStation.getAlliance();
      if (alliance.isPresent()) {
        if (alliance.get() == DriverStation.Alliance.Red) return path.flipPath();
      }

      return path;

    } catch (FileVersionException | IOException | ParseException e) {
      return null;
    }
  }

  public Command basicShoot(){
    Command warmupShooter = new SetShooter(m_shooterSubsystem, 4.2, .65).raceWith(new WaitCommand(2));

    Command shoot = new SetShooter(m_shooterSubsystem, 4.2, .65).alongWith(new SetIndexer(m_indexerSubsystem, -1,-1));

    return warmupShooter.andThen(shoot);
  }

  public Command getTestCommand(){

    PathPlannerPath path = getPath("New Path");

    Command drivePath = AutoBuilder.followPath(path);

    Command resetPose = new InstantCommand(() -> m_swerveSubsystem.resetOdometry(path.getStartingHolonomicPose().get()));

    return resetPose.andThen(drivePath);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return chooser.getSelected();
  }
}
