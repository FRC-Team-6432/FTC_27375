package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;

import java.util.ArrayList;

public class ShooterSubsystem {
    private final DcMotor shooterMotor;
    private final Telemetry telemetry;
    private double power;
    private double offset;
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
        this.power = Constants.MAX_POWER;
        this.offset = Constants.SERVO_OFFSET;

        this.servo = servo;
        if(servo != null) {
            this.servo.setDirection(Servo.Direction.FORWARD);
        }

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
                shooterMotor.setPower(power);
                servo.setPosition(offset + Constants.SERVO_OPEN);
                break;
            case REVERSESHOOTING:
                shooterMotor.setPower(-power);
                servo.setPosition(offset + Constants.SERVO_OPEN);
                break;
            case STOPPED:
                shooterMotor.setPower(0);
                servo.setPosition(offset + Constants.SERV0_CLOSED);
                break;
        }
    }

    public void calibrate(boolean increasePower, boolean decreasePower, boolean increaseOffset, boolean decreaseOffset) {
        if (increasePower) {
            this.power += 0.01;
        } else if (decreasePower){
            this.power -= 0.01;
        }

        if (increaseOffset) {
            this.offset += 0.01;
        }else if (decreaseOffset) {
            this.offset -= 0.01;
        }
    }

    private ShooterState getState() {
        return shooterStateList.get(shooterStateList.size()-1);
    }

    public void telemetry() {
        telemetry.addData("Shooter State: ", this.getState());
        telemetry.addData("Power", this.power);
        telemetry.addData("Offset", this.offset);
    }
}
