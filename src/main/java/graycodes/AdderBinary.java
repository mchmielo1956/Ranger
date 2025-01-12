/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graycodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author marek
 */
public class AdderBinary {

    private static final Logger logger
            = LoggerFactory.getLogger(AdderBinary.class);

    private int nbits = 10;
    private int posmax = 511;
    private int valmax = 1023;

    public AdderBinary(int nbits) {
        this.nbits = nbits;
        posmax = 1;
        for (int n=0;n<nbits-1;n++) {
            posmax|=1<<n;
        }
    }

    class Doublet {

        public int s;
        public int c;

        public Doublet(int s, int c) {
            this.s = s;
            this.c = c;
        }

    }

    public Doublet halfAdder(int a, int b) {
        // https://www.101computing.net/binary-additions-using-logic-gates/
        int sum = a ^ b;
        int carry = a & b;

        Doublet s = new Doublet(sum, carry);
        //logger.debug("halfAdder a: " + a + " b: " + b + " s.s: " + s.s + " s.c: " + s.c);
        return s;
    }

    public Doublet fullAdder(int a, int b, int c) {
        // https://www.101computing.net/binary-additions-using-logic-gates/
        // https://www.101computing.net/binary-subtraction-using-logic-gates/
        Doublet s1 = halfAdder(a, b);
        Doublet s2 = halfAdder(c, s1.s);
        Doublet s = new Doublet(s2.s, (s1.c | s2.c));
        //logger.debug("fullAdder a: " + a + " b: " + b + " c: " + c + " s.s: " + s.s + " s.c: " + s.c);
        return s;
    }

    public int adder(int a, int b) {
        Doublet s = new Doublet(0, 0);
        int c = 0;
        for (int n = 0; n <= nbits; n++) {
            s = fullAdder(a, b, c);
            c = s.c << 1;
            //logger.debug("adder a: " + a + " b: " + b + " s.s: " + s.s + " s.c: " + s.c);
        }
        return s.s;
    }
    public int adderSubstractor(int a, int b, boolean substract) {
        int bx;
        int nones = 1;
        int nzeros = 0;
        for (int n=0;n<nbits;n++) {
            nones |= 1<<n;
        }
        if (substract) {
            bx = b^nones;
        } else {
            bx = b^nzeros;
        }
        //logger.debug("posmax: "+posmax+" a: "+a+" b: "+b+" bx: "+bx);
        int d = adder(a,bx);
        int sd = d;
        if (d>posmax) {
            sd = d-valmax;
        }
        return sd;
    }
    // Driver code

    public static void main(String[] args) {
        AdderBinary ab = new AdderBinary(10);
        int nmax = 9;
        int mmax = 3;
        for (int n = 0; n <= nmax; n++) {
            for (int m = 1; m <= mmax; m++) {
                System.out.println("n: " + n + " m: " + m + " sum n+m: " + ab.adderSubstractor(n, m, false));
            }
        }
        
        for (int n = 1; n <= nmax; n++) {
            for (int m = 0; m <= mmax; m++) {
                System.out.println("n: " + n + " m: " + m + " diff n-m: " + ab.adderSubstractor(n, m,true));
            }
        }
    }
}
