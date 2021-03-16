package com.garume.Garuff.module.modules.render;

import java.util.Stack;
import java.util.function.Function;

import org.lwjgl.input.Keyboard;

import com.garume.Garuff.module.Category;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.setting.settings.BooleanSetting;
import com.garume.Garuff.module.setting.settings.ModeSetting;
import com.garume.Garuff.module.setting.settings.NumberSetting;

public class HalfBright  extends Module{

	public HalfBright() {
		super("HalfBright", "Bright",Keyboard.KEY_M, Category.RENDER);
		this.addSettings(transition,seconds,mode);
		}

    BooleanSetting transition = new BooleanSetting("transition", this, true);
    NumberSetting seconds = new NumberSetting("seconds",this,0.1,0,10,0.1);
    ModeSetting mode = new ModeSetting("mode",this,"Sine","Liner");

    private final Stack<Float> transitionStack = new Stack<>();

    private static float currentBrightness = 0;
    private static boolean inTransition = false;

    private void addTransition(boolean isUpwards) {
        if (transition.isEnabled()) {
            int length = (int) (seconds.getValue() * 20);
            float[] values;
            if(mode.is("Sine")) {
                values = sine(length, isUpwards);
            }
            if(mode.is("Liner")) {
                values = linear(length, isUpwards);
            }
            else {
                values = new float[]{0};
			}

            for (float v : values) {
                transitionStack.add(v);
            }

            inTransition = true;
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        addTransition(true);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        addTransition(false);
    }

    @Override
    public void onUpdate() {
        if (inTransition) {
            if (transitionStack.isEmpty()) {
                inTransition = false;
                currentBrightness = isToggled() ? 1 : 0;
            } else {
                currentBrightness = transitionStack.pop();
            }
        }
    }

    private float[] createTransition(int length, boolean upwards, Function<Float, Float> function) {
        float[] transition = new float[length];
        for (int i = 0; i < length; i++) {
            float v = function.apply(((float) i / (float) length));
            if (upwards) v = 1 - v;
            transition[i] = v;
        }
        return transition;
    }

    private float[] linear(int length, boolean polarity) { // length of 20 = 1 second
        return createTransition(length, polarity, d -> d);
    }

    private float sine(float x) { // (sin(pi*x-(pi/2)) + 1) / 2
        return ((float) Math.sin(Math.PI * x - Math.PI / 2) + 1) / 2;
    }

    private float[] sine(int length, boolean polarity) {
        return createTransition(length, polarity, this::sine);
    }

    public static float getCurrentBrightness() {
        return currentBrightness;
    }

    public static boolean isInTransition() {
        return inTransition;
    }

    public static boolean shouldBeActive() {
        return isInTransition() || currentBrightness == 1; // if in transition or enabled
    }
}
