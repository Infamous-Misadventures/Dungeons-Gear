package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid = MODID)
public class ChainReactionEnchantment extends DungeonsEnchantment {

    public static final String INTRINSIC_CHAIN_REACTION_TAG = "IntrinsicChainReaction";

    public ChainReactionEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    @SubscribeEvent
    public static void onChainReactionDamage(LivingDamageEvent event) {
        if (event.getSource() instanceof IndirectEntityDamageSource) {
            IndirectEntityDamageSource indirectEntityDamageSource = (IndirectEntityDamageSource) event.getSource();
            if (indirectEntityDamageSource.getDirectEntity() instanceof AbstractArrowEntity) {
                AbstractArrowEntity arrowEntity = (AbstractArrowEntity) indirectEntityDamageSource.getDirectEntity();

                LivingEntity victim = event.getEntityLiving();
                if (indirectEntityDamageSource.getEntity() instanceof LivingEntity) {
                    if (!(indirectEntityDamageSource.getEntity() instanceof LivingEntity)) return;
                    LivingEntity attacker = (LivingEntity) indirectEntityDamageSource.getEntity();
                    int chainReactionLevel = ArrowHelper.enchantmentTagToLevel(arrowEntity, RangedEnchantmentList.CHAIN_REACTION);
                    if (chainReactionLevel > 0) {
                        float chainReactionChance = chainReactionLevel * 0.1f;
                        float chainReactionRand = attacker.getRandom().nextFloat();
                        if (chainReactionRand <= chainReactionChance) {
                            ProjectileEffectHelper.fireChainReactionProjectiles(victim.getCommandSenderWorld(), attacker, victim, 3.15F, 1.0F, arrowEntity);
                        }
                    }
                    if (arrowEntity.getTags().contains(INTRINSIC_CHAIN_REACTION_TAG)) {
                        float chainReactionRand = attacker.getRandom().nextFloat();
                        if (chainReactionRand <= 0.1F) {
                            ProjectileEffectHelper.fireChainReactionProjectiles(victim.getCommandSenderWorld(), attacker, victim, 3.15F, 1.0F, arrowEntity);
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
