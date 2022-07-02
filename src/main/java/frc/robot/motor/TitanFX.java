package frc.robot.motor;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/*
 * Motor control (talonFX)
 * TitanFX is our enhanced version of the regular TalonFX code
 */
public class TitanFX extends WPI_TalonFX implements MotorController {

    private Encoder encoder;
    private static final int TIMEOUT_MS = 30;
    public static final int CURRENT_LIMIT = 41;
    public static final int CURRENT_LIMIT_THRESHOLD = 41;
    public static final int LIMIT_TIMEOUT = 200; //ms

    private TitanFX brownoutFollower = null;
    private boolean brownout = false;

    public static final double DEFAULT_FILTER_VALUE = 1.0; // is like identity function rn
    private SlewRateLimiter percentOutputFilter;

    /**
     * Constructor for a TalonFX motor
     *
     * @param channel  The port where the TalonFX is plugged in.
     * @param reversed If the TalonFX should invert the signal.
     */
    public TitanFX(int channel, boolean reversed) {
        super(channel);
        super.setInverted(reversed);
        percentOutputFilter = new SlewRateLimiter(DEFAULT_FILTER_VALUE); // maybe constructorize later
        percentOutputFilter.reset(0);
    }

    /**
     * Constructor
     *
     * @param channel  The port where the TalonFX is plugged in.
     * @param reversed If the TalonX should invert the signal.
     * @param encoder  Encoder to attach to this TalonFX.
     */
    public TitanFX(int channel, boolean reversed, Encoder encoder) {
        super(channel);
        super.setInverted(reversed);
        this.encoder = encoder;
    }

    /**
     * Set the speed of the TalonFX.
     *
     * @param speed -- Speed from 0 to 1 (or negative for backwards)
     */
    public void set(double speed) {
        speed = percentOutputFilter.calculate(speed);
        super.set(ControlMode.PercentOutput, speed > 1 ? 1 : speed < -1 ? -1 : speed);
    }

    public void brake() {
//        this.set(0); not needed
        super.setNeutralMode(NeutralMode.Brake);
    }

    public void coast() {
//        this.set(0); not needed
        super.setNeutralMode(NeutralMode.Coast);
    }


    // Just do if getEncoder != null
//    public boolean hasEncoder() {
//        return !(encoder == null);
//    }

    public Encoder getEncoder() {
        return encoder;
    }

    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    public int getChannel() {
        return super.getDeviceID();
    }

    public boolean isReversed() {
        return super.getInverted();
    }

    public double getPercentSpeed() {
        return super.getMotorOutputPercent();
    }

    public double getCurrent() {
        return super.getStatorCurrent();
    }

    public double getSpeed() {
        if (getEncoder() != null)
            return 0;
        return encoder.getRate(); // was .getSpeed not sure if this is right
    }

    public double getError() {
        return super.getClosedLoopError(0);
    }

    public void stop() {
        super.stopMotor();
    }

    //This may be why we have so much can usage
//    public void follow(TitanFX other) {
//        other.brownoutFollower = this;
//        this.set(ControlMode.Follower, other.getChannel());
//    }


    public void setupCurrentLimiting(int currentLimit, int currentLimitThreshold, int limitTimeout) {
        SupplyCurrentLimitConfiguration currentLimitor = new SupplyCurrentLimitConfiguration(true,
                currentLimit, currentLimitThreshold, limitTimeout);
        this.configSupplyCurrentLimit(currentLimitor);
        this.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, currentLimit, currentLimitThreshold, limitTimeout));
        // todo change above maybe idk

    }

    public void disableCurrentLimiting() {
//	    this.enableCurrentLimit(false);
    }

    public void enableBrownoutProtection() {
        if (brownoutFollower != null) {
            brownoutFollower.coast();
        }
        brownout = true;
    }

    public void disableBrownoutProtection() {
        if (brownoutFollower != null && brownout) {
            brownoutFollower.setNeutralMode(NeutralMode.Brake);
            brownoutFollower.set(ControlMode.Follower, getChannel());
        }
        brownout = false;
    }

    public void configPID(double P, double D, double F) {
        configPID(P, 0, D, F, 0);
    }

    public void configPID(double P, double I, double D, double F, int iZone) {
        // If these ever need to be nonzero, we can make them parameters instead
        final int pidSlot = 0, profileSlot = 0;

        configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, pidSlot, TIMEOUT_MS);
        setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TIMEOUT_MS);
        setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, TIMEOUT_MS);

        configNominalOutputForward(0, TIMEOUT_MS);
        configNominalOutputReverse(0, TIMEOUT_MS);
        configPeakOutputForward(1, TIMEOUT_MS);
        configPeakOutputReverse(-1, TIMEOUT_MS);

        // MARK - PIDF stuff
        selectProfileSlot(profileSlot, pidSlot);
        config_kF(profileSlot, F, TIMEOUT_MS);
        config_kP(profileSlot, P, TIMEOUT_MS);
        config_kI(profileSlot, I, TIMEOUT_MS);
        config_kD(profileSlot, D, TIMEOUT_MS);
        config_IntegralZone(profileSlot, iZone, TIMEOUT_MS);
    }

    public void postEstimatedKf(String name) {
        double speed = getSelectedSensorVelocity(0);
        if (Math.abs(speed) > 10) {
        }
    }
}
