package com.learn.plugin.strproguard;
import org.gradle.api.Project

public class GlobalConfig{
    private static Project project

    public static void setProject(Project project){
        this.project = project
    }

    public static Project getProject(){
        return project
    }
    static SettingParams getParams() {
        return project.stringEncry
    }
}