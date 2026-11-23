package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Constants.Constants25573;
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterSubsystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;


@TeleOp
public class TeleOpMain25573 extends LinearOpMode {

    @Override
    public void runOpMode() {
        // TODO: add initialise code here
        RobotContainer25573 robot = new RobotContainer25573(hardwareMap, telemetry);

        telemetry.addLine("Initialised!");
        telemetry.update();

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                //TODO: add loop code here
                AprilTagPoseFtc ftcPose = robot.aprilTagVision.getFtcPose();
                AprilTagPoseFtc targetPose = new AprilTagPoseFtc(
                        0,
                        -1400,
                        0
                        , 0, 0, 0,
                        0,
                        0,
                        0
                );
                if (gamepad1.x) {
                    robot.drivebase.goToTargetPose(
                            ftcPose,
                            targetPose,
                            0.003,
                            0.05
                    );
                } else {
                    robot.drivebase.setDriveParameters(-gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
                }

                robot.drivebase.drive();
                robot.pollenShooter.handle(gamepad1.left_trigger_pressed, false, gamepad1.left_bumper);
                robot.nectarShooter.handle(gamepad1.right_trigger_pressed, false, gamepad1.right_bumper);
                robot.intake.handle(gamepad1.a, gamepad1.b);

//                pollenShooter.calibrateOffset(gamepad1.dpadLeftWasPressed(), gamepad1.dpadRightWasPressed(), 0.02);
//                nectarShooter.calibrateOffset(gamepad1.dpadUpWasPressed(), gamepad1.dpadDownWasPressed(), 0.02);

                robot.nectarShooter.calibratePower(gamepad1.dpadLeftWasPressed(), gamepad1.dpadRightWasPressed(), 0.02);
                robot.pollenShooter.calibratePower(gamepad1.dpadUpWasPressed(), gamepad1.dpadDownWasPressed(), 0.02);


                telemetry.addData("Runtime: ", getRuntime());
                robot.aprilTagVision.telemetry();
//                pollenShooter.telemetry();
//                nectarShooter.telemetry();
//                intake.telemetry();
                robot.drivebase.telemetry();
                telemetry.update();
            }
            robot.aprilTagVision.closeVisionPortal();
        }
    }
}