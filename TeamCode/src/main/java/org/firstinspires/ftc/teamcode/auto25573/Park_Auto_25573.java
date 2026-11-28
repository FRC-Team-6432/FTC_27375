package org.firstinspires.ftc.teamcode.auto25573;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.constants.Constants25573;
import org.firstinspires.ftc.teamcode.robotContainer.RobotContainer25573;

@Autonomous(group = "25573")
public class Park_Auto_25573 extends LinearOpMode {
    private Park25573 park25573;
    private RobotContainer25573 robot;

    @Override
    public void runOpMode() throws InterruptedException {
        this.robot =  new RobotContainer25573(hardwareMap, telemetry);
        this.park25573= new Park25573(
                this, robot, -0.5, 0,-0.5, 4
        );

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while(opModeIsActive()) {
                park25573.run();
            }
        }
    }
}
