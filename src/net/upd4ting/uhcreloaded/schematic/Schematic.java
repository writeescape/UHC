package net.upd4ting.uhcreloaded.schematic;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.Material;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.nms.common.PasteHandler;

public class Schematic {
	
	public interface SchematicEvent {
		public BlockInfo onPaste(Location loc, BlockInfo info);
		public void onPasteEnd();
		public void onFileNotFound();
	}
	
	public static class BlockInfo {
		public Material material;
		public byte data;
		
		public BlockInfo(Material material, byte data) {
			this.material = material;
			this.data = data;
		}
	}
	
	private File schematicFile;
	private SchematicEvent event;
	
	public Schematic(File file) {
		this.schematicFile = file;
	}
	
	public Schematic(File file, SchematicEvent event) {
		this(file);
		this.event = event;
	}

	public void paste(Location loc, Boolean log) {
    	PasteHandler handler = UHCReloaded.getNMSHandler().getPasterHandler();
    	handler.paste(schematicFile, event, loc, log);
    }
}
