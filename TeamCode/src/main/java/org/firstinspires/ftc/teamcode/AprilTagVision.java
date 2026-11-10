package org.firstinspires.ftc.teamcode;

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

public class AprilTagVision {
    private final VisionPortal visionPortal;
    private final AprilTagProcessor aprilTagProcessor;
    private AprilTagPoseFtc ftcPose;
    private final Telemetry telemetry;

    public AprilTagVision(WebcamName webcam, Telemetry telemetry) {
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
                .setCamera(webcam)
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .setCameraResolution(new Size(1280, 800))
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

        if (validTags.isEmpty()) {
            return null;
        }

        validTags.sort(Comparator.comparing((AprilTagClusterDetection detection) -> detection.ftcPose.range));

        this.ftcPose = validTags.get(0).ftcPose;

        return this.ftcPose;
    }

    public void closeVisionPortal() {
        this.visionPortal.close();
    }

    public void telemetry() {
        telemetry.addData("Range: ", ftcPose.range);
        telemetry.addData("Bearing: ", ftcPose.bearing);
        telemetry.addData("Elevation: ", ftcPose.elevation);
        telemetry.addData("Pitch: ", ftcPose.pitch);
        telemetry.addData("Roll: ", ftcPose.roll);
        telemetry.addData("Yaw: ", ftcPose.yaw);
        telemetry.addData("X: ", ftcPose.x);
        telemetry.addData("Y: ", ftcPose.y);
        telemetry.addData("Z: ", ftcPose.z);
    }
}
