package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class TeleOp_Main extends LinearOpMode {

    @Override
    public void runOpMode() {
        // TODO: add initialise code here
        ShooterSubsystem shooter = new ShooterSubsystem(null, telemetry);
        IntakeSubsystem intake = new IntakeSubsystem(null, telemetry);
        DriveSubsystem drive = new DriveSubsystem(null, null, null, null, telemetry);

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                //TODO: add loop code here
                drive.setDriveParameters(-gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
                drive.drive();

                shooter.handle(gamepad1.right_bumper, gamepad1.right_trigger_pressed);
                intake.handle(gamepad1.left_bumper, gamepad1.left_trigger_pressed);
                telemetry.addData("Runtime: ", getRuntime());
                shooter.telemetry();
                intake.telemetry();
                telemetry.update();
            }
        }
    }
}