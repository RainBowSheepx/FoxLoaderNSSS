package net.minecraft.src.client.packets;


import com.mojang.minecraft.networknew.NetHandler;
import com.mojang.minecraft.networknew.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet250PluginMessage extends Packet {
    public String channel;
    public byte[] data;

    public Packet250PluginMessage() {
    }

    public Packet250PluginMessage(String channel, byte[] data) {
        if (data.length > 32767) {
            throw new IllegalArgumentException("data is too large, size is " + data.length + " when max is " + 32767);
        } else {
            this.channel = channel;
            this.data = data;
        }
    }
    @Override
    public void readPacketData(DataInputStream dataInputStream) throws IOException {
        this.channel = readString(dataInputStream, 32);
        int len = dataInputStream.readShort();
        if (len < 0 || dataInputStream.read(this.data = new byte[len]) != len) {
            this.data = null;
        }
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
        writeString(this.channel, dataOutputStream);
        dataOutputStream.writeShort(this.data.length);
        dataOutputStream.write(this.data);
    }

    @Override
    public void processPacket(NetHandler netHandler) {

       // (netHandler).onHandlePluginMessage(this);
    }

    @Override
    public int getPacketSize() {
        return this.channel.length() + this.data.length + 4;
    }
}
