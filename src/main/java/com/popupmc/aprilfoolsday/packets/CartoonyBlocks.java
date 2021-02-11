package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.MultiBlockChangeInfo;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.OnToggleJokeCommand;
import org.bukkit.Material;

import java.util.Arrays;

/*
 * No Human on Earth can figure this !##@!#@ out
* */

public class CartoonyBlocks extends PacketAdapter {
    public CartoonyBlocks(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.MAP_CHUNK);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        // If disabled for this player do nothing, stop here
        if(!OnToggleJokeCommand.getStatus(event.getPlayer()))
            return;

        // I have no ##@@### idea
        byte[] bytes = event.getPacket().getByteArrays().read(0);

//        long val =

//        // Get Blocks in packet
//        MultiBlockChangeInfo[] blocks = event.getPacket().getMultiBlockChangeInfoArrays().read(0);
//
//        // Change Grass Blocks to Green Concrete
//        for(MultiBlockChangeInfo block : blocks) {
//            WrappedBlockData blockData = block.getData();
//
//            if(blockData.getType() == Material.GRASS_BLOCK)
//                blockData.setType(Material.GREEN_CONCRETE);
//
//            block.setData(blockData);
//        }
    }
}
