package io.github.aleksandarharalanov.invaudit.listener.player;

import io.github.aleksandarharalanov.invaudit.core.ConfigManager;
import io.github.aleksandarharalanov.invaudit.util.auth.AccessUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerListener;

import java.util.HashSet;
import java.util.Set;

public class PlayerDropItemListener extends PlayerListener {

    @Override
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (AccessUtil.hasPermission(player, "invaudit.bypass")) return;

        int itemId = event.getItemDrop().getItemStack().getTypeId();
        Set<Integer> audits = new HashSet<>(ConfigManager.getAudits());
        if (audits.contains(itemId)) event.setCancelled(true);
    }
}
