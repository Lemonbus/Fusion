package fusion.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;
import fusion.utils.Chat;
import fusion.utils.KitGUI;
import fusion.utils.ShopGUI;
import fusion.utils.mKitUser;

/**
	 * 
	 * Copyright GummyPvP. Created on May 17, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class InventoryClick implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		if (!(e.getWhoClicked() instanceof Player)) return;
		if (e.getInventory().getName() == null) return;
		if (!e.getInventory().getName().contains("GummyPvP - ")) return;
		if (e.getCurrentItem() == null) return;
		if (!e.getCurrentItem().hasItemMeta()) return;
		if (e.getCurrentItem().getItemMeta() == null) return;
		
		Player player = (Player) e.getWhoClicked();
		
		e.setCancelled(true);
		
		if (e.getInventory().getName().contains(KitGUI.INVENTORY_NAME)) {
			
			if (KitManager.getInstance().valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) == null) {
				
				switch (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().toLowerCase())) {
				
				case "-->":
					
					new KitGUI(player, KitGUI.getPage(player) + 1);
					
					break;
					
				case "<--":
					
					new KitGUI(player, KitGUI.getPage(player) - 1);
					
					break;
					
				case "kit shop":
					
					new ShopGUI(player);
					
					break;
				}
				
				return;
			}
			
			KitManager.getInstance().valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())).apply(player);
			
			return;
		}
		
		if (e.getInventory().getName().contains(ShopGUI.INVENTORY_NAME)) {
			
			if (KitManager.getInstance().valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) == null) return;
			
			Kit kit = KitManager.getInstance().valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
			
			if (!((mKitUser.getInstance(player).getCandies() - kit.getCost()) >= 0)) {
				
				Chat.getInstance().messagePlayer(player, Chat.SECONDARY_BASE + "You do not have enough candies to purchase " + Chat.IMPORTANT_COLOR + kit.getName());
				
				return;
			}
			
			mKitUser.getInstance(player).addOwnedKit(kit);
			mKitUser.getInstance(player).removeCandies(kit.getCost());
			
			Chat.getInstance().messagePlayer(player, Chat.SECONDARY_BASE + "You now own " + Chat.IMPORTANT_COLOR + kit.getName());
			
			player.closeInventory();
			
			return;
		}
	}

}