package net.minecraft.network.rcon;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.server.MinecraftServer;

public class RConThreadQuery extends RConThreadBase
{
    /** The time of the last client auth check */
    private long lastAuthCheckTime;

    /** The RCon query port */
    private int queryPort;

    /** Port the server is running on */
    private int serverPort;

    /** The maximum number of players allowed on the server */
    private int maxPlayers;

    /** The current server message of the day */
    private String serverMotd;

    /** The name of the currently loaded world */
    private String worldName;

    /** The remote socket querying the server */
    private DatagramSocket querySocket;

    /** A buffer for incoming DatagramPackets */
    private byte[] buffer = new byte[1460];

    /** Storage for incoming DatagramPackets */
    private DatagramPacket incomingPacket;
    private Map field_72644_p;

    /** The hostname of this query server */
    private String queryHostname;

    /** The hostname of the running server */
    private String serverHostname;

    /** A map of SocketAddress objects to RConThreadQueryAuth objects */
    private Map queryClients;

    /**
     * The time that this RConThreadQuery was constructed, from (new Date()).getTime()
     */
    private long time;

    /** The RConQuery output stream */
    private RConOutputStream output;

    /** The time of the last query response sent */
    private long lastQueryResponseTime;
    private static final String __OBFID = "CL_00001802";

    public RConThreadQuery(IServer par1IServer)
    {
        super(par1IServer, "Query Listener");
        this.queryPort = par1IServer.getIntProperty("query.port", 0);
        this.serverHostname = par1IServer.getHostname();
        this.serverPort = par1IServer.getPort();
        this.serverMotd = par1IServer.getMotd();
        this.maxPlayers = par1IServer.getMaxPlayers();
        this.worldName = par1IServer.getFolderName();
        this.lastQueryResponseTime = 0L;
        this.queryHostname = "0.0.0.0";

        if (0 != this.serverHostname.length() && !this.queryHostname.equals(this.serverHostname))
        {
            this.queryHostname = this.serverHostname;
        }
        else
        {
            this.serverHostname = "0.0.0.0";

            try
            {
                InetAddress var2 = InetAddress.getLocalHost();
                this.queryHostname = var2.getHostAddress();
            }
            catch (UnknownHostException var3)
            {
                this.logWarning("Unable to determine local host IP, please set server-ip in \'" + par1IServer.getSettingsFilename() + "\' : " + var3.getMessage());
            }
        }

        if (0 == this.queryPort)
        {
            this.queryPort = this.serverPort;
            this.logInfo("Setting default query port to " + this.queryPort);
            par1IServer.setProperty("query.port", Integer.valueOf(this.queryPort));
            par1IServer.setProperty("debug", Boolean.valueOf(false));
            par1IServer.saveProperties();
        }

        this.field_72644_p = new HashMap();
        this.output = new RConOutputStream(1460);
        this.queryClients = new HashMap();
        this.time = (new Date()).getTime();
    }

    /**
     * Sends a byte array as a DatagramPacket response to the client who sent the given DatagramPacket
     */
    private void sendResponsePacket(byte[] par1ArrayOfByte, DatagramPacket par2DatagramPacket) throws IOException
    {
        this.querySocket.send(new DatagramPacket(par1ArrayOfByte, par1ArrayOfByte.length, par2DatagramPacket.getSocketAddress()));
    }

    /**
     * Parses an incoming DatagramPacket, returning true if the packet was valid
     */
    private boolean parseIncomingPacket(DatagramPacket par1DatagramPacket) throws IOException
    {
        byte[] var2 = par1DatagramPacket.getData();
        int var3 = par1DatagramPacket.getLength();
        SocketAddress var4 = par1DatagramPacket.getSocketAddress();
        this.logDebug("Packet len " + var3 + " [" + var4 + "]");

        if (3 <= var3 && -2 == var2[0] && -3 == var2[1])
        {
            this.logDebug("Packet \'" + RConUtils.getByteAsHexString(var2[2]) + "\' [" + var4 + "]");

            switch (var2[2])
            {
                case 0:
                    if (!this.verifyClientAuth(par1DatagramPacket).booleanValue())
                    {
                        this.logDebug("Invalid challenge [" + var4 + "]");
                        return false;
                    }
                    else if (15 == var3)
                    {
                        this.sendResponsePacket(this.createQueryResponse(par1DatagramPacket), par1DatagramPacket);
                        this.logDebug("Rules [" + var4 + "]");
                    }
                    else
                    {
                        RConOutputStream var5 = new RConOutputStream(1460);
                        var5.writeInt(0);
                        var5.writeByteArray(this.getRequestID(par1DatagramPacket.getSocketAddress()));
                        var5.writeString(this.serverMotd);
                        var5.writeString("SMP");
                        var5.writeString(this.worldName);
                        var5.writeString(Integer.toString(this.getNumberOfPlayers()));
                        var5.writeString(Integer.toString(this.maxPlayers));
                        var5.writeShort((short)this.serverPort);
                        var5.writeString(this.queryHostname);
                        this.sendResponsePacket(var5.toByteArray(), par1DatagramPacket);
                        this.logDebug("Status [" + var4 + "]");
                    }

                case 9:
                    this.sendAuthChallenge(par1DatagramPacket);
                    this.logDebug("Challenge [" + var4 + "]");
                    return true;

                default:
                    return true;
            }
        }
        else
        {
            this.logDebug("Invalid packet [" + var4 + "]");
            return false;
        }
    }

