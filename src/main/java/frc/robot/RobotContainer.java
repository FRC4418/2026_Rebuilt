// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.Climber.SetClimberPercent;
import frc.robot.commands.Indexer.IndexerDefault;
import frc.robot.commands.Indexer.SetIndexer;
import frc.robot.commands.Intake.IntakeDefault;
import frc.robot.commands.Intake.JiggleIntake;
import frc.robot.commands.Intake.SetIntake;
import frc.robot.commands.Intake.SetIntakePercent;
import frc.robot.commands.Intake.ToggleIntake;
import frc.robot.commands.Shooter.AutoAim;
import frc.robot.commands.Shooter.SetShooter;
import frc.robot.commands.Shooter.ShooterDefault;
import frc.robot.constants.ManipulatorConstants;
import frc.robot.constants.ManipulatorConstants.IndexerConstants;
import frc.robot.constants.ManipulatorConstants.IntakeConstants;
import frc.robot.constants.ManipulatorConstants.ShooterConstants;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
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
  // private ClimberSubsystem m_climberSubsystem = new ClimberSubsystem();
  private LEDSubsystem m_LedSubsystem = new LEDSubsystem();

  private SendableChooser<Command> chooser = new SendableChooser<Command>();

  private final CommandXboxController m_driverController = new CommandXboxController(0);

  private Supplier<Pose2d> targetPose;
  

  // ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem(); 

  SwerveSubsystem m_swerveSubsystem = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),"swerve"));

  SwerveInputStream driveAngularVelocity = SwerveInputStream.of(m_swerveSubsystem.getSwerveDrive(),
                                                                () -> m_driverController.getLeftY() * -1,
                                                                () -> m_driverController.getLeftX() * -1)
                                                            .withControllerRotationAxis(() -> -m_driverController.getRightX())
                                                            .deadband(0.1)
                                                            .scaleTranslation(0.8)
                                                            .allianceRelativeControl(true);

  Command driveFieldOrientedAnglularVelocity = m_swerveSubsystem.driveFieldOriented(driveAngularVelocity);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    targetPose = () -> (DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get().equals(Alliance.Red)) ? new Pose2d(ShooterConstants.redHub, Rotation2d.k180deg) : new Pose2d(ShooterConstants.blueHub, Rotation2d.kZero);;
      
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
    // m_LedSubsystem.setDefaultCommand(new LEDSubsystem(m_LedSubsystem));
    // m_climberSubsystem.setDefaultCommand(new SetClimberPercent(m_climberSubsystem, 0));

  }

  private void configureBindings() {


    
    SmartDashboard.putNumber("Shooter Vel Auto", 0);
    SmartDashboard.putNumber("Shooter Pos Auto", 0);
    SmartDashboard.putNumber("shooting angle offset", -0.035);
    SmartDashboard.putNumber("hood offset", -0.9);
    SmartDashboard.putNumber("speed offset", 3);
    SmartDashboard.putNumber("spindexer speed", -.5);
    SmartDashboard.putBoolean("red", false);
    DriverStation.silenceJoystickConnectionWarning(true);

    m_driverController.leftTrigger().whileTrue(new SetIntakePercent(m_intakeSubsystem));
    // m_driverController.b().onTrue(new SetIntake(m_intakeSubsystem, 0, IntakeConstants.kIntakeDownPos));
    m_driverController.b().whileTrue(new ToggleIntake(m_intakeSubsystem));
    m_driverController.y().onTrue(new SetIntake(m_intakeSubsystem, 0, IntakeConstants.kIntakeUpPos));

    m_driverController.x().whileTrue(new AutoAim(m_swerveSubsystem, () -> m_driverController.getLeftY() * -1, () -> m_driverController.getLeftX() * -1, m_shooterSubsystem, targetPose, m_driverController));

    m_driverController.rightTrigger().whileTrue(new SetIndexer(m_indexerSubsystem)
      .alongWith(new JiggleIntake(m_intakeSubsystem, 1.5, .5))
    );

    m_driverController.povLeft().whileTrue(new SetIntakePercent(m_intakeSubsystem, -.5, 0));

    m_driverController.rightBumper().whileTrue(new SetShooter(m_shooterSubsystem, 5.3, 60));
    // m_driverController.povUp().whileTrue(new TestHood(m_shooterSubsystem, 0.3));
    // m_driverController.povDown().whileTrue(new TestHood(m_shooterSubsystem, -.3));

    // m_driverController.a().onTrue(new InstantCommand(() -> m_swerveSubsystem.zeroGyro()));

    m_driverController.leftBumper().onTrue(new InstantCommand(() -> m_swerveSubsystem.resetOdometry(new Pose2d())));
    m_driverController.leftBumper().onTrue(new InstantCommand( () -> m_swerveSubsystem.zeroGyro() ));

    SmartDashboard.putData("Zero Gyro", new InstantCommand( () -> m_swerveSubsystem.zeroGyro() ));
  }


  public void addAutoOptions(){ 
    chooser.setDefaultOption("Nothing", new InstantCommand());
    chooser.addOption("New Path", getTestCommand());
    chooser.addOption("basic auto", basicShoot());
    chooser.addOption("depot one", depotOneCycle());
    chooser.addOption("depot angled", depotAngled());
    chooser.addOption("big one", middleRush());
    chooser.addOption("left mid rush", oneSideBumpLeft());
    chooser.addOption("right mid rush", oneSideBumpRight());

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

  public Command depotOneCycle(){
    PathPlannerPath firstPath = getPath("side to depot");

    Command resetPose = new InstantCommand(() -> m_swerveSubsystem.resetOdometry(firstPath.getStartingHolonomicPose().get()));

    Command toDepot = AutoBuilder.followPath(firstPath);

    Command intake = new SetIntake(m_intakeSubsystem, 0, IntakeConstants.kIntakeDownPos)
                            .raceWith(new WaitCommand(1))
                            .andThen(new SetIntakePercent(m_intakeSubsystem));
  
    Command toShoot = AutoBuilder.followPath(getPath("depot to shoot"));

    Command aim = new AutoAim(m_swerveSubsystem, m_shooterSubsystem, targetPose);

    Command shoot = new SetIndexer(m_indexerSubsystem);

    Command wholeThing = new ParallelCommandGroup(aim, new WaitCommand(1.5).andThen(shoot), new SetIntake(m_intakeSubsystem, IntakeConstants.kIntakeSpeedPercent, IntakeConstants.kIntakeDownPos));

    return new SequentialCommandGroup(resetPose, toDepot.raceWith(intake), toShoot, wholeThing);
  }

  public Command depotAngled() {
    PathPlannerPath path = getPath("angled depot");

    Command resetPose = new InstantCommand(() -> m_swerveSubsystem.resetOdometry(path.getStartingHolonomicPose().get()));

    Command followPath = AutoBuilder.followPath(path);

    Command intake = new SetIntake(m_intakeSubsystem, 0, IntakeConstants.kIntakeDownPos)
                            .raceWith(new WaitCommand(1))
                            .andThen(new SetIntakePercent(m_intakeSubsystem));

    Command aim = new AutoAim(m_swerveSubsystem, m_shooterSubsystem, targetPose);

    Command shoot = new SetIndexer(m_indexerSubsystem);

    Command wholeThing = new ParallelCommandGroup(aim, new WaitCommand(1.5).andThen(shoot), new SetIntake(m_intakeSubsystem, IntakeConstants.kIntakeSpeedPercent, IntakeConstants.kIntakeDownPos));

    return new SequentialCommandGroup(resetPose, followPath.raceWith(intake), wholeThing);
  }

  public Command middleRush(){
    
    PathPlannerPath firstPath = getPath("over bump");
    
    Command resetPose = new InstantCommand(() -> m_swerveSubsystem.resetOdometry(firstPath.getStartingHolonomicPose().get()));

    Command intake = new SetIntake(m_intakeSubsystem, IntakeConstants.kIntakeSpeedPercent, IntakeConstants.kIntakeDownPos)
                            .andThen(new SetIntakePercent(m_intakeSubsystem));

    Command intakeAround = AutoBuilder.followPath(firstPath).raceWith(new WaitCommand(0.8).andThen(intake));

    Command aim = new AutoAim(m_swerveSubsystem, m_shooterSubsystem, targetPose);

    Command shoot = new SetIndexer(m_indexerSubsystem);

    Command wholeThing = new ParallelCommandGroup(aim, new WaitCommand(2).andThen(shoot), new SetIntake(m_intakeSubsystem, IntakeConstants.kIntakeSpeedPercent, IntakeConstants.kIntakeDownPos));

    return new SequentialCommandGroup(resetPose, intakeAround, wholeThing);
  }

  public Command oneSideBumpLeft(){
    PathPlannerPath firstPath = getPath("one side bump left");

    Command resetPose = new InstantCommand(() -> m_swerveSubsystem.resetOdometry(firstPath.getStartingHolonomicPose().get()));

    Command intake = new SetIntake(m_intakeSubsystem, IntakeConstants.kIntakeSpeedPercent, IntakeConstants.kIntakeDownPos)
                            .andThen(new SetIntakePercent(m_intakeSubsystem));

    Command intakeAround = AutoBuilder.followPath(firstPath).raceWith(new WaitCommand(0.8).andThen(intake));

    Command aim = new AutoAim(m_swerveSubsystem, m_shooterSubsystem, targetPose);

    Command shoot = new SetIndexer(m_indexerSubsystem);

    Command wholeThing = new ParallelCommandGroup(aim, new WaitCommand(1).andThen(shoot), new SetIntake(m_intakeSubsystem, IntakeConstants.kIntakeSpeedPercent, IntakeConstants.kIntakeDownPos));

    return new SequentialCommandGroup(resetPose, intakeAround, wholeThing);
  }
  
  public Command oneSideBumpRight(){
    PathPlannerPath firstPath = getPath("one side bump right");

    Command resetPose = new InstantCommand(() -> m_swerveSubsystem.resetOdometry(firstPath.getStartingHolonomicPose().get()));

    Command intake = new SetIntake(m_intakeSubsystem, 0, IntakeConstants.kIntakeDownPos)
                            .andThen(new SetIntakePercent(m_intakeSubsystem));

    Command intakeAround = AutoBuilder.followPath(firstPath).raceWith(new WaitCommand(0.8).andThen(intake));

    Command aim = new AutoAim(m_swerveSubsystem, m_shooterSubsystem, targetPose);

    Command shoot = new SetIndexer(m_indexerSubsystem);

    Command wholeThing = new ParallelCommandGroup(aim, new WaitCommand(1).andThen(shoot), new SetIntake(m_intakeSubsystem, IntakeConstants.kIntakeSpeedPercent, IntakeConstants.kIntakeDownPos));

    return new SequentialCommandGroup(resetPose, intakeAround, wholeThing);
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
