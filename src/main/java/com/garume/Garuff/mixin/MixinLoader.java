package com.garume.Garuff.mixin;

import java.util.Map;

import javax.annotation.Nullable;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import com.garume.Garuff.Garuff;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
public class MixinLoader implements IFMLLoadingPlugin {

	public MixinLoader(){
		Garuff.log.info("mixins initialized");
		MixinBootstrap.init();
		Mixins.addConfiguration("mixins.Garuff.json");
	}

	@Override
	public String[] getASMTransformerClass(){
		return new String[0];
	}

	@Override
	public String getModContainerClass(){
		return null;
	}

	@Nullable
	@Override
	public String getSetupClass(){
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data){
	}

	@Override
	public String getAccessTransformerClass(){
		return null;
	}
}

