package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

enum ShooterState {

    STOPPED,
    SHOOTING,
    REVERSESHOOTING,
}

public class ShooterSubsystem {
    private final DcMotor shooterMotor;
    private final Telemetry telemetry;
    private final double SHOOTER_SPEED = 1;
//    private final double SERVO_OPEN = 1;
//    private final double SERV0_CLOSED = 0;
//    private final Servo servo;


    private final ArrayList<ShooterState> shooterStateList;

    public ShooterSubsystem(DcMotor shooterMotor, Telemetry telemetry){
        shooterStateList = new ArrayList<>();
        shooterStateList.add(ShooterState.STOPPED);

        this.shooterMotor = shooterMotor;
        if (shooterMotor != null) {
            this.shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

//        this.servo = servo;
//        if(servo != null) {
//            this.servo.setDirection(Servo.Direction.FORWARD);
//        }

        this.telemetry = telemetry;
    }

    public void handle(boolean shouldShoot, boolean shouldReverseShoot) {
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

        switch (getState()) {
            case SHOOTING:
                shooterMotor.setPower(SHOOTER_SPEED);
//                servo.setPosition(SERVO_OPEN);
                break;
            case REVERSESHOOTING:
//                shooterMotor.setPower(-SHOOTER_SPEED);
//                servo.setPosition(SERVO_OPEN);
                break;
            case STOPPED:
//                shooterMotor.setPower(0);
//                servo.setPosition(SERV0_CLOSED);
                break;
        }
    }

    private ShooterState getState() {
        return shooterStateList.get(shooterStateList.size()-1);
    }

    public void telemetry() {
        telemetry.addData("Shooter State: ", this.getState());
    }
}
