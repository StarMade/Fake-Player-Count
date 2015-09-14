package deobf;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.schema.game.common.Starter;
import org.schema.game.server.controller.GameServerController;
import org.schema.schine.network.exception.ServerPortNotAvailableException;
import org.schema.schine.network.server.ServerController;

import com.error22.fp.FakePlayers;

public final class Class840Mr implements Runnable {
	private boolean a;

	public Class840Mr(final boolean a) {
		this.a = a;
	}

	@Override
	public final void run() {
		System.err.println("[SERVER] initializing (CUST)");
		Class2555arW.method3825a(new Class3176gM());
		Class1078alN class1078alN = new Class1078alN();
		final GameServerController gameServerController = new GameServerController(class1078alN);
		Starter.sGUI = null;
		boolean b = false;
		if (this.a) {
			SwingUtilities.invokeLater(new Class841Ms(gameServerController));
		}
		Starter.serverUp = false;
		try {
			gameServerController.startServerAndListen();
			while (!gameServerController.isListenting()) {
				try {
					Thread.sleep(30L);
				} catch (InterruptedException ex3) {
					ex3.printStackTrace();
				}
			}
			Starter.serverUp = true;
			if (Starter.dedicatedServer && (boolean) Class2266alZ.bv.method3486a()) {
				Class2266alZ.bw.method3486a();
			}
			final File file;
			if ((file = new File("./debugPlayerLock.lock")).exists()) {
				file.delete();
			}
			file.createNewFile();
			final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
			synchronized (class1078alN.getClients()) {
				dataOutputStream.writeInt(class1078alN.getClients().size());
			}
			dataOutputStream.close();
			Starter.serverInitFinished = true;
			synchronized (Starter.serverLock) {
				Starter.serverLock.notify();
			}
		} catch (Exception ex4) {
			if (Starter.sGUI != null) {
				Starter.sGUI.setVisible(false);
			}
			if (ex4 instanceof ServerPortNotAvailableException) {
				final ServerPortNotAvailableException ex5;
				if ((ex5 = (ServerPortNotAvailableException) ex4).isInstanceRunning()) {
					System.out.println("NOT STARTING SERVER. A StarMade Server Instance is already running on port "
							+ ServerController.port + " DEDICATED: " + Starter.dedicatedServer);
					if (Starter.dedicatedServer) {
						System.err.println("HANDLING EXCEPTION NOW");
						handleServerAlreadyRunningError(
								"A StarMade Server Instance is already running on port " + ServerController.port);
					}
				} else {
					ex5.printStackTrace();
					System.out.println("[ERROR] Some other program is blocking port " + ServerController.port
							+ ". Please end that program or choose another port for starmade");
					Starter.handlePortError("");
					b = true;
				}
			} else {
				ex4.printStackTrace();
			}
//			if (b) {
//				continue;
//			}
			System.err.println("[SERVER] I think this is a rerun run request?");
			Starter.serverInitFinished = true;
			synchronized (Starter.serverLock) {
				Starter.serverLock.notify();
			}
		} finally {
			if (!b) {
				Starter.serverInitFinished = true;
				synchronized (Starter.serverLock) {
					Starter.serverLock.notify();
//					continue;
				}
			}
			System.err.println("[SERVER] We want to rerun run for some reason?)");
			FakePlayers.getInstance().init(class1078alN);
//			this.run();
		}
	}

	private static void handleServerAlreadyRunningError(String paramString) {
		try {
			JFrame localJFrame;
			(localJFrame = new JFrame()).setDefaultCloseOperation(3);
			String[] arrayOfString = { "EXIT" };
			switch (JOptionPane.showOptionDialog(localJFrame, paramString, "StarMade Server Already Running", 1, 0,
					null, arrayOfString, arrayOfString[0])) {
			case 0:
				System.err.println("Exiting because of server already running");
				System.exit(0);
			}
		} catch (Exception localException) {
		}
		System.err.println(paramString);
	}
}
