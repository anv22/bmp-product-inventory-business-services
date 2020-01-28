package com.dtag.bm.service.product.inventory.service.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dtag.bm.service.product.inventory.service.dao.ProductInventoryDAO;
import com.dtag.bm.service.product.inventory.service.exceptions.ProductInventoryValidatorException;
import com.dtag.bm.service.product.inventory.service.model.Product;
import com.dtag.bm.service.product.inventory.service.model.ProductRequest;
import com.dtag.bm.service.product.inventory.service.service.ProductServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/productInventoryManagement/v1")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductInventoryController {

	@Autowired
	ProductServiceImpl productservice;

	@Autowired
	ProductInventoryDAO dao;
	
	@Autowired
	MongoTemplate mongoTemplate;
	

	/**
	 * @param product
	 * @return
	 * @throws IOException
	 */

	@PostMapping(value = "/product")
	public Product CreateProducts(@RequestBody Product product) throws IOException {
		/* UUID generation logic */
		UUID uuid = UUID.randomUUID();
		String Id = uuid.toString();
		product.setId(Id);
		if (product.getStatus() == null)
			throw new ProductInventoryValidatorException("Status Field Can't be null");
		if (product.getProductOfferingRef() == null && product.getProductSpecificationRef() == null)
			throw new ProductInventoryValidatorException(
					"ProductOffering and ProductSpecification both can't be null");
		if (product.getProductCharacteristic().isEmpty())
			throw new ProductInventoryValidatorException(
					"Product Characteristic  can't be null");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String date = dateFormat.format(new Date());
		product.setStartDate(date);
		Resource<Product> resource = new Resource<Product>(product);
		ControllerLinkBuilder links = linkTo(methodOn(this.getClass()).productDetailsbyId(Id));
		resource.add(links.withRel("get-Product-details"));
		product.setHref(links.toString());
		/*
		 * if (product.getProductOfferingRef() != null) {
		 * product.getProductOfferingRef().setHref(links.toString()); } if
		 * (product.getRealizingResourceRef() != null) { if
		 * (!product.getRealizingResourceRef().isEmpty()) {
		 * product.getRealizingResourceRef().forEach(RealizingResourceRef -> {
		 * 
		 * RealizingResourceRef.setHref(links.toString()); }); } }
		 * 
		 * product.getProductSpecificationRef().setHref(links.toString()); if
		 * (product.getRelatedPartyRef() != null) { if
		 * (!product.getRelatedPartyRef().isEmpty()) {
		 * product.getRelatedPartyRef().forEach(RelatedPartyRef -> {
		 * 
		 * RelatedPartyRef.setHref(links.toString()); }); } } if
		 * (product.getBillingAccountRef() != null) {
		 * product.getBillingAccountRef().setHref(links.toString()); }
		 * 
		 * if (product.getProductRelationship() != null) { if
		 * (!product.getProductRelationship().isEmpty()) {
		 * product.getProductRelationship().forEach(ProductRelationship -> { if
		 * (ProductRelationship.getProductRef() != null) {
		 * ProductRelationship.getProductRef().setHref(links.toString()); } }); } } if
		 * (product.getPlace() != null) { if (!product.getPlace().isEmpty()) {
		 * product.getPlace().forEach(Place -> {
		 * 
		 * Place.setHref(links.toString()); }); } } if (product.getRealizingServiceRef()
		 * != null) { if (!product.getRealizingServiceRef().isEmpty()) {
		 * product.getRealizingServiceRef().forEach(RealizingService -> {
		 * 
		 * RealizingService.setHref(links.toString()); }); } } if
		 * (product.getProductOrderRef() != null) { if
		 * (!product.getProductOrderRef().isEmpty()) {
		 * product.getProductOrderRef().forEach(ProductOrderRef -> {
		 * 
		 * ProductOrderRef.setHref(links.toString()); }); } } if
		 * (product.getProductPrice() != null) { if
		 * (!product.getProductPrice().isEmpty()) {
		 * product.getProductPrice().forEach(ProductPrice -> { if
		 * (ProductPrice.getBillingAccountRef() != null) {
		 * ProductPrice.getBillingAccountRef().setHref(links.toString()); } }); } }
		 */
		Product savedProduct = productservice.createProductInventory(product);
		return savedProduct;
	}

	/**
	 * @param role
	 * @return
	 */
	@GetMapping("/product/byRole")
	public ResponseEntity<Collection<Product>> GetProducts(@RequestParam("role") String role) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Responded", "ProductInventoryController");
		return ResponseEntity.accepted().headers(headers).body(dao.findByRelatedPartyRole(role));
	}

	/**
	 * @param id
	 * @return
	 */
	@DeleteMapping("/product/{id}")
	public String DeleteProductById(@PathVariable(value = "id") String id) {
		long numberofRecords = dao.deleteById(id);
		return numberofRecords + "Product deleted.";
	}

	/*
	 * @DeleteMapping("/Products") public String DeleteSimsBasedOnId(@RequestBody
	 * List<String> productIds) { if (!productIds.isEmpty()) {
	 * productIds.forEach(productId -> { dao.deleteById(productId); }); return
	 * "Product/s deleted successfully."; } return
	 * "Products not available with given IDs"; }
	 */

	/**
	 * @param product
	 * @return
	 */
	@PutMapping(value = "/product")
	public Product UpdateProdctInventory(@RequestBody Product product) {
		return productservice.updateProductInventory(product);

	}

	/**
	 * @return
	 * @throws IOException
	 */

	@SuppressWarnings("unused")
	@GetMapping(value = "/product")
	public List<Product> getAllProductInventory() throws IOException {
		List<Product> listOfInventory = dao.findAll();
		Query query = new Query();
		query.with( new Sort(Sort.Direction.DESC, "startDate"));
		return mongoTemplate.find(query, Product.class);
	}

	/**
	 * @param id
	 * @return
	 */
	@GetMapping("/product/{id}")
	public Product productDetailsbyId(@PathVariable(value = "id") String id) {
		return dao.findById(id);
	}

	/**
	 * @param filterKey
	 * @param filterValue
	 * @return
	 */
	@GetMapping("/product/{filterKey}/{filterValue}")
	public List<Product> productsDetails(@PathVariable(value = "filterKey") String filterKey,
			@PathVariable(value = "filterValue") String filterValue) {
		List<Product> product = productservice.productsDetails(filterKey, filterValue);
		return product;
	}

	/**
	 * @param status
	 * @param id
	 * @return
	 */
	@PatchMapping("/product/ChangeStatus/{id}")
	public ResponseEntity<?> updateProdcutStatus(@PathVariable("id") String id) {
		Product ProductToBeUpdated = productDetailsbyId(id);
		if (ProductToBeUpdated != null) {
			ProductToBeUpdated.setStatus("InActive");
			productservice.updateProductInventory(ProductToBeUpdated);
			return new ResponseEntity<>("Product with product id: " + id + " updated successfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Product with product id: " + id + " not available", HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/product/updateStatus/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable("id") String id) {
		Product ProductToBeUpdated = productDetailsbyId(id);
		if (ProductToBeUpdated != null) {
			
			ProductToBeUpdated.setStatus("InActive");
			productservice.updateProductInventory(ProductToBeUpdated);
			return new ResponseEntity<>("Product with product id: " + id + " updated successfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Product with product id: " + id + " not available", HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
	/**
	 * @param filterKey
	 * @param filterValue
	 * @param customerId
	 * @return
	 */
	@GetMapping("/product/customerId")
	public Collection<Product> CustomerproductsDetails( @RequestParam(required = false) String filterKey,@RequestParam(required =false)String filterValue, @RequestParam("RelatedPartyCustomerId") String customerId) {
	    if ((filterKey != null) && (filterValue != null) ) {
	        return productservice.CustomerProductDetails(filterKey,filterValue,customerId);
	    } else if(customerId != null){
	    	return dao.findByRelatedPartyId(customerId);
	    }
	    return null;
	}
	
	/**
	 * @param token
	 * @param customerId
	 * @return 
	 */
	@GetMapping("/product/retrieve/customerId")
	public Collection<Product> retrieveProdDetailsByType( @RequestParam String token, @RequestParam(required =false) String customerId) {
	    if ((token != null) && (customerId != null) ) {
	       return productservice.retrieveProdDetailsByType(token,customerId);
	    }
	    else{
	    	 return null;
	    }
	        
	}
	
	/**
     * @description this method will return the list of products based on the refferedType and status
     * @param type
     * @param relatedPartyRefId
     * @param status
     * @param relatedPartyreferredType
     * @return List of products 
      */
     @GetMapping("/product/prodDetailsByType")
     public List<Product> productsDetailsByRefferedType(@RequestParam String type,
                   @RequestParam String relatedPartyRefId, @RequestParam String status,
                   @RequestParam String relatedPartyreferredType) {

            List<Product> product = productservice.productsDetailsByRefferedType(type, relatedPartyRefId, status,
                         relatedPartyreferredType);
            return product;
     }


     @GetMapping("/product/prodDetails")
     public List<Product> prodDetailsByIdandType(@RequestParam String token,
                   @RequestParam(required = false) String filterKey, @RequestParam(required = false) String filterValue,
                   @RequestParam(required = false) String customerId) {

            List<Product> product = productservice.prodDetailsByIdandTypeTest(token, filterKey, filterValue, customerId);
            return product;
     }

     @GetMapping("/product/Slice")
     public List<Product> prodSliceDetails( @RequestParam(required = false) String custId){
            List<Product> product = productservice.prodSliceDetails(custId);
            return product;
     }
     
     @GetMapping("/product/prodDetailsWithSlices")
     public List<ProductRequest> prodDetailsByIdandType1(@RequestParam String token,
                   @RequestParam(required = false) String customerId) {

            List<ProductRequest> product = productservice.prodDetailsByTokenAndIdOrId(token, customerId);
            return product;
     }
     
     /**
 	 * @description "This method will search the record with given inputs, if
 	 *              found return the present else Not present.
 	 * @param prodId
 	 * @param customerId
 	 * @param offcerId
 	 * @return
 	 */
 	@GetMapping("/product/checkProdDetails")
 	public HashMap<String, String> checkProdDetails(@RequestParam List<String> prodId, @RequestParam String customerId,
 			@RequestParam String offerId) {
 		HashMap<String, String> map = productservice.checkProdDetails(prodId, customerId, offerId);
 		return map;
 	}
     
     
}