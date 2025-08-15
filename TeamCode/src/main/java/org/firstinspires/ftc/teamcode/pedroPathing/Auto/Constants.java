package org.firstinspires.ftc.teamcode.pedroPathing.Auto;

import com.pedropathing.localization.Pose;

public class Constants {
    public final Pose START_POS = new Pose(0,0,Math.toRadians(0));
    public final Pose POS_ONE = new Pose(-12,6,Math.toRadians(76));
    public final Pose POS_TWO = new Pose(12,-6,Math.toRadians(200));
    public final Pose POS_THREE = new Pose(24,0,Math.toRadians(76));
    public final Pose POS_FOUR = new Pose(0,24,Math.toRadians(13));
    public final Pose END_POS = new Pose(24, 24, Math.toRadians(0));
    public final double MOVEMENT_SPEED = 0.5;
    public final double PRECISION_SPEED = 0.3;
    public final double ACTION_TIMEOUT = 5.0;
    public final double SETTLING_TIME = 0.5;
}
