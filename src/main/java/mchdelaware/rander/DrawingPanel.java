/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mchdelaware.rander;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author marek
 */
public class DrawingPanel extends JPanel {

    RangerFrame rangerFrame;

    DrawGrayCircularCode dgcc;
    private DrawSight dSigth;

    int nbits = 10;
    int sigthGray;
    int gunGray;
    int dir;

    public DrawingPanel(RangerFrame rangerFrame) {
        this.rangerFrame = rangerFrame;
    }

    private void doDrawing(Graphics g, int panelWidth, int panelHeight) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawString("Java 2D", 50, 50);
        dgcc = new DrawGrayCircularCode(g, panelWidth, panelHeight, nbits);
        dgcc.drawGrayCircles();
        dSigth = new DrawSight(rangerFrame, g, panelWidth, panelHeight, nbits);
        gunGray = dSigth.drawGun(sigthDegG);
        sigthGray = dSigth.drawSigth(sigthDegY);
        dir = dSigth.drawDirection(sigthGray, gunGray);

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        doDrawing(g, panelWidth, panelHeight);
    }

    public DrawSight getdSigth() {
        return dSigth;
    }

    private double sigthDegY = 0.0;

    public double getSigthDegY() {
        return sigthDegY;
    }

    public void setSigthDegY(double sigthDegY) {
        this.sigthDegY = sigthDegY;
    }

    private double sigthDegG = 0.0;

    public double getSigthDegG() {
        return sigthDegG;
    }

    public void setSigthDegG(double sigthDegY) {
        this.sigthDegG = sigthDegY;
    }

}
