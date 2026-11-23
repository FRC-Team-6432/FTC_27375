package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.Constants25573;

import java.util.ArrayList;

public class ShooterSubsystem {
    private final String name;
    private final DcMotor shooterMotor;
    private final Telemetry telemetry;
    private double power;
    private double servo_offset;
    private final Servo servo;

    private final ArrayList<ShooterState> shooterStateList;

    public ShooterSubsystem(String name, DcMotor shooterMotor, Servo servo, DcMotorSimple.Direction direction, double servo_offset, double power, Telemetry telemetry){
        shooterStateList = new ArrayList<>();
        shooterStateList.add(ShooterState.STOPPED);

        this.shooterMotor = shooterMotor;
        if (shooterMotor != null) {
            this.shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            this.shooterMotor.setDirection(direction);
            this.shooterMotor.setPower(0);
        }
        this.power = power;

        this.servo = servo;
        if(servo != null) {
            this.servo.setDirection(Servo.Direction.FORWARD);
        }

        this.servo_offset = servo_offset;
        this.telemetry = telemetry;
        this.name = name;
    }

    public void handle(boolean shouldSpin, boolean shouldReverseShoot, boolean shouldShoot) {
        if (shouldSpin && !shooterStateList.contains(ShooterState.SHOOTING)) {
            shooterStateList.add(ShooterState.SHOOTING);
        } else  if (!shouldSpin){
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
                break;
            case REVERSESHOOTING:
                shooterMotor.setPower(-power);
                break;
            case STOPPED:
                shooterMotor.setPower(0);
                break;
        }

        if (shouldShoot) {
            servo.setPosition(servo_offset + Constants25573.SERVO_OPEN);
        } else {
            servo.setPosition(servo_offset + Constants25573.SERV0_CLOSED);
        }

    }

    public void calibratePower(boolean increasePower, boolean decreasePower, double step) {
        double newPower = this.power;
        if (increasePower) {
            newPower += step;
        } else if (decreasePower){
            newPower -= step;
        }

        if (newPower >= 0 && newPower <= 1) {
            this.power = newPower;
        }
    }

    public void calibrateOffset(boolean increaseOffset, boolean decreaseOffset, double step){
        double newOffset = this.servo_offset;
        if (increaseOffset) {
            newOffset += step;
        } else if (decreaseOffset){
            newOffset -= step;
        }

        if (newOffset >= 0 && newOffset <= 1) {
            this.servo_offset = newOffset;
        }
    }

    private ShooterState getState() {
        return shooterStateList.get(shooterStateList.size()-1);
    }

    public void telemetry() {
        telemetry.addLine("_____________");
        telemetry.addLine(this.name);
        telemetry.addData("Shooter State: ", this.getState());
        telemetry.addData("Power", this.power);
        telemetry.addData("Offset", this.servo_offset);
    }
}
