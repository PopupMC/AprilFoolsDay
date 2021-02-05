package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import org.bukkit.WorldType;

public class LoginModifications extends PacketAdapter {
    public LoginModifications(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.LOGIN);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();

        // Set world type to the nether
        packet.getDimensions().write(0, -1);

        // Disable Respawn Screen
        packet.getBooleans().write(2, false);

        // Flat World
        packet.getWorldTypeModifier().write(0, WorldType.FLAT);
    }
}
