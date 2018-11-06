package net.upd4ting.uhcreloaded.nms.common;

import java.io.File;

import org.bukkit.Location;

import net.upd4ting.uhcreloaded.schematic.Schematic.SchematicEvent;

public interface PasteHandler {
	public void paste(File schematicFile, SchematicEvent event, Location loc, Boolean log);
}
