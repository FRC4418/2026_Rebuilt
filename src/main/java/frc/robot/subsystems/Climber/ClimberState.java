// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Climber;

/** Add your docs here. */
public class ClimberState {
    private double climberPos;
    
    public ClimberState(double climberPos){
        this.climberPos = climberPos;
    }

    public double getPos(){
        return climberPos;
    }


    public void setPos(double pos){
        this.climberPos = pos;
    }

}
