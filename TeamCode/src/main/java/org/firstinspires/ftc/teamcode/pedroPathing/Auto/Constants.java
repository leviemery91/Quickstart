package org.firstinspires.ftc.teamcode.pedroPathing.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.Pose;

@Config
public class Constants {
    public static final Pose START_POSE = new Pose(0,0,Math.toRadians(0));
    public static final Pose POSE_ONE = new Pose(-24,0,Math.toRadians(0));
    public static final Pose POSE_TWO = new Pose(-24,-24,Math.toRadians(0));
    public static final Pose POSE_THREE = new Pose(-48,48,Math.toRadians(0));
    public static final Pose POSE_FOUR = new Pose(-24,-24,Math.toRadians(0));
    public static final Pose END_POSE = new Pose(-24, 0, Math.toRadians(0));
    public static final double MOVEMENT_SPEED = 0.5;
    public static final double PRECISION_SPEED = 0.3;
    public static final double ACTION_TIMEOUT = 5.0;
    public static final double SETTLING_TIME = 0.5;
}
