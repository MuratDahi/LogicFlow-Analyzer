package com.logic.analyzer;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		CircuitReader dosya = new CircuitReader();
		ArrayList<Character> literaller = new ArrayList<Character>();
		ArrayList<Character> degiskenler = new ArrayList<Character>();
		dosya.seviye_okuma();
		
		literaller = dosya.literal_dondur();
		degiskenler = dosya.degisken_dondur();
		
		CircuitSolver devre = new CircuitSolver(literaller,degiskenler);
		
		devre.kullanici_girdisi();
		devre.cozumle();
		
		
		

	}

}
