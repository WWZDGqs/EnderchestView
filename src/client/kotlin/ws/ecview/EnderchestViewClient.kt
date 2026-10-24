package ws.ecview

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.nbt.NbtOps
import net.minecraft.registry.RegistryOps
import ws.ecview.feature.EnderChestConfig
import ws.ecview.feature.EnderChestScreenHook
import ws.ecview.feature.EnderChestSnapshot

object EnderchestViewClient : ClientModInitializer {

	private var snapshotLoaded = false

	override fun onInitializeClient() {
		EnderChestConfig.load()
		EnderChestScreenHook.register()

		ClientTickEvents.END_CLIENT_TICK.register { client ->
			if (snapshotLoaded) return@register
			val player = client.player ?: return@register
			snapshotLoaded = true
			val ops = RegistryOps.of(NbtOps.INSTANCE, player.getRegistryManager())
			EnderChestSnapshot.load(ops)
		}
	}
}
