package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductOrder implements Serializable {

	private static final long serialVersionUID = 5654979305364025427L;

	@JsonProperty("id")
	private String id;
	@JsonProperty("href")
	private String href;
	@JsonProperty("@referredType")
	private String referredType;
	@JsonProperty("orderItemId")
	private String orderItemId;
	@JsonProperty("orderItemAction")
	private String orderItemAction;

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("href")
	public String getHref() {
		return href;
	}

	@JsonProperty("href")
	public void setHref(String href) {
		this.href = href;
	}

	@JsonProperty("@referredType")
	public String getReferredType() {
		return referredType;
	}

	@JsonProperty("@referredType")
	public void setReferredType(String referredType) {
		this.referredType = referredType;
	}

	@JsonProperty("orderItemId")
	public String getOrderItemId() {
		return orderItemId;
	}

	@JsonProperty("orderItemId")
	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	@JsonProperty("orderItemAction")
	public String getOrderItemAction() {
		return orderItemAction;
	}

	@JsonProperty("orderItemAction")
	public void setOrderItemAction(String orderItemAction) {
		this.orderItemAction = orderItemAction;
	}

}