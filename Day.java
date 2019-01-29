import java.util.*;

/*
 * Day
 * 
 * This class represents a day that has events scheduled for it within CalendarManager.
 * Contains the day of the week, date (year, month, day), date object, and EventTree.
 * 
 * */

interface DayInter 
{
	//Mutators
	
	/*
	 * Method to insert an event into the schedule
	 * 
	 * */
	public void addEventNode(Event event);
	
	/*
	 * Main algorithm that determines if overlaps/collisions exist between
	 * events within the EventTree schedule.
	 * 
	 * */
	public Event getOverlap(Event event);
	
	//Accessors
	public Date getDate();	
	public int getYear();	
	public int getMonth();	
	public int getDay();	
	public int getDayOfWeek();
	public boolean isDayOff();	
}

public class Day implements DayInter {
	
	/*
	 * ------private members------
	 * 
	 * (eventsTree, EventTree):
	 * represents the "schedule" for this day instance.
	 * 
	 * (daysOfWeek, int):
	 * represents day of the week for this day instanec (1-7, 1-indexed).
	 * 
	 * (year, int):
	 * represents the year for this day instance.
	 * 
	 * (month, int):
	 * represents the month for this day instance.
	 * 
	 * (day, int):
	 * represents the day for this day instance.
	 * 
	 * (date, Date):
	 * represents the date for this day instance.
	 * 
	 * (dayOff, boolean):
	 * indicates whether the day is a day off or not (holiday, vacation, etc).
	 * 
	 */
	
	private EventTree eventsTree;//binary tree implementation
	private int dayOfWeek;
	private int year;
	private int month;
	private int day;
	private Date date;
	private boolean dayOff;
	
	/*
	 * Constructor for Day. Initializes the date, dayOfWeek, eventTree, year, month, and day.
	 * This is the constructor used for days off implementation.
	 * */
	public Day(int year, int month, int day, boolean dayOff)
	{	
		//Use Calendar to get day of week to ensure we have the correct weekday.
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, 0, 0);
		this.date = cal.getTime();
		this.dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		this.eventsTree = new EventTree();
		this.year = year;
		this.month = month;
		this.day = day;
		this.dayOff = dayOff;
	}
	
	/*
	 * Constructor for Day. Initializes the date, dayOfWeek, eventTree, year, month, and day.
	 * This is the constructor used for scheduling implementation.
	 * */
	public Day(int year, int month, int day)
	{	
		//Use Calendar to get day of week to ensure we have the correct weekday.
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, 0, 0);
		this.date = cal.getTime();
		this.dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		this.eventsTree = new EventTree();
		this.year = year;
		this.month = month;
		this.day = day;
		this.dayOff = false;
	}

	public void addEventNode(Event event)
	{
		eventsTree.insert(event);
	}
	
	public Event getOverlap(Event event)
	{
		return eventsTree.overlapSearch(eventsTree.root, event);
	}
	
	public Date getDate()
	{
		return this.date;
	}
	
	public int getYear()
	{
		return this.year;
	}
	
	public int getMonth()
	{
		return this.month;
	}
	
	public int getDay()
	{
		return this.day;
	}
	
	public int getDayOfWeek()
	{
		return this.dayOfWeek;
	}
	
	public boolean isDayOff()
	{
		return this.dayOff;
	}
}
