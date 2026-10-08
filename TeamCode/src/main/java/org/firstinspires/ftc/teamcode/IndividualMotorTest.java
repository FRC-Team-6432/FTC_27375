package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class IndividualMotorTest extends LinearOpMode {
    //TODO: define motors, sensors, processors here
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    @Override
    public void runOpMode() throws InterruptedException {
        // TODO: add initialise code here
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class,"backLeft");
        backRight = hardwareMap.get(DcMotor.class,"backRight");

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while(opModeIsActive()) {
                //TODO: add loop code here
                if (gamepad1.x) {
                    frontLeft.setPower(1);
                } else {
                    frontLeft.setPower(0);
                }
                if (gamepad1.y) {
                    frontRight.setPower(1);
                } else {
                    frontRight.setPower(0);
                }
                if (gamepad1.a) {
                    backLeft.setPower(1);
                } else {
                    backLeft.setPower(0);
                }
                if (gamepad1.b) {
                    backRight.setPower(1);
                } else {
                    backRight.setPower(0);
                }

                telemetry.addData("Runtime: ", getRuntime());
                telemetry.update();
            }
        }
    }

    private void drive(double forward) {
        // drive code
        frontLeft.setPower(forward);
        frontRight.setPower(forward);
        backLeft.setPower(forward);
        backRight.setPower(forward);
    }
}
