package frc.robot;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

public class MotorConstants {
    public static class IDs {
        public static final int kShooterMotor = 0;
        public static final int kFeederMotor = 41;
    }

    public static class Configs {
        // in init function
        public static TalonFXConfiguration talonFXConfigs = new TalonFXConfiguration();

        // set slot 0 gains
        public static Slot0Configs slot0Configs = talonFXConfigs.Slot0
            .withKS(0.25)
            .withKV(0.12)
            .withKA(0.01)
            .withKP(0.11)
            .withKI(0)
            .withKD(0);

        public static MotionMagicConfigs motionMagicConfigs = talonFXConfigs.MotionMagic
            .withMotionMagicAcceleration(100)
            .withMotionMagicJerk(1000);
    }
}
