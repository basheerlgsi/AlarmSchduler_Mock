package ex.alarm;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ex.alarm.AlarmScheduler;
import ex.os.service.TimeService;
import ex.alarm.driver.*;


public class AlarmSchedulerTest {
	private static final int SUNDAY = 0;
	private static final int MONDAY = 1;
	private static final int TUESDAY = 2;
	private static final int WEDNESDAY = 3;
	private static final int THURSDAY = 4;
	private static final int FRIDAY = 5;
	private static final int SATURDAY = 6;
	private static final int ALL = 7; //Sunday to Saturday
	private static final int WORKINGDAYS = 8; //Monday to Friday
	AlarmScheduler alarmScheduler;
	AlarmSpy alarmSpy;
	// FakeTimeService fakeTimeService;

	TimeService timeService;

	@Before
	public void setUp() throws Exception {
		timeService = mock(TimeService.class);
		alarmSpy = new AlarmSpy();
		// fakeTimeService = new FakeTimeService();
		// alarmScheduler = new AlarmScheduler(alarmSpy,fakeTimeService);
		alarmScheduler = new AlarmScheduler(alarmSpy, timeService);
	}

	@After
	public void tearDown() throws Exception {
		timeService = null;
		alarmSpy = null;
		alarmScheduler = null;
	}

	@Test
	public void noAlarmIfNoSchedule() {
		alarmScheduler.wakeUp();
		thenAlarmShoudNotAlert();
	}

	@Test
	public void alarmIfScheduled() throws Exception {
		givenScheduleIsAddedAs(MONDAY, 10 * 60);
		whent(MONDAY, 10 * 60);
		thenAlarmShoudAlert();
	}

	@Test
	public void noAlarmIfScheduledButItsNotTheTimeYet() throws Exception {
		givenScheduleIsAddedAs(MONDAY, 10 * 60);
		whent(MONDAY, 9 * 60);
		thenAlarmShoudNotAlert();
	}
	
	@Test
	public void alarmIfScheduledAllWorkingDaysAndDayIsAnyDay() throws Exception {
		givenScheduleIsAddedAs(WORKINGDAYS, 10 * 60);
		whent(WORKINGDAYS, 10 * 60);
		thenAlarmShoudAlert();
	}
	@Test
	public void noAlarmIfScheduledAllWorkingDaysAndDayIsAnyDayAndItsNotTime() throws Exception {
		givenScheduleIsAddedAs(WORKINGDAYS, 10 * 60);
		whent(WORKINGDAYS, 9 * 60);
		thenAlarmShoudNotAlert();
	}
	@Test
	public void alarmIfScheduledAllDays() throws Exception {
		givenScheduleIsAddedAs(ALL, 10 * 60);
		whent(ALL, 10 * 60);
		thenAlarmShoudAlert();
	}
	@Test
	public void alarmIfScheduledAllDaysAndDayIsSunday() throws Exception {
		givenScheduleIsAddedAs(ALL, 10 * 60);
		whent(SUNDAY, 10 * 60);
		thenAlarmShoudAlert();
	}
	@Test
	public void alarmIfScheduledAllDaysAndDayIsSaturday() throws Exception {
		givenScheduleIsAddedAs(ALL, 10 * 60);
		whent(SATURDAY, 10 * 60);
		thenAlarmShoudAlert();
	}
	@Test
	public void noAlarmIfScheduledAllDaysAndItsNotTime() throws Exception {
		givenScheduleIsAddedAs(ALL, 10 * 60);
		whent(ALL, 8 * 60);
		thenAlarmShoudNotAlert();
	}
	@Test
	public void alarmIfScheduledAllWorkingDaysAndDayIsMonday() throws Exception {
		givenScheduleIsAddedAs(WORKINGDAYS, 10 * 60);
		whent(MONDAY, 10 * 60);
		thenAlarmShoudAlert();
	}
	
	@Test
	public void alarmIfScheduledAllWorkingDaysAndDayIsTuesday() throws Exception {
		givenScheduleIsAddedAs(WORKINGDAYS, 10 * 60);
		whent(TUESDAY, 10 * 60);
		thenAlarmShoudAlert();
	}
	
	@Test
	public void alarmIfScheduledAllWorkingDaysAndDayIsWednesday() throws Exception {
		givenScheduleIsAddedAs(WORKINGDAYS, 10 * 60);
		whent(WEDNESDAY, 10 * 60);
		thenAlarmShoudAlert();
	}
	
	@Test
	public void alarmIfScheduledAllWorkingDaysAndDayIsThursday() throws Exception {
		givenScheduleIsAddedAs(WORKINGDAYS, 10 * 60);
		whent(THURSDAY, 10 * 60);
		thenAlarmShoudAlert();
	}
	
	@Test
	public void alarmIfScheduledAllWorkingDaysAndDayIsFriday() throws Exception {
		givenScheduleIsAddedAs(WORKINGDAYS, 10 * 60);
		whent(FRIDAY, 10 * 60);
		thenAlarmShoudAlert();
	}
	
	private void thenAlarmShoudAlert() {
		assertTrue(alarmSpy.isAlerted());
	}

	private void whent(int day, int minute) {
		when(timeService.getCurrentDay()).thenReturn(day);
		when(timeService.getCurrentMinute()).thenReturn(minute);
		// fakeTimeService.setTime(day, minute);
		alarmScheduler.wakeUp();
	}

	private void givenScheduleIsAddedAs(int day, int minute) {
		alarmScheduler.addSchedule(day, minute);
	}

	private void thenAlarmShoudNotAlert() {
		assertFalse(alarmSpy.isAlerted());
	}
}
