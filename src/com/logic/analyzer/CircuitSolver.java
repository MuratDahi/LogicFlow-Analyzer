package com.logic.analyzer;
/**
 * Manages user inputs and computes the final logical result
 * of the parsed Boolean expression.
 * 
 * @author Murat Dahi
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CircuitSolver {
	ArrayList<Character> literaller = new ArrayList<Character>();
	ArrayList<Character> degiskenler = new ArrayList<Character>();
	
	public CircuitSolver(ArrayList<Character> literaller,ArrayList<Character> degiskenler) {
		this.literaller = literaller;
		this.degiskenler = degiskenler;
	}
	
	HashMap<Character,Integer> degerler = new HashMap<Character,Integer>();
	private void ekleme() {
		for(int i = 0 ; i<literaller.size() ; i++ ) {
			degerler.put(literaller.get(i),2);
		}
	}
	public void kullanici_girdisi() {
		ekleme();
		Scanner input = new Scanner(System.in);
		for(int i = 0 ; i<literaller.size() ; i++ ) {
			System.out.println(literaller.get(i)+" değişkeninin değerini giriniz:");
			while(true) {
				int sayi = input.nextInt();
				if(sayi == 0 || sayi == 1) {
				degerler.put(literaller.get(i), sayi);
				break;
				} else {
					System.out.println("Girilen sayı 0 veya 1 olmalıdır !!!! Tekrar Deneyiniz..");
				}
				
			}
			
		}
		
	}
	public void cozumle() {
		
		ArrayList<String> cozumle = new ArrayList<String>();
		String str = "";
		for(int i = 0 ; i<degiskenler.size() ; i++ ) {
			
			if(degiskenler.get(i) == '+') {
				cozumle.add(str);
				str = "";
				continue;
			}else if(i == degiskenler.size()-1) {
				str+=degiskenler.get(i);
				cozumle.add(str);
				str = "";
				break;
			}
			str+=degiskenler.get(i);
		}
		
		
		
		int toplam_sonuc = 0;
		for(int j = 0 ; j<cozumle.size() ; j++ ) {
			str = cozumle.get(j);
			int sonuc = 1;
			for(int i = 0 ; i<str.length() ; i++ ) {
				if(i != str.length()-1 && str.charAt(i+1) == '’') {
					int a = 1;
					if(degerler.get(str.charAt(i))==1) {
						a = 0;
					} else {
						a = 1;
					}
					sonuc = sonuc * a;
					i++;
				} else {
					
					sonuc = sonuc * degerler.get(str.charAt(i));
				}
				
			}
			toplam_sonuc += sonuc;
			if(sonuc == 1) {
				System.out.println("Devrenin Sonucu: 1");
				break;	
		}
		
	}
		if(toplam_sonuc == 0 ) {
			System.out.println("Devrenin Sonucu: 0");
		}

}
}