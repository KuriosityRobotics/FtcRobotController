package org.firstinspires.ftc.teamcode.ultimategoal.modules;

import android.os.SystemClock;

import org.firstinspires.ftc.teamcode.ultimategoal.util.TelemetryProvider;
import org.firstinspires.ftc.teamcode.ultimategoal.Robot;
import org.firstinspires.ftc.teamcode.ultimategoal.util.auto.Point;

import java.util.ArrayList;

public class VelocityModule implements Module, TelemetryProvider {
    private boolean isOn;

    // in/s and rad/s
    public double xVel;
    public double yVel;
    public double angleVel;

    private long oldUpdateTime;
    private long currentUpdateTime;

    private Robot robot;

    public VelocityModule(Robot robot, boolean isOn) {
        robot.telemetryDump.registerProvider(this);
        this.robot = robot;
        this.isOn = isOn;
    }

    public void init() {
        oldUpdateTime = SystemClock.elapsedRealtime();
    }

    private Point oldWorldPosition;
    private double oldWorldAngle = 0;

    public void update() {
        Point robotPosition = robot.drivetrain.getCurrentPosition();
        double robotHeading = robot.drivetrain.getCurrentHeading();

        currentUpdateTime = robot.currentTimeMilli;

        if (oldWorldPosition != null) {
            xVel = 1000 * (robotPosition.x - oldWorldPosition.x) / (currentUpdateTime - oldUpdateTime);
            yVel = 1000 * (robotPosition.y - oldWorldPosition.y) / (currentUpdateTime - oldUpdateTime);
            angleVel = 1000 * (robotHeading - oldWorldAngle) / (currentUpdateTime - oldUpdateTime);
        }

        oldWorldPosition = robotPosition;
        oldWorldAngle = robotHeading;
        oldUpdateTime = currentUpdateTime;
    }

    public ArrayList<String> getTelemetryData() {
        ArrayList<String> data = new ArrayList<>();
        data.add("xVel: " + xVel);
        data.add("yVel: " + yVel);
        data.add("angleVel: "+ angleVel);
        return data;
    }

    public void fileDump(){

    }

    public boolean isOn(){
        return isOn;
    }

    public String getName() {
        return "VelocityModule";
    }
}
