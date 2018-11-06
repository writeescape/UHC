package net.upd4ting.uhcreloaded.inventory;

import net.upd4ting.uhcreloaded.task.Task;

public class RefreshInventory extends Task {
	
	public RefreshInventory() {
		super("RefreshBoard", 20);
	}
	
	@Override
	public void tick() {
		for(Inventory inv : Inventory.currentInventory.values()){
			if(inv.autoRefresh) 
				inv.refresh(true);
		}
	}
}
