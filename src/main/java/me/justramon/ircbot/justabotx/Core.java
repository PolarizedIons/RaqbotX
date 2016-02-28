package me.justramon.ircbot.justabotx;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.ActionEvent;

import me.justramon.ircbot.justabotx.cmd.ChangeState;
import me.justramon.ircbot.justabotx.cmd.CommandSwitch;
import me.justramon.ircbot.justabotx.cmd.QuitAndUpdate;
import me.justramon.ircbot.justabotx.features.JRWUpdates;
import me.justramon.ircbot.justabotx.features.MojangUpdates;
import me.justramon.ircbot.justabotx.features.XtraFunc;
import me.justramon.ircbot.justabotx.util.Channels;
import me.justramon.ircbot.justabotx.util.NotImportant.Passwords;

public class Core extends ListenerAdapter<PircBotX>
{
	public static PircBotX bot;
	public static boolean enabled = true;
	public static boolean wip = false;
	public static String version = "1.8.3.2";
	
	public void onMessage(MessageEvent<PircBotX> event) throws Exception
	{
		String[] args = event.getMessage().split(" ");
		
		if(args[0].equalsIgnoreCase("?enable"))
			ChangeState.enable(event);
		
		if(Core.enabled)
		{
			if(args[0].startsWith("?"))
				CommandSwitch.exe(event, args);
			
			if((event.getMessage().toLowerCase().contains("*lennyface*") || event.getMessage().toLowerCase().contains("*lenny face*")) && enabled && XtraFunc.isAllowed(event))
				event.respond("( ͡° ͜ʖ ͡°)");
			
			Log.logMessage(event, args);
		}
	}
	
	public void onAction(ActionEvent<PircBotX> event) throws IOException
	{
		Log.logAction(event);
	}
	
	public void onConnect(ConnectEvent<PircBotX> event) throws IOException
	{
		if(!wip)
			Channels.joinAll(event.getBot());
	}

	public static void main(String[] args) throws Exception
	{
		File oldJar = new File("JustABotX" + QuitAndUpdate.getJarInt(true) + ".jar");
		Thread.sleep(3000);
		oldJar.delete();
		Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(MojangUpdates.timer, 1, 1, TimeUnit.MINUTES);
		Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(JRWUpdates.timer, 1, 1, TimeUnit.MINUTES);
		if(args.length > 0 && args[0].equals("-wip"))
		{
			wip = true;
			Configuration<PircBotX> configuration = new Configuration.Builder<PircBotX>()
					.setName(args.length > 1 ? args[1] : "JustABotDev")
					.addAutoJoinChannel(args.length > 2 ? args[2] : "#bl4ckb0tTest")
					.setLogin("JustABotX")
					.setRealName("Just A Bot X.")
					.setAutoReconnect(true)
					.setServerHostname("irc.esper.net")
					.setAutoNickChange(true)
					.setCapEnabled(true)
					.addListener(new Core())
					.buildConfiguration();
					bot = new PircBotX(configuration);
					configuration = null;
					bot.startBot()/*.addLove(Integer.MAX_VALUE)*/;
		}
		else
		{
			Configuration<PircBotX> configuration = new Configuration.Builder<PircBotX>()
					.setName("JustABotX")
					.setNickservPassword(Passwords.NICKSERV.getPassword())
					.setLogin("JustABotX")
					.setRealName("Just a Bot X.")
					.setAutoReconnect(true)
					.setServerHostname("irc.esper.net")
					.setAutoNickChange(true)
					.setCapEnabled(true)
					.addListener(new Core())
					.buildConfiguration();
					bot = new PircBotX(configuration);
					configuration = null;
					bot.startBot();
		}
		System.gc();
	}
	public static void setState(boolean state)
	{
		if(state)
			enabled = true;
		else
			enabled = false;
	}
}
