package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.motor.TitanSRX;
import frc.robot.swerve.SwerveModule;

public class Swerve extends SubsystemBase {

    private AHRS navX;
    private SwerveModule FrontLeft, FrontRight, BackLeft, BackRight;

    public Swerve(SwerveModule FrontLeft, SwerveModule FrontRight, SwerveModule BackLeft, SwerveModule BackRight, AHRS navX) {
        this.FrontLeft = FrontLeft;
        this.FrontRight = FrontRight;
        this.BackLeft = BackLeft;
        this.BackRight = BackRight;
        this.navX = navX;
    }

    public void setModuleStates(SwerveModuleState FrontLeft, SwerveModuleState FrontRight, SwerveModuleState BackLeft, SwerveModuleState BackRight) {
        SwerveDriveKinematics.desaturateWheelSpeeds(new SwerveModuleState[]{FrontLeft, FrontRight, BackLeft, BackRight}, 5); // https://youtu.be/0Xi9yb1IMyA?t=717
        this.FrontLeft.setState(FrontLeft);
        this.FrontRight.setState(FrontRight);
        this.BackLeft.setState(BackLeft);
        this.BackRight.setState(BackRight);
    }

    public void stop() {
        this.brake();
        this.FrontLeft.stop();
        this.FrontRight.stop();
        this.BackLeft.stop();
        this.BackRight.stop();
    }

    public void coast() {
        this.FrontLeft.coast();
        this.FrontRight.coast();
        this.BackLeft.coast();
        this.BackRight.coast();
    }

    public void brake() {
        this.FrontLeft.brake();
        this.FrontRight.brake();
        this.BackLeft.brake();
        this.BackRight.brake();
    }

    public boolean hasNavX() {
        return this.navX != null;
    }

    public double getHeading() {
        return Math.IEEEremainder(navX.getAngle(), 360);
    }

    public Rotation2d getRotation2d() {
        return Rotation2d.fromDegrees(getHeading());
    }

}
