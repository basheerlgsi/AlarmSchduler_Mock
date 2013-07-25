package ex.os.service;

public class FakeTimeService implements TimeService {

	private int day = -1;
	private int minute = -1;

	@Override
	public int getCurrentMinute() {
		// TODO Auto-generated method stub
		return this.minute;
	}

	@Override
	public int getCurrentDay() {
		// TODO Auto-generated method stub
		return this.day;
	}

	public void setTime(int day, int minute) {
		this.day = day;
		this.minute = minute;
		
	}

}
