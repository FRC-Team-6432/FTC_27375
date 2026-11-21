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
       RobotContainer27375 robot =  new RobotContainer27375(hardwareMap, telemetry);

        telemetry.addLine("Initialised!");
        telemetry.update();

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                //TODO: add loop code here
                robot.driveSubsystem.setDriveParameters(-gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
                robot.driveSubsystem.drive();

                robot.shooterSubsystem.handle(gamepad1.left_bumper, gamepad1.left_trigger_pressed);
                robot.intakeSubsystem.handle(gamepad1.right_bumper, gamepad1.right_trigger_pressed);

                telemetry.addData("Runtime: ", getRuntime());
                robot.driveSubsystem.telemetry();
                robot.intakeSubsystem.telemetry();
                robot.shooterSubsystem.telemetry();
                telemetry.update();
            }
        }
    }
}