package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constants.Constants25573;

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
        frontLeft = hardwareMap.get(DcMotor.class, Constants25573.FRONT_LEFT_MOTOR);
        frontRight = hardwareMap.get(DcMotor.class, Constants25573.FRONT_RIGHT_MOTOR);
        backLeft = hardwareMap.get(DcMotor.class,Constants25573.BACK_LEFT_MOTOR);
        backRight = hardwareMap.get(DcMotor.class,Constants25573.BACK_RIGHT_MOTOR);

        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while(opModeIsActive()) {
                //TODO: add loop code here
                if (gamepad1.x) {
                    frontLeft.setPower(0.1);
                } else {
                    frontLeft.setPower(0);
                }
                if (gamepad1.y) {
                    frontRight.setPower(0.1);
                } else {
                    frontRight.setPower(0);
                }
                if (gamepad1.a) {
                    backLeft.setPower(0.1);
                } else {
                    backLeft.setPower(0);
                }
                if (gamepad1.b) {
                    backRight.setPower(0.1);
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
