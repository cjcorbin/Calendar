import java.util.*;

/*
 * Event
 * 
 * This class represents individual events which are part of the EventTree which
 * makes up a Day. It contains the date, day of the week, start minute, end minute,
 * and the name of the event.
 *
 * */

interface EventInter
{
	//Accessors
	
	/*
	 * Method for determining if two events overlap.
	 * 
	 * */
	public boolean isOverlap(Event event);
	
	public int getStart();
	public int getEnd();
	public String getName();
	public int getDayOfWeek();
	public Date getDate();
}

public class Event {

	/*
	 * ------private members (Event)------
	 * 
	 * (date, Date):
	 * represents the date for this event.
	 * 
	 * (dayOfWeek, int):
	 * represents day of week for this event.
	 * 
	 * (startMin, int):
	 * represents start minute (0-1440) for this event.
	 * 
	 * (endMin, int):
	 * represents end minute (0-1440) for this event.
	 * 
	 * (name, String):
	 * represents name for this event.
	 * 
	 * */
	
	private Date date;
	private int dayOfWeek;
	private int startMin; 
	private int endMin;
	private String name;
	
	/*
	 * Constructor for Event when inserting events into a day HashMap. Initializes name, date, 
	 * dayOfWeek, startHour, startMin, endHour, and endMin.
	 * 
	 * */
	public Event(String name, Date date, int dayOfWeek, int startHour, int startMin, int endHour, int endMin) 
	{
		this.date = date;
		this.dayOfWeek = dayOfWeek;
		this.name = name;
		this.startMin = startHour*60 + startMin;
		this.endMin = endHour*60 + endMin; 
	} 
	
	/*
	 * Constructor used for simply checking the event tree for an existing event since
	 * The event tree solely relies on meeting times, not dates. Initializes name, 
	 * dayOfWeek, startHour, startMin, endHour, and endMin.
	 * */
	public Event(String name, int dayOfWeek, int startHour, int startMin, int endHour, int endMin) 
	{
		this.date = null;
		this.dayOfWeek = dayOfWeek;
		this.name = name;
		this.startMin = startHour*60 + startMin;
		this.endMin = endHour*60 + endMin; 
	}
	
	//Accessors
	
	public boolean isOverlap(Event event)
	{
		if (this.startMin <= event.getEnd() && event.getStart() <= this.endMin)
		{
			return true;
		}
		return false;
	}
	
	public int getStart()
	{
		return this.startMin;
	}
	
	public int getEnd()
	{
		return this.endMin;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getDayOfWeek()
	{
		return this.dayOfWeek;
	}
	
	public Date getDate()
	{
		return this.date;
	}
}

