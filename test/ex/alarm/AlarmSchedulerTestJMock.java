package ex.alarm;


import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import ex.alarm.driver.AlarmAlert;
import ex.os.service.TimeService;


public class AlarmSchedulerTestJMock {
	private static final int MONDAY = 1;

	@Test
	public void alarmIfScheduledMondayAndDayIsMonDay() throws Exception {
		Mockery context = new Mockery();
		final AlarmAlert alarmAlert = context.mock(AlarmAlert.class);
		final TimeService timeService = context.mock(TimeService.class);
		AlarmScheduler alarmScheduler = new AlarmScheduler(alarmAlert, timeService);

		context.checking(new Expectations() {{
			oneOf(timeService).getCurrentDay();
				will(returnValue(MONDAY));
			oneOf(timeService).getCurrentMinute();
				will(returnValue(12 * 60));
			oneOf(alarmAlert).startAlarm();
		}});

		alarmScheduler.addSchedule(MONDAY, 12 * 60);
		alarmScheduler.wakeUp();
		context.assertIsSatisfied();
	}
}
