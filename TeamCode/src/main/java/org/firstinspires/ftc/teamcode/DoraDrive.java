package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.ArrayList;

enum ShooterState {

    STOP,
    SHOOT,
    REVERSESHOOT,
}
enum IntakeState {
    STOP,
    INTAKE,
    OUTTAKE
}
@TeleOp
public class DoraDrive extends LinearOpMode {
    //TODO: define motors, sensors, processors here
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private ArrayList<IntakeState> intakeStateList = new ArrayList<>();

    private ArrayList<ShooterState> shooterStateList = new ArrayList<>();

    @Override
    public void runOpMode() throws InterruptedException {
        // TODO: add initialise code here

        intakeStateList.add(IntakeState.STOP);

        intakeStateList.add(IntakeState.INTAKE);

        intakeStateList.add(IntakeState.OUTTAKE);

        shooterStateList.add(ShooterState.STOP);

        shooterStateList.add(ShooterState.SHOOT);

        shooterStateList.add(ShooterState.REVERSESHOOT);

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
                handleShooter(gamepad1.right_bumper);
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
        telemetry.addData("forward: ", forward);
        telemetry.addData("rotate: ", rotate);
        telemetry.addData("strafe: ", strafe);
    }

    private IntakeState getIntakeState() {
        // return last element of the list
        return intakeStateList.get(intakeStateList.size()-1);
    }
    
    private void handleIntake(boolean shouldIntake, boolean shouldOutake) {
        if (shouldIntake) {
            intakeStateList.add(IntakeState.INTAKE);
        } else {
            intakeStateList.remove(IntakeState.INTAKE);
        }
        if (shouldOutake) {
            intakeStateList.add(IntakeState.OUTTAKE);
        } else {
            intakeStateList.remove(IntakeState.OUTTAKE);
        }

        switch (getIntakeState()) {
            case INTAKE:
                telemetry.addLine("INTAKING");
            case OUTTAKE:
                telemetry.addLine("OUTTAKING");
            case STOP:
                telemetry.addLine("STOP");
        }
    }

    private void handleShooter(boolean shouldShoot, boolean shootReverseShoot) {
        if (shouldShoot) {
            telemetry.addLine("SHOOTING");
        } else {
            telemetry.addLine("NOT SHOOTING");
        }

    }
}
