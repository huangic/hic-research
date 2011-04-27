package idv.hic.android.gojuon;

import android.util.Log;

public class Letter  implements Cloneable {

	static Letter MOCK = new Letter(-1, "", 0, 0, 0, 0);

	public static Letter getMock() {
		return MOCK;

	}

	int id;
	String name = "";
	int order;
	int errorNum;
	int correctNum;
	int totalNum;
	boolean current=false;
	boolean correct=false;
	boolean used=false;
	String phonics;
	

	public Letter(int id, String name, int order, int error, int correct,
			int total) {
		this.id = id;
		this.name = name;
		this.order = order;
		this.errorNum = error;
		this.correctNum = correct;
		this.totalNum = total;
		Log.d("Extra", this.id + " " + this.name + " " + this.order + " "
				+ this.correct);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	

	public int getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}

	public int getCorrectNum() {
		return correctNum;
	}

	public void setCorrectNum(int correctNum) {
		this.correctNum = correctNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public String getPhonics() {
		return phonics;
	}

	public void setPhonics(String phonics) {
		this.phonics = phonics;
	}
	
	
	
	
}
