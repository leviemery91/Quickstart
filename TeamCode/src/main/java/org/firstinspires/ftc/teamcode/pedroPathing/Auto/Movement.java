package org.firstinspires.ftc.teamcode.pedroPathing.Auto;




import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Auto.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
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

    private Telemetry telemetryA;

    int num = 0;
    private final Constants constants = new Constants();
    private Follower follower;

    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;

    private PathChain one, two, three;

    private Path start,end;

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public void loop() {

        // These loop the movements of the robot
        follower.update();
        autonomousPathUpdate();
        num++;

        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryA.addData("voltage", num);
        follower.telemetryDebug(telemetryA);
        telemetryA.update();
        // Feedback to Driver Hub
//        telemetry.addData("path state", pathState);
//        telemetry.addData("x", follower.getPose().getX());
//        telemetry.addData("y", follower.getPose().getY());
//        telemetry.addData("heading", follower.getPose().getHeading());
//        telemetry.update();
    }

    @Override
    public void init() {

        pathTimer = new Timer();
        actionTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(constants.START_POSE);

        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());

        telemetry.addData("startpose", constants.START_POSE.getHeading());


        telemetry.update();


        buildPaths();
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

    public void buildPaths(){

        start = new Path(new BezierLine(new Point(constants.START_POSE), new Point(constants.POSE_ONE)));
        start.setLinearHeadingInterpolation(constants.START_POSE.getHeading(), constants.POSE_ONE.getHeading());


        one = follower.pathBuilder()
                .addPath(new BezierLine(new Point(constants.POSE_ONE), new Point(constants.POSE_TWO)))
                .setLinearHeadingInterpolation(constants.POSE_ONE.getHeading(), constants.POSE_TWO.getHeading())
                .build();

        two = follower.pathBuilder()
                .addPath(new BezierLine(new Point(constants.POSE_TWO), new Point(constants.POSE_THREE)))
                .setLinearHeadingInterpolation(constants.POSE_TWO.getHeading(), constants.POSE_THREE.getHeading())
                .build();

        three = follower.pathBuilder()
                .addPath(new BezierLine(new Point(constants.POSE_THREE), new Point(constants.POSE_FOUR)))
                .setLinearHeadingInterpolation(constants.POSE_THREE.getHeading(), constants.POSE_FOUR.getHeading())
                .build();

        end = new Path(new BezierLine(new Point(constants.POSE_FOUR), new Point(constants.END_POSE)));
        end.setLinearHeadingInterpolation(constants.POSE_FOUR.getHeading(), constants.END_POSE.getHeading());
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(start);
                setPathState(1);
               // actionTimer.resetTimer();
                break;
            case 1:

                /* You could check for
                - Follower State: "if(!follower.isBusy() {}"
                - Time: "if(pathTimer.getElapsedTimeSeconds() > 1) {}"
                - Robot Position: "if(follower.getPose().getX() > 36) {}"
                */

                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Preload */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(one,true);
                    setPathState(2);
                    pathTimer.resetTimer();
                }
                break;
            case 2:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(two,true);
                    setPathState(3);
                    pathTimer.resetTimer();
                }
                break;
            case 3:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(three,true);
                    setPathState(4);
                    pathTimer.resetTimer();
                }
                break;
            case 4:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are parked */
                    follower.followPath(end,true);
                    setPathState(5);
                }
                break;
            case 5:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Level 1 Ascent */

                    /* Set the state to a Case we won't use or define, so it just stops running an new paths */
                    setPathState(-1);
                }
                break;
        }
    }





}
