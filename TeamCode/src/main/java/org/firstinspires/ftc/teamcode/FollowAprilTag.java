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
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.lang.reflect.Array;
import java.util.ArrayList;

@TeleOp
public class FollowAprilTag extends LinearOpMode {
    //TODO: define motors, sensors, processors here
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTagProcessor;

    @Override
    public void runOpMode() throws InterruptedException {
        // TODO: add initialise code here
        initVision();

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while(opModeIsActive()) {
                //TODO: add loop code here
                telemetry.addData("Runtime: ", getRuntime());
                aprilTagTelemetry();

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
                        new YawPitchRollAngles(AngleUnit.DEGREES, 0, 0, 0, System.nanoTime())
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

    private void aprilTagTelemetry() {
        ArrayList<AprilTagDetection> aprilTagDetections = this.aprilTagProcessor.getDetections();

        for(AprilTagDetection detection : aprilTagDetections) {
            if (detection instanceof AprilTagClusterDetection){
                telemetry.addData("x: ", detection.robotPose.getPosition().x);
                telemetry.addData("y:", detection.robotPose.getPosition().y);
                telemetry.addData("z: ", detection.robotPose.getPosition().z);
                telemetry.addLine();
                telemetry.addData("range: ", detection. ftcPose.range);
                telemetry.addData("bearing: ", detection.ftcPose.bearing);
                telemetry.addData("elavation: ", detection.ftcPose.elevation);

                AprilTagClusterDetection clusterDetection = (AprilTagClusterDetection) detection;
                telemetry.addData("aprilTagName: ", clusterDetection.metadata.name);
                telemetry.addData("aprilTagShortName: ", clusterDetection.metadata.shortName);
                telemetry.addData("aprilTagDistanceUnit: ", clusterDetection.metadata.distanceUnit);
                telemetry.addData("aprilTagFieldPosition: ", clusterDetection.metadata.fieldPosition);
                telemetry.addData("aprilTagFieldOrientation: ", clusterDetection.metadata.fieldOrientation);

            }
        }
    }
}
