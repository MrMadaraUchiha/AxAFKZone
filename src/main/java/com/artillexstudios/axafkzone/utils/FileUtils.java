package com.artillexstudios.axafkzone.utils;

import com.artillexstudios.axafkzone.AxAFKZone;
import com.artillexstudios.axafkzone.selection.Selection;
import com.artillexstudios.axafkzone.zones.Zone;
import com.artillexstudios.axafkzone.zones.Zones;
import com.artillexstudios.axapi.config.Config;
import com.artillexstudios.axapi.serializers.Serializers;
import com.artillexstudios.axapi.utils.StringUtils;
import org.bukkit.Bukkit;

import java.io.File;

public class FileUtils {

    public static void loadAll() {
        final File path = new File(AxAFKZone.getInstance().getDataFolder(), "zones");
        if (path.exists()) {
            for (File file : path.listFiles()) {
                final String fileName = file.getName().replace(".yml", "");
                final Zone zone = Zones.getZoneByName(fileName);
                if (zone != null) {
                    zone.reload();
                    Bukkit.getConsoleSender().sendMessage(StringUtils.formatToString("&#CC0055╠ &#FF8855Reloaded zone &f%name%&#FF8855!".replace("%name%", fileName)));
                    continue;
                }

                load(fileName);
                Bukkit.getConsoleSender().sendMessage(StringUtils.formatToString("&#CC0055╠ &#FF8855Loaded zone &f%name%&#FF8855!".replace("%name%", fileName)));
            }
        }
    }

    public static void create(String zoneName, Selection selection) {
        final Config config = new Config(new File(AxAFKZone.getInstance().getDataFolder(), "zones/" + zoneName + ".yml"), AxAFKZone.getInstance().getResource("zones/example-zone.yml"));
        config.set("zone.location1", Serializers.LOCATION.serialize(selection.getPosition1()));
        config.set("zone.location2", Serializers.LOCATION.serialize(selection.getPosition2()));
        config.save();
        load(zoneName);
    }

    public static void load(String zoneName) {
        final Config config = new Config(new File(AxAFKZone.getInstance().getDataFolder(), "zones/" + zoneName + ".yml"));
        final Zone zone = new Zone(zoneName, config);
        Zones.addZone(zone);
    }

    public static void delete(Zone zone) {
        new File(AxAFKZone.getInstance().getDataFolder(), "zones/" + zone.getName() + ".yml").delete();
        Zones.removeZone(zone);
    }
}
