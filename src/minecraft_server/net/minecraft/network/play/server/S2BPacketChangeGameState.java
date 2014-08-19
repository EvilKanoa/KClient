package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S2BPacketChangeGameState extends Packet
{
    public static final String[] field_149142_a = new String[] {"tile.bed.notValid", null, null, "gameMode.changed"};
    private int field_149140_b;
    private float field_149141_c;
    private static final String __OBFID = "CL_00001301";

    public S2BPacketChangeGameState() {}

    public S2BPacketChangeGameState(int p_i45194_1_, float p_i45194_2_)
    {
        this.field_149140_b = p_i45194_1_;
        this.field_149141_c = p_i45194_2_;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException
    {
        this.field_149140_b = p_148837_1_.readUnsignedByte();
        this.field_149141_c = p_148837_1_.readFloat();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException
    {
        p_148840_1_.writeByte(this.field_149140_b);
        p_148840_1_.writeFloat(this.field_149141_c);
    }

    public void func_148833_a(INetHandlerPlayClient p_149139_1_)
    {
        p_149139_1_.handleChangeGameState(this);
    }

    public void func_148833_a(INetHandler p_148833_1_)
    {
        this.func_148833_a((INetHandlerPlayClient)p_148833_1_);
    }
}
