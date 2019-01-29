import java.util.*;

/*
 * CalendarManager
 * 
 * This is the main component/class that is used to organize and process requests from an imaginary 
 * calendar GUI. The class is able to receive a properly formatted Event as input
 * and organize them into a list of Days that contain an EventTree. The
 * class is then able to receive a request for multiple intervals of
 * reoccurring Event times and determine the total number of meetings
 * that would be able to exist without conflicting with other Events.
 * This implementation of a calendar is only meant to handle Events and
 * is not a literal representation of a calendar.
 * 
 * There are two implementations of this component. One involves simply
 * labeling days as being holidays/vacation days which makes them off limits to have a
 * meeting on (quick and dirty). The other implementation uses an Event scheduling
 * mechanism which it treats holidays, vacations, meetings, or other events as all one
 * entity. This allows for developers to have greater control over scheduling meetings
 * by incorporating other event/meeting times instead of simply relying on a day being marked
 * as off limits. This could also allow for CalendarManagers for separate users to interact 
 * with one another in the sense that they could schedule meetings while taking employees
 * schedules into account.
 * 
 * */

interface CalendarInter
{
	/*
	 * Method that adds a day to daysOfWeek (scheduler).
	 * */
	public void addDay(Day day);
	
	/*
	 * Method that adds a day to daysOfWeek (days off).
	 * */
	public void addDayOff(Day day);
	
	/*
	 * Adds an event to a day in daysOfWeek. If the day does not exist, it will first create
	 * a new day and add it to daysOfWeek with the new event. If the day does exist, it simply
	 * adds the event to it.
	 * */
	public void addEvent(Event event);
	
	/*
	 * Method that counts the number of reoccurring meetings by passing in the
	 * requested meeting time and a certain end date. 
	 * */
	public int checkOccurrencesSchedule(MeetingRequest request);
	
	/*
	 * Method used to calculate total reocurring meeting times based on a list
	 * of events and end dates.
	 * */

	public int checkOccurrencesDaysOff(MeetingRequest request);
	
	//Accessors
	
	public String getUser();
}

public class CalendarManager implements CalendarInter {
	
	/*
	 * ------private members------
	 * 
	 * (userName, String):
	 * placeholder for a potential user's calendar. CalendarManagers
	 * could potentially interact with one another
	 * 
	 * (daysOfWeek, ArrayList<Map<String, Day> >):
	 * meant to represent weeks spanning an entire year where each index is a 
	 * weekday that contains a HashMap of Day that corresponds to that 
	 * particular weekday.
	 * 
	 */
	
	private String userName;
	private ArrayList<Map<String, Day> > daysOfWeek;
	
	/*
	 * Constructor for CalendarManager. Creates the ArrayList daysOfWeek and initializes
	 * userName.
	 * 
	 * */
	public CalendarManager(String user)
	{
		this.userName = user;
		daysOfWeek = new ArrayList<Map<String, Day> >(7);
		for (int i = 0; i <= 7; i++) 
		{
			daysOfWeek.add(new HashMap<String, Day>());
		}
	}
	
	//Mutators
	
	public void addDay(Day day)
	{
		//The key for the <String, Day> pair is a string of integers formed by
		//concatenating the year, month, and day together for a day.
		
		String yearMonthDayKey = Integer.toString(day.getYear()) + Integer.toString(day.getMonth()) +
				Integer.toString(day.getDay());
		daysOfWeek.get(day.getDayOfWeek()-1).put(yearMonthDayKey,day);
	}
	
	public void addDayOff(Day day)
	{
		//The key for the <String, Day> pair is a string of integers formed by
		//concatenating the year, month, and day together for a day.
		
		String yearMonthDayKey = Integer.toString(day.getYear()) + Integer.toString(day.getMonth()) +
				Integer.toString(day.getDay());
		daysOfWeek.get(day.getDayOfWeek()-1).put(yearMonthDayKey,day);
	}
	
