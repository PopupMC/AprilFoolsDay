package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;

public class NoEntitySound extends PacketAdapter {
    public NoEntitySound(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.ENTITY_SOUND);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        event.setCancelled(true);
    }
}
