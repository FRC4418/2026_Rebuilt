
package frc.robot.subsystems.Indexer;

/** Add your docs here. */
public class IndexerState {
    private double kickerVel;
    private double spindexerVel;

    public IndexerState(double kickerVel, double spindexerVel){
        this.kickerVel = kickerVel;
        this.spindexerVel = spindexerVel;
    }
    
    public double getKickerVel(){
        return kickerVel;
    }

    public double getSpindexerVel(){
        return spindexerVel;
    }

    public void setKickerVel(double vel){
        kickerVel = vel;
    }

    public void setSpindexerVel(double vel){
        spindexerVel = vel;
    }
}