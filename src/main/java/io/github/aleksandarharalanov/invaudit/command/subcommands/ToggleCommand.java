package io.github.aleksandarharalanov.invaudit.command.subcommands;

import io.github.aleksandarharalanov.invaudit.core.ConfigManager;
import io.github.aleksandarharalanov.invaudit.core.ItemAuditor;
import io.github.aleksandarharalanov.invaudit.util.auth.AccessUtil;
import io.github.aleksandarharalanov.invaudit.util.misc.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class ToggleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!AccessUtil.hasPermission(sender, "invaudit.config",
                "[InvAudit] You don't have permission to modify the config.")) {
            return true;
        }

        boolean currentState = ConfigManager.getEnabled();
        boolean newState = !currentState;

        ConfigManager.setEnabled(newState);

        if (newState) {
            ItemAuditor.startClearTask();
            sender.sendMessage(ColorUtil.translateColorCodes("&a[InvAudit] Audit task enabled and started."));
        } else {
            ItemAuditor.stopClearTask();
            sender.sendMessage(ColorUtil.translateColorCodes("&c[InvAudit] Audit task disabled and stopped."));
        }

        return true;
    }
}
