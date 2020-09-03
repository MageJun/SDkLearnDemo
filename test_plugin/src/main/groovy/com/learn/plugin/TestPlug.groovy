package com.learn.plugin

import com.android.build.gradle.AppExtension
import com.learn.plugin.strproguard.GlobalConfig
import com.learn.plugin.strproguard.SettingParams
import com.learn.plugin.strproguard.StrProguard
import org.gradle.api.Plugin
import org.gradle.api.Project
class TestPlug implements Plugin<Project>{


    @Override
    void apply(Project project) {
        System.out.println("=============");
        System.out.println("Test plugin say hello to you!");
        System.out.println("=============");
        project.extensions.create('stringEncry', SettingParams);
        GlobalConfig.setProject(project);
        def android = project.extensions.getByType(AppExtension)
        def classTransform = new StrProguard(project);
        android.registerTransform(classTransform);

    }
}