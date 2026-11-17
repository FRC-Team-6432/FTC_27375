package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class ShooterSubsystem27375 {
    private final DcMotor shooterMotor;
    private final Telemetry telemetry;
    private double power;

    private final ArrayList<ShooterState> shooterStateList;

    public ShooterSubsystem27375(DcMotor shooterMotor, double power, Telemetry telemetry){
        shooterStateList = new ArrayList<>();
        shooterStateList.add(ShooterState.STOPPED);

        this.shooterMotor = shooterMotor;
        this.power = power;
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
                break;
            case REVERSESHOOTING:
                shooterMotor.setPower(-power);
                break;
            case STOPPED:
                shooterMotor.setPower(0);
                break;
        }
    }

    public void calibrate(boolean increasePower, boolean decreasePower, boolean increaseOffset, boolean decreaseOffset) {
        if (increasePower) {
            this.power += 0.01;
        } else if (decreasePower){
            this.power -= 0.01;
        }
    }

    private ShooterState getState() {
        return shooterStateList.get(shooterStateList.size()-1);
    }

    public void telemetry() {
        telemetry.addData("Shooter State: ", this.getState());
        telemetry.addData("Power", this.power);
    }
}
