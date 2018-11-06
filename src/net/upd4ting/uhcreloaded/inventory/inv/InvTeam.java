package net.upd4ting.uhcreloaded.inventory.inv;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.inventory.Inventory;
import net.upd4ting.uhcreloaded.inventory.InventoryItem;
import net.upd4ting.uhcreloaded.inventory.InventoryItem.ActionItem;
import net.upd4ting.uhcreloaded.schematic.Schematic.BlockInfo;
import net.upd4ting.uhcreloaded.team.Team;
import net.upd4ting.uhcreloaded.util.UtilItem;

public class InvTeam extends Inventory {
	
	public InvTeam(Player player) {
		super(UHCReloaded.getLangConfiguration().getTeamNameMenu(), 9 * ((Team.getNumber() / 9)+1), player, true);
	}

	@Override
	public void init() {
		Integer index = 0;
		final Integer sizeMax = UHCReloaded.getTeamConfiguration().getTeamSize();
		
		for (final Team t : Team.getTeams()) {
			ArrayList<String> lores = new ArrayList<>();
			for (Player p : t.getPlayers()) lores.add(ChatColor.WHITE + p.getName());
			
			BlockInfo info = t.getBlockInfo();
			String infoSize = ChatColor.GRAY + " [" + ChatColor.GREEN + t.getPlayers().size() + 
					ChatColor.DARK_GRAY + "/" + ChatColor.RED + sizeMax + ChatColor.GRAY + "]";
					
			addItem(index, new InventoryItem(UtilItem.create(t.getName() + infoSize, info.material, 
					info.data, lores), new ActionItem() {

				@Override
				public void run(InventoryClickEvent event) {
					if (t.getPlayers().contains(player) || t.getPlayers().size() >= sizeMax)
						return;
					Team ancient = Team.getTeam(player);
					if (ancient != null) {
						ancient.leave(player);
						ancient.leaveMessage(player);
					}
					t.join(player);
					t.joinMessage(player);
				}
				
			}));
			
			index++;
		}
	}

}
