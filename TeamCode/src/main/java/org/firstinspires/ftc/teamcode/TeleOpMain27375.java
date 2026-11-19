package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constants.Constants27375;
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterSubsystem27375;


@TeleOp
public class TeleOpMain27375 extends LinearOpMode {

    @Override
    public void runOpMode() {
        // TODO: add initialise code here
        DcMotor shooterMotor = hardwareMap.get(DcMotor.class, Constants27375.SHOOTER_MOTOR);
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        ShooterSubsystem27375 shooterSubsystem = new ShooterSubsystem27375(
            shooterMotor,
            Constants27375.SHOOTER_POWER,
            telemetry
        );

        DcMotor intakeMotor = hardwareMap.get(DcMotor.class, Constants27375.INTAKE_MOTOR);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        IntakeSubsystem intakeSubsystem = new IntakeSubsystem(
                intakeMotor,
                telemetry
        );

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, Constants27375.FRONT_LEFT_MOTOR);
        DcMotor frontRight = hardwareMap.get(DcMotor.class, Constants27375.FRONT_RIGHT_MOTOR);
        DcMotor backLeft = hardwareMap.get(DcMotor.class, Constants27375.BACK_LEFT_MOTOR);
        DcMotor backRight = hardwareMap.get(DcMotor.class, Constants27375.BACK_RIGHT_MOTOR);

        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

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
        telemetry.update();

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                //TODO: add loop code here
                drive.setDriveParameters(-gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
                drive.drive();

                shooterSubsystem.handle(gamepad1.left_bumper, gamepad1.left_trigger_pressed);
                intakeSubsystem.handle(gamepad1.right_bumper, gamepad1.right_trigger_pressed);

                telemetry.addData("Runtime: ", getRuntime());
                drive.telemetry();
                intakeSubsystem.telemetry();
                shooterSubsystem.telemetry();
                telemetry.update();
            }
        }
    }
}