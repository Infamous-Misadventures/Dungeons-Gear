package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.utilties.AOECloudHelper;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class ShockwaveEnchantment extends AOEDamageEnchantment {

    public static final float SHOCKWAVE_DAMAGE_MULTIPLIER = 0.25F;

    public ShockwaveEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() ||
                (!(enchantment instanceof DamageEnchantment) && !(enchantment instanceof DamageBoostEnchantment) && !(enchantment instanceof AOEDamageEnchantment));
    }

    @SubscribeEvent
    public static void onVanillaCriticalHit(CriticalHitEvent event){
        if(event.getPlayer() != null
                && event.isVanillaCritical()
        ){
            PlayerEntity attacker = (PlayerEntity) event.getPlayer();
            LivingEntity victim = event.getEntityLiving();
            ItemStack mainhand = attacker.getHeldItemMainhand();
            boolean uniqueWeaponFlag = mainhand.getItem() == DeferredItemInit.WHIRLWIND.get();
            if(ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.SHOCKWAVE) || uniqueWeaponFlag){
                int shockwaveLevel = EnchantmentHelper.getEnchantmentLevel(MeleeEnchantmentList.SHOCKWAVE, mainhand);
                if(uniqueWeaponFlag) shockwaveLevel += 1;
                // gets the attack damage of the original attack before any enchantment modifiers are added
                float attackDamage = (float)attacker.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();
                float cooledAttackStrength = attacker.getCooledAttackStrength(0.5F);
                attackDamage *= 0.2F + cooledAttackStrength * cooledAttackStrength * 0.8F;

                float shockwaveDamage = attackDamage * SHOCKWAVE_DAMAGE_MULTIPLIER;
                shockwaveDamage *= (shockwaveLevel + 1) / 2.0F;
                victim.world.playSound((PlayerEntity) null, victim.getPosX(), victim.getPosY(), victim.getPosZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.PLAYERS, 64.0F, 1.0F);
                AOECloudHelper.spawnCritCloud(attacker, victim, 3.0f);
                AreaOfEffectHelper.causeShockwave(attacker, victim, shockwaveDamage, 3.0f);
            }
        }
    }
}
