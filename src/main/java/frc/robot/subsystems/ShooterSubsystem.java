// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ManipulatorConstants.ShooterConstants;
import frc.robot.utils.LimelightHelpers;
import frc.robot.utils.LimelightHelpers.PoseEstimate;
import frc.robot.constants.ManipulatorConstants;
import frc.robot.constants.MotorConstants;

public class ShooterSubsystem extends SubsystemBase {
  
  private final TalonFX m_shooterMotor = new TalonFX(MotorConstants.Shooter.kShooterMotorID);
  private final TalonFX m_shooterMotorSlave = new TalonFX(MotorConstants.Shooter.kShooterSlaveID);
  private final TalonFX m_turretMotor = new TalonFX(MotorConstants.Shooter.kTurretMotorID);

  private final SparkMax m_hoodMotor = new SparkMax(MotorConstants.Shooter.kHoodMotorID, SparkLowLevel.MotorType.kBrushless);


  final MotionMagicVelocityVoltage m_shooterRequest = new MotionMagicVelocityVoltage(0);
  final MotionMagicVelocityVoltage m_feederRequest = new MotionMagicVelocityVoltage(0);

  final MotionMagicVoltage m_turretRequest = new MotionMagicVoltage(ShooterConstants.kTurretDefaultPos);
  final SparkClosedLoopController m_hoodController;

  private final DigitalInput m_limitSwitch = new DigitalInput(3);

  // public LimelightCamera turretCamera = new LimelightCamera("limelight-turret");
  
  public ShooterSubsystem() {
    
    

    m_shooterMotor.getConfigurator().apply(MotorConstants.Shooter.shooterConfig);
    m_shooterMotorSlave.setControl(new Follower(MotorConstants.Shooter.kShooterMotorID, MotorAlignmentValue.Opposed));
    m_turretMotor.getConfigurator().apply(MotorConstants.Shooter.turretConfig);
    // MotorOutputConfigs config = new MotorOutputConfigs().

    m_hoodMotor.configure(
      MotorConstants.Shooter.hoodConfig,
      ResetMode.kResetSafeParameters,
      PersistMode.kPersistParameters
    );

    m_hoodController = m_hoodMotor.getClosedLoopController();

  }
  
  
  public void setShooterVel(double vel){
    m_shooterMotor.setControl(new VelocityDutyCycle(vel));
  }

  public double getShooterVel(){
    return m_shooterMotor.getVelocity().getValueAsDouble();
  }


  public void setShooterPercentOut(double percent){
    m_shooterMotor.setControl(new DutyCycleOut(percent));
  }

  public void setHoodPercentOut(double percent){
    m_hoodMotor.set(percent);
  }

  public void setHoodPos(double numRotations){
    m_hoodController.setSetpoint(numRotations, SparkBase.ControlType.kPosition);
    
  }

  // public PoseEstimate getTurretPosEst(double robotYaw, double robotYawRate){
  //   // return turretCamera.getLLHPose(robotYaw, robotYawRate);
  //   return LimelightHelpers.getBotPoseEstimate_wpiBlue("limelight-turret");
  // }

  public void setHoodPos(Rotation2d pos){
    
    Rotation2d clamped = Rotation2d.fromDegrees(Math.min(ShooterConstants.minHoodDegrees, Math.min(ShooterConstants.maxHoodDegrees, pos.getDegrees())));
    
    double rotations = clamped.getRotations();

    setHoodPos(rotations*ShooterConstants.hoodRatio);
  }

  public void setTurretPos(Rotation2d pos){
    double clamped = Math.min(Math.max(pos.getDegrees(), ShooterConstants.minTurretDegrees), ShooterConstants.maxTurretDegrees);
    m_turretMotor.setControl(m_turretRequest.withPosition(clamped * (ShooterConstants.turretRatio/360)));
  }

  public Rotation2d getTurretPos(){
    double turretMotorRot = m_turretMotor.getPosition().getValueAsDouble();

    double turretRotations = turretMotorRot / ShooterConstants.turretRatio;

    return Rotation2d.fromRotations(turretRotations);
  }

  @Override
  public void periodic() {

    // Pose3d turretCenterPose = new Pose3d(ShooterConstants.turretPose, new Rotation3d(Rotation2d.kZero.getMeasure(), Rotation2d.kZero.getMeasure(), getTurretPos().getMeasure()));

    // turretCenterPose.transformBy(new Transform3d(ShooterConstants.turretCenterToLL, 0, 0, Rotation3d.kZero));

    // turretCenterPose.rotateBy(ShooterConstants.limelightAngle);

    // turretCamera.setCameraPoseRobotSpace(turretCenterPose);

    // if(m_limitSwitch.get()){
    //   m_hoodMotor.getEncoder().setPosition(0);
    // } 
    // if(DriverStation.isEnabled()){
    //   // System.out.println("setting pipe to 1");
    //   turretCamera.setPipeline(1);
    // }else{
    //   turretCamera.setPipeline(0);
    // }

    SmartDashboard.putNumber("hood pos", m_hoodMotor.getEncoder().getPosition());
    // SmartDashboard.putNumber("hood current", m_hoodMotor.getOutputCurrent());

    // SmartDashboard.putNumber("Shooter Vel", m_shooterMotor.getVelocity().getValueAsDouble());

    // SmartDashboard.putNumber("Shooter Current", m_shooterMotor.getStatorCurrent().getValueAsDouble());
  }
}
