package com.c2h6s.tinkers_advanced.util;

import com.c2h6s.etstlib.util.ModListConstants;
import com.c2h6s.tinkers_advanced.content.compact.pnc.capability.ItemMachineConvertHandler;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.tools.item.IModifiable;

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

    public static String getUnitLong(long amount){
        int a = (int) Math.log10(amount);
        int b =a/3;
        switch (b){
            case 1->{
                return String.format("%.2f",(float)amount/1E+3)+" k";
            }
            case 2->{
                return String.format("%.2f",(float)amount/1E+6)+" M";
            }
            case 3->{
                return String.format("%.2f",(float)amount/1E+9)+" G";
            }
            case 4->{
                return String.format("%.2f",(float)amount/1E+12)+" T";
            }
            case 5->{
                return String.format("%.2f",(float)amount/1E+15)+" P";
            }
            case 6->{
                return String.format("%.2f",(float)amount/1E+18)+" E";
            }
            default-> {
                return amount + " ";
            }
        }
    }

    public static String getEnergyString(long amount){
        return getUnitLong(amount)+"FE";
    }

    public static <T> @NotNull LazyOptional<T> getCompactCapability(@NotNull ItemStack stack, @NotNull Capability<T> capability, @Nullable Direction direction){
        if (ModListConstants.PnCLoaded){
            ItemMachineConvertHandler handler = new ItemMachineConvertHandler(stack);
            if (handler.getCapability(capability).isPresent()) return handler.getCapability(capability).cast();
        }
        return LazyOptional.empty();
    }

    public static boolean isModifiable(ItemStack stack){
        return stack.getItem() instanceof IModifiable;
    }
}
