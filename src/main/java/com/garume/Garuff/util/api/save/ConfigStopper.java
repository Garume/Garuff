package com.garume.Garuff.util.api.save;

import java.io.IOException;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.Garuffhub;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.ModuleManager;

public class ConfigStopper extends Thread {

    @Override
    public void run() {
        saveConfig();
    }

    public static void saveConfig() {
        try {
            ModuleManager.getModuleByName("ClickGuiModule").enable();
            Garuff.getInstance().clickGuiSave.clickGuiSave();
            Garuff.getInstance().clickGuiSave.saveClickGUIPositions();
            Garuffhub.getProfile().save();
            Garuff.log.info("saved config.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}