package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robotContainer.RobotContainer25573;
import org.firstinspires.ftc.teamcode.constants.Constants25573;


@TeleOp(group = "25573")
public class TeleOpMain25573 extends LinearOpMode {
    private RobotContainer25573 robot;

    @Override
    public void runOpMode() {
        robot = new RobotContainer25573(hardwareMap, telemetry);

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (gamepad1.x) {
                    robot.driveSubsystem.goToTargetPose(
                            robot.aprilTagVision.getFtcPose(),
                            Constants25573.SHOOTING_POSE,
                            Constants25573.TRANSLATE_GAIN,
                            Constants25573.YAW_GAIN
                    );
                } else {
                    robot.driveSubsystem.setDriveParameters(-gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
                }

                robot.driveSubsystem.drive();
                robot.pollenShooter.handle(gamepad1.left_trigger_pressed, false, gamepad1.left_bumper);
                robot.nectarShooter.handle(gamepad1.right_trigger_pressed, false, gamepad1.right_bumper);
                robot.intake.handle(gamepad1.a, gamepad1.b);

//                pollenShooter.calibrateOffset(gamepad1.dpadLeftWasPressed(), gamepad1.dpadRightWasPressed(), 0.02);
//                nectarShooter.calibrateOffset(gamepad1.dpadUpWasPressed(), gamepad1.dpadDownWasPressed(), 0.02);

                robot.nectarShooter.calibratePower(gamepad1.dpadLeftWasPressed(), gamepad1.dpadRightWasPressed(), 0.02);
                robot.pollenShooter.calibratePower(gamepad1.dpadUpWasPressed(), gamepad1.dpadDownWasPressed(), 0.02);


                telemetry.addData("Runtime: ", getRuntime());
                robot.aprilTagVision.telemetry();
                robot.pollenShooter.telemetry();
                robot.nectarShooter.telemetry();
                robot.intake.telemetry();
                robot.driveSubsystem.telemetry();
                telemetry.update();
            }
            robot.aprilTagVision.closeVisionPortal();
        }
    }
}