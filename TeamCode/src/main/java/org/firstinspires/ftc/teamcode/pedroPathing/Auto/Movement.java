package org.firstinspires.ftc.teamcode.pedroPathing.Auto;




import org.firstinspires.ftc.teamcode.pedroPathing.Auto.Constants;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Movement extends LinearOpMode {

    private final Constants constants = new Constants();

    public void runOpMode()
    {
        telemetry.addData("test", constants.ACTION_TIMEOUT);
    }
}
