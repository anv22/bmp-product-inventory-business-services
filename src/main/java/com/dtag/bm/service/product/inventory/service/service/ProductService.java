package com.dtag.bm.service.product.inventory.service.service;

import java.util.HashMap;
import java.util.List;

import com.dtag.bm.service.product.inventory.service.model.Product;
import com.dtag.bm.service.product.inventory.service.model.ProductRequest;

public interface ProductService {

	public Product createProductInventory(Product request);

	public Product updateProductInventory(Product request);

	public List<Product> productsDetails(String filterKey, String filterValue);

	public List<Product> CustomerProductDetails(String filterKey, String filterValue, String cutomerId);

	public List<Product> retrieveProdDetailsByType(String token, String cutomerId);

	public List<Product> productsDetailsByRefferedType(String type, String relatedPartyRefId, String status,
			String relatedPartyreferredType);

	public List<Product> prodDetailsByIdandType(String token, String filterKey, String filterValue, String customerId);

	public List<Product> prodDetailsByIdandTypeTest(String token, String filterKey, String filterValue,
			String customerId);

	public List<Product> prodSliceDetails(String custId);

	public List<ProductRequest> prodDetailsByTokenAndIdOrId(String token, String customerId);
	/*
	 * public List<ProductRequest> prodDetailsByIdandTypeSlice(String token,
	 * String filterKey, String filterValue, String customerId) ;
	 */

	/*public HashMap<String, String> checkProducts(@RequestBody ProdObj prodObj);*/

	public HashMap<String, String> checkProdDetails(List<String> prodId, String customerId, String offerId);
	
	
}
