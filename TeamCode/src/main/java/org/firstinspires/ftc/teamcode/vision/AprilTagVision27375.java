package org.firstinspires.ftc.teamcode.vision;

import android.util.Size;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.Comparator;

public class AprilTagVision27375 {
    private final VisionPortal visionPortal;
    private final AprilTagProcessor aprilTagProcessor;
    private AprilTagPoseFtc ftcPose;
    private final Telemetry telemetry;

    public AprilTagVision27375(WebcamName webcam, Telemetry telemetry) {
        AprilTagLibrary aprilTagLibrary = AprilTagGameDatabase.getBioBuzzTagLibrary();

        this.aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.MM, AngleUnit.DEGREES)
                .setCameraPose(
                        new Position(DistanceUnit.MM, 0, 0, 0, System.nanoTime()),
                        new YawPitchRollAngles(AngleUnit.DEGREES, 90, -60, 0, System.nanoTime())
                )
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(aprilTagLibrary)
                .build();

        this.visionPortal = new VisionPortal.Builder()
                .setCamera(webcam)
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .setCameraResolution(new Size(1280, 720))
                .enableLiveView(true)
                .addProcessor(this.aprilTagProcessor)
                .build();

        this.telemetry = telemetry;
    }

    private ArrayList<AprilTagClusterDetection> getValidTags() {
        ArrayList<AprilTagDetection> aprilTagDetections = this.aprilTagProcessor.getDetections();
        ArrayList<AprilTagClusterDetection> validTags = new ArrayList<>();

        for (AprilTagDetection detection : aprilTagDetections) {
            if (!(detection instanceof AprilTagClusterDetection)) {
                continue;
            }
            AprilTagClusterDetection clusterDetection = (AprilTagClusterDetection) detection;

            if (clusterDetection.ftcPose.roll > 90 || clusterDetection.ftcPose.roll < -90) {
                continue;
            }

            validTags.add(clusterDetection);
        }
        return validTags;
    }

    public AprilTagPoseFtc getFtcPose() {
        ArrayList<AprilTagClusterDetection> validTags = getValidTags();
        this.ftcPose = null;
        if (!validTags.isEmpty()) {
            validTags.sort(Comparator.comparing((AprilTagClusterDetection detection) -> detection.ftcPose.range));
            this.ftcPose = changeCoords(validTags.get(0).ftcPose, 60);
            telemetry.addLine(validTags.get(0).metadata.fieldOrientation.toString());
            telemetry.addLine(validTags.get(0).metadata.fieldPosition.toString());
        }
        return this.ftcPose;
    }

    private AprilTagPoseFtc changeCoords(AprilTagPoseFtc cameraPose, double angle) {
        double z = cameraPose.z;
        double y = cameraPose.y;

        double z_prime = y * Math.cos(angle * Math.PI / 180) + z * Math.sin(angle * Math.PI / 180);
        double y_prime = z * Math.cos(angle * Math.PI / 180) - y * Math.sin(angle * Math.PI / 180);

        return new AprilTagPoseFtc(
                cameraPose.x,
                y_prime,
                z_prime,
                cameraPose.yaw,
                cameraPose.pitch,
                cameraPose.roll,
                cameraPose.range,
                cameraPose.bearing,
                cameraPose.elevation
        );
    }

    public void closeVisionPortal() {
        this.visionPortal.close();
    }

    public void telemetry() {
        if (ftcPose == null) {
            telemetry.addLine("No April Tags Detected");
        } else {
            telemetry.addLine("_____APRIL TAG DETECTED_____");
            telemetry.addData("Pitch: ", ftcPose.pitch);
            telemetry.addData("Roll: ", ftcPose.roll);
            telemetry.addData("Yaw: ", ftcPose.yaw);
            telemetry.addData("X: ", ftcPose.x);
            telemetry.addData("Y: ", ftcPose.y);
            telemetry.addData("Z: ", ftcPose.z);
        }
    }
}
