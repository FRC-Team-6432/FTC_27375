package org.firstinspires.ftc.teamcode.auto25573;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.constants.Constants25573;
import org.firstinspires.ftc.teamcode.robotContainer.RobotContainer25573;

@Autonomous(group = "25573")
public class Shoot_And_Park_Auto_25573 extends LinearOpMode {
    private DriveAimShootPark25573 driveAimShootPark25573;
    private RobotContainer25573 robot;

    @Override
    public void runOpMode() throws InterruptedException {
        this.robot =  new RobotContainer25573(hardwareMap, telemetry);
        driveAimShootPark25573 = new DriveAimShootPark25573(
                this, robot,
                Constants25573.Auto.FORWARD_B1, Constants25573.Auto.ROTATE_B1, Constants25573.Auto.STRAFE_B1,
                5, 7, 5,
                -0.4, 0, 0, 5,
                0,0,0.5, 4
        );

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            driveAimShootPark25573.run();
        }
    }
}
