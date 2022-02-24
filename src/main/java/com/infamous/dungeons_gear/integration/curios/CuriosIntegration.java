package com.infamous.dungeons_gear.integration.curios;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.integration.curios.client.CuriosClientIntegration.curiosIconTexture;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CuriosIntegration {

	public static final String ARTIFACT_IDENTIFIER = "artifact";

	@SubscribeEvent
	public static void enqueue(InterModEnqueueEvent event) {
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder(ARTIFACT_IDENTIFIER).icon(curiosIconTexture).priority(10).size(3).build());
	}
	
}