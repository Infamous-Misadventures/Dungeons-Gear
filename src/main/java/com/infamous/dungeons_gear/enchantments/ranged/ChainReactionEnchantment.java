package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ChainReactionEnchantment extends Enchantment {

    public ChainReactionEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    @SubscribeEvent
    public static void onChainReactionDamage(LivingDamageEvent event) {
        if (event.getSource() instanceof IndirectEntityDamageSource) {
            IndirectEntityDamageSource indirectEntityDamageSource = (IndirectEntityDamageSource) event.getSource();
            if (indirectEntityDamageSource.getImmediateSource() instanceof AbstractArrowEntity) {
                AbstractArrowEntity arrowEntity = (AbstractArrowEntity) indirectEntityDamageSource.getImmediateSource();

                LivingEntity victim = event.getEntityLiving();
                if (indirectEntityDamageSource.getTrueSource() instanceof LivingEntity) {
                    if (!(indirectEntityDamageSource.getTrueSource() instanceof LivingEntity)) return;
                    LivingEntity attacker = (LivingEntity) indirectEntityDamageSource.getTrueSource();
                    int chainReactionLevel = ModEnchantmentHelper.enchantmentTagToLevel(arrowEntity, RangedEnchantmentList.CHAIN_REACTION);
                    if (chainReactionLevel > 0) {
                        float chainReactionChance = chainReactionLevel * 0.1f;
                        float chainReactionRand = attacker.getRNG().nextFloat();
                        if (chainReactionRand <= chainReactionChance) {
                            ProjectileEffectHelper.fireChainReactionProjectiles(victim.getEntityWorld(), attacker, victim, 3.15F, 1.0F, arrowEntity);
                        }
                    }
                    if (arrowEntity.getTags().contains("FireboltThrower")) {
                        float chainReactionRand = attacker.getRNG().nextFloat();
                        if (chainReactionRand <= 0.1F) {
                            ProjectileEffectHelper.fireChainReactionProjectiles(victim.getEntityWorld(), attacker, victim, 3.15F, 1.0F, arrowEntity);
                        }
                    }
                }
            }
        }
    }

    public int getMaxLevel() {
        return 3;
    }
}
