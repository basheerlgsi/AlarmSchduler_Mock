package ex.alarm;

import ex.alarm.driver.AlarmAlert;
import ex.os.service.TimeService;
import ex.alarm.ScheduleDay;

public class AlarmScheduler {

	private boolean isScheduled = false;
	private AlarmAlert alarmAlert;
	private int day = -1;
	private int minute = -1;
	private TimeService timeService;

	public AlarmScheduler(AlarmAlert alarmAlert, TimeService timeService) {
		this.alarmAlert = alarmAlert;
		this.timeService = timeService;
	}

	public void wakeUp() {
		switch (this.day) {
		case ScheduleDay.MONDAY:
		case ScheduleDay.TUESDAY:
		case ScheduleDay.WEDNESDAY:
		case ScheduleDay.THURSDAY:
		case ScheduleDay.FRIDAY:
			if (isTheDayAndTime())
				alarmAlert.startAlarm();
			break;
		case ScheduleDay.WORKINGDAYS:
			if ((timeService.getCurrentDay() > 0 && timeService.getCurrentDay() < 7)
					&& (this.minute == timeService.getCurrentMinute()))
				alarmAlert.startAlarm();
			break;
		case ScheduleDay.ALL:
			if ((timeService.getCurrentDay() >= 0 && timeService
					.getCurrentDay() < 7)
					&& (this.minute == timeService.getCurrentMinute()))
				alarmAlert.startAlarm();
			break;

		default:
			isScheduled = false;
			break;
		}

	}

	private boolean isTheDayAndTime() {
		return ((this.day == timeService.getCurrentDay()) && (this.minute == timeService
				.getCurrentMinute()));
	}

	public void addSchedule(int day, int minute) {
		this.day = day;
		this.minute = minute;
		isScheduled = true;
	}

}
