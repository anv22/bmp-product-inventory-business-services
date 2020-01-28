package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductSpecification implements Serializable {

	private static final long serialVersionUID = 1990366911912771534L;

	@JsonProperty("id")
	private String id;
	@JsonProperty("href")
	private String href;
	@JsonProperty("version")
	private String version;
	@JsonProperty("name")
	private String name;
	@JsonProperty("@referredType")
	private String referredType;
	@JsonProperty("TargetResourceSchema")
	private TargetResourceSchema targetResourceSchema;

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

	@JsonProperty("version")
	public String getVersion() {
		return version;
	}

	@JsonProperty("version")
	public void setVersion(String version) {
		this.version = version;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("@referredType")
	public String getReferredType() {
		return referredType;
	}

	@JsonProperty("@referredType")
	public void setReferredType(String referredType) {
		this.referredType = referredType;
	}

	@JsonProperty("TargetResourceSchema")
	public TargetResourceSchema getTargetResourceSchema() {
		return targetResourceSchema;
	}

	@JsonProperty("TargetResourceSchema")
	public void setTargetResourceSchema(TargetResourceSchema targetResourceSchema) {
		this.targetResourceSchema = targetResourceSchema;
	}

}