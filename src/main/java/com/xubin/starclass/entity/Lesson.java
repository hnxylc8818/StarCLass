package com.xubin.starclass.entity;

/**
 * Lesson entity. @author MyEclipse Persistence Tools
 */

public class Lesson implements java.io.Serializable {

	// Fields

	private Integer lid;
	private String lname;
	private String ldesc;
	private Integer needStars;
	private Integer nums;
	private Integer tid;
	private String thumbUrl;
	private Teacher teacher;

	// Constructors

	/** default constructor */
	public Lesson() {
	}

	/** minimal constructor */
	public Lesson(String lname, Integer tid) {
		this.lname = lname;
		this.tid = tid;
	}

	/** full constructor */
	public Lesson(String lname, String desc, Integer needStars, Integer nums,
			Integer tid, String thumbUrl) {
		this.lname = lname;
		this.ldesc = desc;
		this.needStars = needStars;
		this.nums = nums;
		this.tid = tid;
		this.thumbUrl = thumbUrl;
	}

	// Property accessors

	public Integer getLid() {
		return this.lid;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	public String getLname() {
		return this.lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getLdesc() {
		return ldesc;
	}

	public void setLdesc(String ldesc) {
		this.ldesc = ldesc;
	}

	public Integer getNeedStars() {
		return this.needStars;
	}

	public void setNeedStars(Integer needStars) {
		this.needStars = needStars;
	}

	public Integer getNums() {
		return this.nums;
	}

	public void setNums(Integer nums) {
		this.nums = nums;
	}

	public Integer getTid() {
		return this.tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getThumbUrl() {
		return this.thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

}