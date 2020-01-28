package com.dtag.bm.service.product.inventory.service.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.dtag.bm.service.product.inventory.service.Application;
import com.dtag.bm.service.product.inventory.service.constants.PIConstants;
import com.dtag.bm.service.product.inventory.service.dao.ProductInventoryDAO;
import com.dtag.bm.service.product.inventory.service.exceptions.ProductInventoryValidatorException;
import com.dtag.bm.service.product.inventory.service.model.Product;
import com.dtag.bm.service.product.inventory.service.model.ProductRequest;

public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductInventoryDAO dao;

	@Autowired
	MongoTemplate mongoTemplate;

	@Value("${sim.material.type}")
	private String simMaterialType;

	@Value("${sim.direct.slice.access}")
	private String simDirectSliceAccess;

	@Value("${sim.product.slice.access}")
	private String simProductSliceAccess;

	@Value("${slice}")
	private String slice;

	@Value("${other.onap.services}")
	private String otherOnapServices;

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	/**
	 * @param Product
	 * @description it get the product details from Mu-dynamics and save into
	 *              ProductInventoryDetails Collections
	 * @return It return the saved data.
	 */
	@Override
	public Product createProductInventory(Product request) {
		if (request.getStatus() == null)
			throw new ProductInventoryValidatorException("Status Field Can't be null");
		if (request.getProductOfferingRef() == null && request.getProductSpecificationRef() == null)
			throw new ProductInventoryValidatorException(
					"ProductOfferingRef and ProductSpecificationRef both can't be null");
		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy
		// HH:mm:ss.SSS");
		// String date = dateFormat.format(new Date());
		// request.setStartDate(date);
		// Product product = dao.insert(request);
		// return product;
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		request.setStartDate(formatter.format(date));
		Product product = dao.insert(request);
		return product;
	}

	@Override
	public Product updateProductInventory(Product request) {
		return dao.save(request);
	}

	@Override
	public List<Product> productsDetails(String filterKey, String filterValue) {
		Query query = new Query();
		if (filterKey.equalsIgnoreCase("ProductInstanceId")) {
			query.addCriteria(Criteria.where("_id").is(filterValue));
			return mongoTemplate.find(query, Product.class);
		} else if (filterKey.equalsIgnoreCase("ProductOrderRefOrderId")) {
			query.addCriteria(Criteria.where("productOrder._id").is(filterValue));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("ProductOfferingRefOfferName")) {
			query.addCriteria(Criteria.where("productOffering.name").is(filterValue));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("RelatedPartyRefCustomerId")) {
			query.addCriteria(Criteria.where("relatedParty._id").is(filterValue));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("RelatedPartyRefCustomerName")) {
			query.addCriteria(Criteria.where("relatedParty.name").is(filterValue));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("status")) {
			query.addCriteria(Criteria.where("status").is(filterValue));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("startOfSubscription")) {

			long unix_seconds = Long.parseLong(filterValue);
			Date date = new Date(unix_seconds * 1000L);
			SimpleDateFormat storedFormat = new SimpleDateFormat("dd-MM-yyyy");
			String formatedDate = storedFormat.format(date);
			query.addCriteria(Criteria.where("startDate").is(formatedDate));
			return mongoTemplate.find(query, Product.class);
		}

		return null;
	}

	@Override
	public List<Product> CustomerProductDetails(String filterKey, String filterValue, String cutomerId) {
		Query query = new Query();
		if (filterKey.equalsIgnoreCase("ProductInstanceId")) {
			query.addCriteria(Criteria.where("_id").is(filterValue).and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);
		} else if (filterKey.equalsIgnoreCase("ProductOrderRefOrderId")) {
			query.addCriteria(Criteria.where("productOrder._id").is(filterValue).and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("ProductOfferingRefOfferName")) {
			query.addCriteria(
					Criteria.where("productOffering.name").is(filterValue).and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("RelatedPartyRefCustomerName")) {
			query.addCriteria(
					Criteria.where("relatedParty.name").is(filterValue).and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("status")) {
			query.addCriteria(Criteria.where("status").is(filterValue).and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("startOfSubscription")) {

			long unix_seconds = Long.parseLong(filterValue);
			Date date = new Date(unix_seconds * 1000L);
			SimpleDateFormat storedFormat = new SimpleDateFormat("dd-MM-yyyy");
			String formatedDate = storedFormat.format(date);
			query.addCriteria(Criteria.where("startDate").is(formatedDate).and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);
		}
		return null;
	}

	@Override
	public List<Product> retrieveProdDetailsByType(String token, String cutomerId) {

		Query query = new Query();
		if (token.equalsIgnoreCase("sim product")) {
			query.addCriteria(Criteria.where("type").is("sim").and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);
		} else if (token.equalsIgnoreCase("AML")) {
			query.addCriteria(Criteria.where("type").is("AML").and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);
		} else if (token.equalsIgnoreCase("Non sim product")) {
			query.addCriteria(Criteria.where("type").is("Non sim product").and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);

		}
		return mongoTemplate.find(query, Product.class);
	}

	@Override
	public List<Product> productsDetailsByRefferedType(String type, String relatedPartyRefId, String status,
			String relatedPartyreferredType) {

		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("relatedParty._id").is(relatedPartyRefId).and("relatedParty.referredType")
					.is(relatedPartyreferredType).and("status").is(status).and("type").is(type));
			return mongoTemplate.find(query, Product.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings({ "unused", "null" })
	@Override
	public List<Product> prodDetailsByIdandType(String token, String filterKey, String filterValue, String customerId) {

		Query query = new Query();

		if (token != null && token.equalsIgnoreCase("sim product")) {
			query.addCriteria(Criteria.where("type").is("sim"));
			return mongoTemplate.find(query, Product.class);
		} else if (token != null && token.equalsIgnoreCase("ALM")) {
			query.addCriteria(Criteria.where("type").is("ALM"));
			return mongoTemplate.find(query, Product.class);
		} else if (token != null && token.equalsIgnoreCase("Non sim product")) {
			query.addCriteria(Criteria.where("type").is("Non sim product"));
			return mongoTemplate.find(query, Product.class);
		} else if (customerId != null && filterKey == null) {
			if (token.equalsIgnoreCase("simproducts")) {
				query.addCriteria(Criteria.where("relatedParty._id").is(customerId).and("type").is("sim"));
				return mongoTemplate.find(query, Product.class);
			} else if (token.equalsIgnoreCase("ALM")) {
				query.addCriteria(Criteria.where("relatedParty._id").is(customerId).and("type").is("ALM"));
				return mongoTemplate.find(query, Product.class);
			} else if (token.equalsIgnoreCase("Non sim product")) {
				query.addCriteria(Criteria.where("type").is("Non sim product").and("relatedParty._id").is(customerId));
				return mongoTemplate.find(query, Product.class);
			}

		} else if (customerId == null && filterKey == null) {
			return mongoTemplate.find(query, Product.class);
			// Get the details for Admin on page loading

		} else if (customerId != null && (filterKey != null && filterValue != null)) {
			query.addCriteria(Criteria.where("relatedParty._id").is(customerId));
			return mongoTemplate.find(query, Product.class);
			// Return the details for self-care

		} else if (filterKey != null && filterValue != null) {
			return mongoTemplate.find(query, Product.class);
			// Return the details for Admin

		} else if (filterKey.equalsIgnoreCase("ProductInstanceId")) {
			query.addCriteria(Criteria.where("_id").is(filterValue).and("relatedParty._id").is(customerId));
			return mongoTemplate.find(query, Product.class);
		} else if (filterKey.equalsIgnoreCase("ProductOrderRefOrderId")) {
			query.addCriteria(
					Criteria.where("productOrder._id").is(filterValue).and("relatedParty._id").is(customerId));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("ProductOfferingRefOfferName")) {
			query.addCriteria(
					Criteria.where("productOffering.name").is(filterValue).and("relatedParty._id").is(customerId));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("RelatedPartyRefCustomerName")) {
			query.addCriteria(
					Criteria.where("relatedParty.name").is(filterValue).and("relatedParty._id").is(customerId));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("status")) {
			query.addCriteria(Criteria.where("status").is(filterValue).and("relatedParty._id").is(customerId));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("startOfSubscription")) {

			long unix_seconds = Long.parseLong(filterValue);
			Date date = new Date(unix_seconds * 1000L);
			SimpleDateFormat storedFormat = new SimpleDateFormat("dd-MM-yyyy");
			String formatedDate = storedFormat.format(date);
			query.addCriteria(Criteria.where("startDate").is(formatedDate).and("relatedParty._id").is(customerId));
			return mongoTemplate.find(query, Product.class);
		}

		return mongoTemplate.find(query, Product.class);

	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public List<Product> prodDetailsByIdandTypeTest(String token, String filterKey, String filterValue,
			String customerId) {

		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "startDate"));
		if (token != null) {

			query.addCriteria(Criteria.where("type").is(token));
		}

		if (customerId != null) {
			query.addCriteria(Criteria.where("relatedParty._id").is(customerId));
		}

		if (filterKey != null && filterValue != null) {
			if (filterKey.equalsIgnoreCase("startOfSubscription")) {

				long unix_seconds = Long.parseLong(filterValue);
				Date date = new Date(unix_seconds * 1000L);
				SimpleDateFormat storedFormat = new SimpleDateFormat("dd-MM-yyyy");
				String formatedDate = storedFormat.format(date);
				query.addCriteria(Criteria.where("startDate").is(formatedDate));

			} else {
				query.addCriteria(Criteria.where(returnKey(filterKey)).is(filterValue));
			}
		}
		return mongoTemplate.find(query, Product.class);
	}

	private String returnKey(String key) {
		if (key.equals("productInstanceId"))
			return "_id";
		else if (key.equals("ProductOrderRefOrderId"))
			return "productOrder._id";
		else if (key.equals("ProductOfferingRefOfferName"))
			return "productOffering.name";
		else if (key.equals("RelatedPartyRefCustomerName"))
			return "relatedParty.name";
		else if (key.equals("status"))
			return "status";
		else if (key.equals("customerId"))
			return "relatedParty._id";
		else if (key.equals("referredType"))
			return "relatedParty.referredType";
		else if (key.equals("startOfSubscription"))
			return "startDate";
		else if (key.equals("productCharacteristicName"))
			return "productCharacteristic.name";
		return "Successfully";

	}

	@SuppressWarnings("unused")
	@Override
	public List<Product> prodSliceDetails(String custId) {

		String str1 = "openness";
		String str2 = "SliceAccessType";

		Query query = new Query();
		try {
			if (custId != null && str1 != null) {
				query.addCriteria(Criteria.where("productCharacteristic.name").is(str1).and("relatedParty._id")
						.is(custId).and("type").is("product"));
				return mongoTemplate.find(query, Product.class);
			} else if (custId != null && str2 != null) {
				query.addCriteria(Criteria.where("productCharacteristic.name").is(str2).and("relatedParty._id")
						.is(custId).and("type").is("product"));
				return mongoTemplate.find(query, Product.class);
			}

			else {
				if (str1 == null && str2 == null)
					;
				query.addCriteria(Criteria.where("productCharacteristic.name").is(str1).and("type").is("product"));
				LOGGER.info("productCharacteristics with openness and SliceAccessType successfully Retrieved");
				return mongoTemplate.find(query, Product.class);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@Override
	public List<ProductRequest> prodDetailsByTokenAndIdOrId(String token, String customerId) {

		Set<String> technologySet = new HashSet<>(Arrays.asList("SLICE", "OTHER ONAP SERVICE"));
		Set<String> accessSet = new HashSet<>(Arrays.asList("Product_Slice_Access", "Direct_Slice_Access"));

		try {

			/*
			 * If token is Direct_Slice_Access/Product_Slice_Access and customer Id is not
			 * NULL
			 */
			if (accessSet.contains(token) && customerId != null) {
				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Query queryAccess = new Query();
				queryAccess.with(new Sort(Sort.Direction.DESC, "startDate")).addCriteria(Criteria.where("status")
						.is(PIConstants.ACTIVE).and("relatedParty._id").is(customerId).and(PIConstants.TYPE).is(token));
				List<Product> productRequestListAccess = mongoTemplate.find(queryAccess, Product.class);
				ProductRequest productRequestAccess = null;
				for (Product product : productRequestListAccess) {
					productRequestAccess = new ProductRequest();
					BeanUtils.copyProperties(product, productRequestAccess);
					productRequestList.add(productRequestAccess);
				}
				return productRequestList;
			}
			/*
			 * If token is Direct_Slice_Access/Product_Slice_Access and customer Id is not
			 * NULL
			 */
			if (accessSet.contains(token) && customerId == null) {
				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Query queryAccess = new Query();
				queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"))
						.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and(PIConstants.TYPE).is(token));
				List<Product> productRequestListAccess = mongoTemplate.find(queryAccess, Product.class);
				ProductRequest productRequestAccess = null;
				for (Product product : productRequestListAccess) {
					productRequestAccess = new ProductRequest();
					BeanUtils.copyProperties(product, productRequestAccess);
					productRequestList.add(productRequestAccess);
				}
				return productRequestList;
			}

			/*
			 * It return the slices, which are customer's private slice and other public
			 * slices
			 */
			if (token.equalsIgnoreCase(PIConstants.ASSIGN_DIRECT_SLICE) && customerId != null) {
				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Query query11 = new Query();

				query11.with(new Sort(Sort.Direction.DESC, "startDate")).addCriteria(Criteria.where("status")
						.is(PIConstants.ACTIVE).and("relatedParty._id").is(customerId)
						.orOperator(Criteria.where(PIConstants.TYPE).is(otherOnapServices),
								Criteria.where(PIConstants.TYPE).is(slice))
						.andOperator(Criteria.where("productCharacteristic.name").is(PIConstants.SLICE_ACCESS_TYPE),
								Criteria.where("productCharacteristic.value").is("Direct"),
								Criteria.where("productCharacteristic.name").is(PIConstants.SLICE_OPENNESS),
								Criteria.where("productCharacteristic.value").is("Private")));

				List<Product> productRequestList41 = mongoTemplate.find(query11, Product.class);
				ProductRequest productRequest41 = null;
				for (Product product : productRequestList41) {
					productRequest41 = new ProductRequest();
					BeanUtils.copyProperties(product, productRequest41);
					productRequestList.add(productRequest41);
				}

				Query query12 = new Query();
				query12.with(new Sort(Sort.Direction.DESC, "startDate"))
						.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE)
								.orOperator(Criteria.where(PIConstants.TYPE).is(otherOnapServices),
										Criteria.where(PIConstants.TYPE).is(slice))
								.andOperator(
										Criteria.where("productCharacteristic.name").is(PIConstants.SLICE_ACCESS_TYPE),
										Criteria.where("productCharacteristic.value").is("Direct"),
										Criteria.where("productCharacteristic.name").is(PIConstants.SLICE_OPENNESS),
										Criteria.where("productCharacteristic.value").is("Public")));

				List<Product> productRequestList5 = mongoTemplate.find(query12, Product.class);
				ProductRequest productRequest5 = null;
				for (Product product : productRequestList5) {
					productRequest5 = new ProductRequest();
					BeanUtils.copyProperties(product, productRequest5);
					productRequestList.add(productRequest5);

				}

				return productRequestList;
			}
			/*
			 * It return the slices for specific customer and it will display on Non-Sim
			 * products tab
			 */
			if (technologySet.contains(token) && customerId != null) {
				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Query query4 = new Query();
				query4.with(new Sort(Sort.Direction.DESC, "startDate")).addCriteria(
						Criteria.where("relatedParty._id").is(customerId).and("status").is(PIConstants.ACTIVE)
								.orOperator(Criteria.where(PIConstants.TYPE).is(otherOnapServices),
										Criteria.where(PIConstants.TYPE).is(slice)));
				List<Product> productRequestList3 = mongoTemplate.find(query4, Product.class);
				ProductRequest productRequest3 = null;
				for (Product product : productRequestList3) {
					productRequest3 = new ProductRequest();
					BeanUtils.copyProperties(product, productRequest3);
					productRequestList.add(productRequest3);
				}
				return productRequestList;
			}

			/*
			 * It return the all slices and it will display on Non-Sim products tab
			 * 
			 */
			if (technologySet.contains(token) && null == customerId) {
				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Query query4 = new Query();
				query4.with(new Sort(Sort.Direction.DESC, "startDate"))
						.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).orOperator(
								Criteria.where(PIConstants.TYPE).is(otherOnapServices),
								Criteria.where(PIConstants.TYPE).is(slice)));
				List<Product> productRequestList3 = mongoTemplate.find(query4, Product.class);
				ProductRequest productRequest3 = null;
				for (Product product : productRequestList3) {
					productRequest3 = new ProductRequest();
					BeanUtils.copyProperties(product, productRequest3);
					productRequestList.add(productRequest3);
				}
				return productRequestList;
			}

			/*
			 * It return the specific customer's sims and which are associated direct slices
			 * to that sim and produt slice access as well
			 */
			if (customerId != null && token.equalsIgnoreCase(simMaterialType)) {
				Query query7 = new Query();
				query7.addCriteria(Criteria.where(PIConstants.TYPE).is(simMaterialType).and("relatedParty._id")
						.is(customerId).and("status").is(PIConstants.ACTIVE));
				List<Product> productRequestList2 = mongoTemplate.find(query7, Product.class);
				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				for (Product product : productRequestList2) {
					ProductRequest productRequest10 = null;

					for (int i = 0; i < product.getProductCharacteristic().size(); i++) {
						if (product.getProductCharacteristic().get(i).getName().equalsIgnoreCase(PIConstants.IMSI)) {
							productRequest10 = new ProductRequest();
							BeanUtils.copyProperties(product, productRequest10);
							String imsiValue = product.getProductCharacteristic().get(i).getValue().intern().toString();
							Query query8 = new Query();
							query8.addCriteria(
									Criteria.where("productCharacteristic.value").is(imsiValue).and(PIConstants.TYPE)
											.is(simDirectSliceAccess).and("status").is(PIConstants.ACTIVE));
							List<Product> sliceProducts = mongoTemplate.find(query8, Product.class);
							productRequest10.setRelatedProductInventory(sliceProducts);
							productRequestList.add(productRequest10);
						}
					}
					// It is sim level product which is having type = PRODUCT_SLICE_ACCESS
					Query queryGetProductSlice = new Query();
					queryGetProductSlice.addCriteria(Criteria.where(PIConstants.TYPE).is(simProductSliceAccess)
							.and("status").is(PIConstants.ACTIVE));
					List<Product> productSliceAccessList = mongoTemplate.find(queryGetProductSlice, Product.class);
					for (int i = 0; i < productSliceAccessList.size(); i++) {
						ProductRequest productRequest15 = new ProductRequest();
						Product p1 = new Product();
						p1 = productSliceAccessList.get(i);
						BeanUtils.copyProperties(p1, productRequest15);
						productRequestList.add(productRequest15);
					}

				}
				return productRequestList;
			}

			if (null == customerId && token.equalsIgnoreCase(simMaterialType)) {

				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Query query8 = new Query();
				query8.with(new Sort(Sort.Direction.DESC, "startDate"))
						.addCriteria(Criteria.where(PIConstants.TYPE).is(token).and("status").is(PIConstants.ACTIVE));
				List<Product> products = null;
				products = mongoTemplate.find(query8, Product.class);

				for (Product product2 : products) {
					ProductRequest productRequest12 = null;
					for (int i = 0; i < product2.getProductCharacteristic().size(); i++) {
						if (product2.getProductCharacteristic().get(i).getName().equalsIgnoreCase("imsi")) {
							productRequest12 = new ProductRequest();
							BeanUtils.copyProperties(product2, productRequest12);
							String imsiValue = product2.getProductCharacteristic().get(i).getValue().intern()
									.toString();
							Query query1 = new Query();
							query1.with(new Sort(Sort.Direction.DESC, "startDate"))
									.addCriteria(Criteria.where("productCharacteristic.value").is(imsiValue)
											.and(PIConstants.TYPE).is(simDirectSliceAccess).and("status")
											.is(PIConstants.ACTIVE));

							List<Product> sliceProducts = mongoTemplate.find(query1, Product.class);
							productRequest12.setRelatedProductInventory(sliceProducts);
							productRequestList.add(productRequest12);
						}
					}

				}

				// It is sim level product which is having type = PRODUCT_SLICE_ACCESS
				Query queryGetProductSlice1 = new Query();
				queryGetProductSlice1.addCriteria(Criteria.where(PIConstants.TYPE).is(simProductSliceAccess)
						.and("status").is(PIConstants.ACTIVE));
				List<Product> productSliceAccessList1 = mongoTemplate.find(queryGetProductSlice1, Product.class);
				for (int i = 0; i < productSliceAccessList1.size(); i++) {
					ProductRequest productRequest16 = new ProductRequest();
					Product p1 = new Product();
					p1 = productSliceAccessList1.get(i);
					BeanUtils.copyProperties(p1, productRequest16);
					productRequestList.add(productRequest16);
				}
				return productRequestList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// @SuppressWarnings("unused")
	// private List<ProductRequest> returnsim(String key) {
	//
	/*
	 * @Override public List<ProductRequest> prodDetailsByIdandTypeSlice(String
	 * token, String filterKey, String filterValue, String customerId) {
	 * 
	 * String str1 = "openness"; String str2 = "SliceAccessType";
	 * 
	 * List<Product> products = null; ProductRequest productRequest = new
	 * ProductRequest(); List<ProductRequest> productRequestList = new
	 * ArrayList<ProductRequest>(); Query query = new Query();
	 * 
	 * if (!token.equalsIgnoreCase("sim")) {
	 * query.addCriteria(Criteria.where("type").is(token));
	 * mongoTemplate.find(query, Product.class); } else {
	 * 
	 * query.addCriteria(Criteria.where("type").is(token).and("status").is(
	 * "Active")); products = mongoTemplate.find(query, Product.class);
	 * 
	 * for (Product product : products) {
	 * 
	 * for (int i = 0; i < product.getProductCharacteristic().size(); i++) { if
	 * (product.getProductCharacteristic().get(i).getName().equalsIgnoreCase(
	 * "imsi")) { BeanUtils.copyProperties(product, productRequest); String
	 * imsiValue =
	 * product.getProductCharacteristic().get(i).getValue().intern().toString();
	 * Query query1 = new Query();
	 * query1.addCriteria(Criteria.where("productCharacteristic.value").is(
	 * imsiValue).and("type") .is("Non sim product")); List<Product> sliceProducts =
	 * mongoTemplate.find(query1, Product.class); // Assign the slices to sim
	 * productRequest.setRelatedProductInventory(sliceProducts);
	 * productRequestList.add(productRequest); } }
	 * 
	 * return productRequestList;
	 * 
	 * } return productRequestList;
	 * 
	 * } return productRequestList; }
	 */

	/*
	 * @SuppressWarnings("unused")
	 * 
	 * @Override public HashMap<String, String> checkProducts(@RequestBody ProdObj
	 * prodObj) {
	 * 
	 * List<String> prod = prodObj.getProdId(); String customerId =
	 * prodObj.getCustomerId(); HashMap<String, String> map = new HashMap<>(); if
	 * (!prod.isEmpty()) { prod.forEach(prodone -> { HashMap<String, String> map1 =
	 * new HashMap<>(); Query query = new Query();
	 * query.addCriteria(Criteria.where("_id").is(prodObj.getProdId()).and(
	 * "relatedParty._id")
	 * .is(prodObj.getCustomerId()).and("productOffering._id").is(prodObj.
	 * getOffcerId())); List<Product> products = mongoTemplate.find(query,
	 * Product.class);
	 * 
	 * }); } return map; }
	 */

	@Override
	public HashMap<String, String> checkProdDetails(List<String> prodId, String customerId, String offerId) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			for (int i = 0; i < prodId.size(); i++) {
				String productInstance = prodId.get(i);
				String productInstanceId = productInstance.replace(",", "");
				Query query = new Query();
				query.addCriteria(Criteria.where("_id").is(productInstanceId).and("relatedParty._id").is(customerId)
						.and("productOffering._id").is(offerId));
				List<Product> products = mongoTemplate.find(query, Product.class);
				if (!products.isEmpty()) {
					map.put(productInstanceId, "true");
				} else {
					map.put(productInstanceId, "false");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;

	}

}