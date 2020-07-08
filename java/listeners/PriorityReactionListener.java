package listeners;
import main.Main;
import main.Utils;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tasks.Task;
import tasks.TaskAwaitingPriority;
import tasks.TaskReady;

public class PriorityReactionListener extends ListenerAdapter
{
	//priorities are given to tasks by reacting to a message
	//if a reaction is added, we check to see if a priority
	//was added, and we can officially add the task to the list
	
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e)
	{
		//don't respond to our own emotes on this
		if(e.getUser().equals(e.getJDA().getSelfUser()))
				return;
		
		for(Task t : Main.tasks)
		{			
			//if the reacted message is one we're waiting for a reaction on
			//and it is the same member
			if( t instanceof TaskAwaitingPriority && ((TaskAwaitingPriority) t).getReactionid().equals(e.getMessageId())
					&& e.getMember().getId().equals(t.getMember().getId()))
			{
				switch(e.getReactionEmote().getAsCodepoints()) //unicode of emoji, as we're using unicode emojis for this program
				{
					case Main.REAC_LOW: //TODO: make it delete the reaction message?
						TaskReady tr_low = new TaskReady((TaskAwaitingPriority) t, Task.Priority.LOW);
						Main.tasks.add(tr_low);
						Main.tasks.remove(t);
						e.getChannel().sendMessage("Your task has been submitted to the "
								+ "staff team!").queue();
						Utils.sendToModLogs(tr_low.getEmbed("New task: "), e.getGuild());//send to relevant modlogs
						return;
						
					case Main.REAC_MEDIUM:
						TaskReady tr_med = new TaskReady((TaskAwaitingPriority) t, Task.Priority.MEDIUM);
						Main.tasks.add(tr_med);
						Main.tasks.remove(t);
						e.getChannel().sendMessage("Your task has been submitted to the "
								+ "staff team!").queue();
						Utils.sendToModLogs(tr_med.getEmbed("New task: "), e.getGuild());//send to relevant modlogs
						return;
						
					case Main.REAC_HIGH:
						TaskReady tr_hi = new TaskReady((TaskAwaitingPriority) t, Task.Priority.HIGH);
						Main.tasks.add(tr_hi);
						Main.tasks.remove(t);
						e.getChannel().sendMessage("Your task has been submitted to the "
								+ "staff team!").queue();
						Utils.sendToModLogs(tr_hi.getEmbed("New task: "), e.getGuild());//send to relevant modlogs
						return;
						
					default: //any unexpected reactions on the message
						return;
				}
			}
		}
	}
}
