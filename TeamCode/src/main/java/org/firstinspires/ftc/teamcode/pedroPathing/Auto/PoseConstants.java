package org.firstinspires.ftc.teamcode.pedroPathing.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.Pose;

@Config
public class PoseConstants {
    public final Pose START_POSE = new Pose(0,0,Math.toRadians(0));
    public final Pose POSE_ONE = new Pose(12,0,Math.toRadians(0));
    public final Pose POSE_TWO = new Pose(0,0,Math.toRadians(0));
    public final Pose POSE_THREE = new Pose(12,0,Math.toRadians(0));
    public final Pose POSE_FOUR = new Pose(-12,0,Math.toRadians(0));
    public final Pose END_POSE = new Pose(0, 0, Math.toRadians(0));
    public static final double MOVEMENT_SPEED = 0.5;
    public static final double PRECISION_SPEED = 0.3;
    public static final double ACTION_TIMEOUT = 5.0;
    public static final double SETTLING_TIME = 0.5;
}
