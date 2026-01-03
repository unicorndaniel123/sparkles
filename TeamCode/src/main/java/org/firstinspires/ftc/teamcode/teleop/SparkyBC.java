package org.firstinspires.ftc.teamcode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@TeleOp(name = "Sparky blue close")
public class SparkyBC extends LinearOpMode {

    private DcMotor leftFront;
    private DcMotor leftRear;
    private DcMotor rightFront;
    private DcMotor rightRear;
    private DcMotor shrm;
    private Servo shls;
    private Servo shrs;
    private DcMotor shlm;
    private CRServo mid;
    private DcMotor intake;
    public static double shooterOffset = 0;
    // points
    public Pose autoEndingPos = null;
    public Pose startingPose = new Pose(26.9, 134.2, Math.toRadians(53));
    public Pose target = new Pose(15.6, 127.9, Math.toRadians(138.3));

    /**
     * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue
     * Comment Blocks show where to place Initialization code (runs once, after touching the
     * DS INIT button, and before touching the DS Start arrow), Run code (runs once, after
     * touching Start), and Loop code (runs repeatedly while the OpMode is active, namely not
     * Stopped).
     */
    @Override
    public void runOpMode() {
        double shspeed = 0;

        // pedro
        Follower follower = Constants.createFollower(hardwareMap);
        if (autoEndingPos == null) {
            follower.setStartingPose(startingPose);
        } else {
            follower.setStartingPose(autoEndingPos);
            autoEndingPos = null;
        }
        follower.update();

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        shrm = hardwareMap.get(DcMotor.class, "shrm");
        shls = hardwareMap.get(Servo.class, "shls");
        shrs = hardwareMap.get(Servo.class, "shrs");
        shlm = hardwareMap.get(DcMotor.class, "shlm");
        mid = hardwareMap.get(CRServo.class, "mid");
        intake = hardwareMap.get(DcMotor.class, "intake");

        // Put initialization blocks here.
        shrm.setDirection(DcMotor.Direction.REVERSE);
        shls.setPosition(0.15);
        shrs.setPosition(0.15);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here. RUNS ONLY ONCE WHEN WE HIT PLAY
            follower.startTeleopDrive();
            while (opModeIsActive()) {
                double distShooter = Math.sqrt(Math.pow((target.getX() - follower.getPose().getX()), 2) + Math.pow((target.getY() - follower.getPose().getY()), 2));
                distShooter += shooterOffset;
                // Put loop blocks here.
                follower.update();
                follower.setTeleOpDrive(
                        -gamepad1.left_stick_y,
                        -gamepad1.left_stick_x,
                        -gamepad1.right_stick_x,
                        true // Robot Centric
                );
                if (gamepad1.y) {
                    shspeed = 1;
                    shls.setPosition(0.06);
                    shrs.setPosition(0.06);
                }
                if (gamepad1.b) {
                    shspeed = 0.65;
                    shls.setPosition(0.08);
                    shrs.setPosition(0.08);
                }
                if (gamepad1.a) {
                    shspeed = 0.7;
                    shls.setPosition(0.14);
                    shrs.setPosition(0.14);
                }
                if (gamepad1.y || gamepad1.b || gamepad1.a) {
                    shlm.setPower(shspeed);
                    shrm.setPower(shspeed);
                } else {
                    shlm.setPower(0);
                    shrm.setPower(0);
                    shls.setPosition(0.15);
                    shrs.setPosition(0.15);
                }
                if (gamepad1.left_bumper) {
                    mid.setPower(1);
                } else if (gamepad1.right_bumper) {
                    mid.setPower(-1);
                } else {
                    mid.setPower(0);
                }
                if (gamepad1.dpad_up) {
                    intake.setPower(1);
                } else {
                    intake.setPower(0);
                }
                if (gamepad1.dpad_down) {
                    intake.setPower(-1);
                } else {
                    intake.setPower(0);
                }
                telemetry.addData("position", follower.getPose());
                telemetry.update();
            }
        }
    }
}