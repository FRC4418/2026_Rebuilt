// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Random;

import edu.wpi.first.units.Units;
import static edu.wpi.first.units.Units.Hertz;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Seconds;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ManipulatorConstants.LEDConstants;

public class LEDSubsystem extends SubsystemBase {

  private AddressableLED m_led = new AddressableLED(0);

  private AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(30);

  private LEDPattern red = LEDPattern.solid(new Color(255,0, 0));
  // private LEDPattern green = LEDPattern.solid(new Color(0, 255, 0));
  private LEDPattern blue = LEDPattern.solid(new Color(0, 0, 255));

  private static final Distance kLedSpacing = Meters.of(1 / 120.0);

  private LEDPattern rainbow = LEDPattern.rainbow(255, 255);

  private LEDPattern enabledPattern = LEDPattern.rainbow(255, 255);

  
  private LEDPattern redGrad = LEDPattern.gradient(LEDPattern.GradientType.kContinuous, new Color(255,0,0), new Color(255,100,0)).scrollAtRelativeSpeed(Hertz.of(.5));;

  private LEDPattern blueGrad = LEDPattern.gradient(LEDPattern.GradientType.kContinuous, new Color(255,0,200), new Color(0,100,255)).scrollAtRelativeSpeed(Hertz.of(.5));;


  private LEDPattern fancy = blueGrad.breathe(Units.Seconds.of(5));

  private Random random = new Random();

  private double[] ledList = new double[ledBuffer.getLength()];
  

  int rainbowFirstPixelHue = 0;


  /** Creates a new LEDSubsystem. */
  public LEDSubsystem() {
    for (int i = 0; i < ledList.length; i++) {
      ledList[i] = 0;
    }
    rainbow = rainbow.scrollAtAbsoluteSpeed(MetersPerSecond.of(.25), kLedSpacing);
    m_led.setLength(ledBuffer.getLength());
    // m_led2.setLength(ledBuffer.getLength());
    // setPattern(LEDConstants.red);
  }

  public void setPattern(LEDPattern pattern){

    pattern.applyTo(ledBuffer);
    m_led.setData(ledBuffer);
    m_led.start();
  }


    public void setEnabledPattern(LEDPattern pattern){
    enabledPattern = pattern;
  }


  @Override
  public void periodic() {
    if(DriverStation.isTeleopEnabled()){
      setPattern(red);
    }else if(DriverStation.isAutonomousEnabled()){
      setPattern(LEDConstants.blue.breathe(Seconds.of(.5)));
    }else{
      // updateLEDDisabled();
      setPattern(red);
    }
  }


  private void updateLEDDisabled(){
    rainbowFirstPixelHue ++;

    for (int i = 0; i < ledBuffer.getLength(); i++) {
      ledBuffer.setHSV(i, rainbowFirstPixelHue + (i * 180 / ledBuffer.getLength()) % 180, 255, 255);

      ledList[i] -= 0.02;
      if(random.nextDouble() < 0.005){
        ledList[i] = 1;
      }

      if(ledList[i] < 0) continue;

      ledBuffer.setHSV(i, 0, 0, (int) (255*ledList[i]));
    }

    m_led.setData(ledBuffer);
  }
}
