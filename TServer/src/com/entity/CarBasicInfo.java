package com.entity;

import java.util.Date;

import Bean.Bean;

public class CarBasicInfo implements Bean 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3386039880866489003L;

	/**
	 * 下面为将要从数据库输出的属性名，必须与数据库中的属性名完全一样
	 */
	private long id;
	private String mac_adress;
	private int car_num;
	private long group_id;
	private long road_id;//郭
	private boolean shunt;
	private Date create_time;
	private Date update_time;
	private String createTime;
	private String updateTime;
	private boolean is_used;
	private long sequence_num;
	private boolean sequence_flag;
	
	public boolean isUsed() {
	    return is_used;
	}


	public void setUsed(boolean is_used) {
	    this.is_used = is_used;
	}


	public long getSequence() {
	    return sequence_num;
	}


	public void setSequence(long sequence_num) {
	    this.sequence_num = sequence_num;
	}


	

	
	@Override
	public String toString() {
		return "CarInfo [id=" + id + ", macAdress=" + mac_adress+ ", carNum=" + car_num 
				+ ", groupid=" + group_id +"road_id"+road_id+ "createTime="+createTime+"updateTime="+updateTime+"]";
	}
	
	
	public Date getCreate_time() {
		return create_time;
	}



	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}



	public Date getUpdate_time() {
		return update_time;
	}



	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}



	public String getCreateTime() {
		return createTime;
	}



	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}



	public String getUpdateTime() {
		return updateTime;
	}



	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}



	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMac_adress() {
		return mac_adress;
	}
	public void setMac_adress(String mac_adress) {
		this.mac_adress = mac_adress;
	}
	public int getCar_num() {
		return car_num;
	}
	public void setCar_num(int car_num) {
		this.car_num = car_num;
	}
	public long getGroup_id() {
		return group_id;
	}


	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public long getRoad_id() {
		return road_id;
	}


	public void setRoad_id(long road_id) {
		this.road_id = road_id;
	}


	public boolean isShunt() {
		return shunt;
	}


	public void setShunt(boolean shunt) {
		this.shunt = shunt;
	}


	public boolean isSequenceFlag() {
	    return sequence_flag;
	}


	public void setSequenceFlag(boolean sequence_flag) {
	    this.sequence_flag = sequence_flag;
	}

}
