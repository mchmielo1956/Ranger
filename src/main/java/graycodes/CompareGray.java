/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graycodes;

import static graycodes.ConverterIntBGB.convBinToGray;
import static graycodes.ConverterIntBGB.convGrayToBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author marek
 */
public class CompareGray {

    private static final Logger logger
            = LoggerFactory.getLogger(CompareGray.class);

    private int nbits = 10;
    private AdderBinary adderBinary;

    public CompareGray(int nbits) {
        this.nbits = nbits;
        adderBinary = new AdderBinary(this.nbits);
        
    }

    public int compareGray(int gray1, int gray2) {
        if (gray1 == gray2) {
            return 0; // They are equal
        }

        int xorResult = gray1 ^ gray2; // Find the differing bits

        // Find the most significant set bit (MSB) in the XOR result
        int msbPosition = 0;
        while (xorResult > 0) {
            xorResult >>= 1;
            msbPosition++;
        }
        msbPosition--; // Adjust as we over-shift in the loop

        // Check the bit at the MSB position in both Gray codes
        int mask = 1 << msbPosition;
        if ((gray1 & mask) != 0) {
            return 1;
        } else {
            return -1;
        }// gray1 has 1 at the MSB position
    }

    public int grayCodeHammingDiff(int gray1, int gray2) {
        return Integer.bitCount(gray1 ^ gray2);
        /*
        int xorResult = gray1 ^ gray2;
        int diffCount = 0;
        while (xorResult > 0) {
            diffCount += xorResult & 1; // Check the least significant bit
            xorResult >>= 1; // Right shift
        }
        return diffCount;
         */
    }

    public  int grayCodeValueDiff(int g1, int g2) {
        if (g1 == g2) {
            return 0;
        }
        int b1 = convGrayToBin(g1);
        int b2 = convGrayToBin(g2);
        int db = adderBinary.adderSubstractor(b1,b2,true);
        //int dg = convBinToGray(db);
        logger.debug("grayCodeValueDiff g1: "+g1+" g2: "+g2+" b1: "+b1+" b2: "+b2+" db: "+db);
        return db;
    }

    public static void main(String[] args) {
        CompareGray compareGray = new CompareGray(10);
        // Examples (3-bit Gray codes)
        System.out.println("010 (2) > 011 (3): " + compareGray.compareGray(2, 3) + " DiffHamming: " + compareGray.grayCodeHammingDiff(2, 3) + " Diff: " + compareGray.grayCodeValueDiff(2, 3)); // 1); // true
        System.out.println("011 (3) > 010 (2): " + compareGray.compareGray(3, 2) + " DiffHamming: " + compareGray.grayCodeHammingDiff(3, 2) + " Diff: " + compareGray.grayCodeValueDiff(3, 2)); // 1); // true

        System.out.println("001 (1) > 011 (3): " + compareGray.compareGray(1, 3) + " DiffHamming: " + compareGray.grayCodeHammingDiff(1, 3) + " Diff: " + compareGray.grayCodeValueDiff(1, 3)); //; // true
        System.out.println("011 (3) > 001 (1): " + compareGray.compareGray(3, 1) + " DiffHamming: " + compareGray.grayCodeHammingDiff(3, 1) + " Diff: " + compareGray.grayCodeValueDiff(3, 1)); //; // true

        System.out.println("000 (0) > 011 (3): " + compareGray.compareGray(0, 3) + " DiffHamming: " + compareGray.grayCodeHammingDiff(0, 3) + " Diff: " + compareGray.grayCodeValueDiff(0, 3)); // 1); // true
        System.out.println("011 (3) > 000 (0): " + compareGray.compareGray(3, 0) + " DiffHamming: " + compareGray.grayCodeHammingDiff(3, 0) + " Diff: " + compareGray.grayCodeValueDiff(3, 0)); // 1); // true

        // Examples (4-bit Gray codes)
        System.out.println("1100 (12) > 1101 (13): " + compareGray.compareGray(12, 13) + " DiffHamming: " + compareGray.grayCodeHammingDiff(12, 13) + " Diff: " + compareGray.grayCodeValueDiff(12, 13)); // 1); // true
        System.out.println("1101 (13) > 1100 (12): " + compareGray.compareGray(13, 12) + " DiffHamming: " + compareGray.grayCodeHammingDiff(13, 12) + " Diff: " + compareGray.grayCodeValueDiff(13, 12)); // 1); // true

        System.out.println("1000 (8) > 1111 (15): " + compareGray.compareGray(8, 15) + " DiffHamming: " + compareGray.grayCodeHammingDiff(8, 15) + " Diff: " + compareGray.grayCodeValueDiff(8, 15)); // 1); // true
        System.out.println("1111 (15) > 1000 (8): " + compareGray.compareGray(15, 8) + " DiffHamming: " + compareGray.grayCodeHammingDiff(15, 8) + " Diff: " + compareGray.grayCodeValueDiff(15, 8)); // 1); // true

        System.out.println("0000 (0) > 0000 (0): " + compareGray.compareGray(0, 0) + " DiffHamming: " + compareGray.grayCodeHammingDiff(0, 0) + " Diff: " + compareGray.grayCodeValueDiff(0, 0)); // 1); // true

        // Examples (3-bit Gray codes)
        System.out.println("Value difference between 011 (Gray 3) and 000 (Gray 0): " + compareGray.grayCodeValueDiff(3, 0)); // 2
        System.out.println("Value difference between 011 (Gray 3) and 010 (Gray 2): " + compareGray.grayCodeValueDiff(3, 2)); // 1
        System.out.println("Value difference between 000 (Gray 0) and 111 (Gray 7): " + compareGray.grayCodeValueDiff(0, 7)); // 4
        System.out.println("Value difference between 001 (Gray 1) and 110 (Gray 6): " + compareGray.grayCodeValueDiff(1, 6)); // 4

        // Examples (4-bit Gray codes)
        System.out.println("Value difference between 1100 (Gray 12) and 0000 (Gray 0): " + compareGray.grayCodeValueDiff(12, 0)); // 8
        System.out.println("Value difference between 1100 (Gray 12) and 1111 (Gray 15): " + compareGray.grayCodeValueDiff(12, 15)); // 2
        for (int n = 0; n < 1; n++) {
            for (int m = 0; m < 7; m++) {
                int ng = convBinToGray(n);
                int mg = convBinToGray(m);
                int diffBinNinusM = n - m;
                int diffBinMinusN = m - n;
                int diffGrayNinusM = compareGray.compareGray(ng, mg);
                int diffGrayMinusN = compareGray.compareGray(mg, ng);
                System.out.println(" Bin m: " + m + " gra mg: " + mg + " Bin n: " + n + " gray ng: " + ng + " diff bin m-n: " + diffBinMinusN + " diff gray m-n: " + diffGrayMinusN + " diff bin n-m: " + diffBinNinusM + " diff gray n-m: " + diffGrayNinusM);
            }
        }
    }
}
