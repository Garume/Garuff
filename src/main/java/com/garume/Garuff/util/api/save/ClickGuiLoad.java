package com.garume.Garuff.util.api.save;

import java.io.IOException;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.ui.clickgui.ClickGuiConfig;

/**
 * @author Hoosiers
 * @since 10/15/2020
 */

public class ClickGuiLoad {

    public ClickGuiLoad() {
        try {
        	clickGuiLoad();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    String fileName = "postman/";
    String mainName = "clickGui/";

    public void clickGuiLoad() throws IOException {
        loadClickGUIPositions();
    }

    public void loadClickGUIPositions() throws IOException {
		Garuff.getInstance().clickGui.gui.loadConfig(new ClickGuiConfig(fileName+mainName));
    }
}