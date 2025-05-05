package net.minecraft.src.client.gui;


import java.util.List;
import java.util.Random;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.GuiButton;
import com.mojang.minecraft.render.Tessellator;

import net.minecraft.fox2code.ChatColors;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class GuiSlot implements ChatColors {
    private final Minecraft mc;
    protected final int width;
    protected final int height;
    protected final int top;
    protected final int bottom;
    protected final int right;
    protected final int left;
    protected final int slotHeight;
    private int scrollUpButtonID;
    private int scrollDownButtonID;
    protected int maxWidth;
    protected int maxHeight;
    private float initialClickY = -2.0F;
    private float scrollMultiplier;
    private float amountScrolled;
    private int selectedElement = -1;
    private long lastClicked = 0L;
    private boolean field_25123_p = true;
    private boolean field_27262_q;
    private int field_27261_r;
    static Random random = new Random();

    public GuiSlot(Minecraft var1, int var2, int var3, int var4, int var5, int var6) {
        this.mc = var1;
        this.width = var2;
        this.height = var3;
        this.top = var4;
        this.bottom = var5;
        this.slotHeight = var6;
        this.left = 0;
        this.right = var2;
    }

    public void func_27258_a(boolean var1) {
        this.field_25123_p = var1;
    }

    protected void func_27259_a(boolean var1, int var2) {
        this.field_27262_q = var1;
        this.field_27261_r = var2;
        if (!var1) {
            this.field_27261_r = 0;
        }

    }

    protected abstract int getSize();

    protected abstract void elementClicked(int var1, boolean var2);

    protected abstract boolean isSelected(int var1);

    protected int getContentHeight() {
        int size = 0;

        for(int i = 0; i < this.getSize(); ++i) {
            size += this.getSlotHeight(i);
        }

        return size + this.field_27261_r;
    }

    protected abstract void drawBackground();

    protected abstract void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5);

    protected void func_27260_a(int var1, int var2, Tessellator var3) {
    }

    protected void func_27255_a(int var1, int var2) {
    }

    protected void func_27257_b(int var1, int var2) {
    }

    public int func_27256_c(int var1, int var2) {
        int var3 = this.width / 2 - 110;
        int var4 = this.width / 2 + 110;
        int var5 = var2 - this.top - this.field_27261_r + (int)this.amountScrolled - 4;
        int var6 = var5 / this.getSlotHeight(0);
        return var1 >= var3 && var1 <= var4 && var6 >= 0 && var5 >= 0 && var6 < this.getSize() ? var6 : -1;
    }

    public void registerScrollButtons(List<?> var1, int var2, int var3) {
        this.scrollUpButtonID = var2;
        this.scrollDownButtonID = var3;
    }

    private void bindAmountScrolled() {
        int var1 = this.getContentHeight() - (this.bottom - this.top - 4);
        if (var1 < 0) {
            var1 /= 2;
        }

        if (this.amountScrolled < 0.0F) {
            this.amountScrolled = 0.0F;
        }

        if (this.amountScrolled > (float)var1) {
            this.amountScrolled = (float)var1;
        }

    }

    public void actionPerformed(GuiButton var1) {
        if (var1.enabled) {
            if (var1.id == this.scrollUpButtonID) {
                this.amountScrolled -= (float)(this.getSlotHeight(0) * 2 / 3);
                this.initialClickY = -2.0F;
                this.bindAmountScrolled();
            } else if (var1.id == this.scrollDownButtonID) {
                this.amountScrolled += (float)(this.getSlotHeight(0) * 2 / 3);
                this.initialClickY = -2.0F;
                this.bindAmountScrolled();
            }
        }

    }

    public void drawScreen(int width, int height, float deltaTicks) {
        this.maxWidth = width;
        this.maxHeight = height;
        this.drawBackground();
        int maxSlots = this.getSize();
        int centerX = this.width / 2 + 124;
        int centerXWithOffset = centerX + 6;
        int x;
        int offsetY;
        int slotIter;
        int smthYAxis;
        int y;
        if (Mouse.isButtonDown(0)) {
            if (this.initialClickY == -1.0F) {
                boolean var7 = true;
                if (height >= this.top && height <= this.bottom) {
                    int var8 = this.width / 2 - 110;
                    x = this.width / 2 + 110;
                    offsetY = height - this.top - this.field_27261_r + (int)this.amountScrolled - 4;
                    slotIter = offsetY / this.slotHeight;
                    if (width >= var8 && width <= x && slotIter >= 0 && offsetY >= 0 && slotIter < maxSlots) {
                        boolean var12 = slotIter == this.selectedElement && System.currentTimeMillis() - this.lastClicked < 250L;
                        this.elementClicked(slotIter, var12);
                        this.selectedElement = slotIter;
                        this.lastClicked = System.currentTimeMillis();
                    } else if (width >= var8 && width <= x && offsetY < 0) {
                        this.func_27255_a(width - var8, height - this.top + (int)this.amountScrolled - 4);
                        var7 = false;
                    }

                    if (width >= centerX && width <= centerXWithOffset) {
                        this.scrollMultiplier = -1.0F;
                        y = this.getContentHeight() - (this.bottom - this.top - 4);
                        if (y < 1) {
                            y = 1;
                        }

                        smthYAxis = (int)((float)((this.bottom - this.top) * (this.bottom - this.top)) / (float)this.getContentHeight());
                        if (smthYAxis < 32) {
                            smthYAxis = 32;
                        }

                        if (smthYAxis > this.bottom - this.top - 8) {
                            smthYAxis = this.bottom - this.top - 8;
                        }

                        this.scrollMultiplier /= (float)(this.bottom - this.top - smthYAxis) / (float)y;
                    } else {
                        this.scrollMultiplier = 1.0F;
                    }

                    if (var7) {
                        this.initialClickY = (float)height;
                    } else {
                        this.initialClickY = -2.0F;
                    }
                } else {
                    this.initialClickY = -2.0F;
                }
            } else if (this.initialClickY >= 0.0F) {
                this.amountScrolled -= ((float)height - this.initialClickY) * this.scrollMultiplier;
                this.initialClickY = (float)height;
            }
        } else {
            this.initialClickY = -1.0F;
        }

        this.bindAmountScrolled();
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        Tessellator tessellator = Tessellator.instance;
        GL11.glBindTexture(3553, this.mc.renderEngine.getTex("/dirt.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float var17 = 32.0F;
        if (this.mc.mcWorld == null/* || !(this instanceof GuiTexturePackSlot)*/) {
            tessellator.startDrawingQuads();
            tessellator.setColorOpaque_I(2105376);
            tessellator.addVertexWithUV((double)this.left, (double)this.bottom, 0.0, (double)((float)this.left / var17), (double)((float)(this.bottom + (int)this.amountScrolled) / var17));
            tessellator.addVertexWithUV((double)this.right, (double)this.bottom, 0.0, (double)((float)this.right / var17), (double)((float)(this.bottom + (int)this.amountScrolled) / var17));
            tessellator.addVertexWithUV((double)this.right, (double)this.top, 0.0, (double)((float)this.right / var17), (double)((float)(this.top + (int)this.amountScrolled) / var17));
            tessellator.addVertexWithUV((double)this.left, (double)this.top, 0.0, (double)((float)this.left / var17), (double)((float)(this.top + (int)this.amountScrolled) / var17));
            tessellator.draw();
        }

        x = this.width / 2 - 92 - 16;
        offsetY = this.top + 4 - (int)this.amountScrolled;
        if (this.field_27262_q) {
            this.func_27260_a(x, offsetY, tessellator);
        }

        int culmSlot = 0;

        int var14;
        for(slotIter = 0; slotIter < maxSlots; ++slotIter) {
            if (slotIter != 0) {
                culmSlot += this.getSlotHeight(slotIter - 1);
            }

            y = offsetY + (slotIter != 0 ? culmSlot : slotIter * this.getSlotHeight(slotIter)) + this.field_27261_r;
            smthYAxis = this.getSlotHeight(slotIter) - 4;
            if (y <= this.bottom && y + smthYAxis >= this.top) {
                if (this.field_25123_p && this.isSelected(slotIter)) {
                    var14 = this.width / 2 - 110;
                    int var15 = this.width / 2 + 110;
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glDisable(3553);
                    tessellator.startDrawingQuads();
                    tessellator.setColorOpaque_I(8421504);
                    tessellator.addVertexWithUV((double)var14, (double)(y + smthYAxis + 2), 0.0, 0.0, 1.0);
                    tessellator.addVertexWithUV((double)var15, (double)(y + smthYAxis + 2), 0.0, 1.0, 1.0);
                    tessellator.addVertexWithUV((double)var15, (double)(y - 2), 0.0, 1.0, 0.0);
                    tessellator.addVertexWithUV((double)var14, (double)(y - 2), 0.0, 0.0, 0.0);
                    tessellator.setColorOpaque_I(0);
                    tessellator.addVertexWithUV((double)(var14 + 1), (double)(y + smthYAxis + 1), 0.0, 0.0, 1.0);
                    tessellator.addVertexWithUV((double)(var15 - 1), (double)(y + smthYAxis + 1), 0.0, 1.0, 1.0);
                    tessellator.addVertexWithUV((double)(var15 - 1), (double)(y - 1), 0.0, 1.0, 0.0);
                    tessellator.addVertexWithUV((double)(var14 + 1), (double)(y - 1), 0.0, 0.0, 0.0);
                    tessellator.draw();
                    GL11.glEnable(3553);
                }

                this.drawSlot(slotIter, x, y, smthYAxis, tessellator);
            }
        }

        GL11.glDisable(2929);
        byte var18 = 4;
        this.overlayBackground(0, this.top, 255, 255);
        this.overlayBackground(this.bottom, this.height, 255, 255);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(0, 0);
        tessellator.addVertexWithUV((double)this.left, (double)(this.top + var18), 0.0, 0.0, 1.0);
        tessellator.addVertexWithUV((double)this.right, (double)(this.top + var18), 0.0, 1.0, 1.0);
        tessellator.setColorRGBA_I(0, 255);
        tessellator.addVertexWithUV((double)this.right, (double)this.top, 0.0, 1.0, 0.0);
        tessellator.addVertexWithUV((double)this.left, (double)this.top, 0.0, 0.0, 0.0);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(0, 255);
        tessellator.addVertexWithUV((double)this.left, (double)this.bottom, 0.0, 0.0, 1.0);
        tessellator.addVertexWithUV((double)this.right, (double)this.bottom, 0.0, 1.0, 1.0);
        tessellator.setColorRGBA_I(0, 0);
        tessellator.addVertexWithUV((double)this.right, (double)(this.bottom - var18), 0.0, 1.0, 0.0);
        tessellator.addVertexWithUV((double)this.left, (double)(this.bottom - var18), 0.0, 0.0, 0.0);
        tessellator.draw();
        y = this.getContentHeight() - (this.bottom - this.top - 4);
        if (y > 0) {
            smthYAxis = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
            if (smthYAxis < 32) {
                smthYAxis = 32;
            }

            if (smthYAxis > this.bottom - this.top - 8) {
                smthYAxis = this.bottom - this.top - 8;
            }

            var14 = (int)this.amountScrolled * (this.bottom - this.top - smthYAxis) / y + this.top;
            if (var14 < this.top) {
                var14 = this.top;
            }

            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_I(0, 255);
            tessellator.addVertexWithUV((double)centerX, (double)this.bottom, 0.0, 0.0, 1.0);
            tessellator.addVertexWithUV((double)centerXWithOffset, (double)this.bottom, 0.0, 1.0, 1.0);
            tessellator.addVertexWithUV((double)centerXWithOffset, (double)this.top, 0.0, 1.0, 0.0);
            tessellator.addVertexWithUV((double)centerX, (double)this.top, 0.0, 0.0, 0.0);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_I(8421504, 255);
            tessellator.addVertexWithUV((double)centerX, (double)(var14 + smthYAxis), 0.0, 0.0, 1.0);
            tessellator.addVertexWithUV((double)centerXWithOffset, (double)(var14 + smthYAxis), 0.0, 1.0, 1.0);
            tessellator.addVertexWithUV((double)centerXWithOffset, (double)var14, 0.0, 1.0, 0.0);
            tessellator.addVertexWithUV((double)centerX, (double)var14, 0.0, 0.0, 0.0);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_I(12632256, 255);
            tessellator.addVertexWithUV((double)centerX, (double)(var14 + smthYAxis - 1), 0.0, 0.0, 1.0);
            tessellator.addVertexWithUV((double)(centerXWithOffset - 1), (double)(var14 + smthYAxis - 1), 0.0, 1.0, 1.0);
            tessellator.addVertexWithUV((double)(centerXWithOffset - 1), (double)var14, 0.0, 1.0, 0.0);
            tessellator.addVertexWithUV((double)centerX, (double)var14, 0.0, 0.0, 0.0);
            tessellator.draw();
        }

        this.func_27257_b(width, height);
        GL11.glEnable(3553);
        GL11.glShadeModel(7424);
        GL11.glEnable(3008);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    private void overlayBackground(int var1, int var2, int var3, int var4) {
        Tessellator var5 = Tessellator.instance;
        GL11.glBindTexture(3553, this.mc.renderEngine.getTex("/dirt.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float var6 = 32.0F;
        var5.startDrawingQuads();
        var5.setColorRGBA_I(4210752, var4);
        var5.addVertexWithUV(0.0, (double)var2, 0.0, 0.0, (double)((float)var2 / var6));
        var5.addVertexWithUV((double)this.width, (double)var2, 0.0, (double)((float)this.width / var6), (double)((float)var2 / var6));
        var5.setColorRGBA_I(4210752, var3);
        var5.addVertexWithUV((double)this.width, (double)var1, 0.0, (double)((float)this.width / var6), (double)((float)var1 / var6));
        var5.addVertexWithUV(0.0, (double)var1, 0.0, 0.0, (double)((float)var1 / var6));
        var5.draw();
    }

    protected int getSlotHeight(int slotIter) {
        return this.slotHeight;
    }
}
