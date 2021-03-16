package com.garume.Garuff.util.api.save;

import java.io.IOException;

import com.garume.Garuff.Garuff;

public class ConfigStopper extends Thread {

    @Override
    public void run() {
        saveConfig();
    }

    public static void saveConfig() {
        try {
            Garuff.getInstance().clickGuiSave.clickGuiSave();
            Garuff.getInstance().clickGuiSave.saveClickGUIPositions();
            Garuff.log.info("saved config.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}