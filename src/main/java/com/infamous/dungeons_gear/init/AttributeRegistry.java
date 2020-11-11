package com.infamous.dungeons_gear.init;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class AttributeRegistry {

    public static final IAttribute ATTACK_REACH = new RangedAttribute(
            ((IAttribute)null),
            "generic.attackReach",
            3.0D,
            0.0D,
            1024.0D)
            .setShouldWatch(true);
}