	public void addEvent(Event event)
	{
		//Create calendar instance to get year, month, day from event's date.
		Calendar calInstance = Calendar.getInstance();
		calInstance.setTime(event.getDate());
		
		//Key used in HashMap
		String yearMonthDayKey = 
				Integer.toString(calInstance.get(Calendar.YEAR)) + 
				Integer.toString(calInstance.get(Calendar.MONTH)) + 
				Integer.toString(calInstance.get(Calendar.DAY_OF_MONTH));
		
		Map<String, Day> days = daysOfWeek.get(event.getDayOfWeek()-1);
		
		//If day exists, add event to it
		if (days.containsKey(yearMonthDayKey))
		{
			days.get(yearMonthDayKey).addEventNode(event);
		}
		
		//Otherwise, create day and event to it then add it to daysOfWeek
		else
		{
			//When creating a new day in this implementation, we set the default as false
			Day newDay = new Day(calInstance.get(Calendar.YEAR), 
					calInstance.get(Calendar.MONTH), calInstance.get(Calendar.DAY_OF_MONTH));
			newDay.addEventNode(event);
			this.addDay(newDay);
		}
	}
	
	//Accessors
	
	public String getUser()
	{
		return this.userName;
	}
	
	public int checkOccurrencesDaysOff(MeetingRequest request)
	{	
		Event weeklyEvent = request.event;
		Date startDate = request.startDate;
		Date endDate = request.endDate;
		
		Map<String, Day> days = daysOfWeek.get(weeklyEvent.getDayOfWeek()-1);// - 1 because array is
																			// zero index, days aren't
		
		//instance used to "iterate" from start date to end date
		Calendar calInstance = Calendar.getInstance();
		calInstance.setTime(startDate);
		
		//Quick while loop used to find the first occurrence of the reocurring weekly
		//event
		while(calInstance.get(Calendar.DAY_OF_WEEK) != weeklyEvent.getDayOfWeek())
		{
			calInstance.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		int numMeetings = 0;
		
		//While the start date is before the end date, increment the date by a week.
		while (calInstance.getTime().before(endDate) || calInstance.getTime().equals(endDate))
		{	
			//Key used in HashMap
			String yearMonthDayKey = 
					Integer.toString(calInstance.get(Calendar.YEAR)) + 
					Integer.toString(calInstance.get(Calendar.MONTH)) + 
					Integer.toString(calInstance.get(Calendar.DAY_OF_MONTH));
			
			//If the day exists in HashMap
			if (days.containsKey(yearMonthDayKey))
			{
				//checks for overlap/collision of events. If null, there
				//was not collision and increment numMeetings
				if (!days.get(yearMonthDayKey).isDayOff())
				{
					numMeetings += 1;
				}
			}
			
			//If day does not even exist in HashMap, increment numMeetings
			else
			{
				numMeetings += 1;
			}
			//Increments by one week.
			calInstance.add(Calendar.DAY_OF_MONTH, 7);
		}
		return numMeetings;
	}
	
	public int checkOccurrencesSchedule(MeetingRequest request)
	{
		Event weeklyEvent = request.event;
		Date startDate = request.startDate;
		Date endDate = request.endDate;
		
		Map<String, Day> days = daysOfWeek.get(weeklyEvent.getDayOfWeek()-1);// - 1 because array is
		// zero index, days aren't

		//instance used to "iterate" from start date to end date
		Calendar calInstance = Calendar.getInstance();
		calInstance.setTime(startDate);

		//Quick while loop used to find the first occurrence of the reocurring weekly
		//event
		while(calInstance.get(Calendar.DAY_OF_WEEK) != weeklyEvent.getDayOfWeek())
		{
			calInstance.add(Calendar.DAY_OF_MONTH, 1);
		}

		int numMeetings = 0;

		//While the start date is before the end date, increment the date by a week.
		while (calInstance.getTime().before(endDate) || calInstance.getTime().equals(endDate))
		{	
			//Key used in HashMap
			String yearMonthDayKey = 
					Integer.toString(calInstance.get(Calendar.YEAR)) + 
					Integer.toString(calInstance.get(Calendar.MONTH)) + 
					Integer.toString(calInstance.get(Calendar.DAY_OF_MONTH));
			
			//If the day exists in HashMap
			if (days.containsKey(yearMonthDayKey))
			{
				//checks for overlap/collision of events. If null, there
				//was not collision and increment numMeetings
				if (days.get(yearMonthDayKey).getOverlap(weeklyEvent) == null)
				{
					numMeetings += 1;
				}
			}
			
			//If day does not even exist in HashMap, increment numMeetings
			else
			{
				numMeetings += 1;
			}
			//Increments by one week.
			calInstance.add(Calendar.DAY_OF_MONTH, 7);
		}
		return numMeetings;
	}
}
