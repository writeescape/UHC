package net.upd4ting.uhcreloaded;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import net.upd4ting.uhcreloaded.util.IDTools;
import net.upd4ting.uhcreloaded.exception.InvalidConfigException;
import net.upd4ting.uhcreloaded.nms.NMSHandler;

public class GenRule {
	public static List<GenRule> rules = new ArrayList<>();
	
	private Material material;
	private Integer propability;
	private int minHeight;
	private int maxHeight;
	private int size;
	private int rounds;
	
	public GenRule(Material material, Integer propability, int minHeight, int maxHeight, int size, int rounds) {
		this.material = material; 
		this.propability = propability; 
		this.minHeight = minHeight; 
		this.maxHeight = maxHeight; 
		this.size = size; 
		this.rounds = rounds;
		rules.add(this);
	}
	
	public Material getMaterial() { return this.material; } 
	public Integer getPropability() { return this.propability; } 
	public int getMinHeight() { return this.minHeight; } 
	public int getMaxHeight() { return this.maxHeight; } 
	public int getSize() { return this.size; } 
	public int getRounds() { return this.rounds; }
	
	@SuppressWarnings("depreciation")
	public static GenRule unparse(String parsed) throws InvalidConfigException {
		String[] splitted = parsed.split(":");
		NMSHandler nms = new NMSHandler();
		String version = nms.getNmsVersion();
			if(version.startsWith("v1_13")){
				IDTools I = new IDTools();
				Integer mat = Integer.parseInt(splitted[0]);
				Material material = I.getMaterial(mat);
				Integer probability = Integer.parseInt(splitted[1]);
				Integer minHeight = Integer.parseInt(splitted[2]);
				Integer maxHeight = Integer.parseInt(splitted[3]);
				Integer size = Integer.parseInt(splitted[4]);
				Integer round = Integer.parseInt(splitted[5]);
				return new GenRule(material, probability, minHeight, maxHeight, size, round);
			} else {
				Integer mat = Integer.parseInt(splitted[0]);
				Material material = Material.getMaterial(mat);
				Integer probability = Integer.parseInt(splitted[1]);
				Integer minHeight = Integer.parseInt(splitted[2]);
				Integer maxHeight = Integer.parseInt(splitted[3]);
				Integer size = Integer.parseInt(splitted[4]);
				Integer round = Integer.parseInt(splitted[5]);
				return new GenRule(material, probability, minHeight, maxHeight, size, round);
			}
	}
}
