package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class IntakeSubsystem {
        private final Telemetry telemetry;
        private final double INTAKE_SPEED = 1;
        private final DcMotor intakeMotor;
        private final ArrayList<IntakeState> intakeStateList;

        public IntakeSubsystem(DcMotor intakeMotor, Telemetry telemetry){
            intakeStateList = new ArrayList<>();
            intakeStateList.add(IntakeState.STOPPED);

            this.intakeMotor = intakeMotor;
            this.telemetry = telemetry;
        }

        public void handle(boolean shouldIntake, boolean shouldOuttake) {
            if (shouldIntake && !intakeStateList.contains(IntakeState.INTAKING)) {
                intakeStateList.add(IntakeState.INTAKING);
            } else  if (!shouldIntake){
                intakeStateList.remove(IntakeState.INTAKING);
            }
            if (shouldOuttake && !intakeStateList.contains(IntakeState.OUTTAKING)) {
                intakeStateList.add(IntakeState.OUTTAKING);
            } else if (!shouldOuttake){
                intakeStateList.remove(IntakeState.OUTTAKING);
            }

            switch (getIntakeState()) {
                case INTAKING:
                    intakeMotor.setPower(INTAKE_SPEED);
                    break;
                case OUTTAKING:
                    intakeMotor.setPower(-INTAKE_SPEED);
                    break;
                case STOPPED:
                    intakeMotor.setPower(0);
                    break;
            }
        }

        private IntakeState getIntakeState() {
            // return last element of the list
            return intakeStateList.get(intakeStateList.size()-1);
        }

        public void telemetry() {
            telemetry.addData("Intake State: ", this.getIntakeState());
        }
    }