package com.idtech;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.io.FileUtils;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class QuickSounds {
	
	private static HashMap<String,SoundEvent> soundEvents = new HashMap<String, SoundEvent>();
	
	public static SoundEvent getSoundEvent(String name) {
		return soundEvents.get(name);
	}
	
	
	public static void registerSounds() {
		
		File jsonFile = new File("../bin/assets/examplemod/sounds.json");
		if (jsonFile.exists()) {
			jsonFile.delete();
		}

		StringBuilder jsonString = new StringBuilder();
		
		File soundFolder = new File("../bin/assets/examplemod/sounds");
		//System.out.println(f.getAbsolutePath() +" flag");
		String[] files = soundFolder.list();
		
		//add files to quicksounds.json
		jsonString.append("{");
		for (String fileName: files) {
			if (fileName.endsWith(".ogg")) {
				//gets rid of extension
				String name = fileName.substring(0, fileName.lastIndexOf('.'));
				
				jsonString.append("\"");
				jsonString.append(name);
				jsonString.append("\": {");
				jsonString.append("   \"category\": \"entity\",");
				jsonString.append("   \"subtitle\": ");
				jsonString.append("\"");
				jsonString.append(name);
				jsonString.append("\",");
				jsonString.append("\"sounds\": [{ \"name\": \"examplemod:"+ name +"\", \"stream\": true}]");
				jsonString.append("},");
				
				//register sound event
				SoundEvent x = registerSound(name);
				System.out.println("registered sound: "+x.getSoundName().toString());
				
				
			}
			
		}
		jsonString.delete(jsonString.length()-1, jsonString.length());
		jsonString.append("}");
		
		try {
			FileUtils.writeStringToFile(jsonFile, jsonString.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static SoundEvent registerSound(String name) {
		ResourceLocation location = new ResourceLocation(BaseMod.MODID,name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		soundEvents.put(name, event);
		return event;
	}
}
