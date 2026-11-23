package org.firstinspires.ftc.teamcode.robotContainer;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.constants.Constants27375;
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterSubsystem27375;
import org.firstinspires.ftc.teamcode.vision.AprilTagVision;

public class RobotContainer27375 {
    public final DriveSubsystem driveSubsystem;
    public final IntakeSubsystem intakeSubsystem;
    public final ShooterSubsystem27375 shooterSubsystem;
    public final AprilTagVision aprilTagVision;

    public RobotContainer27375(HardwareMap hardwareMap, Telemetry telemetry) {
        DcMotor shooterMotor = hardwareMap.get(DcMotor.class, Constants27375.SHOOTER_MOTOR);
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.shooterSubsystem = new ShooterSubsystem27375(
                shooterMotor,
                hardwareMap.get(Servo.class, Constants27375.SERVO),
                Constants27375.SHOOTER_POWER,
                telemetry
        );

        DcMotor intakeMotor = hardwareMap.get(DcMotor.class, Constants27375.INTAKE_MOTOR);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.intakeSubsystem = new IntakeSubsystem(
                intakeMotor,
                telemetry
        );

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, Constants27375.FRONT_LEFT_MOTOR);
        DcMotor frontRight = hardwareMap.get(DcMotor.class, Constants27375.FRONT_RIGHT_MOTOR);
        DcMotor backLeft = hardwareMap.get(DcMotor.class, Constants27375.BACK_LEFT_MOTOR);
        DcMotor backRight = hardwareMap.get(DcMotor.class, Constants27375.BACK_RIGHT_MOTOR);

        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.driveSubsystem = new DriveSubsystem(
                frontLeft,
                frontRight,
                backLeft,
                backRight,
                telemetry
        );

        this.aprilTagVision = new AprilTagVision(hardwareMap.get(WebcamName.class, Constants27375.WEBCAM), telemetry);

        telemetry.addLine("Initialised!");
        telemetry.update();
    }


}
