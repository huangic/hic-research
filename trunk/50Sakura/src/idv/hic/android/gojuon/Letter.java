package idv.hic.android.gojuon;

import android.util.Log;

public class Letter {
	
	static Letter mock=new Letter(-1,"",0,0,0,0);
	public static Letter getMock(){
		return mock;
		
	}
	int id;
	String name="";
	int order;
	int error;
	int correct;
	int total;
	
	public Letter(){
		super();
	};
	
	public Letter(int id,String name,int order,int error,int correct,int total){
			this.id=id;
			this.name=name;
			this.order=order;
			this.error=error;
			this.correct=correct;
			this.total=total;
		Log.d("Extra",this.id+" "+this.name+" "+this.order+" "+this.correct);
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
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public int getCorrect() {
		return correct;
	}
	public void setCorrect(int correct) {
		this.correct = correct;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
