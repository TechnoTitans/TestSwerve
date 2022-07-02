package frc.robot.swerve;

import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;
import frc.robot.Constants;
import frc.robot.motor.TitanFX;

public class SwerveModule {

    private final TitanFX drive, turn;
    private final CANCoder absoluteEncoder;
    private boolean encoderReversed;
    private double encoderOffset;

    public SwerveModule(TitanFX drive, TitanFX turn, CANCoder absoluteEncoder, boolean encoderReversed) {
        this.drive = drive;
        this.turn = turn;
        this.absoluteEncoder = absoluteEncoder;
        this.encoderReversed = encoderReversed;
        this.encoderOffset = 0;

        this.turn.configPID(Constants.TurnkP, 0, 0);
    }

    public TitanFX getDrive() {
        return drive;
    }

    public TitanFX getTurn() {
        return turn;
    }

    public CANCoder getAbsoluteEncoder() {
        return absoluteEncoder;
    }

    public double getDrivePosition() {
        return drive.getEncoder().getRaw();
    }

    public double getTurningPosition() {
        return turn.getEncoder().getRaw();
    }

    public double getDriveRate() {
        return drive.getEncoder().getRate();
    }

    public double getTurningRate() {
        return turn.getEncoder().getRate();
    }

    public double getAbsoluteEncoderRad() {
        double angle = ((absoluteEncoder.getBusVoltage() / RobotController.getVoltage5V()) * 2 * Math.PI) - encoderOffset;
        return angle * (encoderReversed ? -1 : 1);
    }

}
