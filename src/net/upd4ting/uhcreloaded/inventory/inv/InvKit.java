package net.upd4ting.uhcreloaded.inventory.inv;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.upd4ting.uhcreloaded.Kit;
import net.upd4ting.uhcreloaded.UHCPlayer;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.KitConfig;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.inventory.Inventory;
import net.upd4ting.uhcreloaded.inventory.InventoryItem;
import net.upd4ting.uhcreloaded.inventory.InventoryItem.ActionItem;
import net.upd4ting.uhcreloaded.util.Util;

public class InvKit extends Inventory {

	public InvKit(Player player) {
		super(UHCReloaded.getLangConfiguration().getKitNameMenu().replace("%k", UHCPlayer.instanceOf(player).getSelectedKitName()), 9 * ((Kit.kits.size()
				/ 9)+1), player, true);
	}

	@Override
	public void init() {
		final KitConfig config = UHCReloaded.getKitConfiguration();
		final LangConfig langConfig = UHCReloaded.getLangConfiguration();
		final UHCPlayer uhcp = UHCPlayer.instanceOf(player);
		Integer index = 0;
		
		for (final Kit k : Kit.kits) {
			
			addItem(index, new InventoryItem(k.contructItemStack(player), new ActionItem() {

				@Override
				public void run(InventoryClickEvent event) {
					if (uhcp.getSelectedKit() == k.getID())
						return;
					
					if (player.hasPermission(k.getPermission())) {
						if (config.isKitPurchaseEnabled()) {
							if (uhcp.hasKit(k.getID())) {
								uhcp.setSelectedKit(k.getID());
								Util.sendConfigMessage(player, langConfig.getSelectKit().replace("%k", k.getName()));
							} else {
								Double money = UHCReloaded.getEconomyHandler().getBalance(player);
								
								if (money >= k.getPrice()) {
									UHCReloaded.getEconomyHandler().withdrawPlayer(player, k.getPrice());
									uhcp.addKit(k.getID());
									Util.sendConfigMessage(player, langConfig.getBuyKit().replace("%k", k.getName()));
								} else
									Util.sendConfigMessage(player, langConfig.getNotEnoughMoneyKit());
							}
						} else {
							uhcp.setSelectedKit(k.getID());
							Util.sendConfigMessage(player, langConfig.getSelectKit().replace("%k", k.getName()));
						}
					} else
						Util.sendConfigMessage(player, langConfig.getDontHavePermissionKit());
					
					player.closeInventory();
				}
				
			}));
			
			index++;
		}
	}
}
