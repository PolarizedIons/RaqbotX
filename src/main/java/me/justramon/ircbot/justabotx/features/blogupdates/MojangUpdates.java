package me.justramon.ircbot.justabotx.features.blogupdates;

import java.io.IOException;
import java.net.URL;

import org.pircbotx.Colors;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import me.justramon.ircbot.justabotx.config.ConfigHandler;
import me.justramon.ircbot.justabotx.core.Core;

public class MojangUpdates
{
	private static String lastUri = "";

	public static void run()
	{
		URL feedUrl;
		SyndFeed feed = null;

		try
		{
			feedUrl = new URL("http://mojang.com/feed.xml");
			feed = new SyndFeedInput().build(new XmlReader(feedUrl));
			SyndEntry latestPost = feed.getEntries().get(0);
			if (!latestPost.getUri().equals(lastUri) && !lastUri.equals("") && !latestPost.getUri().equals(""))
			{
				for(String channel : ConfigHandler.config.mojangUpdateChans)
				{
					Core.bot.sendIRC().message(channel, Colors.BOLD + "New Mojang blog post titled: " + Colors.RED + latestPost.getTitle());
					if(latestPost.getTitle().toLowerCase().contains("snapshot"))
					{
						Core.bot.sendIRC().message(channel, Colors.BOLD + Colors.GREEN + "Minecraft Snapshot!");
					}
					Core.bot.sendIRC().message(channel, Colors.BOLD + "Link: " + Colors.PURPLE + latestPost.getLink());
				}
			}
			lastUri = latestPost.getUri();
		}
		catch (IllegalArgumentException | FeedException | IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Force-show for the Mojang updater.
	 */
	public static void debugForceShow() throws IOException
	{
		URL feedUrl;
		SyndFeed feed = null;

		try
		{
			feedUrl = new URL("http://mojang.com/feed.xml");
			feed = new SyndFeedInput().build(new XmlReader(feedUrl));
			SyndEntry latestPost = feed.getEntries().get(0);
			Core.bot.sendIRC().message(ConfigHandler.config.updateDevChan, Colors.BOLD + "New Mojang blog post titled: " + Colors.RED + latestPost.getTitle());
			if(latestPost.getTitle().toLowerCase().contains("snapshot"))
			{
				Core.bot.sendIRC().message(ConfigHandler.config.updateDevChan, Colors.BOLD + Colors.GREEN + "Minecraft Snapshot!");
			}
			Core.bot.sendIRC().message(ConfigHandler.config.updateDevChan, Colors.BOLD + "Link: " + Colors.PURPLE + latestPost.getLink());
		}
		catch (IllegalArgumentException | FeedException | IOException e)
		{
			e.printStackTrace();
		}
	}
}
