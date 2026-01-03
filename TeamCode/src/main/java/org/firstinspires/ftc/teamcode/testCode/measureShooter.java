package org.firstinspires.ftc.teamcode.testCode;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;


@Configurable
@TeleOp(name = "measureShooter", group = "test_ftc14212")
public class measureShooter extends LinearOpMode {
    public static double shooterVelo = 0;
    public static double pivotCpos = 0;
    @Override
    public void runOpMode() {
        // motors
        DcMotorEx shooterL = hardwareMap.get(DcMotorEx.class, "shlm"); // 6000 rpm
        DcMotorEx shooterR = hardwareMap.get(DcMotorEx.class, "shrm"); // 6000 rpm
        // servos
        Servo pivot = hardwareMap.get(Servo.class, "shls"); // 1x axon mini
        CRServo indexer = hardwareMap.get(CRServo.class, "mid");
        // reverse
        shooterR.setDirection(DcMotorEx.Direction.REVERSE);
        // colors
        gamepad1.setLedColor(0, 255, 255, -1);
        gamepad2.setLedColor(0, 255, 0, -1);
        hardwareMap.get(IMU.class, "imu").resetYaw();
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                indexer.setPower(1);
                pivot.setPosition(pivotCpos);
                // double sPower = shooterPID.calculate(shooterR.getVelocity(), shooterVelo) + shooterVelo;
                shooterR.setPower(shooterVelo); // leader
                shooterL.setPower(shooterVelo); // follower
                telemetry.update();
            }
        }
        if (isStopRequested() || !isStarted()) {
            // stop code
        }
    }
}