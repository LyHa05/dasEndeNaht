package Aufgabe_09;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class ResolutionP {

	public static HashSet<Klausel> knf;
	public static boolean resolviert = false;
	public static int zaehler1 = 0;
	public static int zaehler2 = 0;
	

	public static HashSet<Klausel> pResolvieren(HashSet<Klausel> uebergebenerKNF) {

		HashSet<Klausel> verfuegbareKlauseln = new HashSet<Klausel>(tiefeKopieErstellen(uebergebenerKNF));
		HashSet<Klausel> zuResolvierendeKlauseln = new HashSet<Klausel>(tiefeKopieErstellen(uebergebenerKNF));

		do {

			resolviert = false;

			/** Klauseln nach positiv und nicht positiv aufteilen */

			HashSet<Klausel> positiveKlauselnVerfuegbar = new HashSet<Klausel>(
					(aufteilungKlauseln(verfuegbareKlauseln)).get(0));
			HashSet<Klausel> nichtPositiveKlauselnVerfuegbar = new HashSet<Klausel>(
					(aufteilungKlauseln(verfuegbareKlauseln)).get(1));

			HashSet<Klausel> positiveKlauselnZuResolvieren = new HashSet<Klausel>(
					(aufteilungKlauseln(zuResolvierendeKlauseln)).get(0));
			HashSet<Klausel> nichtPositiveKlauselnZuResolvieren = new HashSet<Klausel>(
					(aufteilungKlauseln(zuResolvierendeKlauseln)).get(1));

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

								Klausel neueKlausel = new Klausel(
										resolvierteLiteraleZusammen(zuResolvierendeKlauseln, k1, k2, l1, l2));

								if (!neueKlausel.getLiterale().isEmpty()) {
									verfuegbareKlauseln.add(neueKlausel);
								}
								;

								if (!neueKlausel.getLiterale().isEmpty()) {
									zuResolvierendeKlauseln.add(neueKlausel);
								}
								;

								zuResolvierendeKlauseln.remove(k1);
								zuResolvierendeKlauseln.remove(k2);
								
								++zaehler2;
							}
						}
					}
				}

			}
		} while (resolviert == true && !(zuResolvierendeKlauseln.isEmpty()));

		return zuResolvierendeKlauseln;

	}

	
	/** Methode resolviert Klauseln*/
	public static ArrayList<HashSet<Klausel>> pResolvieren(HashSet<Klausel> verfKlauseln, HashSet<Klausel> zuResolvKlauseln) {

		ArrayList<HashSet<Klausel>> resolvErgebnis = new ArrayList<HashSet<Klausel>>();
		
		Iterator<Klausel> verfuegIter = verfKlauseln.iterator();
		while (verfuegIter.hasNext() && (resolviert == false)) {
			Klausel k1 = verfuegIter.next();

			Iterator<Klausel> zuResIter = zuResolvKlauseln.iterator();
			while (zuResIter.hasNext() && (resolviert == false)) {
				Klausel k2 = zuResIter.next();

				for (Literal l1 : k1.getLiterale()) {
					for (Literal l2 : k2.getLiterale()) {

						if (istResolvierbar(k1, k2, l1, l2)) {

							resolviert = true;

							Klausel neueKlausel = new Klausel(
									resolvierteLiteraleZusammen(zuResolvKlauseln, k1, k2, l1, l2));

							if (!neueKlausel.getLiterale().isEmpty()) {
								verfKlauseln.add(neueKlausel);
							};

							if (!neueKlausel.getLiterale().isEmpty()) {
								zuResolvKlauseln.add(neueKlausel);
							};

							zuResolvKlauseln.remove(k1);
							zuResolvKlauseln.remove(k2);
						}
					}
				}
			}

		}
		
		resolvErgebnis.add(verfKlauseln);
		resolvErgebnis.add(zuResolvKlauseln);

		return resolvErgebnis;

	}

	/**
	 * Methode teilt Klauseln in positiv und nicht positive Klauseln auf. Erstes
	 * Element enthält positiven Klauseln.
	 */
	public static ArrayList<HashSet<Klausel>> aufteilungKlauseln(HashSet<Klausel> aufzuteilendeKlauseln) {
		ArrayList<HashSet<Klausel>> aufgeteilteKlauseln = new ArrayList<HashSet<Klausel>>();

		HashSet<Klausel> positiveKlauseln = new HashSet<Klausel>();
		HashSet<Klausel> nichtPositiveKlauseln = new HashSet<Klausel>();

		for (Klausel k : aufzuteilendeKlauseln) {
			if (istPositiveKlausel(k.getLiterale())) {
				positiveKlauseln.add(k);
			} else {
				nichtPositiveKlauseln.add(k);
			}
		}

		aufgeteilteKlauseln.add(positiveKlauseln);
		aufgeteilteKlauseln.add(nichtPositiveKlauseln);

		return aufgeteilteKlauseln;

	}

	/** Methode prüft, ob Klausel positiv ist */
	public static boolean istPositiveKlausel(HashSet<Literal> zuPruefendeKlausel) {
		for (Literal l : zuPruefendeKlausel) {
			if (!l.getWahrheitswert()) {
				return false;
			}
		}
		return true;
	}

	private static Klausel resolvierteLiteraleZusammen(HashSet<Klausel> nochZuResolvieren, Klausel k1, Klausel k2,
			Literal l1, Literal l2) {

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

	private static String ausgabe(HashSet<Klausel> ergebnisKlausel) {
		StringBuilder builder = new StringBuilder();
		Object[] knf = ergebnisKlausel.toArray();
		builder.append('{');

		if (knf.length != 0) {
			for (int i = 0; i < knf.length - 1; i++) {
				builder.append(knf[i].toString());
			}
			builder.append(knf[knf.length - 1]);
		} else {
			builder.append(' ');
		}
		builder.append('}');

		return builder.toString();
	}

	/** prueft, ob 2 Literale resolvierbar sind */
	private static boolean istResolvierbar(Klausel klauselA, Klausel klauselB, Literal literalA, Literal literalB) {
		return ((literalA.getBezeichner() == literalB.getBezeichner())
				&& (literalA.getWahrheitswert() != literalB.getWahrheitswert()) && !(klauselA.equals(klauselB))
				&& (istPositiveKlausel(klauselA.getLiterale()) || istPositiveKlausel(klauselB.getLiterale())));
	}

	public static void main(String[] args) {

		HashSet<Literal> litset1 = new HashSet<Literal>();
		Literal l1 = new Literal('A', true);
		Literal l2 = new Literal('B', true);
		// Literal l3 = new Literal('B', false);
		litset1.add(l1);
		litset1.add(l2);
		// litset1.add(l3);

		HashSet<Literal> litset2 = new HashSet<Literal>();
		Literal l4 = new Literal('A', false);
		Literal l5 = new Literal('B', true);

		litset2.add(l4);
		litset2.add(l5);

		HashSet<Literal> litset3 = new HashSet<Literal>();
		Literal l6 = new Literal('A', true);
		Literal l7 = new Literal('B', false);
		// Literal l8 = new Literal('C', true);
		litset3.add(l6);
		litset3.add(l7);
		// litset3.add(l8);

//		HashSet<Literal> litset4 = new HashSet<Literal>();
//		Literal l9 = new Literal('A', false);
//		Literal l10 = new Literal('B', false);
		// Literal l8 = new Literal('C', true);
//		litset4.add(l9);
//		litset4.add(l10);
		// litset3.add(l8);

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

		System.out.println("Das Ende: " + ausgabe(pResolvieren(knf)));
		System.out.println(knf);

		System.out.println(zaehler1);
		System.out.println(zaehler2);

	}

}
