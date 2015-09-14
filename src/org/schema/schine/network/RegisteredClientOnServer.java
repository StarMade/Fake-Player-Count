package org.schema.schine.network;

import java.io.PrintStream;
import java.net.InetAddress;
import java.util.ArrayList;
import org.schema.schine.network.commands.MessageTo;
import org.schema.schine.network.server.ServerMessage;
import org.schema.schine.network.server.ServerProcessor;
import org.schema.schine.network.server.ServerStateInterface;

public class RegisteredClientOnServer
  implements Identifiable, Recipient, RegisteredClientInterface
{
  protected NetworkStateContainer localAndRemoteContainer;
  protected SynchronizationContainerController synchController;
  protected ArrayList wispers = new ArrayList();
  public boolean wasFullSynched;
  protected int id;
  protected String playerName;
  protected ServerProcessor serverProcessor;
  protected boolean connected;
  protected short needsSynch = Short.MIN_VALUE;
  protected Object player;
  protected String starmadeName;
  protected boolean upgradedAccount;
  
  public RegisteredClientOnServer(int paramInt, String paramString)
  {
    this.id = paramInt;
    this.playerName = paramString;
    this.connected = true;
  }
  
  public RegisteredClientOnServer(int paramInt, String paramString, ServerStateInterface paramServerStateInterface)
  {
    this.id = paramInt;
    this.playerName = paramString;
    this.connected = true;
    
    this.localAndRemoteContainer = new NetworkStateContainer(true, paramServerStateInterface);
    this.synchController = new SynchronizationContainerController(this.localAndRemoteContainer, paramServerStateInterface, true);
  }
  
  public boolean checkConnection()
  {
    if (!this.connected) {
      return false;
    }
    if (!getProcessor().isConnectionAlive()) {
      return false;
    }
    return true;
  }
  
  public void executedAdminCommand() {}
  
  public int getId()
  {
    return this.id;
  }
  
  public String getPlayerName()
  {
    return this.playerName;
  }
  
  public void serverMessage(String paramString)
  {
    System.err.println("[SEND][SERVERMESSAGE] " + paramString + " to " + this);
    sendCommand(getId(), IdGen.getNewPacketId(), MessageTo.class, new Object[] { "SERVER", paramString, Integer.valueOf(0) });
  }
  
  public void setPlayerName(String paramString)
  {
    this.playerName = paramString;
  }
  
  public void setId(int paramInt)
  {
    this.id = paramInt;
  }
  
  public void flagSynch(short paramShort)
  {
    this.needsSynch = paramShort;
  }
  
  public String getIp()
  {
    try
    {
      return getProcessor().getClientIp().toString().replace("/", "");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();;
    }
    return "0.0.0.0";
  }
  
  public NetworkStateContainer getLocalAndRemoteObjectContainer()
  {
    return this.localAndRemoteContainer;
  }
  
  public Object getPlayerObject()
  {
    return this.player;
  }
  
  public void setPlayerObject(Object paramObject)
  {
    this.player = paramObject;
  }
  
  public ServerProcessor getProcessor()
  {
    return this.serverProcessor;
  }
  
  public void setProcessor(ServerProcessor paramServerProcessor)
  {
    this.serverProcessor = paramServerProcessor;
  }
  
  public SynchronizationContainerController getSynchController()
  {
    return this.synchController;
  }
  
  public short getSynchPacketId()
  {
    return this.needsSynch;
  }
  
  public ArrayList getWispers()
  {
    return this.wispers;
  }
  
  public boolean isConnected()
  {
    return this.connected;
  }
  
  public void setConnected(boolean paramBoolean)
  {
    this.connected = false;
  }
  
  public boolean needsSynch()
  {
    return this.needsSynch > Short.MIN_VALUE;
  }
  
  public void sendCommand(int paramInt, short paramShort, Class paramClass, Object... paramVarArgs)
  {
    NetUtil.commands.getByClass(paramClass).writeAndCommitParametriziedCommand(paramVarArgs, getId(), paramInt, paramShort, this.serverProcessor);
  }
  
  public Object[] sendReturnedCommand(int paramInt, short paramShort, Class paramClass, Object... paramVarArgs)
  {
    throw new IllegalArgumentException("this moethod is only used: client to server for client requests");
  }
  
  public void method7017serverMessage(ServerMessage paramServerMessage)
  {
    System.err.println("[SEND][SERVERMESSAGE] " + paramServerMessage + " to " + this);
    sendCommand(getId(), IdGen.getNewPacketId(), MessageTo.class, new Object[] { "SERVER", paramServerMessage.message, Integer.valueOf(paramServerMessage.type) });
  }
  
  public String toString()
  {
    return "RegisteredClient: " + getPlayerName() + " (" + this.id + ") " + (this.starmadeName != null ? "[" + this.starmadeName + "]" : "") + "connected: " + this.connected;
  }
  
  public String getStarmadeName()
  {
    return this.starmadeName;
  }
  
  public void setStarmadeName(String paramString)
  {
    this.starmadeName = paramString;
  }
  
  public boolean isUpgradedAccount()
  {
    return this.upgradedAccount;
  }
  
  public void setUpgradedAccount(boolean paramBoolean)
  {
    this.upgradedAccount = paramBoolean;
  }
}
