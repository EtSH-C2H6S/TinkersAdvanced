package com.c2h6s.tinkers_advanced.content.objects;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class EntityTicker<T extends Entity> {
    public final UUID tickerUuid;
    public final T entity;

    public EntityTicker(@NotNull UUID tickerUuid,@NotNull T entity) {
        this.tickerUuid = tickerUuid;
        this.entity = entity;
    }

    public static final Map<Entity, Map<UUID,EntityTicker<?>>> tickerMap = new HashMap<>();

    public static void endAll(){
        ArrayList<Entity> list = new ArrayList<>(tickerMap.keySet());
        for (Entity entity1:list){
            Map<UUID,EntityTicker<?>> map = tickerMap.get(entity1);
            if (map!=null){
                ArrayList<UUID> list1 = new ArrayList<>(map.keySet());
                for (UUID uuid:list1){
                    EntityTicker<?> ticker = map.get(uuid);
                    if (ticker!= null) ticker.end();
                }
            }
        }
    }

    public void start(){
        if (EntityTicker.tickerMap.get(this.entity)==null){
            Map<UUID,EntityTicker<?>> map = new HashMap<>();
            map.put(this.tickerUuid,this);
            EntityTicker.tickerMap.put(this.entity,map);
        }
        else {
            EntityTicker.tickerMap.get(this.entity).put(this.tickerUuid,this);
        }
    }
    public void end(){
        if (EntityTicker.tickerMap.get(this.entity)!=null){
            EntityTicker.tickerMap.get(this.entity).remove(this.tickerUuid);
        }
    }


    public abstract boolean tick();
}
