package com.songshu.jucai.mylibrary.manager;

import android.app.Activity;

import java.util.Stack;

public class ActivityManager {


    private Stack<Activity> activitys = new Stack<>();


    private static ActivityManager manager = new ActivityManager();


    private ActivityManager() {

    }

    public static ActivityManager getManager() {
        return manager;
    }


    public void add(Activity activity) {
        if (activitys.contains(activity)) {
            return;
        }
        activitys.addElement(activity);
    }


    public Activity getTop() {
        return activitys.peek();
    }


    public void removeTop() {
        Activity pop = activitys.pop();
    }


    public void killAll() {
        for (Activity activity : activitys) {
            activity.finish();
        }
    }


    public void kill(Activity activity) {
        if (activitys.contains(activity)) {
            activitys.remove(activity);
            activity.finish();
        }
    }


}
