package org.firstinspires.ftc.teamcode.pedroPathing.Auto;




import org.firstinspires.ftc.teamcode.pedroPathing.Auto.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

import com.pedropathing.localization.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
@Autonomous(name = "Movement Auto")
public class Movement extends OpMode {

    private final Constants constants = new Constants();
    private Follower follower;

    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;

    private Path start,end;

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public void loop() {

        // These loop the movements of the robot
        follower.update();
        //autonomousPathUpdate();

        // Feedback to Driver Hub
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

//        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
//        follower.setStartingPose(startPose);
//        buildPaths();
    }

    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {}

    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    /** We do not use this because everything should automatically disable **/
    @Override
    public void stop() {
    }
}
