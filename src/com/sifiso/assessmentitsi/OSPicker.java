/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifiso.assessmentitsi;

import java.io.File;

/**
 *
 * @author sifiso.mtshweni
 */
public class OSPicker {

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0);
    }

    public static boolean isSolaris() {
        return (OS.contains("sunos"));
    }

    public static File getTmpDir(String name) {
        /**
         The path can be made configurable*/
        String tmp = null;
        if (OSPicker.isWindows()) {
            tmp = "C:/tmp/" + name;
        } else if (OSPicker.isMac()) {
            tmp = "/tmp/" + name;
        } else if (OSPicker.isUnix()) {
            tmp = "/tmp/" + name;
        } else if (OSPicker.isSolaris()) {
            tmp = "/tmp/" + name;
        } else {
            System.out.println("Your OS is not support!!");
        }

        File f = new File(tmp);
        if (!f.exists()) {
            f.mkdirs();
        }
        return f;

    }

}
