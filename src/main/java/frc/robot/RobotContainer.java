// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.DriveTeleop;
import frc.robot.motor.TitanSRX;
import frc.robot.subsystems.Swerve;
import frc.robot.utils.TitanButton;

public class RobotContainer {
    //OI
    public OI oi;

    //Motors
    public TitanSRX leftFront, leftRear, rightFront, rightRear;

    //Subsystems
    public Swerve drive;

    //Commands
    public DriveTeleop driveTeleop;

    public RobotContainer() {
        oi = new OI();

        //Drivetrain
        leftFront = new TitanSRX(RobotMap.leftFront, RobotMap.leftFrontReverse);
        leftRear = new TitanSRX(RobotMap.leftRear, RobotMap.leftRearReverse);
        rightFront = new TitanSRX(RobotMap.rightFront, RobotMap.rightFrontReverse);
        rightRear = new TitanSRX(RobotMap.rightRear, RobotMap.rightRearReverse);

        drive = new Swerve(leftFront, rightFront);
        driveTeleop = new DriveTeleop(drive, oi.getXboxLeftTrigger(), oi.getXboxRightTrigger(), oi.getXboxLeftX());

        configureButtonBindings();
    }

    private void configureButtonBindings() {
    }

    public Command getAutonomousCommand() {
        // Unless you're crazy and want the big robot with the big scary air tanks to drive itself don't use this.
        return null;
    }
}
