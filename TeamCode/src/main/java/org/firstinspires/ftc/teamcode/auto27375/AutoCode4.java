package org.firstinspires.ftc.teamcode.auto27375;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robotContainer.RobotContainer27375;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

@TeleOp
public class AutoCode4 extends LinearOpMode {
    //TODO: define motors, sensors, processors here

    @Override
    public void runOpMode() throws InterruptedException {
        RobotContainer27375 robot = new RobotContainer27375(hardwareMap, telemetry);
        double endTime = 0;

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                //TODO: add loop code here

                while (robot.aprilTagVision.getFtcPose() == null) {
                    robot.driveSubsystem.setDriveParameters(0.5, 0.5, 0.5);
                }
                endTime = getRuntime() + 3;
                while (getRuntime() < endTime) {
                    robot.driveSubsystem.goToTargetPose(robot.aprilTagVision.getFtcPose(),
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
                            .001
                    );

                    endTime = getRuntime() + 3;
                    while (getRuntime() < endTime) {
                        robot.shooterSubsystem.handle(true, false);
                    }

                    telemetry.addData("Runtime: ", getRuntime());
                    telemetry.update();
                }
            }
        }

    }
}
