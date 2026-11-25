package org.firstinspires.ftc.teamcode.robotContainer;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.constants.Constants25573;
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveSubsystem25573;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.vision.AprilTagVision;

public class RobotContainer25573 {
    public final DriveSubsystem25573 driveSubsystem;
    public final ShooterSubsystem pollenShooter;
    public final ShooterSubsystem nectarShooter;
    public final IntakeSubsystem intake;
    public final AprilTagVision aprilTagVision;

    public RobotContainer25573(HardwareMap hardwareMap, Telemetry telemetry) {
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, Constants25573.FRONT_LEFT_MOTOR);
        DcMotor frontRight = hardwareMap.get(DcMotor.class, Constants25573.FRONT_RIGHT_MOTOR);
        DcMotor backLeft = hardwareMap.get(DcMotor.class, Constants25573.BACK_LEFT_MOTOR);
        DcMotor backRight = hardwareMap.get(DcMotor.class, Constants25573.BACK_RIGHT_MOTOR);

        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.driveSubsystem = new DriveSubsystem25573(
                frontLeft,
                frontRight,
                backLeft,
                backRight,
                telemetry
        );

        this.pollenShooter = new ShooterSubsystem(
                "Pollen Shooter",
                hardwareMap.tryGet(DcMotor.class, Constants25573.POLLENSHOOTER_MOTOR),
                hardwareMap.tryGet(Servo.class, Constants25573.POLLENSHOOTER_SERVO),
                DcMotorSimple.Direction.REVERSE,
                Constants25573.POLLEN_SERVO_OFFSET,
                Constants25573.POLLEN_POWER,
                telemetry
        );

        this.nectarShooter = new ShooterSubsystem(
                "Nectar Shooter",
                hardwareMap.tryGet(DcMotor.class, Constants25573.NECTARSHOOTER_MOTOR),
                hardwareMap.tryGet(Servo.class, Constants25573.NECTARSHOOTER_SERVO),
                DcMotorSimple.Direction.FORWARD,
                Constants25573.NECTAR_SERVO_OFFSET,
                Constants25573.NECTAR_POWER,
                telemetry
        );

        this.intake = new IntakeSubsystem(hardwareMap.tryGet(DcMotor.class,
                Constants25573.INTAKE_MOTOR), telemetry,Constants25573.INTAKE_SPEED);

        this.aprilTagVision = new AprilTagVision(
                hardwareMap.get(WebcamName.class, Constants25573.WEBCAM),
                telemetry
        );

        telemetry.addLine("Initialised!");
        telemetry.update();
    }

}
