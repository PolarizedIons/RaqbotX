package me.justramon.ircbot.justabotx.cmd;

import java.io.IOException;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import me.justramon.ircbot.justabotx.Core;
import me.justramon.ircbot.justabotx.util.Ops;

public class ChangeState
{
	public static void enable(MessageEvent<PircBotX> event) throws IOException
	{
		if(!Core.enabled)
		{
			if(Ops.isOp(event))
			{
				Core.setState(true);
				event.respond("Enabled!");
			}
		}
		else
			event.respond("JustABotX is already enabled!");
	}

	public static void disable(MessageEvent<PircBotX> event) throws IOException
	{
			if(Ops.isOp(event))
			{
				Core.setState(false);
				event.respond("Disabled!");
			}
	}
}
