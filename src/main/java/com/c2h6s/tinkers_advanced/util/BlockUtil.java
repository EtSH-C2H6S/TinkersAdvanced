package com.c2h6s.tinkers_advanced.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class BlockUtil {
    public static List<BlockPos> getPosInRange(AABB aabb){
        List<BlockPos> list = new ArrayList<>();
        for (var x =(int) aabb.minX;x<aabb.maxX;x++){
            for (var y =(int) aabb.minY;y<aabb.maxX;y++){
                for (var z =(int) aabb.minZ;z<aabb.maxX;z++){
                    list.add(new BlockPos(x,y,z));
                }
            }
        }
        return list;
    }
}
