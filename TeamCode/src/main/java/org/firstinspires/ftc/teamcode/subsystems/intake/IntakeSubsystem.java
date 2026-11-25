package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class IntakeSubsystem {
        private final Telemetry telemetry;
        private double intakeSpeed;
        private final DcMotor intakeMotor;
        private final ArrayList<IntakeState> intakeStateList;

        public IntakeSubsystem(DcMotor intakeMotor, Telemetry telemetry, double intakeSpeed){
            intakeStateList = new ArrayList<>();
            intakeStateList.add(IntakeState.STOPPED);

            this.intakeMotor = intakeMotor;
            this.telemetry = telemetry;
            this.intakeSpeed = intakeSpeed;
        }

        public IntakeSubsystem(DcMotor intakeMotor, Telemetry telemetry) {
            this(intakeMotor, telemetry, 1);
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
                    intakeMotor.setPower(intakeSpeed);
                    break;
                case OUTTAKING:
                    intakeMotor.setPower(-intakeSpeed);
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