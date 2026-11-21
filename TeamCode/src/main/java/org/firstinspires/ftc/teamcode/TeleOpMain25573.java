package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
        ShooterSubsystem pollenShooter = new ShooterSubsystem(
                "Pollen Shooter",
                hardwareMap.tryGet(DcMotor.class, Constants25573.POLLENSHOOTER_MOTOR),
                hardwareMap.tryGet(Servo.class, Constants25573.POLLENSHOOTER_SERVO),
                DcMotorSimple.Direction.REVERSE,
                Constants25573.POLLEN_SERVO_OFFSET,
                Constants25573.POLLEN_POWER,
                telemetry
        );

        ShooterSubsystem nectarShooter = new ShooterSubsystem(
                "Nectar Shooter",
                hardwareMap.tryGet(DcMotor.class, Constants25573.NECTARSHOOTER_MOTOR),
                hardwareMap.tryGet(Servo.class, Constants25573.NECTARSHOOTER_SERVO),
                DcMotorSimple.Direction.FORWARD,
                Constants25573.NECTAR_SERVO_OFFSET,
                Constants25573.NECTAR_POWER,
                telemetry
        );

        IntakeSubsystem intake = new IntakeSubsystem(hardwareMap.tryGet(DcMotor.class,
                Constants25573.INTAKE_MOTOR), telemetry);

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, Constants25573.FRONT_LEFT_MOTOR);
        DcMotor frontRight = hardwareMap.get(DcMotor.class, Constants25573.FRONT_RIGHT_MOTOR);
        DcMotor backLeft = hardwareMap.get(DcMotor.class, Constants25573.BACK_LEFT_MOTOR);
        DcMotor backRight = hardwareMap.get(DcMotor.class, Constants25573.BACK_RIGHT_MOTOR);

        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        DriveSubsystem drive = new DriveSubsystem(
                frontLeft,
                frontRight,
                backLeft,
                backRight,
                telemetry
        );

        AprilTagVision aprilTagVision = new AprilTagVision(
                hardwareMap.get(WebcamName.class, "Webcam 1"),
                telemetry
        );

        telemetry.addLine("Initialised!");
        if (hardwareMap.tryGet(Servo.class, Constants25573.NECTARSHOOTER_SERVO) != null) {
            telemetry.addLine("nectar servo ok");
        }

        if (hardwareMap.tryGet(Servo.class, Constants25573.POLLENSHOOTER_SERVO) != null) {
            telemetry.addLine("pollen servo ok");
        }
        telemetry.update();

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                //TODO: add loop code here
                AprilTagPoseFtc ftcPose = aprilTagVision.getFtcPose();
                AprilTagPoseFtc targetPose = new AprilTagPoseFtc(
                        0,
                        -1000,
                        0
                        , 0, 0, 0,
                        0,
                        0,
                        0
                );
                if (gamepad1.x) {
                    drive.goToTargetPose(
                            ftcPose,
                            targetPose,
                            0.004,
                            0.01
                    );
                } else {
                    drive.setDriveParameters(-gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
                }

                drive.drive();
                pollenShooter.handle(gamepad1.left_trigger_pressed, false, gamepad1.left_bumper);
                nectarShooter.handle(gamepad1.right_trigger_pressed, false, gamepad1.right_bumper);
                intake.handle(gamepad1.a, gamepad1.b);

//                pollenShooter.calibrateOffset(gamepad1.dpadLeftWasPressed(), gamepad1.dpadRightWasPressed(), 0.02);
//                nectarShooter.calibrateOffset(gamepad1.dpadUpWasPressed(), gamepad1.dpadDownWasPressed(), 0.02);

                nectarShooter.calibratePower(gamepad1.dpadLeftWasPressed(), gamepad1.dpadRightWasPressed(), 0.02);
                pollenShooter.calibratePower(gamepad1.dpadUpWasPressed(), gamepad1.dpadDownWasPressed(), 0.02);


                telemetry.addData("Runtime: ", getRuntime());
                aprilTagVision.telemetry();
//                pollenShooter.telemetry();
//                nectarShooter.telemetry();
//                intake.telemetry();
                drive.telemetry();
                telemetry.update();
            }
            aprilTagVision.closeVisionPortal();
        }
    }
}