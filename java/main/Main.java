package main;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import listeners.ListCancelListener;
import listeners.ListListener;
import listeners.NotesListener;
import listeners.PriorityReactionListener;
import listeners.PublicListener;
import listeners.StopListener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import tasks.Task;

public class Main
{
	public static JDA jda;
	public static ArrayList<Task> tasks;
	public static final String PREFIX = "^",
			REAC_LOW = "U+1f1f1", //regional_indicator_l
			REAC_MEDIUM = "U+1f1f2", //regional_indicator_m
			REAC_HIGH = "U+1f1ed"; //regional_indicator_h
	
	/*
	 * TODO: make sure there are no using-sootbot exploits- remove @everyone and @here from the bot
	 * add hard-storage
	 * add command syntax help for users
	 * delete tasks/messages that don't get priority added after 5 minutes, and delete reaction message when a priority is chosen
	 * ping user who made task when task is closed?
	 * make specific role only able to add tasks?
	 */
	
	public static void main(String[] args)
	{
		try
		{
			jda = new JDABuilder(AccountType.BOT).setToken("").build();
		}
		catch (LoginException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			jda.awaitReady();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		tasks = new ArrayList<Task>();
		
		jda.addEventListener(new PublicListener());
		jda.addEventListener(new PriorityReactionListener());
		jda.addEventListener(new ListListener());
		jda.addEventListener(new StopListener());
		jda.addEventListener(new ListCancelListener());
		jda.addEventListener(new NotesListener());
	}
}
