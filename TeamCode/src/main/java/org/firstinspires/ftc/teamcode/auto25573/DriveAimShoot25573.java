package org.firstinspires.ftc.teamcode.auto25573;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.constants.Constants25573;
import org.firstinspires.ftc.teamcode.constants.Constants27375;
import org.firstinspires.ftc.teamcode.robotContainer.RobotContainer25573;

public class DriveAimShoot25573 {
    private final RobotContainer25573 robot;
    private final LinearOpMode opMode;
    private final double forward;
    private final double rotate;
    private final double strafe;
    private final double aimSeconds;
    private final double shootSeconds;
    private final double spinupSeconds;
    public DriveAimShoot25573(LinearOpMode opMode, RobotContainer25573 robot, double forward, double rotate, double strafe, double aimSeconds, double shootSeconds, double spinupSeconds) {
        this.opMode = opMode;
        this.robot = robot;
        this.forward = forward;
        this.rotate = rotate;
        this.strafe = strafe;
        this.aimSeconds = aimSeconds;
        this.shootSeconds = shootSeconds;
        this.spinupSeconds = spinupSeconds;
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

        // Aim at the tag
        ElapsedTime aimTimer = new ElapsedTime();
        while (opMode.opModeIsActive() && aimTimer.seconds() < aimSeconds) {
            robot.driveSubsystem.goToTargetPose(
                    robot.aprilTagVision.getFtcPose(),
                    Constants25573.SHOOTING_POSE,
                    Constants25573.TRANSLATE_GAIN,
                    Constants25573.YAW_GAIN
            );
            robot.driveSubsystem.drive();
            opMode.telemetry.addData("AimTimer:", aimTimer.seconds());
            opMode.telemetry.update();
        }

        // Stop
        robot.driveSubsystem.setDriveParameters(0, 0, 0);
        robot.driveSubsystem.drive();

        // Spinup
        ElapsedTime spinupTimer = new ElapsedTime();
        robot.pollenShooter.handle(true, false, false);
        while (opMode.opModeIsActive() && spinupTimer.seconds() < spinupSeconds) {
            opMode.sleep(20);
            opMode.telemetry.addData("SpinUpTimer:", spinupTimer.seconds());
            opMode.telemetry.update();
        }

        // Shoot
        ElapsedTime shooterTimer = new ElapsedTime();
        robot.pollenShooter.handle(true, false, true);
        while (opMode.opModeIsActive() && shooterTimer.seconds() < shootSeconds) {
            opMode.sleep(20);
            opMode.telemetry.addData("ShootTimer:", shooterTimer.seconds());
            opMode.telemetry.update();
        }

        robot.pollenShooter.handle(false, false, false);
        opMode.telemetry.addLine("Auto Complete!!!!!!!!!!!!");
        opMode.telemetry.update();
    }

}
