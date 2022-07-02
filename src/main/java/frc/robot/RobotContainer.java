// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.sensors.CANCoder;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.DriveTeleop;
import frc.robot.motor.TitanFX;
import frc.robot.motor.TitanSRX;
import frc.robot.subsystems.Swerve;
import frc.robot.swerve.SwerveModule;
import frc.robot.utils.TitanButton;

public class RobotContainer {
    //OI
    public OI oi;

    //Motors
    public TitanFX FLDrive, FLTurn;
    public TitanFX FRDrive, FRTurn;
    public TitanFX BLDrive, BLTurn;
    public TitanFX BRDrive, BRTurn;

    //Encoders
    public CANCoder FLEncoder, FREncoder, BLEncoder, BREncoder;

    //Gyro
    public AHRS navX;

    //Swerve Modules
    public SwerveModule FrontLeft, FrontRight, BackLeft, BackRight;

    //Subsystems
    public Swerve drive;

    //Commands
    public DriveTeleop driveTeleop;

    public RobotContainer() {
        oi = new OI();

        //Swerve
        FLDrive = new TitanFX(RobotMap.FLDrive, RobotMap.FLDriveRev);
        FLTurn = new TitanFX(RobotMap.FLTurn, RobotMap.FLTurnRev);
        FLEncoder = new CANCoder(RobotMap.FLEncoder);

        FRDrive = new TitanFX(RobotMap.FRDrive, RobotMap.FRDriveRev);
        FRTurn = new TitanFX(RobotMap.FRTurn, RobotMap.FRTurnRev);
        FREncoder = new CANCoder(RobotMap.FREncoder);

        BLDrive = new TitanFX(RobotMap.BLDrive, RobotMap.BLDriveRev);
        BLTurn = new TitanFX(RobotMap.BLTurn, RobotMap.BLTurnRev);
        BLEncoder = new CANCoder(RobotMap.BLEncoder);

        BRDrive = new TitanFX(RobotMap.BRDrive, RobotMap.BRDriveRev);
        BRTurn = new TitanFX(RobotMap.BRTurn, RobotMap.BRTurnRev);
        BREncoder = new CANCoder(RobotMap.BREncoder);

        FrontLeft = new SwerveModule(FLDrive, FLTurn, FLEncoder, RobotMap.FLEncoderRev);
        FrontRight = new SwerveModule(FRDrive, FRTurn, FREncoder, RobotMap.FREncoderRev);
        BackLeft = new SwerveModule(BLDrive, BLTurn, BLEncoder, RobotMap.BLEncoderRev);
        BackRight = new SwerveModule(BRDrive, BRTurn, BREncoder, RobotMap.BREncoderRev);

        navX = new AHRS(SPI.Port.kMXP);

        drive = new Swerve(FrontLeft, FrontRight, BackLeft, BackRight, navX);

        driveTeleop = new DriveTeleop(drive, oi.getXboxLeftTrigger(), oi.getXboxRightTrigger(), oi.getXboxLeftX());

        configureButtonBindings();
    }

    private void configureButtonBindings() {
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
