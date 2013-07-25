package ex.alarm;

import ex.alarm.driver.AlarmAlert;
import ex.os.service.TimeService;

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
		if ((isScheduled && isTheWorkingDayAndTime())
				|| (isScheduled && isTheDayAndTime())
				|| (isScheduled && isAllDaysAndTime()))
			alarmAlert.startAlarm();
	}
	
	private boolean isAllDaysAndTime() {
		
		if ((timeService.getCurrentDay() >= 0 && this.minute == timeService
				.getCurrentMinute())
				&& (timeService.getCurrentDay() <= 7 && this.minute == timeService
						.getCurrentMinute()))
			return true;
		return false;
	}
	
	private boolean isTheWorkingDayAndTime() {
		
		if ((timeService.getCurrentDay() > 0 && this.minute == timeService
				.getCurrentMinute())
				&& (timeService.getCurrentDay() < 7 && this.minute == timeService
						.getCurrentMinute()))
			return true;
		return false;
	}

	private boolean isTheDayAndTime() {

		return this.day == timeService.getCurrentDay()
				&& this.minute == timeService.getCurrentMinute();
	}

	public void addSchedule(int day, int minute) {
		this.day = day;
		this.minute = minute;
		isScheduled = true;
	}

}
