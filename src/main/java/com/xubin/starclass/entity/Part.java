package com.xubin.starclass.entity;

/**
 * Part entity. @author MyEclipse Persistence Tools
 */

public class Part implements java.io.Serializable {

	// Fields

	private Integer pid;
	private String name;
	private Integer uid;
	private String partUrl;

	// Constructors

	/** default constructor */
	public Part() {
	}

	/** full constructor */
	public Part(String name, Integer uid, String partUrl) {
		this.name = name;
		this.uid = uid;
		this.partUrl = partUrl;
	}

	// Property accessors

	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getPartUrl() {
		return this.partUrl;
	}

	public void setPartUrl(String partUrl) {
		this.partUrl = partUrl;
	}

}