package com.infamous.dungeons_gear.items.artifacts;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_gear.network.PacketBreakItem;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_libraries.capabilities.soulcaster.ISoulCaster;
import com.infamous.dungeons_libraries.capabilities.soulcaster.SoulCasterHelper;
import com.infamous.dungeons_libraries.items.interfaces.ISoulConsumer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.UUID;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.SOUL_GATHERING;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;

public class SoulHealerItem extends ArtifactItem implements ISoulConsumer {
    public SoulHealerItem(Properties properties) {
        super(properties);
        procOnItemUse = true;
    }

    public ActionResult<ItemStack> procArtifact(ArtifactUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        ItemStack itemStack = c.getItemStack();
        if(playerIn == null)  return new ActionResult<>(ActionResultType.FAIL, itemStack);

        LivingEntity mostInjuredAlly = AreaOfEffectHelper.findMostInjuredAlly(playerIn, 12);
        float currentHealth = 0;
        float maxHealth = 0;
        float lostHealth = 0;
        if(mostInjuredAlly != null) {
            currentHealth = mostInjuredAlly.getHealth();
            maxHealth = mostInjuredAlly.getMaxHealth();
            lostHealth = maxHealth - currentHealth;
        }

        float playerCurrentHealth = playerIn.getHealth();
        float playerMaxHealth = playerIn.getMaxHealth();
        float playerLostHealth = playerMaxHealth - playerCurrentHealth;
        if(playerLostHealth >= lostHealth || mostInjuredAlly == null) {
            return healAlly(playerIn, playerLostHealth, playerIn, itemStack);
        }else{
            return healAlly(playerIn, lostHealth, mostInjuredAlly, itemStack);
        }
    }

    private ActionResult<ItemStack> healAlly(PlayerEntity playerEntity, float lostHealth, LivingEntity target, ItemStack itemStack) {
        ISoulCaster soulCasterCapability = SoulCasterHelper.getSoulCasterCapability(playerEntity);
        if(soulCasterCapability == null) return new ActionResult<>(ActionResultType.FAIL, itemStack);
        float toHeal = Math.min(lostHealth, Math.min(target.getMaxHealth() / 5, soulCasterCapability.getSouls() * 0.1f));
        if (toHeal > 0 && SoulCasterHelper.consumeSouls(playerEntity, toHeal*10)) {
            target.heal(toHeal);
            itemStack.hurtAndBreak(1, playerEntity, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getId(), itemStack)));
            ArtifactItem.putArtifactOnCooldown(playerEntity, itemStack.getItem());
            PROXY.spawnParticles(target, ParticleTypes.HEART);
        }
        return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
    }



    @Override
    public int getCooldownInSeconds() {
        return 5;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }

    @Override
    public float getActivationCost(ItemStack stack) {
        return 20;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(int slotIndex) {
        return getAttributeModifiersForSlot(getUUIDForSlot(slotIndex));
    }

    private ImmutableMultimap<Attribute, AttributeModifier> getAttributeModifiersForSlot(UUID slot_uuid) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(SOUL_GATHERING.get(), new AttributeModifier(slot_uuid, "Artifact modifier", 1, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }
}
