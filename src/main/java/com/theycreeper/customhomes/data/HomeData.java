package com.theycreeper.customhomes.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.util.ValueIOSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HomeData implements ValueIOSerializable {
    private final Map<String, Home> homes = new HashMap<>();

    @Override
    public void serialize(ValueOutput output) {
        var list = output.childrenList("homes");
        for (Map.Entry<String, Home> entry : homes.entrySet()) {
            ValueOutput child = list.addChild();
            child.putString("name", entry.getKey());
            ValueOutput data = child.child("data");
            data.putDouble("x", entry.getValue().getX());
            data.putDouble("y", entry.getValue().getY());
            data.putDouble("z", entry.getValue().getZ());
            data.putString("dimension", entry.getValue().getDimension());
        }
    }

    @Override
    public void deserialize(ValueInput input) {
        homes.clear();
        for (ValueInput child : input.childrenListOrEmpty("homes")) {
            String name = child.getStringOr("name", "");

            ValueInput data = child.childOrEmpty("data"); // <- fix

            Home home = new Home(
                    data.getDoubleOr("x", 0.0),
                    data.getDoubleOr("y", 0.0),
                    data.getDoubleOr("z", 0.0),
                    data.getStringOr("dimension", "minecraft:overworld")
            );
            homes.put(name, home);
        }
    }

    public void addHome(String name, Home home) {
        homes.put(name, home);
    }

    public String getHomeList() {
        String homesList = "";
        for (String name : homes.keySet().toArray(new String[0])) {
            homesList = homesList + (name + ",");
        }
        return homesList;
    }

    public Home getHome(String name) {
        return homes.get(name);
    }

    public boolean hasHome(String name) {
        return homes.containsKey(name);
    }

    public void removeHome(String name) {
        homes.remove(name);
    }

    public boolean renameHome(String oldName, String newName) {
        if (!homes.containsKey(oldName) || homes.containsKey(newName)) {
            return false;
        }

        Home home = homes.remove(oldName);
        homes.put(newName, home);
        return true;
    }

    public Map<String, Home> getAllHomes() {
        return new HashMap<>(homes);
    }

    public CompoundTag save(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        ListTag homesList = new ListTag();

        for (Map.Entry<String, Home> entry : homes.entrySet()) {
            CompoundTag homeTag = new CompoundTag();
            homeTag.putString("name", entry.getKey());
            homeTag.put("data", entry.getValue().save());
            homesList.add(homeTag);
        }

        tag.put("homes", homesList);
        return tag;
    }

    public void load(CompoundTag tag, HolderLookup.Provider provider) {
        homes.clear();
        Optional<ListTag> homesList = tag.getList("homes");

        for (int i = 0; i < homesList.get().size(); i++) {
            CompoundTag homeTag = (CompoundTag) homesList.get().get(i);
            String name = homeTag.get("name").toString();
            Home home = Home.load((CompoundTag) homeTag.get("data"));
            homes.put(name, home);
        }
    }

    public static class Home {
        private final double x;
        private final double y;
        private final double z;
        private final String dimension;

        public Home(double x, double y, double z, String dimension) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.dimension = dimension;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public String getDimension() {
            return dimension;
        }

        public CompoundTag save() {
            CompoundTag tag = new CompoundTag();
            tag.putDouble("x", x);
            tag.putDouble("y", y);
            tag.putDouble("z", z);
            tag.putString("dimension", dimension);
            return tag;
        }

        public static Home load(CompoundTag tag) {
            double x = tag.get("x").asDouble().orElse(0.0);
            double y = tag.get("y").asDouble().orElse(0.0);
            double z = tag.get("z").asDouble().orElse(0.0);
            String dimension = tag.get("dimension").asString().orElse("minecraft:overworld");

            return new Home(x, y, z, dimension);
        }
    }
}
