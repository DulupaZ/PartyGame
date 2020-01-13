package com.dulupa.scum.struct;

public class Vector2 {
    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 normalize(){
        return new Vector2(x/( (float)Math.sqrt(x*x+y*y)),y/( (float)Math.sqrt(x*x+y*y)));
    }

	@Override
	public String toString() {
		return String.format("%f,%f", x,y);
	}
    
    
}
