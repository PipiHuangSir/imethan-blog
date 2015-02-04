package com.the3.entity.todo;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.the3.base.entity.BaseEntity;

@Entity
@Table(name="imethan_sys_todo")
public class Todo extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private TodoType type = TodoType.IMETHAN;//类型
	private String content;//待办内容
	private boolean finish;//是否完成
	private Integer orderNo;//序号
	@Transient
	private Integer nextOrderNo;//下一条记录排序
	@Transient
	private Integer previousOrderNo;//上一条记录排序
	
	public Integer getNextOrderNo() {
		return nextOrderNo;
	}
	public void setNextOrderNo(Integer nextOrderNo) {
		this.nextOrderNo = nextOrderNo;
	}
	public Integer getPreviousOrderNo() {
		return previousOrderNo;
	}
	public void setPreviousOrderNo(Integer previousOrderNo) {
		this.previousOrderNo = previousOrderNo;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public boolean isFinish() {
		return finish;
	}
	public void setFinish(boolean finish) {
		this.finish = finish;
	}
	public TodoType getType() {
		return type;
	}
	public void setType(TodoType type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
