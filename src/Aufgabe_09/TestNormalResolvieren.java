package Aufgabe_09;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class TestNormalResolvieren {
	
	HashSet<Klausel> knf;

    @Before
    public void setUp(){
    	
//    	HashSet<Literal> litset1 = new HashSet<Literal>();
//		Literal l1 = new Literal('A', true);
//		Literal l2 = new Literal('B', true);
//		litset1.add(l1);
//		litset1.add(l2);
//
//		HashSet<Literal> litset2 = new HashSet<Literal>();
//		Literal l4 = new Literal('A', false);
//		Literal l5 = new Literal('B', true);
//		litset2.add(l4);
//		litset2.add(l5);
//
//		HashSet<Literal> litset3 = new HashSet<Literal>();
//		Literal l6 = new Literal('A', true);
//		Literal l7 = new Literal('B', false);
//		litset3.add(l6);
//		litset3.add(l7);
//		
//		HashSet<Literal> litset4 = new HashSet<Literal>();
//		Literal l9 = new Literal('A', false);
//		Literal l10 = new Literal('B', false);
//		litset4.add(l9);
//		litset4.add(l10);
//
//		Klausel klausel1 = new Klausel(litset1);
//		Klausel klausel2 = new Klausel(litset2);
//		Klausel klausel3 = new Klausel(litset3);
//		Klausel klausel4 = new Klausel(litset4);
//
//		this.knf = new HashSet<Klausel>();
//
//		knf.add(klausel1);
//		knf.add(klausel2);
//		knf.add(klausel3);
//		knf.add(klausel4);
    }
	
	@Test
	public void testKNF1() {
		
		/**(A,B),(!A,B),(A,!B),(!A,!B) = Box  */
		
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
		Klausel klausel4 = new Klausel(litset4);

		this.knf = new HashSet<Klausel>();

		knf.add(klausel1);
		knf.add(klausel2);
		knf.add(klausel3);
		knf.add(klausel4);
		
		assertEquals(new HashSet<Klausel>(),EinfacheResolution.resolvierenNormal(this.knf));
	}

	@Test
	public void testKNF2() {
		
		/**(A,B),(!B,C),(!C,D) = (A,D)  */
		
    	HashSet<Literal> litset1 = new HashSet<Literal>();
		Literal l1 = new Literal('A', true);
		Literal l2 = new Literal('B', true);
		litset1.add(l1);
		litset1.add(l2);

		HashSet<Literal> litset2 = new HashSet<Literal>();
		Literal l4 = new Literal('B', false);
		Literal l5 = new Literal('C', true);
		litset2.add(l4);
		litset2.add(l5);

		HashSet<Literal> litset3 = new HashSet<Literal>();
		Literal l6 = new Literal('C', false);
		Literal l7 = new Literal('D', true);
		litset3.add(l6);
		litset3.add(l7);
		
		HashSet<Literal> litset4 = new HashSet<Literal>();
		Literal l9 = new Literal('A', true);
		Literal l10 = new Literal('D', true);
		litset4.add(l9);
		litset4.add(l10);

		Klausel klausel1 = new Klausel(litset1);
		Klausel klausel2 = new Klausel(litset2);
		Klausel klausel3 = new Klausel(litset3);
		Klausel klausel4 = new Klausel(litset4);

		this.knf = new HashSet<Klausel>();

		knf.add(klausel1);
		knf.add(klausel2);
		knf.add(klausel3);
		
		HashSet<Klausel> knfTest = new HashSet<Klausel>();
		knfTest.add(klausel4);
				
		assertEquals(knfTest,EinfacheResolution.resolvierenNormal(this.knf));
	}
	
	@Test
	public void testKNF3() {
		
		/**(!A,!B,!C),(A,!D),(B,!C),(C,!F),(F) = !D*/
		
    	HashSet<Literal> litset1 = new HashSet<Literal>();
		Literal l1 = new Literal('A', false);
		Literal l2 = new Literal('B', false);
		Literal l3 = new Literal('C', false);
		litset1.add(l1);
		litset1.add(l2);
		litset1.add(l3);

		HashSet<Literal> litset2 = new HashSet<Literal>();
		Literal l4 = new Literal('A', true);
		Literal l5 = new Literal('D', false);
		litset2.add(l4);
		litset2.add(l5);

		HashSet<Literal> litset3 = new HashSet<Literal>();
		Literal l6 = new Literal('B', true);
		Literal l7 = new Literal('C', false);
		litset3.add(l6);
		litset3.add(l7);
		
		HashSet<Literal> litset4 = new HashSet<Literal>();
		Literal l9 = new Literal('C', true);
		Literal l10 = new Literal('F', false);
		litset4.add(l9);
		litset4.add(l10);
		
		HashSet<Literal> litset5 = new HashSet<Literal>();
		Literal l8 = new Literal('F', true);
		litset5.add(l8);
		
		HashSet<Literal> litset6 = new HashSet<Literal>();
		Literal l11 = new Literal('D', false);
		litset6.add(l11);

		Klausel klausel1 = new Klausel(litset1);
		Klausel klausel2 = new Klausel(litset2);
		Klausel klausel3 = new Klausel(litset3);
		Klausel klausel4 = new Klausel(litset4);
		Klausel klausel5 = new Klausel(litset5);
		Klausel klausel6 = new Klausel(litset6);

		this.knf = new HashSet<Klausel>();

		knf.add(klausel1);
		knf.add(klausel2);
		knf.add(klausel3);
		knf.add(klausel4);
		knf.add(klausel5);
		
		HashSet<Klausel> knfTest = new HashSet<Klausel>();
		knfTest.add(klausel6);
				
		assertEquals(knfTest,EinfacheResolution.resolvierenNormal(this.knf));
	}

}


//HashSet<Literal> litset1 = new HashSet<Literal>();
//Literal l1 = new Literal('A', false);
//Literal l2 = new Literal('B', false);
//Literal l3 = new Literal('C', false);
//Literal l4 = new Literal('D', false);
//litset1.add(l1);
//litset1.add(l2);
//litset1.add(l3);
//litset1.add(l4);
//
//HashSet<Literal> litset2 = new HashSet<Literal>();
//Literal l5 = new Literal('B', true);
//Literal l6 = new Literal('D', true);
//Literal l7 = new Literal('G', true);
//Literal l8 = new Literal('E', true);
//Literal l9 = new Literal('H', true);
//litset2.add(l5);
//litset2.add(l6);
//litset2.add(l7);
//litset2.add(l8);
//litset2.add(l9);
//
//HashSet<Literal> litset3 = new HashSet<Literal>();
//Literal l10 = new Literal('C', false);
////Literal l7 = new Literal('B', false);
////Literal l8 = new Literal('C', true);
//litset3.add(l10);
////litset3.add(l7);
////litset3.add(l8);
//
//HashSet<Literal> litset4 = new HashSet<Literal>();
//Literal l11 = new Literal('B', true);
////Literal l10 = new Literal('B', false);
////Literal l8 = new Literal('C', true);
//litset4.add(l11);
////litset4.add(l10);
////litset3.add(l8);
//
//Klausel klausel1 = new Klausel(litset1);
//Klausel klausel2 = new Klausel(litset2);
//Klausel klausel3 = new Klausel(litset3);
//Klausel klausel4 = new Klausel(litset4);
//
//knf = new HashSet<Klausel>();
//
//knf.add(klausel1);
//knf.add(klausel2);
//knf.add(klausel3);
//knf.add(klausel4);