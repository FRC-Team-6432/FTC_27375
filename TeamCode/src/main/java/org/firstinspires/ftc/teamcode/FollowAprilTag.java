package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@TeleOp
public class FollowAprilTag extends LinearOpMode {
    //TODO: define motors, sensors, processors here
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTagProcessor;

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
            this.visionPortal.close();
        }
    }

    private void initVision() {
        AprilTagLibrary aprilTagLibrary = AprilTagGameDatabase.getBioBuzzTagLibrary();

        this.aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.MM, AngleUnit.DEGREES)
                .setCameraPose(
                        new Position(DistanceUnit.MM, 0, 0, 0, System.nanoTime()),
                        new YawPitchRollAngles(AngleUnit.DEGREES, 0, -45, 0, System.nanoTime())
                )
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(aprilTagLibrary)
                .build();

        this.visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .setCameraResolution(new Size(1280, 720))
                .enableLiveView(true)
                .addProcessor(this.aprilTagProcessor)
                .build();
    }
}
