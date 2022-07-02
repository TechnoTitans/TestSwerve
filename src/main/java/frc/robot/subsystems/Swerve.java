package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.motor.TitanSRX;
import frc.robot.swerve.SwerveModule;

public class Swerve extends SubsystemBase {

    public SwerveModule FrontLeft, FrontRight, BackLeft, BackRight;

    public Swerve(SwerveModule FrontLeft, SwerveModule FrontRight, SwerveModule BackLeft, SwerveModule BackRight) {
        this.FrontLeft = FrontLeft;
        this.FrontRight = FrontRight;
        this.BackLeft = BackLeft;
        this.BackRight = BackRight;
    }

    public void set(double left, double right) {

    }

    public void stop() {
        this.brake();
        this.FrontLeft.getDrive().stop();
        this.FrontLeft.getTurn().stop();

        this.FrontRight.getDrive().stop();
        this.FrontRight.getTurn().stop();

        this.BackLeft.getDrive().stop();
        this.BackLeft.getTurn().stop();

        this.BackRight.getDrive().stop();
        this.BackRight.getTurn().stop();
    }

    public void coast() {
        this.FrontLeft.getDrive().coast();
        this.FrontRight.getDrive().coast();
        this.BackLeft.getDrive().coast();
        this.BackRight.getDrive().coast();
    }

    public void brake() {
        this.FrontLeft.getDrive().brake();
        this.FrontRight.getDrive().brake();
        this.BackLeft.getDrive().brake();
        this.BackRight.getDrive().brake();
    }

}
