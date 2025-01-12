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
public class ConvertDoubleRangeToInt {
        private static final Logger logger
            = LoggerFactory.getLogger(ConvertDoubleRangeToInt.class);

    private int nbits = 10;
    double range = 360.0;

    public ConvertDoubleRangeToInt(int nbits, double range) {
        this.nbits = nbits;
        this.range = range;
    }

    public static int powerOfTwoBitwise(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Exponent must be non-negative.");
        }
        if (n > 30) { // Avoid integer overflow
            throw new ArithmeticException("Exponent too large for integer result.");
        }
        return 1 << n;
    }
    
    public int convertDegYtoInt(double degY) {
        
        double vY = (degY+range/2.0)/range;
        int vInt = (int) (vY*powerOfTwoBitwise(nbits));
        return vInt;
    }

}
