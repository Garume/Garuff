package com.garume.Garuff.module;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.event.events.RenderEvent;
import com.garume.Garuff.module.modules.client.ClientFont;
import com.garume.Garuff.module.modules.exploits.EzDupe;
import com.garume.Garuff.module.modules.exploits.InfinityChat;
import com.garume.Garuff.module.modules.exploits.RPC;
import com.garume.Garuff.module.modules.games.Snake;
import com.garume.Garuff.module.modules.player.GuiMove;
import com.garume.Garuff.module.modules.player.NoSlow;
import com.garume.Garuff.module.modules.player.Sprint;
import com.garume.Garuff.module.modules.pvp.NotAutoCrystal;
import com.garume.Garuff.module.modules.render.Esp;
import com.garume.Garuff.module.modules.render.Freecam;
import com.garume.Garuff.module.modules.render.FullBright;
import com.garume.Garuff.module.modules.render.NoRender;
import com.garume.Garuff.ui.HudEditor;
import com.garume.Garuff.ui.clickgui.ClickGuiModule;
import com.garume.Garuff.ui.hud.ArrayListt;
import com.garume.Garuff.ui.hud.PlayerModel;
import com.garume.Garuff.util.api.render.JTessellator;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class ModuleManager {


    public static ModuleManager INSTANCE;
	public static ArrayList<Module> modules;

	public ModuleManager() {
		modules = new ArrayList<>();
		//client
		ModuleManager.modules.add(new ClickGuiModule());
		ModuleManager.modules.add(new ClientFont());
		//player
		ModuleManager.modules.add(new Sprint());
		ModuleManager.modules.add(new NoSlow());
		ModuleManager.modules.add(new GuiMove());
		//exploit
		ModuleManager.modules.add(new EzDupe());
		ModuleManager.modules.add(new RPC());
		ModuleManager.modules.add(new InfinityChat());
		//pvp
		ModuleManager.modules.add(new NotAutoCrystal());
		//render
		ModuleManager.modules.add(new Freecam());
		ModuleManager.modules.add(new Esp());
		ModuleManager.modules.add(new FullBright());
		ModuleManager.modules.add(new NoRender());
		//game
		ModuleManager.modules.add(new Snake());
		//hud
		ModuleManager.modules.add(new HudEditor());
		ModuleManager.modules.add(new ArrayListt());
		ModuleManager.modules.add(new PlayerModel());
        INSTANCE = this;

	}

	public static void onUpdate() {
		modules.stream().filter(Module::isToggled).forEach(Module::onUpdate);
	}
	public static void onRender() {
		modules.stream().filter(Module::isToggled).forEach(Module::onRender);
		Garuff.getInstance().clickGui.render();
	}

	@SuppressWarnings("static-access")
	public static void onKey(InputUpdateEvent event){
        INSTANCE.modules.forEach( mod -> {
            if (mod.isToggled()) mod.onKey(event);
        });
    }

	public static void onWorldRender(RenderWorldLastEvent event) {
		Minecraft.getMinecraft().profiler.startSection("postman");
		Minecraft.getMinecraft().profiler.startSection("setup");
		JTessellator.prepare();
		RenderEvent e = new RenderEvent(event.getPartialTicks());
		Minecraft.getMinecraft().profiler.endSection();

		modules.stream().filter(module -> module.isToggled()).forEach(module -> {
			Minecraft.getMinecraft().profiler.startSection(module.getName());
			module.onWorldRender(e);
			Minecraft.getMinecraft().profiler.endSection();
		});

		Minecraft.getMinecraft().profiler.startSection("release");
		JTessellator.release();
		Minecraft.getMinecraft().profiler.endSection();
		Minecraft.getMinecraft().profiler.endSection();
	}

	public Module getModule (String name){
		for(Module m : ModuleManager.modules) {
			if(m.getName().equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}

	public static boolean isModuleEnabled(String name){
		Module m = modules.stream().filter(mm->mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
		return m.isToggled();
	}

	public static ArrayList<Module> getModules(){
		return modules;
	}

	public static ArrayList<Module> getModulesInCategory(Category c){
		ArrayList<Module> list = (ArrayList<Module>) getModules().stream().filter(m -> m.getCategory().equals(c)).collect(Collectors.toList());
		return list;
	}


	public static List<Module> getModulesByCategory(Category c){
		List<Module> modules = new ArrayList<Module>();

		for(Module m : ModuleManager.modules) {
			if(m.getCategory() == c)
				modules.add(m);
		}
		return modules;
	}
	public static Module getModuleByName(String name){
		Module m = modules.stream().filter(mm->mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
		return m;
	}

}
