package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.drive.DriveSubsystem25573;

@TeleOp
@Disabled
public class DriveTest extends LinearOpMode {
    //TODO: define motors, sensors, processors here


    @Override
    public void runOpMode() throws InterruptedException {
        // TODO: add initialise code here
        DriveSubsystem25573 driveSubsystem = new DriveSubsystem25573(
                hardwareMap.get(DcMotor.class, "front left"),
                hardwareMap.get(DcMotor.class, "front right"),
                hardwareMap.get(DcMotor.class, "back left"),
                hardwareMap.get(DcMotor.class, "back right"),
                telemetry
        );

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while(opModeIsActive()) {
                //TODO: add loop code here
                driveSubsystem.setDriveParameters(gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
                driveSubsystem.drive();
                telemetry.addData("Runtime: ", getRuntime());
                telemetry.update();
            }
        }
    }
}
