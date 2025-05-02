# 🎒 InvAudit
Audits player inventories for illegal items. Automatically removes any illegal items and prevents players from opening containers while possessing them. Players are also unable to pick up illegal items from the ground or drop them.

---
## 🤝 Contributions, Suggestions, and Issues
Consider helping InvAudit become even more versatile and robust.

It is **highly recommended** to visit the [CONTRIBUTING](https://github.com/AleksandarHaralanov/InvAudit/blob/master/.github/CONTRIBUTING.md) guide for details on how to get started and where to focus your efforts.

For any issues with the plugin, or suggestions, please submit them [here](https://github.com/AleksandarHaralanov/InvAudit/issues).

---
## ⬇️ Download
Latest releases of **InvAudit** can be found here on [GitHub](https://github.com/AleksandarHaralanov/InvAudit/releases).<br/>

The plugin is fully open-source and transparent.<br/>
If you'd like additional peace of mind, you're welcome to scan the `.jar` file using [VirusTotal](https://www.virustotal.com/gui/home/upload).

---
## 📋 Requirements
Your server must be running one of the following software: [CB1060](https://github.com/AleksandarHaralanov/InvAudit/raw/refs/heads/master/libs/craftbukkit-1060.jar), [Project Poseidon](https://github.com/retromcorg/Project-Poseidon) or [UberBukkit](https://github.com/Moresteck/Project-Poseidon-Uberbukkit).

---
## 🚀 Usage
By default, only OPs have permission.

Use PermissionsEx or similar plugins to grant groups the permission, enabling the commands.

### Commands:
- `/ia` - View InvAudit commands.
- `/ia about` - About InvAudit.
- `/ia reload` - `invaudit.config` - Reload InvAudit configuration.
- `/ia toggle` - `invaudit.config` - Toggle InvAudit plugin.
- `/ia timer <ticks>` - `invaudit.config` - Alter clear timer for illegal items.
- `/ia audit <ID>` - `invaudit.config` - Add/remove illegal items.

### Permissions:
#### Single permissions:
- `invaudit.bypass` - Allows player to bypass the InvAudit protection.
- `invaudit.config` - Allows player to reload and modify the InvAudit configuration.
#### Wildcard permissions:
- `invaudit.*` - Wildcard permission that grants all permissions.

---
## ⚙️ Configuration
InvAudit generates a configuration file with the default settings.

#### Config `config.yml`:
```yaml
enabled: true           # Toggles the automatic clear task of InvAudit on/off
clear-timer-ticks: 100  # Ticks between each automatic clear task (20 ticks is 1 second)
audit-items: []         # List of IDs of items that are considered illegal
```

## 📊 Project Statistics
<img src="https://repobeats.axiom.co/api/embed/90e23efcc1acd5afb5cebb59524342088e9aca95.svg" alt="Statistics" />