package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Hertz;
import static edu.wpi.first.units.Units.Seconds;

import java.util.function.BooleanSupplier;
import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ManipulatorConstants.LEDConstants;

public class LEDSubsystem extends SubsystemBase {
  public enum AimState { IDLE, BLOCKED, ALIGNING, SETTLING, READY }

  private AimState aimState = AimState.IDLE;
  private BooleanSupplier feeding = () -> false;
  private final AddressableLED m_led = new AddressableLED(0);
  private final AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(30);
  private final LEDPattern rainbow = LEDPattern.rainbow(255, 255)
      .scrollAtRelativeSpeed(Hertz.of(0.25)).breathe(Seconds.of(4));
  private final LEDPattern amber = LEDPattern.solid(new Color(255, 100, 0));
  private final LEDPattern settling = LEDConstants.blue.breathe(Seconds.of(1));

  public LEDSubsystem() {
    m_led.setLength(ledBuffer.getLength());
    m_led.setData(ledBuffer);
    m_led.start();
  }

  public void setAimState(AimState state) {
    aimState = state;
  }

  public void setFeedingSupplier(BooleanSupplier supplier) {
    feeding = supplier;
  }

  public void setPattern(LEDPattern pattern) {
    pattern.applyTo(ledBuffer);
    m_led.setData(ledBuffer);
  }

  @Override
  public void periodic() {
    String displayedState;
    if (DriverStation.isDisabled()) {
      displayedState = "DISABLED";
      setPattern(rainbow);
    } else if (aimState == AimState.READY && feeding.getAsBoolean()) {
      displayedState = "FEEDING";
      // Moving bright green segments over a dim green background.
      int phase = (int) (Timer.getFPGATimestamp() * 20) % 10;
      for (int i = 0; i < ledBuffer.getLength(); i++) {
        ledBuffer.setRGB(i, 0, (i + phase) % 10 < 3 ? 255 : 30, 0);
      }
      m_led.setData(ledBuffer);
    } else {
      displayedState = aimState.name();
      switch (aimState) {
        case BLOCKED:
          setPattern(((int) (Timer.getFPGATimestamp() * 4)) % 2 == 0
              ? LEDConstants.red : LEDPattern.solid(Color.kBlack));
          break;
        case ALIGNING:
          setPattern(amber);
          break;
        case SETTLING:
          setPattern(settling);
          break;
        case READY:
          setPattern(LEDConstants.green);
          break;
        default:
          setPattern(DriverStation.getAlliance().orElse(DriverStation.Alliance.Blue)
              == DriverStation.Alliance.Red ? LEDConstants.red : LEDConstants.blue);
          break;
      }
    }
    Logger.recordOutput("LEDs/State", displayedState);
  }
}
