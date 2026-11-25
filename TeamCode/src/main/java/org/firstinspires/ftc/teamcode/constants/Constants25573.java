package org.firstinspires.ftc.teamcode.constants;

import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

public class Constants25573 {
    public static String FRONT_LEFT_MOTOR = "front left";
    public static String FRONT_RIGHT_MOTOR = "front right";
    public static String BACK_LEFT_MOTOR = "back left";
    public static String BACK_RIGHT_MOTOR = "back right";
    public static String POLLENSHOOTER_MOTOR = "pollen shooter";
    public static String NECTARSHOOTER_MOTOR = "nectar shooter";
    public static String POLLENSHOOTER_SERVO = "pollen gate";
    public static String NECTARSHOOTER_SERVO = "nectar gate";
    public static String INTAKE_MOTOR = "intake";
    public static String WEBCAM = "Webcam 1";
    public static final double SERVO_OPEN = 60.0 / 300.0;
    public static final double SERV0_CLOSED = 0;
    public static final double POLLEN_SERVO_OFFSET = 0.12;
    public static final double NECTAR_SERVO_OFFSET = 0.57;
    public static final double POLLEN_POWER = 0.51;
    public static final double NECTAR_POWER = 0.7;
    public static final AprilTagPoseFtc SHOOTING_POSE = new AprilTagPoseFtc(
            0, -1400, 0,
            0, 0, 0,
            0, 0, 0
    );
    public static final double TRANSLATE_GAIN = 0.003;
    public static final double YAW_GAIN = 0.02;
}
