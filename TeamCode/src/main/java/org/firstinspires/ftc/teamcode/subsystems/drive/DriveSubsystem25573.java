package org.firstinspires.ftc.teamcode.subsystems.drive;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.Constants25573;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

public class DriveSubsystem25573 {
    private final DcMotor frontLeft;
    private final DcMotor frontRight;
    private final DcMotor backLeft;
    private final DcMotor backRight;
    private final Telemetry telemetry;
    private double forward;
    private double rotate;
    private double strafe;

    public DriveSubsystem25573(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight, Telemetry telemetry) {

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
        frontLeft.setPower((forward+rotate+strafe) * Constants25573.DRIVE_GAIN);
        frontRight.setPower((forward-rotate-strafe) * Constants25573.DRIVE_GAIN);
        backLeft.setPower((forward+rotate-strafe) * Constants25573.DRIVE_GAIN);
        backRight.setPower((forward-rotate+strafe) * Constants25573.DRIVE_GAIN);
    }

    public void goToTargetPose(AprilTagPoseFtc currentPose, AprilTagPoseFtc targetPose, double translateGain, double yawGain) {
        if (currentPose == null || targetPose == null) {
            setDriveParameters(0,0,0);
            return;
        }

        double xError = targetPose.x - currentPose.x;
        double yError = targetPose.y - currentPose.y;
        double yawError = targetPose.yaw - currentPose.yaw;

        setDriveParameters(yError * -translateGain, yawError * yawGain,xError * translateGain);
    }

    public void telemetry(){
        telemetry.addLine("_____DRIVE_____");
        telemetry.addData("forward: ", forward);
        telemetry.addData("rotate: ", rotate);
        telemetry.addData("strafe: ", strafe);
        telemetry.addLine("________________");
    }
}

