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
		case ScheduleDay.WORKINGDAYS:
		case ScheduleDay.ALL:
			if (!(this.minute == timeService.getCurrentMinute()))
				isScheduled = false;
			break;

		default:
			if (!isTheDayAndTime())
				isScheduled = false;
			break;
		}

		if (isScheduled)
			alarmAlert.startAlarm();
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
