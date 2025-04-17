package net.minecraft.src.client.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;

import com.mojang.minecraft.gui.GuiButton;
import com.mojang.minecraft.gui.GuiScreen;
import com.mojang.minecraft.gui.GuiSmallButton;
import com.mojang.minecraft.gui.GuiYesNo;
import org.lwjgl.Sys;

public class GuiLinkConfirm extends GuiYesNo {
    private final GuiScreen parentScreen;
    private final String link;
    private final String openText;
    private final String copyText;
    private final String cancelText;
    private final String warningText;

    public GuiLinkConfirm(GuiScreen origin, String link) {
        super(origin, StringTranslate.getInstance().translateKey("gui.link.confirm"), link,  -1);
        this.parentScreen = origin;
        this.link = link;
        StringTranslate stringTranslate = StringTranslate.getInstance();
        this.openText = stringTranslate.translateKey("gui.link.open");
        this.copyText = stringTranslate.translateKey("gui.link.copy");
        this.cancelText = stringTranslate.translateKey("gui.no");
        this.warningText = stringTranslate.translateKey("gui.link.warning");
    }

    public void initGui() {
        int y = this.height / 6 + 96;
        this.controlList.add(new GuiSmallButton(0, this.width / 2 - 50 - 105, y, 100, 20, this.openText));
        this.controlList.add(new GuiSmallButton(1, this.width / 2 - 50, y, 100, 20, this.copyText));
        this.controlList.add(new GuiSmallButton(2, this.width / 2 - 50 + 105, y, 100, 20, this.cancelText));
    }

    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                this.openLinkInBrowser();
                break;
            case 1:
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(this.link), (ClipboardOwner)null);
            case 2:
        }

        this.mc.setCurrentScreen(this.parentScreen);
    }

    private void openLinkInBrowser() {
        Sys.openURL(this.link);
    }

    public void drawScreen(int var1, int var2, float deltaTicks) {
        super.drawScreen(var1, var2, deltaTicks);
        this.drawCenteredString(this.fontRenderer, this.warningText, this.width / 2, 110, 16764108);
    }
}
