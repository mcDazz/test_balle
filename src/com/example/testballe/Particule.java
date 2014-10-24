package com.example.testballe;

public class Particule {
    /* coefficient of restitution */
    private static final float COR = 0.7f;
     
    public float positionX;
    public float positionY;
    private float velociteX;
    private float velociteY;
     
    public void majPosition(float sx, float sy, float sz, long timestamp) {
        float dt = (System.nanoTime() - timestamp) / 1000000000.0f;
        velociteX += -sx * dt;
        velociteY += -sy * dt;
         
        positionX += velociteX * dt;
        positionY += velociteY * dt;
    }
     
    public void collisionCote(float boundHorizontal, float boundVertical) {
        if (positionX > boundHorizontal) {
            positionX = boundHorizontal;
            velociteX = -velociteX * COR;
        } else if (positionX < -boundHorizontal) {
            positionX = -boundHorizontal;
            velociteX = -velociteX * COR;
        }
        if (positionY > boundVertical) {
            positionY = boundVertical;
            velociteY = -velociteY * COR;
        } else if (positionY < -boundVertical) {
            positionY = -boundVertical;
            velociteY = -velociteY * COR;
        }
    }
}
