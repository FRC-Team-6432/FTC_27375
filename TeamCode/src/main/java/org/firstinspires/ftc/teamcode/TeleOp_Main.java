package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.drive.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterDirection;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterSubsystem;


@TeleOp
public class TeleOp_Main extends LinearOpMode {

    @Override
    public void runOpMode() {
        // TODO: add initialise code here
        ShooterSubsystem pollenShooter = new ShooterSubsystem(
                hardwareMap.tryGet(DcMotor.class, Constants.POLLENSHOOTER_MOTOR),
                telemetry,
                DcMotorSimple.Direction.REVERSE,
                hardwareMap.tryGet(Servo.class,Constants.POLLENSHOOTER_SERVO)
        );

        ShooterSubsystem nectarShooter = new ShooterSubsystem(
                hardwareMap.tryGet(DcMotor.class, Constants.NECTARSHOOTER_MOTOR),
                telemetry,
                DcMotorSimple.Direction.FORWARD,
                hardwareMap.tryGet(Servo.class,Constants.NECTARSHOOTER_SERVO)
        );

        IntakeSubsystem intake = new IntakeSubsystem(hardwareMap.tryGet(DcMotor.class,
                Constants.INTAKE_MOTOR), telemetry);

        DriveSubsystem drive = new DriveSubsystem(
                hardwareMap.tryGet(DcMotor.class, Constants.FRONT_LEFT_MOTOR),
                hardwareMap.tryGet(DcMotor.class, Constants.FRONT_RIGHT_MOTOR),
                hardwareMap.tryGet(DcMotor.class, Constants.BACK_LEFT_MOTOR),
                hardwareMap.tryGet(DcMotor.class, Constants.BACK_RIGHT_MOTOR),
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
                pollenShooter.handle(gamepad1.right_trigger_pressed, false, gamepad1.right_trigger);
                nectarShooter.handle(gamepad1.left_trigger_pressed, false, gamepad1.left_trigger);
                intake.handle(gamepad1.right_bumper, gamepad1.left_bumper);

                telemetry.addData("Runtime: ", getRuntime());
                pollenShooter.telemetry();
                nectarShooter.telemetry();
                intake.telemetry();
                telemetry.update();
            }
        }
    }
}