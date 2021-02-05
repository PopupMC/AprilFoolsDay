package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;

public class GameStateModifications extends PacketAdapter {
    public GameStateModifications(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.GAME_STATE_CHANGE);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();

        // Get Reason
        int reason = packet.getIntegers().read(0);

        // If rain is starting or stopping, don't tell the client
        // This doesnt appear to work, oh well, leaving it in
        if(reason == 1 || reason == 2)
            //packet.getIntegers().write(0, 1);
            event.setCancelled(true);

        // If Quick Respawn changes ensure it remains enabled
        else if(reason == 11)
            packet.getFloat().write(0, 1f);
    }
}
