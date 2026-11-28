package org.firstinspires.ftc.teamcode.auto25573;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.constants.Constants25573;
import org.firstinspires.ftc.teamcode.robotContainer.RobotContainer25573;

public class DriveAimShootPark25573 {
    private final RobotContainer25573 robot;
    private final LinearOpMode opMode;
    private final double forward;
    private final double rotate;
    private final double strafe;
    private final double park1Forward;
    private final double park1Rotate;
    private final double park1Strafe;
    private final double park2Forward;
    private final double park2Rotate;
    private final double park2Strafe;
    private final double aimSeconds;
    private final double shootSeconds;
    private final double spinupSeconds;
    private final double park1Seconds;
    private final double park2Seconds;

    public DriveAimShootPark25573(LinearOpMode opMode, RobotContainer25573 robot, double forward, double rotate, double strafe, double aimSeconds, double shootSeconds, double spinupSeconds, double park1Forward, double park1Rotate, double park1Strafe, double park1Seconds, double park2Forward, double park2Rotate, double park2Strafe, double park2Seconds) {
        this.opMode = opMode;
        this.robot = robot;
        this.forward = forward;
        this.rotate = rotate;
        this.strafe = strafe;
        this.aimSeconds = aimSeconds;
        this.shootSeconds = shootSeconds;
        this.spinupSeconds = spinupSeconds;
        this.park1Forward = park1Forward;
        this.park1Rotate = park1Rotate;
        this.park1Strafe = park1Strafe;
        this.park1Seconds = park1Seconds;
        this.park2Forward = park2Forward;
        this.park2Rotate = park2Rotate;
        this.park2Strafe = park2Strafe;
        this.park2Seconds = park2Seconds;
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

        ElapsedTime park1Timer = new ElapsedTime();
        robot.driveSubsystem.setDriveParameters(park1Forward,park1Rotate,park1Strafe);
        while (opMode.opModeIsActive() && park1Timer.seconds() < park1Seconds) {
            robot.driveSubsystem.drive();
        }

        // Stop
        robot.driveSubsystem.setDriveParameters(0, 0, 0);
        robot.driveSubsystem.drive();

        ElapsedTime park2Timer = new ElapsedTime();
        robot.driveSubsystem.setDriveParameters(park2Forward,park2Rotate,park2Strafe);
        while (opMode.opModeIsActive() && park2Timer.seconds() < park2Seconds) {
            robot.driveSubsystem.drive();
        }

        opMode.telemetry.addLine("Auto Complete!!!!!!!!!!!!");
        opMode.telemetry.update();
    }

}
