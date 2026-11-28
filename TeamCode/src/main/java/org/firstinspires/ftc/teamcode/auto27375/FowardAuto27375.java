package org.firstinspires.ftc.teamcode.auto27375;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotContainer.RobotContainer27375;

@Autonomous(group = "27375")
public class FowardAuto27375 extends LinearOpMode {
    private DriveAimShoot27375 driveAimShoot27375;
    private RobotContainer27375 robot;

    @Override
    public void runOpMode() throws InterruptedException {
        this.robot =  new RobotContainer27375(hardwareMap, telemetry);
        driveAimShoot27375 = new DriveAimShoot27375(
                this, robot,
                0, 0, -0.5,
                5, 7,
                0.5,0, 0, 5
        );

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                driveAimShoot27375.run();
            }
        }
    }
}
