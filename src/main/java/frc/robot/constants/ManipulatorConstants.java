package frc.robot.constants;

import edu.wpi.first.math.geometry.Rotation2d;

public class ManipulatorConstants {
    public static class Shooter{
        public static final double kShooterIdleSpeed = 100;
        public static final double kFeederSpeed = 100; 
        public static final double shooterHeight = 0.62;

        public static final double kHoodDefaultPos = 0;
        public static final double minHoodDegrees = 41.4;
        public static final double maxHoodDegrees = 79.96;
        public static final double hoodRatio = 121d/20d;

        public static final double kTurretDefaultPos = 0;
        public static final double turretRatio = 13.4;
        public static final double minTurretDegrees = -60;
        public static final double maxTurretDegrees = 60;
    }

    public static class Intake{
        public static final double kIntakeSpeed = 100;

        public static final double kIntakeUpPos = 10;
        public static final double kIntakeDownPos = 0;
    }

    public static class Climber{
        public static final double kClimberUpPos = 100;
        public static final double kClimberDownPos = 0;
    }
}
