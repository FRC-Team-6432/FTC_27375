package org.firstinspires.ftc.teamcode.subsystems.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.AprilTagVision;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

import java.util.ArrayList;

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

        this.backLeft.setPower(0);
        this.backRight.setPower(0);
        this.frontRight.setPower(0);
        this.frontLeft.setPower(0);

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

    public void goToTargetPose(AprilTagPoseFtc currentPose, AprilTagPoseFtc targetPose, double gain) {
        if (currentPose == null || targetPose == null) {
            return;
        }

        double xError = targetPose.x - currentPose.x;
        double yError = targetPose.y - currentPose.y;
        double yawError = targetPose.yaw - currentPose.yaw;

        this.forward = yError * -gain;
        this.strafe = xError * gain;
        this.rotate = yawError * gain;
    }

    public void telemetry(){
        telemetry.addLine("_____DRIVE_____");
        telemetry.addData("forward: ", forward);
        telemetry.addData("rotate: ", rotate);
        telemetry.addData("strafe: ", strafe);
        telemetry.addLine("________________");
    }
}

