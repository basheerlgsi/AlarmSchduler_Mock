package ex.alarm.driver;

public class AlarmSpy implements AlarmAlert {

	private boolean isAlarmed = false;

	@Override
	public void startAlarm() {
		isAlarmed = true;
	}

	public boolean isAlerted() {
		return isAlarmed ;
	}

}
