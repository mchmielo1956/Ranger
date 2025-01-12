/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mchdelaware.rander;

import org.slf4j.LoggerFactory;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;

import java.awt.geom.Ellipse2D;
import org.slf4j.Logger;

/**
 *
 * @author marek
 */
public class DrawGrayCircularCode {

    private static final Logger logger
            = LoggerFactory.getLogger(DrawGrayCircularCode.class);

    Graphics g;
    Graphics2D g2d;
    int panelWidth;
    int panelHeight;

    private double diameter;
    private int ncodes = 10;
    int nshift = ncodes - 3;
    int maxPaths = 2;
    Arc2D[][] arcs;

    public DrawGrayCircularCode(Graphics g,
            int panelWidth,
            int panelHeight,
            int ncodes
    ) {
        this.g = g;
        g2d = (Graphics2D) this.g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        diameter = panelHeight;
        this.ncodes = ncodes;        
        if (nshift < 0) {
            nshift = 0;
        }
        maxPaths = 2 << nshift;
        //logger.debug("DrawGrayCircularCode ncodes=" + ncodes + " nshift=" + nshift + " maxPaths=" + maxPaths);
        arcs = new Arc2D[ncodes][maxPaths];
    }

    void drawGrayCircles() {
        // Calculate the center of the panel
        int x = (int) ((panelWidth - diameter) / 2.0);
        int y = (int) ((panelHeight - diameter) / 2.0);
        Ellipse2D.Double circle;

        Ellipse2D.Double circleOuter = new Ellipse2D.Double(x, y, diameter, diameter);
        g2d.setColor(Color.BLUE);
        g2d.draw(circleOuter); // Or g2d.fill(circle) to fill

        double a_start = 90.0;
        double a_length = 180.0;
        double diam = 0;
        int nempty = ncodes;
        double diamStep = diameter / (ncodes + nempty);
        double diam0 = diamStep * nempty;
        double lineStep = 3.0;

        //Ellipse2D.Double circleInnner = new Ellipse2D.Double((int) ((panelWidth - diam0) / 2.0), (int) ((panelHeight - diam0) / 2.0), diam0, diam0);
        //g2d.setColor(Color.BLUE);
        //g2d.draw(circleInnner);
        double xc = panelWidth / 2.0;
        double yc = panelHeight / 2.0;
        double angleR = 0;
        int km = 16;
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke((float) 1.0));
        for (int k = 0; k < km; k++) {
            angleR = k * 2.0 * Math.PI / km;
            g2d.drawLine((int) xc, (int) yc, (int) (xc + diameter * Math.cos(angleR)), (int) (yc + diameter * Math.sin(angleR)));
        }

        int n = 0;
        int nn = 0;

        //logger.debug("n=" + n);
        diam = diam0 + diamStep * n;
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke((float) 1.0));
        circle = new Ellipse2D.Double((int) ((panelWidth - diam) / 2.0), (int) ((panelHeight - diam) / 2.0), diam, diam);
        g2d.setColor(Color.BLUE);
        g2d.draw(circle);
        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke((float) (lineStep)));
        arcs[n][nn] = new Arc2D.Double((panelWidth - diam) / 2, (panelHeight - diam) / 2, diam, diam, a_start, a_length, Arc2D.OPEN);
        g2d.draw(arcs[n][nn]);
        //logger.debug("Inserted Arc2D to crcs at n=" + n + " nn=" + nn);

        n++;
        diam = diam0 + diamStep * n;
        a_length = a_length / n;
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke((float) 1.0));
        circle = new Ellipse2D.Double((int) ((panelWidth - diam) / 2.0), (int) ((panelHeight - diam) / 2.0), diam, diam);
        g2d.setColor(Color.BLUE);
        g2d.draw(circle);
        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke((float) (lineStep)));
        a_start = 180.0;
        arcs[n][nn] = new Arc2D.Double((panelWidth - diam) / 2, (panelHeight - diam) / 2, diam, diam, a_start, a_length, Arc2D.OPEN);
        g2d.draw(arcs[n][nn]);
        //logger.debug("Inserted Arc2D to crcs at n=" + n + " nn=" + nn);

        int imax = 1;
        for (int j = 2; j < ncodes; j++) {
            //logger.debug("j=" + j);
            diam = diam0 + diamStep * j;
            a_length = a_length / 2;
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke((float) 1.0));
            circle = new Ellipse2D.Double((int) ((panelWidth - diam) / 2.0), (int) ((panelHeight - diam) / 2.0), diam, diam);
            g2d.setColor(Color.BLUE);
            g2d.draw(circle);
            g2d.setColor(Color.black);
            g2d.setStroke(new BasicStroke((float) (lineStep)));

            for (int i = 0; i < imax; i++) {
                int jm = j - 1;
                //logger.debug("Getting arcs getAngleStart at j-1=" + jm + " i=" + i);
                a_start = arcs[j - 1][i].getAngleStart() - arcs[j - 1][i].getAngleExtent() / 4;
                arcs[j][2 * i] = new Arc2D.Double((panelWidth - diam) / 2, (panelHeight - diam) / 2, diam, diam, a_start, a_length, Arc2D.OPEN);
                g2d.draw(arcs[j][2 * i]);
                int id = 2 * i;
                //logger.debug("Inserted Arc2D to crcs at j=" + j + " 2*i=" + id);

                //logger.debug("Getting arcs getAngleStart - getAngleExtent at j-1=" + jm + " i=" + i);
                double a_end = arcs[j - 1][i].getAngleStart() - arcs[j - 1][i].getAngleExtent();
                a_start = a_end - arcs[j - 1][i].getAngleExtent() / 4;
                arcs[j][2 * i + 1] = new Arc2D.Double((panelWidth - diam) / 2, (panelHeight - diam) / 2, diam, diam, a_start, a_length, Arc2D.OPEN);
                g2d.draw(arcs[j][2 * i + 1]);
                int idp = 2 * i + 1;
                //logger.debug("Inserted Arc2D to crcs at j=" + j + " 2*i+1=" + idp);

            }
            imax *= 2;

        }

    }

}
