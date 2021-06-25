package com.infamous.dungeons_gear.client;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = DungeonsGear.MODID)
public class SoulRender {
    private static final ResourceLocation hud = new ResourceLocation(DungeonsGear.MODID, "textures/misc/soul.png");
    private static float increment = 0;

    @SubscribeEvent
    public static void displaySoul(RenderGameOverlayEvent.Post event) {
        MainWindow sr = event.getWindow();
        final Minecraft mc = Minecraft.getInstance();
        if (event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR) && mc.getRenderViewEntity() instanceof PlayerEntity) {
            //draw souls
            mc.getTextureManager().bindTexture(hud);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

            PlayerEntity p = (PlayerEntity) mc.getRenderViewEntity();
            if(p == null) return;
            ICombo comboCapability = CapabilityHelper.getComboCapability(p);
            if(comboCapability == null) return;

            float souls = comboCapability.getSouls();
            float maxSouls = comboCapability.getMaxSouls(p);
            int width = sr.getScaledWidth();

            GlStateManager.enableRescaleNormal();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderHelper.enableStandardItemLighting();

            mc.getProfiler().startSection("soulBar");
            boolean close = true;
            if (increment < souls) {
                increment++;
                close = false;
            }
            if (increment > souls) {
                increment--;
                close = !close;
            }
            if (close) increment = souls;
            if (increment > 0) {
                int k = (int) (increment / maxSouls * 122.0F);
                int l = sr.getScaledHeight() - 7;
                AbstractGui.blit(event.getMatrixStack(), width - 123, l, 0, 0, 122, 5, 121, 10);
                AbstractGui.blit(event.getMatrixStack(), width - 123, l, 0, 5, k, 5, 121, 10);
            }

            mc.getProfiler().endSection();
            mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
            RenderHelper.disableStandardItemLighting();
            RenderSystem.disableBlend();
        }


    }

}
