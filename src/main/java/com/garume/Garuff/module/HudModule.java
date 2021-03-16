package com.garume.Garuff.module;

import java.awt.Point;

import org.lwjgl.input.Keyboard;

import com.garume.Garuff.Garuff;
import com.lukflug.panelstudio.FixedComponent;
import com.lukflug.panelstudio.theme.Theme;


/**
 * @author lukflug
 */

public abstract class HudModule extends Module {

	protected FixedComponent component;
	protected Point position;

	public HudModule (String title, String description, Point defaultPos, Category category) {
		super(title, description, Keyboard.KEY_NONE, category);
		position = defaultPos;
	}

	public abstract void populate (Theme theme);

	public FixedComponent getComponent() {
		return component;
	}

	public void resetPosition() {
		component.setPosition(Garuff.getInstance().clickGui.guiInterface,position);
	}
}