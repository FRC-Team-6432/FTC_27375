package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.dfrobot.HuskyLens;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ColourDetectionVision {
    private final HuskyLens huskyLens;
    private final Telemetry telemetry;

    public ColourDetectionVision(HuskyLens huskyLens, Telemetry telemetry) {
        this.telemetry = telemetry;

        this.huskyLens = huskyLens;
        this.huskyLens.selectAlgorithm(HuskyLens.Algorithm.COLOR_RECOGNITION);

        if (huskyLens.knock()) {
            this.telemetry.addData("HuskyLens", "HuskyLens Connected");
        }else{
            this.telemetry.addLine("HuskyLens not Connected");
        }
    }

    public void closeVisionPortal(){
        this.huskyLens.close();
    }

    public void telemetry() {
        for (HuskyLens.Block block : huskyLens.blocks()) {
            this.telemetry.addLine(block.toString());
        }

    }
}

