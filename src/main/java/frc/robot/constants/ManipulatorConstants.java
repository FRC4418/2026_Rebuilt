package frc.robot.constants;

import edu.wpi.first.math.geometry.Rotation2d;

public class ManipulatorConstants {
    // Shooter
    public static final double kShooterSpeed = 100;
    public static final double kFeederSpeed = 41; 

    public static final double kHoodDefaultPos = 67;

    // Intake    
    public static final double kIntakeSpeed = 67;

    public static final double kIntakeUpPos = 40;
    public static final double kIntakeDownPos = 0;

    // Climber
    public static final double kClimberUpPos = 67676767;
    public static final double kClimberDownPos = 676676;

    // Turret
    public static final double kTurretDefaultPos = 0;
    public static final Rotation2d kMinHoodAngle = Rotation2d.fromDegrees(40);
    public static final Rotation2d kMaxHoodAngle = Rotation2d.fromDegrees(85);

    public static final double turretRatio = 13.4;
}
