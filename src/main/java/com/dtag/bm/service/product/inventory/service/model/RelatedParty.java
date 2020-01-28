package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class RelatedParty implements Serializable {

	private static final long serialVersionUID = 6943410552791058106L;
	
	@JsonProperty("id")
	private String id;
	@JsonProperty("href")
	private String href;
	@JsonProperty("role")
	private String role;
	@JsonProperty("name")
	private String name;
	@JsonProperty("ValidFor")
	private ValidFor validFor;
	@JsonProperty("@referredType")
	private String referredType;

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

	@JsonProperty("role")
	public String getRole() {
		return role;
	}

	@JsonProperty("role")
	public void setRole(String role) {
		this.role = role;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("ValidFor")
	public ValidFor getValidFor() {
		return validFor;
	}

	@JsonProperty("ValidFor")
	public void setValidFor(ValidFor validFor) {
		this.validFor = validFor;
	}

	@JsonProperty("@referredType")
	public String getReferredType() {
		return referredType;
	}

	@JsonProperty("@referredType")
	public void setReferredType(String referredType) {
		this.referredType = referredType;
	}

}