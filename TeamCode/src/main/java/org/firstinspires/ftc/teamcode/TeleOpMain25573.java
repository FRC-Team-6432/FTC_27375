package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants.Constants25573;
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterSubsystem;


@TeleOp
public class TeleOpMain25573 extends LinearOpMode {

    @Override
    public void runOpMode() {
        // TODO: add initialise code here
        ShooterSubsystem pollenShooter = new ShooterSubsystem(
                hardwareMap.tryGet(DcMotor.class, Constants25573.POLLENSHOOTER_MOTOR),
                telemetry,
                DcMotorSimple.Direction.REVERSE,
                hardwareMap.tryGet(Servo.class, Constants25573.POLLENSHOOTER_SERVO)
        );

        ShooterSubsystem nectarShooter = new ShooterSubsystem(
                hardwareMap.tryGet(DcMotor.class, Constants25573.NECTARSHOOTER_MOTOR),
                telemetry,
                DcMotorSimple.Direction.FORWARD,
                hardwareMap.tryGet(Servo.class, Constants25573.NECTARSHOOTER_SERVO)
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
                drive.setDriveParameters(-gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
                drive.drive();
                pollenShooter.handle(gamepad1.right_trigger_pressed, false);
                nectarShooter.handle(gamepad1.left_trigger_pressed, false);
                intake.handle(gamepad1.right_bumper, gamepad1.left_bumper);

                pollenShooter.calibrate(gamepad1.dpadLeftWasPressed(), gamepad1.dpadRightWasPressed(),gamepad1.xWasPressed(),gamepad1.bWasPressed());
                nectarShooter.calibrate(gamepad1.dpadUpWasPressed(), gamepad1.dpadDownWasPressed(),gamepad1.yWasPressed(),gamepad1.aWasPressed());

                telemetry.addData("Runtime: ", getRuntime());
                pollenShooter.telemetry();
                nectarShooter.telemetry();
                intake.telemetry();
                telemetry.update();
            }
        }
    }
}