package org.schema.schine.network.commands;

import org.schema.schine.network.client.*;
import org.schema.schine.network.server.*;

import com.error22.fp.FakePlayers;

import org.schema.schine.network.*;

public class GetInfo extends Command
{
    private static final byte INFO_VERSION = 2;
    private long started;
    
    public GetInfo() {
        this.mode = 1;
    }
    
    @Override
    public void clientAnswerProcess(final Object[] array, final ClientStateInterface clientStateInterface, final short n) {
        final float floatValue = (float)array[1];
        final String s = (String)array[2];
        final String s2 = (String)array[3];
        final long longValue = (long)array[4];
        final int intValue = (int)array[5];
        final int intValue2 = (int)array[6];
        System.currentTimeMillis();
        final long started = this.started;
        System.out.println("[CLIENT][INFO]: CLIENT INFO ");
        System.out.println("[CLIENT][INFO]: Version: " + floatValue);
        System.out.println("[CLIENT][INFO]: Name: " + s);
        System.out.println("[CLIENT][INFO]: Description: " + s2);
        System.out.println("[CLIENT][INFO]: Started: " + longValue);
        System.out.println("[CLIENT][INFO]: Players: " + intValue + "/" + intValue2);
    }
    
    @Override
    public void serverProcess(final ServerProcessor serverProcessor, final Object[] array, final ServerStateInterface serverStateInterface, final short n) {
        final float version = serverStateInterface.getVersion();
        final String serverName = serverStateInterface.getServerName();
        final String serverDesc = serverStateInterface.getServerDesc();
        final long startTime = serverStateInterface.getStartTime();
        final int size = serverStateInterface.getClients().size() + FakePlayers.getInstance().getUnnamedPlayers();
        final int maxClients = serverStateInterface.getMaxClients() + FakePlayers.getInstance().getUnnamedPlayers();
        System.err.println("[SERVER] This client is an info ping (server-lists): " + serverProcessor.getClientIp() + "; PID: " + serverProcessor.id);
        serverProcessor.setInfoPinger(true);
        this.createReturnToClient(serverStateInterface, serverProcessor, n, (byte)2, version, serverName, serverDesc, startTime, size, maxClients);
        serverProcessor.disconnectAfterSent();
    }
    
    @Override
    public void writeAndCommitParametriziedCommand(final Object[] array, final int n, final int n2, final short n3, final NetworkProcessor networkProcessor) {
        this.started = System.currentTimeMillis();
        super.writeAndCommitParametriziedCommand(array, n, n2, n3, networkProcessor);
    }
}
