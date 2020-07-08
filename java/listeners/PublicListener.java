package listeners;
import java.time.Instant;

import main.Main;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tasks.TaskAwaitingPriority;

public class PublicListener extends ListenerAdapter
{
	//command for initially creating a task in the system
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		String msg = e.getMessage().getContentRaw();
		String[] args = msg.split(" ");
		
		// :JoshTodo
		if(args[0].equalsIgnoreCase(Main.PREFIX + "StaffTodo"))
		{
			if(args.length == 1)
				return;
			
			//if there is actually a message to be sent
			e.getChannel().sendMessage("Task recorded! Please specify a priority by reacting "
					+ "with :regional_indicator_l:, :regional_indicator_m: or "
					+ ":regional_indicator_h:").queue(reactmsg ->
					{
						//extract the message
						String message = "";
						for(int i = 1; i < args.length; i++)
							message += " " + args[i];
						
						message = message.substring(1);
						
						//create a TaskAwaitingPriority and add it to the list
						Main.tasks.add(new TaskAwaitingPriority(e.getMember(), message, reactmsg.getId(), Instant.now()));
						
						//add emotes for ease of user
						reactmsg.addReaction(Main.REAC_LOW).queue(); //L emote
						reactmsg.addReaction(Main.REAC_MEDIUM).queue(); //M emote
						reactmsg.addReaction(Main.REAC_HIGH).queue(); //H emote
					});
		}
	}
}
