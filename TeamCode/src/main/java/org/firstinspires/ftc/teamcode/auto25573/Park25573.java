package org.firstinspires.ftc.teamcode.auto25573;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.constants.Constants25573;
import org.firstinspires.ftc.teamcode.robotContainer.RobotContainer25573;

public class Park25573 {
    private final RobotContainer25573 robot;
    private final LinearOpMode opMode;
    private final double forward;
    private final double rotate;
    private final double strafe;
    private final double driveSeconds;


    public Park25573(LinearOpMode opMode, RobotContainer25573 robot, double forward, double rotate, double strafe, double driveSeconds) {
        this.opMode = opMode;
        this.robot = robot;
        this.forward = forward;
        this.rotate = rotate;
        this.strafe = strafe;
        this.driveSeconds = driveSeconds;
    }

    public void run() throws InterruptedException {
        // Drive
        robot.driveSubsystem.setDriveParameters(forward, rotate, strafe);
        ElapsedTime parkTimer = new ElapsedTime();
        while (parkTimer.seconds() < driveSeconds && opMode.opModeIsActive()) {
            robot.driveSubsystem.drive();
            opMode.sleep(20);
        }
            opMode.telemetry.addLine("Auto Complete!!!!!!!!!!!!");
            opMode.telemetry.update();
        }
    }
