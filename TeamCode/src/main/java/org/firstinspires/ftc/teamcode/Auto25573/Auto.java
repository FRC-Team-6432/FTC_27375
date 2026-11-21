package org.firstinspires.ftc.teamcode.Auto25573;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotContainer25573;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

@Autonomous
public class Auto extends LinearOpMode {
    //TODO: define motors, sensors, processors here
    RobotContainer25573 robot;

    @Override
    public void runOpMode() throws InterruptedException {
        // TODO: add initialise code here

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while(opModeIsActive()) {
                //TODO: add loop code here
                robot.drivebase.setDriveParameters(1, 0.5, 0);
                while(robot.aprilTagVision.getFtcPose() == null) {
                    robot.drivebase.drive();
                }

                robot.drivebase.goToTargetPose(
                        robot.aprilTagVision.getFtcPose(),
                        new AprilTagPoseFtc(
                                0,
                                -1000,
                                0,
                                0,
                                0,
                                0,
                                0,
                                0,
                                0
                        ),
                        0.001,
                        0.001
                );

                telemetry.addData("Runtime: ", getRuntime());
                telemetry.update();
            }
        }
    }
}
