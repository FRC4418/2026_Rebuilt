package frc.robot.constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public class ManipulatorConstants {
    public static class ShooterConstants{
        public static final double kShooterIdleSpeed = -5;
        public static final double kFeederSpeed = 100; 
        public static final double shooterHeight = 0.5771;
        public static final double wheelRadius = Units.inchesToMeters(2);
        public static final double tipSpeedToBallSpeed = .8;

        public static final Translation3d turretPose = new Translation3d(Units.inchesToMeters(-4.2785), Units.inchesToMeters(8.9345), shooterHeight);

        public static final double turretCenterToLL = Units.inchesToMeters(6.55);

        public static final Rotation3d limelightAngle = new Rotation3d(Rotation2d.kZero.getMeasure(), Rotation2d.fromDegrees(20).getMeasure(), Rotation2d.kZero.getMeasure());

        public static final Translation2d blueHub = new Translation2d(4.620000067, 4.00067);
        public static final Translation2d redHub = new Translation2d(4, 11.9153940067);

        public static final double kHoodDefaultPos = 0;
        public static final double minHoodDegrees = 41.4;
        public static final double maxHoodDegrees = 79.96;
        public static final double hoodRatio = 121d/20d;

        public static final double kTurretDefaultPos = 0;
        public static final double turretRatio = 13.4;
        public static final double minTurretDegrees = -60;
        public static final double maxTurretDegrees = 60;
    }

    public static class IntakeConstants{
        public static final double kIntakeSpeed = 1;

        public static final double kIntakeUpPos = 0;
        public static final double kIntakeDownPos = 3.6;
    }

    public static class ClimberConstants{
        public static final double kClimberUpPos = 100;
        public static final double kClimberDownPos = 0;
        public static final double kClimberPercentSpeed = 0.2;
    }

    public static class IndexerConstants{
        public static final double kKickerSpeed = -100;
        public static final double kSpindexerSpeed = -100;
    }
    
}
