package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;

public class DriveTeleop extends CommandBase {
    private final Swerve drive;
    private double leftInput, rightInput, steeringInput;
    private SlewRateLimiter leftLimiter, rightLimiter, steeringLimiter;
    private ChassisSpeeds speeds;


    public DriveTeleop(Swerve drive, double leftInput, double rightInput, double steeringInput) {
        this.drive = drive;
        this.leftInput = leftInput;
        this.rightInput = rightInput;
        this.steeringInput = steeringInput;

        leftLimiter = new SlewRateLimiter(0.5); //https://youtu.be/0Xi9yb1IMyA?t=785
        rightLimiter = new SlewRateLimiter(0.5); //https://youtu.be/0Xi9yb1IMyA?t=785
        steeringLimiter = new SlewRateLimiter(0.5); //https://youtu.be/0Xi9yb1IMyA?t=785

        addRequirements(drive);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // Remove drift from controller
        steeringInput = steeringInput > -0.25 && steeringInput < 0.25 ? 0 : steeringInput;
        leftInput = leftInput > -0.25 && leftInput < 0.25 ? 0 : leftInput;
        rightInput = rightInput > -0.25 && rightInput < 0.25 ? 0 : rightInput;

        //slew rate limiters
        leftInput = leftLimiter.calculate(leftInput) * 5; //https://youtu.be/0Xi9yb1IMyA?t=814
        rightInput = rightLimiter.calculate(rightInput) * 5; //https://youtu.be/0Xi9yb1IMyA?t=814
        steeringInput = steeringLimiter.calculate(steeringInput) * 5; //https://youtu.be/0Xi9yb1IMyA?t=814

        //Not that this has ever happend 0.0 but if our navx unplugs we're still gonna wanna drive so this will stop field oriented and will default to tank driving mode.
        if (drive.hasNavX()) {
            speeds = ChassisSpeeds.fromFieldRelativeSpeeds(leftInput, rightInput, steeringInput, drive.getRotation2d());
        } else {
            speeds = new ChassisSpeeds(leftInput, rightInput, steeringInput);
        }
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
