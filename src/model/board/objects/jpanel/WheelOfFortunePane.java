package model.board.objects.jpanel;

import model.board.objects.IGameObject;
import model.game.logic.GameLogic;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class WheelOfFortunePane extends JPanel {

    public double degreesT0 = 0;
    public double degreesT1 = 0;
    public double nextDegree = 0;
    private int imageSize = 60;
    private ArrayList<Image> images = new ArrayList<>();
    private float radius;
    public double speed = 0;
    private int t = 0;
    private int rotationNr = 1;
    private boolean wheelIsStarted = false;

    public double frictionConstant = 4;

    public WheelOfFortunePane(IGameObject sourceGameObject, JFrame frame) {
        loadImages();
        setup();

        // Wheel Event
        Timer timer = new Timer(40, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t++;
                nextDegree = getNextDegreeValue();
                // termination condition
                if (nextDegree - degreesT1 <= 0.2 && wheelIsStarted) {
                    GameLogic.executeInitSpecialsPhase(getSelectionNr(), sourceGameObject);
                    frame.dispose();
                    timer.stop();
                }
                // calculate new degrees
                degreesT0 = degreesT1;
                degreesT1 = nextDegree >= 0 ? nextDegree : 0;
                speed = degreesT1 - degreesT0;
                // monitor rotation
                if (rotationNr * 360 < degreesT1) {
                    rotationNr++;
                }
                repaint();
            }
        });
        timer.start();

        // Mouse Events
        addMouseListener(new MouseAdapter() {
            private LocalTime time1;
            private Point location1;

            @Override
            public void mousePressed(MouseEvent e) {
                if (!wheelIsStarted) {
                    location1 = e.getLocationOnScreen();
                    time1 = java.time.LocalTime.now();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!wheelIsStarted) {
                    long timeDiff = time1.until(java.time.LocalTime.now(), ChronoUnit.MILLIS);
                    double locationDiff = Math.sqrt(Math.pow(location1.x - e.getLocationOnScreen().x, 2) + Math.pow(location1.y - e.getLocationOnScreen().y, 2));
                    double newAcc = locationDiff / (double) (timeDiff / 100);
                    speed = 10 * Math.log(newAcc + 3);
                    // start wheel flag
                    wheelIsStarted = true;
                }
            }
        });
    }

    /**
     * Load all necessary images
     */
    private void loadImages() {
        try {
            images.add(ImageIO.read(new File("assets/MysterySelection/bed.png")));
            images.add(ImageIO.read(new File("assets/MysterySelection/bomb.png")));
            images.add(ImageIO.read(new File("assets/MysterySelection/dead.png")));
            images.add(ImageIO.read(new File("assets/MysterySelection/growing.png")));
            images.add(ImageIO.read(new File("assets/MysterySelection/moveFour.png")));
            images.add(ImageIO.read(new File("assets/MysterySelection/moveOutHome.png")));
            images.add(ImageIO.read(new File("assets/MysterySelection/plusToThree.png")));
            images.add(ImageIO.read(new File("assets/MysterySelection/switch.png")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Setup used variables
     */
    public void setup() {
        radius = getPreferredSize().height / 3;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int diameter = Math.min(getWidth(), getHeight());
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;

        // draw circle
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x, y, diameter, diameter);
        float innerDiameter = 0;

        // draw limiter and special images
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Point p = getPointOnCircle(degreesT1 + i * 45, (diameter / 2f) - (innerDiameter / 2));
            g2d.drawLine(x + diameter / 2, y + diameter / 2, p.x, p.y);
            points.add(p);
            // images
            Point newRotatePoint = getPointOnCircle(degreesT1 + i * 45 + 20, radius);
            Point newDispayingPoint = getDrawingPoint(newRotatePoint);
            g2d.drawImage(images.get(i), newDispayingPoint.x, newDispayingPoint.y, imageSize, imageSize, null);
        }

        // draw selector
        g2d.setColor(Color.RED);
        g2d.fillRect(getWidth() / 2 - 10 / 2, 0, 10, 25);

        g2d.dispose();
    }

    /**
     * Calculate the position of a point after a rotation
     *
     * @param degree degree of the rotation
     * @param radius radius of the circle
     * @return Point with the new coordinates
     */
    private Point getPointOnCircle(double degree, float radius) {
        int x = Math.round(getWidth() / 2);
        int y = Math.round(getHeight() / 2);
        double rads = Math.toRadians(degree - 90); // 0 becomes the top

        // Calculate the new point coords
        int xPosy = Math.round((float) (x + Math.cos(rads) * radius));
        int yPosy = Math.round((float) (y + Math.sin(rads) * radius));

        return new Point(xPosy, yPosy);
    }

    /**
     * Get the coordinates top left from the midpoint of an image to draw images correct
     *
     * @param p Midpoint coordinates of an image
     * @return Point with the new coordinates needed to draw the image to the right position
     */
    private Point getDrawingPoint(Point p) {
        return new Point(p.x - imageSize / 2, p.y - imageSize / 2);
    }

    /**
     * Calculate the next degree
     *
     * @return new degree of the next rotation
     */
    public double getNextDegreeValue() {
        double torque = radius * speed - speed * frictionConstant * radius;
        double inertiaMoment = 1 * Math.pow(radius, 2);
        double angularAcceleration = torque / inertiaMoment;
        return degreesT1 + angularAcceleration + speed;
    }

    /**
     * Get the number of the selected mystery
     *
     * @return the selected mystery number
     */
    public int getSelectionNr() {
        double deg = degreesT1 % 360;
        for (int i = 0; i < 8; i++) {
            if (deg <= (i + 1) * 360 / 8) {
                return 7 - i;
            }
        }
        return 0;
    }

    /**
     * Get the number of completed rotations
     *
     * @return number of rotations
     */
    public int getRotationNr() {
        return rotationNr;
    }

}