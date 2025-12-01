package com.c2h6s.tinkers_advanced.core.data.condition;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class ConfigCondition implements ICondition {
    public static final Map<String,ConfigCondition> CONDITIONS = new HashMap<>();
    public static final ResourceLocation ID = TinkersAdvanced.getLocation("config");
    public static final IConditionSerializer<ConfigCondition> SERIALIZER = new ConfigSerializer();
    private final String configName;
    private final Supplier<Boolean> config;

    public ConfigCondition(String configName, Supplier<Boolean> config) {
        this.configName = configName;
        this.config = config;
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public boolean test(IContext context) {
        return config.get();
    }

    public static ConfigCondition add(String prop, Supplier<Boolean> supplier) {
        ConfigCondition conf = new ConfigCondition(prop, supplier);
        CONDITIONS.put(prop.toLowerCase(Locale.ROOT), conf);
        return conf;
    }
    public static void add(ConfigCondition condition) {
        CONDITIONS.put(condition.configName.toLowerCase(Locale.ROOT), condition);
    }

    private static class ConfigSerializer implements Serializer<ConfigCondition>, IConditionSerializer<ConfigCondition> {
        @Override
        public ResourceLocation getID() {
            return ConfigCondition.ID;
        }

        @Override
        public void write(JsonObject json, ConfigCondition value) {
            json.addProperty("prop", value.configName);
        }

        @Override
        public ConfigCondition read(JsonObject json) {
            String prop = GsonHelper.getAsString(json, "prop");
            ConfigCondition config = CONDITIONS.get(prop.toLowerCase(Locale.ROOT));
            if (config == null) {
                throw new JsonSyntaxException("Invalid property name '" + prop + "'");
            }
            return config;
        }

        @Override
        public void serialize(JsonObject json, ConfigCondition condition, JsonSerializationContext context) {
            write(json, condition);
        }

        @Override
        public ConfigCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            return read(json);
        }
    }
}
