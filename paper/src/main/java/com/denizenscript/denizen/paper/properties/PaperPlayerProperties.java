package com.denizenscript.denizen.paper.properties;

import com.denizenscript.denizen.objects.PlayerTag;
import com.denizenscript.denizencore.objects.Mechanism;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.properties.Property;
import com.denizenscript.denizencore.objects.properties.PropertyParser;

public class PaperPlayerProperties implements Property {
    public static boolean describes(ObjectTag player) {
        return player instanceof PlayerTag
                && ((PlayerTag) player).isOnline();
    }

    public static PaperPlayerProperties getFrom(ObjectTag player) {
        if (!describes(player)) {
            return null;
        }
        return new PaperPlayerProperties((PlayerTag) player);
    }

    public static final String[] handledMechs = new String[] {
            "affects_monster_spawning"
    };

    private PaperPlayerProperties(PlayerTag player) {
        this.player = player;
    }

    PlayerTag player;

    @Override
    public String getPropertyString() {
        return null;
    }

    @Override
    public String getPropertyId() {
        return "PaperPlayerProperties";
    }

    public static void registerTags() {

        // <--[tag]
        // @attribute <PlayerTag.affects_monster_spawning>
        // @returns ElementTag(Boolean)
        // @mechanism PlayerTag.affects_monster_spawning
        // @group properties
        // @Plugin Paper
        // @description
        // Returns whether the player affects monster spawning. When false, no monsters will spawn naturally because of this player.
        // -->
        PropertyParser.<PaperPlayerProperties, ElementTag>registerTag(ElementTag.class, "affects_monster_spawning", (attribute, player) -> {
            return new ElementTag(player.player.getPlayerEntity().getAffectsSpawning());
        });
    }

    @Override
    public void adjust(Mechanism mechanism) {

        // <--[mechanism]
        // @object PlayerTag
        // @name affects_monster_spawning
        // @input ElementTag(Boolean)
        // @Plugin Paper
        // @description
        // Sets whether this player affects monster spawning. When false, no monsters will spawn naturally because of this player.
        // @tags
        // <PlayerTag.affects_monster_spawning>
        // -->
        if (mechanism.matches("affects_monster_spawning") && mechanism.requireBoolean()) {
            player.getPlayerEntity().setAffectsSpawning(mechanism.getValue().asBoolean());
        }
    }
}