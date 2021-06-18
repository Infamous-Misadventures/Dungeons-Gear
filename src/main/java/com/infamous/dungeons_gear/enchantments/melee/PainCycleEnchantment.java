package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class PainCycleEnchantment extends DungeonsEnchantment {
    protected PainCycleEnchantment() {
        super(Rarity.COMMON, ModEnchantmentTypes.MELEE, ModEnchantmentTypes.MELEE_RANGED_SLOT);
    }

}
