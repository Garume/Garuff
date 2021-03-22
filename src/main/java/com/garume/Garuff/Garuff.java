package com.garume.Garuff;

import java.awt.Font;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import com.garume.Garuff.event.EventProces;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.ModuleManager;
import com.garume.Garuff.module.setting.SettingManager;
import com.garume.Garuff.proxy.CommonProxy;
import com.garume.Garuff.rpc.Discord;
import com.garume.Garuff.ui.TabGui;
import com.garume.Garuff.ui.clickgui.ClickGui;
import com.garume.Garuff.util.Refrence;
import com.garume.Garuff.util.api.font.CustomFontRenderer;
import com.garume.Garuff.util.api.render.Cape;
import com.garume.Garuff.util.api.save.ClickGuiLoad;
import com.garume.Garuff.util.api.save.ClickGuiSave;
import com.garume.Garuff.util.api.save.ConfigStopper;
import com.garume.Garuff.util.api.save.SaveandLoad;

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

@Mod(modid = Garuff.MOD_ID, version = Garuff.MOD_VERSION)
public class Garuff {
	public static final String MOD_ID = "garuff";
	public static final String MOD_VERSION = "0.1";

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

	public void fontInit() {

		customFontRenderer = new CustomFontRenderer(new Font("Comic Sans MS", Font.PLAIN, 18), false,false);
		printLog("custom font initialized.");
	}

	private void loadCfg() {
		saveLoad = new SaveandLoad();
		printLog("configs initialized.");
	}

	public void extClientInit() {

		eventProcessor = new EventProces();
		eventProcessor.init();
		printLog("garuff event system initialized.");

		MinecraftForge.EVENT_BUS.register(this);
		printLog("forge event system initialized.");

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

		clickGuiSave = new ClickGuiSave();
		clickGuiLoad = new ClickGuiLoad();
		Runtime.getRuntime().addShutdownHook(new ConfigStopper());

	}

	@EventHandler
	public void init (FMLInitializationEvent event) {
		// Create a thread with extClientInit
		Thread t = new Thread(this::extClientInit);
		// Start it
		t.start();
		// Run opengl
		fontInit();
		try {
			// Wait for extClientInit to finish
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("garuff initialization finished.");
		// Start an async thread with loadCfg. I dunno why but, for some reasons, you cannot put this with
		// The other, if you do, it will create problems with CustomFontRenderer
		new Thread(this::loadCfg).start();

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
}