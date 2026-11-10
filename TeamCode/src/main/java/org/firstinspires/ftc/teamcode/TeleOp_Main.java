package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp
public class TeleOp_Main extends LinearOpMode {

    @Override
    public void runOpMode() {
        // TODO: add initialise code here
        ShooterSubsystem pollenShooter = new ShooterSubsystem(hardwareMap.tryGet(DcMotor.class,
                Constants.Drive.POLLENSHOOTER_MOTOR), telemetry);
        ShooterSubsystem nectarShooter = new ShooterSubsystem(hardwareMap.tryGet(DcMotor.class,
                Constants.Drive.NECTARSHOOTER_MOTOR), telemetry);
        IntakeSubsystem intake = new IntakeSubsystem(hardwareMap.tryGet(DcMotor.class,
                Constants.Drive. INTAKE_MOTOR), telemetry);
        DriveSubsystem drive = new DriveSubsystem(
                hardwareMap.tryGet(DcMotor.class, Constants.Drive.FRONT_LEFT_MOTOR),
                hardwareMap.tryGet(DcMotor.class, Constants.Drive.FRONT_RIGHT_MOTOR),
                hardwareMap.tryGet(DcMotor.class, Constants.Drive.BACK_LEFT_MOTOR),
                hardwareMap.tryGet(DcMotor.class, Constants.Drive.BACK_RIGHT_MOTOR),
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
                pollenShooter.handle(gamepad1.right_trigger_pressed, false);
                nectarShooter.handle(gamepad1.left_trigger_pressed, false);
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