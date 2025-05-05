package com.fox2code.foxloader.client.mixins;

import com.fox2code.foxloader.client.helpers.EntityPlayerSPHelper;
import com.fox2code.foxloader.loader.ClientMod;
import com.fox2code.foxloader.loader.ModContainer;
import com.fox2code.foxloader.network.NetworkConnection;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.RegisteredItemStack;
import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.entity.EntityPlayer;
import com.mojang.minecraft.entity.EntityPlayerSP;
import com.mojang.minecraft.level.World;
import net.minecraft.src.client.gui.StringTranslate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends EntityPlayer implements NetworkPlayer , EntityPlayerSPHelper {

    @Shadow public Minecraft mc;

    public MixinEntityPlayerSP(World var1) {
        super(var1);
    }

    @Override
    public NetworkConnection getNetworkConnection() {
        return null;
    }

    @Override
    public ConnectionType getConnectionType() {
        return ConnectionType.SINGLE_PLAYER;
    }

    @Override
    public void sendNetworkData(ModContainer modContainer, byte[] data) {}

    @Override
    public void displayChatMessage(String chatMessage) {
        StringTranslate st = StringTranslate.getInstance();
        if (chatMessage.indexOf('\n') == -1) {
            Minecraft.getMinecraft().ingameGUI.addChatMessage(st.translateKey(chatMessage));
        } else {
            String[] splits = chatMessage.split("\\n");
            for (String split : splits) {
                Minecraft.getMinecraft().ingameGUI.addChatMessage(st.translateKey(split));
            }
        }
    }

    @Override
    public boolean hasFoxLoader() {
        return true;
    }

    @Override
    public String getPlayerName() {
        return ((EntityPlayer) (Object) this).playerName;
    }

    @Override
    public boolean isOperator() {
        return Minecraft.getMinecraft().mcWorld.cheatsDisabled;
    }

    @Override
    public void kick(String message) {
        throw new IllegalStateException("kick cannot be used client-side");
    }

    @Override
    public NetworkPlayerController getNetworkPlayerController() {
        return (NetworkPlayerController) Minecraft.getMinecraft().playerController;
    }

    @Override
    public boolean isConnected() {
        final Minecraft mc = Minecraft.getMinecraft();
        return mc.mcWorld != null && !mc.mcWorld.multiplayerWorld;
    }

    @Override
    public RegisteredItemStack getRegisteredHeldItem() {
        EntityPlayerSP networkPlayerSP = (EntityPlayerSP) (Object) this;
        return ClientMod.toRegisteredItemStack(networkPlayerSP.inventory.getCurrentItem());
    }
/*

    @Override
    public void sendPlayerThroughPortalRegistered() {
        this.mc.usePortal();
        this.inPortal = false;
    }
    public String goingtodim=null;
    public float timeInPortalcustom = 0.f;
    public boolean incustomportal=false;

    public int timeUntilPortalcustom=0;
    @Override
    public void setInPortalcustom(String name) {

        if (this.timeUntilPortalcustom > 0) {
            this.timeUntilPortalcustom = 10;
        } else {
            this.incustomportal = true;
            this.goingtodim=name;
        }
    }
*/


/*    @Override
    public void preparePlayerToSpawn() {
        super.preparePlayerToSpawn();

        try {
            customDimension= (String) Minecraft.getInstance().getClass().getField("Dim").get(Minecraft.getInstance());
            customrespawnDimension= (String) Minecraft.getInstance().getClass().getField("DimR").get(Minecraft.getInstance());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }*/

/*    @Unique
    public String customDimension="notcustom";
    @Unique
    public String customrespawnDimension="notcustom";
    @Inject(method = "writeEntityToNBT",at = @At("TAIL"))
    public void writeEntityToNBT(NBTTagCompound var1, CallbackInfo ci) {
        var1.setString("customDimension", this.customDimension);
        var1.setString("customrespawnDimension", this.customrespawnDimension);


    }

    @Inject(method = "readEntityToNBT",at = @At("TAIL"))
    public void readEntityFromNBT(NBTTagCompound var1, CallbackInfo ci) {
        this.customDimension = var1.getString("customDimension");
        this.customrespawnDimension = var1.getString("customrespawnDimension");
    }
    @Override
    public EnumStatus sleepInBedAt(int x, int y, int z) {
        this.customrespawnDimension=customDimension;
        try {
            Minecraft.getInstance().getClass().getField("DimR").set(Minecraft.getInstance(),customrespawnDimension);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return super.sleepInBedAt(x, y, z);
    }*/
}
