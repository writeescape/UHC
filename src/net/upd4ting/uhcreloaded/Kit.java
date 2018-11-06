package net.upd4ting.uhcreloaded;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.upd4ting.uhcreloaded.configuration.configs.KitConfig;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.schematic.Schematic.BlockInfo;
import net.upd4ting.uhcreloaded.util.Util;
import net.upd4ting.uhcreloaded.util.Util.ActionMessage;

public class Kit {
	public static List<Kit> kits = new ArrayList<Kit>();
	
	private Integer id;
	private String name;
	private String permission;
	private Double price;
	private List<String> description;
	private List<ItemStack> items;
	private BlockInfo info;
	
	public Kit(Integer id, String name, List<String> description, String permission, Double price, List<ItemStack> items, BlockInfo info) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.permission = permission;
		this.price = price;
		this.items = items;
		this.info = info;
		kits.add(this);
	}
	
	public ItemStack contructItemStack(Player p) {
		final LangConfig config = UHCReloaded.getLangConfiguration();
		KitConfig kitConfig = UHCReloaded.getKitConfiguration();
		UHCPlayer uhcp = UHCPlayer.instanceOf(p);
		
		final List<String> desc = new ArrayList<>();
		desc.add("");
		desc.addAll(description);
		desc.add("");
		
		ItemStack item = new ItemStack(info.material, 1, (short) info.data);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		
		if (p.hasPermission(permission)) {
			// Si purchase on regarde si il l'a ou si il doit l'acheter
			
			if (uhcp.getSelectedKit() == id) {
				Util.sendActionConfigMessage(config.getSelectedKit(), new ActionMessage() {
					@Override
					public void run() {
						desc.add(config.getSelectedKit());
					}
				});
			} else if (kitConfig.isKitPurchaseEnabled()) {
				if (uhcp.hasKit(id)) {
					Util.sendActionConfigMessage(config.getCanSelectKit(), new ActionMessage() {
						@Override
						public void run() {
							desc.add(config.getCanSelectKit());
						}
					});
				} else {
					Util.sendActionConfigMessage(config.getCostKit().replace("%m", Double.toString(price)), new ActionMessage() {
						@Override
						public void run() {
							desc.add(config.getCostKit().replace("%m", Double.toString(price)));
						}
					});
				}
			} else {
				Util.sendActionConfigMessage(config.getCanSelectKit(), new ActionMessage() {
					@Override
					public void run() {
						desc.add(config.getCanSelectKit());
					}
				});
			}
		} else {
			Util.sendActionConfigMessage(config.getDontHavePermissionKit(), new ActionMessage() {
				@Override
				public void run() {
					desc.add(config.getDontHavePermissionKit());
				}
			});
		}
		
		im.setLore(desc);
		item.setItemMeta(im);
		
		return item;
	}
	
	public void give(Player p) {
		for (ItemStack i : items)
			p.getInventory().addItem(i);
		p.updateInventory();
	}
	
	public Integer getID() { return id; }
	public String getName() { return name; }
	public String getPermission() { return permission; }
	public Double getPrice() { return price; }
	public List<String> getDescription() { return description; }
	public List<ItemStack> getItems() { return items; }
	public BlockInfo getInfo() { return info; }
	
	public static Kit getKitByID(Integer id) {
		for (Kit k : kits) {
			if (k.getID() == id)
				return k;
		}
		
		return null;
	}
}