    /**
     * Creates a query response as a byte array for the specified query DatagramPacket
     */
    private byte[] createQueryResponse(DatagramPacket par1DatagramPacket) throws IOException
    {
        long var2 = MinecraftServer.getCurrentTimeMillis();

        if (var2 < this.lastQueryResponseTime + 5000L)
        {
            byte[] var9 = this.output.toByteArray();
            byte[] var10 = this.getRequestID(par1DatagramPacket.getSocketAddress());
            var9[1] = var10[0];
            var9[2] = var10[1];
            var9[3] = var10[2];
            var9[4] = var10[3];
            return var9;
        }
        else
        {
            this.lastQueryResponseTime = var2;
            this.output.reset();
            this.output.writeInt(0);
            this.output.writeByteArray(this.getRequestID(par1DatagramPacket.getSocketAddress()));
            this.output.writeString("splitnum");
            this.output.writeInt(128);
            this.output.writeInt(0);
            this.output.writeString("hostname");
            this.output.writeString(this.serverMotd);
            this.output.writeString("gametype");
            this.output.writeString("SMP");
            this.output.writeString("game_id");
            this.output.writeString("MINECRAFT");
            this.output.writeString("version");
            this.output.writeString(this.server.getMinecraftVersion());
            this.output.writeString("plugins");
            this.output.writeString(this.server.getPlugins());
            this.output.writeString("map");
            this.output.writeString(this.worldName);
            this.output.writeString("numplayers");
            this.output.writeString("" + this.getNumberOfPlayers());
            this.output.writeString("maxplayers");
            this.output.writeString("" + this.maxPlayers);
            this.output.writeString("hostport");
            this.output.writeString("" + this.serverPort);
            this.output.writeString("hostip");
            this.output.writeString(this.queryHostname);
            this.output.writeInt(0);
            this.output.writeInt(1);
            this.output.writeString("player_");
            this.output.writeInt(0);
            String[] var4 = this.server.getAllUsernames();
            String[] var5 = var4;
            int var6 = var4.length;

            for (int var7 = 0; var7 < var6; ++var7)
            {
                String var8 = var5[var7];
                this.output.writeString(var8);
            }

            this.output.writeInt(0);
            return this.output.toByteArray();
        }
    }

    /**
     * Returns the request ID provided by the authorized client
     */
    private byte[] getRequestID(SocketAddress par1SocketAddress)
    {
        return ((RConThreadQuery.Auth)this.queryClients.get(par1SocketAddress)).getRequestId();
    }

    /**
     * Returns true if the client has a valid auth, otherwise false
     */
    private Boolean verifyClientAuth(DatagramPacket par1DatagramPacket)
    {
        SocketAddress var2 = par1DatagramPacket.getSocketAddress();

        if (!this.queryClients.containsKey(var2))
        {
            return Boolean.valueOf(false);
        }
        else
        {
            byte[] var3 = par1DatagramPacket.getData();
            return ((RConThreadQuery.Auth)this.queryClients.get(var2)).getRandomChallenge() != RConUtils.getBytesAsBEint(var3, 7, par1DatagramPacket.getLength()) ? Boolean.valueOf(false) : Boolean.valueOf(true);
        }
    }

    /**
     * Sends an auth challenge DatagramPacket to the client and adds the client to the queryClients map
     */
    private void sendAuthChallenge(DatagramPacket par1DatagramPacket) throws IOException
    {
        RConThreadQuery.Auth var2 = new RConThreadQuery.Auth(par1DatagramPacket);
        this.queryClients.put(par1DatagramPacket.getSocketAddress(), var2);
        this.sendResponsePacket(var2.getChallengeValue(), par1DatagramPacket);
    }

