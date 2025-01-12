/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mchdelaware.ranger;

import graycodes.CompareGray;
import graycodes.ConvertDoubleRangeToInt;
import graycodes.ConverterIntBGB;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import static java.awt.SystemColor.text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author marek
 */
public class DrawSight {

    private static final Logger logger
            = LoggerFactory.getLogger(DrawSight.class);

    RangerFrame rangerFrame;
    Graphics g;
    Graphics2D g2d;
    int panelWidth;
    int panelHeight;

    private double diameter;

    double xc;
    double yc;
    double angleR = 0;

    private int nbits = 10;
    ConvertDoubleRangeToInt convertDoubleRangeToInt;
    CompareGray compareGray;

    public DrawSight(RangerFrame rangerFrame, Graphics g,
            int panelWidth,
            int panelHeight,
            int ncodes
    ) {
        this.rangerFrame = rangerFrame;
        this.g = g;
        g2d = (Graphics2D) this.g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        xc = panelWidth / 2.0;
        yc = panelHeight / 2.0;
        diameter = panelHeight;
        this.nbits = ncodes;
        convertDoubleRangeToInt = new ConvertDoubleRangeToInt(nbits, 360.0);
        compareGray = new CompareGray(ncodes);
    }

    int drawSigth(double degY) {
        double radY = 2.0 * Math.PI / 360.0 * degY;
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke((float) 1.0));
        g2d.drawLine((int) xc, (int) yc, (int) (xc - diameter * Math.sin(radY)), (int) (yc - diameter * Math.cos(radY)));
        int sigthGray = drawGray(degY);
        return sigthGray;
    }

    int drawGray(double degY) {
        int sigthGray = ConverterIntBGB.convBinToGray(convertDoubleRangeToInt.convertDegYtoInt(degY));
        rangerFrame.getGrayCodeY().setText("" + sigthGray);
        return sigthGray;
    }

    int drawGun(double degY) {
        double radY = 2.0 * Math.PI / 360.0 * degY;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke((float) 3.0));
        g2d.drawLine((int) xc, (int) yc, (int) (xc - diameter * Math.sin(radY)), (int) (yc - diameter * Math.cos(radY)));
        int gunGray = drawGunGray(degY);
        return gunGray;
    }

    int drawGunGray(double degY) {
        int gunGray = ConverterIntBGB.convBinToGray(convertDoubleRangeToInt.convertDegYtoInt(degY));
        rangerFrame.getGrayCodeYG().setText("" + gunGray);
        return gunGray;
    }

    int drawDirection(int g1, int g2) {
        int dir = compareGray.grayCodeValueDiff(g1, g2);
        rangerFrame.getGunMoveDir().setText("" + dir);
        logger.debug("g1: " + g1 + " g2: " + g2 + " diff: " + dir);
        return dir;
    }
}
