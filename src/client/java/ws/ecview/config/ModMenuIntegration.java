package ws.ecview.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import ws.ecview.feature.EnderChestConfig;
import ws.ecview.feature.EnderChestSnapshotScreen;

public class ModMenuIntegration implements ModMenuApi {

    private static final String MOD_ID = "enderchestview";

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModMenuIntegration::createConfigScreen;
    }

    private static Screen createConfigScreen(Screen parent) {
        return new ConfigScreen(parent);
    }

    private static final class ConfigScreen extends Screen {

        private final Screen parent;

        private ConfigScreen(Screen parent) {
            super(Text.translatable("enderchestview.page.title"));
            this.parent = parent;
        }

        @Override
        protected void init() {
            super.init();
            addDrawableChild(net.minecraft.client.gui.widget.ButtonWidget.builder(
                            Text.translatable("enderchestview.option.ender_chest_enabled"),
                            b -> {
                                EnderChestConfig.setEnabled(!EnderChestConfig.getEnabled());
                                EnderChestConfig.save();
                                b.setMessage(toggleText());
                            })
                    .dimensions(this.width / 2 - 150, this.height / 2 - 40, 300, 20)
                    .build());
            addDrawableChild(net.minecraft.client.gui.widget.ButtonWidget.builder(
                            Text.translatable("enderchestview.option.ender_chest_button"),
                            b -> {
                                EnderChestConfig.setShowSnapshotButton(!EnderChestConfig.getShowSnapshotButton());
                                EnderChestConfig.save();
                                b.setMessage(buttonText());
                            })
                    .dimensions(this.width / 2 - 150, this.height / 2 - 10, 300, 20)
                    .build());
            addDrawableChild(net.minecraft.client.gui.widget.ButtonWidget.builder(
                            Text.translatable("enderchestview.option.ender_chest_view"),
                            b -> {
                                ClientPlayerEntity player = MinecraftClient.getInstance().player;
                                if (player != null) {
                                    MinecraftClient.getInstance().setScreen(
                                            new EnderChestSnapshotScreen(this, player));
                                }
                            })
                    .dimensions(this.width / 2 - 150, this.height / 2 + 20, 300, 20)
                    .build());
            addDrawableChild(net.minecraft.client.gui.widget.ButtonWidget.builder(
                            Text.translatable("gui.done"),
                            b -> close())
                    .dimensions(this.width / 2 - 150, this.height / 2 + 50, 300, 20)
                    .build());
        }

        private Text toggleText() {
            return Text.translatable("enderchestview.option.ender_chest_enabled")
                    .copy()
                    .append(Text.literal(": " + (EnderChestConfig.getEnabled() ? "ON" : "OFF")));
        }

        private Text buttonText() {
            return Text.translatable("enderchestview.option.ender_chest_button")
                    .copy()
                    .append(Text.literal(": " + (EnderChestConfig.getShowSnapshotButton() ? "ON" : "OFF")));
        }

        @Override
        public void render(net.minecraft.client.gui.DrawContext context, int mouseX, int mouseY, float delta) {
            super.render(context, mouseX, mouseY, delta);
            context.drawCenteredTextWithShadow(
                    this.textRenderer,
                    this.title,
                    this.width / 2,
                    20,
                    0xFFFFFFFF);
        }

        @Override
        public void close() {
            this.client.setScreen(this.parent);
        }
    }
}
