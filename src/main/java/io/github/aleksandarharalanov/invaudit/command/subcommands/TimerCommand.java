package io.github.aleksandarharalanov.invaudit.command.subcommands;

import io.github.aleksandarharalanov.invaudit.core.ConfigManager;
import io.github.aleksandarharalanov.invaudit.core.ItemAuditor;
import io.github.aleksandarharalanov.invaudit.util.auth.AccessUtil;
import io.github.aleksandarharalanov.invaudit.util.log.LogUtil;
import io.github.aleksandarharalanov.invaudit.util.misc.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class TimerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!AccessUtil.hasPermission(sender, "invaudit.config",
                "[InvAudit] You don't have permission to modify the config.")) {
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ColorUtil.translateColorCodes("&c[InvAudit] Missing timer ticks argument."));
            return true;
        }

        try {
            String input = args[1];
            if (input.toLowerCase().endsWith("l")) {
                input = input.substring(0, input.length() - 1);
            }

            int newTimer = Integer.parseInt(input);
            if (newTimer < 10) {
                sender.sendMessage(ColorUtil.translateColorCodes("&c[InvAudit] Invalid range. Must be 10L ticks or more."));
                return true;
            }

            ItemAuditor.stopClearTask();
            ConfigManager.setTimerTicks(newTimer);

            if (sender instanceof Player) {
                sender.sendMessage(ColorUtil.translateColorCodes(String.format(
                        "&a[InvAudit] Set new clear timer to %dL ticks.", newTimer)));
            }
            LogUtil.logConsoleInfo(String.format(
                    "[InvAudit] Set new clear timer to %dL ticks.", newTimer));

            ItemAuditor.startClearTask();
        } catch (NumberFormatException e) {
            sender.sendMessage(ColorUtil.translateColorCodes("&c[InvAudit] Invalid input. Must be a number of 10L ticks or more."));
        }

        return true;
    }
}
