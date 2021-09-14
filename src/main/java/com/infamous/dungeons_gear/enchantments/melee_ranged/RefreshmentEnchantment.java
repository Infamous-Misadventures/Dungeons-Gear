package com.infamous.dungeons_gear.enchantments.melee_ranged;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DropsEnchantment;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.items.interfaces.IRangedWeapon;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.PlayerAttackHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class RefreshmentEnchantment extends DropsEnchantment {

    public static final int REFRESHMENT_GOAL = 45;

    public RefreshmentEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE_RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() ||
                !(enchantment instanceof DropsEnchantment);
    }

    @SubscribeEvent
    public static void onRefreshmentKill(LivingDeathEvent event){
        DamageSource damageSource = event.getSource();
        if(damageSource.getTrueSource() instanceof PlayerEntity){
            PlayerEntity killerPlayer = (PlayerEntity) damageSource.getTrueSource();
            if(!PlayerAttackHelper.isProbablyNotMeleeDamage(damageSource)){
                ItemStack mainhand = killerPlayer.getHeldItemMainhand();
                boolean uniqueWeaponFlag = hasRefreshmentBuiltIn(mainhand);
                int refreshmentLevel = EnchantmentHelper.getEnchantmentLevel(MeleeRangedEnchantmentList.REFRESHMENT, mainhand);
                if(uniqueWeaponFlag) refreshmentLevel++;
                if(refreshmentLevel > 0){
                    updateRefreshment(killerPlayer, refreshmentLevel);
                }
            } else if(damageSource.isProjectile()){
                Entity immediateSource = damageSource.getImmediateSource();
                if(immediateSource instanceof AbstractArrowEntity){
                    AbstractArrowEntity arrowEntity = (AbstractArrowEntity) immediateSource;
                    int refreshmentLevel = ModEnchantmentHelper.enchantmentTagToLevel(arrowEntity, MeleeRangedEnchantmentList.REFRESHMENT);
                    if(refreshmentLevel > 0){
                        updateRefreshment(killerPlayer, refreshmentLevel);
                    }
                }
            }
        }
    }

    private static void updateRefreshment(PlayerEntity player, int refreshmentLevel){
        ICombo comboCap = CapabilityHelper.getComboCapability(player);
        if(comboCap == null || refreshmentLevel <= 0) return;
        comboCap.setRefreshmentCounter(comboCap.getRefreshmentCounter() + refreshmentLevel);

        if(comboCap.getRefreshmentCounter() >= REFRESHMENT_GOAL){
            PlayerInventory playerInventory = player.inventory;
            for(int slotId = 0; slotId < playerInventory.getSizeInventory(); slotId++){
                ItemStack currentStack = playerInventory.getStackInSlot(slotId);
                if(currentStack.getItem() instanceof GlassBottleItem){
                    ItemStack healthPotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.HEALING);
                    playerInventory.setInventorySlotContents(slotId, healthPotion);
                    comboCap.setRefreshmentCounter(comboCap.getRefreshmentCounter() - REFRESHMENT_GOAL);
                    return;
                }
            }
        }
    }

    private static boolean hasRefreshmentBuiltIn(ItemStack itemStack){
        Item item = itemStack.getItem();
        return item instanceof IMeleeWeapon && ((IMeleeWeapon<?>) item).hasRefreshmentBuiltIn(itemStack)
                || item instanceof IRangedWeapon && ((IRangedWeapon<?>) item).hasRefreshmentBuiltIn(itemStack);
    }
}
