package com.garume.Garuff;


import java.awt.Font;
import java.io.File;

import com.garume.Garuff.command.Commands;
import com.garume.Garuff.command.client.HelpCommand;
import com.garume.Garuff.command.client.PrefixCommand;
import com.garume.Garuff.command.client.ToggleCommand;
import com.garume.Garuff.event.events.command.CommandCallHandler;
import com.garume.Garuff.ui.command.CommandHandler;
import com.garume.Garuff.util.api.save.*;
import com.garume.Garuff.util.proxy.CommonProxy;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import com.garume.Garuff.event.EventProces;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.ModuleManager;
import com.garume.Garuff.module.setting.SettingManager;
import com.garume.Garuff.rpc.Discord;
import com.garume.Garuff.ui.TabGui;
import com.garume.Garuff.ui.clickgui.ClickGui;
import com.garume.Garuff.util.Refrence;
import com.garume.Garuff.util.api.font.CustomFontRenderer;
import com.garume.Garuff.util.api.render.Cape;

import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.lwjgl.opengl.Display;


@Mod(modid = Garuff.MOD_ID, version = Garuff.MOD_VERSION)
public class Garuff {
	public static final String MOD_ID = "garuff";
	public static final String MOD_VERSION = "0.21";

	public static ModuleManager moduleManager;
	public static SettingManager settingManager;
	public ClickGui clickGui;
	public static TabGui tabGui;
	public static Cape cape;
	public static SaveandLoad saveLoad;
	public EventProces eventProcessor;
	public CustomFontRenderer customFontRenderer;
	public ClickGuiSave clickGuiSave;
	public ClickGuiLoad clickGuiLoad;
	public CommandHandler command;

	private static final Gson gson;

	static {
		gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
	}

	public ModConfig modConfig;

	public static final Logger log = LogManager.getLogger("GARUFF");

	public static final EventBus EVENT_BUS = new EventManager();

	@Instance
	public static Garuff instance;

	public Garuff() {
		instance = this;
	}

	public static Garuff getInstance() {
		return instance;
	}

	@SidedProxy(clientSide = Refrence.CLIENT_PROXY_CLASS, serverSide = Refrence.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	@EventHandler
	public void PreInit (FMLPreInitializationEvent event) {

	}

	public Object syncronize = new Object();

	public void printLog(String text) {
		synchronized (syncronize) {
			log.info(text);
		}
	}


	public void extClientInit() {

		eventProcessor = new EventProces();
		eventProcessor.init();
		printLog("garuff event system initialized.");

		MinecraftForge.EVENT_BUS.register(this);
		printLog("forge event system initialized.");

		customFontRenderer = new CustomFontRenderer(new Font("Arial", Font.PLAIN, 18), false,false);
		printLog("custom font initialized.");

		settingManager = new SettingManager();
		printLog("settings system initialized.");

		MinecraftForge.EVENT_BUS.register(new ModuleManager()); // for onKeyPressed
		moduleManager = new ModuleManager();
		printLog("module system initialized.");

		cape = new Cape();
		printLog("capes initialized.");

		MinecraftForge.EVENT_BUS.register(new TabGui());
		tabGui = new TabGui();
		printLog("tabgui initialized.");

		clickGui = new ClickGui();
		printLog("clickGui initialized.");

		Discord.startRPC();
		printLog("DiscordRPC runched.");

		saveLoad = new SaveandLoad();
		printLog("configs initialized.");

		clickGuiSave = new ClickGuiSave();
		clickGuiLoad = new ClickGuiLoad();
		Runtime.getRuntime().addShutdownHook(new ConfigStopper());

	}

	public void commandInit(){
		// load profile,prefix
		final File basedir = new File(Minecraft.getMinecraft().gameDir, Refrence.MOD_STRING_NAME);
		GsonProfiles profiles = new GsonProfiles(new File(basedir, "profiles"), gson);
		Profile profile = profiles.load(modConfig.name);
		Garuffhub.setProfiles(profiles);
		Garuffhub.setProfile(profile);
		profile.load();
		printLog("Profile loaded.");
		CommandCallHandler.register(new CommandHandler());
		printLog("command initialized.");
		Commands.register(new HelpCommand());
		Commands.register(new ToggleCommand());
		Commands.register(new PrefixCommand());
		//Commands.register(this, new SetCommand());
		printLog("command list loaded.");
	}


	@EventHandler
	public void init(FMLInitializationEvent event) {
		Display.setTitle("Garuff " + Garuff.MOD_VERSION);

		Garuff.log.info("Starting up " + Garuff.MOD_ID + " " + Garuff.MOD_VERSION + "!");
		extClientInit();
		commandInit();
		Garuff.log.info("Finished initialization for " + Garuff.MOD_ID + " " + Garuff.MOD_VERSION + "!");
		Garuff.log.info("garuff initialization finished.");
	}

	@EventHandler
	public void PostInit (FMLPostInitializationEvent event) {

	}

	@SubscribeEvent
	public void key(KeyInputEvent e) {
		if(Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null)
			return;
	try {
		if(Keyboard.isCreated()) {
			if(Keyboard.getEventKeyState()) {
				int keyCode = Keyboard.getEventKey();
				if(keyCode <= 0)
					return;
				for(Module m : ModuleManager.modules) {
					if(m.getKey() == keyCode && keyCode > 0) {
						m.toggle();
					}
				}

			}
		}
		} catch (Exception q) { q.printStackTrace(); }
	}
	private static class ModConfig{
		String name = "default";
	}
}