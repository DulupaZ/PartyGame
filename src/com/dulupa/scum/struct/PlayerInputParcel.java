package com.dulupa.scum.struct;



public class PlayerInputParcel {
    private float inputX;
    private float inputY;
    private Vector2 direction;

    public PlayerInputParcel(float inputX, float inputY, Vector2 direction) {
        this.inputX = inputX;
        this.inputY = inputY;
        this.direction = direction;
    }

    public static PlayerInputParcel parse(String input) {
    	String[] segments=input.split(",");
    	Vector2 direc=new Vector2(Float.parseFloat(segments[2]),Float.parseFloat(segments[3]));
    	return new PlayerInputParcel(Float.parseFloat(segments[0]),Float.parseFloat(segments[1]),direc);
    	
    }
    

    public float getInputX() {
        return inputX;
    }

    public void setInputX(float inputX) {
        this.inputX = inputX;
    }

    public float getInputY() {
        return inputY;
    }

    public void setInputY(float inputY) {
        this.inputY = inputY;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }



    public String toString() {
        return String.format("%f,%f,%f,%f\n",inputX,inputY,direction.x,direction.y);
    }
    
    
}
