package tk.justramon.ircbot.justlogbotx;

import java.io.IOException;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;

import tk.justramon.ircbot.justlogbotx.cmds.CommandSwitch;

public class Core extends ListenerAdapter
{
	@Override
	public void onGenericMessage(GenericMessageEvent event) throws IOException
	{
		String[] args = event.getMessage().split(" ");
		String msg = event.getMessage();
		User user = event.getUser();
		if(args[0].startsWith("?"))
		{
			CommandSwitch.exe(event, args);
			
		}
		Log.exe(event, args);
	}
	public static void main(String[] args) throws Exception
	{
		//Configure what we want our bot to do
		Configuration configuration = new Configuration.Builder()
				.setName("JustLogBotX") //Set the nick of the bot. CHANGE IN YOUR CODE
				.setServerHostname("irc.esper.net") //Join the freenode network
				.addAutoJoinChannel("#bl4ckb0tTest") //Join the official #pircbotx channel
				.addListener(new Core()) //Add our listener that will be called on Events
				.buildConfiguration();

		//Create our bot with the configuration
		PircBotX bot = new PircBotX(configuration);
		//Connect to the server
		bot.startBot();
	}
}
