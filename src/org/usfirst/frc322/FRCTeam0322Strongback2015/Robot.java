/* Created Sat Jan 23 22:33:11 EST 2016 */
package org.usfirst.frc322.FRCTeam0322Strongback2015;

import org.strongback.Strongback;
import org.strongback.components.AngleSensor;
import org.strongback.components.Motor;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.Switch;
import org.strongback.components.ThreeAxisAccelerometer;
import org.strongback.components.ui.FlightStick;
import org.strongback.components.ui.Gamepad;
import org.strongback.drive.MecanumDrive;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;

public class Robot extends IterativeRobot {
	
	private static final int DRIVE_STICK_PORT = 1;
	private static final int MANIPULATOR_STICK_PORT = 3;
	private static final int LF_MOTOR_PORT = 1;
	private static final int LR_MOTOR_PORT = 2;
	private static final int RF_MOTOR_PORT = 3;
	private static final int RR_MOTOR_PORT = 4;
	private static final SPI.Port ACCEL_PORT = SPI.Port.kOnboardCS1;
	private static final Range ACCEL_RANGE = Range.k2G;
	private static final SPI.Port GYRO_PORT = SPI.Port.kOnboardCS0;

	private MecanumDrive drive;
	private ContinuousRange driveX;
	private ContinuousRange driveY;
	private ContinuousRange driveR;
	
	private ThreeAxisAccelerometer accel;
	private AngleSensor gyro;

	private Switch brake;
	private Switch liftUp;
	private Switch liftDown;
	private Switch wheelsIn;
	private Switch wheelsOut;
	private Switch wheelsInAlt;
	private Switch wheelsOutAlt;
	private Switch wheelsClockwise;
	private Switch wheelsCounterClockwise;
	
    @Override
    public void robotInit() {
    	Motor lf = Hardware.Motors.talon(LF_MOTOR_PORT);
    	Motor lr = Hardware.Motors.talon(LR_MOTOR_PORT);
    	Motor rf = Hardware.Motors.talon(RF_MOTOR_PORT).invert();
    	Motor rr = Hardware.Motors.talon(RR_MOTOR_PORT).invert();
    	accel = Hardware.Accelerometers.accelerometer(ACCEL_PORT, ACCEL_RANGE);
    	gyro = Hardware.AngleSensors.gyroscope(GYRO_PORT);
    	drive = new MecanumDrive(lf,lr,rf,rr,gyro);
    	
    	FlightStick driveStick = Hardware.HumanInterfaceDevices.logitechAttack3D(DRIVE_STICK_PORT);
    	Gamepad manipulatorStick = Hardware.HumanInterfaceDevices.logitechDualAction(MANIPULATOR_STICK_PORT);
    	driveX = driveStick.getPitch();
    	driveY = driveStick.getRoll();
    	driveR = driveStick.getYaw();
    	
    	brake = driveStick.getTrigger();
    	liftUp = manipulatorStick.getButton(1);
    	liftDown = manipulatorStick.getButton(3);
    	wheelsIn = manipulatorStick.getButton(2);
    	wheelsOut = manipulatorStick.getButton(4);
    	wheelsInAlt = manipulatorStick.getButton(5);
    	wheelsOutAlt = manipulatorStick.getButton(6);
    	wheelsClockwise = manipulatorStick.getButton(8);
    	wheelsCounterClockwise = manipulatorStick.getButton(7);
    	
    	Strongback.configure().recordNoEvents().recordNoData().initialize();
    }

    @Override
    public void teleopInit() {
        // Start Strongback functions ...
        Strongback.start();
    }

    @Override
    public void teleopPeriodic() {
    	drive.cartesian(driveX.read(), driveY.read(), driveR.read());
    	
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }

}
