package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class OI {
    public static final int XBOX_A = 1;
    public static final int XBOX_B = 2;
    public static final int XBOX_X = 3;
    public static final int XBOX_Y = 4;
    public static final int XBOX_BUMPER_RIGHT = 6;
    public static final int XBOX_BUMPER_LEFT = 5;
    public static final int XBOX_BTN_SELECT = 7;
    public static final int XBOX_BTN_START = 8;

    private XboxController xboxMain;
    private XboxController xboxCo;

    public OI() {
        xboxMain = new XboxController(RobotMap.MainController);
        xboxCo = new XboxController(RobotMap.CoController);
    }

    public double getXboxLeftYMain() { return -xboxMain.getLeftY(); }

    public double getXboxLeftXMain() { return -xboxMain.getLeftX(); }

    public double getXboxRightYMain() {
        return xboxMain.getRightY();
    }

    public double getXboxRightXMain() {
        return xboxMain.getRightX();
    }

    public double getXboxLeftTriggerMain() {
        return xboxMain.getLeftTriggerAxis();
    }

    public double getXboxRightTriggerMain(){return xboxMain.getRightTriggerAxis(); }

    public double getXboxLeftYCo() { return -xboxCo.getLeftY(); }

    public double getXboxLeftXCo() { return -xboxCo.getLeftX(); }

    public double getXboxRightYCo() {
        return xboxCo.getRightY();
    }

    public double getXboxRightXCo() {
        return xboxCo.getRightX();
    }

    public double getXboxLeftTriggerCo() {
        return xboxCo.getLeftTriggerAxis();
    }

    public double getXboxRightTriggerCo(){return xboxCo.getRightTriggerAxis(); }

    public int getPOVCo() {
        return xboxCo.getPOV();
    }

    public XboxController getXboxMain() {
        return xboxMain;
    }
    public XboxController getXboxCo() {
        return xboxCo;
    }

    public double deadband(double value, double deadband) {
        if (-deadband <= value && value <= deadband) {
            value = 0;
        } else if (value > deadband) {
            value -= deadband;
            value *= (1 + deadband);
        } else if (value < -deadband) {
            value += deadband;
            value *= (1 + deadband);
        }
        return value;
    }




}
