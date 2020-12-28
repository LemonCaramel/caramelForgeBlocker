package moe.caramel.forgeblocker;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import moe.caramel.forgeblocker.commands.MainCommand;
import moe.caramel.forgeblocker.events.MessageBlocker;
import moe.caramel.forgeblocker.events.PlayerCommandEvent;
import moe.caramel.forgeblocker.events.PlayerHandshakeEvent;
import moe.caramel.forgeblocker.packets.PlayerHandshakePacket;
import moe.caramel.forgeblocker.utils.Constants;
import moe.caramel.forgeblocker.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Main extends JavaPlugin {

    public Main plugin;
    public ViaAPI<?> viaAPI;
    public List<InetAddress> kickPlayer;
    public ProtocolManager protocolManager;

    public int mcVersion;
    public boolean stopDetect;

    @Override
    public void onLoad() {
        this.plugin = this;
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.kickPlayer = new ArrayList<>();
    }

    @Override
    public void onEnable() {
        /* Get Server Version */
        mcVersion = Util.getMCVersion(getServer().getBukkitVersion());

        if (mcVersion == -1) {
            this.getLogger().warning(getServer().getBukkitVersion().split("-")[0]
                    + " 버전은 존재하지 않는 버전입니다. 서버를 종료합니다.");
            getServer().shutdown();
            return;
        }

        /* Plugin Edit Detect */
        if (!this.getDescription().getName().equals(Constants.PLUGIN_INFO_NAME) ||
                this.getDescription().getCommands().get(Constants.PLUGIN_MAIN_COMMAND) == null) {
            this.getLogger().warning("플러그인 변조가 감지되었습니다. 서버를 종료합니다.");
            getServer().shutdown();
            return;
        }

        /* Plugin Update Channel Check */
        if (!this.getDescription().getVersion().contains(Constants.PLUGIN_CHANNEL_STABLE)) {
            this.getLogger().warning("급하게 제작 된 플러그인이므로 버그가 존재할 수도 있습니다. " +
                    "플러그인이 배포 된 페이지에서 업데이트를 수시로 확인해 주시기 바랍니다.");
            this.getLogger().warning("이 메시지는 정식 버전이 아닌 플러그인에서 출력되며, 제거할 수 없습니다.");
        }

        /* Initialize Config & Vars */
        stopDetect = false;
        this.saveDefaultConfig();
        if (getConfig().getInt("config-version", 0) != Constants.PLUGIN_CONFIG_VERSION) {
            File file = new File(plugin.getDataFolder(), "config.yml");
            file.delete();
            this.saveDefaultConfig();
            this.getLogger().info("The version of config has changed. Please reconfigure config.yml.");
        }

        /* Register Command */
        MainCommand mainCommand = new MainCommand(this);
        Objects.requireNonNull(getServer().getPluginCommand("caramelforgeblocker")).setExecutor(mainCommand);
        Objects.requireNonNull(getServer().getPluginCommand("caramelforgeblocker")).setTabCompleter(mainCommand);
        if (mcVersion >= 11300)
            getServer().getPluginManager().registerEvents(new PlayerCommandEvent.MC1_13(), this);
        else getServer().getPluginManager().registerEvents(new PlayerCommandEvent.MC1_12(), this);

        /* Detect ViaVersion */
        if (getServer().getPluginManager().getPlugin("ViaVersion") != null) {
            viaAPI = Via.getAPI();
            this.getLogger().info("ViaVersion 사용이 감지되었습니다. 접속 버전에 따라 감지 방식이 변경됩니다.");
        }

        /* Bungeecord Check && Packet Detect */
        if (getServer().spigot().getSpigotConfig().getBoolean(Constants.CONFIG_SPIGOT_BUNGEE, true))
            this.getLogger().warning("Bungeecord 사용이 감지되었습니다. Forge 접속 패킷 추적 기능을 비활성화 합니다.");
        else {
            if (getConfig().getBoolean(Constants.CONFIG_PLUGIN_PACKET, true)) {
                protocolManager.addPacketListener(new PlayerHandshakePacket(this,
                        ListenerPriority.LOWEST, PacketType.Handshake.Client.SET_PROTOCOL));
                getServer().getPluginManager().registerEvents(new PlayerHandshakeEvent(this), this);
            } else this.getLogger().warning("패킷 사용 감지 기능이 비활성화되어 있습니다. " +
                    "플러그인 우회가 가능할 수도 있습니다. 활성화 후 플러그인을 재시작해야 합니다.");
        }

        /* Register MessageChannel */
        MessageBlocker messageBlocker = new MessageBlocker(this);
        getServer().getPluginManager().registerEvents(messageBlocker, this);
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, Constants.MESSAGE_CHANNEL_BRAND_NEWER);
        Bukkit.getMessenger().registerIncomingPluginChannel(this,
                Constants.MESSAGE_CHANNEL_BRAND_NEWER, messageBlocker);
        if (mcVersion >= 11300)
            this.getLogger().info("1.13 이상의 서버 버전을 감지하였습니다. 이 플러그인은 신채널을 사용합니다.");
        else {
            this.getLogger().info("1.13 미만의 서버 버전을 감지하였습니다. 이 플러그인은 구채널과, 신채널을 사용합니다.");
            Bukkit.getMessenger().registerOutgoingPluginChannel(this, Constants.MESSAGE_CHANNEL_BRAND_OLDER);
            Bukkit.getMessenger().registerIncomingPluginChannel(this,
                    Constants.MESSAGE_CHANNEL_BRAND_OLDER, messageBlocker);
            Bukkit.getMessenger().registerOutgoingPluginChannel(this, Constants.MESSAGE_CHANNEL_FMLHS);
            Bukkit.getMessenger().registerIncomingPluginChannel(this,
                    Constants.MESSAGE_CHANNEL_FMLHS, messageBlocker);
        }

    }

}
