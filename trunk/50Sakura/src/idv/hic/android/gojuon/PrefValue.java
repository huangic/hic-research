package idv.hic.android.gojuon;

import android.content.SharedPreferences;

public class PrefValue {
	
	public int getQuiznum() {
		return quiznum;
	}

	public void setQuiznum(int quiznum) {
		this.quiznum = quiznum;
	}

	public boolean isHiragana() {
		return hiragana;
	}

	public void setHiragana(boolean hiragana) {
		this.hiragana = hiragana;
	}

	public boolean isKatakana() {
		return katakana;
	}

	public void setKatakana(boolean katakana) {
		this.katakana = katakana;
	}

	public boolean isHira_vocal_1() {
		return hira_vocal_1;
	}

	public void setHira_vocal_1(boolean hira_vocal_1) {
		this.hira_vocal_1 = hira_vocal_1;
	}

	public boolean isHira_vocal_2() {
		return hira_vocal_2;
	}

	public void setHira_vocal_2(boolean hira_vocal_2) {
		this.hira_vocal_2 = hira_vocal_2;
	}

	public boolean isHira_vocal_3() {
		return hira_vocal_3;
	}

	public void setHira_vocal_3(boolean hira_vocal_3) {
		this.hira_vocal_3 = hira_vocal_3;
	}

	public boolean isKata_vocal_1() {
		return kata_vocal_1;
	}

	public void setKata_vocal_1(boolean kata_vocal_1) {
		this.kata_vocal_1 = kata_vocal_1;
	}

	public boolean isKata_vocal_2() {
		return kata_vocal_2;
	}

	public void setKata_vocal_2(boolean kata_vocal_2) {
		this.kata_vocal_2 = kata_vocal_2;
	}

	public boolean isKata_vocal_3() {
		return kata_vocal_3;
	}

	public void setKata_vocal_3(boolean kata_vocal_3) {
		this.kata_vocal_3 = kata_vocal_3;
	}

	int quiznum;
	
	boolean hiragana;
	boolean katakana;
	
	boolean hira_vocal_1;
	boolean hira_vocal_2;
	boolean hira_vocal_3;
	
	boolean kata_vocal_1;
	boolean kata_vocal_2;
	boolean kata_vocal_3;
	
	public PrefValue(SharedPreferences pref){
		this.quiznum=Integer.parseInt(pref.getString("quiznum", "10"));
		
		this.hiragana=pref.getBoolean("hiragana", false);
		this.katakana=pref.getBoolean("katakana", false);
		
		this.hira_vocal_1=pref.getBoolean("hira_vocal_1",false);
		this.hira_vocal_2=pref.getBoolean("hira_vocal_2", false);
		this.hira_vocal_3=pref.getBoolean("hira_vocal_3", false);
		
		this.kata_vocal_1=pref.getBoolean("kata_vocal_1", false);
		this.kata_vocal_2=pref.getBoolean("kata_vocal_2", false);
		this.kata_vocal_3=pref.getBoolean("kata_vocal_3", false);
		
		
	}
	
	
}
