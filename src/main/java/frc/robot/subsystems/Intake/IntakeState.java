// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Intake;

/** Add your docs here. */
public class IntakeState {
    private double intakePos;
    private double intakeVel;
    
    public IntakeState(double intakePos, double intakeVel){
        this.intakePos = intakePos;
        this.intakeVel = intakeVel;
    }

    public double getPos(){
        return intakePos;
    }

    public double getVel(){
        return intakeVel;
    }

    public void setPos(double pos){
        this.intakePos = pos;
    }

    public void setVel(double vel){
        this.intakeVel = vel;
    }
}
