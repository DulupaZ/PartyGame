package com.dulupa.scum.struct;

public class Player {
	/*
	 * Player
	 * 		Vector2 position
	 * 		float speed;
	 * 		
	 * 		void moveBaseOn(PlayerInputParcel input);
	 * 			position.x=position.x+speed*input.direc.x;
	 * 			position.y=position.y+speed*input.direc.y;
	 * 		
	 * 		Vector2 getPosition()
	 * 			return position;
	 * */
	
	Vector2 position;
	float speed=10;
	

	public Player() {
		position=new Vector2(0,0);
	}
	
	//根據玩家的操作更新玩家位置
	public void moveBaseOn(PlayerInputParcel input) {
		position.x=position.x+speed*input.getDirection().x;
		position.y=position.y+speed*input.getDirection().y;
	}


	public Vector2 getPosition() {
		return position;
	}



	public void setPosition(Vector2 position) {
		this.position = position;
	}



	public float getSpeed() {
		return speed;
	}



	public void setSpeed(float speed) {
		this.speed = speed;
	}



	
}
