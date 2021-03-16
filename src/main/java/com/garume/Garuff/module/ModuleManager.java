package com.garume.Garuff.module;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.event.events.RenderEvent;
import com.garume.Garuff.module.modules.exploits.EzDupe;
import com.garume.Garuff.module.modules.exploits.InfinityChat;
import com.garume.Garuff.module.modules.exploits.RPC;
import com.garume.Garuff.module.modules.player.GuiMove;
import com.garume.Garuff.module.modules.player.NoSlow;
import com.garume.Garuff.module.modules.player.Sprint;
import com.garume.Garuff.module.modules.pvp.NotAutoCrystal;
import com.garume.Garuff.module.modules.render.HalfBright;
import com.garume.Garuff.ui.clickgui.ClickGuiModule;
import com.garume.Garuff.util.api.render.JTessellator;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class ModuleManager {
	public static ArrayList<Module> modules;

	public ModuleManager() {
		modules = new ArrayList<>();
		//client
		ModuleManager.modules.add(new ClickGuiModule());
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
		ModuleManager.modules.add(new HalfBright());

	}

	public static void onUpdate() {
		modules.stream().filter(Module::isToggled).forEach(Module::onUpdate);
	}
	public static void onRender() {
		modules.stream().filter(Module::isToggled).forEach(Module::onRender);
		Garuff.getInstance().clickGui.render();
	}

	public static void onWorldRender(RenderWorldLastEvent event) {
		Minecraft.getMinecraft().mcProfiler.startSection("postman");
		Minecraft.getMinecraft().mcProfiler.startSection("setup");
		JTessellator.prepare();
		RenderEvent e = new RenderEvent(event.getPartialTicks());
		Minecraft.getMinecraft().mcProfiler.endSection();

		modules.stream().filter(module -> module.isToggled()).forEach(module -> {
			Minecraft.getMinecraft().mcProfiler.startSection(module.getName());
			module.onWorldRender(e);
			Minecraft.getMinecraft().mcProfiler.endSection();
		});

		Minecraft.getMinecraft().mcProfiler.startSection("release");
		JTessellator.release();
		Minecraft.getMinecraft().mcProfiler.endSection();
		Minecraft.getMinecraft().mcProfiler.endSection();
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