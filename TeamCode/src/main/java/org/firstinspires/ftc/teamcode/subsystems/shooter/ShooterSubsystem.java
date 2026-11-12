package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class ShooterSubsystem {
    private final DcMotor shooterMotor;
    private final Telemetry telemetry;
    private final double maxPower;
    private final Servo servo;


    private final ArrayList<ShooterState> shooterStateList;

    public ShooterSubsystem(DcMotor shooterMotor, Telemetry telemetry, DcMotorSimple.Direction direction, Servo  servo){
        shooterStateList = new ArrayList<>();
        shooterStateList.add(ShooterState.STOPPED);

        this.shooterMotor = shooterMotor;
        if (shooterMotor != null) {
            this.shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            this.shooterMotor.setDirection(direction);
            this.shooterMotor.setPower(0);
        }

        this.servo = servo;
        if(servo != null) {
            this.servo.setDirection(Servo.Direction.FORWARD);
        }

        this.telemetry = telemetry;
    }

    public void handle(boolean shouldShoot, boolean shouldReverseShoot, double power) {
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
                shooterMotor.setPower(maxPower * power);
//                servo.setPosition(SERVO_OPEN);
                break;
            case REVERSESHOOTING:
                shooterMotor.setPower(-maxPower * power);
//                servo.setPosition(SERVO_OPEN);
                break;
            case STOPPED:
                shooterMotor.setPower(0);
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
