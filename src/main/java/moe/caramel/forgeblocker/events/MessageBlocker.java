package moe.caramel.forgeblocker.events;

import moe.caramel.forgeblocker.Main;
import moe.caramel.forgeblocker.utils.Constants;
import moe.caramel.forgeblocker.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class MessageBlocker implements Listener, PluginMessageListener {

    private final Main plugin;

    public MessageBlocker(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (plugin.stopDetect) return;
        if (plugin.getConfig().getBoolean(Constants.CONFIG_PLUGIN_BYPASS)
                && player.hasPermission(Constants.PERM_BYPASS_PLUGIN)) return;

        boolean kick = false;
        if (channel.equals(Constants.MESSAGE_CHANNEL_FMLHS)) kick = true;
        else {
            String realMessage = Util.getStringMessage(message).substring(1);
            if (realMessage.contains(Constants.BRAND_FML) || realMessage.contains(Constants.BRAND_FORGE))
                kick = true;
        }

        if (kick) {
            player.kickPlayer(Constants.ETC_KICK_MESSAGE_PREFIX
                    + Util.colorTranslate(plugin.getConfig().getString(Constants.CONFIG_PLUGIN_KICKMESSAGE)));
            plugin.getLogger().info(player.getName() + " 님의 Forge 사용이 감지되어 추방하였습니다.");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (plugin.stopDetect) return;
        Player player = event.getPlayer();

        if (plugin.getConfig().getBoolean(Constants.CONFIG_PLUGIN_BYPASS)
                && player.hasPermission(Constants.PERM_BYPASS_PLUGIN)) return;

        int protocolVersion = (plugin.viaAPI == null) ? player.getProtocolVersion()
                : plugin.viaAPI.getPlayerVersion(player.getUniqueId());

        player.sendPluginMessage(plugin, ((plugin.viaAPI == null) ? player.getProtocolVersion() > 340
                : plugin.viaAPI.getPlayerVersion(player.getUniqueId()) > 340)
                ? Constants.MESSAGE_CHANNEL_BRAND_NEWER : Constants.MESSAGE_CHANNEL_BRAND_OLDER, new byte[]{});

        if (plugin.mcVersion < 11300 && protocolVersion <= 340)
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                if (player.isOnline()) {
                    player.sendPluginMessage(plugin, Constants.MESSAGE_CHANNEL_FMLHS, new byte[]{-2, 0});
                    player.sendPluginMessage(plugin, Constants.MESSAGE_CHANNEL_FMLHS, new byte[]{0, 2, 0, 0, 0, 0});
                    player.sendPluginMessage(plugin, Constants.MESSAGE_CHANNEL_FMLHS, new byte[]{2, 0, 0, 0, 0});
                }
            }, 20L);
    }

}
