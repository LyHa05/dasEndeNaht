package Aufgabe_09;

import java.util.HashSet;
import java.util.Iterator;

public class PResolution {

	public static HashSet<Klausel> knf;
	public static boolean resolviert = false;
	public static int zaehler1 = 0;
	public static int zaehler2 = 0;
	
	
	/**Methode prüft, ob Klausel positiv ist*/
	public static boolean istPositiveKlausel(HashSet<Literal> zuPruefendeKlausel) {
		
//		System.out.println("----pruefen, ob positive Klausel");
		
//		System.out.println("zuPruefendeKlausel: " + zuPruefendeKlausel);
		
		for(Literal l: zuPruefendeKlausel) {
			if (l.getWahrheitswert() == false) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean enthaeltPositiveKlausel(HashSet<Klausel> zuPruefendeKlauseln) {
		for(Klausel k: zuPruefendeKlauseln) {
			if (istPositiveKlausel(k.getLiterale())) {
				return true;
			}
		}
		return false;
	}

	public static HashSet<Klausel> pResolvieren(HashSet<Klausel> uebergebenerKNF) {

		if(!enthaeltPositiveKlausel(uebergebenerKNF)) {
			throw new IllegalArgumentException("Die uebergebene KNF enthaelt keine positiven Klauseln.");
		}
		
		HashSet<Klausel> verfuegbareKlauseln = new HashSet<Klausel>(tiefeKopieErstellen(uebergebenerKNF));
		HashSet<Klausel> zuResolvierendeKlauseln = new HashSet<Klausel>(tiefeKopieErstellen(uebergebenerKNF));
		int anzahlVerfKlau;
		
		do {
			
			resolviert = false;
			anzahlVerfKlau = verfuegbareKlauseln.size();
								
			/** Suchen von komplementaeren Klauseln */
			
			Iterator<Klausel> verfuegIter = verfuegbareKlauseln.iterator();
			while (verfuegIter.hasNext() && (resolviert == false)) {
				Klausel k1 = verfuegIter.next(); 	    
				
				Iterator<Klausel> zuResIter = zuResolvierendeKlauseln.iterator();
				while (zuResIter.hasNext() && (resolviert == false)) {
					Klausel k2 = zuResIter.next();
					
					for (Literal l1 : k1.getLiterale()) {
						for (Literal l2 : k2.getLiterale()) {

							++zaehler1;
							
							if (istResolvierbar(k1, k2, l1, l2)) {

								resolviert = true;
								
//								System.out.println("-----Resolution-----");
//								System.out.println("k1: " + k1);
//								System.out.println("k2: " + k2);

								Klausel neueKlausel = new Klausel(resolvierteLiteraleZusammen(zuResolvierendeKlauseln, k1, k2, l1, l2));
								
								if (!neueKlausel.getLiterale().isEmpty()) {verfuegbareKlauseln.add(neueKlausel);};
								
								if (!neueKlausel.getLiterale().isEmpty()) {zuResolvierendeKlauseln.add(neueKlausel);};
								
								zuResolvierendeKlauseln.remove(k1);
								zuResolvierendeKlauseln.remove(k2);
								
//								System.out.println("neue Klausel: " + neueKlausel);
//								System.out.println("verfuegbareKlauseln: " + verfuegbareKlauseln);
//								System.out.println("zuResolvierendeKlauseln: " + zuResolvierendeKlauseln);
								
								++zaehler2;
								
//								break;
							}
//							break;
						}
//						break;
					}
//					break;
				}
//				break;

			}
		} while (resolviert == true && !(zuResolvierendeKlauseln.isEmpty()) && (verfuegbareKlauseln.size() != anzahlVerfKlau));

		return zuResolvierendeKlauseln;

	}

	private static Klausel resolvierteLiteraleZusammen(HashSet<Klausel> nochZuResolvieren, Klausel k1, Klausel k2, Literal l1, Literal l2) {

		Klausel neuK = new Klausel();


		for (Literal tempL : k1.getLiterale()) {
			if (!(tempL.equals(l1))) {
				neuK.setLiteral(tempL);
			}
		}
		
		for (Literal tempL : k2.getLiterale()) {
			if (!(tempL.equals(l2))) {
				neuK.setLiteral(tempL);
			}
		}		
		
		return neuK;
	}

	private static HashSet<Klausel> tiefeKopieErstellen(HashSet<Klausel> klauselliste) {
		HashSet<Klausel> kopierteKlauselListe = new HashSet<Klausel>();

		for (Klausel tempKlausel : klauselliste) {
			Klausel neueK = new Klausel(tempKlausel.getLiterale());

			for (Literal tempLiteral : tempKlausel.getLiterale()) {
				Literal lit = new Literal(tempLiteral);
				neueK.setLiteral(lit);
			}

			kopierteKlauselListe.add(neueK);
		}

		return kopierteKlauselListe;
	}

	public static String ausgabe(HashSet<Klausel> ergebnisKlausel) {
		StringBuilder builder = new StringBuilder();
		Object[] knf = ergebnisKlausel.toArray();
		builder.append('{');
		
		if (knf.length != 0) {
			for (int i = 0; i < knf.length-1; i++) {
				builder.append(knf[i].toString());
			}
			builder.append(knf[knf.length-1]);
		} else {
			builder.append(' ');
		}
		builder.append('}');
		
		return builder.toString();
	}

	/** prueft, ob 2 Literale resolvierbar sind */
	private static boolean istResolvierbar(Klausel klauselA, Klausel klauselB, Literal literalA, Literal literalB) {
		return ((literalA.getBezeichner() == literalB.getBezeichner())
				&& (literalA.getWahrheitswert() != literalB.getWahrheitswert())
				&& !(klauselA.equals(klauselB))
				&& (istPositiveKlausel(klauselA.getLiterale()) || istPositiveKlausel(klauselB.getLiterale())));
	}

	public static void main(String[] args) {

		HashSet<Literal> litset1 = new HashSet<Literal>();
		Literal l1 = new Literal('A', true);
		Literal l2 = new Literal('B', true);
		litset1.add(l1);
		litset1.add(l2);

		HashSet<Literal> litset2 = new HashSet<Literal>();
		Literal l4 = new Literal('A', false);
		Literal l5 = new Literal('B', true);
		litset2.add(l4);
		litset2.add(l5);

		HashSet<Literal> litset3 = new HashSet<Literal>();
		Literal l6 = new Literal('A', true);
		Literal l7 = new Literal('B', false);
		litset3.add(l6);
		litset3.add(l7);
		
		HashSet<Literal> litset4 = new HashSet<Literal>();
		Literal l9 = new Literal('A', false);
		Literal l10 = new Literal('B', false);
		litset4.add(l9);
		litset4.add(l10);

		Klausel klausel1 = new Klausel(litset1);
		Klausel klausel2 = new Klausel(litset2);
		Klausel klausel3 = new Klausel(litset3);
//		Klausel klausel4 = new Klausel(litset4);

		knf = new HashSet<Klausel>();

		knf.add(klausel1);
		knf.add(klausel2);
		knf.add(klausel3);
//		knf.add(klausel4);
		
		System.out.println(knf);

		System.out.println(ausgabe(pResolvieren(knf)));
		System.out.println(knf);

	}

}
