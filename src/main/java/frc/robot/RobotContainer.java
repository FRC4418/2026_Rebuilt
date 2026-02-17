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
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.Shooter.Spin;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.utils.TrajectoryCalculator;
import swervelib.SwerveInputStream;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  private SendableChooser<Command> chooser = new SendableChooser<Command>();

  private final CommandXboxController m_driverController = new CommandXboxController(0);

  // ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem(); 

  SwerveSubsystem m_swerveSubsystem = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),"swerve"));

  SwerveInputStream driveAngularVelocity = SwerveInputStream.of(m_swerveSubsystem.getSwerveDrive(),
                                                                () -> m_driverController.getLeftY() * -1,
                                                                () -> m_driverController.getLeftX() * -1)
                                                            .withControllerRotationAxis(() -> m_driverController.getRightX() * -1)
                                                            .deadband(0.1)
                                                            .scaleTranslation(0.8)
                                                            .allianceRelativeControl(true);

  Command driveFieldOrientedAnglularVelocity = m_swerveSubsystem.driveFieldOriented(driveAngularVelocity);

  //IntakeSubsystem m_leftIntakeSubsystem = new IntakeSubsystem(MotorConstants.Intake.kLeftMotorID, MotorConstants.IntakeActuator.kLeftMotorID, 'L');
  //IntakeSubsystem m_rightIntakeSubsystem = new IntakeSubsystem(MotorConstants.Intake.kRightMotorID, MotorConstants.IntakeActuator.kRightMotorID, 'R');

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    m_swerveSubsystem.setDefaultCommand(driveFieldOrientedAnglularVelocity);
 
    //m_shooterSubsystem.setDefaultCommand(new ShooterDefault(m_shooterSubsystem));
    //m_leftIntakeSubsystem.setDefaultCommand(new IntakeDefault(m_leftIntakeSubsystem));
    //m_rightIntakeSubsystem.setDefaultCommand(new IntakeDefault(m_rightIntakeSubsystem));
    // Configure the trigger bindings
    // SmartDashboard.putNumber("shooter speed", 0);
    configureBindings();
    // m_shooterSubsystem.setDefaultCommand(new Spin(m_shooterSubsystem, () -> 0));

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
  private void configureBindings() {
    //m_driverController.povLeft().onTrue(new SwitchToLeft(m_leftIntakeSubsystem, m_rightIntakeSubsystem));
    //m_driverController.povRight().onTrue(new SwitchToRight(m_leftIntakeSubsystem, m_rightIntakeSubsystem));

    // m_driverController.b().whileTrue(new Spin(m_shooterSubsystem, () -> SmartDashboard.getNumber("shooter speed", -60)));

    m_driverController.a().onTrue(new InstantCommand(() -> m_swerveSubsystem.zeroGyro()));
    m_driverController.x().onTrue(new InstantCommand(() -> m_swerveSubsystem.resetOdometry(new Pose2d())));
  }

  public void addAutoOptions(){
    chooser.setDefaultOption("New Path", getTestCommand());
    chooser.addOption("New Path", getTestCommand());

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
