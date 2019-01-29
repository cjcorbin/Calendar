import java.util.*;

/*
 * This file is used to demonstrate the functionality of the implementations/algorithms for
 * both the Event scheduling implementation and the Day-Off implementation.
 * */

/*
 * Mock meeting request. This would likely be created in the GUI and then
 * sent to the CalendarManager.
 * 
 **/
class MeetingRequest
{
	public Date startDate;
	public Date endDate;
	public Event event;
	
	MeetingRequest(Date startDate,Date endDate,Event event)
	{  
        this.startDate=startDate;  
        this.endDate=endDate;  
        this.event=event;  
    }     
} 

public class FrontEnd {
	
	public static void main(String[] args) {
		
		///////// Event scheduling implementation /////////
		//////////////////////////////////////////////////
		
		//Instance of CalendarManager for scheduling
		CalendarManager calendarMgr_1 = new CalendarManager("cjcorbin_1");
		
		System.out.println("Demo for Event scheduling implementation");
		
		//Java Calendar instance used throughout tests (keep in mind, Java Calendar months are 0-indexed)
		Calendar instance = Calendar.getInstance();
		
		instance.set(2019, 0, 16); //January 16th, 2019
		
		//Creating new Events for January 16th, 2019
		Event event_testScheduler_1 = new Event("event_testScheduler_1", instance.getTime(), instance.get(Calendar.DAY_OF_WEEK), 9, 30, 11, 30);
		Event event_testScheduler_2 = new Event("event_testScheduler_2", instance.getTime(), instance.get(Calendar.DAY_OF_WEEK), 12, 00, 13, 00);
		
		//Adding events to calendar manager
		calendarMgr_1.addEvent(event_testScheduler_1);
		calendarMgr_1.addEvent(event_testScheduler_2);
		
		instance.set(2020,0, 23);//January 23rd, 2020
		
		//Creating new Events for January 3rd, 2020
		Event event_testScheduler_3 = new Event("event_testScheduler_5", instance.getTime(), instance.get(Calendar.DAY_OF_WEEK), 11, 40, 12, 55);
		Event event_testScheduler_4 = new Event("event_testScheduler_6", instance.getTime(), instance.get(Calendar.DAY_OF_WEEK), 12, 30, 14, 0);
		
		//Adding events to calendar manager
		calendarMgr_1.addEvent(event_testScheduler_3);
		calendarMgr_1.addEvent(event_testScheduler_4);
		
		//Creating a weekly occurring meeting and checking how many days it can occur
		//Note: the meeting created here is different from meetings created when adding
		//them to the tree. These meetings do not have a date because they are simply
		//used to check collisions on a certain day.
		
		//This test is meant to replicate the example in the assignment prompt. The
		//meeting requests passed in are intended to collide with a meeting during that year.
		//both occurrences between 2019-2020 and 2020-2021 should end up with 52 occurrences.
		
		instance.set(2019, 0, 1, 0, 0, 0);//January 1st, 2019
		Date startDate_1 = instance.getTime();
		instance.set(2020, 0, 1, 0, 0, 0);//January 1st, 2020
		Date endDate_1 = instance.getTime();
		Event weeklyEvent_1 = new Event("weeklyEvent_1",  4, 12, 30, 13, 00);//Thursday meeting, 12:30-13:30
		MeetingRequest request_1 = new MeetingRequest(startDate_1, endDate_1, weeklyEvent_1);
		
		instance.set(2020, 0, 1, 0, 0, 0);//January 1st, 2020
		Date startDate_2 = instance.getTime();
		instance.set(2021, 0, 1, 0, 0, 0);//January 1st, 2021
		Date endDate_2 = instance.getTime();
		Event weeklyEvent_2 = new Event("weeklyEvent_2",  5, 10, 30, 12, 30);//Thursday meeting, 10:30-12:30
		MeetingRequest request_2 = new MeetingRequest(startDate_2, endDate_2, weeklyEvent_2);
		
		//List of requests if the user wants to check occurrences for multiple intervals
		ArrayList<MeetingRequest> requests_1 = new ArrayList<MeetingRequest>();
		requests_1.add(request_1);
		requests_1.add(request_2);
		
		System.out.println("Occurrences for 2019-2020 Wed and 2020-2021 Thurs:");
		int totalMeetings = 0;
		for (MeetingRequest request : requests_1)
		{
			int occurrences = calendarMgr_1.checkOccurrencesSchedule(request);
			System.out.println(occurrences);
			totalMeetings += occurrences;
		}
		
		System.out.println("Total Occurences between each interval: " + totalMeetings);
		System.out.println();
		
		///////// Days Off implementation /////////
		///////////////////////////////////////////
		
		//Instance of CalendarManager for days off
		CalendarManager calendarMgr_2 = new CalendarManager("cjcorbin_2");
		
		System.out.println("Demo for Event scheduling implementation");
		
		Day day_1 = new Day(2019, 0, 9, true);//Wednesday that is off
		Day day_2 = new Day(2019, 4, 15, true);//Wednesday that is off
		Day day_3 = new Day(2019, 9, 9, true);//Wednesday that is off
		Day day_4 = new Day(2019, 9, 23, false);//Wednesday that is not off
		Day day_5 = new Day(2019, 10, 4, true);//Monday that is off
		Day day_6 = new Day(2019, 10, 27, false);//Wednesday that is not off
		
		//Adding days of to calendar
		calendarMgr_2.addDayOff(day_1);
		calendarMgr_2.addDayOff(day_2);
		calendarMgr_2.addDayOff(day_3);
		calendarMgr_2.addDayOff(day_4);
		calendarMgr_2.addDayOff(day_5);
		calendarMgr_2.addDayOff(day_6);
		
		//The meeting request passed in is intended to collide with 3 days off during that year.
		//There should be 50 occurrences for this interval.
		
		//Event scheduled on January 1st, 2019 12:30-13:30
		Event event_testDayOff = new Event("event_testDayOff", 4, 12, 30, 13, 30);
		instance.set(2019, 0, 2);
		Date startDate_3 = instance.getTime();
		instance.set(2020, 0, 2);
		Date endDate_3 = instance.getTime();
		
		
		MeetingRequest request_3 = new MeetingRequest(startDate_3,endDate_3, event_testDayOff);
		int meetings_3 = calendarMgr_2.checkOccurrencesDaysOff(request_3);
		
		System.out.println("Occurrences for 2019-2020: ");
		System.out.println(meetings_3);	
	}
}
