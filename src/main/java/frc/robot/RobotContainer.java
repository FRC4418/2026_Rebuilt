// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Shooter.Spin;
import frc.robot.subsystems.ShooterSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);

  ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem(); 

  //IntakeSubsystem m_leftIntakeSubsystem = new IntakeSubsystem(MotorConstants.Intake.kLeftMotorID, MotorConstants.IntakeActuator.kLeftMotorID, 'L');
  //IntakeSubsystem m_rightIntakeSubsystem = new IntakeSubsystem(MotorConstants.Intake.kRightMotorID, MotorConstants.IntakeActuator.kRightMotorID, 'R');

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    //m_shooterSubsystem.setDefaultCommand(new ShooterDefault(m_shooterSubsystem));
    //m_leftIntakeSubsystem.setDefaultCommand(new IntakeDefault(m_leftIntakeSubsystem));
    //m_rightIntakeSubsystem.setDefaultCommand(new IntakeDefault(m_rightIntakeSubsystem));
    // Configure the trigger bindings
    configureBindings();
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

    m_driverController.b().whileTrue(new Spin(m_shooterSubsystem, 100));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return new InstantCommand();
  }
}
