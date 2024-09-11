package com.example.IMS.controller;

import com.example.IMS.dto.*;
import com.example.IMS.entity.mssql.Icitem;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Controller
public class HomeController {
		private final com.example.IMS.service.service service;
	 	@GetMapping("/")
	    public String index(){
	        return "index";
	    }
		@GetMapping("/import")
	    public String getImport(){
	        return "importView";
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
		@PostMapping("/post-po-to-sage")
		public ResponseEntity<String> postPoToSage(@RequestBody postPODTO request) {
			try {
				int invnumber = request.getInvnumber();
				String po_number = request.getPo_number();
				List<postPOItemsDTO> itemDetails = request.getItem_details();
				service.postPoToSage(invnumber, po_number,itemDetails); // Call the method
				return ResponseEntity.ok("Purchase order processed successfully.");
			} catch (JsonProcessingException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Error processing purchase order: " + e.getMessage());
			}
		}

	@PostMapping("/imports")
	public String getImports(@RequestParam("refNumber") String refNumber, Model model) throws JsonProcessingException {
		// Call the service method to get the data

		List<importItemListDTO> response = service.getImports(refNumber);
		List<Icitem> icitems = service.returnItems();
		List<supplierPurchaseOrderDTO> purchaseOrderDTOS = service.returnPOs();
		// Optionally, add data to the model if you need to display it on a Thymeleaf page
		model.addAttribute("importData", response);
		model.addAttribute("sageItems",icitems);
		model.addAttribute("purchaseOrders", purchaseOrderDTOS);
		model.addAttribute("refNo",refNumber);
		// Return a view name (you can redirect or render a view as needed)
		return "importResult";  // Replace with the appropriate view name
	}

		@GetMapping("/sales")
		public String sales(){
			return "salesView";
		}

	@GetMapping("/pending-sales")
	public String pendingSales(Model model){

			 model.addAttribute("sales",service.getPendingSales());
			 return "salesHistory";
	}

	@GetMapping("/pending-purchases")
	public String pendingPurchases(Model model){

		model.addAttribute("purchases",service.getPendingPurchases());
		return "purchasesHistory";
	}



}
