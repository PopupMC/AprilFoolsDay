package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;

public class CreeperPaintings extends PacketAdapter {
    public CreeperPaintings(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.SPAWN_ENTITY_PAINTING);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        // Change Painting ID to creeper
        event.getPacket().getIntegers().write(1, 11);
    }
}
