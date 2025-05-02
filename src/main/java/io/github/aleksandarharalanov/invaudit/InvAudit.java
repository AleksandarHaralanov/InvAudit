package io.github.aleksandarharalanov.invaudit;

import io.github.aleksandarharalanov.invaudit.command.InvAuditCommand;
import io.github.aleksandarharalanov.invaudit.core.ItemAuditor;
import io.github.aleksandarharalanov.invaudit.listener.player.PlayerDropItemListener;
import io.github.aleksandarharalanov.invaudit.listener.player.PlayerInteractEntityListener;
import io.github.aleksandarharalanov.invaudit.listener.player.PlayerInteractListener;
import io.github.aleksandarharalanov.invaudit.listener.player.PlayerPickupItemListener;
import io.github.aleksandarharalanov.invaudit.util.config.ConfigUtil;
import io.github.aleksandarharalanov.invaudit.util.log.UpdateUtil;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.aleksandarharalanov.invaudit.util.log.LogUtil;

public class InvAudit extends JavaPlugin {

    private static ConfigUtil config;
    private static InvAudit plugin;

    @Override
    public void onEnable() {
        plugin = this;

        UpdateUtil.checkAvailablePluginUpdates(this,
                "https://api.github.com/repos/AleksandarHaralanov/InvAudit/releases/latest");

        config = new ConfigUtil(this, "config.yml");
        config.load();

        PluginManager pM = getServer().getPluginManager();
        final PlayerDropItemListener pDIL = new PlayerDropItemListener();
        final PlayerInteractEntityListener pIEL = new PlayerInteractEntityListener();
        final PlayerInteractListener pIL = new PlayerInteractListener();
        final PlayerPickupItemListener pPIL = new PlayerPickupItemListener();
        pM.registerEvent(Event.Type.PLAYER_DROP_ITEM, pDIL, Event.Priority.Lowest, this);
        pM.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, pIEL, Event.Priority.Lowest, this);
        pM.registerEvent(Event.Type.PLAYER_INTERACT, pIL, Event.Priority.Lowest, this);
        pM.registerEvent(Event.Type.PLAYER_PICKUP_ITEM, pPIL, Event.Priority.Lowest, this);

        final InvAuditCommand command = new InvAuditCommand(this);
        getCommand("invaudit").setExecutor(command);

        ItemAuditor.startClearTask();

        LogUtil.logConsoleInfo(String.format("[%s] v%s Enabled.",
                getDescription().getName(), getDescription().getVersion()));
    }

    @Override
    public void onDisable() {
        ItemAuditor.stopClearTask();
        LogUtil.logConsoleInfo(String.format("[%s] v%s Disabled.",
                getDescription().getName(), getDescription().getVersion()));
    }

    public static InvAudit getInstance() {
        return plugin;
    }

    public static ConfigUtil getConfig() {
        return config;
    }
}