package org.firstinspires.ftc.teamcode.auto27375;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotContainer.RobotContainer27375;

@Autonomous
public class AutoCode1 extends LinearOpMode {
    private DriveAimShoot driveAimShoot;
    private RobotContainer27375 robot;

    @Override
    public void runOpMode() throws InterruptedException {
        this.robot =  new RobotContainer27375(hardwareMap, telemetry);
        driveAimShoot = new DriveAimShoot(
                this, robot,
                0.5, 0, 0,
                3, 3
        );

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                driveAimShoot.run();
            }
        }
    }
}
