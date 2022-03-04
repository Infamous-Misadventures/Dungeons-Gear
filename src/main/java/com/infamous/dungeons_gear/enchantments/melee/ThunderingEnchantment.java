package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.THUNDERING_BASE_DAMAGE;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.THUNDERING_CHANCE;

@Mod.EventBusSubscriber(modid = MODID)
public class ThunderingEnchantment extends AOEDamageEnchantment {

    public ThunderingEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 1;
    }

    @Override
    public void doPostAttack(LivingEntity user, Entity target, int level) {
        if(!(target instanceof LivingEntity)) return;
        float chance = user.getRandom().nextFloat();
        if(chance <=  THUNDERING_CHANCE.get()){
            SoundHelper.playLightningStrikeSounds(user);
            AreaOfEffectHelper.electrifyNearbyEnemies(user, 5, THUNDERING_BASE_DAMAGE.get(), Integer.MAX_VALUE);
            //AbilityUtils.castLightningBolt(user, (LivingEntity)target);
        }
    }
}
