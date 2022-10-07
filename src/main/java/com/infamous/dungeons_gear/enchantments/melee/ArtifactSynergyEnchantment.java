package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_libraries.event.ArtifactEvent;
import com.infamous.dungeons_libraries.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.AOECloudHelper;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.PlayerAttackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid = MODID)
public class ArtifactSynergyEnchantment extends DungeonsEnchantment {

    public ArtifactSynergyEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onArtifactSynergyAttack(LivingDamageEvent event){
        if(PlayerAttackHelper.isProbablyNotMeleeDamage(event.getSource())) return;
        if(event.getSource() instanceof OffhandAttackDamageSource) return;
        if(!(event.getSource().getEntity() instanceof LivingEntity)) return;
        LivingEntity attacker = (LivingEntity)event.getSource().getEntity();
        ItemStack mainhand = attacker.getMainHandItem();
        ICombo comboCap = CapabilityHelper.getComboCapability(attacker);
        if(comboCap == null) return;

        if(comboCap.hasArtifactSynergy() && !attacker.level.isClientSide){
            comboCap.setArtifactSynergy(false);
            int artifactSynergyLevel = EnchantmentHelper.getItemEnchantmentLevel(MeleeEnchantmentList.ARTIFACT_SYNERGY, mainhand);
            if(artifactSynergyLevel > 0){
                float damageMultiplier = 1.2F + artifactSynergyLevel * 0.2F;
                float currentDamage = event.getAmount();
                event.setAmount(currentDamage * damageMultiplier);
            }

        }
    }

    @SubscribeEvent
    public static void onArtifactTriggered(ArtifactEvent.Activated event){
        ICombo comboCap = CapabilityHelper.getComboCapability(event.getEntityLiving());
        if(comboCap != null && !comboCap.hasArtifactSynergy()){
            comboCap.setArtifactSynergy(true);
        }
    }
}
