package com.example.IMS.controller;

import com.example.IMS.dto.*;
import com.example.IMS.entity.mssql.Icitem;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.model.IModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Controller
public class HomeController implements ErrorController {
		private final com.example.IMS.service.service service;
	@Retryable( backoff = @Backoff(delay = 2000))
	 	@GetMapping("/")
	    public String index(Model model){
			 Boolean installed = service.findInstalledDevice();
			 if(installed){
				 model.addAttribute("installed",installed);
			 }
			 return "index";
	    }
	@Retryable( backoff = @Backoff(delay = 2000))
		@GetMapping("/imports")
	    public String getImport(){
	        return "importView";
	    }
	@Retryable( backoff = @Backoff(delay = 2000))
		@GetMapping("/api/purchaseOrders")
		public ResponseEntity<List<supplierPurchaseOrderDTO>> getPurchaseOrders(@RequestParam String supplierTpin, @RequestParam String supplierName) {
			List<supplierPurchaseOrderDTO> purchaseOrders = service.returnVendorReceipts(supplierTpin,supplierName);
			return ResponseEntity.ok(purchaseOrders);
		}
	@Retryable( backoff = @Backoff(delay = 2000))
	@GetMapping("/api/import_receipts")
	public ResponseEntity<List<supplierPurchaseOrderDTO>> getImportReceipts( @RequestParam String supplierName) {
		List<supplierPurchaseOrderDTO> purchaseOrders = service.returnImportReceipts(supplierName);
		return ResponseEntity.ok(purchaseOrders);
	}
		@GetMapping("/purchases")
		public String getPurchases(Model model) {
			List<Icitem> icitems = service.returnItems();
			PurchasesResponse purchasesResponse = service.selectPurchase();

			// Ensure the response is not null before proceeding
			if (purchasesResponse != null) {
				model.addAttribute("invoices", purchasesResponse.getPurchases());
				model.addAttribute("purchaseOrders", purchasesResponse.getAdditionalData());
				model.addAttribute("sageItems",icitems);
			}
				return "purchasesView";
	    }
		@PostMapping("/post-imports")
		public ResponseEntity<String> postImports(@RequestBody importPostDTO request) {
			if(service.postImport(request)){
				return new ResponseEntity<>("success",HttpStatus.OK);
			}
			else{
				return new ResponseEntity<>("failed",HttpStatus.EXPECTATION_FAILED);

			}
		}
		@PostMapping("/post-imports-reject")
		public ResponseEntity<String> postImportsReject(@RequestBody importPostDTO request) {
			 if (service.postImportReject(request)){
				 return new ResponseEntity<>("success",HttpStatus.OK);

			 }
			 else{
				 return new ResponseEntity<>("failed",HttpStatus.EXPECTATION_FAILED);
			 }
		}
		@PostMapping("/post-po-to-sage")
		public ResponseEntity<String> postPoToSage(@RequestBody postPODTO request) {
			try {

				if (service.postToSage(request)){; // Call the method
				return ResponseEntity.ok("Purchase order processed successfully.");
				}
				else{
					return new ResponseEntity<>("Purchase processing unsuccessful",HttpStatus.EXPECTATION_FAILED);

				}
			} catch (JsonProcessingException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Error processing purchase order: " + e.getMessage());
			}
		}
	@Retryable( backoff = @Backoff(delay = 2000))
	@PostMapping("/post-po-to-sage-reject")
	public ResponseEntity<String> postPoToSageReject(@RequestBody postPODTO request) {
		try {

			if (service.postToSageReject(request)){; // Call the method
				return ResponseEntity.ok("Purchase order processed successfully.");
			}
			else{
				return new ResponseEntity<>("Purchase processing unsuccessful",HttpStatus.EXPECTATION_FAILED);

			}
		} catch (JsonProcessingException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error processing purchase order: " + e.getMessage());
		}
	}
	@Retryable( backoff = @Backoff(delay = 2000))
	@GetMapping("/import")
	public String getImports( @RequestParam(required = false) String refnumber, Model model) throws JsonProcessingException {
		// Call the service method to get the data

		List<importItemListDTO> response = service.getImports(refnumber);
		List<Icitem> icitems = service.returnItems();
		List<supplierPurchaseOrderDTO> purchaseOrderDTOS = service.returnPOs();
		// Optionally, add data to the model if you need to display it on a Thymeleaf page
		model.addAttribute("importData", response);
		model.addAttribute("sageItems",icitems);
		//model.addAttribute("purchaseOrders", purchaseOrderDTOS);
		// Return a view name (you can redirect or render a view as needed)
		return "importResult";
	}
	@Retryable( backoff = @Backoff(delay = 2000))
	@GetMapping("/sales")
	public String sales(){
			return "salesView";
		}
	@Retryable( backoff = @Backoff(delay = 2000))
	@GetMapping("/item-list")
	public String getItems(Model model) throws JsonProcessingException {
			 List<selectItemDTO> itemList = service.getStockItems();
			 model.addAttribute("itemList",itemList);
		return "getItems";
	}
	@Retryable( backoff = @Backoff(delay = 2000))
	@GetMapping("/failed-sales")
	public String failedSales(Model model){

		model.addAttribute("sales",service.getFailedSales());

		return "salesHistoryFailed";
	}
	@Retryable( backoff = @Backoff(delay = 2000))
	@GetMapping("/pending-sales")
	public String pendingSales(Model model){

			 model.addAttribute("sales",service.getPendingSales());
			 return "salesHistory";
	}
	@Retryable( backoff = @Backoff(delay = 2000))
	@GetMapping("/pending-purchases")
	public String pendingPurchases(Model model){

		model.addAttribute("pendingPurchases",service.getPendingPurchases());

		return "purchasesHistory";
	}
	@Retryable( backoff = @Backoff(delay = 2000))
	@GetMapping("/failed-purchases")
	public String failedPurchases(Model model){

		model.addAttribute("failedPurchases",service.getFailedPurchases());

		return "purchasesHistoryFailed";
	}
	@Retryable( backoff = @Backoff(delay = 2000))
	@PostMapping(value="/initialize-device")
	public String initialize(@RequestBody initializeDTO dto){
		try {
			if(service.initialize_device(dto)){
				return "redirect:/";
			}
			else {
				return "redirect:/";
			}


		}
		catch (Exception e) {
			//sendErrorEmail(e);
			return "redirect:/";
		}
	}
	@Retryable( backoff = @Backoff(delay = 2000))
	@GetMapping(value="/create-packaging-unit")
	public ResponseEntity<Map<String,String>> createPackagingUnit(){
		try {
			return service.createPackagingUnit();
		}
		catch (Exception e) {
			//sendErrorEmail(e);
			Map<String,String> responses = new HashMap<>();
			responses.put("message", "Failed");
			return new ResponseEntity<>(responses, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute("jakarta.servlet.error.status_code");
		if (status != null) {
			int statusCode = Integer.parseInt(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "404";  // Return 404 error page
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "500";  // Return 500 error page
			}
		}
		return "error";  // Default error page
	}


}
