package Aufgabe_09;

import java.util.HashSet;
import java.util.Iterator;

public class Resolution {

	public static HashSet<Klausel> knf;
	public static boolean resolviert = false;
	
	public static HashSet<Klausel> pResolvieren(HashSet<Klausel> uebergebenerKNF) {
				
		HashSet<Klausel> verfuegbareKlauseln = new HashSet<Klausel>(tiefeKopieErstellen(uebergebenerKNF));
		HashSet<Klausel> zuResolvierendeKlauseln = new HashSet<Klausel>(tiefeKopieErstellen(uebergebenerKNF));
		
		
		/** angepasster Algorithmus für P-Resolution*/
		
		
		return zuResolvierendeKlauseln;
		
		
	}
	
	/**Methode prueft, ob Hornklausel vorliegt.*/
	public static boolean istHornKlausel(HashSet<Literal> zuPruefendeKlausel) {
		int anzahlPositiveLiterale = 0;
		for(Literal l : zuPruefendeKlausel) {
			if(l.getWahrheitswert()) {
				++anzahlPositiveLiterale;
			}
		}
		if(anzahlPositiveLiterale <= 1) {
			return true;
		} else {
			return false;
		}
			
	}
	
	/** Methode prueft, ob Hornformel vorliegt.*/
	public static boolean istHornFormel(HashSet<Klausel> zupruefendeKlauseln) {
		for(Klausel k : zupruefendeKlauseln) {
			if(!istHornKlausel(k.getLiterale())) {
				return false;
			}
		}
		return true;
	}
	
	
	
	/**Methode prüft, ob Klausel für Einheitsklausel höchstens ein Literal enthält.*/
	public static boolean istEinheitsklausel(HashSet<Literal> zuPruefendeKlausel)  {
		if(zuPruefendeKlausel.size() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**Methode prüft, ob Klausel negativ ist*/
	public static boolean istNegativeKlausel(HashSet<Literal> zuPruefendeKlausel) {
		for(Literal l: zuPruefendeKlausel) {
			if (l.getWahrheitswert()) {
				return false;
			}
		}
		return true;
	}
	
	/**Methode prüft, ob Klausel positiv ist*/
	public static boolean istPositiveKlausel(HashSet<Literal> zuPruefendeKlausel) {
		for(Literal l: zuPruefendeKlausel) {
			if (!l.getWahrheitswert()) {
				return false;
			}
		}
		return true;
	}

	public static HashSet<Klausel> resolvierenPrimitiv(HashSet<Klausel> uebergebenerKNF) {

		HashSet<Klausel> verfuegbareKlauseln = new HashSet<Klausel>(tiefeKopieErstellen(uebergebenerKNF));
		HashSet<Klausel> zuResolvierendeKlauseln = new HashSet<Klausel>(tiefeKopieErstellen(uebergebenerKNF));
		
		do {
			
			resolviert = false;
								
			/** Suchen von komplementaeren Klauseln */
			
			Iterator<Klausel> verfuegIter = verfuegbareKlauseln.iterator();
			while (verfuegIter.hasNext() && (resolviert == false)) {
				Klausel k1 = verfuegIter.next(); 	    
				
				Iterator<Klausel> zuResIter = zuResolvierendeKlauseln.iterator();
				while (zuResIter.hasNext() && (resolviert == false)) {
					Klausel k2 = zuResIter.next();
					
					for (Literal l1 : k1.getLiterale()) {
						for (Literal l2 : k2.getLiterale()) {

							if (istResolvierbar(k1, k2, l1, l2)) {

								resolviert = true;

								Klausel neueKlausel = new Klausel(resolvierteLiteraleZusammen(zuResolvierendeKlauseln, k1, k2, l1, l2));
								
								if (!neueKlausel.getLiterale().isEmpty()) {verfuegbareKlauseln.add(neueKlausel);};
								
								if (!neueKlausel.getLiterale().isEmpty()) {zuResolvierendeKlauseln.add(neueKlausel);};
								
								zuResolvierendeKlauseln.remove(k1);
								zuResolvierendeKlauseln.remove(k2);
							}
						}
					}
				}

			}
		} while (resolviert == true && !(zuResolvierendeKlauseln.isEmpty()) );

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

//	private static String ausgabe(HashSet<Klausel> ergebnisKlausel) {
//		StringBuilder ausgabeString = new StringBuilder();
//		Object knf = ergebnisKlausel.toArray();
//		if (ergebnisKlausel.isEmpty()) {
////			return ausgabeString = "{ } --> erfuellbar!";
//		} else {
//			ausgabeString.append('{');
//			if (ergebnisKlausel.size() != 0) {
//				for (int i = 0; i < knf.length; i++) {
//					ausgabeString.append(ausgabeString[i].toString()).append(", ");
//					
//				}
//			}
//			
//		}
//		
//				
//		if (lit.length != 0) {
//		for (int i = 0; i < lit.length-1; i++) {
//			builder.append(lit[i].toString()).append(", ");
//		}
//		builder.append(lit[lit.length-1]);
//		} else {
//			builder.append(' ');
//		}
//		builder.append('}');
//		return builder.toString();
//	
//		
//		
//	}

	/** prueft, ob 2 Literale resolvierbar sind */
	private static boolean istResolvierbar(Klausel klauselA, Klausel klauselB, Literal literalA, Literal literalB) {
		return ((literalA.getBezeichner() == literalB.getBezeichner())
				&& (literalA.getWahrheitswert() != literalB.getWahrheitswert()) && !(klauselA.equals(klauselB)));
	}

	public static void main(String[] args) {

		HashSet<Literal> litset1 = new HashSet<Literal>();
		Literal l1 = new Literal('A', true);
		Literal l2 = new Literal('B', true);
//		Literal l3 = new Literal('B', false);
		litset1.add(l1);
		litset1.add(l2);
//		litset1.add(l3);

		HashSet<Literal> litset2 = new HashSet<Literal>();
		Literal l4 = new Literal('A', false);
		Literal l5 = new Literal('B', true);
		
		litset2.add(l4);
		litset2.add(l5);

		HashSet<Literal> litset3 = new HashSet<Literal>();
		Literal l6 = new Literal('A', true);
		Literal l7 = new Literal('B', false);
//		Literal l8 = new Literal('C', true);
		litset3.add(l6);
		litset3.add(l7);
//		litset3.add(l8);
		
		HashSet<Literal> litset4 = new HashSet<Literal>();
		Literal l9 = new Literal('A', false);
		Literal l10 = new Literal('B', false);
//		Literal l8 = new Literal('C', true);
		litset4.add(l9);
		litset4.add(l10);
//		litset3.add(l8);

		Klausel klausel1 = new Klausel(litset1);
		Klausel klausel2 = new Klausel(litset2);
		Klausel klausel3 = new Klausel(litset3);
		Klausel klausel4 = new Klausel(litset4);

		knf = new HashSet<Klausel>();

		knf.add(klausel1);
		knf.add(klausel2);
		knf.add(klausel3);
		knf.add(klausel4);
		
		System.out.println(knf);

		System.out.println("Das Ende: " + resolvierenPrimitiv(knf));
		System.out.println(knf);

	}

}
