package com.paul.club.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Club implements Serializable{
	private String id;
	private String name;
	private String principal;
	private String statement;
	private double interest;
	private String wechat;
	private String nameInItaly;
	private Set<DayBill> bills;
	private double extra;
	private int state;
	@Column(nullable=false)
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public double getExtra() {
		return extra;
	}
	public void setExtra(double extra) {
		this.extra = extra;
	}
	@OneToMany(cascade={CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.MERGE},fetch=FetchType.LAZY,mappedBy="club")
	public Set<DayBill> getBills() {
		return bills;
	}
	public void setBills(Set<DayBill> bills) {
		this.bills = bills;
	}
	@Id@Column(length=12)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable=false)
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	@Column(nullable=false)
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	@Column(nullable=false)
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	@Column(nullable=false)
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getNameInItaly() {
		return nameInItaly;
	}
	public void setNameInItaly(String nameInItaly) {
		this.nameInItaly = nameInItaly;
	}
}
