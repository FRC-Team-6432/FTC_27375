package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.lang.reflect.Array;
import java.util.ArrayList;

@TeleOp
public class FollowAprilTag extends LinearOpMode {
    //TODO: define motors, sensors, processors here
    private VisionPortal visionPortal;

    private HuskyLens huskyLens;
    private AprilTagProcessor aprilTagProcessor;

    private AprilTagPoseFtc myFtcPose;

    private final Pose3D targetPose = new Pose3D(
            new Position(DistanceUnit.MM, 0, 500, 0, System.nanoTime()),
            new YawPitchRollAngles(AngleUnit.DEGREES, 0, 0, 0, System.nanoTime())
    );

    @Override
    public void runOpMode() throws InterruptedException {
        // TODO: add initialise code here
        initVision();
        initHuskyLens();
        telemetry.update();
        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while(opModeIsActive()) {
                //TODO: add loop code here
                telemetry.addData("Runtime: ", getRuntime());
                handleHuskyLens();
                telemetry.update();

                handleAprilTagDetections();
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
                        new YawPitchRollAngles(AngleUnit.DEGREES, 90, 0, 0, System.nanoTime())
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

    private void initHuskyLens() {
        this.huskyLens = hardwareMap.get(HuskyLens.class, "huskylens");
        this.huskyLens.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);
        if (!huskyLens.knock()){
            telemetry.addData("HuskyLens", "Communication failed! Go mess around with the wiring");
        } else {
            telemetry.addData("HuskyLens", "YAYAYAYAYAY WE HAVE A CAMERA!");
        }
    }

    private void handleHuskyLens(){
        HuskyLens.Block[] blocks = huskyLens.blocks();
        for (HuskyLens.Block block : blocks) {
            telemetry.addData("id:", block.id);
            telemetry.addData("x: ", block.x);
            telemetry.addData("y:", block.y);
        }
    }

    private void aprilTagTelemetry(AprilTagClusterDetection clusterDetection) {
//        ArrayList<AprilTagDetection> aprilTagDetections = this.aprilTagProcessor.getDetections();

//        for(AprilTagDetection detection : aprilTagDetections) {
//            if (detection instanceof AprilTagClusterDetection){
//                AprilTagClusterDetection clusterDetection = (AprilTagClusterDetection) detection;
                telemetry.addData("aprilTagName: ", clusterDetection.metadata.name);
                telemetry.addData("aprilTagShortName: ", clusterDetection.metadata.shortName);
                telemetry.addData("aprilTagDistanceUnit: ", clusterDetection.metadata.distanceUnit);
//                telemetry.addData("aprilTagFieldPosition: ", clusterDetection.metadata.fieldPosition);
//                telemetry.addData("aprilTagFieldOrientation: ", clusterDetection.metadata.fieldOrientation);
                telemetry.addData("Ftc Pose: ", clusterDetection.ftcPose);

//            }
//        }
    }

    private void handleAprilTagDetections() {
        ArrayList<AprilTagClusterDetection> validTags = getValidTags();

        if (validTags.isEmpty()) {
            myFtcPose = null;
            return;
        }

        for (AprilTagClusterDetection clusterDetection : validTags) {
            aprilTagTelemetry(clusterDetection);
        }

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
}
