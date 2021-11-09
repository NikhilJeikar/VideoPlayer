package com.example.project.Holder;

import java.io.File;

public class FileHolder {
    public File file;
    public Integer type;

    public FileHolder(File file, Integer type)
    {
        this.file = file;
        this.type = type;
    }

    public static int VIDEO = 1;
    public static int AUDIO = 2;

}