    /**
     * Removes all clients whose auth is no longer valid
     */
    private void cleanQueryClientsMap()
    {
        if (this.running)
        {
            long var1 = MinecraftServer.getCurrentTimeMillis();

            if (var1 >= this.lastAuthCheckTime + 30000L)
            {
                this.lastAuthCheckTime = var1;
                Iterator var3 = this.queryClients.entrySet().iterator();

                while (var3.hasNext())
                {
                    Entry var4 = (Entry)var3.next();

                    if (((RConThreadQuery.Auth)var4.getValue()).hasExpired(var1).booleanValue())
                    {
                        var3.remove();
                    }
                }
            }
        }
    }

    public void run()
    {
        this.logInfo("Query running on " + this.serverHostname + ":" + this.queryPort);
        this.lastAuthCheckTime = MinecraftServer.getCurrentTimeMillis();
        this.incomingPacket = new DatagramPacket(this.buffer, this.buffer.length);

        try
        {
            while (this.running)
            {
                try
                {
                    this.querySocket.receive(this.incomingPacket);
                    this.cleanQueryClientsMap();
                    this.parseIncomingPacket(this.incomingPacket);
                }
                catch (SocketTimeoutException var7)
                {
                    this.cleanQueryClientsMap();
                }
                catch (PortUnreachableException var8)
                {
                    ;
                }
                catch (IOException var9)
                {
                    this.stopWithException(var9);
                }
            }
        }
        finally
        {
            this.closeAllSockets();
        }
    }

    /**
     * Creates a new Thread object from this class and starts running
     */
    public void startThread()
    {
        if (!this.running)
        {
            if (0 < this.queryPort && 65535 >= this.queryPort)
            {
                if (this.initQuerySystem())
                {
                    super.startThread();
                }
            }
            else
            {
                this.logWarning("Invalid query port " + this.queryPort + " found in \'" + this.server.getSettingsFilename() + "\' (queries disabled)");
            }
        }
    }

    /**
     * Stops the query server and reports the given Exception
     */
    private void stopWithException(Exception par1Exception)
    {
        if (this.running)
        {
            this.logWarning("Unexpected exception, buggy JRE? (" + par1Exception.toString() + ")");

            if (!this.initQuerySystem())
            {
                this.logSevere("Failed to recover from buggy JRE, shutting down!");
                this.running = false;
            }
        }
    }

    /**
     * Initializes the query system by binding it to a port
     */
    private boolean initQuerySystem()
    {
        try
        {
            this.querySocket = new DatagramSocket(this.queryPort, InetAddress.getByName(this.serverHostname));
            this.registerSocket(this.querySocket);
            this.querySocket.setSoTimeout(500);
            return true;
        }
        catch (SocketException var2)
        {
            this.logWarning("Unable to initialise query system on " + this.serverHostname + ":" + this.queryPort + " (Socket): " + var2.getMessage());
        }
        catch (UnknownHostException var3)
        {
            this.logWarning("Unable to initialise query system on " + this.serverHostname + ":" + this.queryPort + " (Unknown Host): " + var3.getMessage());
        }
        catch (Exception var4)
        {
            this.logWarning("Unable to initialise query system on " + this.serverHostname + ":" + this.queryPort + " (E): " + var4.getMessage());
        }

        return false;
    }

    class Auth
    {
        private long timestamp = (new Date()).getTime();
        private int randomChallenge;
        private byte[] requestId;
        private byte[] challengeValue;
        private String requestIdAsString;
        private static final String __OBFID = "CL_00001803";

        public Auth(DatagramPacket par2DatagramPacket)
        {
            byte[] var3 = par2DatagramPacket.getData();
            this.requestId = new byte[4];
            this.requestId[0] = var3[3];
            this.requestId[1] = var3[4];
            this.requestId[2] = var3[5];
            this.requestId[3] = var3[6];
            this.requestIdAsString = new String(this.requestId);
            this.randomChallenge = (new Random()).nextInt(16777216);
            this.challengeValue = String.format("\t%s%d\u0000", new Object[] {this.requestIdAsString, Integer.valueOf(this.randomChallenge)}).getBytes();
        }

        public Boolean hasExpired(long par1)
        {
            return Boolean.valueOf(this.timestamp < par1);
        }

        public int getRandomChallenge()
        {
            return this.randomChallenge;
        }

        public byte[] getChallengeValue()
        {
            return this.challengeValue;
        }

        public byte[] getRequestId()
        {
            return this.requestId;
        }
    }
}
