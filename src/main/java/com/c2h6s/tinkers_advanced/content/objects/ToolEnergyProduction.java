package com.c2h6s.tinkers_advanced.content.objects;

import com.c2h6s.etstlib.util.CommonUtil;
import com.c2h6s.etstlib.util.IToolUuidGetter;
import com.c2h6s.etstlib.util.ToolEnergyUtil;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ToolEnergyProduction {
    public static ResourceLocation LOCATION = TinkersAdvanced.getLocation("energy_production");
    public static final Map<UUID, ToolEnergyProduction> ENERGY_PRODUCTION_MAP = new HashMap<>();
    public long energyToProduce;
    public long energyToReduce;
    public int generatePerTick;
    public int consumePerTick;
    public int lastGeneration;
    public int coolDown;
    public int gameTimeLastTick;
    public ToolStack toolStack;
    public static final String KEY_ENERGY_GEN_REMAIN = "energy_generate_remain";
    public static final String KEY_ENERGY_COST_REMAIN = "energy_cost_remain";
    public static final String KEY_GENERATE_PER_TICK = "generate_per_tick";
    public static final String KEY_CONSUME_PER_TICK = "consume_per_tick";
    public ToolEnergyProduction(ToolStack toolStack, long energyToProduce, long energyToReduce, int generatePerTick, int consumePerTick){
        this.energyToProduce = energyToProduce;
        this.energyToReduce = energyToReduce;
        this.generatePerTick = generatePerTick;
        this.consumePerTick = consumePerTick;
        this.toolStack = toolStack;
    }
    public static ToolEnergyProduction emptyInstance(ToolStack tool){
        return new ToolEnergyProduction(tool,0,0,0,0);
    }
    public void start(){
        ENERGY_PRODUCTION_MAP.put(((IToolUuidGetter)this.toolStack).etstlib$getUuid(),this);
    }
    public static void updateProduction(ToolStack toolStack,ToolEnergyProduction production){
        IToolUuidGetter.getUuid(toolStack).ifPresent(uuid ->
                ENERGY_PRODUCTION_MAP.put(uuid,production));

    }

    public static @Nullable ToolEnergyProduction getFromMap(ToolStack toolStack){
        if (CommonUtil.getUuidFromTool(toolStack)!=null) return ENERGY_PRODUCTION_MAP.get(CommonUtil.getUuidFromTool(toolStack));
        return null;
    }
    public static @NotNull ToolEnergyProduction getOrCreate(@NotNull ToolStack tool){
        ToolEnergyProduction production = getFromMap(tool);
        if (production!=null){
            return production;
        }
        production = emptyInstance(tool);
        if (CommonUtil.getUuidFromTool(tool)!=null) ENERGY_PRODUCTION_MAP.put(CommonUtil.getUuidFromTool(tool),production);
        return production;
    }

    public boolean needUpdate(){
        return this.coolDown<=0&&(this.requireGeneration()||this.requireConsumption());
    }
    public boolean requireGeneration(){
        return this.energyToProduce<=0||this.generatePerTick<=0;
    }
    public boolean requireConsumption(){
        return this.energyToReduce<=0||this.consumePerTick<=0;
    }

    public static void setCoolDown(ToolStack toolStack,int coolDown){
        UUID key = ((IToolUuidGetter)toolStack).etstlib$getUuid();
        ToolEnergyProduction production = ENERGY_PRODUCTION_MAP.get(key);
        if (production!=null){
            production.coolDown=coolDown;
            ENERGY_PRODUCTION_MAP.put(key,production);
        }
    }

    public void tick(){
        if (this.coolDown>0) this.coolDown--;
        if (this.toolStack==null){
            this.remove();
            return;
        }
        this.lastGeneration =0;
        if (this.energyToProduce>0) {
            int toGenerate = (int) Math.min(this.generatePerTick, this.energyToProduce);
            toGenerate = ToolEnergyUtil.receiveEnergy(this.toolStack, toGenerate, true);
            if (toGenerate > 0) {
                this.energyToProduce -= toGenerate;
                ToolEnergyUtil.receiveEnergy(this.toolStack, toGenerate, false);
                this.lastGeneration+=toGenerate;
            }
        }
        if (this.energyToReduce>0) {
            int toConsume = (int) Math.min(this.consumePerTick, this.energyToReduce);
            toConsume = ToolEnergyUtil.extractEnergy(this.toolStack, toConsume, true);
            if (toConsume > 0) {
                this.energyToReduce -= toConsume;
                ToolEnergyUtil.extractEnergy(this.toolStack, toConsume, false);
                this.lastGeneration-=toConsume;
            }
        }
    }

    public void remove(){
        ENERGY_PRODUCTION_MAP.remove(((IToolUuidGetter)this.toolStack).etstlib$getUuid());
    }

    public static void addEnergyAndUpdate(ToolStack toolStack,ToolEnergyProduction production,long toAdd){
        if (toAdd!=0){
            if (toAdd > 0) {
                production.energyToProduce+=toAdd;
            } else production.energyToReduce-=toAdd;
            updateProduction(toolStack,production);
        }
    }
    public static void addEnergyAndUpdate(ToolStack toolStack,long toAdd){
        addEnergyAndUpdate(toolStack,ENERGY_PRODUCTION_MAP.get(((IToolUuidGetter)toolStack).etstlib$getUuid()),toAdd);
    }

    public static @NotNull ToolEnergyProduction readFromTool(ToolStack tool){
        CompoundTag tag = tool.getPersistentData().getCompound(LOCATION);
        ToolEnergyProduction energyProduction = new ToolEnergyProduction(tool,
                tag.getLong(KEY_ENERGY_GEN_REMAIN),
                tag.getLong(KEY_ENERGY_COST_REMAIN),
                tag.getInt(KEY_GENERATE_PER_TICK),
                tag.getInt(KEY_CONSUME_PER_TICK)
        );
        tool.getPersistentData().remove(LOCATION);
        energyProduction.start();
        return energyProduction;
    }
    public void saveToTool(){
        ModDataNBT nbt = this.toolStack.getPersistentData();
        CompoundTag tag = new CompoundTag();
        tag.putLong(KEY_ENERGY_GEN_REMAIN,this.energyToProduce);
        tag.putLong(KEY_ENERGY_COST_REMAIN,this.energyToReduce);
        tag.putInt(KEY_CONSUME_PER_TICK,this.generatePerTick);
        tag.putInt(KEY_GENERATE_PER_TICK,this.consumePerTick);
        nbt.put(LOCATION,tag);
    }

    public static void saveAll(){
        ENERGY_PRODUCTION_MAP.forEach(((toolStack1, energyProduction) -> energyProduction.saveToTool()));
    }

    public static ToolEnergyProduction readFromNetwork(FriendlyByteBuf buf){
        return new ToolEnergyProduction(ToolStack.from( buf.readItem()),buf.readLong(),buf.readLong(),buf.readInt(),buf.readInt());
    }

    public void toNetwork (FriendlyByteBuf buf){
        ItemStack stack = this.toolStack.createStack();
        buf.writeItem(stack);
        buf.writeLong(this.energyToProduce);
        buf.writeLong(this.energyToReduce);
        buf.writeInt(this.generatePerTick);
        buf.writeInt(this.consumePerTick);
        buf.writeInt(this.lastGeneration);
    }
}
