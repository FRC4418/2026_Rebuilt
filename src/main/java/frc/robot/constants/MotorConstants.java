package frc.robot.constants;

import com.ctre.phoenix6.configs.TalonFXConfiguration;

public class MotorConstants {

    public static class Shooter {
        public static final int kMotorID = 30;
        public static final int kSlaveID = 40;

        public static final TalonFXConfiguration config = new TalonFXConfiguration();

        static {
            config.Slot0
                .withKS(0)
                .withKV(0.12)
                .withKA(0.01)
                .withKP(0.11)
                .withKI(0)
                .withKD(0);

            config.MotionMagic
                .withMotionMagicAcceleration(400)
                .withMotionMagicJerk(7000);
        }
    }

    public static class Feeder {
        public static final int kMotorID = 109;

        public static final TalonFXConfiguration config = new TalonFXConfiguration();

        static {
            config.Slot0
                .withKS(0)
                .withKV(0)
                .withKA(0)
                .withKP(0.1)
                .withKI(0)
                .withKD(0);

            config.MotionMagic
                .withMotionMagicAcceleration(670)
                .withMotionMagicJerk(1500);
        }
    }

    public static class Hood {
        public static final int kMotorID = 104;

        public static final TalonFXConfiguration config = new TalonFXConfiguration();

        static {
            config.Slot0
                .withKS(0)
                .withKV(0)
                .withKA(0)
                .withKP(0.1)
                .withKI(0)
                .withKD(0);

            config.MotionMagic
                .withMotionMagicCruiseVelocity(100)
                .withMotionMagicAcceleration(670)
                .withMotionMagicJerk(1500);
        }
    }

    public static class Intake {
        public static final int kLeftMotorID = 102;
        public static final int kRightMotorID = 103;

        public static final TalonFXConfiguration config = new TalonFXConfiguration();

        static {
            config.Slot0
                .withKS(0.67)
                .withKV(0)
                .withKA(0)
                .withKP(0.1)
                .withKI(0)
                .withKD(0);

            config.MotionMagic
                .withMotionMagicAcceleration(67)
                .withMotionMagicJerk(670);
            }
    }

    public static class IntakeActuator {
        public static final int kLeftMotorID = 100;
        public static final int kRightMotorID = 101;

        public static final TalonFXConfiguration config = new TalonFXConfiguration();

        static {
            config.Slot0
                .withKS(0.67)
                .withKV(0)
                .withKA(0)
                .withKP(0.1)
                .withKI(0)
                .withKD(0);

            config.MotionMagic
                .withMotionMagicCruiseVelocity(76)
                .withMotionMagicAcceleration(67)
                .withMotionMagicJerk(670);
            }
    }

    public static class Climber {
        public static final int kMotorID = 78;

        public static final TalonFXConfiguration config = new TalonFXConfiguration();

        static {
            config.Slot0
                .withKS(0.67)
                .withKV(0)
                .withKA(0)
                .withKP(0.1)
                .withKI(0)
                .withKD(0);

            config.MotionMagic
                .withMotionMagicCruiseVelocity(76)
                .withMotionMagicAcceleration(67)
                .withMotionMagicJerk(670);
            }
    }

    public static class Turret {
        public static final int kMotorID = 143;

        public static final TalonFXConfiguration config = new TalonFXConfiguration();

        static {
            config.Slot0
                .withKS(0)
                .withKV(0)
                .withKA(0)
                .withKP(0.5)
                .withKI(0)
                .withKD(0);

            config.MotionMagic
                .withMotionMagicCruiseVelocity(86)
                .withMotionMagicAcceleration(67)
                .withMotionMagicJerk(2756);
        }
    }
}
