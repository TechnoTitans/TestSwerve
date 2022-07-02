package frc.robot.swerve;

import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;
import frc.robot.Constants;
import frc.robot.motor.TitanFX;

public class SwerveModule {

    private final TitanFX drive, turn;
    private final CANCoder absoluteEncoder;
    private boolean encoderReversed;
    private double encoderOffset;
    private PIDController turnController;



    public SwerveModule(TitanFX drive, TitanFX turn, CANCoder absoluteEncoder, boolean encoderReversed) {
        this.drive = drive;
        this.turn = turn;
        this.absoluteEncoder = absoluteEncoder;
        this.encoderReversed = encoderReversed;
        this.encoderOffset = 0;

        this.turnController = new PIDController(Constants.TurnkP, 0, 0);
        this.turnController.enableContinuousInput(-Math.PI, Math.PI);

        resetEncoders();
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

    public void resetEncoders() {
        drive.getEncoder().reset();
        absoluteEncoder.setPosition(getAbsoluteEncoderRad());
    }

    public SwerveModuleState getModuleState() {
        return new SwerveModuleState(getDriveRate(), new Rotation2d(getTurningPosition()));
    }

    public void setState(SwerveModuleState state) {
        state = SwerveModuleState.optimize(state, getModuleState().angle);
        drive.set(state.speedMetersPerSecond / 5); // https://youtu.be/0Xi9yb1IMyA?t=543
        turn.set(turnController.calculate(getTurningPosition(), state.angle.getRadians()));
    }

    public void stop() {
        drive.stop();
        turn.stop();
    }

    public void coast() {
        drive.coast();
    }

    public void brake() {
        drive.brake();
    }
}
