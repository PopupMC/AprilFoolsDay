package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.OnToggleJokeCommand;

public class NoEntitySound extends PacketAdapter {
    public NoEntitySound(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.ENTITY_SOUND);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        // If disabled for this player do nothing, stop here
        if(!OnToggleJokeCommand.getStatus(event.getPlayer()))
            return;

        event.setCancelled(true);
    }
}
