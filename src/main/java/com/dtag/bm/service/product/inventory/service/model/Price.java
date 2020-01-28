package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Price implements Serializable {

	private static final long serialVersionUID = -4188360700894077598L;
	
	@JsonProperty("TaxIncludedAmount")
	private TaxIncludedAmount taxIncludedAmount;
	@JsonProperty("DutyFreeAmount")
	private DutyFreeAmount dutyFreeAmount;
	@JsonProperty("percentage")
	private Double percentage;
	@JsonProperty("taxRate")
	private Integer taxRate;
	@JsonProperty("@type")
	private String type;
	@JsonProperty("@schemaLocation")
	private String schemaLocation;

	@JsonProperty("TaxIncludedAmount")
	public TaxIncludedAmount getTaxIncludedAmount() {
		return taxIncludedAmount;
	}

	@JsonProperty("TaxIncludedAmount")
	public void setTaxIncludedAmount(TaxIncludedAmount taxIncludedAmount) {
		this.taxIncludedAmount = taxIncludedAmount;
	}

	@JsonProperty("DutyFreeAmount")
	public DutyFreeAmount getDutyFreeAmount() {
		return dutyFreeAmount;
	}

	@JsonProperty("DutyFreeAmount")
	public void setDutyFreeAmount(DutyFreeAmount dutyFreeAmount) {
		this.dutyFreeAmount = dutyFreeAmount;
	}

	@JsonProperty("percentage")
	public Double getPercentage() {
		return percentage;
	}

	@JsonProperty("percentage")
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	@JsonProperty("taxRate")
	public Integer getTaxRate() {
		return taxRate;
	}

	@JsonProperty("taxRate")
	public void setTaxRate(Integer taxRate) {
		this.taxRate = taxRate;
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

}