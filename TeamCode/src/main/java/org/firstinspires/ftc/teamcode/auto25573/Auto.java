package org.firstinspires.ftc.teamcode.auto25573;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotContainer.RobotContainer25573;

@Autonomous(group = "25573")
public class Auto extends LinearOpMode {
    private DriveAimShoot25573 driveAimShoot25573;
    private RobotContainer25573 robot;

    @Override
    public void runOpMode() throws InterruptedException {
        // TODO: add initialise code here

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while(opModeIsActive()) {
                this.robot =  new RobotContainer25573(hardwareMap, telemetry);
                driveAimShoot25573 = new DriveAimShoot25573(
                        this, robot,
                        0, 0, 1,
                        3, 3
                );

                waitForStart();

                if (opModeIsActive()) {
                    while (opModeIsActive()) {
                        driveAimShoot25573.run();
                    }
                }
            }
        }
    }
}
