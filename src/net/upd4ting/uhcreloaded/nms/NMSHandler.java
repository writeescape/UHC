package net.upd4ting.uhcreloaded.nms;

import org.bukkit.Bukkit;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.nms.common.BiomeHandler;
import net.upd4ting.uhcreloaded.nms.common.BorderHandler;
import net.upd4ting.uhcreloaded.nms.common.Init;
import net.upd4ting.uhcreloaded.nms.common.NametagHandler;
import net.upd4ting.uhcreloaded.nms.common.PasteHandler;
import net.upd4ting.uhcreloaded.nms.common.RespawnHandler;
import net.upd4ting.uhcreloaded.nms.common.SpecificWriterHandler;
import net.upd4ting.uhcreloaded.nms.common.TitleHandler;

public class NMSHandler {
	
	private static String NMS_VERSION = "";
	
	private BiomeHandler brh;
	private PasteHandler ph;
	private TitleHandler th;
	private SpecificWriterHandler swh;
	private NametagHandler nh;
	private RespawnHandler rh;
	private BorderHandler bh;
	
	public NMSHandler() {
	    String packageName = UHCReloaded.getInstance().getServer().getClass().getPackage().getName();
	    NMS_VERSION =  packageName.substring(packageName.lastIndexOf('.') + 1);
	    
	    // -- Init
	    brh = (BiomeHandler) instantiate("BiomeReplacer");
	    ph = (PasteHandler) instantiate("Paster");
	    th = (TitleHandler) instantiate("TitleUtils");
	    swh = (SpecificWriterHandler) instantiate("SpecificWriter");
	    nh = (NametagHandler) instantiate("NametagEdit");
	    rh = (RespawnHandler) instantiate("Respawn");
	    bh = (BorderHandler) instantiate("Border");
	}
	
	
	public BiomeHandler getBiomeHandler() { return brh; }
	public PasteHandler getPasterHandler() { return ph; }
	public TitleHandler getTitleHandler() { return th; }
	public SpecificWriterHandler getSpecificWriterHandler() { return swh; }
	public NametagHandler getNametagHandler() { return nh; }
	public RespawnHandler getRespawnHandler() { return rh; }
	public BorderHandler getBorderHandler() { return bh; }
	
	private static Object instantiate(String name) {
		try {
			Bukkit.getLogger().info("Trying loading class");
			Bukkit.getLogger().info("With nms version: " + NMS_VERSION);
			Bukkit.getLogger().info("Class name: " + name);
			Object o = Class.forName("net.upd4ting.uhcreloaded.nms." + NMS_VERSION + "." + name).newInstance();
			if (o instanceof Init)
				((Init)o).init();
			return o;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getNmsVersion() {
		return NMS_VERSION;
	}
	
	public static Boolean isBasicSound() {
		return NMS_VERSION.startsWith("v1_8") || NMS_VERSION.startsWith("v1_7");
	}
}
