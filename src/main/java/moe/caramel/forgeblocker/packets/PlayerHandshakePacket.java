package moe.caramel.forgeblocker.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import moe.caramel.forgeblocker.Main;
import moe.caramel.forgeblocker.utils.Constants;

import java.util.Objects;

public class PlayerHandshakePacket extends PacketAdapter {

    private final Main plugin;

    public PlayerHandshakePacket(Main plugin, ListenerPriority listenerPriority, PacketType... types) {
        super(plugin, listenerPriority, types);
        this.plugin = plugin;
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        if (plugin.stopDetect) return;
        PacketContainer packet = event.getPacket();
        if (packet.getProtocols().read(0) == PacketType.Protocol.LOGIN) {
            String host = packet.getStrings().read(0);
            if (host.contains(Constants.HOSTNAME_FML) || host.contains(Constants.HOSTNAME_FML2)) {
                plugin.kickPlayer.add(Objects.requireNonNull(event.getPlayer().getAddress()).getAddress());
            }
        }
    }

}
