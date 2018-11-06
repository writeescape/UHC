package net.upd4ting.uhcreloaded.configuration.configs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.upd4ting.uhcreloaded.nms.NMSHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.upd4ting.uhcreloaded.Kit;
import net.upd4ting.uhcreloaded.configuration.Configuration;
import net.upd4ting.uhcreloaded.exception.InvalidConfigException;
import net.upd4ting.uhcreloaded.schematic.Schematic.BlockInfo;


public class KitConfig extends Configuration {
	
	private Boolean kitEnabled;
	private Boolean kitPurchase;
	private String kitPurchaseTable;
	private Integer itemId;
	private Integer itemSlot;
	
	public KitConfig() {
		super(ConfType.STARTUP, "kits.yml", "Kits");
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public void loadData() throws InvalidConfigException {
		kitEnabled = conf.getBoolean("enabled");
		kitPurchase = conf.getBoolean("Purchase.enabled");
		kitPurchaseTable = conf.getString("Purchase.table");
		itemId = conf.getInt("id-item");
		itemSlot = conf.getInt("slot-item");

		Set<String> kits = (Set<String>) conf.getConfigurationSection("Kits").getKeys(false);
		
		for (String s : kits) {
			String target = "Kits." + s + ".";
			
			Integer id = conf.getInt(target + "id");
			String name = getStringConfig(target + "name");
			String permission = conf.getString(target + "permission");
			Integer itemID = conf.getInt(target + "itemID");
            String itemMAT = conf.getString(target + "material");
			byte itemData = (byte) conf.getInt(target + "itemData");
			Double price = conf.getDouble(target + "price");
			List<String> description = getStringListConfig(target + "description");
			
			List<ItemStack> items = new ArrayList<>();
			
			Set<String> item = (Set<String>) conf.getConfigurationSection(target + "items").getKeys(false);
			
			for (String a : item) {
				String target2 = target + "items." + a + ".";
				Integer idItem = conf.getInt(target2 + "id");
                String itemMat = conf.getString(target2 + "material");
				int data = conf.getInt(target2 + "data");
				Integer amount = conf.getInt(target2 + "amount");
				String nameItem = getStringConfig(target2 + "name");
				List<String> descriptionItem = getStringListConfig(target2 + "description");
				List<String> enchantement = (List<String>) conf.getList(target2 + "enchantment");
                if(NMSHandler.getNmsVersion().startsWith("v1_13")){

                    Material itemMaterial = Material.matchMaterial(itemMat);

                    ItemStack i = new ItemStack(itemMaterial,amount);

                    items.add(i);
                } else {
                    ItemStack i = new ItemStack(idItem, amount);
                    ItemMeta im = i.getItemMeta();
                    if (!nameItem.equals("default"))
                        im.setDisplayName(nameItem);

                    descriptionItem.add(0, "");

                    im.setLore(descriptionItem);
                    i.setItemMeta(im);

                    if (enchantement != null) {
                        for (String enchant : enchantement) {
                            String[] e = enchant.split(":");
                            String nameEnchant = e[0];
                            Integer level = Integer.parseInt(e[1]);
                            Enchantment en = Enchantment.getByName(nameEnchant);

                            if (en == null)
                                throw new InvalidConfigException("[kits.yml] Enchantment name: " + nameEnchant + " does not exist !");
                            else
                                i.addUnsafeEnchantment(en, level);
                        }
                    }
                    items.add(i);
                }

				


			}
            if(NMSHandler.getNmsVersion().startsWith("v1_13")) {
                new Kit(id, name, description, permission, price, items, new BlockInfo(Material.getMaterial(itemMAT), itemData));
            } else {
                new Kit(id, name, description, permission, price, items, new BlockInfo(Material.getMaterial(itemID), itemData));
            }
		}
	}

	public Boolean isKitEnabled() { return kitEnabled; }
	public Boolean isKitPurchaseEnabled() { return kitPurchase; }
	public String getKitPurchaseTable() { return kitPurchaseTable; }
	public Integer getItemId() { return itemId; }
	public Integer getItemSlot() { return itemSlot; }
}
