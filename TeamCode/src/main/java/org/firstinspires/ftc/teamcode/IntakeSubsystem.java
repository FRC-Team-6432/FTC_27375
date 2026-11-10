package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

    enum IntakeState {

        STOPPED,
        INTAKING,
        OUTTAKING,
    }

    public class IntakeSubsystem {
        private final Telemetry telemetry;
        private final double INTAKE_SPEED = 1;

        private final DcMotor intakeMotor;


        private final ArrayList<org.firstinspires.ftc.teamcode.IntakeState> intakeStateList;

        public IntakeSubsystem(DcMotor intakeMotor, Telemetry telemetry){
            intakeStateList = new ArrayList<>();
            intakeStateList.add(org.firstinspires.ftc.teamcode.IntakeState.STOPPED);

            this.intakeMotor = intakeMotor;
            if (intakeMotor != null) {
                this.intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                this.intakeMotor.setPower(0);
            }

            this.telemetry = telemetry;
        }

        public void handle(boolean shouldIntake, boolean shouldOuttake) {
            if (shouldIntake && !intakeStateList.contains(IntakeState.INTAKING)) {
                intakeStateList.add(IntakeState.INTAKING);
                intakeStateList.add(IntakeState.INTAKING);
            } else  if (!shouldIntake){
                intakeStateList.remove(IntakeState.INTAKING);
            }
            if (shouldOuttake && !intakeStateList.contains(org.firstinspires.ftc.teamcode.IntakeState.OUTTAKING)) {
                intakeStateList.add(org.firstinspires.ftc.teamcode.IntakeState.OUTTAKING);
            } else if (!shouldOuttake){
                intakeStateList.remove(org.firstinspires.ftc.teamcode.IntakeState.OUTTAKING);
            }

            switch (getintakeState()) {
                case INTAKING:
//                    intakeMotor.setPower(INTAKE_SPEED);
                    break;
                case OUTTAKING:
//                    intakeMotor.setPower(-INTAKE_SPEED);
                    break;
                case STOPPED:
//                    intakeMotor.setPower(0);
                    break;
            }
        }

        private IntakeState getIntakeState() {
            // return last element of the list
            return intakeStateList.get(intakeStateList.size()-1);
        }

        private org.firstinspires.ftc.teamcode.IntakeState getintakeState() {
            return intakeStateList.get(intakeStateList.size()-1);
        }

        public void telemetry() {
            telemetry.addData("Intake State: ", this.getintakeState());
        }
    }
