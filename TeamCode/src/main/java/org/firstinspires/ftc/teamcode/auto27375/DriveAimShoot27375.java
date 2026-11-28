package org.firstinspires.ftc.teamcode.auto27375;

import static java.lang.Thread.sleep;

import org.firstinspires.ftc.teamcode.constants.Constants27375;
import org.firstinspires.ftc.teamcode.robotContainer.RobotContainer27375;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class DriveAimShoot27375 {
    private final RobotContainer27375 robot;
    private final LinearOpMode opMode;
    private final double forward;
    private final double rotate;
    private final double strafe;
    private final double parkForward;
    private final double parkRotate;
    private final double parkStrafe;
    private final double aimSeconds;
    private final double shootSeconds;
    private final double parkSeconds;
    public DriveAimShoot27375(LinearOpMode opMode, RobotContainer27375 robot, double forward, double rotate, double strafe, double aimSeconds, double shootSeconds, double parkForward, double parkRotate, double parkStrafe, double parkSeconds) {
        this.opMode = opMode;
        this.robot = robot;
        this.forward = forward;
        this.rotate = rotate;
        this.strafe = strafe;
        this.aimSeconds = aimSeconds;
        this.shootSeconds = shootSeconds;
        this.parkForward = parkForward;
        this.parkRotate = parkRotate;
        this.parkStrafe = parkStrafe;
        this.parkSeconds= parkSeconds;
    }

    public void run() throws InterruptedException {
        // Drive until we see the tag
        robot.driveSubsystem.setDriveParameters(forward, rotate, strafe);
        while (opMode.opModeIsActive() && robot.aprilTagVision.getFtcPose() == null) {
            robot.driveSubsystem.drive();
            opMode.sleep(20);
        }

        // Stop
        robot.driveSubsystem.setDriveParameters(0, 0, 0);
        robot.driveSubsystem.drive();

        // Aim at the tag for 3 seconds
        ElapsedTime aimTimer = new ElapsedTime();
        while (opMode.opModeIsActive() && aimTimer.seconds() < aimSeconds) {
            robot.driveSubsystem.goToTargetPose(
                    robot.aprilTagVision.getFtcPose(),
                    Constants27375.SHOOTING_POSE,
                    Constants27375.TRANSLATE_GAIN,
                    Constants27375.YAW_GAIN
            );
            robot.driveSubsystem.drive();
        }

        // Stop
        robot.driveSubsystem.setDriveParameters(0, 0, 0);
        robot.driveSubsystem.drive();

        // Shoot for 3 seconds
        ElapsedTime shootTimer = new ElapsedTime();
        robot.shooterSubsystem.handle(true, false);
        while (opMode.opModeIsActive() && shootTimer.seconds() < shootSeconds) {
            opMode.sleep(20);
        }
        robot.shooterSubsystem.handle(false, false);

        ElapsedTime parkTimer = new ElapsedTime();
        robot.driveSubsystem.setDriveParameters(forward,rotate,strafe);
        while (opMode.opModeIsActive() && parkTimer.seconds() < parkSeconds) {
            robot.driveSubsystem.drive();
        }
    }

}
