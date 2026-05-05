package com.logic.analyzer;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CircuitReader {
	ArrayList<String> degiskenler = new ArrayList<String>();
	ArrayList<Character> karakterler = new ArrayList<Character>();

	public CircuitReader() {
		try {
			File dosya = new File("boole.txt");
			Scanner input = new Scanner(dosya);
			while (input.hasNextLine()) {
				degiskenler.add(input.nextLine());
			}
			System.out.println("boole.txt dosyası okundu.");
			input.close();

		} catch (FileNotFoundException e) {
			System.out.println("Dosya Bulunamadı !");
			e.printStackTrace();
		}
	}

	private void str_list_to_char_list() {
		String str = degiskenler.get(0);
		for (int i = 0; i < str.length(); i++) {
			karakterler.add(str.charAt(i));
		}

	}

	public ArrayList<Character> literal_dondur() {
		String str = degiskenler.get(0);
		ArrayList<Character> list = new ArrayList<Character>();
		list.add(str.charAt(4));
		for (int i = 5; i < str.length(); i++) {
			boolean ctr = list.contains(str.charAt(i));
			if (ctr == false && str.charAt(i) != ' ' && str.charAt(i) != '+' && str.charAt(i) != '’') {
				list.add(str.charAt(i));
			}

		}
		return list;
	}

	public ArrayList<Character> degisken_dondur() {

		ArrayList<Character> list = new ArrayList<Character>();

		String str = degiskenler.get(0);

		for (int i = 4; i < str.length(); i++) {
			if (str.charAt(i) != ' ') {
				list.add(str.charAt(i));
			}
		}
		return list;

	}

	public void seviye_okuma() {

		str_list_to_char_list();
		int devre_seviyesi = 0;

		int not_kapi_sayisi = 0;
		ArrayList<Character> kapi_degiskenleri_not = new ArrayList<Character>();
		for (int i = 1; i < karakterler.size(); i++) {
			if (karakterler.get(i) == '’' && !kapi_degiskenleri_not.contains(karakterler.get(i - 1))) {
				kapi_degiskenleri_not.add(karakterler.get(i - 1));
				not_kapi_sayisi++;
			}
		}

		if (not_kapi_sayisi != 0) {
			devre_seviyesi++;
		}

		int and_kapi_sayisi = 0;
		ArrayList<String> terimler = new ArrayList<String>();
		String str = "";
		for (int i = 4; i < karakterler.size(); i++) {
			if (karakterler.get(i) == ' ') {
				terimler.add(str);
				str = "";
				i = i + 2;
				continue;
			}
			str = str + karakterler.get(i);
			if (karakterler.size() - 1 == i) {
				terimler.add(str);
				break;
			}
		}

		ArrayList<String> kapi_degiskenleri_and = new ArrayList<String>();
		for (int i = 0; i < terimler.size(); i++) {
			String terim = terimler.get(i);
			if (terim.length() == 2 && terim.charAt(1) == '’') {
				continue;
			} else if (terim.length() == 1) {
				continue;
			} else {
				and_kapi_sayisi++;
				kapi_degiskenleri_and.add(terim);
			}

		}

		if (and_kapi_sayisi != 0) {
			devre_seviyesi++;
		}

		int or_kapi_sayisi = terimler.size();

		ArrayList<String> kapi_degiskenleri_or = new ArrayList<String>();
		
		for (int i = 0; i < terimler.size(); i++) {
			kapi_degiskenleri_or.add(terimler.get(i));
		}

		if (or_kapi_sayisi != 1) {
			devre_seviyesi++;
		}

		System.out.println("Devre " + devre_seviyesi + " seviyelidir.");
		int yazdirma = 1;
		if (not_kapi_sayisi != 0) {
			System.out.println(yazdirma + ".Seviye İçin:");
			System.out.println("\tKapı türü:DEĞİL," + not_kapi_sayisi + " tane kapı var");
			for (int i = 1; i <= not_kapi_sayisi; i++) {
				System.out.println("\t" + i + ".Kapının girişi:" + kapi_degiskenleri_not.get(i - 1));
			}
			yazdirma++;
		}
		
		if (and_kapi_sayisi != 0) {
			System.out.println(yazdirma + ". Seviye İçin:");
			System.out.println("\tKapı türü:VE," + and_kapi_sayisi + " tane kapı var");
			for (int i = 1; i <= and_kapi_sayisi; i++) {

				String harfler = kapi_degiskenleri_and.get(i - 1);
				int kapi_sayisi = 0;
				for (int b = 0; b < harfler.length(); b++) {
					if (harfler.charAt(b) != '’') {
						kapi_sayisi++;
					}
				}
				System.out.print("\t" + i + ".Kapı " + kapi_sayisi + "-girişli ve girişleri: ");

				for (int j = 0; j < harfler.length(); j++) {
					if (j != harfler.length() - 1 && harfler.charAt(j + 1) != '’') {
						System.out.print(harfler.charAt(j) + ", ");
					} else if (j == harfler.length() - 1) {
						System.out.print(harfler.charAt(j));
					} else if (j + 1 != harfler.length() - 1 && harfler.charAt(j + 1) == '’') {
						char harf = harfler.charAt(j);
						for (int a = 0; a < kapi_degiskenleri_not.size(); a++) {
							if (harf == kapi_degiskenleri_not.get(a)) {
								System.out.print(a + 1 + ".DEĞİL kapısı, ");
							}
						}
						j++;
					} else if (j + 1 == harfler.length() - 1 && harfler.charAt(j + 1) == '’') {
						char harf = harfler.charAt(j);
						for (int a = 0; a < kapi_degiskenleri_not.size(); a++) {
							if (harf == kapi_degiskenleri_not.get(a)) {
								System.out.print(a + 1 + ".DEĞİL kapısı");
							}
						}
						j++;
					}

				}
				System.out.println();

			}
			yazdirma++;

		}
		
		if (or_kapi_sayisi >= 1) {
			System.out.println(yazdirma + ". Seviye İçin:");
			System.out.println("\tKapı türü: VEYA,1 tane kapı var");
			System.out.print("\t1.Kapı " + or_kapi_sayisi + "-girişli ve girişleri: ");
			for (int i = 0; i < kapi_degiskenleri_or.size(); i++) {
				String degisken = kapi_degiskenleri_or.get(i);
				if (degisken.length() == 2) {
					for (int a = 0; a < kapi_degiskenleri_not.size(); a++) {
						if (degisken.charAt(0) == kapi_degiskenleri_not.get(a)
								&& i != kapi_degiskenleri_or.size() - 1) {
							System.out.print(a + 1 + ". DEĞİL kapısı,");
						} else if (degisken.charAt(0) == kapi_degiskenleri_not.get(a)
								&& i == kapi_degiskenleri_or.size() - 1) {
							System.out.print(a + 1 + ". DEĞİL kapısı");
						}
					}
				} else if (degisken.length() == 1) {
					if (i != kapi_degiskenleri_or.size() - 1) {
						System.out.print(degisken + ", ");
					} else {
						System.out.print(degisken);
					}
				}

				for (int x = 0; x < kapi_degiskenleri_and.size(); x++) {
					if (degisken.equals(kapi_degiskenleri_and.get(x)) && i != kapi_degiskenleri_or.size() - 1) {
						System.out.print(x + 1 + ".VE kapısı, ");
					} else if (degisken.equals(kapi_degiskenleri_and.get(x)) && i == kapi_degiskenleri_or.size() - 1) {
						System.out.print(x + 1 + ".VE kapısı");
					}
				}

			}

		}

		System.out.println();
	}

}
