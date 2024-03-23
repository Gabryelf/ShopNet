package com.example.ShopNet.configurations;

public class UnityProjectInfo {
    private String projectName;
    private String projectPath;

    public UnityProjectInfo() {

    }

    public UnityProjectInfo(String projectName, String projectPath) {
        this.projectName = projectName;
        this.projectPath = projectPath;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }
}


