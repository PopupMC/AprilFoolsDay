package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.Toggle;
import org.bukkit.WorldType;

public class RespawnModifications extends PacketAdapter {
    public RespawnModifications(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.RESPAWN);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        // If disabled for this player do nothing, stop here
        if(!Toggle.getStatus(event.getPlayer()))
            return;

        PacketContainer packet = event.getPacket();

        // Set world type to the nether
        packet.getDimensions().write(0, -1);

        // Flat World
        packet.getWorldTypeModifier().write(0, WorldType.FLAT);
    }
}
