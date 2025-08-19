package org.firstinspires.ftc.teamcode.pedroPathing.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.GoBildaPinpointDriver;
import com.pedropathing.localization.constants.PinpointConstants;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

@TeleOp
public class Test extends LinearOpMode {
    GoBildaPinpointDriver pinpoint = null;
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
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        pinpoint.initialize();
        pinpoint.resetPosAndIMU();

        waitForStart();
        while(opModeIsActive())
        {
            if(gamepad1.a) {
                frontLeft.setPower(.5);
                telemetry.addData("Motor", "Front Left");
            } else if (gamepad1.b) {
                frontRight.setPower(.5);
                telemetry.addData("Motor", "Front Right");
            } else if (gamepad1.y) {
                backLeft.setPower(.5);
                telemetry.addData("Motor", "Back Left");
            } else if (gamepad1.x) {
                backRight.setPower(.5);
                telemetry.addData("Motor", "Back Right");
            } else {
                frontRight.setPower(0);
                frontLeft.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);
            }
            f.update();
            telemetry.addData("x",f.getPose().getX());
            telemetry.addData("y",f.getPose().getY());
            telemetry.addData("heading",f.getPose().getHeading());
            telemetry.update();
        }

    }
}
