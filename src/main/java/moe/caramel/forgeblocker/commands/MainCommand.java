package moe.caramel.forgeblocker.commands;

import moe.caramel.forgeblocker.Main;
import moe.caramel.forgeblocker.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Arrays;
import java.util.List;

public class MainCommand implements CommandExecutor, TabExecutor {

    private final Main plugin;

    public MainCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission(Constants.PERM_COMMAND_PLUGIN)) {
            sender.sendMessage(" §6§lcaramelAntiCheat §f§l>  §fcaramelForgeBlocker은 AntiCheat 프로젝트 중 일부입니다.");
            sender.sendMessage(" §6§lcaramelAntiCheat §f§l>  §f" + plugin.getDescription().getVersion()
                    + " | by LemonCaramel §7( https://caramel.moe/ )");
            sender.sendMessage(" §6§lcaramelAntiCheat §f§l>  §f제작자의 서버 - §7카운터온라인 ( zbcounter.net )");
            return true;
        }

        if (args.length <= 0) {
            sender.sendMessage("§7----==== [ §d" + Constants.PLUGIN_INFO_NAME + " §7] ====----");
            sender.sendMessage("  §e버전 §f- §7" + plugin.getDescription().getVersion());
            sender.sendMessage(" ");
            sender.sendMessage("  §e/" + alias + " reload §f- §7플러그인을 리로드합니다.");
            sender.sendMessage("  §e/" + alias + " toggle §f- §7Forge 클라이언트 제한 모드를 변경합니다. (서버 재시작 시 초기화)");
            sender.sendMessage(" ");
            sender.sendMessage("§7해당 플러그인은 무료이며 한마포에서 다운로드 가능합니다! (주기적으로 업데이트를 확인해 주세요!)");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.reloadConfig();
                sender.sendMessage("§b플러그인 설정의 리로드가 완료되었습니다.");
                break;
            case "toggle":
                if (plugin.stopDetect) sender.sendMessage("§aForge 클라이언트 제한 모드를 활성화하였습니다.");
                else sender.sendMessage("§cForge 클라이언트 제한 모드를 비활성화하였습니다.");
                plugin.stopDetect = !plugin.stopDetect;
                break;
            default:
                sender.sendMessage("§c올바른 명령어를 입력해주세요!");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission(Constants.PERM_COMMAND_PLUGIN)) return null;
        if (args.length == 1) return Arrays.asList("reload", "toggle");
        else return null;
    }

}
