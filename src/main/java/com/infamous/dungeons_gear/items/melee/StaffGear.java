package com.infamous.dungeons_gear.items.melee;

import com.infamous.dungeons_libraries.items.gearconfig.MeleeGear;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;

public class StaffGear extends MeleeGear {

    public StaffGear(IItemTier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean shouldProcSpecialEffects(ItemStack stack, LivingEntity attacker, int combo) {
        combo -= 1;
        combo %= 10;
        return combo == 3 || combo == 6 || combo == 8 || combo == 0;
    }

    @Override
    public float damageMultiplier(ItemStack stack, LivingEntity attacker, int combo) {
        float additional = 0;
        switch (combo % 10) {
            case 3:
                additional = 0.2f;
                break;
            case 6:
                additional = 0.4f;
                break;
            case 8:
                additional = 0.6f;
                break;
            case 0:
                additional = 0.8f;
                break;

        }
        return 1 + additional;
    }

}
