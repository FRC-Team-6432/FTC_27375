package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveSubsystem {

   private final DcMotor frontLeft;
    private final DcMotor frontRight;
    private final DcMotor backLeft;
    private final DcMotor backRight;

    private final Telemetry telemetry;

    private double forward;

    private double rotate;

    private double strafe;

    public DriveSubsystem(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight, Telemetry telemetry) {

        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;

        if (frontLeft != null && frontRight != null && backLeft != null && backRight != null) {
            frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            this.backLeft.setPower(0);
            this.backRight.setPower(0);
            this.frontRight.setPower(0);
            this.frontLeft.setPower(0);
        }


        this.telemetry = telemetry;
    }

    public void setDriveParameters(double forward, double rotate, double strafe) {
        this.forward = forward;
        this.strafe = strafe;
        this.rotate = rotate;
    }

    public void drive() {
        // drive code
        frontLeft.setPower(forward+rotate+strafe);
        frontRight.setPower(forward-rotate-strafe);
        backLeft.setPower(forward+rotate-strafe);
        backRight.setPower(forward-rotate+strafe);
    }

    public void setTelemetry(){
        telemetry.addLine("_____DRIVE_____");
        telemetry.addData("forward: ", forward);
        telemetry.addData("rotate: ", rotate);
        telemetry.addData("strafe: ", strafe);
        telemetry.addLine("________________");
    }
}

