package me.btelnyy.itemutils.listener;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.btelnyy.itemutils.ItemUtils;
import me.btelnyy.itemutils.constants.ConfigData;
import me.btelnyy.itemutils.service.Utils;
import me.btelnyy.itemutils.service.file_manager.Configuration;
import me.btelnyy.itemutils.service.file_manager.FileID;

public class EventListener implements Listener {
    private static final Configuration language = ItemUtils.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
    
    @EventHandler
    public void onItemCraft(CraftItemEvent event){
        ItemStack item = event.getCurrentItem();
        if(ConfigData.getInstance().BannedItemTypes.contains(item.getType())){
            event.setCancelled(true);
            return;
        }
        //assumes item is not banned.
        ItemMeta meta = item.getItemMeta();
        meta.getLore().add(Utils.colored(language.getString("tooltip_item_crafter_message").replace("{player}", event.getWhoClicked().getName())));
        List<String> lore = meta.getLore();
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public void onItemPickup(EntityPickupItemEvent event){
        ItemStack item = event.getItem().getItemStack();
        if(ConfigData.getInstance().BannedItemTypes.contains(item.getType())){
            event.setCancelled(true);
            return;
        }
        ItemMeta meta = item.getItemMeta();
        for(String s : meta.getLore()){
            if(s.split(":")[0].equals(Utils.colored(language.getString("tooltip_item_crafter_message")).split(":")[0])){
                return;
            }else{
                continue;
            }
        }
        meta.getLore().add(Utils.colored(language.getString("tooltip_item_looter_message").replace("{player}", event.getEntity().getName())));
        List<String> lore = meta.getLore();
        meta.setLore(lore);
        item.setItemMeta(meta);
    }
}
