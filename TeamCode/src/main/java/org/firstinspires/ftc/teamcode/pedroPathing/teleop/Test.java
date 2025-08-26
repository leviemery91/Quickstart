package org.firstinspires.ftc.teamcode.pedroPathing.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.GoBildaPinpointDriver;
import com.pedropathing.localization.constants.PinpointConstants;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

@TeleOp
public class Test extends LinearOpMode {
    private Telemetry telemetryA;
    GoBildaPinpointDriver pinpoint = null;
    private VoltageSensor batteryVolt;
    DcMotorEx frontLeft = null;
    DcMotorEx frontRight = null;
    DcMotorEx backLeft = null;
    DcMotorEx backRight = null;
    Follower f;

    @Override
    public void runOpMode() throws InterruptedException {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class,"odo");
        f = new Follower(hardwareMap, FConstants.class, LConstants.class);
        frontLeft = hardwareMap.get(DcMotorEx.class, "leftFront");
        frontRight = hardwareMap.get(DcMotorEx.class, "rightFront");
        backLeft = hardwareMap.get(DcMotorEx.class, "leftBack");
        backRight = hardwareMap.get(DcMotorEx.class, "rightBack");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        batteryVolt = hardwareMap.voltageSensor.iterator().next();


        pinpoint.initialize();
        pinpoint.resetPosAndIMU();

        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryA.update();

        waitForStart();
        while(opModeIsActive())
        {
//            if(gamepad1.a) {
//
//                double power = (gamepad1.right_trigger > .1) ? -.5 : .5;
//                frontLeft.setPower(power);
//                telemetry.addData("Motor", "Front Left");
//            } else if (gamepad1.b) {
//                double power = (gamepad1.right_trigger > .1) ? -.5 : .5;
//                frontRight.setPower(power);
//                telemetry.addData("Motor", "Front Right");
//            } else if (gamepad1.y) {
//                double power = (gamepad1.right_trigger > .1) ? -.5 : .5;
//                backLeft.setPower(power);
//                telemetry.addData("Motor", "Back Left");
//            } else if (gamepad1.x) {
//                double power = (gamepad1.right_trigger > .1) ? -.5 : .5;
//                backRight.setPower(power);
//                telemetry.addData("Motor", "Back Right");
//            } else {
//                frontRight.setPower(0);
//                frontLeft.setPower(0);
//                backLeft.setPower(0);
//                backRight.setPower(0);
//            }


//            f.update();
//            telemetry.addData("x",f.getPose().getX());
//            telemetry.addData("y",f.getPose().getY());
//            telemetry.addData("heading",f.getPose().getHeading());
//

            double y = -gamepad1.left_stick_y;  // Forward/Backward movement
            double x = gamepad1.left_stick_x;   // Strafing (left/right)
            double turn = gamepad1.right_stick_x;  // Turning (rotation)

// Apply non-linear scaling for joystick input (quadratic scaling for smoother low-speed control)
            y = Math.signum(y) * Math.pow(y, 2);  // Square the value for non-linear sensitivity
            x = Math.signum(x) * Math.pow(x, 2);  // Same for x-axis
            turn = Math.signum(turn) * Math.pow(turn, 2);  // And for turn

// Define speed factor based on whether the robot is in slow mode (D-pad up/down for y-axis, left/right for x-axis)
            boolean isSlowModeY = gamepad1.dpad_up || gamepad1.dpad_down;  // Slow mode for forward/backward (y-axis)
            boolean isSlowModeX = gamepad1.dpad_left || gamepad1.dpad_right;  // Slow mode for strafing (x-axis)

// Speed factors for the axes              hiiiii
            double speedFactorY = isSlowModeY ? 0.2 : 1;  // Apply slower speed in slow mode (0.2 for slow, 0.92 for normal)
            double speedFactorX = isSlowModeX ? 0.5 : 1;  // Apply slower speed for strafing (0.5 for slow, 0.92 for normal)

// Handle D-Pad input for strafing (left, right, up, down) with fixed slow mode
            if (gamepad1.dpad_left || gamepad1.dpad_right || gamepad1.dpad_up || gamepad1.dpad_down) {
                // Apply fixed speed for D-Pad movements
                if (gamepad1.dpad_left) {
                    x = -0.5;  // Strafe left
                    y = 0;     // Prevent forward/backward movement while strafing
                } else if (gamepad1.dpad_right) {
                    x = 0.5;   // Strafe right
                    y = 0;     // Prevent forward/backward movement while strafing
                } else if (gamepad1.dpad_up) {
                    y = 0.5;  // Move forward
                    x = 0;     // Prevent strafing while moving forward
                } else if (gamepad1.dpad_down) {
                    y = -0.5;   // Move backward
                    x = 0;     // Prevent strafing while moving backward
                }

                // Ensure that slow mode is active for D-pad actions
                speedFactorY = 0.2;  // Slow down forward/backward motion
                speedFactorX = 0.5;  // Slow down strafing motion
            }

// Denominator calculation for normalizing speed and to prevent division by zero
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(turn), 0.2);

// Calculate power for each wheel
            double frontLeftPower = ((y + x + turn) / denominator) * speedFactorY;
            double backLeftPower = ((y - x + turn) / denominator) * speedFactorY;
            double frontRightPower = ((y - x - turn) / denominator) * speedFactorY;
            double backRightPower = ((y + x - turn) / denominator) * speedFactorY;

// Set power to wheels
            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);


            double volts = batteryVolt.getVoltage();
            telemetryA.addData("voltage", volts);
            telemetryA.addData("Back Left Current", backLeft.getCurrent(CurrentUnit.AMPS));
            telemetryA.addData("Back Right Current", backRight.getCurrent(CurrentUnit.AMPS));
            telemetryA.addData("Front Left Current", frontLeft.getCurrent(CurrentUnit.AMPS));
            telemetryA.addData("Front Right Current", frontRight.getCurrent(CurrentUnit.AMPS));
           // f.telemetryDebug(telemetryA);
            telemetry.update();
            telemetryA.update();


        }

    }
}
