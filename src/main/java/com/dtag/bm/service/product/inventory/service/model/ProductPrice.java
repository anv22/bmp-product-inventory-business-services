package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductPrice implements Serializable {

	private static final long serialVersionUID = -103436383956817649L;

	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("priceType")
	private String priceType;
	@JsonProperty("recurringChargePeriod")
	private String recurringChargePeriod;
	@JsonProperty("unitOfMeasure")
	private String unitOfMeasure;
	@JsonProperty("@type")
	private String type;
	@JsonProperty("@schemaLocation")
	private String schemaLocation;
	@JsonProperty("BillingAccountRef")
	private BillingAccount billingAccountRef;
	@JsonProperty("Price")
	private Price price;
	@JsonProperty("PriceAlteration")
	private List<PriceAlteration> priceAlteration = null;

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("priceType")
	public String getPriceType() {
		return priceType;
	}

	@JsonProperty("priceType")
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	@JsonProperty("recurringChargePeriod")
	public String getRecurringChargePeriod() {
		return recurringChargePeriod;
	}

	@JsonProperty("recurringChargePeriod")
	public void setRecurringChargePeriod(String recurringChargePeriod) {
		this.recurringChargePeriod = recurringChargePeriod;
	}

	@JsonProperty("unitOfMeasure")
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	@JsonProperty("unitOfMeasure")
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	@JsonProperty("@type")
	public String getType() {
		return type;
	}

	@JsonProperty("@type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("@schemaLocation")
	public String getSchemaLocation() {
		return schemaLocation;
	}

	@JsonProperty("@schemaLocation")
	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	@JsonProperty("BillingAccountRef")
	public BillingAccount getBillingAccountRef() {
		return billingAccountRef;
	}

	@JsonProperty("BillingAccountRef")
	public void setBillingAccountRef(BillingAccount billingAccountRef) {
		this.billingAccountRef = billingAccountRef;
	}

	@JsonProperty("Price")
	public Price getPrice() {
		return price;
	}

	@JsonProperty("Price")
	public void setPrice(Price price) {
		this.price = price;
	}

	@JsonProperty("PriceAlteration")
	public List<PriceAlteration> getPriceAlteration() {
		return priceAlteration;
	}

	@JsonProperty("PriceAlteration")
	public void setPriceAlteration(List<PriceAlteration> priceAlteration) {
		this.priceAlteration = priceAlteration;
	}

}