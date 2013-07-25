package ex.alarm;

import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ex.alarm.AlarmScheduler;
import ex.os.service.TimeService;
import ex.alarm.driver.*;

public class AlarmSchedulerTest {

	AlarmScheduler alarmScheduler;
	AlarmAlert alarmAlert;

	TimeService timeService;

	@Before
	public void setUp() throws Exception {
		timeService = mock(TimeService.class);
		alarmAlert = mock(AlarmAlert.class);
		alarmScheduler = new AlarmScheduler(alarmAlert, timeService);
	}

	@After
	public void tearDown() throws Exception {
		timeService = null;
		alarmAlert = null;
		alarmScheduler = null;
	}

	@Test
	public void noAlarmIfNoSchedule() {
		alarmScheduler.wakeUp();
		thenAlarmShoudNotAlert();
	}

	@Test
	public void alarmIfScheduled() throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.MONDAY, 10 * 60);
		whent(ScheduleDay.MONDAY, 10 * 60);
		thenAlarmShoudAlert();
	}

	@Test
	public void noAlarmIfScheduledButItsNotTheTimeYet() throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.MONDAY, 10 * 60);
		whent(ScheduleDay.MONDAY, 9 * 60);
		thenAlarmShoudNotAlert();
	}

	@Test
	public void alarmIfScheduledAllWorkingDaysAndDayIsAnyDay() throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.WORKINGDAYS, 10 * 60);
		whent(ScheduleDay.MONDAY, 10 * 60);
		thenAlarmShoudAlert();
	}

	@Test
	public void noAlarmIfScheduledAllWorkingDaysAndDayIsAnyDayButTimeIsNotYet() throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.WORKINGDAYS, 10 * 60);
		whent(ScheduleDay.MONDAY, 9 * 60);
		thenAlarmShoudNotAlert();
	}
	@Test
	public void noAlarmIfScheduledAllWorkingDaysAndDayIsNotWorkingDay() throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.WORKINGDAYS, 10 * 60);
		whent(ScheduleDay.SUNDAY, 10 * 60);
		thenAlarmShoudNotAlert();
	}
	@Test
	public void noAlarmIfScheduledAllWorkingDaysAndDayIsAnyDayAndItsNotTime()
			throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.WORKINGDAYS, 10 * 60);
		whent(ScheduleDay.MONDAY, 9 * 60);
		thenAlarmShoudNotAlert();
	}

	@Test
	public void alarmIfScheduledAllDaysDayIsAnydayandIsTime() throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.ALL, 10 * 60);
		whent(ScheduleDay.MONDAY, 10 * 60);
		thenAlarmShoudAlert();
	}

	@Test
	public void noAlarmIfScheduledAllDaysDayIsAnydayandIsNotTime() throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.ALL, 10 * 60);
		whent(ScheduleDay.SUNDAY, 2 * 60);
		thenAlarmShoudNotAlert();
	}

	@Test
	public void alarmIfScheduledAllDaysDayIsNotWorkingDayAndIsTime() throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.ALL, 10 * 60);
		whent(ScheduleDay.SATURDAY, 10 * 60);
		thenAlarmShoudAlert();
	}

	@Test
	public void noAlarmIfScheduledAllDaysAndDayIsNotaDay() throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.ALL, 10 * 60);
		whent(ScheduleDay.ALL, 10 * 60);
		thenAlarmShoudNotAlert();
	}
	
	@Test
	public void noAlarmIfScheduledAllDaysDayIsAnyDayAndItsNotTime() throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.ALL, 10 * 60);
		whent(ScheduleDay.FRIDAY, 8 * 60);
		thenAlarmShoudNotAlert();
	}

	@Test
	public void alarmIfScheduledAllWorkingDaysAndDayIsMonday() throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.WORKINGDAYS, 10 * 60);
		whent(ScheduleDay.MONDAY, 10 * 60);
		thenAlarmShoudAlert();
	}

	@Test
	public void alarmIfScheduledAllWorkingDaysAndDayIsTuesday()
			throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.WORKINGDAYS, 10 * 60);
		whent(ScheduleDay.TUESDAY, 10 * 60);
		thenAlarmShoudAlert();
	}

	@Test
	public void alarmIfScheduledAllWorkingDaysAndDayIsWednesday()
			throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.WORKINGDAYS, 10 * 60);
		whent(ScheduleDay.WEDNESDAY, 10 * 60);
		thenAlarmShoudAlert();
	}

	@Test
	public void alarmIfScheduledAllWorkingDaysAndDayIsThursday()
			throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.WORKINGDAYS, 10 * 60);
		whent(ScheduleDay.THURSDAY, 10 * 60);
		thenAlarmShoudAlert();
	}

	@Test
	public void noAlarmIfScheduledAllWorkingDaysAndDayIsThursdayItsNotTime()
			throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.WORKINGDAYS, 10 * 60);
		whent(ScheduleDay.THURSDAY, 9 * 60);
		thenAlarmShoudNotAlert();
	}

	@Test
	public void alarmIfScheduledAllWorkingDaysAndDayIsFriday() throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.WORKINGDAYS, 10 * 60);
		whent(ScheduleDay.FRIDAY, 10 * 60);
		thenAlarmShoudAlert();
	}

	@Test
	public void noAlarmIfScheduledAllWorkingDaysAndDayIsNotWorking() throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.WORKINGDAYS, 10 * 60);
		whent(ScheduleDay.SUNDAY, 10 * 60);
		thenAlarmShoudNotAlert();
	}
	
	@Test
	public void noAlarmIfScheduledAllWorkingDaysAndDayIsNotOfWeekDay() throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.WORKINGDAYS, 10 * 60);
		whent(ScheduleDay.ALL, 10 * 60);
		thenAlarmShoudNotAlert();
	}
	
	@Test
	public void noalarmIfScheduledeDayAndDayIsdifferent() throws Exception {
		givenScheduleIsAddedAs(ScheduleDay.MONDAY, 10 * 60);
		whent(ScheduleDay.TUESDAY, 10 * 60);
		thenAlarmShoudNotAlert();
	}

	private void thenAlarmShoudAlert() {
		verify(alarmAlert).startAlarm();
	}

	private void whent(int day, int minute) {
		when(timeService.getCurrentDay()).thenReturn(day);
		when(timeService.getCurrentMinute()).thenReturn(minute);
		alarmScheduler.wakeUp();
	}

	private void givenScheduleIsAddedAs(int day, int minute) {
		alarmScheduler.addSchedule(day, minute);
	}

	private void thenAlarmShoudNotAlert() {
		verify(alarmAlert, times(0)).startAlarm();
	}
}
