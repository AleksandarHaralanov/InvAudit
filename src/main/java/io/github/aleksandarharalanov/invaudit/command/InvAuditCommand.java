package io.github.aleksandarharalanov.invaudit.command;

import io.github.aleksandarharalanov.invaudit.InvAudit;
import io.github.aleksandarharalanov.invaudit.command.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public final class InvAuditCommand implements CommandExecutor {

    private final Map<String, CommandExecutor> subcommands = new HashMap<>();

    public InvAuditCommand(InvAudit plugin) {
        subcommands.put("about", new AboutCommand(plugin));
        subcommands.put("reload", new ReloadCommand());
        subcommands.put("toggle", new ToggleCommand());
        subcommands.put("timer", new TimerCommand());
        subcommands.put("audit", new AuditCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            HelpCommand.sendHelp(sender);
            return true;
        }

        CommandExecutor subcommand = subcommands.get(args[0].toLowerCase());
        if (subcommand != null) {
            return subcommand.onCommand(sender, command, label, args);
        }

        HelpCommand.sendHelp(sender);
        return true;
    }
}
