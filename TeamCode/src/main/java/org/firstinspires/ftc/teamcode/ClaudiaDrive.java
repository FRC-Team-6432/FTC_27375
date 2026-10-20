package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class ClaudiaDrive extends LinearOpMode {
    //TODO: define motors, sensors, processors here

    private DcMotor frontLeft;

    private DcMotor frontRight;

    private DcMotor backLeft;

    private DcMotor backRight;

    @Override
    public void runOpMode() throws InterruptedException {
        // TODO: add initialise code here

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while(opModeIsActive()) {
                //TODO: add loop code here
                telemetry.addData("Runtime: ", getRuntime());
                telemetry.update();
            }
        }
    }
}
