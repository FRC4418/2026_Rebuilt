package frc.robot.constants;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.config.SparkMaxConfig;

public class MotorConstants {
    
    public static class Intake {
        public static final int kActuatorMotorID = 20;

        public static final TalonFXConfiguration actuatorConfig = new TalonFXConfiguration();

        static {
            actuatorConfig.Slot0
                .withKS(0)
                .withKV(0)
                .withKA(0)
                .withKP(.5)
                .withKI(0.05)
                .withKD(0);

            actuatorConfig.withCurrentLimits(new CurrentLimitsConfigs().withSupplyCurrentLimit(20));

            actuatorConfig.MotionMagic
                .withMotionMagicCruiseVelocity(20)
                .withMotionMagicAcceleration(200)
                .withMotionMagicJerk(1000);
            }

        public static final int kActuatorSlaveID = 21;
    
        

        public static final int kSpinMotorID = 22;

        public static final TalonFXConfiguration spinConfig = new TalonFXConfiguration();

        static {
            spinConfig.Slot0
                .withKS(0)
                .withKV(0)
                .withKA(0)
                .withKP(.1)
                .withKI(0)
                .withKD(0);

            spinConfig.withCurrentLimits(new CurrentLimitsConfigs().withSupplyCurrentLimit(60));

            spinConfig.MotionMagic
                .withMotionMagicAcceleration(200)
                .withMotionMagicJerk(200);
            }
    }


    public static class Indexer {
        //spindexer
        public static final int kSpindexerMotorID = 30;

        public static final TalonFXConfiguration spindexerConfig = new TalonFXConfiguration();
        
        static {
            spindexerConfig.Slot0
            .withKS(0)
            .withKV(0)
            .withKA(0)
            .withKP(.1)
            .withKI(0)
            .withKD(0);

            spindexerConfig.withCurrentLimits(new CurrentLimitsConfigs().withSupplyCurrentLimit(60));
                
            spindexerConfig.MotionMagic
                .withMotionMagicAcceleration(200)
                .withMotionMagicJerk(1000);
            }
            
        
        //kicker
        public static final int kKickerMotorID = 31;

        public static final TalonFXConfiguration kickerConfig = new TalonFXConfiguration();
        
        static {
            kickerConfig.Slot0
                .withKS(0)
                .withKV(0)
                .withKA(0)
                .withKP(.1)
                .withKI(0)
                .withKD(0);

            kickerConfig.withCurrentLimits(new CurrentLimitsConfigs().withSupplyCurrentLimit(60));

            kickerConfig.MotionMagic
                .withMotionMagicAcceleration(200)
                .withMotionMagicJerk(1000);
        }
    }

    public static class Shooter {

        //turret
        public static final int kTurretMotorID = 32;

        public static final TalonFXConfiguration turretConfig = new TalonFXConfiguration();

        static {
            turretConfig.Slot0
                .withKS(0)
                .withKV(0)
                .withKA(0)
                .withKP(.1)
                .withKI(0)
                .withKD(0);

            turretConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

            turretConfig.MotionMagic
                .withMotionMagicCruiseVelocity(30)
                .withMotionMagicAcceleration(100)
                .withMotionMagicJerk(500);
        }

        //hood
        public static final int kHoodMotorID = 40;

        public static final SparkMaxConfig hoodConfig = new SparkMaxConfig();

        static {
            hoodConfig.closedLoop.maxMotion
                .cruiseVelocity(500)
                .maxAcceleration(100);

            hoodConfig.closedLoop
                .p(.1)
                .i(0)
                .d(0)
                .outputRange(-1, 1);
        }

        //flywheel
        public static final int kShooterMotorID = 43;
        public static final int kShooterSlaveID = 41;

        public static final TalonFXConfiguration shooterConfig = new TalonFXConfiguration();

        static {
            shooterConfig.MotorOutput.PeakReverseDutyCycle = 0;
            
            shooterConfig.withCurrentLimits(new CurrentLimitsConfigs().withSupplyCurrentLimit(60));

            shooterConfig.Slot0
                .withKS(0)
                .withKV(0)
                .withKA(0)
                .withKP(6767)
                .withKI(0)
                .withKD(0);

            shooterConfig.MotionMagic
                .withMotionMagicAcceleration(500)
                .withMotionMagicJerk(5000);
        }
    }

    public static class Climber {
        public static final int kMotorID = 50;

        public static final TalonFXConfiguration config = new TalonFXConfiguration();

        static {
            config.Slot0
                .withKS(0)
                .withKV(0)
                .withKA(0)
                .withKP(.1)
                .withKI(0)
                .withKD(0);

            config.MotionMagic
                .withMotionMagicCruiseVelocity(80)
                .withMotionMagicAcceleration(200)
                .withMotionMagicJerk(1000);
            }
    }
}
