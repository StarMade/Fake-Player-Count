package com.error22.fp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.schema.schine.network.DataInputStreamPositional;
import org.schema.schine.network.DataOutputStreamPositional;
import org.schema.schine.network.NetworkStateContainer;
import org.schema.schine.network.RegisteredClientOnServer;
import org.schema.schine.network.StateInterface;
import org.schema.schine.network.SynchronizationContainerController;
import org.schema.schine.network.objects.Sendable;
import org.schema.schine.network.server.ServerMessage;
import org.schema.schine.network.server.ServerProcessor;
import org.schema.schine.network.server.ServerStateInterface;

import deobf.Class793aAF;
import it.unimi.dsi.fastutil.io.FastByteArrayInputStream;
import it.unimi.dsi.fastutil.io.FastByteArrayOutputStream;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class FakePlayer extends RegisteredClientOnServer {
	private static Random random = new Random();
	private int ping;
	private long lastPing;

	public FakePlayer(int id, String name, StateInterface si) {
		super(id, name);

		localAndRemoteContainer = new NetworkStateContainer(true, null) {
			@Override
			public void putLocal(int paramInt, Sendable paramSendable) {
				assert(paramSendable != null);
				this.getLocalObjects().put(paramInt, paramSendable);
				if (paramSendable.isUpdatable()) {
					getLocalUpdatableObjects().put(paramInt, paramSendable);
				}
				if (((paramSendable instanceof Class793aAF))
						&& (((Class793aAF) paramSendable).getUniqueIdentifier() != null)) {
					getUidObjectMap().put(((Class793aAF) paramSendable).getUniqueIdentifier(), paramSendable);
				}
			}

			@Override
			public Sendable removeLocal(int paramInt) {
				Sendable localSendable = (Sendable) this.getLocalObjects().remove(paramInt);
				if (localSendable.isUpdatable()) {
					getLocalUpdatableObjects().remove(paramInt);
				}
				if (((localSendable instanceof Class793aAF))
						&& (((Class793aAF) localSendable).getUniqueIdentifier() != null)) {
					getUidObjectMap().remove(((Class793aAF) localSendable).getUniqueIdentifier());
				}
				return localSendable;
			}
		};

		this.synchController = new SynchronizationContainerController(this.localAndRemoteContainer, si, true) {
			@Override
			public void addImmediateSynchronizedObject(Sendable arg0) {
			}
		};
		
		
		setProcessor(new ServerProcessor(null, (ServerStateInterface) si){
			private DataInputStreamPositional fakeInDoubleBuffer;
			
			@Override
			public void closeSocket() {
				System.out.println("FakeServerProcessor closeSocket");
			}
			
			@Override
			public void flushDoubleOutBuffer() {
			}
			
			@Override
			public DataInputStreamPositional getIn() {
				return fakeInDoubleBuffer;
			}
			
			@Override
			public InputStream getInRaw() {
				return getIn();
			}
			
			@Override
			public OutputStream getOutRaw() {
				return getOut();
			}
			
			@Override
			public boolean isAlive() {
				return true;
			}
			
			@Override
			public void resetDoubleOutBuffer() {
			}
			
			@Override
			public void disconnect() {
				System.out.println("FakeServerProcessor disconnect");
			}
			
			@Override
			public void disconnectAfterSent() {
				System.out.println("FakeServerProcessor disconnectAfterSent");
			}
			
			@Override
			public void disconnectDelayed() {
				System.out.println("FakeServerProcessor disconnectDelayed");
			}
			
			@Override
			public boolean needsFlush() {
				return false;
			}
			
			@Override
			public InetAddress getClientIp() {
				try {
					return InetAddress.getByName("localhost");
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			public String getIp() {
//				return "n/a";
				return "0.0.0.0:-1";
			}
			
			@Override
			public long getPingTime() {
				long cur = System.currentTimeMillis();
				if(cur - lastPing > 10000){
					lastPing = cur - 500 + random.nextInt(1000);
					ping = 40 + (random.nextInt(100));
				}
				
				return ping;
			}
			
			@Override
			public int getPort() {
				return -1;
			}
			
			@Override
			public boolean isConnectionAlive() {
				System.out.println("FakeServerProcessor isConnectionAlive");
				return true;
			}
			
			@Override
			public boolean isInfoPinger() {
				return false;
			}
			
			@Override
			public void run() {
				System.out.println("FakeServerProcessor run");
				fakeInDoubleBuffer = new DataInputStreamPositional(new FastByteArrayInputStream(new byte[0]));
			}
			
			@Override
			public boolean isStopTransmit() {
				System.out.println("FakeServerProcessor isStopTransmit");
				return false;
			}
			
			@Override
			public void serverCommand(byte arg0, int arg1, Object... arg2) {
				System.out.println("FakeServerProcessor serverCommand");
			}
			
			@Override
			public RegisteredClientOnServer getClient() {
				return FakePlayer.this;
			}
		});
	}

	@Override
	public boolean checkConnection() {
		return true;
	}

	@Override
	public void serverMessage(String paramString) {
	}

	@Override
	public void setPlayerName(String paramString) {
		System.out.println("Player name overwrite denied!");
	}

	@Override
	public void setId(int paramInt) {
		System.out.println("Player id overwrite denied!");
	}

	@Override
	public void flagSynch(short paramShort) {
	}

	@Override
	public String getIp() {
		return "0.0.0.0";
	}

	@Override
	public void sendCommand(int paramInt, short paramShort, Class paramClass, Object... paramVarArgs) {
	}

	@Override
	public void method7017serverMessage(ServerMessage paramServerMessage) {
	}

}
