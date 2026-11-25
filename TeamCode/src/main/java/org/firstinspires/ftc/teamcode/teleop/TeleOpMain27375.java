package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.constants.Constants25573;
import org.firstinspires.ftc.teamcode.constants.Constants27375;
import org.firstinspires.ftc.teamcode.robotContainer.RobotContainer27375;


@TeleOp(group = "27375")
public class TeleOpMain27375 extends LinearOpMode {
    public RobotContainer27375 robot;

    @Override
    public void runOpMode() {
       this.robot =  new RobotContainer27375(hardwareMap, telemetry);

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (gamepad1.x) {
                    robot.driveSubsystem.goToTargetPose(
                            robot.aprilTagVision.getFtcPose(),
                            Constants27375.SHOOTING_POSE,
                            Constants27375.TRANSLATE_GAIN,
                            Constants27375.YAW_GAIN
                    );
                } else {
                    robot.driveSubsystem.setDriveParameters(-gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
                }

                robot.driveSubsystem.drive();

                robot.shooterSubsystem.handle(gamepad1.left_bumper, gamepad1.left_trigger_pressed);
                robot.intakeSubsystem.handle(gamepad1.right_bumper, gamepad1.right_trigger_pressed);

                telemetry.addData("Runtime: ", getRuntime());
                robot.aprilTagVision.telemetry();
                robot.driveSubsystem.telemetry();
                robot.intakeSubsystem.telemetry();
                robot.shooterSubsystem.telemetry();
                telemetry.update();
            }
        }
    }
}