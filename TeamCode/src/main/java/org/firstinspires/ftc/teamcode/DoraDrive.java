package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

enum ShooterState {

    STOPPED,
    SHOOTING,
    REVERSESHOOTING,
}
enum IntakeState {
    STOPPED,
    INTAKING,
    OUTTAKING
}
@TeleOp
public class DoraDrive extends LinearOpMode {
    //TODO: define motors, sensors, processors here
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private final ArrayList<IntakeState> intakeStateList = new ArrayList<>();

    private final ArrayList<ShooterState> shooterStateList = new ArrayList<>();

    @Override
    public void runOpMode() throws InterruptedException {
        // TODO: add initialise code here

        intakeStateList.add(IntakeState.STOPPED);
        shooterStateList.add(ShooterState.STOPPED);

//        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
//        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
//        backLeft = hardwareMap.get(DcMotor.class,"backLeft");
//        backRight = hardwareMap.get(DcMotor.class,"backRight");


//        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while(opModeIsActive()) {
                //TODO: add loop code here
                drive(-gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
                handleIntake(gamepad1.left_bumper, gamepad1.left_trigger_pressed);
                handleShooter(gamepad1.right_bumper, gamepad1.right_trigger_pressed);
                telemetry.addData("Runtime: ", getRuntime());
                telemetry.update();
            }
        }
    }

    private void drive(double forward, double rotate, double strafe) {
        // drive code
//        frontLeft.setPower(forward+rotate+strafe);
//        frontRight.setPower(forward-rotate-strafe);
//        backLeft.setPower(forward+rotate-strafe);
//        backRight.setPower(forward-rotate+strafe);
        telemetry.addLine("_____DRIVE_____");
        telemetry.addData("forward: ", forward);
        telemetry.addData("rotate: ", rotate);
        telemetry.addData("strafe: ", strafe);
        telemetry.addLine("_______________");
    }

    private IntakeState getIntakeState() {
        // return last element of the list
        return intakeStateList.get(intakeStateList.size()-1);
    }

    private ShooterState getShooterState() {
        return shooterStateList.get(shooterStateList.size()-1);
    }
    
    private void handleIntake(boolean shouldIntake, boolean shouldOutake) {
        if (shouldIntake && !intakeStateList.contains(IntakeState.INTAKING)) {
            intakeStateList.add(IntakeState.INTAKING);
        } else if (!shouldIntake) {
            intakeStateList.remove(IntakeState.INTAKING);
        }
        if (shouldOutake && !intakeStateList.contains(IntakeState.OUTTAKING)) {
            intakeStateList.add(IntakeState.OUTTAKING);
        } else if (!shouldOutake){
            intakeStateList.remove(IntakeState.OUTTAKING);
        }

        telemetry.addData("Intake State: ", getIntakeState());

        switch (getIntakeState()) {
            case INTAKING:

                break;
            case OUTTAKING:

                break;
            case STOPPED:

                break;
        }
    }

    private void handleShooter(boolean shouldShoot, boolean shouldReverseShoot) {
        if (shouldShoot && !shooterStateList.contains(ShooterState.SHOOTING)) {
            shooterStateList.add(ShooterState.SHOOTING);
        } else  if (!shouldShoot){
            shooterStateList.remove(ShooterState.SHOOTING);
        }
        if (shouldReverseShoot && !shooterStateList.contains(ShooterState.REVERSESHOOTING)) {
            shooterStateList.add(ShooterState.REVERSESHOOTING);
        } else if (!shouldReverseShoot){
            shooterStateList.remove(ShooterState.REVERSESHOOTING);
        }

        telemetry.addData("Shooter State: ", getShooterState());

        switch (getShooterState()) {
            case SHOOTING:

                break;
            case REVERSESHOOTING:

                break;
            case STOPPED:

                break;
        }
    }
}
