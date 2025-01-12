/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graycodes;

/**
 *
 * @author marek
 */
public class ConverterIntBGB {

    public static int convBinToGray(int b) {
        return b ^ (b >> 1);
    }

    public static int convGrayToBin(int g) {
        int res = g;

        while (g > 0) {
            g >>= 1;
            res ^= g;
        }

        return res;
    }


    // Driver code
    public static void main(String[] args) {
        int nmax = 64;
        for (int n = 0; n < nmax; n++) {
            int ng = convBinToGray(n);
            System.out.println(("Bin n=" + n + " gray=" + ng + " gray to bin=" + convGrayToBin(ng)));
        }
    }
}
