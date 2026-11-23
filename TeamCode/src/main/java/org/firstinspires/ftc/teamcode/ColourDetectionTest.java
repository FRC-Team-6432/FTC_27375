package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class ColourDetectionTest extends LinearOpMode {
    private ColourDetectionVision colourDetectionVision;

    @Override
    public void runOpMode() throws InterruptedException {
        this.colourDetectionVision = new ColourDetectionVision(hardwareMap.get(HuskyLens.class, "huskylens"), telemetry);
        telemetry.update();
        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while(opModeIsActive()) {
                telemetry.addData("Runtime: ", getRuntime());

                this.colourDetectionVision.telemetry();
                telemetry.update();
            }
            this.colourDetectionVision.closeVisionPortal();
        }
    }
}
