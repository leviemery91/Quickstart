package org.firstinspires.ftc.teamcode.pedroPathing.Auto;

import com.pedropathing.localization.Pose;

public class Constants {
    public final Pose START_POSE = new Pose(0,0,0);
    public final Pose POSE_ONE = new Pose(-24,12,0);
    public final Pose POSE_TWO = new Pose(24,-12,0);
    public final Pose POSE_THREE = new Pose(48,0,0);
    public final Pose POSE_FOUR = new Pose(0,48,0);
    public final Pose END_POSE = new Pose(48, 48, 0);
    public final double MOVEMENT_SPEED = 0.5;
    public final double PRECISION_SPEED = 0.3;
    public final double ACTION_TIMEOUT = 5.0;
    public final double SETTLING_TIME = 0.5;
}
