package com.c2h6s.tinkers_advanced.util;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static com.c2h6s.etstlib.util.EntityInRangeUtil.toManhattanDistance;

public class CommonUtil {
    public static ArmorItem.Type[] ALL_ARMOR = new ArmorItem.Type[]{ArmorItem.Type.HELMET, ArmorItem.Type.CHESTPLATE, ArmorItem.Type.LEGGINGS, ArmorItem.Type.BOOTS};

    public static String KEY_ATTACKER = "tinkers_advanced_attacker";

    public static Entity getNearestEntity(@NotNull Entity centerEntity, float range, @NotNull IntOpenHashSet ignoreEntityIds, @NotNull Predicate<Entity> predicate){
        List<Entity> list = centerEntity.level().getEntitiesOfClass(Entity.class,new AABB(centerEntity.blockPosition()).inflate(range));
        list.sort(Comparator.comparingDouble(toManhattanDistance(centerEntity)));
        for (Entity entity:list){
            if (!ignoreEntityIds.contains(entity.getId())&&predicate.test(entity)&&entity!=centerEntity){
                return entity;
            }
        }
        return null;
    }
}
