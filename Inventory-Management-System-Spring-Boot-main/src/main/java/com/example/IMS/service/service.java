package com.example.IMS.service;

import com.example.IMS.controller.PurchasesResponse;
import com.example.IMS.dto.*;
import com.example.IMS.dto.mysql.pendingPurchases;
import com.example.IMS.entity.mssql.*;
import com.example.IMS.entity.mysql.Auditlog;
import com.example.IMS.entity.mysql.Smartpurchase;
import com.example.IMS.entity.mysql.Stockmaster;
import com.example.IMS.entity.mysql.TaxPayerInformation;
import com.example.IMS.repository.mssql.*;
import com.example.IMS.repository.mysql.AuditlogRepository;
import com.example.IMS.repository.mysql.SmartpurchaseRepository;
import com.example.IMS.repository.mysql.TaxPayerInfo_repo;
import com.example.IMS.repository.mysql.stockMasterRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

@AllArgsConstructor
@Service
public class service {
    public static String baseUrl = System.getenv("BaseUrl");
    public static String clientTpin = System.getenv("ClientTpin");
    public static String deviceSerialNo = System.getenv("DeviceSerialNo");
    public static String company = System.getenv("SageDatabase");
    public static String branchId = "000";
    private final Icitem_repo icitemRepository;
    private final Icitemo_repo icitemoRepository;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final Porcph1_repo porcph1Repo;
    private final Porcph2_repo porcph2Repo;
    private final Porcpl_repo porcplRepo;
    private final Poopt_repo pooptRepo;
    private final Poporh1_repo poporh1Repo;
    private final Poporh2_repo poporh2Repo;
    private final Poporl_repo poporlRepo;
    private final CsoptfdRepository csoptfdRepo;
    private final IcwcodRepository icwcodRepo ;
    private final AuditlogRepository auditlogRepository;
    private final Apven_repo apvenRepo;
    private final stockMasterRepo stock_master_repo;
    private final ApoblRepository apoblRepo;
    private final Arobl_repo aroblRepo;
    private final Poinvh1Repository poinvh1Repository;
    private final TaxPayerInfo_repo taxPayerInfoRepo;
    private final  Icucod_repo IcucodRepo;
    private  final Icival_repo icival_repo;
    private final PoinvlRepository poinvlRepository;
    private final PoporcRepository poporcRepository;
    private AuditlogRepository auditlogRepo;
    private final IcadehRepository icadehRepository;
    private final IcadedRepository icadedRepository;
    private final IcoptRepository icoptRepository;

    public String validateReference(String ref) {
        if (ref.length() > 80) {
            return ref.substring(0, 79);
        }
        return ref;
    }

    public Boolean initialize_device(initializeDTO initializeDto) {
        System.out.println(initializeDto);
        String url = baseUrl + "/initializer/selectInitInfo";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("tpin",initializeDto.getTpin() );
        requestBody.put("bhfId", initializeDto.getBhfId());
        requestBody.put("dvcSrlNo", initializeDto.getDvcSrlNo());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            if (jsonObject.getString("resultCd").equals("000")) {
                JSONObject txjsonObject = jsonObject.getJSONObject("data").getJSONObject("info");
                // Create an AuditLog object
                Auditlog audt = auditlogRepo.findByDocumentId("111111");
                if (audt != null){
                  audt.setRequestDate(Instant.now());
                  audt.setResponseMessage(jsonObject.getString("resultMsg"));
                  audt.setResponseCode(jsonObject.getString("resultCd"));
                  auditlogRepo.save(audt);
                }
                else{
                    Auditlog auditLog = new Auditlog();
                    auditLog.setTpin(clientTpin);
                    auditLog.setRequestDate(Instant.now());
                    auditLog.setDocumentId("111111");
                    auditLog.setResponseMessage(jsonObject.getString("resultMsg"));
                    auditLog.setRequestUrl("/initialize_device");
                    auditLog.setLastRequestDate(getCurrentDateTime());
                    auditLog.setRequestBody(requestBody.toString());
                    auditLog.setResponseCode(jsonObject.getString("resultCd"));
                    auditLog.setTransactionType("0");
                    auditlogRepo.save(auditLog);
                }

                TaxPayerInformation taxPayerInformation = new TaxPayerInformation();
                taxPayerInformation.setTpin((String) txjsonObject.get("tin"));
                taxPayerInformation.setTaxPayerName((String) txjsonObject.get("taxprNm"));
                taxPayerInformation.setBusinessActivity(txjsonObject.get("bsnsActv") != null ? (String) txjsonObject.get("bsnsActv") : "");
                taxPayerInformation.setBranchId((String) txjsonObject.get("bhfId"));
                taxPayerInformation.setBranchName((String) txjsonObject.get("bhfNm"));
                taxPayerInformation.setBranchDateCreated(String.valueOf(new Date((String) txjsonObject.get("bhfOpenDt"))));
                taxPayerInformation.setProvince((String) txjsonObject.get("prvncNm"));
                taxPayerInformation.setDistrict(txjsonObject.get("dstrtNm") != null ? (String) txjsonObject.get("dstrtNm") : "");
                taxPayerInformation.setSector((String) txjsonObject.get("sctrNm"));
                taxPayerInformation.setLocation((String) txjsonObject.get("locDesc"));
                taxPayerInformation.setHq((String) txjsonObject.get("hqYn"));
                taxPayerInformation.setManagerName((String) txjsonObject.get("mgrNm"));
                taxPayerInformation.setManagerNumber((String) txjsonObject.get("mgrTelNo"));
                taxPayerInformation.setManagerEmail((String) txjsonObject.get("mgrEmail"));
                taxPayerInformation.setDeviceid((String) txjsonObject.get("sdcId"));
                taxPayerInformation.setSdcid((String) txjsonObject.get("sdcId"));
                taxPayerInformation.setMrcid((String) txjsonObject.get("mrcNo"));
                taxPayerInformation.setInternalKey("");
                taxPayerInformation.setSignKey("");
                taxPayerInformation.setLastSaleInvoiceNumber(txjsonObject.get("lastSaleInvcNo") != null ? txjsonObject.get("lastSaleInvcNo").toString() : "");
                taxPayerInformation.setLastPurchaseInvoiceNumber(txjsonObject.get("lastPchsInvcNo") != null ? txjsonObject.get("lastPchsInvcNo").toString() : "");
                taxPayerInformation.setLastSaleReceiptNumber(txjsonObject.get("lastSaleRcptNo") != null ? txjsonObject.get("lastSaleRcptNo").toString() : "");
                taxPayerInformation.setLastInvoiceNumber(txjsonObject.get("lastInvcNo") != null ? txjsonObject.get("lastInvcNo").toString() : "");
                taxPayerInformation.setLastProformaInvoiceNumber(txjsonObject.get("lastProfrmInvcNo") != null ? txjsonObject.get("lastProfrmInvcNo").toString() : "");
                taxPayerInformation.setLastCopyInvoiceNumber(txjsonObject.get("lastCopyInvcNo") != null ? txjsonObject.get("lastCopyInvcNo").toString() : "");
                taxPayerInfoRepo.save(taxPayerInformation);
                return true;

            } else {

                Auditlog auditLog = new Auditlog();
                auditLog.setTpin(clientTpin);
                auditLog.setRequestDate(Instant.now());
                auditLog.setDocumentId("111111");
                auditLog.setResponseMessage(jsonObject.getString("resultMsg"));
                auditLog.setRequestUrl("item/saveItems");
                auditLog.setLastRequestDate(getCurrentDateTime());
                auditLog.setRequestBody(requestBody.toString());
                auditLog.setResponseCode(jsonObject.getString("resultCd"));
                auditLog.setTransactionType("0");
                auditlogRepo.save(auditLog);
               return false;

            }
        } else {
            return false;

        }

    }
    public Boolean findInstalledDevice(){
        Optional<Auditlog> auditlog = auditlogRepository.findInstalledDevice();
        return auditlog.isPresent();
    }
    public static String getFiscalYear() {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();

        // Assuming the fiscal year starts on January 1st
        if (currentDate.isBefore(LocalDate.of(currentYear, Month.JANUARY, 1))) {
            return String.valueOf(currentYear - 1); // If before January, return last year
        } else {
            return String.valueOf(currentYear); // Otherwise, return the current year
        }
    }
    public static Short getFiscalPeriod() {
        LocalDate currentDate = LocalDate.now();

        // Assuming the fiscal year starts in January and has 12 periods (months)
        return (short) currentDate.getMonthValue(); // Returns the month as the fiscal period
    }


    public List<selectItemDTO> getStockItems() throws JsonProcessingException {

        selectImportDataDTO importDataDTO = new selectImportDataDTO();
        importDataDTO.setTpin(clientTpin);
        importDataDTO.setBhfId(branchId);
        importDataDTO.setLastReqDt("20231215000000");
        //importDataDTO.setDclRefNum(refNumber);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<selectImportDataDTO> requestEntity = new HttpEntity<>(importDataDTO, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/zrasandboxvsdc/stock/selectStockItems", requestEntity, String.class);
        System.out.print(response);
        if (response.getStatusCode() == HttpStatus.OK) {
            String jsonResponse = response.getBody(); // Use actual response body here
            JSONObject jsonObject = new JSONObject(jsonResponse);
            if (jsonObject.getString("resultCd").equals("000")) {
                JSONArray itemListNode = jsonObject.getJSONObject("data").getJSONArray("stockList");
                List<selectItemDTO> itemList = objectMapper.readValue(itemListNode.toString(), new TypeReference<>() {
                });
                return itemList;
            }
        }
        return null;
    }
    public List<selectItemDTO> getItems() throws JsonProcessingException {

        selectImportDataDTO importDataDTO = new selectImportDataDTO();
        importDataDTO.setTpin(clientTpin);
        importDataDTO.setBhfId(branchId);
        importDataDTO.setLastReqDt("20231215000000");
        //importDataDTO.setDclRefNum(refNumber);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<selectImportDataDTO> requestEntity = new HttpEntity<>(importDataDTO, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/zrasandboxvsdc/items/selectItems/", requestEntity, String.class);
        System.out.print(response);
        //String jsonResponse = "{\"resultCd\":\"000\",\"resultMsg\":\"It is succeeded\",\"resultDt\":\"20231120194118\",\"data\":{\"itemList\":[{\"taskCd\":\"2239078\",\"dclDe\":\"20240912\",\"itemSeq\":1,\"dclNo\":\"C3460-2019-TZDL\",\"hsCd\":\"20055900005\",\"itemNm\":\"BAKED BEANS\",\"imptItemsttsCd\":\"2\",\"orgnNatCd\":\"BR\",\"exptNatCd\":\"BR\",\"pkg\":2922,\"pkgUnitCd\":null,\"qty\":19946,\"qtyUnitCd\":\"KGM\",\"totWt\":19945.57,\"netWt\":19945.57,\"spplrNm\":\"ZERRIS CREATIONS\",\"agntNm\":\"BN METRO Ltd\",\"invcFcurAmt\":296865.6,\"invcFcurCd\":\"USD\",\"invcFcurExcrt\":929.79},{\"taskCd\":\"2239078\",\"dclDe\":\"20240917\",\"itemSeq\":1,\"dclNo\":\"C3460-2019-TZDL\",\"hsCd\":\"20055900000\",\"itemNm\":\"BAKED BEANS\",\"imptItemsttsCd\":\"2\",\"orgnNatCd\":\"BR\",\"exptNatCd\":\"BR\",\"pkg\":2922,\"pkgUnitCd\":null,\"qty\":19946,\"qtyUnitCd\":\"KGM\",\"totWt\":19945.57,\"netWt\":19945.57,\"spplrNm\":\"ODERICH CONSERVA QUALIDADE\\nBRASIL\",\"agntNm\":\"BN METRO Ltd\",\"invcFcurAmt\":296865.6,\"invcFcurCd\":\"USD\",\"invcFcurExcrt\":929.79}]}}";

        if (response.getStatusCode() == HttpStatus.OK) {
            String jsonResponse = response.getBody(); // Use actual response body here
            JSONObject jsonObject = new JSONObject(jsonResponse);
            if (jsonObject.getString("resultCd").equals("000")) {
                JSONArray itemListNode = jsonObject.getJSONObject("data").getJSONArray("itemList");
                List<selectItemDTO> itemList = objectMapper.readValue(itemListNode.toString(), new TypeReference<>() {
                });
                return itemList;
            }
        }
        return null;
    }


    public List<importItemListDTO> getImports(String refnumber) throws JsonProcessingException {

        selectImportDataDTO importDataDTO = new selectImportDataDTO();
        importDataDTO.setTpin(clientTpin);
        importDataDTO.setBhfId(branchId);
        importDataDTO.setLastReqDt("20240918133803");
        importDataDTO.setDclRefNum(refnumber!= null? refnumber.trim() : "");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<selectImportDataDTO> requestEntity = new HttpEntity<>(importDataDTO, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/zrasandboxvsdc/imports/selectImportItems", requestEntity, String.class);
        System.out.print(response);
        //String jsonResponse = "{\"resultCd\":\"000\",\"resultMsg\":\"It is succeeded\",\"resultDt\":\"20231120194118\",\"data\":{\"itemList\":[{\"taskCd\":\"2239078\",\"dclDe\":\"20240912\",\"itemSeq\":1,\"dclNo\":\"C3460-2019-TZDL\",\"hsCd\":\"20055900005\",\"itemNm\":\"BAKED BEANS\",\"imptItemsttsCd\":\"2\",\"orgnNatCd\":\"BR\",\"exptNatCd\":\"BR\",\"pkg\":2922,\"pkgUnitCd\":null,\"qty\":19946,\"qtyUnitCd\":\"KGM\",\"totWt\":19945.57,\"netWt\":19945.57,\"spplrNm\":\"ZERRIS CREATIONS\",\"agntNm\":\"BN METRO Ltd\",\"invcFcurAmt\":296865.6,\"invcFcurCd\":\"USD\",\"invcFcurExcrt\":929.79},{\"taskCd\":\"2239078\",\"dclDe\":\"20240917\",\"itemSeq\":1,\"dclNo\":\"C3460-2019-TZDL\",\"hsCd\":\"20055900000\",\"itemNm\":\"BAKED BEANS\",\"imptItemsttsCd\":\"2\",\"orgnNatCd\":\"BR\",\"exptNatCd\":\"BR\",\"pkg\":2922,\"pkgUnitCd\":null,\"qty\":19946,\"qtyUnitCd\":\"KGM\",\"totWt\":19945.57,\"netWt\":19945.57,\"spplrNm\":\"ODERICH CONSERVA QUALIDADE\\nBRASIL\",\"agntNm\":\"BN METRO Ltd\",\"invcFcurAmt\":296865.6,\"invcFcurCd\":\"USD\",\"invcFcurExcrt\":929.79}]}}";

        if (response.getStatusCode() == HttpStatus.OK) {
            String jsonResponse = response.getBody(); // Use actual response body here
            JSONObject jsonObject = new JSONObject(jsonResponse);
            if (jsonObject.getString("resultCd").equals("000")) {
                JSONArray itemListNode = jsonObject.getJSONObject("data").getJSONArray("itemList");
                List<importItemListDTO> itemList = objectMapper.readValue(itemListNode.toString(), new TypeReference<>() {
                });
                return itemList;
            }
        }
        return null;
    }


    public List<Icitem> returnItems(){
        return icitemRepository.findAll();
    }

    public Double setDoubleScale(BigDecimal orgNum){
        BigDecimal scaledValue = orgNum.setScale(4, RoundingMode.HALF_UP);
        return Double.parseDouble(String.valueOf(scaledValue));
    }
    private final SmartpurchaseRepository smartpurchaseRepository;
    public purchaseDTO createpurchaseDTOReject(postPODTO request) throws JsonProcessingException {

        String url = baseUrl + "/trnsPurchase/selectTrnsPurchaseSales";
        selectSales selectSales = new selectSales();
        selectSales.setTpin(clientTpin);
        selectSales.setBhfId(branchId);
        selectSales.setLastReqDt("20231215000000");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<selectSales> requestEntity = new HttpEntity<>(selectSales, headers);
        purchaseDTO save_purchase = new purchaseDTO();
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            String jsonResponse = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode saleList = rootNode.path("data").path("saleList");
            List<purchaseItemDTO> purchaseItem = new ArrayList<>();
            if (saleList != null) {
                for (JsonNode sale : saleList) {
                    int invoiceNumber = sale.path("spplrInvcNo").asInt();
                    if (invoiceNumber == Integer.parseInt(request.getInvoiceNumber())) {
                            for (JsonNode item : sale.path("itemList")) {



                                purchaseItemDTO itemlist = new purchaseItemDTO();
                                itemlist.setItemSeq(item.path("itemSeq").asInt());
                                itemlist.setItemCd(item.path("itemCd").asText());
                                itemlist.setItemClsCd(item.path("itemClsCd").asText());
                                itemlist.setItemNm(item.path("itemNm").asText());
                                itemlist.setBcd(item.path("bcd").asText());
                                itemlist.setPkgUnitCd(item.path("pkgUnitCd").asText());
                                itemlist.setPkg(item.path("pkg").doubleValue());
                                itemlist.setQtyUnitCd(item.path("qtyUnitCd").asText());
                                itemlist.setQty(Double.parseDouble(String.valueOf(item.path("qty").asInt())));
                                itemlist.setPrc(item.path("prc").asInt());
                                itemlist.setSplyAmt(setDoubleScale(item.path("splyAmt").decimalValue()));
                                itemlist.setDcRt(item.path("dcRt").doubleValue());
                                itemlist.setDcAmt(item.path("dcAmt").doubleValue());
                                itemlist.setIplCatCd("");
                                itemlist.setTaxTyCd(item.path("vatCatCd").asText());
                                itemlist.setTlCatCd("");
                                itemlist.setExciseCatCd(item.path("exciseCatCd").asText());
                                itemlist.setTaxblAmt(item.path("taxblAmt").asDouble());
                                itemlist.setVatCatCd(item.path("vatCatCd").asText());
                                itemlist.setIplTaxblAmt(item.path("iplTaxblAmt").asDouble());
                                itemlist.setTlTaxblAmt(item.path("tlTaxblAmt").asDouble());
                                itemlist.setExciseTaxblAmt(item.path("exciseTaxbleAmt").asDouble());
                                itemlist.setTaxAmt(item.path("taxAmt").asDouble());
                                itemlist.setIplAmt(item.path("iplAmt").asDouble());
                                itemlist.setTlAmt(item.path("tlAmt").asDouble());
                                itemlist.setExciseTaxblAmt(item.path("exciseTaxbleAmt").asDouble());
                                itemlist.setTotAmt(item.path("totAmt").asDouble());
                                purchaseItem.add(itemlist);
                            }

                            save_purchase.setTpin(clientTpin);
                            save_purchase.setBhfId(branchId);
                            save_purchase.setCisInvcNo("0");
                            save_purchase.setOrgInvcNo(0);
                            save_purchase.setSpplrTpin(sale.path("spplrTpin").asText().replace(" ",""));
                            save_purchase.setSpplrBhfId(sale.path("spplrBhfId").asText());
                            save_purchase.setSpplrNm(sale.path("spplrNm").asText());
                            save_purchase.setSpplrInvcNo(sale.path("spplrInvcNo").asText());
                            save_purchase.setRegTyCd("A");
                            save_purchase.setPchsTyCd("N");
                            save_purchase.setRcptTyCd("P");
                            save_purchase.setPmtTyCd("01");
                            save_purchase.setPchsSttsCd("04");
                            save_purchase.setCfmDt(currentDate()+"000000");
                            save_purchase.setPchsDt(sale.path("salesDt").asText());
                            save_purchase.setWrhsDt("");
                            save_purchase.setCnclReqDt("");
                            save_purchase.setCnclDt("");
                            save_purchase.setRfdDt("");
                            save_purchase.setTotItemCnt(sale.path("totItemCnt").asInt());
                            save_purchase.setTotTaxblAmt(sale.path("totTaxblAmt").asDouble());
                            save_purchase.setTotTaxAmt(sale.path("totTaxAmt").asDouble());
                            save_purchase.setTotAmt(sale.path("totAmt").asDouble());
                            save_purchase.setRemark(request.getRemark());
                            save_purchase.setRegrNm("ADMIN");
                            save_purchase.setRegrId("ADMIN");
                            save_purchase.setModrNm("ADMIN");
                            save_purchase.setModrId("ADMIN");
                            save_purchase.setItemList(purchaseItem);


                            // createPOInvoice(filteredSales,po_number,items);

                            // createPurchaseOrderReceipt(filteredSales, po_number,items);

                        }
                    }
                }
            }

        return save_purchase ;
        }
    /*public purchaseDTO createpurchaseDTO(postPODTO request) throws JsonProcessingException {

        String url = baseUrl + "/trnsPurchase/selectTrnsPurchaseSales";
        selectSales selectSales = new selectSales();
        selectSales.setTpin(clientTpin);
        selectSales.setBhfId(branchId);
        selectSales.setLastReqDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        // Poporh1 poporh1 = poporh1Repo.findByPonumberContaining(po_number);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<selectSales> requestEntity = new HttpEntity<>(selectSales, headers);
        purchaseDTO save_purchase = new purchaseDTO();
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            //String jsonResponse = "{\"resultCd\":\"000\",\"resultMsg\":\"It is succeeded\",\"resultDt\":\"20240510103403\",\"data\":{\"saleList\":[{\"spplrTpin\":\"1019249900                    \",\"spplrNm\":\"SMART SUPPLIER\",\"spplrBhfId\":\"000\",\"spplrInvcNo\":59,\"rcptTyCd\":\"S\",\"pmtTyCd\":\"01\",\"cfmDt\":\"2024-05-08 10:20:10\",\"salesDt\":\"20240502\",\"stockRlsDt\":null,\"totItemCnt\":1,\"totTaxblAmt\":86.2069,\"totTaxAmt\":13.7931,\"totAmt\":100,\"remark\":null,\"itemList\":[{\"itemSeq\":1,\"itemCd\":\"20044\",\"itemClsCd\":\"50102517\",\"itemNm\":\"ChickenWings\",\"bcd\":null,\"pkgUnitCd\":\"BA\",\"pkg\":0,\"qtyUnitCd\":\"BE\",\"qty\":1,\"prc\":100,\"splyAmt\":100,\"dcRt\":0,\"dcAmt\":0,\"vatCatCd\":\"A\",\"iplCatCd\":null,\"tlCatCd\":null,\"exciseTxCatCd\":null,\"vatTaxblAmt\":86.21,\"exciseTaxblAmt\":0,\"iplTaxblAmt\":0,\"tlTaxblAmt\":0,\"taxblAmt\":86.21,\"vatAmt\":13.79,\"iplAmt\":0,\"tlAmt\":0,\"exciseTxAmt\":0,\"totAmt\":100}]},{\"spplrTpin\":\"1019249900                    \",\"spplrNm\":\"SMART SUPPLIER\",\"spplrBhfId\":\"000\",\"spplrInvcNo\":46,\"rcptTyCd\":\"S\",\"pmtTyCd\":\"01\",\"cfmDt\":\"2024-05-08 10:20:10\",\"salesDt\":\"20240502\",\"stockRlsDt\":null,\"totItemCnt\":1,\"totTaxblAmt\":86.2069,\"totTaxAmt\":13.7931,\"totAmt\":100,\"remark\":null,\"itemList\":[{\"itemSeq\":1,\"itemCd\":\"20044\",\"itemClsCd\":\"50102517\",\"itemNm\":\"ChickenWings\",\"bcd\":null,\"pkgUnitCd\":\"BA\",\"pkg\":0,\"qtyUnitCd\":\"BE\",\"qty\":1,\"prc\":100,\"splyAmt\":100,\"dcRt\":0,\"dcAmt\":0,\"vatCatCd\":\"A\",\"iplCatCd\":null,\"tlCatCd\":null,\"exciseTxCatCd\":null,\"vatTaxblAmt\":86.21,\"exciseTaxblAmt\":0,\"iplTaxblAmt\":0,\"tlTaxblAmt\":0,\"taxblAmt\":86.21,\"vatAmt\":13.79,\"iplAmt\":0,\"tlAmt\":0,\"exciseTxAmt\":0,\"totAmt\":100}]}]}}";
            String jsonResponse = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode saleList = rootNode.path("data").path("saleList");
            List<purchaseItemDTO> purchaseItem = new ArrayList<>();
            if (saleList != null) {
                for (JsonNode sale : saleList) {
                    int invoiceNumber = sale.path("spplrInvcNo").asInt();
                    if (invoiceNumber == Integer.parseInt(request.getInvoiceNumber())) {
                        Poinvh1 poinvh1 = poinvh1Repository.findByRcpnumber(request.getPoNumber());
                        if (Objects.equals(sale.path("totItemCnt").decimalValue().setScale(4,RoundingMode.HALF_UP), poinvh1.getRqreceived())) {
                            for (JsonNode item : sale.path("itemList")) {
                                String rawItem = "";

                                List<SageItemDTO> sageItemDTOS = request.getSageItems();
                                for (SageItemDTO sageItemDTO : sageItemDTOS) {
                                    if (sageItemDTO.getItemName().equals(item.path("itemNm").asText())) {
                                        rawItem = sageItemDTO.getSageItem();
                                    }
                                }
                                Icitem o = icitemRepository.findByFmtitemno(rawItem);
                                itemInformationDTO iCode = items(o);

                                purchaseItemDTO itemlist = new purchaseItemDTO();
                                itemlist.setItemSeq(item.path("itemSeq").asInt());
                                itemlist.setItemCd(iCode.getItemCode().replace(" ", ""));
                                itemlist.setItemClsCd(iCode.getItemClassCode().replace(" ", ""));
                                itemlist.setItemNm(iCode.getItemName());
                                itemlist.setBcd("");
                                itemlist.setPkgUnitCd(iCode.getPackagingUnit().replace(" ", ""));
                                itemlist.setPkg(1.0);
                                itemlist.setQtyUnitCd("U");
                                itemlist.setQty(Double.parseDouble(String.valueOf(item.path("qty").asInt())));
                                itemlist.setPrc(item.path("prc").asInt());
                                itemlist.setSplyAmt(setDoubleScale(item.path("splyAmt").decimalValue()));
                                itemlist.setDcRt(0.0);
                                itemlist.setDcAmt(0.0);
                                itemlist.setIplCatCd("");
                                itemlist.setTaxTyCd(item.path("vatCatCd").asText());
                                itemlist.setTlCatCd("");
                                itemlist.setExciseCatCd("");
                                itemlist.setTaxblAmt(item.path("vatTaxbleAmt").asDouble());
                                itemlist.setVatCatCd(item.path("vatCatCd").asText());
                                itemlist.setIplTaxblAmt(0.0);
                                itemlist.setTlTaxblAmt(0.0);
                                itemlist.setExciseTaxblAmt(0.0);
                                itemlist.setTaxAmt(item.path("vatAmt").asDouble());
                                itemlist.setIplAmt(0.0);
                                itemlist.setTlAmt(0.0);
                                itemlist.setExciseTaxblAmt(0.0);
                                itemlist.setTotAmt(item.path("totAmt").asDouble());
                                purchaseItem.add(itemlist);
                            }
                            save_purchase.setTpin(clientTpin);
                            save_purchase.setBhfId(branchId);
                            save_purchase.setCisInvcNo(poinvh1.getInvnumber());
                            save_purchase.setOrgInvcNo(0);
                            save_purchase.setSpplrTpin(sale.path("spplrTpin").asText().replace(" ",""));
                            save_purchase.setSpplrBhfId(sale.path("spplrBhfId").asText());
                            save_purchase.setSpplrNm(sale.path("spplrNm").asText());
                            save_purchase.setSpplrInvcNo(sale.path("spplrInvcNo").asText());
                            save_purchase.setRegTyCd("A");
                            save_purchase.setPchsTyCd("N");
                            save_purchase.setRcptTyCd("P");
                            save_purchase.setPmtTyCd("01");
                            save_purchase.setPchsSttsCd("02");
                            save_purchase.setCfmDt(currentDate() +"000000");
                            save_purchase.setPchsDt(sale.path("salesDt").asText());
                            save_purchase.setWrhsDt("");
                            save_purchase.setCnclReqDt("");
                            save_purchase.setCnclDt("");
                            save_purchase.setRfdDt("");
                            save_purchase.setTotItemCnt(sale.path("totItemCnt").asInt());
                            save_purchase.setTotTaxblAmt(sale.path("totTaxblAmt").asDouble());
                            save_purchase.setTotTaxAmt(sale.path("totTaxAmt").asDouble());
                            save_purchase.setTotAmt(sale.path("totAmt").asDouble());
                            save_purchase.setRemark(poinvh1.getDescriptio());
                            save_purchase.setRegrNm("ADMIN");
                            save_purchase.setRegrId("ADMIN");
                            save_purchase.setModrNm("ADMIN");
                            save_purchase.setModrId("ADMIN");
                            save_purchase.setItemList(purchaseItem);
                        } else {
                            for (JsonNode item : sale.path("itemList")) {
                                String rawItem = "";

                                List<SageItemDTO> sageItemDTOS = request.getSageItems();

                                for (SageItemDTO sageItemDTO : sageItemDTOS) {
                                    System.out.println(""+sageItemDTO.getItemName()+""+item.path("itemNm").asText());
                                    if (sageItemDTO.getItemName().equals(item.path("itemNm").asText())) {
                                        rawItem = sageItemDTO.getSageItem();
                                    }
                                }
                                Icitem o = icitemRepository.findByFmtitemno(rawItem);
                                itemInformationDTO iCode = items(o);

                                purchaseItemDTO itemlist = new purchaseItemDTO();
                                itemlist.setItemSeq(item.path("itemSeq").asInt());
                                itemlist.setItemCd(iCode.getItemCode().replace(" ", ""));
                                itemlist.setItemClsCd(iCode.getItemClassCode().replace(" ", ""));
                                itemlist.setItemNm(iCode.getItemName());
                                itemlist.setBcd("");
                                itemlist.setPkgUnitCd(iCode.getPackagingUnit().replace(" ", ""));
                                itemlist.setPkg(1.0);
                                itemlist.setQtyUnitCd("U");
                                itemlist.setQty(Double.parseDouble(String.valueOf(item.path("qty").asInt())));
                                itemlist.setPrc(item.path("prc").asInt());
                                itemlist.setSplyAmt(setDoubleScale(item.path("splyAmt").decimalValue()));
                                itemlist.setDcRt(0.0);
                                itemlist.setDcAmt(0.0);
                                itemlist.setIplCatCd("");
                                itemlist.setTaxTyCd(item.path("vatCatCd").asText());
                                itemlist.setTlCatCd("");
                                itemlist.setExciseCatCd("");
                                itemlist.setTaxblAmt(item.path("vatTaxblAmt").asDouble());
                                itemlist.setVatCatCd(item.path("vatCatCd").asText());
                                itemlist.setIplTaxblAmt(0.0);
                                itemlist.setTlTaxblAmt(0.0);
                                itemlist.setExciseTaxblAmt(0.0);
                                itemlist.setTaxAmt(item.path("vatAmt").asDouble());
                                itemlist.setIplAmt(0.0);
                                itemlist.setTlAmt(0.0);
                                itemlist.setExciseTaxblAmt(0.0);
                                itemlist.setTotAmt(item.path("totAmt").asDouble());
                                purchaseItem.add(itemlist);
                            }

                            save_purchase.setTpin(clientTpin);
                            save_purchase.setBhfId(branchId);
                            save_purchase.setCisInvcNo(poinvh1.getInvnumber());
                            save_purchase.setOrgInvcNo(0);
                            save_purchase.setSpplrTpin(sale.path("spplrTpin").asText().replace(" ",""));
                            save_purchase.setSpplrBhfId(sale.path("spplrBhfId").asText());
                            save_purchase.setSpplrNm(sale.path("spplrNm").asText());
                            save_purchase.setSpplrInvcNo(sale.path("spplrInvcNo").asText());
                            save_purchase.setRegTyCd("A");
                            save_purchase.setPchsTyCd("N");
                            save_purchase.setRcptTyCd("P");
                            save_purchase.setPmtTyCd("01");
                            save_purchase.setPchsSttsCd("04");
                            save_purchase.setCfmDt(currentDate()+"000000");
                            save_purchase.setPchsDt(sale.path("salesDt").asText());
                            save_purchase.setWrhsDt("");
                            save_purchase.setCnclReqDt("");
                            save_purchase.setCnclDt("");
                            save_purchase.setRfdDt("");
                            save_purchase.setTotItemCnt(sale.path("totItemCnt").asInt());
                            save_purchase.setTotTaxblAmt(sale.path("totTaxblAmt").asDouble());
                            save_purchase.setTotTaxAmt(sale.path("totTaxAmt").asDouble());
                            save_purchase.setTotAmt(sale.path("totAmt").asDouble());
                            save_purchase.setRemark(poinvh1.getDescriptio());
                            save_purchase.setRegrNm("ADMIN");
                            save_purchase.setRegrId("ADMIN");
                            save_purchase.setModrNm("ADMIN");
                            save_purchase.setModrId("ADMIN");
                            save_purchase.setItemList(purchaseItem);


                            // createPOInvoice(filteredSales,po_number,items);

                            // createPurchaseOrderReceipt(filteredSales, po_number,items);

                        }
                    }
                }
            }
            }
        return save_purchase ;
        }

*/
    public Boolean postToSageReject(postPODTO request ) throws JsonProcessingException {
                String url = "http://localhost:8080/zrasandboxvsdc/trnsPurchase/savePurchase";
                purchaseDTO dto = createpurchaseDTOReject(request);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<purchaseDTO> requestEntity = new HttpEntity<>(dto, headers);
                System.out.println(requestEntity);
                ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    JSONObject jsonObject = new JSONObject(response.getBody());
                    System.out.println(jsonObject);
                    if (jsonObject.getString("resultCd").equals("000")) {
                        Smartpurchase smartpurchase = new Smartpurchase();
                        smartpurchase.setSpplrInvc(Integer.valueOf(dto.getSpplrInvcNo()));
                        smartpurchaseRepository.save(smartpurchase);
                        return true;


                    } else {

                        return false;

                    }
                }
                return false;
            }

    public Boolean postToSage(postPODTO request ) throws JsonProcessingException {
        System.out.print(request.getSageItems());
        String comment = "Supplier Invoice:"+request.getInvoiceNumber()+",";
                Poporh1 poporh1 = poporh1Repo.findByPonumberContaining(request.getPoNumber());
                poporh1.setComment(comment);
                poporh1Repo.save(poporh1);
        Smartpurchase smartpurchase = new Smartpurchase();
        smartpurchase.setSpplrInvc(Integer.valueOf(request.getInvoiceNumber()));
        smartpurchaseRepository.save(smartpurchase);
                for( SageItemDTO sageItemDTO : request.getSageItems()){
                    String itemComment = String.format("Supplier item: %s, Supplier item code: %s, Class code: %s",
                            sageItemDTO.getItemName(), sageItemDTO.getItemCode(), sageItemDTO.getItemClassCode());
                    Poporl poporl = poporlRepo.findByIdAndItemdesc(poporh1.getId(),sageItemDTO.getSageItem());

                    Poporc poporc = new Poporc();
                    PoporcId poporcId = new PoporcId();
                    poporcId.setPorhseq(poporh1.getId());
                    poporcId.setPorcrev(poporl.getId().getPorlrev().divide(new BigDecimal(100),0,RoundingMode.HALF_UP));
                    poporc.setId(poporcId);
                    poporc.setAudtdate(currentDate());
                    poporc.setAudttime(currentTime());
                    poporc.setAudtorg(company);
                    poporc.setAudtuser("ADMIN");
                    poporc.setPorcseq(poporl.getPorcseq());
                    poporc.setCommenttyp((short)1);
                    poporc.setIndbtable((short)1);
                    poporc.setComment(validateReference(itemComment));
                    poporcRepository.save(poporc);
                    poporl.setHascomment((short)1);
                    poporlRepo.save(poporl);
                }

        /*String url = "http://localhost:8080/zrasandboxvsdc/trnsPurchase/savePurchase";
                purchaseDTO dto = createpurchaseDTO(request);
                String status = dto.getPchsSttsCd();

                List<String> itemcode = new ArrayList<>();
                for(purchaseItemDTO purchasI : dto.getItemList()){
                  itemcode.add(purchasI.getItemCd());
                }
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                Apobl obj = apoblRepo.findByIdinvc(dto.getInvcNo());
                Porcph1 porcph1 = porcph1Repo.findByInvnumber(dto.getInvcNo());
                HttpEntity<purchaseDTO> requestEntity = new HttpEntity<>(dto, headers);
                System.out.println(requestEntity);
                ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    JSONObject jsonObject = new JSONObject(response.getBody());
                    System.out.println(jsonObject);
                    if (jsonObject.getString("resultCd").equals("000")) {
                        porcph1.setComment(dto.getSpplrInvcNo());
                        porcph1Repo.save(porcph1);
                        Auditlog auditLog = new Auditlog();
                        auditLog.setTpin(clientTpin);
                        auditLog.setRequestDate(Instant.now());
                        auditLog.setDocumentId(String.valueOf(requestEntity.getBody().getInvcNo()));
                        auditLog.setResponseMessage(jsonObject.getString("resultMsg"));
                        auditLog.setRequestUrl(url);
                        auditLog.setLastRequestDate(getCurrentDateTime());
                        auditLog.setRequestBody(requestEntity.toString());
                        auditLog.setTransactionType("2");
                        auditLog.setResponseCode(jsonObject.getString("resultCd"));
                        auditLog.setPurchaseTransactionType("A");
                        auditlogRepo.save(auditLog);
                        if (obj.getTxttrxtype() == 1 && status.equals("02")) {
                            addStockItem(dto.getInvcNo(), "02");
                            addImportStockMaster(itemcode);
                        }
                        else if (obj.getTxttrxtype() == 3 && status.equals("02")) {

                            adjustStockItem(dto.getInvcNo() , "03");

                        }*/


                        return true;


                    /*} else {
                        Auditlog auditLog = new Auditlog();
                        auditLog.setTpin(clientTpin);
                        auditLog.setRequestDate(Instant.now());
                        auditLog.setDocumentId(String.valueOf(requestEntity.getBody().getInvcNo()));
                        auditLog.setResponseMessage(jsonObject.getString("resultMsg"));
                        auditLog.setRequestUrl(url);
                        auditLog.setLastRequestDate(getCurrentDateTime());
                        auditLog.setRequestBody(requestEntity.toString());
                        auditLog.setTransactionType("2");
                        auditLog.setResponseCode(jsonObject.getString("resultCd"));
                        auditLog.setPurchaseTransactionType("A");
                        auditlogRepo.save(auditLog);
                        return false;

                    }
                }
                return false;*/
            }

    public void addStockItem(String seq_no, String sarcode) {
        String url = baseUrl+"/stock/saveStockItems";
        BigDecimal exrate;

        Poinvh1 po_obj = poinvh1Repository.findByInvnumber(seq_no);
        Apobl obj = apoblRepo.findByIdinvc(po_obj.getInvnumber());
        exrate = obj.getExchratehc();
        List<Poinvl> po_list = poinvlRepository.findByPorhseqAndStockitem(po_obj.getPorhseq(),(short)1);
        Optional<Apven> vendor = apvenRepo.findByVendorid(po_obj.getVdcode());
        int itemSeq = 1;
        List<stockItemDTO> stockItemList = new ArrayList<>();
        if(!po_list.isEmpty()){
            for (Poinvl item : po_list) {

                String txCode = taxClass.get(item.getTaxclass1());
                BigDecimal price;
                if (item.getTaxinclud1()==1){
                    price = (item.getUnitcost().multiply(exrate)).setScale(4, RoundingMode.HALF_UP);
                }else {
                    price = (((item.getTaxbase1().multiply(obj.getExchratehc()).add((item.getTaxamount1().multiply(obj.getExchratehc())))).divide(item.getOqreceived()))).setScale(4, RoundingMode.HALF_UP);

                }
                BigDecimal suppliedAmt = item.getOqreceived().multiply(price).setScale(4, RoundingMode.HALF_UP);
                Icitem o = icitemRepository.findByFmtitemno(item.getItemno());
                itemInformationDTO iCode = items(o);
                stockItemDTO stockItem = new stockItemDTO();
                stockItem.setItemSeq(itemSeq);
                stockItem.setItemCd(iCode.getItemCode().replace(" ", ""));
                stockItem.setItemNm(iCode.getItemName().replace(" ", ""));
                stockItem.setItemClsCd(iCode.getItemClassCode().replace(" ", ""));
                stockItem.setPkgUnitCd(iCode.getPackagingUnit().replace(" ", ""));
                stockItem.setQtyUnitCd("U");
                stockItem.setQty(Double.parseDouble(String.valueOf(item.getOqreceived())));
                stockItem.setPrc(Double.parseDouble(String.valueOf(price)));
                stockItem.setSplyAmt(Double.parseDouble(String.valueOf(suppliedAmt)));
                stockItem.setVatCatCd(txCode);
                stockItem.setPkg(1.0);
                stockItem.setTotDcAmt(0);
                stockItem.setTotAmt(setDoubleScale(item.getTaxamount1().add(item.getExtended())));
                stockItem.setTaxAmt(setDoubleScale(item.getTaxamount1()));
                stockItem.setTaxblAmt(setDoubleScale(item.getExtended()));
                itemSeq++;
                stockItemList.add(stockItem);
            }
            stockDTO stockItemBody = new stockDTO();
            stockItemBody.setTpin(clientTpin);
            stockItemBody.setBhfId(branchId);
            stockItemBody.setSarNo(Integer.parseInt(String.valueOf(po_obj.getPorhseq())));
            stockItemBody.setRegTyCd("M");
            stockItemBody.setCustTpin(vendor.map(Apven::getBrn).map(brn -> brn.replace(" ", ""))  .filter(brnWithoutSpaces -> brnWithoutSpaces.length() == 10) .orElse(null));
            stockItemBody.setCustNm(vendor.map(Apven::getVendname)
                    .map(vendname -> vendname.replace(" ", ""))
                    .orElse(null));
            stockItemBody.setCustBhfId(null);
            stockItemBody.setSarTyCd(sarcode);
            stockItemBody.setOcrnDt(String.valueOf(po_obj.getDate()));
            stockItemBody.setTotItemCnt(po_obj.getLines());
            stockItemBody.setTotTaxblAmt(setDoubleScale(po_obj.getTaxbase1()));
            stockItemBody.setTotTaxAmt(setDoubleScale(po_obj.getTaxamount1()));
            stockItemBody.setTotAmt(setDoubleScale(po_obj.getDoctotal()));
            stockItemBody.setRemark(null);
            stockItemBody.setRegrId("ADMIN");
            stockItemBody.setRegrNm("ADMIN");
            stockItemBody.setModrId("ADMIN");
            stockItemBody.setModrNm("ADMIN");
            stockItemBody.setItemList(stockItemList);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<stockDTO> requestEntity = new HttpEntity<>(stockItemBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject jsonObject = new JSONObject(response.getBody());
                System.out.println(jsonObject);
                if (jsonObject.getString("resultCd").equals("000")) {

                    Auditlog auditLog = new Auditlog();
                    auditLog.setTpin(clientTpin);
                    auditLog.setRequestDate(Instant.now());
                    auditLog.setDocumentId(String.valueOf(requestEntity.getBody().getSarNo()));
                    auditLog.setResponseMessage(jsonObject.getString("resultMsg"));
                    auditLog.setRequestUrl(url);
                    auditLog.setLastRequestDate(getCurrentDateTime());
                    auditLog.setRequestBody(requestEntity.toString());
                    auditLog.setTransactionType("5");
                    auditLog.setResponseCode(jsonObject.getString("resultCd"));
                    auditlogRepo.save(auditLog);



                } else {
                    Auditlog auditLog = new Auditlog();
                    auditLog.setTpin(clientTpin);
                    auditLog.setRequestDate(Instant.now());
                    auditLog.setDocumentId(String.valueOf(requestEntity.getBody().getSarNo()));

                    auditLog.setResponseMessage(jsonObject.getString("resultMsg"));
                    auditLog.setRequestUrl(url);
                    auditLog.setLastRequestDate(getCurrentDateTime());
                    auditLog.setRequestBody(requestEntity.toString());
                    auditLog.setTransactionType("5");
                    auditLog.setResponseCode(jsonObject.getString("resultCd"));
                    auditlogRepo.save(auditLog);

                }
            }
        }

    }
    public void adjustStockItem(String seq_no, String sarcode) {
        String url = baseUrl+"/stock/saveStockItems";
        BigDecimal exrate;

        Poinvh1 po_obj = poinvh1Repository.findByRcpnumber(seq_no);
        Apobl obj = apoblRepo.findByIdinvc(po_obj.getInvnumber());
        exrate = obj.getExchratehc();
        List<Poinvl> po_list = poinvlRepository.findByPorhseqAndStockitem(po_obj.getPorhseq(),(short)1);
        Optional<Apven> vendor = apvenRepo.findByVendorid(po_obj.getVdcode());
        int itemSeq = 1;
        List<stockItemDTO> stockItemList = new ArrayList<>();
        if(!po_list.isEmpty()){
            for (Poinvl item : po_list) {

                String txCode = taxClass.get(item.getTaxclass1());
                BigDecimal price;
                if (item.getTaxinclud1()==1){
                    price = (item.getUnitcost().multiply(exrate)).setScale(4, RoundingMode.HALF_UP);
                }else {
                    price = (((item.getTaxbase1().multiply(obj.getExchratehc()).add((item.getTaxamount1().multiply(obj.getExchratehc())))).divide(item.getOqreceived()))).setScale(4, RoundingMode.HALF_UP);

                }
                BigDecimal suppliedAmt = item.getOqreceived().multiply(price).setScale(4, RoundingMode.HALF_UP);
                Icitem o = icitemRepository.findByFmtitemno(item.getItemno());
                itemInformationDTO iCode = items(o);
                stockItemDTO stockItem = new stockItemDTO();
                stockItem.setItemSeq(itemSeq);
                stockItem.setItemCd(iCode.getItemCode().replace(" ", ""));
                stockItem.setItemNm(iCode.getItemName().replace(" ", ""));
                stockItem.setItemClsCd(iCode.getItemClassCode().replace(" ", ""));
                stockItem.setPkgUnitCd(iCode.getPackagingUnit().replace(" ", ""));
                stockItem.setQtyUnitCd("U");
                stockItem.setQty(Double.parseDouble(String.valueOf(item.getOqreceived())));
                stockItem.setPrc(Double.parseDouble(String.valueOf(price)));
                stockItem.setSplyAmt(Double.parseDouble(String.valueOf(suppliedAmt)));
                stockItem.setVatCatCd(txCode);
                stockItem.setPkg(1.0);
                stockItem.setTotDcAmt(0);
                stockItem.setTotAmt(setDoubleScale(item.getTaxamount1().add(item.getExtended())));
                stockItem.setTaxAmt(setDoubleScale(item.getTaxamount1()));
                stockItem.setTaxblAmt(setDoubleScale(item.getExtended()));
                itemSeq++;
                stockItemList.add(stockItem);
            }
            stockDTO stockItemBody = new stockDTO();
            stockItemBody.setTpin(clientTpin);
            stockItemBody.setBhfId(branchId);
            stockItemBody.setSarNo(Integer.parseInt(String.valueOf(po_obj.getPorhseq())));
            stockItemBody.setRegTyCd("M");
            stockItemBody.setCustTpin(vendor.map(Apven::getBrn).map(brn -> brn.replace(" ", ""))  .filter(brnWithoutSpaces -> brnWithoutSpaces.length() == 10) .orElse(null));
            stockItemBody.setCustNm(vendor.map(Apven::getVendname)
                    .map(vendname -> vendname.replace(" ", ""))
                    .orElse(null));
            stockItemBody.setCustBhfId(null);
            stockItemBody.setSarTyCd(sarcode);
            stockItemBody.setOcrnDt(String.valueOf(po_obj.getDate()));
            stockItemBody.setTotItemCnt(po_obj.getLines());
            stockItemBody.setTotTaxblAmt(setDoubleScale(po_obj.getTaxbase1()));
            stockItemBody.setTotTaxAmt(setDoubleScale(po_obj.getTaxamount1()));
            stockItemBody.setTotAmt(setDoubleScale(po_obj.getDoctotal()));
            stockItemBody.setRemark(null);
            stockItemBody.setRegrId("ADMIN");
            stockItemBody.setRegrNm("ADMIN");
            stockItemBody.setModrId("ADMIN");
            stockItemBody.setModrNm("ADMIN");
            stockItemBody.setItemList(stockItemList);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<stockDTO> requestEntity = new HttpEntity<>(stockItemBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject jsonObject = new JSONObject(response.getBody());
                System.out.println(jsonObject);
                if (jsonObject.getString("resultCd").equals("000")) {

                    Auditlog auditLog = new Auditlog();
                    auditLog.setTpin(clientTpin);
                    auditLog.setRequestDate(Instant.now());
                    auditLog.setDocumentId(String.valueOf(requestEntity.getBody().getSarNo()));
                    auditLog.setResponseMessage(jsonObject.getString("resultMsg"));
                    auditLog.setRequestUrl(url);
                    auditLog.setLastRequestDate(getCurrentDateTime());
                    auditLog.setRequestBody(requestEntity.toString());
                    auditLog.setTransactionType("5");
                    auditLog.setResponseCode(jsonObject.getString("resultCd"));
                    auditlogRepo.save(auditLog);



                } else {
                    Auditlog auditLog = new Auditlog();
                    auditLog.setTpin(clientTpin);
                    auditLog.setRequestDate(Instant.now());
                    auditLog.setDocumentId(String.valueOf(requestEntity.getBody().getSarNo()));

                    auditLog.setResponseMessage(jsonObject.getString("resultMsg"));
                    auditLog.setRequestUrl(url);
                    auditLog.setLastRequestDate(getCurrentDateTime());
                    auditLog.setRequestBody(requestEntity.toString());
                    auditLog.setTransactionType("5");
                    auditLog.setResponseCode(jsonObject.getString("resultCd"));
                    auditlogRepo.save(auditLog);

                }
            }
        }

    }
    @Retryable( backoff = @Backoff(delay = 2000))
    public  ResponseEntity<Map<String, String>> saveStockMaster(){
        String url = baseUrl+"/stockMaster/saveStockMaster";
        List<stockMasterItemDTO> itemList = new ArrayList<>();
        String todaysDateRaw = new SimpleDateFormat("yyyyMMdd").format(new Date());

        BigDecimal todaysDate = new BigDecimal(todaysDateRaw);
        List<String> stockList = new ArrayList<>();
        Map<String, String> responses = new HashMap<>();

        List<Icival> items = icival_repo.findByAudtdate(todaysDate);

        for (Icival item : items) {
            itemList.add(new stockMasterItemDTO(item.getId().getItemno(), item.getTotalqty().intValue()));
        }
        stockMasterDTO requestBody = new stockMasterDTO(clientTpin, "000", "ADMIN", "ADMIN", "ADMIN", "ADMIN", itemList);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<stockMasterDTO> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            System.out.println(jsonObject);
            String resultCd = jsonObject.getString("resultCd");
            if (jsonObject.getString("resultCd").equals("000")) {
                // Create Audit Log
                Auditlog auditLog = new Auditlog();
                auditLog.setTpin(clientTpin);
                auditLog.setRequestDate(Instant.now());
                auditLog.setDocumentId(requestBody.getStockItemList().getFirst().getItemCd());
                auditLog.setResponseMessage(jsonObject.getString("resultMsg"));
                auditLog.setRequestUrl(url);
                auditLog.setLastRequestDate(getCurrentDateTime());
                auditLog.setRequestBody(requestBody.toString());
                auditLog.setResponseCode(resultCd);
                auditLog.setTransactionType("7");
                auditlogRepo.save(auditLog);

                // Save item information
                Stockmaster stockmaster = new Stockmaster();
                stockmaster.setItemCode(requestBody.getStockItemList().getFirst().getItemCd());
                stockmaster.setItemQuantity(requestBody.getStockItemList().getFirst().getRsdQty());


                stock_master_repo.save(stockmaster);
                responses.put(requestBody.getStockItemList().getFirst().getItemCd(), resultCd);
            } else {
                // Handle error response



                Auditlog auditLog = new Auditlog();
                auditLog.setTpin(clientTpin);
                auditLog.setRequestDate(Instant.now());
                auditLog.setDocumentId(requestBody.getStockItemList().getFirst().getItemCd());
                auditLog.setResponseMessage(jsonObject.getString("resultMsg"));
                auditLog.setRequestUrl(url);
                auditLog.setLastRequestDate(getCurrentDateTime());
                auditLog.setRequestBody(requestBody.toString());
                auditLog.setResponseCode(resultCd);
                auditLog.setTransactionType("7");
                auditlogRepo.save(auditLog);
                responses.put(requestBody.getStockItemList().getFirst().getItemCd(), resultCd);

            }

            return new ResponseEntity<>(responses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responses, HttpStatus.EXPECTATION_FAILED);
        }





    }
    @Retryable(backoff = @Backoff(delay = 2000))
    public ResponseEntity<Map<String, String>> createPackagingUnit() throws JsonProcessingException {
        Auditlog object = auditlogRepo.findLastRecord();
        codeDTO codeDTO = new codeDTO();
        codeDTO.setBhfId(branchId);
        codeDTO.setTpin(clientTpin);
        codeDTO.setLastReqDt("20231215000000");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<codeDTO> requestEntity = new HttpEntity<>(codeDTO, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/zrasandboxvsdc/code/selectCodes", requestEntity, String.class);
        System.out.print(response);
        Map<String, String> responses = new HashMap<>();
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            System.out.println(jsonObject);
            String resultCd = jsonObject.getString("resultCd");
            if (jsonObject.getString("resultCd").equals("000")) {
                String responseBody = response.getBody();
                CodeResponseDTO codeResponseDTO = objectMapper.readValue(responseBody, CodeResponseDTO.class);
                saveQuantityUnitFromResponse(codeResponseDTO);


                responses.put("message", "Quantity units loaded");
                } else {
                    responses.put("message", "Failed to load quantity units try again.");
                }

                return ResponseEntity.ok(responses);

        }
        responses.put("message", "Failed to load quantity units try again.");
        return ResponseEntity.ok(responses);
    }


    @Retryable( backoff = @Backoff(delay = 2000))


    public itemInformationDTO items(Icitem item){

            String stockUnit = item.getStockunit().trim().isEmpty() ? "each" : item.getStockunit().trim();
            List<Icitemo> itemss = icitemoRepository.findByItemno(item.getItemno());
            String packagingUnit = PACKAGING_CODES.getOrDefault(stockUnit.toLowerCase(), stockUnit.toLowerCase());
            String classCode = itemss.isEmpty() ? "" : itemss.get(0).getValue().trim();
            itemInformationDTO itemInfDto = new itemInformationDTO();
            itemInfDto.setItemCode(item.getItemno());
            itemInfDto.setItemName(item.getDesc());
            itemInfDto.setItemClassCode(classCode);
            itemInfDto.setPackagingUnit(packagingUnit);
            return itemInfDto;
        }
    public List<supplierPurchaseOrderDTO> returnVendorReceipts(String tpin,String name){
    String vendor = getVendorFromTpin(tpin,name);
    List<supplierPurchaseOrderDTO> supplierPurchaseOrderDTOS = new ArrayList<>();
        List<Poporh1> porcph1s;
    if (vendor == null){
        porcph1s = poporh1Repo.findIncomplete();
    }
    else{
        porcph1s = poporh1Repo.findByVdcodeAndIscomplete(vendor, (short) 0); //findByVdcodeAndIscompleteAndIsinvoiced(, (short)1);

    }
    for (Poporh1 porcph1 : porcph1s) {
        supplierPurchaseOrderDTO purchaseOrderDTO = new supplierPurchaseOrderDTO();
        List<supplierPurchaseOrderItems> supplierPurchaseOrderItems = new ArrayList<>();
        List<Poporl> porcpls = poporlRepo.findById(porcph1.getId());

        purchaseOrderDTO.setPoNumber(porcph1.getPonumber());
        purchaseOrderDTO.setVendorName(porcph1.getVdname());
        purchaseOrderDTO.setVendorCode(porcph1.getVdcode());
        purchaseOrderDTO.setTpin(tpin);
        purchaseOrderDTO.setTaxableAmount(porcph1.getTaxbase1());
        purchaseOrderDTO.setTax(porcph1.getTaxamount1());
        purchaseOrderDTO.setTotalAmount(porcph1.getExtended());

        for (Poporl porcpl : porcpls) {
            supplierPurchaseOrderItems purchaseOrderItem = new supplierPurchaseOrderItems();
            purchaseOrderItem.setItemCode(porcpl.getItemno());
            purchaseOrderItem.setItemName(porcpl.getItemdesc());
            purchaseOrderItem.setPrice(porcpl.getUnitcost());
            purchaseOrderItem.setTaxableAmount(porcpl.getTaxbase1());
            purchaseOrderItem.setTaxAmount(porcpl.getTaxamount1());
            purchaseOrderItem.setTotalAmount(porcpl.getExtended());
            purchaseOrderItem.setQuantity(porcpl.getOqordered());
            supplierPurchaseOrderItems.add(purchaseOrderItem);
        }

        purchaseOrderDTO.setItems(supplierPurchaseOrderItems);
        supplierPurchaseOrderDTOS.add(purchaseOrderDTO);

    }
    return supplierPurchaseOrderDTOS;
}
    public List<supplierPurchaseOrderDTO> returnImportReceipts(String name){
        String vendor;
        if (name == "")
        {
            vendor = null;

        }
        else{
            vendor = getVendorFromName(name);

        }

        List<supplierPurchaseOrderDTO> supplierPurchaseOrderDTOS = new ArrayList<>();
        List<Poporh1> porcph1s;
        if (vendor == null){
            porcph1s = poporh1Repo.findIncomplete();
        }
        else{
            porcph1s = poporh1Repo.findByVdcodeAndIscomplete(vendor, (short) 0); //findByVdcodeAndIscompleteAndIsinvoiced(, (short)1);

        }
        for (Poporh1 porcph1 : porcph1s) {
            supplierPurchaseOrderDTO purchaseOrderDTO = new supplierPurchaseOrderDTO();
            List<supplierPurchaseOrderItems> supplierPurchaseOrderItems = new ArrayList<>();
            List<Poporl> porcpls = poporlRepo.findById(porcph1.getId());

            purchaseOrderDTO.setPoNumber(porcph1.getPonumber());
            purchaseOrderDTO.setVendorName(porcph1.getVdname());
            purchaseOrderDTO.setVendorCode(porcph1.getVdcode());
            purchaseOrderDTO.setTpin("");
            purchaseOrderDTO.setTaxableAmount(porcph1.getTaxbase1());
            purchaseOrderDTO.setTax(porcph1.getTaxamount1());
            purchaseOrderDTO.setTotalAmount(porcph1.getExtended());

            for (Poporl porcpl : porcpls) {
                supplierPurchaseOrderItems purchaseOrderItem = new supplierPurchaseOrderItems();
                purchaseOrderItem.setItemCode(porcpl.getItemno());
                purchaseOrderItem.setItemName(porcpl.getItemdesc());
                purchaseOrderItem.setPrice(porcpl.getUnitcost());
                purchaseOrderItem.setTaxableAmount(porcpl.getTaxbase1());
                purchaseOrderItem.setTaxAmount(porcpl.getTaxamount1());
                purchaseOrderItem.setTotalAmount(porcpl.getExtended());
                purchaseOrderItem.setQuantity(porcpl.getOqordered());
                supplierPurchaseOrderItems.add(purchaseOrderItem);
            }

            purchaseOrderDTO.setItems(supplierPurchaseOrderItems);
            supplierPurchaseOrderDTOS.add(purchaseOrderDTO);

        }
        return supplierPurchaseOrderDTOS;
}
    public List<supplierPurchaseOrderDTO> returnPOs(){
        List<supplierPurchaseOrderDTO> supplierPurchaseOrderDTOS = new ArrayList<>();
        List<Poporh1> porcph1s = poporh1Repo.findIncomplete(); // findByComplete();

        for (Poporh1 porcph1 : porcph1s) {
           Optional<Apven> apven = apvenRepo.findByVendorid(porcph1.getVdcode());
            supplierPurchaseOrderDTO purchaseOrderDTO = new supplierPurchaseOrderDTO();
            List<supplierPurchaseOrderItems> supplierPurchaseOrderItems = new ArrayList<>();
            List<Poporl> porcpls = poporlRepo.findById(porcph1.getId());

            purchaseOrderDTO.setPoNumber(porcph1.getPonumber());
            purchaseOrderDTO.setVendorName(porcph1.getVdname());
            purchaseOrderDTO.setVendorCode(porcph1.getVdcode());
            purchaseOrderDTO.setTpin(apven.map(Apven::getBrn)
                    .map(brn -> brn.replace(" ", ""))
                    .filter(brnWithoutSpaces -> brnWithoutSpaces.length() == 10).orElse(null));
            purchaseOrderDTO.setTaxableAmount(porcph1.getTaxbase1());
            purchaseOrderDTO.setTax(porcph1.getTaxamount1());
            purchaseOrderDTO.setTotalAmount(porcph1.getDoctotal());

            for (Poporl porcpl : porcpls) {
                supplierPurchaseOrderItems purchaseOrderItem = new supplierPurchaseOrderItems();
                purchaseOrderItem.setItemCode(porcpl.getItemno());
                purchaseOrderItem.setItemName(porcpl.getItemdesc());
                purchaseOrderItem.setPrice(porcpl.getUnitcost());
                purchaseOrderItem.setTaxableAmount(porcpl.getTaxbase1());
                purchaseOrderItem.setTaxAmount(porcpl.getTaxamount1());
                purchaseOrderItem.setTotalAmount(porcpl.getExtended());
                purchaseOrderItem.setQuantity(porcpl.getOqordered());
                supplierPurchaseOrderItems.add(purchaseOrderItem);
            }

            purchaseOrderDTO.setItems(supplierPurchaseOrderItems);
            supplierPurchaseOrderDTOS.add(purchaseOrderDTO);
        }
        return supplierPurchaseOrderDTOS;
    }
    //Select purchases
    @Retryable( backoff = @Backoff(delay = 2000))
    public PurchasesResponse selectPurchase() {
        String url = "http://localhost:8080/zrasandboxvsdc/trnsPurchase/selectTrnsPurchaseSales";
        List<String> auditlog = auditlogRepository.findDocumentIdsByTransactionType("2");
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("tpin",clientTpin );
        requestBody.put("bhfId", branchId);
        requestBody.put("lastReqDt", "20240626133803");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        System.out.println(response);
        //String jsonResponse = "{\"resultCd\":\"000\",\"resultMsg\":\"It is succeeded\",\"resultDt\":\"20240510103403\",\"data\":{\"saleList\":[{\"spplrTpin\":\"1001733250                    \",\"spplrNm\":\"CFAO MOTORS (ZAMBIA) LIMITED                                \",\"spplrBhfId\":\"000\",\"spplrInvcNo\":59,\"rcptTyCd\":\"S\",\"pmtTyCd\":\"01\",\"cfmDt\":\"2024-05-08 10:20:10\",\"salesDt\":\"20240502\",\"stockRlsDt\":null,\"totItemCnt\":1,\"totTaxblAmt\":86.2069,\"totTaxAmt\":13.7931,\"totAmt\":100,\"remark\":null,\"itemList\":[{\"itemSeq\":1,\"itemCd\":\"20044\",\"itemClsCd\":\"50102517\",\"itemNm\":\"ChickenWings\",\"bcd\":null,\"pkgUnitCd\":\"BA\",\"pkg\":0,\"qtyUnitCd\":\"BE\",\"qty\":1,\"prc\":100,\"splyAmt\":100,\"dcRt\":0,\"dcAmt\":0,\"vatCatCd\":\"A\",\"iplCatCd\":null,\"tlCatCd\":null,\"exciseTxCatCd\":null,\"vatTaxblAmt\":86.21,\"exciseTaxblAmt\":0,\"iplTaxblAmt\":0,\"tlTaxblAmt\":0,\"taxblAmt\":86.21,\"vatAmt\":13.79,\"iplAmt\":0,\"tlAmt\":0,\"exciseTxAmt\":0,\"totAmt\":100}]},{\"spplrTpin\":\"1001733250                    \",\"spplrNm\":\"CFAO MOTORS (ZAMBIA) LIMITED                                \",\"spplrBhfId\":\"000\",\"spplrInvcNo\":46,\"rcptTyCd\":\"S\",\"pmtTyCd\":\"01\",\"cfmDt\":\"2024-05-08 10:20:10\",\"salesDt\":\"20240502\",\"stockRlsDt\":null,\"totItemCnt\":1,\"totTaxblAmt\":86.2069,\"totTaxAmt\":13.7931,\"totAmt\":100,\"remark\":null,\"itemList\":[{\"itemSeq\":1,\"itemCd\":\"20044\",\"itemClsCd\":\"50102517\",\"itemNm\":\"ChickenWings\",\"bcd\":null,\"pkgUnitCd\":\"BA\",\"pkg\":0,\"qtyUnitCd\":\"BE\",\"qty\":1,\"prc\":100,\"splyAmt\":100,\"dcRt\":0,\"dcAmt\":0,\"vatCatCd\":\"A\",\"iplCatCd\":null,\"tlCatCd\":null,\"exciseTxCatCd\":null,\"vatTaxblAmt\":86.21,\"exciseTaxblAmt\":0,\"iplTaxblAmt\":0,\"tlTaxblAmt\":0,\"taxblAmt\":86.21,\"vatAmt\":13.79,\"iplAmt\":0,\"tlAmt\":0,\"exciseTxAmt\":0,\"totAmt\":100},{\"itemSeq\":1,\"itemCd\":\"20044\",\"itemClsCd\":\"50102517\",\"itemNm\":\"ChickenWings\",\"bcd\":null,\"pkgUnitCd\":\"BA\",\"pkg\":0,\"qtyUnitCd\":\"BE\",\"qty\":1,\"prc\":100,\"splyAmt\":100,\"dcRt\":0,\"dcAmt\":0,\"vatCatCd\":\"A\",\"iplCatCd\":null,\"tlCatCd\":null,\"exciseTxCatCd\":null,\"vatTaxblAmt\":86.21,\"exciseTaxblAmt\":0,\"iplTaxblAmt\":0,\"tlTaxblAmt\":0,\"taxblAmt\":86.21,\"vatAmt\":13.79,\"iplAmt\":0,\"tlAmt\":0,\"exciseTxAmt\":0,\"totAmt\":100}]}]}}";
        if (response.getStatusCode() == HttpStatus.OK) {
            String jsonResponse = response.getBody(); // Use actual response body here
            JSONObject jsonObject = new JSONObject(jsonResponse);
            if (jsonObject.getString("resultCd").equals("000")) {
                JSONArray saleList = jsonObject.getJSONObject("data").getJSONArray("saleList");
                List<selectPurchasesDTO> invoiceDTOList = new ArrayList<>();
                List<supplierPurchaseOrderDTO> supplierPurchaseOrderDTOS = new ArrayList<>();

                for (int i = 0; i < saleList.length(); i++) {

                    JSONObject sale = saleList.getJSONObject(i);
                   Smartpurchase smartpurchase= smartpurchaseRepository.findBySpplrInvc(sale.getInt("spplrInvcNo"));
                    if (smartpurchase == null){
                        selectPurchasesDTO invoiceDTO = new selectPurchasesDTO();

                        String vendor = getVendorFromTpin(sale.getString("spplrTpin"),sale.getString("spplrNm"));

                        List<Porcph1> porcph1s = porcph1Repo.findByVdcodeAndIscompleteAndIsinvoiced(vendor, (short) 1, (short)1);
                        for (Porcph1 porcph1 : porcph1s) {
                            supplierPurchaseOrderDTO purchaseOrderDTO = new supplierPurchaseOrderDTO();
                            List<supplierPurchaseOrderItems> supplierPurchaseOrderItems = new ArrayList<>();
                            List<Porcpl> porcpls = porcplRepo.findRcpId(porcph1.getId());

                            purchaseOrderDTO.setPoNumber(porcph1.getRcpnumber());
                            purchaseOrderDTO.setVendorName(porcph1.getVdname());
                            purchaseOrderDTO.setVendorCode(porcph1.getVdcode());
                            purchaseOrderDTO.setTpin(sale.getString("spplrTpin"));
                            purchaseOrderDTO.setTaxableAmount(porcph1.getTaxbase1());
                            purchaseOrderDTO.setTax(porcph1.getTaxamount1());
                            purchaseOrderDTO.setTotalAmount(porcph1.getExtended());

                            for (Porcpl porcpl : porcpls) {
                                supplierPurchaseOrderItems purchaseOrderItem = new supplierPurchaseOrderItems();
                                purchaseOrderItem.setItemCode(porcpl.getItemno());
                                purchaseOrderItem.setItemName(porcpl.getItemdesc());
                                purchaseOrderItem.setPrice(porcpl.getUnitcost());
                                purchaseOrderItem.setTaxableAmount(porcpl.getTaxbase1());
                                purchaseOrderItem.setTaxAmount(porcpl.getTaxamount1());
                                purchaseOrderItem.setTotalAmount(porcpl.getExtended());
                                purchaseOrderItem.setQuantity(porcpl.getOqordered());
                                supplierPurchaseOrderItems.add(purchaseOrderItem);
                            }

                            purchaseOrderDTO.setItems(supplierPurchaseOrderItems);
                            supplierPurchaseOrderDTOS.add(purchaseOrderDTO);
                        }

                        invoiceDTO.setSpplrTpin(sale.getString("spplrTpin"));
                        invoiceDTO.setSpplrNm(sale.getString("spplrNm"));
                        invoiceDTO.setSpplrBhfId(sale.getString("spplrBhfId"));
                        invoiceDTO.setSpplrInvcNo(sale.getInt("spplrInvcNo"));
                        invoiceDTO.setRcptTyCd(sale.getString("rcptTyCd"));
                        invoiceDTO.setPmtTyCd(sale.getString("pmtTyCd"));
                        invoiceDTO.setCfmDt(LocalDateTime.parse(sale.getString("cfmDt"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        invoiceDTO.setSalesDt(LocalDate.parse(sale.getString("salesDt"), DateTimeFormatter.ofPattern("yyyyMMdd")));
                        invoiceDTO.setStockRlsDt(null); // Handle null appropriately
                        invoiceDTO.setTotItemCnt(sale.getInt("totItemCnt"));
                        invoiceDTO.setTotTaxblAmt(sale.getBigDecimal("totTaxblAmt"));
                        invoiceDTO.setTotTaxAmt(sale.getBigDecimal("totTaxAmt"));
                        invoiceDTO.setTotAmt(sale.getBigDecimal("totAmt"));
                        invoiceDTO.setRemark(sale.isNull("remark") ? null : sale.getString("remark"));

                        JSONArray itemList = sale.getJSONArray("itemList");
                        List<selectPurchasesItemsDTO> itemDTOList = new ArrayList<>();
                        for (int j = 0; j < itemList.length(); j++) {
                            JSONObject item = itemList.getJSONObject(j);
                            selectPurchasesItemsDTO itemDTO = new selectPurchasesItemsDTO();

                            itemDTO.setItemSeq(item.getInt("itemSeq"));
                            itemDTO.setItemCd(item.getString("itemCd"));
                            itemDTO.setItemClsCd(item.getString("itemClsCd"));
                            itemDTO.setItemNm(item.getString("itemNm"));
                            itemDTO.setBcd(item.isNull("bcd") ? null : item.getString("bcd"));
                            itemDTO.setPkgUnitCd(item.getString("pkgUnitCd"));
                            itemDTO.setPkg(item.getInt("pkg"));
                            itemDTO.setQtyUnitCd(item.getString("qtyUnitCd"));
                            itemDTO.setQty(item.getInt("qty"));
                            itemDTO.setPrc(item.getBigDecimal("prc"));
                            itemDTO.setSplyAmt(item.getBigDecimal("splyAmt"));
                            itemDTO.setDcRt(item.getBigDecimal("dcRt"));
                            itemDTO.setDcAmt(item.getBigDecimal("dcAmt"));
                            itemDTO.setVatCatCd(item.getString("vatCatCd"));
                            itemDTO.setIplCatCd(item.isNull("iplCatCd") ? null : item.getString("iplCatCd"));
                            itemDTO.setTlCatCd(item.isNull("tlCatCd") ? null : item.getString("tlCatCd"));
                            itemDTO.setExciseTxCatCd(item.isNull("exciseTxCatCd") ? null : item.getString("exciseTxCatCd"));
                            itemDTO.setVatTaxblAmt(item.getBigDecimal("vatTaxblAmt"));
                            itemDTO.setExciseTaxblAmt(item.getBigDecimal("exciseTaxblAmt"));
                            itemDTO.setIplTaxblAmt(item.getBigDecimal("iplTaxblAmt"));
                            itemDTO.setTlTaxblAmt(item.getBigDecimal("tlTaxblAmt"));
                            itemDTO.setTaxblAmt(item.getBigDecimal("taxblAmt"));
                            itemDTO.setVatAmt(item.getBigDecimal("vatAmt"));
                            itemDTO.setIplAmt(item.getBigDecimal("iplAmt"));
                            itemDTO.setTlAmt(item.getBigDecimal("tlAmt"));
                            itemDTO.setExciseTxAmt(item.getBigDecimal("exciseTxAmt"));
                            itemDTO.setTotAmt(item.getBigDecimal("totAmt"));
                            itemDTOList.add(itemDTO);
                        }

                        invoiceDTO.setItemList(itemDTOList);
                        invoiceDTOList.add(invoiceDTO);
                    }

                }

                return new PurchasesResponse(invoiceDTOList, supplierPurchaseOrderDTOS);
            } else {
                return null;
            }
        }

        return null;
    }


    public static String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.now().format(formatter);
    }
    public BigDecimal currentDate() {
        LocalDate date0 = LocalDate.now();
        String dateString = date0.toString();
        String cleanedDate = dateString.replaceAll(("-*"), "");
        return new BigDecimal(cleanedDate);
    }

    public LocalDateTime timestamp() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime;
    }

    public BigDecimal currentTime() {
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSS");
        String time1 = sdf.format(date1);
        String time = time1.substring(0, 8);
        return new BigDecimal(time);
    }

    public String getVendorFromTpin(String tpin,String name){
        Optional<Apven> vendor = apvenRepo.findByBrnAndVendname(tpin,name);
        return vendor.map(Apven::getVendorid).orElse(null);
    }
    public String getVendorFromName(String name){
        Optional<Apven> vendor = apvenRepo.findByVendnameContainingIgnoreCase(name);
        return vendor.map(Apven::getVendorid).orElse(null);
    }


    public String nextReceiptNo(){
        Poopt pt = pooptRepo.findByRatetype("SP");
        return pt.getRcpprefixd().replace(" ","") + pt.getRcpbodyd().replace(" ","");
    }
    public List<pendingPurchases> getFailedPurchases(){

        List<Auditlog> auditlog = auditlogRepository.findDocumentIdsByTransactionTypeAndResponseCode("2");
        List<pendingPurchases> pendingPurchasesList = new ArrayList<>();
        for (Auditlog auditlog1 :auditlog){
            pendingPurchases pendingPurchases = new pendingPurchases();
            pendingPurchases.setInvoiceNumber(auditlog1.getDocumentId());
            pendingPurchases.setResponseMessage(auditlog1.getResponseMessage());
            pendingPurchases.setRequestDate(auditlog1.getRequestDate());
            pendingPurchasesList.add(pendingPurchases);
        }
        return pendingPurchasesList;

    }
    public List<pendingPurchases> getPendingPurchases(){
        List<pendingPurchases> pendingPurchasesList = new ArrayList<>();
        Date endDate = new Date();

        // Calculate date 3 days ago
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DATE, -3);
        Date startDate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String formattedStartDate = sdf.format(startDate);
        String formattedEndDate = sdf.format(endDate);
        List<String> auditlog = auditlogRepository.findDocumentIdsByTransactionType("2");
        List<Apobl> apoblList = apoblRepo.findByAudtdateBetween(BigDecimal.valueOf(Long.parseLong(formattedStartDate)),BigDecimal.valueOf(Long.parseLong(formattedEndDate)),auditlog);
        for(Apobl apobl :apoblList){
            pendingPurchases pendingPurchases = new pendingPurchases();
            pendingPurchases.setInvoiceNumber(apobl.getId().getIdinvc());
            pendingPurchases.setResponseMessage("Pending");
            pendingPurchases.setRequestDate(null);
            pendingPurchasesList.add(pendingPurchases);
        }
        return pendingPurchasesList;
    }

    public List<pendingPurchases> getPendingSales(){
        List<pendingPurchases> pendingSalesList = new ArrayList<>();
        Date endDate = new Date();

        // Calculate date 3 days ago
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DATE, -3);
        Date startDate = calendar.getTime();

        // Format the dates to 'yyyyMMdd'
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String formattedStartDate = sdf.format(startDate);
        String formattedEndDate = sdf.format(endDate);
        List<String> auditlog = auditlogRepository.findDocumentIdsByTransactionType("1");
        List<Arobl> apoblList = aroblRepo.findByAudtdateBetween(BigDecimal.valueOf(Long.parseLong(formattedStartDate)),BigDecimal.valueOf(Long.parseLong(formattedEndDate)),auditlog);
        for(Arobl apobl :apoblList){
            pendingPurchases pendingPurchases = new pendingPurchases();
            pendingPurchases.setInvoiceNumber(apobl.getId().getIdinvc());
            pendingPurchases.setResponseMessage("Pending");
            pendingPurchases.setRequestDate(null);
            pendingSalesList.add(pendingPurchases);
        }
        return pendingSalesList;

    }
    public List<pendingPurchases> getFailedSales(){
        List<Auditlog> auditlog = auditlogRepository.findDocumentIdsByTransactionTypeAndResponseCode("1");
        List<pendingPurchases> pendingPurchasesList = new ArrayList<>();
        for (Auditlog auditlog1 :auditlog){
            pendingPurchases pendingPurchases = new pendingPurchases();
            pendingPurchases.setInvoiceNumber(auditlog1.getDocumentId());
            pendingPurchases.setResponseMessage(auditlog1.getResponseMessage());
            pendingPurchases.setRequestDate(auditlog1.getRequestDate());
            pendingPurchasesList.add(pendingPurchases);
        }
        return pendingPurchasesList;

    }

    public static final Map<String, String> PACKAGING_CODES = new HashMap<>();

    static {
        PACKAGING_CODES.put("ampoule", "AM");
        PACKAGING_CODES.put("barrel", "BA");
        PACKAGING_CODES.put("bottle crate", "BC");
        PACKAGING_CODES.put("balloon, non-protected", "BF");
        PACKAGING_CODES.put("bag", "BG");
        PACKAGING_CODES.put("bucket", "BJ");
        PACKAGING_CODES.put("basket", "BK");
        PACKAGING_CODES.put("bale", "BL");
        PACKAGING_CODES.put("bottle, protected cylindrical", "BQ");
        PACKAGING_CODES.put("bar", "BR");
        PACKAGING_CODES.put("bottle, bulbous", "BV");
        PACKAGING_CODES.put("can", "CA");
        PACKAGING_CODES.put("chest", "CH");
        PACKAGING_CODES.put("coffin", "CJ");
        PACKAGING_CODES.put("coil", "CL");
        PACKAGING_CODES.put("wooden box, wooden case", "CR");
        PACKAGING_CODES.put("cassette", "CS");
        PACKAGING_CODES.put("carton", "CT");
        PACKAGING_CODES.put("container", "CTN");
        PACKAGING_CODES.put("cylinder", "CY");
        PACKAGING_CODES.put("drum", "DR");
        PACKAGING_CODES.put("extra countable item", "GT");
        PACKAGING_CODES.put("hand baggage", "HH");
        PACKAGING_CODES.put("ingots", "IZ");
        PACKAGING_CODES.put("jar", "JR");
        PACKAGING_CODES.put("jug", "JU");
        PACKAGING_CODES.put("jerry can cylindrical", "JY");
        PACKAGING_CODES.put("canester", "KZ");
        PACKAGING_CODES.put("logs, in bundle/bunch/truss", "LZ");
        PACKAGING_CODES.put("net", "NT");
        PACKAGING_CODES.put("non-exterior packaging unit", "OU");
        PACKAGING_CODES.put("poddon", "PD");
        PACKAGING_CODES.put("plate", "PG");
        PACKAGING_CODES.put("pipe", "PI");
        PACKAGING_CODES.put("pilot", "PO");
        PACKAGING_CODES.put("traypack", "PU");
        PACKAGING_CODES.put("reel", "RL");
        PACKAGING_CODES.put("roll", "RO");
        PACKAGING_CODES.put("rods, in bundle/bunch/truss", "RZ");
        PACKAGING_CODES.put("skeletoncase", "SK");
        PACKAGING_CODES.put("tank, cylindrical", "TY");
        PACKAGING_CODES.put("bulk,gas(at 1031 mbar 15 oC)", "VG");
        PACKAGING_CODES.put("bulk,liquid(at normal temperature/pressure)", "VL");
        PACKAGING_CODES.put("bulk, solid, large particles(nodules)", "VO");
        PACKAGING_CODES.put("bulk, gas (liquefied at abnormal temperature/pressure)", "VQ");
        PACKAGING_CODES.put("bulk, solid, granular particles(grains)", "VR");
        PACKAGING_CODES.put("extra bulk item", "VT");
        PACKAGING_CODES.put("bulk, fine particles(powder)", "VY");
        PACKAGING_CODES.put("mills", "ML");
        PACKAGING_CODES.put("tan", "TN");
        PACKAGING_CODES.put("black lug", "B/L");
        PACKAGING_CODES.put("bin", "BIN");
        PACKAGING_CODES.put("bottle", "BOTT");
        PACKAGING_CODES.put("bouquet", "BOUQ");
        PACKAGING_CODES.put("bowl", "BOWL");
        PACKAGING_CODES.put("box", "BOX");
        PACKAGING_CODES.put("budget bag", "BUBG");
        PACKAGING_CODES.put("bulk pack", "BULK");
        PACKAGING_CODES.put("bunch", "BUNC");
        PACKAGING_CODES.put("bundle", "BUND");
        PACKAGING_CODES.put("clear lid", "CLEA");
        PACKAGING_CODES.put("each", "EACH");
        PACKAGING_CODES.put("economy bag", "ECON");
        PACKAGING_CODES.put("econo poc sell", "ECPO");
        PACKAGING_CODES.put("green lugs", "G/L");
        PACKAGING_CODES.put("karripoc", "KARR");
        PACKAGING_CODES.put("labels", "LABE");
        PACKAGING_CODES.put("mesh", "MESH");
        PACKAGING_CODES.put("netlon", "NETL");
        PACKAGING_CODES.put("per kilogram", "P/KG");
        PACKAGING_CODES.put("pack", "PACK");
    }
    public static Map<Short, String> taxClass = new HashMap<>();

private final TxrateRepository txrateRepository;
    static {
        taxClass.put((short) 1, "A");
        taxClass.put((short) 2, "B");
        taxClass.put((short) 3, "C1");
        taxClass.put((short) 4, "C2");
        taxClass.put((short) 5, "C3");
        taxClass.put((short) 6, "D");
        taxClass.put((short) 7, "E");
        taxClass.put((short) 8, "RVAT");

    }
    public void saveQuantityUnitFromResponse(CodeResponseDTO response) {
        IcucodRepo.deleteAll();
        icwcodRepo.deleteAll();
        if (response != null && response.getData() != null) {
            List<ClsDTO> clsList = response.getData().getClsList();
            for (ClsDTO cls : clsList) {
                if ("Packing Unit".equals(cls.getCdClsNm())) {  // Or another appropriate condition
                    for (DtlDTO dtl : cls.getDtlList()) {
                       Icwcod icwcod = new Icwcod();
                       icwcod.setAudtdate(currentDate());
                       icwcod.setAudttime(currentTime());
                       icwcod.setWeightunit(dtl.getCd());
                       icwcod.setWuomdesc(dtl.getCdNm());
                       icwcod.setAudtuser("ADMIN");
                       icwcod.setAudtorg(company);
                       icwcod.setConversion(new BigDecimal("1.00"));
                       icwcodRepo.save(icwcod);
                    }
                }
                else if ("Taxation Type".equals(cls.getCdClsNm()))
                {
                        List<Txrate> txrates = txrateRepository.findByTaxclass();
                        for (Txrate txrate :txrates){
                            txrate.setItemrate1(BigDecimal.valueOf(16.00000));
                            txrateRepository.save(txrate);
                        }
                }
                else if ("Quantity Unit".equals(cls.getCdClsNm()))
                {
                    for (DtlDTO dtl : cls.getDtlList()) {
                        Icucod icucod = new Icucod();
                        icucod.setUnit(dtl.getCd());
                        icucod.setAudtdate(currentDate());
                        icucod.setAudttime(currentTime());
                        icucod.setAudtuser("ADMIN");
                        icucod.setAudtorg(company);
                        icucod.setDefconv(new BigDecimal("1.00"));
                        IcucodRepo.save(icucod);
                    }
                }
                else if("Country".equals(cls.getCdClsNm())) {
                    List<Csoptfd> csoptfds = csoptfdRepo.findByOptfield("COUNTRY");
                    csoptfdRepo.deleteAll(csoptfds);
                    for (DtlDTO dtl : cls.getDtlList()) {

                        Csoptfd csoptfd = new Csoptfd();
                        CsoptfdId csoptfdId = new CsoptfdId();
                        csoptfdId.setOptfield("COUNTRY");
                        csoptfdId.setValue(dtl.getCd());
                        csoptfd.setId(csoptfdId);
                        csoptfd.setAudtdate(currentDate());
                        csoptfd.setAudttime(currentTime());
                        csoptfd.setVdesc(dtl.getCdNm());
                        csoptfd.setAudtuser("ADMIN");
                        csoptfd.setAudtorg(company);
                        csoptfd.setLength(((short)60));
                        csoptfd.setSortedval(dtl.getCd());
                        csoptfd.setType((short)1 );
                        csoptfd.setAllownull((short) 0);
                        csoptfd.setDecimals((short)0);
                        csoptfd.setValidate((short)1);
                        csoptfdRepo.save(csoptfd);


                    }
                }
                else if("Credit Note Reason".equals(cls.getCdClsNm())) {
                    List<Csoptfd> csoptfds = csoptfdRepo.findByOptfield("CRNREASON   ");
                    csoptfdRepo.deleteAll(csoptfds);
                    for (DtlDTO dtl : cls.getDtlList()) {
                        Csoptfd csoptfd = new Csoptfd();
                        CsoptfdId csoptfdId = new CsoptfdId();
                        csoptfdId.setOptfield("CRNREASON");
                        csoptfdId.setValue(dtl.getCd());
                        csoptfd.setId(csoptfdId);
                        csoptfd.setVdesc(dtl.getCdNm());
                        csoptfd.setAudtdate(currentDate());
                        csoptfd.setAudttime(currentTime());
                        csoptfd.setAudtuser("ADMIN");
                        csoptfd.setAudtorg(company);
                        csoptfd.setLength(((short)60));
                        csoptfd.setSortedval(dtl.getCd());
                        csoptfd.setType((short)1 );
                        csoptfd.setAllownull((short) 0);
                        csoptfd.setDecimals((short)0);
                        csoptfd.setValidate((short)1);
                        csoptfdRepo.save(csoptfd);


                    }
                }
                else if("Reason for Debit note".equals(cls.getCdClsNm())) {
                    List<Csoptfd> csoptfds = csoptfdRepo.findByOptfield("DBNREASON");
                    csoptfdRepo.deleteAll(csoptfds);
                    for (DtlDTO dtl : cls.getDtlList()) {
                        Csoptfd csoptfd = new Csoptfd();
                        CsoptfdId csoptfdId = new CsoptfdId();
                        csoptfdId.setOptfield("DBNREASON");
                        csoptfdId.setValue(dtl.getCd());
                        csoptfd.setId(csoptfdId);
                        csoptfd.setVdesc(dtl.getCdNm());
                        csoptfd.setAudtdate(currentDate());
                        csoptfd.setAudttime(currentTime());
                        csoptfd.setAudtuser("ADMIN");
                        csoptfd.setAudtorg(company);
                        csoptfd.setLength(((short)60));
                        csoptfd.setSortedval(dtl.getCd());
                        csoptfd.setType((short)1 );
                        csoptfd.setAllownull((short) 0);
                        csoptfd.setDecimals((short)0);
                        csoptfd.setValidate((short)1);
                        csoptfdRepo.save(csoptfd);


                    }
                }
                else if("Reason of Inventory Adjustment".equals(cls.getCdClsNm())) {
                    List<Csoptfd> csoptfds = csoptfdRepo.findByOptfield("ADJREASON   ");
                    csoptfdRepo.deleteAll(csoptfds);
                    for (DtlDTO dtl : cls.getDtlList()) {
                        Csoptfd csoptfd = new Csoptfd();
                        CsoptfdId csoptfdId = new CsoptfdId();
                        csoptfdId.setOptfield("ADJREASON");
                        csoptfdId.setValue(dtl.getCd());
                        csoptfd.setId(csoptfdId);
                        csoptfd.setVdesc(dtl.getCdNm());
                        csoptfd.setAudtdate(currentDate());
                        csoptfd.setAudttime(currentTime());
                        csoptfd.setAudtuser("ADMIN");
                        csoptfd.setAudtorg(company);
                        csoptfd.setLength(((short)60));
                        csoptfd.setSortedval(dtl.getCd());
                        csoptfd.setType((short)1 );
                        csoptfd.setAllownull((short) 0);
                        csoptfd.setDecimals((short)0);
                        csoptfd.setValidate((short)1);
                        csoptfdRepo.save(csoptfd);


                    }
                }
                else if("Item Type".equals(cls.getCdClsNm())) {
                    List<Csoptfd> csoptfds = csoptfdRepo.findByOptfield("ITEMTYPE    ");
                    csoptfdRepo.deleteAll(csoptfds);
                    for (DtlDTO dtl : cls.getDtlList()) {
                        Csoptfd csoptfd = new Csoptfd();
                        CsoptfdId csoptfdId = new CsoptfdId();
                        csoptfdId.setOptfield("ITEMTYPE");
                        csoptfdId.setValue(dtl.getCd());
                        csoptfd.setId(csoptfdId);
                        csoptfd.setVdesc(dtl.getCdNm());
                        csoptfd.setAudtdate(currentDate());
                        csoptfd.setAudttime(currentTime());
                        csoptfd.setAudtuser("ADMIN");
                        csoptfd.setAudtorg(company);
                        csoptfd.setLength(((short)60));
                        csoptfd.setSortedval(dtl.getCd());
                        csoptfd.setType((short)1 );
                        csoptfd.setAllownull((short) 0);
                        csoptfd.setDecimals((short)0);
                        csoptfd.setValidate((short)1);
                        csoptfdRepo.save(csoptfd);


                    }
                }
            }
        }
    }

    public String returnNextAjdNum(String adjbodyd) {
            // Extract the numeric part (in this case '0563')
            String prefix = adjbodyd.substring(0, 3); // 'ADJ'
            String numericPart = adjbodyd.substring(3, 13).trim(); // '0563'

            // Increment the numeric part
            int numericValue = Integer.parseInt(numericPart);
            numericValue++; // Increment '0563' to '0564'

            // Format it back to a 10-digit padded number and append spaces
            String newNumericPart = String.format("%010d", numericValue); // '0000000564'

            // Concatenate the parts and ensure it is the same length (22 characters total)
            return prefix + newNumericPart + "         "; // 'ADJ0000000564         '
        }
    public static int extractNonZeroDigits(String adjbodyd) {

        String trimmedAdjBodyD = adjbodyd.trim();
        int trailingSpacesCount = adjbodyd.length() - trimmedAdjBodyD.length();

        // Remove leading zeros
        String withoutLeadingZeros = trimmedAdjBodyD.replaceFirst("^0+", "");

        // Convert to integer; if empty after removing leading zeros, treat as zero
        int numericValue = withoutLeadingZeros.isEmpty() ? 0 : Integer.parseInt(withoutLeadingZeros);
        System.out.println(numericValue);
        return numericValue;
    }
    public int docuniq (){
        Optional<Icopt> icopt = icoptRepository.findById((short) 0);
        return extractNonZeroDigits(icopt.get().getAdjbodyd());
    }
    public String incrementValue() {
        // Extract trailing spaces
        Optional<Icopt> icopt = icoptRepository.findById((short) 0);
        String adjbodyd = icopt.get().getAdjbodyd();
        String trimmedAdjBodyD = adjbodyd.trim();
        int trailingSpacesCount = adjbodyd.length() - trimmedAdjBodyD.length();

        // Remove leading zeros
        String withoutLeadingZeros = trimmedAdjBodyD.replaceFirst("^0+", "");

        // Convert to integer; if empty after removing leading zeros, treat as zero
        int numericValue = withoutLeadingZeros.isEmpty() ? 0 : Integer.parseInt(withoutLeadingZeros);

        // Increment the value
        numericValue += 1;

        // Convert back to string and add leading zeros
        String incrementedValueStr = Integer.toString(numericValue);
        StringBuilder result = new StringBuilder();

        // Add back leading zeros based on original length minus the incremented value's length
        int leadingZerosCount = Math.max(0, adjbodyd.length() - trimmedAdjBodyD.length() - incrementedValueStr.length());
        for (int i = 0; i < leadingZerosCount; i++) {
            result.append('0');
        }

        // Append incremented value
        result.append(incrementedValueStr);

        // Append trailing spaces back
        for (int i = 0; i < trailingSpacesCount; i++) {
            result.append(' ');
        }

        // Update the adjbodyd field with the new value
        return result.toString();
    }

    public boolean postImportReject( importPostDTO request) {
        postImportDTO postImportDto = new postImportDTO();
        Icitem o = icitemRepository.findByFmtitemno(request.getSageItem());
        List<postImportItemDTO> importItemDTO = new ArrayList<>();
        itemInformationDTO iCode = items(o);
        postImportItemDTO postImportItem = new postImportItemDTO();
        postImportItem.setItemSeq(request.getItemSeq());
        postImportItem.setHsCd(request.getHsCd());
        postImportItem.setImptItemSttsCd(String.valueOf(request.getActionValue()));
        postImportItem.setItemCd(iCode.getItemCode());
        postImportItem.setRemark(request.getRemark());
        postImportItem.setItemClsCd(iCode.getItemClassCode());
        postImportItem.setModrId("ADMIN");
        postImportItem.setModrNm("ADMIN");
        System.out.print(postImportItem);
        importItemDTO.add(postImportItem);
        postImportDto.setTpin(clientTpin);
        postImportDto.setBhfId(branchId);
        postImportDto.setTaskCd(request.getTaskCd());
        postImportDto.setDclDe(request.getDclDe());
        postImportDto.setImportItemList(importItemDTO);
        String url = "http://localhost:8080/zrasandboxvsdc/imports/updateImportItems";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<postImportDTO> requestEntity = new HttpEntity<>(postImportDto, headers);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestEntity.getBody());
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            System.out.println(jsonObject);
            if (jsonObject.getString("resultCd").equals("000")) {
                return true;
            }
        }
        return false;
    }




    public Boolean postImport( importPostDTO request){
        postImportDTO postImportDto = new postImportDTO();
        Icitem o = icitemRepository.findByFmtitemno(request.getSageItem());
        List<postImportItemDTO> importItemDTO = new ArrayList<>();
        itemInformationDTO iCode = items(o);
        postImportItemDTO postImport = new postImportItemDTO();
        postImport.setItemSeq(request.getItemSeq());
        postImport.setHsCd(request.getHsCd());
        postImport.setImptItemSttsCd(String.valueOf(request.getActionValue()));
        postImport.setItemCd(iCode.getItemCode());
        postImport.setRemark("");
        postImport.setItemClsCd(iCode.getItemClassCode());
        postImport.setModrId("ADMIN");
        postImport.setModrNm("ADMIN");
        importItemDTO.add(postImport);
        postImportDto.setTpin(clientTpin);
        postImportDto.setBhfId(branchId);
        postImportDto.setTaskCd(request.getTaskCd());
        postImportDto.setDclDe(request.getDclDe());
        postImportDto.setImportItemList(importItemDTO);
        String url = "http://localhost:8080/zrasandboxvsdc/imports/updateImportItems";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<postImportDTO> requestEntity = new HttpEntity<>(postImportDto, headers);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestEntity.getBody());
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            System.out.println(jsonObject);
            if (jsonObject.getString("resultCd").equals("000")) {
                Poporh1 poporh1 = poporh1Repo.findByPonumberContaining(request.getRecieptNo());
                poporh1.setComment("Declaration reference number: "+request.getDclRefNum());
                poporh1Repo.save(poporh1);
                return true;
            }
        }
        return false;

    }
        /*Icadeh icadeh1 = icadehRepository.findByReferenceContaining(request.getTaskCd());
        Icitem icitem = icitemRepository.findByFmtitemno(request.getSageItem());
        if (icadeh1 != null){
            IcadedId icadedId = new IcadedId();
            icadedId.setAdjenseq(icadeh1.getId());
            icadedId.setLineno((short) 100);
            Icaded icaded = new Icaded();

            // Set the embedded ID
            icaded.setId(icadedId);

            // Set other properties using setters, passing zeros and empty strings where applicable
            icaded.setAudtdate(currentDate());
            icaded.setAudttime(currentTime());
            icaded.setAudtuser("ADMIN");
            icaded.setAudtorg(company);
            icaded.setItemno(request.getSageItem());
            icaded.setItemdesc(icitem.getDesc());
            icaded.setLocation("HQ");
            icaded.setTranstype((short) 1);
            icaded.setQuantity(new BigDecimal(String.valueOf(request.getQty())));
            icaded.setUnit(icitem.getStockunit());
            icaded.setConversion(new BigDecimal(1));
            icaded.setExtcost(BigDecimal.ZERO);
            icaded.setComments(request.getHsCd());  // Set to empty string
            icaded.setWoffacct("");
            icaded.setOrigacct("");
            icaded.setCostmethod((short) 6);
            icaded.setMdallocate((short) 1);
            icaded.setReceipt("");
            icaded.setCostdate(BigDecimal.ZERO);
            icaded.setCostseqnum(0);
            icaded.setStockitem((short) 0);
            icaded.setManitemno("");
            icaded.setDetailnum(icadeh1.getNextdtlnum());
            icaded.setPmcontract("");
            icaded.setPmproject("");
            icaded.setPmcategory("");
            icaded.setPmohacct("");
            icaded.setPmohamt(BigDecimal.ZERO);
            icaded.setValues(0);
            icaded.setSerialqty(0);
            icaded.setLotqty(BigDecimal.ZERO);
            icaded.setSerialcost(BigDecimal.ZERO);
            icaded.setLotcost(BigDecimal.ZERO);
            icadedRepository.save(icaded);

            icadeh1.setNextdtlnum((short) (icadeh1.getNextdtlnum()+1));
            icadehRepository.save(icadeh1);

        }
        else{
            int max = icadehRepository.findMaxId();
            Optional<Icadeh> icadehid = icadehRepository.findById(max);
            Icadeh icadeh = new Icadeh();
            icadeh.setId(max+1);
            icadeh.setAudtdate(currentDate());
            icadeh.setAudttime(currentTime());
            icadeh.setAudtuser("ADMIN");
            icadeh.setAudtorg(company);
            icadeh.setTransnum(icadehid.get().getTransnum().add(new BigDecimal(5)));
            icadeh.setDocnum(returnNextAjdNum(icadehid.get().getDocnum()));
            icadeh.setHdrdesc("ASYCUDA Import");
            icadeh.setTransdate(new BigDecimal(request.getDclDe()));
            icadeh.setFiscyear(getFiscalYear());
            icadeh.setFiscperiod(getFiscalPeriod());
            icadeh.setReference(request.getTaskCd());
            icadeh.setDocuniq(new BigDecimal(docuniq()));
            icadeh.setStatus((short) 1);
            icadeh.setDeleted((short) 0);
            icadeh.setNextdtlnum((short) 2);
            icadeh.setPrinted((short) 0);
            icadeh.setValues(0);
            icadeh.setJobcost((short) 0);
            icadeh.setPmadjustno("");
            icadeh.setEnteredby("ADMIN");
            icadeh.setDatebus(currentDate());
            icadehRepository.save(icadeh);

            IcadedId icadedId = new IcadedId();
            icadedId.setAdjenseq(max+1);
            icadedId.setLineno((short) 100);
            Icaded icaded = new Icaded();

            // Set the embedded ID
            icaded.setId(icadedId);

            // Set other properties using setters, passing zeros and empty strings where applicable
            icaded.setAudtdate(currentDate());
            icaded.setAudttime(currentTime());
            icaded.setAudtuser("ADMIN");
            icaded.setAudtorg(company);
            icaded.setItemno(request.getSageItem());
            icaded.setItemdesc(icitem.getDesc());
            icaded.setLocation("HQ");
            icaded.setTranstype((short) 1);
            icaded.setQuantity(new BigDecimal(String.valueOf(request.getQty())));
            icaded.setUnit(icitem.getStockunit());
            icaded.setConversion(new BigDecimal(1));
            icaded.setExtcost(BigDecimal.ZERO);
            icaded.setComments(request.getHsCd());  // Set to empty string
            icaded.setWoffacct("");
            icaded.setOrigacct("");
            icaded.setCostmethod((short) 6);
            icaded.setMdallocate((short) 1);
            icaded.setReceipt("");
            icaded.setCostdate(BigDecimal.ZERO);
            icaded.setCostseqnum(0);
            icaded.setStockitem((short) 0);
            icaded.setManitemno("");
            icaded.setDetailnum((short) 1);
            icaded.setPmcontract("");
            icaded.setPmproject("");
            icaded.setPmcategory("");
            icaded.setPmohacct("");
            icaded.setPmohamt(BigDecimal.ZERO);
            icaded.setValues(0);
            icaded.setSerialqty(0);
            icaded.setLotqty(BigDecimal.ZERO);
            icaded.setSerialcost(BigDecimal.ZERO);
            icaded.setLotcost(BigDecimal.ZERO);
            icadedRepository.save(icaded);
            Optional<Icopt> icopt = icoptRepository.findById((short) 0);
            Icopt icopt1 = icopt.get();
            icopt1.setAdjbodyd(incrementValue());
            icoptRepository.save(icopt1);

        }
        return true;*/


     /*String taskCode = "";
        String dclDate = "";

        Porcph1 porcph1 = porcph1Repo.findByRcpnumberContaining(request.getRecieptNo());
        List<postImportItemDTO> importItemDTO = new ArrayList<>();
        List<stockItemDTO> stockItemList = new ArrayList<>();
        List<String> itemcodes = new ArrayList<>();
        postImportDTO postImportDto = new postImportDTO();

         int itemSeq = 1;
         BigDecimal totAmount =new BigDecimal(0);

            String status =String.valueOf(request.getActionValue());
            Icitem o = icitemRepository.findByFmtitemno(request.getSageItem());
            itemInformationDTO iCode = items(o);
            taskCode = request.getTaskCd();
            dclDate = request.getDclDe();
            postImportItemDTO postImport = new postImportItemDTO();
            postImport.setHsCd(request.getHsCd());
            postImport.setImptItemSttsCd(String.valueOf(request.getActionValue()));
            postImport.setItemCd(iCode.getItemCode());
            postImport.setItemClsCd(iCode.getItemClassCode());
            postImport.setModrId("ADMIN");
            postImport.setModrNm("ADMIN");
            importItemDTO.add(postImport);
            itemcodes.add(iCode.getItemCode());
            //create stock item list
            if (status.equals("3")){
            BigDecimal price = (request.getInvcFcurAmt().multiply(request.getInvcFcurExcrt())).divide(request.getQty(),4, RoundingMode.HALF_UP);
            stockItemDTO stockItem = new stockItemDTO();
                stockItem.setItemSeq(itemSeq);
                stockItem.setItemCd(iCode.getItemCode().replace(" ", ""));
                stockItem.setItemNm(iCode.getItemName().replace(" ", ""));
                stockItem.setItemClsCd(iCode.getItemClassCode().replace(" ", ""));
                stockItem.setPkgUnitCd(iCode.getPackagingUnit().replace(" ", ""));
                stockItem.setQtyUnitCd("U");
                stockItem.setQty(Double.parseDouble(String.valueOf(request.getQty())));
                stockItem.setPrc(Double.parseDouble(String.valueOf(price)));
                stockItem.setSplyAmt(Double.parseDouble(String.valueOf((request.getInvcFcurAmt().multiply(request.getInvcFcurExcrt())))));
                stockItem.setVatCatCd("C2");
                stockItem.setPkg(Double.parseDouble(request.getPkg()));
                stockItem.setTotDcAmt(0);
                stockItem.setTotAmt(Double.parseDouble(String.valueOf((request.getInvcFcurAmt().multiply(request.getInvcFcurExcrt())))));
                stockItem.setTaxAmt(0.0);
                stockItem.setTaxblAmt(Double.parseDouble(String.valueOf(request.getInvcFcurAmt().multiply(request.getInvcFcurExcrt()))));
                itemSeq++;
                stockItemList.add(stockItem);
                totAmount = totAmount.add( request.getInvcFcurAmt().multiply(request.getInvcFcurExcrt()));

        }
        postImportDto.setTpin(clientTpin);
        postImportDto.setBhfId(branchId);
        postImportDto.setTaskCd(taskCode);
        postImportDto.setDclDe(dclDate);
        postImportDto.setImportItemList(importItemDTO);

        stockDTO stockItemBody = new stockDTO();
        if (!stockItemList.isEmpty()) {

            stockItemBody.setTpin(clientTpin);
            stockItemBody.setBhfId(branchId);
            stockItemBody.setSarNo(Integer.parseInt(String.valueOf(porcph1.getId())));
            stockItemBody.setRegTyCd("M");
            stockItemBody.setCustTpin(null);
            stockItemBody.setCustBhfId(null);
            stockItemBody.setSarTyCd("01");
            stockItemBody.setOcrnDt(String.valueOf(currentDate()));
            stockItemBody.setTotItemCnt(itemSeq);
            stockItemBody.setTotTaxblAmt(setDoubleScale(totAmount));
            stockItemBody.setTotTaxAmt(setDoubleScale(new BigDecimal(0)));
            stockItemBody.setTotAmt(setDoubleScale(totAmount));
            stockItemBody.setRemark(null);
            stockItemBody.setRegrId("ADMIN");
            stockItemBody.setRegrNm("ADMIN");
            stockItemBody.setModrId("ADMIN");
            stockItemBody.setModrNm("ADMIN");
            stockItemBody.setItemList(stockItemList);
        }
        String url = "http://localhost:8080/zrasandboxvsdc/imports/updateImportItems";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<postImportDTO> requestEntity = new HttpEntity<>(postImportDto, headers);
        System.out.println(requestEntity);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            System.out.println(jsonObject);
            if (jsonObject.getString("resultCd").equals("000")) {

                Auditlog auditLog = new Auditlog();
                auditLog.setTpin(clientTpin);
                auditLog.setRequestDate(Instant.now());
                auditLog.setDocumentId(String.valueOf(requestEntity.getBody().getTaskCd()));
                auditLog.setResponseMessage(jsonObject.getString("resultMsg"));
                auditLog.setRequestUrl(url);
                auditLog.setLastRequestDate(getCurrentDateTime());
                auditLog.setRequestBody(requestEntity.toString());
                auditLog.setTransactionType("9");
                auditLog.setResponseCode(jsonObject.getString("resultCd"));
                auditLog.setPurchaseTransactionType("A");

                if (stockItemBody != null) {
                    addImportStock(stockItemBody);
                    addImportStockMaster(itemcodes);
                }
                return true;


            } else {
                Auditlog auditLog = new Auditlog();
                auditLog.setTpin(clientTpin);
                auditLog.setRequestDate(Instant.now());
                auditLog.setDocumentId(String.valueOf(requestEntity.getBody().getTaskCd()));
                auditLog.setResponseMessage(jsonObject.getString("resultMsg"));
                auditLog.setRequestUrl(url);
                auditLog.setLastRequestDate(getCurrentDateTime());
                auditLog.setRequestBody(requestEntity.toString());
                auditLog.setTransactionType("2");
                auditLog.setResponseCode(jsonObject.getString("resultCd"));
                auditlogRepo.save(auditLog);
                return false;

            }
        }
        return false;*/

    @Retryable( backoff = @Backoff(delay = 2000))
    public void addImportStockMaster(List<String> icodes){
    String url = baseUrl+"/stockMaster/saveStockMaster";

    List<stockMasterItemDTO> itemList = new ArrayList<>();
        String todaysDateRaw = new SimpleDateFormat("yyyyMMdd").format(new Date());

        BigDecimal todaysDate = new BigDecimal(todaysDateRaw);
        List<String> stockList = new ArrayList<>();
        Map<String, String> responses = new HashMap<>();

        List<Icival> items = icival_repo.findMostRecentByItemnoAndTransactionType(icodes);

        for (Icival item : items) {
            itemList.add(new stockMasterItemDTO(item.getId().getItemno(), item.getTotalqty().intValue()));
        }
        stockMasterDTO requestBody = new stockMasterDTO(clientTpin, "000", "ADMIN", "ADMIN", "ADMIN", "ADMIN", itemList);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<stockMasterDTO> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            System.out.println(jsonObject);
            String resultCd = jsonObject.getString("resultCd");
            if (jsonObject.getString("resultCd").equals("000")) {
                // Create Audit Log
                Auditlog auditLog = new Auditlog();
                auditLog.setTpin(clientTpin);
                auditLog.setRequestDate(Instant.now());
                auditLog.setDocumentId(requestBody.getStockItemList().getFirst().getItemCd());
                auditLog.setResponseMessage(jsonObject.getString("resultMsg"));
                auditLog.setRequestUrl(url);
                auditLog.setLastRequestDate(getCurrentDateTime());
                auditLog.setRequestBody(requestBody.toString());
                auditLog.setResponseCode(resultCd);
                auditLog.setTransactionType("7");
                auditlogRepo.save(auditLog);

                // Save item information
                Stockmaster stockmaster = new Stockmaster();
                stockmaster.setItemCode(requestBody.getStockItemList().getFirst().getItemCd());
                stockmaster.setItemQuantity(requestBody.getStockItemList().getFirst().getRsdQty());


                stock_master_repo.save(stockmaster);
                responses.put(requestBody.getStockItemList().getFirst().getItemCd(), resultCd);
            } else {
                // Handle error response



                Auditlog auditLog = new Auditlog();
                auditLog.setTpin(clientTpin);
                auditLog.setRequestDate(Instant.now());
                auditLog.setDocumentId(requestBody.getStockItemList().getFirst().getItemCd());
                auditLog.setResponseMessage(jsonObject.getString("resultMsg"));
                auditLog.setRequestUrl(url);
                auditLog.setLastRequestDate(getCurrentDateTime());
                auditLog.setRequestBody(requestBody.toString());
                auditLog.setResponseCode(resultCd);
                auditLog.setTransactionType("7");
                auditlogRepo.save(auditLog);
                responses.put(requestBody.getStockItemList().getFirst().getItemCd(), resultCd);

            }

            new ResponseEntity<>(responses, HttpStatus.OK);
        } else {
            new ResponseEntity<>(responses, HttpStatus.EXPECTATION_FAILED);
        }





    }
    public void addImportStock(stockDTO stock){
        String url = baseUrl+"/stock/saveStockItems";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<stockDTO> requestEntity = new HttpEntity<>(stock, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            System.out.println(jsonObject);
            if (jsonObject.getString("resultCd").equals("000")) {

                Auditlog auditLog = new Auditlog();
                auditLog.setTpin(clientTpin);
                auditLog.setRequestDate(Instant.now());
                auditLog.setDocumentId(String.valueOf(requestEntity.getBody().getSarNo()));
                auditLog.setResponseMessage(jsonObject.getString("resultMsg"));
                auditLog.setRequestUrl(url);
                auditLog.setLastRequestDate(getCurrentDateTime());
                auditLog.setRequestBody(requestEntity.toString());
                auditLog.setTransactionType("5");
                auditLog.setResponseCode(jsonObject.getString("resultCd"));
                auditlogRepo.save(auditLog);



            } else {
                Auditlog auditLog = new Auditlog();
                auditLog.setTpin(clientTpin);
                auditLog.setRequestDate(Instant.now());
                auditLog.setDocumentId(String.valueOf(requestEntity.getBody().getSarNo()));

                auditLog.setResponseMessage(jsonObject.getString("resultMsg"));
                auditLog.setRequestUrl(url);
                auditLog.setLastRequestDate(getCurrentDateTime());
                auditLog.setRequestBody(requestEntity.toString());
                auditLog.setTransactionType("5");
                auditLog.setResponseCode(jsonObject.getString("resultCd"));
                auditlogRepo.save(auditLog);

            }
        }
    }

   /* public BigDecimal maxID (){
        Porcph1 porcph1= porcph1Repo.findMaxId();
        return porcph1.getId().add(BigDecimal.valueOf(2));
    }
    public BigDecimal maxsqlID (){
       return porcplRepo.findByRcphseq();
    }
    public BigDecimal maxInvSqlID (){
        return poinvlRepository.findByRcphseq();
    }
    private void createPurchaseOrderReceipt(JsonNode sale , String ponumber, List<postPOItemsDTO> items){

        Poporh1 poporh1 = poporh1Repo.findByPonumberContaining(ponumber);
        Optional<Apven> apven = apvenRepo.findByVendorid(poporh1.getVdcode());
        Porcph1 maxID = porcph1Repo.findMaxId();
        System.out.println(sale.path("totAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
        String inv = sale.path("spplrInvcNo").asText();
        BigDecimal totalQty = new BigDecimal("0.0");
        BigDecimal poprxlInst = poprxlRepository.findMaxId();
        BigDecimal nextPrxLineSeq = poprxlInst.add(new BigDecimal(1));
        BigDecimal  nextRcLineSeq = porcpalRepository.findByRcpal().add(new BigDecimal(1));
        BigDecimal rcplseq = maxsqlID();
        BigDecimal rcpcseq = maxsqlID().add(new BigDecimal(2));
        BigDecimal rcplrev = BigDecimal.valueOf(31);
        for (JsonNode node : sale.path("itemList")){
            BigDecimal qty = node.path("qty").decimalValue();
            totalQty = totalQty.add(qty);
        }
        BigDecimal poprxhInst = poprxhRepository.findMaxId();
        List<Poporl> poporls = poporlRepo.findById(poporh1.getId());
        for (Poporl poporl :poporls){
        Icitem icitem = icitemRepository.findByFmtitemno(poporl.getItemno());

            for (JsonNode node : sale.path("itemList")) {
                String itemno = "";
                for (int i = 0; i < items.size(); i++) {
                    postPOItemsDTO item = items.get(i);
                    String itemcode = item.getItemCd();
                    if (itemcode.equals(node.path("itemCd").asText())) {
                        itemno = item.getSageItemCode();
                    }
                }

                // Create an instance of PorcplId
                PorcplId porcplId = new PorcplId();
                porcplId.setRcphseq(maxID.getId().add(BigDecimal.valueOf(2)));
                porcplId.setRcplrev(rcplrev);


                Porcpl porcpl = new Porcpl();
                porcpl.setId(porcplId);
                porcpl.setAudtdate(currentDate()); // Assuming currentDate() returns a BigDecimal
                porcpl.setAudttime(currentTime()); // Assuming currentTime() returns a BigDecimal
                porcpl.setAudtuser("ADMIN");
                porcpl.setAudtorg(company);
                porcpl.setRcplseq(rcplseq);
                porcpl.setRcpcseq(rcpcseq);
                porcpl.setOeonumber(""); // Set as needed
                porcpl.setIndbtable((short) 1);
                porcpl.setPostedtoic((short) 1);
                porcpl.setCompletion((short) 1);
                porcpl.setDtcomplete(new BigDecimal(0)); // Assuming currentDate() returns a BigDecimal
                porcpl.setPorhseq(poporl.getId().getPorhseq());
                porcpl.setPorlseq(poporl.getPorlseq());
                porcpl.setPocomplete((short) 1);
                porcpl.setItemexists((short) 1);
                porcpl.setItemno(poporl.getItemno()); // Set as needed
                porcpl.setLocation("HQ");
                porcpl.setItemdesc(poporl.getItemdesc()); // Assuming sItem is defined
                porcpl.setVenditemno(""); // Set as needed
                porcpl.setHascomment((short) 0);
                porcpl.setOrderunit(poporl.getOrderunit()); // Assuming sItem is defined
                porcpl.setOrderconv(new BigDecimal(1));
                porcpl.setOrderdecml((short) 4);
                porcpl.setRcpunit(poporl.getOrderunit()); // Assuming sItem is defined
                porcpl.setRcpconv(new BigDecimal(1));
                porcpl.setRcpdecml((short) 4);
                porcpl.setStockdecml((short) 4);
                porcpl.setOqordered(node.path("qty").decimalValue()); // Assuming node is defined
                porcpl.setOqprevrecv(new BigDecimal(0));
                porcpl.setOqoutstpo(node.path("qty").decimalValue());
                porcpl.setRqreceived(node.path("qty").decimalValue());
                porcpl.setRqcanceled(new BigDecimal(0));
                porcpl.setRqoutstand(new BigDecimal(0));
                porcpl.setSqordered(node.path("qty").decimalValue());
                porcpl.setSqprevrecv(new BigDecimal(0));
                porcpl.setSqoutstpo(node.path("qty").decimalValue());
                porcpl.setRqordered(node.path("qty").decimalValue());
                porcpl.setRqprevrecv(new BigDecimal(0));
                porcpl.setRqoutstpo(node.path("qty").decimalValue());
                porcpl.setRqrcpextra(new BigDecimal(0));
                porcpl.setSqreceived(node.path("qty").decimalValue());
                porcpl.setSqcanceled(new BigDecimal(0));
                porcpl.setSqoutstand(new BigDecimal(0));
                porcpl.setSqrcpextra(new BigDecimal(0));
                porcpl.setOqreceived(node.path("qty").decimalValue());
                porcpl.setOqcanceled(new BigDecimal(0));
                porcpl.setOqoutstand(new BigDecimal(0));
                porcpl.setOqrcpextra(new BigDecimal(0));
                porcpl.setRqreturned(new BigDecimal(0));
                porcpl.setSqreturned(new BigDecimal(0));
                porcpl.setOqreturned(new BigDecimal(0));
                porcpl.setRqstocked(node.path("qty").decimalValue());
                porcpl.setSqstocked(node.path("qty").decimalValue());
                porcpl.setOqstocked(node.path("qty").decimalValue());
                porcpl.setRqinadjust(new BigDecimal(0));
                porcpl.setSqinadjust(new BigDecimal(0));
                porcpl.setOqinadjust(new BigDecimal(0));
                porcpl.setRqpofilled(node.path("qty").decimalValue());
                porcpl.setSqpofilled(node.path("qty").decimalValue());
                porcpl.setOqpofilled(node.path("qty").decimalValue());
                porcpl.setRqsettled(new BigDecimal(0));
                porcpl.setSqsettled(new BigDecimal(0));
                porcpl.setOqsettled(new BigDecimal(0));
                porcpl.setRqustocked(new BigDecimal(0));
                porcpl.setSqustocked(new BigDecimal(0));
                porcpl.setOqustocked(new BigDecimal(0));
                porcpl.setUnitweight(new BigDecimal(0));
                porcpl.setExtweight(new BigDecimal(0));
                porcpl.setUnitcost(node.path("prc").decimalValue());
                porcpl.setExtended(node.path("taxblAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpl.setTaxbase1(node.path("taxblAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpl.setTaxbase2(new BigDecimal(0));
                porcpl.setTaxbase3(new BigDecimal(0));
                porcpl.setTaxbase4(new BigDecimal(0));
                porcpl.setTaxbase5(new BigDecimal(0));
                porcpl.setTaxclass1((short) 1);
                porcpl.setTaxclass2((short) 1);
                porcpl.setTaxclass3((short) 1);
                porcpl.setTaxclass4((short) 1);
                porcpl.setTaxclass5((short) 1);
                porcpl.setTaxrate1(new BigDecimal(16));
                porcpl.setTaxrate2(new BigDecimal(0));
                porcpl.setTaxrate3(new BigDecimal(0));
                porcpl.setTaxrate4(new BigDecimal(0));
                porcpl.setTaxrate5(new BigDecimal(0));
                porcpl.setTaxinclud1((short) 0);
                porcpl.setTaxinclud2((short) 0);
                porcpl.setTaxinclud3((short) 0);
                porcpl.setTaxinclud4((short) 0);
                porcpl.setTaxinclud5((short) 0);
                porcpl.setTaxamount1(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpl.setTaxamount2(new BigDecimal(0));
                porcpl.setTaxamount3(new BigDecimal(0));
                porcpl.setTaxamount4(new BigDecimal(0));
                porcpl.setTaxamount5(new BigDecimal(0));
                porcpl.setTxrecvamt1(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpl.setTxrecvamt2(new BigDecimal(0));
                porcpl.setTxrecvamt3(new BigDecimal(0));
                porcpl.setTxrecvamt4(new BigDecimal(0));
                porcpl.setTxrecvamt5(new BigDecimal(0));
                porcpl.setTxexpsamt1(new BigDecimal(0));
                porcpl.setTxexpsamt2(new BigDecimal(0));
                porcpl.setTxexpsamt3(new BigDecimal(0));
                porcpl.setTxexpsamt4(new BigDecimal(0));
                porcpl.setTxexpsamt5(new BigDecimal(0));
                porcpl.setTxalloamt1(new BigDecimal(0));
                porcpl.setTxalloamt2(new BigDecimal(0));
                porcpl.setTxalloamt3(new BigDecimal(0));
                porcpl.setTxalloamt4(new BigDecimal(0));
                porcpl.setTxalloamt5(new BigDecimal(0));
                porcpl.setTxbaseallo(node.path("taxblAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpl.setTxincluded(new BigDecimal(0));
                porcpl.setTxexcluded(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpl.setTaxamount(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpl.setTxrecvamt(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpl.setTxexpsamt(new BigDecimal(0));
                porcpl.setTxalloamt(new BigDecimal(0));
                porcpl.setTfbaseallo(node.path("taxblAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpl.setTfinclude1(new BigDecimal(0));
                porcpl.setTfinclude2(new BigDecimal(0));
                porcpl.setTfinclude3(new BigDecimal(0));
                porcpl.setTfinclude4(new BigDecimal(0));
                porcpl.setTfinclude5(new BigDecimal(0));
                porcpl.setTfalloamt1(new BigDecimal(0));
                porcpl.setTfalloamt2(new BigDecimal(0));
                porcpl.setTfalloamt3(new BigDecimal(0));
                porcpl.setTfalloamt4(new BigDecimal(0));
                porcpl.setTfalloamt5(new BigDecimal(0));
                porcpl.setTfrecvamt1(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpl.setTfrecvamt2(new BigDecimal(0));
                porcpl.setTfrecvamt3(new BigDecimal(0));
                porcpl.setTfrecvamt4(new BigDecimal(0));
                porcpl.setTfrecvamt5(new BigDecimal("0"));
                porcpl.setTfexpsamt1(new BigDecimal("0"));
                porcpl.setTfexpsamt2(new BigDecimal("0.000"));
                porcpl.setTfexpsamt3(new BigDecimal("0.000"));
                porcpl.setTfexpsamt4(new BigDecimal("0.000"));
                porcpl.setTfexpsamt5(new BigDecimal("0.000"));
                porcpl.setGlacexpens("");
                porcpl.setDtarrival(currentDate());
                porcpl.setLabelcount((short) 0);
                porcpl.setMprorated(new BigDecimal("0.000"));
                porcpl.setHasdropshi((short) 0);
                porcpl.setDroptype((short) 2);
                porcpl.setIdcust("");
                porcpl.setIdcustshpt("");
                porcpl.setDlocation("");
                porcpl.setDesc("");
                porcpl.setAddress1("");
                porcpl.setAddress2("");
                porcpl.setAddress3("");
                porcpl.setAddress4("");
                porcpl.setCity("");
                porcpl.setState("");
                porcpl.setZip("");
                porcpl.setCountry("");
                porcpl.setPhone("");
                porcpl.setFax("");
                porcpl.setContact("e");
                porcpl.setStockitem(poporl.getStockitem());
                porcpl.setPonumber(poporh1.getPonumber());
                porcpl.setEmail("");
                porcpl.setPhonec("");
                porcpl.setFaxc("");
                porcpl.setEmailc("");
                porcpl.setGlnonstkcr("40070");
                porcpl.setManitemno("");
                porcpl.setDiscpct(new BigDecimal("0.0"));
                porcpl.setDiscount(new BigDecimal("0.000"));
                porcpl.setDiscountf(new BigDecimal("0.000"));
                porcpl.setXirqrecevd(node.path("qty").decimalValue());
                porcpl.setXiextwght(new BigDecimal("0.0000"));
                porcpl.setXinetxtend(node.path("taxblAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpl.setInvlines(node.path("itemSeq").asInt());
                porcpl.setValues(0);
                porcpl.setContract("");
                porcpl.setProject("");
                porcpl.setCcategory("");
                porcpl.setCostclass((short) 0);
                porcpl.setBillcurr("");
                porcpl.setAritemno("");
                porcpl.setArunit("");
                porcpl.setBillrate(new BigDecimal("0.000"));
                porcpl.setRtgpercent(new BigDecimal("0.0"));
                porcpl.setRtgdays((short) 0);
                porcpl.setRtgamount(new BigDecimal("0.000"));
                porcpl.setRtgamtover((short) 0);
                porcpl.setTaramount1(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpl.setTaramount2(new BigDecimal("0.000"));
                porcpl.setTaramount3(new BigDecimal("0.000"));
                porcpl.setTaramount4(new BigDecimal("0.000"));
                porcpl.setTaramount5(new BigDecimal("0.000"));
                porcpl.setTralloamt1(new BigDecimal("0.000"));
                porcpl.setTralloamt2(new BigDecimal("0.000"));
                porcpl.setTralloamt3(new BigDecimal("0.000"));
                porcpl.setTralloamt4(new BigDecimal("0.000"));
                porcpl.setTralloamt5(new BigDecimal("0.000"));
                porcpl.setTrrecvamt1(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpl.setTrrecvamt2(new BigDecimal("0.000"));
                porcpl.setTrrecvamt3(new BigDecimal("0.000"));
                porcpl.setTrrecvamt4(new BigDecimal("0.000"));
                porcpl.setTrrecvamt5(new BigDecimal("0.000"));
                porcpl.setTrexpsamt1(new BigDecimal("0.000"));
                porcpl.setTrexpsamt2(new BigDecimal("0.000"));
                porcpl.setTrexpsamt3(new BigDecimal("0.000"));
                porcpl.setTrexpsamt4(new BigDecimal("0.000"));
                porcpl.setTrexpsamt5(new BigDecimal("0.000"));
                porcpl.setRaxbase1(new BigDecimal("0.000"));
                porcpl.setRaxbase2(new BigDecimal("0.000"));
                porcpl.setRaxbase3(new BigDecimal("0.000"));
                porcpl.setRaxbase4(new BigDecimal("0.000"));
                porcpl.setRaxbase5(new BigDecimal("0.000"));
                porcpl.setRaxamount1(new BigDecimal("0.000"));
                porcpl.setRaxamount2(new BigDecimal("0.000"));
                porcpl.setRaxamount3(new BigDecimal("0.000"));
                porcpl.setRaxamount4(new BigDecimal("0.000"));
                porcpl.setRaxamount5(new BigDecimal("0.000"));
                porcpl.setRxrecvamt1(new BigDecimal("0.000"));
                porcpl.setRxrecvamt2(new BigDecimal("0.000"));
                porcpl.setRxrecvamt3(new BigDecimal("0.000"));
                porcpl.setRxrecvamt4(new BigDecimal("0.000"));
                porcpl.setRxrecvamt5(new BigDecimal("0.000"));
                porcpl.setRxexpsamt1(new BigDecimal("0.000"));
                porcpl.setRxexpsamt2(new BigDecimal("0.000"));
                porcpl.setRxexpsamt3(new BigDecimal("0.000"));
                porcpl.setRxexpsamt4(new BigDecimal("0.000"));
                porcpl.setRxexpsamt5(new BigDecimal("0.000"));
                porcpl.setRxalloamt1(new BigDecimal("0.000"));
                porcpl.setRxalloamt2(new BigDecimal("0.000"));
                porcpl.setRxalloamt3(new BigDecimal("0.000"));
                porcpl.setRxalloamt4(new BigDecimal("0.000"));
                porcpl.setRxalloamt5(new BigDecimal("0.000"));
                porcpl.setUcismanual((short) 1);
                porcpl.setWeightunit("");
                porcpl.setWeightconv(new BigDecimal("1.0"));
                porcpl.setDefuweight(new BigDecimal("0.0000"));
                porcpl.setDefextwght(new BigDecimal("0.0000"));
                porcpl.setXidefextwt(new BigDecimal("0.0000"));
                porcpl.setFasdetail((short) 0);
                porcpl.setSerialqty(0);
                porcpl.setLotqty(new BigDecimal("0.0000"));
                porcpl.setSlitem((short) 0);
                porcpl.setDetailnum((short) 0);
                porcpl.setCaxable1((short) 0);
                porcpl.setCaxable2((short) 0);
                porcpl.setCaxable3((short) 0);
                porcpl.setCaxable4((short) 0);
                porcpl.setCaxable5((short) 0);
                porcpl.setBilltype((short) 0);
                Icitem sItem = icitemRepository.findByItemno(itemno);
                PorcpnId porcpnId = new PorcpnId();
                porcpnId.setRcphseq(maxID.getId().add(BigDecimal.valueOf(2)));
                porcpnId.setRcplseq(maxsqlID());

                Porcpn porcpn = new Porcpn();
                porcpn.setId(porcpnId);
                porcpn.setAudtdate(currentDate());
                porcpn.setAudttime(currentTime());
                porcpn.setAudtuser("ADMIN");
                porcpn.setAudtorg(company);
                porcpn.setItemdesc(sItem.getDesc());
                porcpn.setStockunit(poporl.getOrderunit());
                porcpn.setRcpunit("");
                porcpn.setRcpconv(new BigDecimal("0.0"));
                porcpn.setRcpdecml((short) 4);
                porcpn.setRqreceived(node.path("qty").decimalValue());
                porcpn.setRqreturned(new BigDecimal("0.0"));
                porcpn.setRqcanceled(new BigDecimal("0.0"));
                porcpn.setSqreceived(poporl.getOqreceived());
                porcpn.setSqcanceled(new BigDecimal("0.0"));
                porcpn.setOqreceived(node.path("qty").decimalValue());
                porcpn.setOqcanceled(new BigDecimal("0.0"));
                porcpn.setRqpofilled(new BigDecimal("0.0"));
                porcpn.setSqpofilled(new BigDecimal("0.0"));
                porcpn.setOqpofilled(new BigDecimal("0.0"));
                porcpn.setUnitweight(new BigDecimal("0.0"));
                porcpn.setExtweight(new BigDecimal("0.0"));
                porcpn.setUnitcost(poporl.getUnitcost());
                porcpn.setExtended(poporl.getExtended());
                porcpn.setMprorated(new BigDecimal("0.0"));
                porcpn.setRcpdays((short) 0);
                porcpn.setTaxbase1(node.path("taxblAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpn.setTaxbase2(new BigDecimal("0.0"));
                porcpn.setTaxbase3(new BigDecimal("0.0"));
                porcpn.setTaxbase4(new BigDecimal("0.0"));
                porcpn.setTaxbase5(new BigDecimal("0.0"));
                porcpn.setTaxinclud1((short) 0);
                porcpn.setTaxinclud2((short) 0);
                porcpn.setTaxinclud3((short) 0);
                porcpn.setTaxinclud4((short) 0);
                porcpn.setTaxinclud5((short) 0);
                porcpn.setTxbaseallo(node.path("taxblAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpn.setTxinclude1(new BigDecimal("0.0"));
                porcpn.setTxinclude2(new BigDecimal("0.0"));
                porcpn.setTxinclude3(new BigDecimal("0.0"));
                porcpn.setTxinclude4(new BigDecimal("0.0"));
                porcpn.setTxinclude5(new BigDecimal("0.0"));
                porcpn.setTxrecvamt1(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpn.setTxrecvamt2(new BigDecimal("0.0"));
                porcpn.setTxrecvamt3(new BigDecimal("0.0"));
                porcpn.setTxrecvamt4(new BigDecimal("0.0"));
                porcpn.setTxrecvamt5(new BigDecimal("0.0"));
                porcpn.setTxexpsamt1(new BigDecimal("0.0"));
                porcpn.setTxexpsamt2(new BigDecimal("0.0"));
                porcpn.setTxexpsamt3(new BigDecimal("0.0"));
                porcpn.setTxexpsamt4(new BigDecimal("0.0"));
                porcpn.setTxexpsamt5(new BigDecimal("0.0"));
                porcpn.setTxalloamt1(new BigDecimal("0.0"));
                porcpn.setTxalloamt2(new BigDecimal("0.0"));
                porcpn.setTxalloamt3(new BigDecimal("0.0"));
                porcpn.setTxalloamt4(new BigDecimal("0.0"));
                porcpn.setTxalloamt5(new BigDecimal("0.0"));
                porcpn.setTfbaseallo(new BigDecimal(String.valueOf(node.path("taxblAmt").decimalValue().setScale(1, RoundingMode.HALF_UP))));
                porcpn.setTfinclude1(new BigDecimal("0.0"));
                porcpn.setTfinclude2(new BigDecimal("0.0"));
                porcpn.setTfinclude3(new BigDecimal("0.0"));
                porcpn.setTfinclude4(new BigDecimal("0.0"));
                porcpn.setTfinclude5(new BigDecimal("0.0"));
                porcpn.setTfalloamt1(new BigDecimal("0.0"));
                porcpn.setTfalloamt2(new BigDecimal("0.0"));
                porcpn.setTfalloamt3(new BigDecimal("0.0"));
                porcpn.setTfalloamt4(new BigDecimal("0.0"));
                porcpn.setTfalloamt5(new BigDecimal("0.0"));
                porcpn.setTfrecvamt1(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpn.setTfrecvamt2(new BigDecimal("0.0"));
                porcpn.setTfrecvamt3(new BigDecimal("0.0"));
                porcpn.setTfrecvamt4(new BigDecimal("0.0"));
                porcpn.setTfrecvamt5(new BigDecimal("0.0"));
                porcpn.setTfexpsamt1(new BigDecimal("0.0"));
                porcpn.setTfexpsamt2(new BigDecimal("0.0"));
                porcpn.setTfexpsamt3(new BigDecimal("0.0"));
                porcpn.setTfexpsamt4(new BigDecimal("0.0"));
                porcpn.setTfexpsamt5(new BigDecimal("0.0"));
                porcpn.setDiscpct(new BigDecimal("0.0"));
                porcpn.setDiscount(new BigDecimal("0.0"));
                porcpn.setDiscountf(new BigDecimal("0.0"));
                porcpn.setBillrate(new BigDecimal("0.0"));
                porcpn.setRtgpercent(new BigDecimal("0.0"));
                porcpn.setRtgdays((short) 0);
                porcpn.setRtgamount(new BigDecimal("0.0"));
                porcpn.setRtgamtover((short) 0);
                porcpn.setTrinclude1(new BigDecimal("0.0"));
                porcpn.setTrinclude2(new BigDecimal("0.0"));
                porcpn.setTrinclude3(new BigDecimal("0.0"));
                porcpn.setTrinclude4(new BigDecimal("0.0"));
                porcpn.setTrinclude5(new BigDecimal("0.0"));
                porcpn.setTrrecvamt1(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpn.setTrrecvamt2(new BigDecimal("0.0"));
                porcpn.setTrrecvamt3(new BigDecimal("0.0"));
                porcpn.setTrrecvamt4(new BigDecimal("0.0"));
                porcpn.setTrrecvamt5(new BigDecimal("0.0"));
                porcpn.setTrexpsamt1(new BigDecimal("0.0"));
                porcpn.setTrexpsamt2(new BigDecimal("0.0"));
                porcpn.setTrexpsamt3(new BigDecimal("0.0"));
                porcpn.setTrexpsamt4(new BigDecimal("0.0"));
                porcpn.setTrexpsamt5(new BigDecimal("0.0"));
                porcpn.setTralloamt1(new BigDecimal("0.0"));
                porcpn.setTralloamt2(new BigDecimal("0.0"));
                porcpn.setTralloamt3(new BigDecimal("0.0"));
                porcpn.setTralloamt4(new BigDecimal("0.0"));
                porcpn.setTralloamt5(new BigDecimal("0.0"));
                porcpn.setRaxbase1(new BigDecimal("0.0"));
                porcpn.setRaxbase2(new BigDecimal("0.0"));
                porcpn.setRaxbase3(new BigDecimal("0.0"));
                porcpn.setRaxbase4(new BigDecimal("0.0"));
                porcpn.setRaxbase5(new BigDecimal("0.0"));
                porcpn.setRxrecvamt1(new BigDecimal("0.0"));
                porcpn.setRxrecvamt2(new BigDecimal("0.0"));
                porcpn.setRxrecvamt3(new BigDecimal("0.0"));
                porcpn.setRxrecvamt4(new BigDecimal("0.0"));
                porcpn.setRxrecvamt5(new BigDecimal("0.0"));
                porcpn.setRxexpsamt1(new BigDecimal("0.0"));
                porcpn.setRxexpsamt2(new BigDecimal("0.0"));
                porcpn.setRxexpsamt3(new BigDecimal("0.0"));
                porcpn.setRxexpsamt4(new BigDecimal("0.0"));
                porcpn.setRxexpsamt5(new BigDecimal("0.0"));
                porcpn.setRxalloamt1(new BigDecimal("0.0"));
                porcpn.setRxalloamt2(new BigDecimal("0.0"));
                porcpn.setRxalloamt3(new BigDecimal("0.0"));
                porcpn.setRxalloamt4(new BigDecimal("0.0"));
                porcpn.setRxalloamt5(new BigDecimal("0.0"));
                porcpn.setWeightunit("");
                porcpn.setWeightconv(new BigDecimal("0.0"));
                porcpn.setDefuweight(new BigDecimal("0.0"));
                porcpn.setDefextwght(new BigDecimal("0.0"));
                porcpnRepository.save(porcpn);

                PorcpalId porcpalId = new PorcpalId();
                porcpalId.setDayendseq(dayEndNo());
                porcpalId.setRcpahseq(BigDecimal.valueOf(1));
                porcpalId.setRcpalseq(nextRcLineSeq);
                Porcpal porcpal = new Porcpal();
                porcpal.setId(porcpalId);
                porcpal.setAudtdate(currentDate());
                porcpal.setAudttime(currentTime());
                porcpal.setAudtuser("ADMIN");
                porcpal.setAudtorg(company);
                porcpal.setTranstype((short) 11);
                porcpal.setOeonumber("");
                porcpal.setItemexists((short) 1);
                porcpal.setItemno(sItem.getItemno());
                porcpal.setLocation("HQ");
                porcpal.setItemdesc(poporl.getDesc());
                porcpal.setCntlacct(sItem.getCategory());
                porcpal.setTaxclass1(poporl.getTaxclass1());
                porcpal.setTaxclass2((short) 1);
                porcpal.setTaxclass3((short) 1);
                porcpal.setTaxclass4((short) 1);
                porcpal.setTaxclass5((short) 1);

                porcpal.setRqreceived(new BigDecimal("0.0"));
                porcpal.setRqcanceled(new BigDecimal("0.0"));
                porcpal.setRcpunit(poporl.getOrderunit());
                porcpal.setConversion(new BigDecimal("0.0"));
                porcpal.setCostconv(new BigDecimal("0.0"));
                porcpal.setSqreceived(node.path("qty").decimalValue());
                porcpal.setStockunit(poporl.getOrderunit());
                porcpal.setCostunit(poporl.getOrderunit());
                porcpal.setUnitcost(node.path("prc").decimalValue());
                porcpal.setPrunitcost(new BigDecimal("0.0"));
                porcpal.setLoadedcost(node.path("prc").decimalValue());
                porcpal.setRecentcost(node.path("prc").decimalValue());
                porcpal.setFcextended(node.path("totAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpal.setScextended(node.path("totAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpal.setFcbaseallo(node.path("taxblAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpal.setScbaseallo(node.path("taxblAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpal.setFctaxallo(new BigDecimal("0.0"));
                porcpal.setSctaxallo(new BigDecimal("0.0"));
                porcpal.setFcprorated(new BigDecimal("0.0"));
                porcpal.setScprorated(new BigDecimal("0.0"));
                porcpal.setFctaxincl(new BigDecimal("0.0"));
                porcpal.setSctaxincl(new BigDecimal("0.0"));
                porcpal.setFctaxexcl(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpal.setSctaxexcl(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpal.setFcmprorate(new BigDecimal("0.0"));
                porcpal.setScmprorate(new BigDecimal("0.0"));
                porcpal.setTaxbase1(node.path("taxblAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpal.setTaxbase2(new BigDecimal("0.0"));
                porcpal.setTaxbase3(new BigDecimal("0.0"));
                porcpal.setTaxbase4(new BigDecimal("0.0"));
                porcpal.setTaxbase5(new BigDecimal("0.0"));
                porcpal.setTaxinclud1((short) 0);
                porcpal.setTaxinclud2((short) 0);
                porcpal.setTaxinclud3((short) 0);
                porcpal.setTaxinclud4((short) 0);
                porcpal.setTaxinclud5((short) 0);
                porcpal.setTaxamount1(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpal.setTaxamount2(new BigDecimal("0.0"));
                porcpal.setTaxamount3(new BigDecimal("0.0"));
                porcpal.setTaxamount4(new BigDecimal("0.0"));
                porcpal.setTaxamount5(new BigDecimal("0.0"));
                porcpal.setTxrecvamt1(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpal.setTxrecvamt2(new BigDecimal("0.0"));
                porcpal.setTxrecvamt3(new BigDecimal("0.0"));
                porcpal.setTxrecvamt4(new BigDecimal("0.0"));
                porcpal.setTxrecvamt5(new BigDecimal("0.0"));
                porcpal.setTxexpsamt1(new BigDecimal("0.0"));
                porcpal.setTxexpsamt2(new BigDecimal("0.0"));
                porcpal.setTxexpsamt3(new BigDecimal("0.0"));
                porcpal.setTxexpsamt4(new BigDecimal("0.0"));
                porcpal.setTxexpsamt5(new BigDecimal("0.0"));
                porcpal.setGlclearing("40060                                        ");
                porcpal.setGlitem(poporl.getGlnonstkcr());
                porcpal.setGlisposted((short) 1);
                porcpal.setRqrectotal(node.path("qty").decimalValue());
                porcpal.setSqrectotal(node.path("qty").decimalValue());
                porcpal.setFcexttotal(node.path("taxblAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpal.setScexttotal(node.path("taxblAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpal.setRcpdays((short) 0);
                porcpal.setLastcost(node.path("prc").decimalValue());
                porcpal.setStockitem(poporl.getStockitem());
                porcpal.setPonumber(poporh1.getPonumber());
                porcpal.setQivalinstk((short) 0);
                porcpal.setDiscpct(new BigDecimal("0.0"));
                porcpal.setFcdiscount(new BigDecimal("0.0"));
                porcpal.setScdiscount(new BigDecimal("0.0"));
                porcpal.setFcdisctot(new BigDecimal("0.0"));
                porcpal.setScdisctot(new BigDecimal("0.0"));
                porcpal.setValues(0);
                porcpal.setRcplseq(maxsqlID());
                porcpal.setFctaxrecv(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpal.setSctaxrecv(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpal.setFctaxexps(new BigDecimal("0.0"));
                porcpal.setSctaxexps(new BigDecimal("0.0"));
                porcpal.setFctaxamt1(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpal.setFctaxamt2(new BigDecimal("0.0"));
                porcpal.setFctaxamt3(new BigDecimal("0.0"));
                porcpal.setFctaxamt4(new BigDecimal("0.0"));
                porcpal.setFctaxamt5(new BigDecimal("0.0"));
                porcpal.setComment("");
                porcpal.setContract("");
                porcpal.setProject("");
                porcpal.setCcategory("");
                porcpal.setCostclass((short) 0);
                porcpal.setBilltype((short)0);
                porcpal.setBillrate(new BigDecimal("0.0"));
                porcpal.setBillcurr("");
                porcpal.setAritemno("");
                porcpal.setArunit("");
                porcpal.setRtgpercent(new BigDecimal("0.0"));
                porcpal.setRtgdays((short) 0);
                porcpal.setScrtgamt(new BigDecimal("0.0"));
                porcpal.setScrtgamtot(new BigDecimal("0.0"));
                porcpal.setRtgamtover((short) 0);
                porcpal.setGloverhead("");
                porcpal.setGllabor("");
                porcpal.setFcovrhdamt(new BigDecimal("0.0"));
                porcpal.setScovrhdamt(new BigDecimal("0.0"));
                porcpal.setFclaboramt(new BigDecimal("0.0"));
                porcpal.setSclaboramt(new BigDecimal("0.0"));
                porcpal.setDfcunitcst(node.path("prc").decimalValue());
                porcpal.setDscunitcst(node.path("prc").decimalValue());
                porcpal.setDbillrate(new BigDecimal("0.0"));
                porcpal.setPmtransnum(0);
                porcpal.setRctaxallo(new BigDecimal("0.0"));
                porcpal.setRctaxrecv(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpal.setRctaxexps(new BigDecimal("0.0"));
                porcpal.setRctaxincl(new BigDecimal("0.0"));
                porcpal.setTaramount1(node.path("vatAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                porcpal.setTaramount2(new BigDecimal("0.0"));
                porcpal.setTaramount3(new BigDecimal("0.0"));
                porcpal.setTaramount4(new BigDecimal("0.0"));
                porcpal.setTaramount5(new BigDecimal("0.0"));
                porcpal.setScraxallo(new BigDecimal("0.0"));
                porcpal.setFcraxallo(new BigDecimal("0.0"));
                porcpal.setScraxexps(new BigDecimal("0.0"));
                porcpal.setFcraxexps(new BigDecimal("0.0"));
                porcpal.setDefextwght(new BigDecimal("0.0"));
                porcpal.setTotdefexwt(new BigDecimal("0.0"));
                porcpal.setDqreceived(node.path("qty").decimalValue());
                porcpal.setDeltaconve(new BigDecimal("0"));
                porcpal.setDeltaunit(poporl.getOrderunit());
                porcpal.setDetailnum(node.path("itemSeq").shortValue());
                porcpalRepository.save(porcpal);
                PoprxlId poprxlId = new PoprxlId();
                poprxlId.setLineseq(nextRcLineSeq);
                poprxlId.setProrseq(poprxhInst.add(new BigDecimal(1)));
                Poprxl poprxl = new Poprxl();
                // Set the ID
                poprxl.setId(poprxlId);
                poprxl.setAudtdate(currentDate());
                poprxl.setAudttime(currentTime());
                poprxl.setAudtuser("ADMIN");
                poprxl.setAudtorg(company);
                poprxl.setScextended(node.path("totAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                poprxl.setWtextended(new BigDecimal("0.0"));
                poprxl.setRqextended(node.path("qty").decimalValue());
                poprxl.setScreturned(new BigDecimal("0.0"));
                poprxl.setWtreturned(new BigDecimal("0.0"));
                poprxl.setRqreturned(new BigDecimal("0.0"));
                poprxl.setScextendpr(node.path("totAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                poprxl.setWtextendpr(new BigDecimal("0.0001"));
                poprxl.setRqextendpr(node.path("totAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
                poprxl.setScreturnpr(new BigDecimal("0.0"));
                poprxl.setWtreturnpr(new BigDecimal("0.0"));
                poprxl.setRqreturnpr(new BigDecimal("0.0"));
                poprxl.setLineflags(0);
                poprxl.setForprorate((short) 1);
                poprxl.setScextendpb(new BigDecimal("0.0"));
                poprxl.setWtextendpb(new BigDecimal("0.0"));
                poprxl.setRqextendpb(new BigDecimal("0.0"));
                poprxl.setScreturnpb(new BigDecimal("0.0"));
                poprxl.setWtreturnpb(new BigDecimal("0.0"));
                poprxl.setRqreturnpb(new BigDecimal("0.0"));
                poprxlRepository.save(poprxl);
                nextPrxLineSeq.add(new BigDecimal(1));
// Save the instance
                porcplRepo.save(porcpl);
                rcpcseq = rcpcseq.add(new BigDecimal(2));
                rcplseq = rcplseq.add(new BigDecimal(2));
                rcplrev = rcplrev.add(new BigDecimal(31));
                nextRcLineSeq.add(new BigDecimal(2));
//
                //Icival


            }
        }






        Porcph1 porcph1Instance = Porcph1.builder()
                .id(maxID.getId().add(BigDecimal.valueOf(2)))
                .audtdate(currentDate())
                .audttime(currentTime())
                .audtuser("ADMIN")
                .audtorg(company)
                .nextlseq(maxID.getNextlseq().add(BigDecimal.valueOf(1)))
                .lines(sale.path("totItemCnt").asInt())
                .linesprora(sale.path("totItemCnt").asInt())
                .linescmpl(sale.path("totItemCnt").asInt())
                .costs(0)
                .costsprora(0)
                .costscmpl(0)
                .vends(0)
                .vendscmpl(0)
                .vendsinvc(0)
                .taxlines(1)
                .extraneous(0)
                .taxautocal((short)1)
                .isprinted((short)0)
                .isinvoiced((short)0)
                .iscomplete((short)0)
                .dtcomplete(new BigDecimal(0))
                .postdate(currentDate())
                .date(currentDate())
                .fiscyear(fscyear())
                .fiscperiod(fscyper())
                .rcpnumber(nextReceiptNo())
                .template("")
                .fobpoint("")
                .vdcode(poporh1.getVdcode())
                .vdexists((short)1)
                .vdname(poporh1.getVdname())
                .vdaddress1(poporh1.getVdaddress1())
                .vdaddress2(poporh1.getVdaddress2())
                .vdaddress3(poporh1.getVdaddress3())
                .vdaddress4(poporh1.getVdaddress4())
                .vdcity(poporh1.getVdcity())
                .vdstate(poporh1.getVdstate())
                .vdzip(poporh1.getVdzip())
                .vdcountry(poporh1.getVdcountry())
                .vdphone("")
                .vdfax("")
                .vdcontact("")
                .termscode("30DAYS")
                .porhseq(poporh1.getId())
                .ponumber(poporh1.getPonumber())
                .invnumber(inv)
                .descriptio(sale.path("remark").asText())
                .reference("")
                .comment("")
                .viacode("")
                .vianame("")
                .currency("ZMW")
                .rate(new BigDecimal("1"))
                .spread(new BigDecimal("0.0"))
                .ratetype("SP")
                .ratematch((short)1)
                .ratedate(currentDate())
                .rateoper((short)1)
                .rateover((short)0)
                .scurndecml((short)2)
                .extweight(new BigDecimal("0.0"))
                .extended(sale.path("totTaxblAmt").decimalValue().setScale(1, RoundingMode.HALF_UP))
                .doctotal(sale.path("totAmt").decimalValue().setScale(1, RoundingMode.HALF_UP))
                .amount(new BigDecimal("0.00"))
                .rqreceived(totalQty)
                .taxgroup("VATZMW01    ")
                .taxauth1("VATZMW      ")
                .taxauth2("")
                .taxauth3("")
                .taxauth4("")
                .taxauth5("")
                .taxclass1((short)1)
                .taxclass2((short)1)
                .taxclass3((short)1)
                .taxclass4((short)1)
                .taxclass5((short)1)
                .taxbase1(sale.path("totTaxblAmt").decimalValue().setScale(1, RoundingMode.HALF_UP))
                .taxbase2(new BigDecimal("0"))
                .taxbase3(new BigDecimal("0"))
                .taxbase4(new BigDecimal("0"))
                .taxbase5(new BigDecimal("0"))
                .txinclude1(new BigDecimal("0"))
                .txinclude2(new BigDecimal("0"))
                .txinclude3(new BigDecimal("0"))
                .txinclude4(new BigDecimal("0"))
                .txinclude5(new BigDecimal("0"))
                .txexclude1(sale.path("totTaxAmt").decimalValue().setScale(1, RoundingMode.HALF_UP))
                .txexclude2(new BigDecimal("0"))
                .txexclude3(new BigDecimal("0"))
                .txexclude4(new BigDecimal("0"))
                .txexclude5(new BigDecimal("0"))
                .taxamount1(sale.path("totTaxAmt").decimalValue().setScale(1, RoundingMode.HALF_UP))
                .taxamount2(new BigDecimal("0"))
                .taxamount3(new BigDecimal("0"))
                .taxamount4(new BigDecimal("0"))
                .taxamount5(new BigDecimal("0"))
                .txrecvamt1(sale.path("totTaxAmt").decimalValue().setScale(1, RoundingMode.HALF_UP))
                .txrecvamt2(new BigDecimal("0"))
                .txrecvamt3(new BigDecimal("0"))
                .txrecvamt4(new BigDecimal("0"))
                .txrecvamt5(new BigDecimal("0"))
                .txexpsamt1(new BigDecimal("0"))
                .txexpsamt2(new BigDecimal("0"))
                .txexpsamt3(new BigDecimal("0"))
                .txexpsamt4(new BigDecimal("0"))
                .txexpsamt5(new BigDecimal("0"))
                .txalloamt1(new BigDecimal("0.00"))
                .txalloamt2(new BigDecimal("0.00"))
                .txalloamt3(new BigDecimal("0.00"))
                .txalloamt4(new BigDecimal("0.00"))
                .txalloamt5(new BigDecimal("0.00"))
                .txbaseallo(sale.path("totTaxblAmt").decimalValue().setScale(1, RoundingMode.HALF_UP))
                .txincluded(new BigDecimal("0.00"))
                .txexcluded(sale.path("totTaxAmt").decimalValue().setScale(1, RoundingMode.HALF_UP))
                .taxamount(sale.path("totTaxAmt").decimalValue().setScale(1, RoundingMode.HALF_UP))
                .txrecvamt(sale.path("totTaxAmt").decimalValue().setScale(1, RoundingMode.HALF_UP))
                .txexpsamt(new BigDecimal("0.00"))
                .txalloamt(new BigDecimal("0.00"))
                .mprorated(new BigDecimal("0.00"))
                .mtoprorate(new BigDecimal("0.00"))
                .scamount(sale.path("totAmt").decimalValue().setScale(1, RoundingMode.HALF_UP))
                .fcamount(sale.path("totAmt").decimalValue().setScale(1, RoundingMode.HALF_UP))
                .multipor((short) 0)
                .pors(0)
                .vdemail("")
                .vdphonec("")
                .vdfaxc("")
                .vdemailc("")
                .discpct(new BigDecimal("0.00"))
                .discount(new BigDecimal("0.00"))
                .values(0)
                .verprorate((short) 2)
                .hasrtg((short) 0)
                .rtgrate((short) 0)
                .rtgterms("")
                .joblines(0)
                .jobcosts(0)
                .billlines(0)
                .costsblpro(0)
                .rtgbase((short) 0)
                .rtgamount(new BigDecimal("0.00"))
                .trcurrency("ZMW")
                .raterc(new BigDecimal("1.00"))
                .spreadrc(new BigDecimal("0.0"))
                .ratetyperc("SP")
                .ratemtchrc((short) 1)
                .ratedaterc(currentDate())
                .rateoperrc((short) 2)
                .ratercover((short) 0)
                .rcurndecml((short) 1)
                .taramount1(sale.path("totTaxAmt").decimalValue().setScale(1, RoundingMode.HALF_UP))
                .taramount2(new BigDecimal("0.00"))
                .taramount3(new BigDecimal("0.00"))
                .taramount4(new BigDecimal("0.00"))
                .taramount5(new BigDecimal("0.00"))
                .trinclude1(new BigDecimal("0.00"))
                .trinclude2(new BigDecimal("0.00"))
                .trinclude3(new BigDecimal("0.00"))
                .trinclude4(new BigDecimal("0.00"))
                .trinclude5(new BigDecimal("0.00"))
                .trexclude1(sale.path("totTaxAmt").decimalValue().setScale(1, RoundingMode.HALF_UP))
                .trexclude2(new BigDecimal("0.00"))
                .trexclude3(new BigDecimal("0.00"))
                .trexclude4(new BigDecimal("0.00"))
                .trexclude5(new BigDecimal("0.00"))
                .trrecvamt1(sale.path("totTaxAmt").decimalValue().setScale(1, RoundingMode.HALF_UP))
                .trrecvamt2(new BigDecimal("0.00"))
                .trrecvamt3(new BigDecimal("0.00"))
                .trrecvamt4(new BigDecimal("0.00"))
                .trrecvamt5(new BigDecimal("0.00"))
                .trexpsamt1(new BigDecimal("0.00"))
                .trexpsamt2(new BigDecimal("0.00"))
                .trexpsamt3(new BigDecimal("0.00"))
                .trexpsamt4(new BigDecimal("0.00"))
                .trexpsamt5(new BigDecimal("0.00"))
                .tralloamt1(new BigDecimal("0.00"))
                .tralloamt2(new BigDecimal("0.00"))
                .tralloamt3(new BigDecimal("0.00"))
                .tralloamt4(new BigDecimal("0.00"))
                .tralloamt5(new BigDecimal("0.00"))
                .rtgtaxrep((short) 0)
                .raxbase1(new BigDecimal("0.00"))
                .raxbase2(new BigDecimal("0.00"))
                .raxbase3(new BigDecimal("0.00"))
                .raxbase4(new BigDecimal("0.00"))
                .raxbase5(new BigDecimal("0.00"))
                .raxamount1(new BigDecimal("0.00"))
                .raxamount2(new BigDecimal("0.00"))
                .raxamount3(new BigDecimal("0.00"))
                .raxamount4(new BigDecimal("0.00"))
                .raxamount5(new BigDecimal("0.00"))
                .rxrecvamt1(new BigDecimal("0.00"))
                .rxrecvamt2(new BigDecimal("0.00"))
                .rxrecvamt3(new BigDecimal("0.00"))
                .rxrecvamt4(new BigDecimal("0.00"))
                .rxrecvamt5(new BigDecimal("0.00"))
                .rxexpsamt1(new BigDecimal("0.00"))
                .rxexpsamt2(new BigDecimal("0.00"))
                .rxexpsamt3(new BigDecimal("0.00"))
                .rxexpsamt4(new BigDecimal("0.00"))
                .rxexpsamt5(new BigDecimal("0.00"))
                .rxalloamt1(new BigDecimal("0.00"))
                .rxalloamt2(new BigDecimal("0.00"))
                .rxalloamt3(new BigDecimal("0.00"))
                .rxalloamt4(new BigDecimal("0.00"))
                .rxalloamt5(new BigDecimal("0.00"))
                .build();
        porcph1Repo.save(porcph1Instance);

        Porcph2 porcph2Instance = Porcph2.builder()
                .id(maxID.getId().add(BigDecimal.valueOf(2)))
                .audtdate(currentDate())
                .audttime(currentTime())
                .audtuser("ADMIN")
                .audtorg(company)
                .btcode("")
                .btdesc("")
                .btaddress1("")
                .btaddress2("")
                .btaddress3("")
                .btaddress4("")
                .btcity("")
                .btstate("")
                .btzip("")
                .btcountry("")
                .btphone("")
                .btfax("")
                .btcontact("")
                .stcode("")
                .stdesc("")
                .staddress1("")
                .staddress2("")
                .staddress3("")
                .staddress4("")
                .stcity("")
                .ststate("")
                .stzip("")
                .stcountry("")
                .stphone("")
                .stfax("")
                .stcontact("")
                .pdrate(new BigDecimal("1.0"))
                .pdratetype("SP")
                .pdratedate(currentDate())
                .pdrateoper((short)1)
                .pdrateover((short)0)
                .btemail("")
                .btphonec("")
                .btfaxc("")
                .btemailc("")
                .stemail("")
                .stphonec("")
                .stfaxc("")
                .stemailc("")
                .pdraterc(new BigDecimal("1.0"))
                .pdrattyprc("SP")
                .pdratedtrc(currentDate())
                .pdrateoprc((short)1)
                .pdratercov((short)0)
                .vdacctset("ZMW")
                .datebus(currentDate())
                .enteredby("ADMIN")
                .detailnext((short)2)
                .caxbase1(new BigDecimal("0.0"))
                .caxbase2(new BigDecimal("0.0"))
                .caxbase3(new BigDecimal("0.0"))
                .caxbase4(new BigDecimal("0.0"))
                .caxbase5(new BigDecimal("0.0"))
                .caxdtamt1(new BigDecimal("0"))
                .caxdtamt2(new BigDecimal("0"))
                .caxdtamt3(new BigDecimal("0"))
                .caxdtamt4(new BigDecimal("0"))
                .caxdtamt5(new BigDecimal("0"))
                .caxapply1((short)0)
                .caxapply2((short)0)
                .caxapply3((short)0)
                .caxapply4((short)0)
                .caxapply5((short)0)
                .caxamount1(new BigDecimal("0.0"))
                .caxamount2(new BigDecimal("0.0"))
                .caxamount3(new BigDecimal("0.0"))
                .caxamount4(new BigDecimal("0.0"))
                .caxamount5(new BigDecimal("0.0"))
                .build();

        porcph2Repo.save(porcph2Instance);

        BigDecimal porcpjSeq = porcpjRepo.findMaxProrateSeq();
        Porcpj porcpj = new Porcpj();
        porcpj.setId(maxID.getId().add(BigDecimal.valueOf(2)));
        porcpj.setAudtdate(currentDate());
        porcpj.setAudttime(currentTime());
        porcpj.setAudtuser("ADMIN");
        porcpj.setAudtorg(company);
        porcpj.setProrateseq(poprxhInst.add(new BigDecimal(1)));
        porcpj.setPostdate(currentDate());
        porcpj.setScamount(sale.path("totAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
        porcpj.setFcamount(sale.path("totAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
        porcpj.setScdoctotal(sale.path("totAmt").decimalValue().setScale(1, RoundingMode.HALF_UP));
        porcpj.setIscomplete((short) 0);
        porcpj.setDtcomplete(new BigDecimal(0));
        porcpj.setScrtgamt(new BigDecimal("0.0"));
        porcpj.setCaxamount(new BigDecimal("0.0"));
        porcpjRepo.save(porcpj);
        Poprxh poprxh = new Poprxh();

        poprxh.setId(poprxhInst.add(new BigDecimal(1)));
        poprxh.setAudtdate(currentDate());
        poprxh.setAudttime(currentTime());
        poprxh.setAudtuser("ADMIN");
        poprxh.setAudtorg(company);
        poprxh.setTotcbase(sale.path("totAmt").decimalValue().setScale(1,RoundingMode.HALF_UP));
        poprxh.setTotqbase(totalQty);
        poprxh.setTotwbase(new BigDecimal("0.0"));
        poprxh.setTotcrtns(new BigDecimal("0.0"));
        poprxh.setTotqrtns(new BigDecimal("0.0"));
        poprxh.setTotwrtns(new BigDecimal("0.0"));
        poprxh.setPrvcbase(sale.path("totAmt").decimalValue().setScale(1,RoundingMode.HALF_UP));
        poprxh.setPrvqbase(new BigDecimal("0.0"));
        poprxh.setPrvwbase(new BigDecimal("0.0001"));
        poprxh.setPrvcrtns(new BigDecimal("0.0"));
        poprxh.setPrvqrtns(new BigDecimal("0.0"));
        poprxh.setPrvwrtns(new BigDecimal("0.0"));
        poprxh.setReferences(new BigDecimal("0"));
        poprxh.setVerprorate((short) 2);
        poprxh.setTotcbaseb(new BigDecimal("0.0"));
        poprxh.setTotqbaseb(new BigDecimal("0.0"));
        poprxh.setTotwbaseb(new BigDecimal("0.0"));
        poprxh.setTotcrtnsb(new BigDecimal("0.0"));
        poprxh.setTotqrtnsb(new BigDecimal("0.0"));
        poprxh.setTotwrtnsb(new BigDecimal("0.0"));
        poprxh.setPrvcbaseb(new BigDecimal("0.0"));
        poprxh.setPrvqbaseb(new BigDecimal("0.0"));
        poprxh.setPrvwbaseb(new BigDecimal("0.0"));
        poprxh.setPrvcrtnsb(new BigDecimal("0.0"));
        poprxh.setPrvqrtnsb(new BigDecimal("0.0"));
        poprxh.setPrvwrtnsb(new BigDecimal("0.0"));
        poprxh.setRtgbase((short) 0);
        poprxhRepository.save(poprxh);
        PorcpahId porcpahId = new PorcpahId();
        porcpahId.setDayendseq(dayEndNo());
        porcpahId.setRcpahseq(BigDecimal.valueOf(1));
        Porcpah porcpah = new Porcpah();
        porcpah.setId(porcpahId);
        porcpah.setAudtdate(currentDate());
        porcpah.setAudttime(currentTime());
        porcpah.setAudtuser("ADMIN");
        porcpah.setAudtorg(company);
        porcpah.setIsprinted((short) 0);
        porcpah.setRcphseq(maxID.getId().add(BigDecimal.valueOf(2)));
        porcpah.setPostdate(currentDate());
        porcpah.setDayenddate(new BigDecimal(20240908));
        porcpah.setTransdate(currentDate());
        porcpah.setReference(poporh1.getReference());
        porcpah.setFiscyear(fscyear());
        porcpah.setFiscperiod(fscyper());
        porcpah.setDescriptio(poporh1.getDescriptio());
        porcpah.setTranstype((short) 1);
        porcpah.setVendor(poporh1.getVdcode());
        porcpah.setVendorname(poporh1.getVdname());
        porcpah.setTaxgroup(apven.get().getCodetaxgrp());
        porcpah.setTaxauth1("VATZMW      ");
        porcpah.setTaxauth2("");
        porcpah.setTaxauth3("");
        porcpah.setTaxauth4("");
        porcpah.setTaxauth5("");
        porcpah.setTaxclass1(poporh1.getTaxclass1());
        porcpah.setTaxclass2((short) 1);
        porcpah.setTaxclass3((short) 1);
        porcpah.setTaxclass4((short) 1);
        porcpah.setTaxclass5((short) 1);
        porcpah.setPonumber(poporh1.getPonumber());
        porcpah.setRcpnumber(nextReceiptNo());
        porcpah.setRcpcurr(poporh1.getCurrency());
        porcpah.setExrate(poporh1.getRate());
        porcpah.setRatedate(currentDate());
        porcpah.setRatetype("SP");
        porcpah.setRateoper((short) 1);
        porcpah.setRateover((short) 0);
        porcpah.setScurndecml((short) 2);
        porcpah.setFcdoctotal(poporh1.getDoctotal());
        porcpah.setScdoctotal(poporh1.getDoctotal());
        porcpah.setComplete((short) 0);
        porcpah.setPrinted((short) 0);
        porcpah.setMultipor((short) 0);
        porcpah.setValues(0);
        porcpah.setPgmver("71A");
        porcpah.setTransnum(new BigDecimal("0"));
        porcpah.setVerprorate((short) 2);
        porcpah.setHasrtg((short) 0);
        porcpah.setRtgrate((short) 0);
        porcpah.setRtgbase((short) 0);
        porcpah.setScrtgamt(new BigDecimal("0.0"));
        porcpah.setHasjob((short) 0);
        porcpah.setTrcurrency(apven.get().getCurncode());
        porcpah.setExraterc(new BigDecimal("1.0"));
        porcpah.setRatedaterc(currentDate());
        porcpah.setRatetyperc("SP");
        porcpah.setRateoperrc((short) 1);
        porcpah.setRatercover((short) 0);
        porcpah.setRcurndecml((short) 2);
        porcpah.setDatebus(new BigDecimal(20240908));
        porcpah.setVdacctset(apven.get().getIdacctset());
        porcpah.setCaxamount(new BigDecimal("0.0"));
        porcpahRepository.save(porcpah);

    }public void createPOInvoice(JsonNode sale, String ponumber,List<postPOItemsDTO>items){
        Porcph1 maxID = porcph1Repo.findMaxId();
    Poporh1 poporh1 = poporh1Repo.findByPonumberContaining(ponumber);
    Optional<Apven> apven = apvenRepo.findByVendorid(poporh1.getVdcode());
    Poinvh1 poinvh1Instance = poinvh1Repository.findMaxId();

    /*AP Posted Batch
        Apobl apobl = Apobl.builder()
                .id(ApoblId.builder()
                        .idvend(apven.get().getVendorid())
                        .idinvc(sale.path("spplrInvcNo").asText())
                        .build())
                .audtdate(currentDate())
                .audttime(currentTime())
                .audtuser("ADMIN")
                .audtorg(company)
                .idrmit("")
                .idordernbr(poporh1.getPonumber())
                .idponbr("")
                .dateinvcdu(currentDate())
                .idrmitto("")
                .idtrxtype((short) 12) // Assuming Short fields are initialized to 0
                .txttrxtype((short) 1)
                .datebtch(currentDate())
                .cntbtch(new BigDecimal(getBatchNumber()))
                .cntitem(new BigDecimal("1"))
                .idvendgrp(apven.get().getCodetaxgrp())
                .descinvc(poporh1.getDescriptio())
                .dateinvc(currentDate())
                .dateasof(currentDate())
                .codeterm("COD")
                .datedisc(new BigDecimal("0"))
                .codecurn(poporh1.getCurrency())
                .idratetype("SP")
                .swrateovrd((short) 0)
                .exchratehc(poporh1.getRate())
                .amtinvchc(sale.path("totAmt").decimalValue())
                .amtduehc(sale.path("totAmt").decimalValue())
                .amttxblhc(new BigDecimal("0.0"))
                .amtnontxhc(new BigDecimal("0.0"))
                .amttaxhc(sale.path("totVatAmt").decimalValue())
                .amtdischc(new BigDecimal("0.0"))
                .amtinvctc(sale.path("totAmt").decimalValue())
                .amtduetc(sale.path("totAmt").decimalValue())
                .amttxbltc(new BigDecimal("0.0"))
                .amtnontxtc(new BigDecimal("0.0"))
                .amttaxtc(sale.path("totVatAmt").decimalValue())
                .amtdisctc(new BigDecimal("0.0"))
                .swpaid((short) 0)
                .datelstact(new BigDecimal("0.0"))
                .datelststm(new BigDecimal("0.0"))
                .cnttotpaym(new BigDecimal("1"))
                .cntlstpaym(new BigDecimal("0.0"))
                .cntlstpyst(new BigDecimal("0.0"))
                .amtremit(new BigDecimal("0.0"))
                .cntlastsch(new BigDecimal("0.0"))
                .swtaxovrd((short) 0)
                .codetax1("VAT"+poporh1.getCurrency())
                .codetax2("")
                .codetax3("")
                .codetax4("")
                .codetax5("")
                .amtbase1hc(sale.path("totTaxblAmt").decimalValue())
                .amtbase2hc(new BigDecimal("0.0"))
                .amtbase3hc(new BigDecimal("0.0"))
                .amtbase4hc(new BigDecimal("0.0"))
                .amtbase5hc(new BigDecimal("0.0"))
                .amttax1hc(sale.path("totVatAmt").decimalValue())
                .amttax2hc(new BigDecimal("0.0"))
                .amttax3hc(new BigDecimal("0.0"))
                .amttax4hc(new BigDecimal("0.0"))
                .amttax5hc(new BigDecimal("0.0"))
                .amtbase1tc(sale.path("totTaxblAmt").decimalValue())
                .amtbase2tc(new BigDecimal("0.0"))
                .amtbase3tc(new BigDecimal("0.0"))
                .amtbase4tc(new BigDecimal("0.0"))
                .amtbase5tc(new BigDecimal("0.0"))
                .amttax1tc(sale.path("totVatAmt").decimalValue())
                .amttax2tc(new BigDecimal("0.0"))
                .amttax3tc(new BigDecimal("0.0"))
                .amttax4tc(new BigDecimal("0.0"))
                .amttax5tc(new BigDecimal("0.0"))
                .fiscyr("")
                .fiscper("")
                .idprepay("")
                .datebus(currentDate())
                .id1099clas("")
                .amt1099org(new BigDecimal("0.0"))
                .amt1099rem(new BigDecimal("0.0"))
                .ratedate(currentDate())
                .rateop((short) 1)
                .yplastact("")
                .idbank("")
                .longserial(0L) // Assuming Long is initialized to 0
                .postseqnce(new BigDecimal("postingSeq"))
                .swjob((short) 0)
                .swrtg((short) 0)
                .swrtgout((short) 0)
                .rtgdatedue(new BigDecimal("0.0"))
                .rtgoamthc(new BigDecimal("0.0"))
                .rtgamthc(new BigDecimal("0.0"))
                .rtgoamttc(new BigDecimal("0.0"))
                .rtgamttc(new BigDecimal("0.0"))
                .rtgterms("")
                .swrtgrate((short) 0)
                .rtgapplyto("")
                .values(0) // Assuming Integer is initialized to 0
                .srceappl("PO")
                .swpystts((short) 0)
                .datepystts(new BigDecimal("0.0"))
                .apversion("71A")
                .typebtch("IN")
                .cntoblj(1) // Assuming Integer is initialized to 0
                .codecurnrc(poporh1.getCurrency())
                .raterc(new BigDecimal("1"))
                .ratetyperc("SP")
                .ratedaterc(currentDate())
                .rateoprc((short) 1)
                .swraterc((short) 0)
                .swtxrtgrpt((short) 0)
                .codetaxgrp(apven.get().getCodetaxgrp())
                .taxversion(1) // Assuming Integer is initialized to 0
                .swtxbsectl((short) 0)
                .swtxctlrc((short) 0)
                .taxclass1((short) 0)
                .taxclass2((short) 0)
                .taxclass3((short) 0)
                .taxclass4((short) 0)
                .taxclass5((short) 0)
                .swtaxincl1((short) 0)
                .swtaxincl2((short) 0)
                .swtaxincl3((short) 0)
                .swtaxincl4((short) 0)
                .swtaxincl5((short) 0)
                .txbsert1tc(new BigDecimal("0.0"))
                .txbsert2tc(new BigDecimal("0.0"))
                .txbsert3tc(new BigDecimal("0.0"))
                .txbsert4tc(new BigDecimal("0.0"))
                .txbsert5tc(new BigDecimal("0.0"))
                .txamtrt1tc(new BigDecimal("0.0"))
                .txamtrt2tc(new BigDecimal("0.0"))
                .txamtrt3tc(new BigDecimal("0.0"))
                .txamtrt4tc(new BigDecimal("0.0"))
                .txamtrt5tc(new BigDecimal("0.0"))
                .datefrstbk(new BigDecimal("0.0"))
                .datelstrvl(new BigDecimal("0.0"))
                .orate(poporh1.getRate())
                .oratetype("SP")
                .oratedate(currentDate())
                .orateop((short) 0)
                .oswrate((short) 0)
                .idacctset(poporh1.getCurrency())
                .datepaid(new BigDecimal("0.0"))
                .swnonrcvbl((short) 0)
                .oamtwht1tc(new BigDecimal("0.0"))
                .oamtwht2tc(new BigDecimal("0.0"))
                .oamtwht3tc(new BigDecimal("0.0"))
                .oamtwht4tc(new BigDecimal("0.0"))
                .oamtwht5tc(new BigDecimal("0.0"))
                .build();
        apoblRepo.save(apobl);*/


        /*//Accounts Payable Batch

        //Headers
        Apibh apibh = new Apibh();

        apibh.setId(new ApibhId());
        apibh.getId().setCntbtch(new BigDecimal(getBatchNumber()));
        apibh.getId().setCntitem(BigDecimal.valueOf(1));

        apibh.setAudtdate(currentDate());
        apibh.setAudttime(currentTime());
        apibh.setAudtuser("ADMIN");
        apibh.setAudtorg(company);
        apibh.setIdvend(poporh1.getVdcode());
        apibh.setIdinvc(sale.path("spplrInvcNo").asText());
        apibh.setIdrmitto("");
        apibh.setAmtcxtx1tc(BigDecimal.valueOf(0));
        apibh.setAmtcxtx2tc(BigDecimal.valueOf(0));
        apibh.setAmtcxtx3tc(BigDecimal.valueOf(0));
        apibh.setAmtcxtx4tc(BigDecimal.valueOf(0));
        apibh.setAmtcxtx5tc(BigDecimal.valueOf(0));
        apibh.setTexttrx((short) 1);
        apibh.setIdtrx((short) 12);
        apibh.setInvcstts((short) 0);
        apibh.setOrdrnbr("");
        apibh.setPonbr(poporh1.getPonumber());
        apibh.setInvcdesc(poporh1.getDescriptio());
        apibh.setSwprtinvc((short) 0);
        apibh.setInvcapplto("");
        apibh.setIdacctset(poporh1.getCurrency());
        apibh.setDateinvc(currentDate());
        apibh.setDateasof(currentDate());
        apibh.setFiscyr(fscyear());
        apibh.setFiscper(String.valueOf(fscyper()));
        apibh.setCodecurn(poporh1.getCurrency());
        apibh.setRatetype("SP");
        apibh.setSwmanrte((short) 0);
        apibh.setExchratehc(poporh1.getRate());
        apibh.setOrigratehc(BigDecimal.valueOf(1));
        apibh.setTermcode("COD");
        apibh.setSwtermovrd((short) 1);
        apibh.setDatedue(currentDate());
        apibh.setDatedisc(BigDecimal.valueOf(0));
        apibh.setPctdisc(BigDecimal.valueOf(0));
        apibh.setAmtdiscavl(BigDecimal.valueOf(0));
        apibh.setLastline(BigDecimal.valueOf(1));
        apibh.setSwtaxbl((short) 0);
        apibh.setSwcalctx((short) 0);
        apibh.setCodetaxgrp(apven.get().getCodetaxgrp());
        apibh.setCodetax1("VAT" + poporh1.getCurrency());
        apibh.setCodetax2("");
        apibh.setCodetax3("");
        apibh.setCodetax4("");
        apibh.setCodetax5("");
        apibh.setTaxclass1((short) 1);
        apibh.setTaxclass2((short) 0);
        apibh.setTaxclass3((short) 0);
        apibh.setTaxclass4((short) 0);
        apibh.setTaxclass5((short) 0);
        apibh.setBasetax1(sale.path("totTaxblAmt").decimalValue());
        apibh.setBasetax2(BigDecimal.valueOf(0));
        apibh.setBasetax3(BigDecimal.valueOf(0));
        apibh.setBasetax4(BigDecimal.valueOf(0));
        apibh.setBasetax5(BigDecimal.valueOf(0));
        apibh.setAmttax1(sale.path("totVatAmt").decimalValue());
        apibh.setAmttax2(BigDecimal.valueOf(0));
        apibh.setAmttax3(BigDecimal.valueOf(0));
        apibh.setAmttax4(BigDecimal.valueOf(0));
        apibh.setAmttax5(BigDecimal.valueOf(0));
        apibh.setAmt1099(BigDecimal.valueOf(0));
        apibh.setAmtdistset(BigDecimal.valueOf(0));
        apibh.setAmttaxdist(BigDecimal.valueOf(0));
        apibh.setAmtinvctot(sale.path("totTaxblAmt").decimalValue());
        apibh.setAmtalloctx(BigDecimal.valueOf(0));
        apibh.setCntpaymsch(BigDecimal.valueOf(1));
        apibh.setAmttotdist(sale.path("totTaxblAmt").decimalValue());
        apibh.setAmtgrosdst(sale.path("totAmt").decimalValue());
        apibh.setIdppd("");
        apibh.setTextrmit("");
        apibh.setTextste1("");
        apibh.setTextste2("");
        apibh.setTextste3("");
        apibh.setTextste4("");
        apibh.setNamecity("");
        apibh.setCodestte("");
        apibh.setCodepstl("");
        apibh.setCodectry("");
        apibh.setNamectac("");
        apibh.setTextphon("");
        apibh.setTextfax("");
        apibh.setDaterate(currentDate());
        apibh.setAmtrectax(sale.path("totVatAmt").decimalValue());
        apibh.setCodepayppd(BigDecimal.valueOf(0));
        apibh.setCodevndgrp(poporh1.getCurrency());
        apibh.setTermsdesc("Cash on Delivery");
        apibh.setIddistset("");
        apibh.setId1099clas("");
        apibh.setAmttaxtot(sale.path("totVatAmt").decimalValue());
        apibh.setAmtgrostot(sale.path("totAmt").decimalValue());
        apibh.setSwtaxincl1((short) 0);
        apibh.setSwtaxincl2((short) 0);
        apibh.setSwtaxincl3((short) 0);
        apibh.setSwtaxincl4((short) 0);
        apibh.setSwtaxincl5((short) 0);
        apibh.setAmtexptax(BigDecimal.valueOf(0));
        apibh.setAmtaxtobe(BigDecimal.valueOf(0));
        apibh.setTaxoutbal(BigDecimal.valueOf(0));
        apibh.setCodeoper((short) 1);
        apibh.setAcctrec1("21040 ");
        apibh.setAcctrec2("");
        apibh.setAcctrec3("");
        apibh.setAcctrec4("");
        apibh.setAcctrec5("");
        apibh.setAcctexp1("");
        apibh.setAcctexp2("");
        apibh.setAcctexp3("");
        apibh.setAcctexp4("");
        apibh.setAcctexp5("");
        apibh.setDrillapp("PO");
        apibh.setDrilltype((short) 5);
        apibh.setDrilldwnlk(poinvh1Instance.getId().add(new BigDecimal(2)));
        apibh.setSwjob((short) 0);
        apibh.setAmtrecdist(sale.path("totVatAmt").decimalValue());
        apibh.setAmtexpdist(BigDecimal.valueOf(0));
        apibh.setErrbatch(0);
        apibh.setErrentry(0);
        apibh.setEmail("");
        apibh.setCtacphone("");
        apibh.setCtacfax("");
        apibh.setCtacemail("");
        apibh.setAmtppd(BigDecimal.valueOf(0));
        apibh.setIdstdinvc("");
        apibh.setDateprcs(BigDecimal.valueOf(0));
        apibh.setAmtdsbwtax(sale.path("totAmt").decimalValue());
        apibh.setAmtdsbntax(sale.path("totTaxblAmt").decimalValue());
        apibh.setAmtdscbase(sale.path("totAmt").decimalValue());
        apibh.setSwrtginvc((short) 0);
        apibh.setRtgapplyto("");
        apibh.setSwrtg((short) 0);
        apibh.setRtgamt(BigDecimal.valueOf(0));
        apibh.setRtgpercent(BigDecimal.valueOf(0));
        apibh.setRtgdays((short) 0);
        apibh.setRtgdatedue(BigDecimal.valueOf(0));
        apibh.setRtgterms("");
        apibh.setSwrtgddtov((short) 0);
        apibh.setSwrtgamtov((short) 0);
        apibh.setSwrtgrate((short) 0);
        apibh.setSwtxbsectl((short) 1);
        apibh.setValues(0);
        apibh.setOrigcomp("");
        apibh.setDetailcnt(sale.path("totItemCnt").intValue());
        apibh.setSrceappl("PO");
        apibh.setSwhold((short) 0);
        apibh.setApversion("71A");
        apibh.setTaxversion(1);
        apibh.setSwtxrtgrpt((short) 0);
        apibh.setCodecurnrc(poporh1.getCurrency());
        apibh.setSwtxctlrc((short) 0);
        apibh.setRaterc(BigDecimal.valueOf(1));
        apibh.setRatetyperc("SP");
        apibh.setRatedaterc(currentDate());
        apibh.setRateoprc((short) 1);
        apibh.setSwraterc((short) 0);
        apibh.setTxamt1rc(sale.path("totVatAmt").decimalValue());
        apibh.setTxamt2rc(BigDecimal.valueOf(0));
        apibh.setTxamt3rc(BigDecimal.valueOf(0));
        apibh.setTxamt4rc(BigDecimal.valueOf(0));
        apibh.setTxamt5rc(BigDecimal.valueOf(0));
        apibh.setTxtotrc(sale.path("totVatAmt").decimalValue());
        apibh.setTxallrc(BigDecimal.valueOf(0));
        apibh.setTxexprc(BigDecimal.valueOf(0));
        apibh.setTxrecrc(BigDecimal.valueOf(0));
        apibh.setTxbsert1tc(BigDecimal.valueOf(0));
        apibh.setTxbsert2tc(BigDecimal.valueOf(0));
        apibh.setTxbsert3tc(BigDecimal.valueOf(0));
        apibh.setTxbsert4tc(BigDecimal.valueOf(0));
        apibh.setTxbsert5tc(BigDecimal.valueOf(0));
        apibh.setTxamtrt1tc(BigDecimal.valueOf(0));
        apibh.setTxamtrt2tc(BigDecimal.valueOf(0));
        apibh.setTxamtrt3tc(BigDecimal.valueOf(0));
        apibh.setTxamtrt4tc(BigDecimal.valueOf(0));
        apibh.setTxamtrt5tc(BigDecimal.valueOf(0));
        apibh.setTxbse1hc(sale.path("totTaxblAmt").decimalValue());
        apibh.setTxbse2hc(BigDecimal.valueOf(0));
        apibh.setTxbse3hc(BigDecimal.valueOf(0));
        apibh.setTxbse4hc(BigDecimal.valueOf(0));
        apibh.setTxbse5hc(BigDecimal.valueOf(0));
        apibh.setTxamt1hc(sale.path("totVatAmt").decimalValue());
        apibh.setTxamt2hc(BigDecimal.valueOf(0));
        apibh.setTxamt3hc(BigDecimal.valueOf(0));
        apibh.setTxamt4hc(BigDecimal.valueOf(0));
        apibh.setTxamt5hc(BigDecimal.valueOf(0));
        apibh.setAmtgroshc(sale.path("totAmt").decimalValue());
        apibh.setRtgamthc(BigDecimal.valueOf(0));
        apibh.setAmtdischc(BigDecimal.valueOf(0));
        apibh.setAmt1099hc(BigDecimal.valueOf(0));
        apibh.setAmtppdhc(BigDecimal.valueOf(0));
        apibh.setAmtduetc(sale.path("totAmt").decimalValue());
        apibh.setAmtduehc(sale.path("totAmt").decimalValue());
        apibh.setTextven(apven.get().getVendname());
        apibh.setEnteredby("ADMIN");
        apibh.setDatebus(currentDate());
        apibh.setIdn("");
        apibh.setAmtwht1tc(BigDecimal.valueOf(0));
        apibh.setAmtwht2tc(BigDecimal.valueOf(0));
        apibh.setAmtwht3tc(BigDecimal.valueOf(0));
        apibh.setAmtwht4tc(BigDecimal.valueOf(0));
        apibh.setAmtwht5tc(BigDecimal.valueOf(0));
        apibh.setAmtcxbs1tc(BigDecimal.valueOf(0));
        apibh.setAmtcxbs2tc(BigDecimal.valueOf(0));
        apibh.setAmtcxbs3tc(BigDecimal.valueOf(0));
        apibh.setAmtcxbs4tc(BigDecimal.valueOf(0));
        apibh.setAmtcxbs5tc(BigDecimal.valueOf(0));

        apibhRepo.save(apibh);
        Apibs apibs = Apibs.builder()
                .id(ApibsId.builder()
                        .cntbtch(new BigDecimal(getBatchNumber()))
                        .cntpaym(BigDecimal.valueOf(1))
                        .cntitem(BigDecimal.valueOf(1))
                        .build())
                .audtdate(currentDate())
                .audttime(currentTime())
                .audtuser("ADMIN")
                .audtorg(company)
                .datedue(currentDate())
                .amtdue(sale.path("totAmt").decimalValue())
                .datedisc(BigDecimal.valueOf(0))
                .amtdisc(BigDecimal.valueOf(0))
                .amtduehc(sale.path("totAmt").decimalValue())
                .amtdischc(BigDecimal.valueOf(0))
                .build();
        apibsRepo.save(apibs);
        Apibc apibc = Apibc.builder()
                .id(new BigDecimal(getBatchNumber()))
                .audtdate(currentDate())
                .audttime(currentTime())
                .audtuser("ADMIN")
                .audtorg(company)
                .datebtch(currentDate())
                .btchdesc("P/O Generated Batch                                         ")
                .cntinvcent(BigDecimal.valueOf(1))
                .amtentr(sale.path("totAmt").decimalValue())
                .btchtype((short) 5)
                .btchstts((short) 1)
                .invctype((short) 1)
                .cntlstitem(BigDecimal.valueOf(1))
                .postseqnbr(BigDecimal.valueOf(0))
                .nbrerrors(BigDecimal.valueOf(0))
                .dtelstedit(currentDate())
                .swprinted((short) 0)
                .srceappl("PO")
                .swict((short) 0)
                .build();
        apibcRepo.save(apibc);

        //Details
        Apibd apibd = new Apibd();

        apibd.setId(new ApibdId());
        apibd.getId().setCntbtch(BigDecimal.valueOf(getBatchNumber()));
        apibd.getId().setCntitem(BigDecimal.valueOf(1));
        apibd.getId().setCntline(BigDecimal.valueOf(20));

        apibd.setAudtdate(currentDate());
        apibd.setAudttime(currentTime());
        apibd.setAudtorg("ADMIN");
        apibd.setAudtuser(company);
        apibd.setIddist("");
        apibd.setTextdesc("IC/AP STOCK CLEARING ACCOUNT ");
        apibd.setSwmanldist((short) 0);
        apibd.setAmttottax(sale.path("totVatAmt").decimalValue());
        apibd.setSwmanltx((short) 0);
        apibd.setBasetax1(sale.path("totTaxblAmt").decimalValue());
        apibd.setBasetax2(BigDecimal.valueOf(0));
        apibd.setBasetax3(BigDecimal.valueOf(0));
        apibd.setBasetax4(BigDecimal.valueOf(0));
        apibd.setBasetax5(BigDecimal.valueOf(0));
        apibd.setTaxclass1((short) 1);
        apibd.setTaxclass2((short) 0);
        apibd.setTaxclass3((short) 0);
        apibd.setTaxclass4((short) 0);
        apibd.setTaxclass5((short) 0);
        apibd.setSwtaxincl1((short) 0);
        apibd.setSwtaxincl2((short) 0);
        apibd.setSwtaxincl3((short) 0);
        apibd.setSwtaxincl4((short) 0);
        apibd.setSwtaxincl5((short) 0);
        apibd.setRatetax1(BigDecimal.valueOf(16));
        apibd.setRatetax2(BigDecimal.valueOf(0));
        apibd.setRatetax3(BigDecimal.valueOf(0));
        apibd.setRatetax4(BigDecimal.valueOf(0));
        apibd.setRatetax5(BigDecimal.valueOf(0));
        apibd.setAmttax1(sale.path("totVatAmt").decimalValue());
        apibd.setAmttax2(BigDecimal.valueOf(0));
        apibd.setAmttax3(BigDecimal.valueOf(0));
        apibd.setAmttax4(BigDecimal.valueOf(0));
        apibd.setAmttax5(BigDecimal.valueOf(0));
        apibd.setIdglacct("40060 ");
        apibd.setIdaccttax("40060 ");
        apibd.setId1099clas("");
        apibd.setAmtdist(sale.path("totTaxblAmt").decimalValue());
        apibd.setAmtdistnet(sale.path("totTaxblAmt").decimalValue());
        apibd.setAmtincltax(BigDecimal.valueOf(0));
        apibd.setAmtgldist(sale.path("totTaxblAmt").decimalValue());
        apibd.setAmttaxrec1(sale.path("totVatAmt").decimalValue());
        apibd.setAmttaxrec2(BigDecimal.valueOf(0));
        apibd.setAmttaxrec3(BigDecimal.valueOf(0));
        apibd.setAmttaxrec4(BigDecimal.valueOf(0));
        apibd.setAmttaxrec5(BigDecimal.valueOf(0));
        apibd.setAmttaxexp1(BigDecimal.valueOf(0));
        apibd.setAmttaxexp2(BigDecimal.valueOf(0));
        apibd.setAmttaxexp3(BigDecimal.valueOf(0));
        apibd.setAmttaxexp4(BigDecimal.valueOf(0));
        apibd.setAmttaxexp5(BigDecimal.valueOf(0));
        apibd.setAmttaxtobe(BigDecimal.valueOf(0));
        apibd.setContract("");
        apibd.setProject("");
        apibd.setCategory("");
        apibd.setResource("");
        apibd.setTransnbr(0);
        apibd.setCostclass((short) 0);
        apibd.setBilltype((short) 0);
        apibd.setIditem("");
        apibd.setUnitmeas("");
        apibd.setQtyinvc(BigDecimal.valueOf(0));
        apibd.setAmtcost(BigDecimal.valueOf(0));
        apibd.setBilldate(currentTime());
        apibd.setBillrate(BigDecimal.valueOf(0));
        apibd.setBillcurn("");
        apibd.setSwibt((short) 0);
        apibd.setSwdiscabl((short) 1);
        apibd.setOcntline(BigDecimal.valueOf(0));
        apibd.setRtgamt(BigDecimal.valueOf(0));
        apibd.setRtgpercent(BigDecimal.valueOf(0));
        apibd.setRtgdays((short) 0);
        apibd.setRtgdatedue(BigDecimal.valueOf(0));
        apibd.setSwrtgddtov((short) 0);
        apibd.setSwrtgamtov((short) 0);
        apibd.setValues(0);
        apibd.setDescomp("");
        apibd.setRoute((short) 0);
        apibd.setRtgdisttc(BigDecimal.valueOf(0));
        apibd.setRtginvdist(BigDecimal.valueOf(0));
        apibd.setTxamt1rc(sale.path("totVatAmt").decimalValue());
        apibd.setTxamt2rc(BigDecimal.valueOf(0));
        apibd.setTxamt3rc(BigDecimal.valueOf(0));
        apibd.setTxamt4rc(BigDecimal.valueOf(0));
        apibd.setTxamt5rc(BigDecimal.valueOf(0));
        apibd.setTxtotrc(sale.path("totVatAmt").decimalValue());
        apibd.setTxallrc(BigDecimal.valueOf(0));
        apibd.setTxexp1rc(BigDecimal.valueOf(0));
        apibd.setTxexp2rc(BigDecimal.valueOf(0));
        apibd.setTxexp3rc(BigDecimal.valueOf(0));
        apibd.setTxexp4rc(BigDecimal.valueOf(0));
        apibd.setTxexp5rc(BigDecimal.valueOf(0));
        apibd.setTxrec1rc(sale.path("totVatAmt").decimalValue());
        apibd.setTxrec2rc(BigDecimal.valueOf(0));
        apibd.setTxrec3rc(BigDecimal.valueOf(0));
        apibd.setTxrec4rc(BigDecimal.valueOf(0));
        apibd.setTxrec5rc(BigDecimal.valueOf(0));
        apibd.setTxbsert1tc(sale.path("totTaxblAmt").decimalValue());
        apibd.setTxbsert2tc(BigDecimal.valueOf(0));
        apibd.setTxbsert3tc(BigDecimal.valueOf(0));
        apibd.setTxbsert4tc(BigDecimal.valueOf(0));
        apibd.setTxbsert5tc(BigDecimal.valueOf(0));
        apibd.setTxamtrt1tc(BigDecimal.valueOf(0));
        apibd.setTxamtrt2tc(BigDecimal.valueOf(0));
        apibd.setTxamtrt3tc(BigDecimal.valueOf(0));
        apibd.setTxamtrt4tc(BigDecimal.valueOf(0));
        apibd.setTxamtrt5tc(BigDecimal.valueOf(0));
        apibd.setTxbse1hc(sale.path("totTaxblAmt").decimalValue());
        apibd.setTxbse2hc(BigDecimal.valueOf(0));
        apibd.setTxbse3hc(BigDecimal.valueOf(0));
        apibd.setTxbse4hc(BigDecimal.valueOf(0));
        apibd.setTxbse5hc(BigDecimal.valueOf(0));
        apibd.setTxamt1hc(sale.path("totVatAmt").decimalValue());
        apibd.setTxamt2hc(BigDecimal.valueOf(0));
        apibd.setTxamt3hc(BigDecimal.valueOf(0));
        apibd.setTxamt4hc(BigDecimal.valueOf(0));
        apibd.setTxamt5hc(BigDecimal.valueOf(0));
        apibd.setTxamtrt1hc(BigDecimal.valueOf(0));
        apibd.setTxamtrt2hc(BigDecimal.valueOf(0));
        apibd.setTxamtrt3hc(BigDecimal.valueOf(0));
        apibd.setTxamtrt4hc(BigDecimal.valueOf(0));
        apibd.setTxamtrt5hc(BigDecimal.valueOf(0));
        apibd.setTxrec1hc(sale.path("totVatAmt").decimalValue());
        apibd.setTxrec2hc(BigDecimal.valueOf(0));
        apibd.setTxrec3hc(BigDecimal.valueOf(0));
        apibd.setTxrec4hc(BigDecimal.valueOf(0));
        apibd.setTxrec5hc(BigDecimal.valueOf(0));
        apibd.setTxexp1hc(BigDecimal.valueOf(0));
        apibd.setTxexp2hc(BigDecimal.valueOf(0));
        apibd.setTxexp3hc(BigDecimal.valueOf(0));
        apibd.setTxexp4hc(BigDecimal.valueOf(0));
        apibd.setTxexp5hc(BigDecimal.valueOf(0));
        apibd.setTxallhc(BigDecimal.valueOf(0));
        apibd.setTxall1hc(BigDecimal.valueOf(0));
        apibd.setTxall2hc(BigDecimal.valueOf(0));
        apibd.setTxall3hc(BigDecimal.valueOf(0));
        apibd.setTxall4hc(BigDecimal.valueOf(0));
        apibd.setTxall5hc(BigDecimal.valueOf(0));
        apibd.setTxall1tc(BigDecimal.valueOf(0));
        apibd.setTxall2tc(BigDecimal.valueOf(0));
        apibd.setTxall3tc(BigDecimal.valueOf(0));
        apibd.setTxall4tc(BigDecimal.valueOf(0));
        apibd.setTxall5tc(BigDecimal.valueOf(0));
        apibd.setAmtcosthc(BigDecimal.valueOf(0));
        apibd.setAmtdisthc(sale.path("totTaxblAmt").decimalValue());
        apibd.setDistnethc(sale.path("totTaxblAmt").decimalValue());
        apibd.setRtgamthc(BigDecimal.valueOf(0));
        apibd.setTxallrthc(BigDecimal.valueOf(0));
        apibd.setTxallrttc(BigDecimal.valueOf(0));
        apibd.setTxexprthc(BigDecimal.valueOf(0));
        apibd.setTxexprttc(BigDecimal.valueOf(0));
        apibd.setSwfas((short) 0);
        apibd.setAmtwht1tc(BigDecimal.valueOf(0));
        apibd.setAmtwht2tc(BigDecimal.valueOf(0));
        apibd.setAmtwht3tc(BigDecimal.valueOf(0));
        apibd.setAmtwht4tc(BigDecimal.valueOf(0));
        apibd.setAmtwht5tc(BigDecimal.valueOf(0));
        apibd.setAmtcxtx1tc(BigDecimal.valueOf(0));
        apibd.setAmtcxtx2tc(BigDecimal.valueOf(0));
        apibd.setAmtcxtx3tc(BigDecimal.valueOf(0));
        apibd.setAmtcxtx4tc(BigDecimal.valueOf(0));
        apibd.setAmtcxtx5tc(BigDecimal.valueOf(0));
        apibd.setSwcaxable1((short) 0);
        apibd.setSwcaxable2((short) 0);
        apibd.setSwcaxable3((short) 0);
        apibd.setSwcaxable4((short) 0);
        apibd.setSwcaxable5((short) 0);
        apibdRepo.save(apibd);


        // Purchase Order Invoices
        //Headers
        Poinvh1 poinvh1 = new Poinvh1();
        poinvh1.setId(poinvh1Instance.getId().add(new BigDecimal(2)));
        poinvh1.setAudtdate(currentDate());
        poinvh1.setAudttime(currentTime());
        poinvh1.setAudtuser("ADMIN");
        poinvh1.setAudtorg(company);
        poinvh1.setNextlseq(poinvh1Instance.getNextlseq().add(BigDecimal.valueOf(1)));
        poinvh1.setLines(sale.path("totItemCnt").asInt());
        poinvh1.setLinescmpl(sale.path("totItemCnt").asInt());
        poinvh1.setCosts(0);
        poinvh1.setCostscmpl(0);
        poinvh1.setPayments(0);
        poinvh1.setTaxlines(1);
        poinvh1.setTaxautocal((short) 0);
        poinvh1.setIscomplete((short) 1);
        poinvh1.setDtcomplete(currentDate());
        poinvh1.setPostdate(currentDate());
        poinvh1.setPorhseq(poporh1.getId());
        poinvh1.setPonumber(poporh1.getPonumber());
        poinvh1.setDate(currentDate());
        poinvh1.setFiscyear(fscyear());
        poinvh1.setFiscperiod(fscyper());
        poinvh1.setInvnumber(sale.path("spplrInvcNo").asText());
        poinvh1.setVdcode(poporh1.getVdcode());
        poinvh1.setVdexists((short) 1);
        poinvh1.setVdname(poporh1.getVdname());
        poinvh1.setVdaddress1(poporh1.getVdaddress1());
        poinvh1.setVdaddress2(poporh1.getVdaddress2());
        poinvh1.setVdaddress3(poporh1.getVdaddress3());
        poinvh1.setVdaddress4(poporh1.getVdaddress4());
        poinvh1.setVdcity(poporh1.getVdcity());
        poinvh1.setVdstate(poporh1.getVdstate());
        poinvh1.setVdzip(poporh1.getVdzip());
        poinvh1.setVdcountry(poporh1.getVdcountry());
        poinvh1.setVdphone("");
        poinvh1.setVdfax("");
        poinvh1.setVdcontact("");
        poinvh1.setTermscode("30DAYS");
        poinvh1.setFromdoc((short) 3);
        poinvh1.setForprimary((short) 1);
        poinvh1.setRcphseq(maxID.getId().add(BigDecimal.valueOf(2)));
        poinvh1.setRcpnumber(nextReceiptNo());
        poinvh1.setRcpdate(currentDate());
        poinvh1.setRethseq(new BigDecimal(0));
        poinvh1.setRetnumber("");
        poinvh1.setRetdate(new BigDecimal(0));
        poinvh1.setDescriptio(sale.path("remarks").asText());
        poinvh1.setReference(sale.path("").asText());
        poinvh1.setComment("");
        poinvh1.setCurrency(poporh1.getCurrency());
        poinvh1.setRate(new BigDecimal(1.0));
        poinvh1.setSpread(new BigDecimal(0.0));
        poinvh1.setRatetype("SP");
        poinvh1.setRatematch((short) 0);
        poinvh1.setRatedate(currentDate());
        poinvh1.setRateoper((short) 1);
        poinvh1.setRateover((short) 0);
        poinvh1.setScurndecml((short) 2);
        poinvh1.setDtpymtasof(currentDate());
        poinvh1.setTermdueamt(sale.path("totAmt").decimalValue());
        poinvh1.setExtweight(new BigDecimal(0.0));
        poinvh1.setExtended(sale.path("totTaxblAmt").decimalValue());
        poinvh1.setDoctotal(sale.path("totAmt").decimalValue());
        poinvh1.setAmount(new BigDecimal(0.0));
        poinvh1.setRqreceived(sale.path("totItemCnt").decimalValue());
        poinvh1.setTaxgroup(apven.get().getCodetaxgrp());
        poinvh1.setTaxauth1("VATZMW");
        poinvh1.setTaxauth2("");
        poinvh1.setTaxauth3("");
        poinvh1.setTaxauth4("");
        poinvh1.setTaxauth5("");
        poinvh1.setTaxclass1((short) 1);
        poinvh1.setTaxclass2((short) 1);
        poinvh1.setTaxclass3((short) 1);
        poinvh1.setTaxclass4((short) 1);
        poinvh1.setTaxclass5((short) 1);
        poinvh1.setTaxbase1(sale.path("totTaxblAmt").decimalValue());
        poinvh1.setTaxbase2(new BigDecimal(0.0));
        poinvh1.setTaxbase3(new BigDecimal(0.0));
        poinvh1.setTaxbase4(new BigDecimal(0.0));
        poinvh1.setTaxbase5(new BigDecimal(0.0));
        poinvh1.setTxinclude1(new BigDecimal(0.0));
        poinvh1.setTxinclude2(new BigDecimal(0.0));
        poinvh1.setTxinclude3(new BigDecimal(0.0));
        poinvh1.setTxinclude4(new BigDecimal(0.0));
        poinvh1.setTxinclude5(new BigDecimal(0.0));
        poinvh1.setTxexclude1(sale.path("totTaxAmt").decimalValue());
        poinvh1.setTxexclude2(new BigDecimal(0.0));
        poinvh1.setTxexclude3(new BigDecimal(0.0));
        poinvh1.setTxexclude4(new BigDecimal(0.0));
        poinvh1.setTxexclude5(new BigDecimal(0.0));
        poinvh1.setTaxamount1(sale.path("totTaxAmt").decimalValue());
        poinvh1.setTaxamount2(new BigDecimal(0.0));
        poinvh1.setTaxamount3(new BigDecimal(0.0));
        poinvh1.setTaxamount4(new BigDecimal(0.0));
        poinvh1.setTaxamount5(new BigDecimal(0.0));
        poinvh1.setTxrecvamt1(sale.path("totTaxAmt").decimalValue());
        poinvh1.setTxrecvamt2(new BigDecimal(0.0));
        poinvh1.setTxrecvamt3(new BigDecimal(0.0));
        poinvh1.setTxrecvamt4(new BigDecimal(0.0));
        poinvh1.setTxrecvamt5(new BigDecimal(0.0));
        poinvh1.setTxexpsamt1(new BigDecimal(0.0));
        poinvh1.setTxexpsamt2(new BigDecimal(0.0));
        poinvh1.setTxexpsamt3(new BigDecimal(0.0));
        poinvh1.setTxexpsamt4(new BigDecimal(0.0));
        poinvh1.setTxexpsamt5(new BigDecimal(0.0));
        poinvh1.setTxalloamt1(new BigDecimal(0.0));
        poinvh1.setTxalloamt2(new BigDecimal(0.0));
        poinvh1.setTxalloamt3(new BigDecimal(0.0));
        poinvh1.setTxalloamt4(new BigDecimal(0.0));
        poinvh1.setTxalloamt5(new BigDecimal(0.0));
        poinvh1.setTxbaseallo(sale.path("totTaxblAmt").decimalValue());
        poinvh1.setTxincluded(new BigDecimal(0.0));
        poinvh1.setTxexcluded(sale.path("totTaxAmt").decimalValue());
        poinvh1.setTaxamount(sale.path("totTaxAmt").decimalValue());
        poinvh1.setTxrecvamt(sale.path("totTaxAmt").decimalValue());
        poinvh1.setTxexpsamt(new BigDecimal(0.0));
        poinvh1.setTxalloamt(new BigDecimal(0.0));
        poinvh1.setF1099class("");
        poinvh1.setF1099amt(new BigDecimal(0.0));
        poinvh1.setMprorated(new BigDecimal(0.0));
        poinvh1.setMtoprorate(new BigDecimal(0.0));
        poinvh1.setMexpensed(new BigDecimal(0.0));
        poinvh1.setScamount(sale.path("totAmt").decimalValue());
        poinvh1.setFcamount(sale.path("totAmt").decimalValue());
        poinvh1.setMultircp((short) 0);
        poinvh1.setRcps(0);
        poinvh1.setVdemail(apven.get().getEmail1());
        poinvh1.setVdphonec("");
        poinvh1.setVdfaxc("");
        poinvh1.setVdemailc("");
        poinvh1.setDiscpct(new BigDecimal(0.0));
        poinvh1.setDiscount(new BigDecimal(0.0));
        poinvh1.setValues(0);
        poinvh1.setOnhold((short) 0);
        poinvh1.setTermdbwt(sale.path("totAmt").decimalValue());
        poinvh1.setTermdbnt(sale.path("totTaxblAmt").decimalValue());
        poinvh1.setVerprorate((short) 2);
        poinvh1.setHasrtg((short) 0);
        poinvh1.setRtgrate((short) 0);
        poinvh1.setRtgbase((short) 0);
        poinvh1.setRtgterms("");
        poinvh1.setRtgamount(new BigDecimal(0.0));
        poinvh1.setJoblines(0);
        poinvh1.setJobcosts(0);
        poinvh1.setTrcurrency("ZMW");
        poinvh1.setRaterc(new BigDecimal(1.0));
        poinvh1.setSpreadrc(new BigDecimal(0.0));
        poinvh1.setRatetyperc("SP");
        poinvh1.setRatemtchrc((short) 1);
        poinvh1.setRatedaterc(currentDate());
        poinvh1.setRateoperrc((short)1);
        poinvh1.setRatercover((short)0);
        poinvh1.setRcurndecml((short)2);
        poinvh1.setTaramount1(sale.path("totTaxAmt").decimalValue());
        poinvh1.setTaramount2(new BigDecimal("0.000"));
        poinvh1.setTaramount3(new BigDecimal("0.000"));
        poinvh1.setTaramount4(new BigDecimal("0.000"));
        poinvh1.setTaramount5(new BigDecimal("0.000"));
        poinvh1.setTrinclude1(new BigDecimal("0.000"));
        poinvh1.setTrinclude2(new BigDecimal("0.000"));
        poinvh1.setTrinclude3(new BigDecimal("0.000"));
        poinvh1.setTrinclude4(new BigDecimal("0.000"));
        poinvh1.setTrinclude5(new BigDecimal("0.000"));
        poinvh1.setTrexclude1(sale.path("totTaxAmt").decimalValue());
        poinvh1.setTrexclude2(new BigDecimal("0.000"));
        poinvh1.setTrexclude3(new BigDecimal("0.000"));
        poinvh1.setTrexclude4(new BigDecimal("0.000"));
        poinvh1.setTrexclude5(new BigDecimal("0.000"));
        poinvh1.setTrrecvamt1(sale.path("totTaxAmt").decimalValue());
        poinvh1.setTrrecvamt2(new BigDecimal("0.000"));
        poinvh1.setTrrecvamt3(new BigDecimal("0.000"));
        poinvh1.setTrrecvamt4(new BigDecimal("0.000"));
        poinvh1.setTrrecvamt5(new BigDecimal("0.000"));
        poinvh1.setTrexpsamt1(new BigDecimal("0.000"));
        poinvh1.setTrexpsamt2(new BigDecimal("0.000"));
        poinvh1.setTrexpsamt3(new BigDecimal("0.000"));
        poinvh1.setTrexpsamt4(new BigDecimal("0.000"));
        poinvh1.setTrexpsamt5(new BigDecimal("0.000"));
        poinvh1.setTralloamt1(new BigDecimal("0.000"));
        poinvh1.setTralloamt2(new BigDecimal("0.000"));
        poinvh1.setTralloamt3(new BigDecimal("0.000"));
        poinvh1.setTralloamt4(new BigDecimal("0.000"));
        poinvh1.setTralloamt5(new BigDecimal("0.000"));
        poinvh1.setRtgtaxrep((short) 0); // Example short value
        poinvh1.setTaxversion(3); // Example integer value
        poinvh1.setRaxbase1(new BigDecimal("0.000"));
        poinvh1.setRaxbase2(new BigDecimal("0.000"));
        poinvh1.setRaxbase3(new BigDecimal("0.000"));
        poinvh1.setRaxbase4(new BigDecimal("0.000"));
        poinvh1.setRaxbase5(new BigDecimal("0.000"));
        poinvh1.setRaxamount1(new BigDecimal("0.000"));
        poinvh1.setRaxamount2(new BigDecimal("0.000"));
        poinvh1.setRaxamount3(new BigDecimal("0.000"));
        poinvh1.setRaxamount4(new BigDecimal("0.000"));
        poinvh1.setRaxamount5(new BigDecimal("0.000"));
        poinvh1.setRxrecvamt1(new BigDecimal("0.000"));
        poinvh1.setRxrecvamt2(new BigDecimal("0.000"));
        poinvh1.setRxrecvamt3(new BigDecimal("0.000"));
        poinvh1.setRxrecvamt4(new BigDecimal("0.000"));
        poinvh1.setRxrecvamt5(new BigDecimal("0.000"));
        poinvh1.setRxexpsamt1(new BigDecimal("0.000"));
        poinvh1.setRxexpsamt2(new BigDecimal("0.000"));
        poinvh1.setRxexpsamt3(new BigDecimal("0.000"));
        poinvh1.setRxexpsamt4(new BigDecimal("0.000"));
        poinvh1.setRxexpsamt5(new BigDecimal("0.000"));
        poinvh1.setRxalloamt1(new BigDecimal("0.000"));
        poinvh1.setRxalloamt2(new BigDecimal("0.000"));
        poinvh1.setRxalloamt3(new BigDecimal("0.000"));
        poinvh1.setRxalloamt4(new BigDecimal("0.000"));
        poinvh1.setRxalloamt5(new BigDecimal("0.000"));
        poinvh1Repository.save(poinvh1);

        //POINVH2
        Poinvh2 poinvh2 = new Poinvh2();
        poinvh2.setId(poinvh1Instance.getId().add(new BigDecimal(2)));
        poinvh2.setAudtdate(currentDate());
        poinvh2.setAudttime(currentTime());
        poinvh2.setAudtuser("ADMIN");
        poinvh2.setAudtorg(company);
        poinvh2.setBtcode("");
        poinvh2.setBtdesc("");
        poinvh2.setBtaddress1("");
        poinvh2.setBtaddress2("");
        poinvh2.setBtaddress3("");
        poinvh2.setBtaddress4("");
        poinvh2.setBtcity("");
        poinvh2.setBtstate("");
        poinvh2.setBtzip("");
        poinvh2.setBtcountry("");
        poinvh2.setBtphone("");
        poinvh2.setBtfax("");
        poinvh2.setBtcontact("");
        poinvh2.setStcode("");
        poinvh2.setStdesc("");
        poinvh2.setStaddress1("");
        poinvh2.setStaddress2("");
        poinvh2.setStaddress3("");
        poinvh2.setStaddress4("");
        poinvh2.setStcity("");
        poinvh2.setStstate("");
        poinvh2.setStzip("");
        poinvh2.setStcountry("");
        poinvh2.setStphone("");
        poinvh2.setStfax("");
        poinvh2.setStcontact("");
        poinvh2.setRtcode("");
        poinvh2.setRtdesc("");
        poinvh2.setRtaddress1("");
        poinvh2.setRtaddress2("");
        poinvh2.setRtaddress3("");
        poinvh2.setRtaddress4("");
        poinvh2.setRtcity("");
        poinvh2.setRtstate("");
        poinvh2.setRtzip("");
        poinvh2.setRtcountry("");
        poinvh2.setRtphone("");
        poinvh2.setRtfax("");
        poinvh2.setRtcontact("");
        poinvh2.setPdrate(BigDecimal.ONE);
        poinvh2.setPdratetype("");
        poinvh2.setPdratedate(currentDate());
        poinvh2.setPdrateoper((short) 1);
        poinvh2.setPdrateover((short) 0);
        poinvh2.setBtemail("");
        poinvh2.setBtphonec("");
        poinvh2.setBtfaxc("");
        poinvh2.setBtemailc("");
        poinvh2.setStemail("");
        poinvh2.setStphonec("");
        poinvh2.setStfaxc("");
        poinvh2.setStemailc("");
        poinvh2.setRtemail("");
        poinvh2.setRtphonec("");
        poinvh2.setRtfaxc("");
        poinvh2.setRtemailc("");
        poinvh2.setPdraterc(BigDecimal.ONE);
        poinvh2.setPdrattyprc("SP");
        poinvh2.setPdratedtrc(currentDate());
        poinvh2.setPdrateoprc((short) 1);
        poinvh2.setPdratercov((short) 0);
        poinvh2.setVdacctset("ZMW");
        poinvh2.setDatebus(currentDate());
        poinvh2.setEnteredby("ADMIN");
        poinvh2.setDetailnext((short) 2);
        poinvh2.setIdn("");
        poinvh2.setCaxbase1(new BigDecimal("0.0"));
        poinvh2.setCaxbase2(new BigDecimal("0.0"));
        poinvh2.setCaxbase3(new BigDecimal("0.0"));
        poinvh2.setCaxbase4(new BigDecimal("0.0"));
        poinvh2.setCaxbase5(new BigDecimal("0.0"));
        poinvh2.setCaxdtamt1(new BigDecimal("0.0"));
        poinvh2.setCaxdtamt2(new BigDecimal("0.0"));
        poinvh2.setCaxdtamt3(new BigDecimal("0.0"));
        poinvh2.setCaxdtamt4(new BigDecimal("0.0"));
        poinvh2.setCaxdtamt5(new BigDecimal("0.0"));
        poinvh2.setCaxapply1((short) 0);
        poinvh2.setCaxapply2((short) 0);
        poinvh2.setCaxapply3((short) 0);
        poinvh2.setCaxapply4((short) 0);
        poinvh2.setCaxapply5((short) 0);
        poinvh2.setCaxamount1(new BigDecimal("0.0"));
        poinvh2.setCaxamount2(new BigDecimal("0.0"));
        poinvh2.setCaxamount3(new BigDecimal("0.0"));
        poinvh2.setCaxamount4(new BigDecimal("0.0"));
        poinvh2.setCaxamount5(new BigDecimal("0.0"));
        poinvh2Repository.save(poinvh2);

        Poinvj poinvj = new Poinvj();
        // Set the properties
        poinvj.setId(poinvh1Instance.getId().add(new BigDecimal(2)));
        poinvj.setAudtdate(currentDate());
        poinvj.setAudttime(currentTime());
        poinvj.setAudtuser("ADMIN");
        poinvj.setAudtorg(company);
        poinvj.setProrateseq(new BigDecimal("1"));
        poinvj.setPostdate(currentDate());
        poinvj.setScamount(sale.path("totAmt").decimalValue());
        poinvj.setFcamount(sale.path("totAmt").decimalValue());
        poinvj.setScdoctotal(sale.path("totAmt").decimalValue());
        poinvj.setIscomplete((short) 1);
        poinvj.setDtcomplete(currentDate());
        poinvj.setScrtgamt(new BigDecimal("0.0"));
        poinvj.setCaxamount(new BigDecimal("0.0"));

        PoinvahId poinvahId = new PoinvahId();
        poinvahId.setDayendseq(dayEndNo());
        poinvahId.setInvahseq(BigDecimal.valueOf(1));
        Poinvah poinvah = new Poinvah();
        poinvah.setId(poinvahId);

        poinvah.setAudtdate(currentDate());
        poinvah.setAudttime(currentTime());
        poinvah.setAudtuser("ADMIN");
        poinvah.setAudtorg(company);
        poinvah.setIsprinted((short) 0);
        poinvah.setInvhseq(poinvh1Instance.getId().add(new BigDecimal(2)));
        poinvah.setPostdate(currentDate());
        poinvah.setDayenddate(currentDate());
        poinvah.setTransdate(currentDate());
        poinvah.setReference(poporh1.getReference());
        poinvah.setFiscyear(fscyear());
        poinvah.setFiscperiod(fscyper());
        poinvah.setDescriptio(poporh1.getDescriptio());
        poinvah.setTranstype((short) 1);
        poinvah.setVendor(poporh1.getVdcode());
        poinvah.setVendorname(poporh1.getVdname());
        poinvah.setTaxgroup(apven.get().getCodetaxgrp());
        poinvah.setTaxauth1("");
        poinvah.setTaxauth2("");
        poinvah.setTaxauth3("");
        poinvah.setTaxauth4("");
        poinvah.setTaxauth5("");
        poinvah.setTaxclass1(poporh1.getTaxclass1());
        poinvah.setTaxclass2((short) 1);
        poinvah.setTaxclass3((short) 1);
        poinvah.setTaxclass4((short) 1);
        poinvah.setTaxclass5((short) 1);
        poinvah.setInvnumber(nextReceiptNo());
        poinvah.setPonumber(poporh1.getPonumber());
        poinvah.setRcpnumber(nextReceiptNo());
        poinvah.setRcpcurr(poporh1.getCurrency());
        poinvah.setExrate(poporh1.getRate());
        poinvah.setRatedate(currentDate());
        poinvah.setRatetype("SP");
        poinvah.setRateoper((short) 1);
        poinvah.setRateover((short) 0);
        poinvah.setScurndecml((short) 2);
        poinvah.setFcdoctotal(poporh1.getDoctotal());
        poinvah.setScdoctotal(poporh1.getDoctotal());
        poinvah.setFcaptotal(new BigDecimal("0"));
        poinvah.setScaptotal(new BigDecimal("0.0"));
        poinvah.setF1099class("");
        poinvah.setF1099amt(new BigDecimal("0.0"));
        poinvah.setComplete((short) 1);
        poinvah.setPrinted((short) 0);
        poinvah.setMultircp((short) 0);
        poinvah.setValues(0);
        poinvah.setOnhold((short) 0);
        poinvah.setPgmver("71A");
        poinvah.setTransnum(new BigDecimal("0"));
        poinvah.setVerprorate((short) 2);
        poinvah.setHasrtg((short) 0);
        poinvah.setRtgrate((short) 0);
        poinvah.setRtgbase((short) 0);
        poinvah.setScrtgamt(new BigDecimal("0.0"));
        poinvah.setScaprtgamt(new BigDecimal("0.0"));
        poinvah.setRtgpercent(new BigDecimal("0.0"));
        poinvah.setHasjob((short) 0);
        poinvah.setFcaprtgamt(new BigDecimal("0.0"));
        poinvah.setTrcurrency(apven.get().getCurncode());
        poinvah.setExraterc(new BigDecimal("1.0"));
        poinvah.setRatedaterc(currentDate());
        poinvah.setRatetyperc("SP");
        poinvah.setRateoperrc((short) 1);
        poinvah.setRatercover((short) 0);
        poinvah.setRcurndecml((short) 2);
        poinvah.setDatebus(currentDate());
        poinvah.setVdacctset(apven.get().getIdacctset());
        poinvah.setIdn("0");
        poinvah.setCaxamount(new BigDecimal("0.0"));
        //Save the instance
        poinvahRepository.save(poinvah);


        // Print to verify values (optional)


        //Save
        poinvjRepository.save(poinvj);

        List<Poporl> poporls = poporlRepo.findById(poporh1.getId());
        for (Poporl poporl :poporls) {
            for (JsonNode node : sale.path("itemList")) {
                String itemno = "";
                Icitem icitem = icitemRepository.findByFmtitemno(poporl.getItemno());
                for (int i = 0; i < items.size(); i++) {
                    postPOItemsDTO item = items.get(i);
                    String itemcode = item.getItemCd();
                    if (itemcode.equals(node.path("itemCd").asText())) {
                        itemno = item.getSageItemCode();
                    }
                }
                Icitem sItem = icitemRepository.findByItemno(itemno);
                BigDecimal poinvlInst = poinvlRepository.findByRcphseq();
                Porcph1 maxRcpID = porcph1Repo.findMaxId();

            if (poporl.getStockitem() == 1) {
                //Icival

            }
                PoinvalId poinvalId = new PoinvalId();
                poinvalId.setDayendseq(dayEndNo());
                poinvalId.setInvahseq(BigDecimal.valueOf(1));
                poinvalId.setInvalseq(maxInvSqlID());
                Poinval poinval = new Poinval();
                poinval.setId(poinvalId);
                poinval.setAudtdate(currentDate());
                poinval.setAudttime(currentTime());
                poinval.setAudtuser("ADMIN");
                poinval.setAudtorg(company);
                poinval.setTranstype((short) 11);
                poinval.setOeonumber("");
                poinval.setItemexists((short) 1);
                poinval.setItemno(sItem.getItemno());
                poinval.setLocation("HQ");
                poinval.setItemdesc(sItem.getDesc());
                poinval.setCntlacct(sItem.getCategory());
                poinval.setTaxclass1(poporl.getTaxclass1());
                poinval.setTaxclass2((short) 1);
                poinval.setTaxclass3((short) 1);
                poinval.setTaxclass4((short) 1);
                poinval.setTaxclass5((short) 1);
                poinval.setRqreceived(new BigDecimal("0.0"));
                poinval.setRcpunit(poporl.getOrderunit());
                poinval.setConversion(new BigDecimal("1.0"));
                poinval.setSqreceived(new BigDecimal("0.0"));
                poinval.setStockunit(poporl.getOrderunit());
                poinval.setCostunit("");
                poinval.setUnitcost(node.path("prc").decimalValue());
                poinval.setPrunitcost(new BigDecimal("0.0"));
                poinval.setLoadedcost(node.path("prc").decimalValue());
                poinval.setFcextended(node.path("totAmt").decimalValue());
                poinval.setScextended(node.path("totAmt").decimalValue());
                poinval.setFcbaseallo(node.path("taxblAmt").decimalValue());
                poinval.setScbaseallo(node.path("taxblAmt").decimalValue());
                poinval.setFctaxallo(new BigDecimal("0.0"));
                poinval.setSctaxallo(new BigDecimal("0.0"));
                poinval.setFcprorated(new BigDecimal("0.0"));
                poinval.setScprorated(new BigDecimal("0.0"));
                poinval.setFctaxincl(new BigDecimal("0.0"));
                poinval.setSctaxincl(new BigDecimal("0.0"));
                poinval.setFctaxexcl(node.path("vatAmt").decimalValue());
                poinval.setSctaxexcl(node.path("vatAmt").decimalValue());
                poinval.setTaxbase1(node.path("taxblAmt").decimalValue());
                poinval.setTaxbase2(new BigDecimal("0.0"));
                poinval.setTaxbase3(new BigDecimal("0.0"));
                poinval.setTaxbase4(new BigDecimal("0.0"));
                poinval.setTaxbase5(new BigDecimal("0.0"));
                poinval.setTaxinclud1((short) 0);
                poinval.setTaxinclud2((short) 0);
                poinval.setTaxinclud3((short) 0);
                poinval.setTaxinclud4((short) 0);
                poinval.setTaxinclud5((short) 0);
                poinval.setTaxamount1(new BigDecimal("0.0"));
                poinval.setTaxamount2(new BigDecimal("0.0"));
                poinval.setTaxamount3(new BigDecimal("0.0"));
                poinval.setTaxamount4(new BigDecimal("0.0"));
                poinval.setTaxamount5(new BigDecimal("0.0"));
                poinval.setTxrecvamt1(new BigDecimal("0.0"));
                poinval.setTxrecvamt2(new BigDecimal("0.0"));
                poinval.setTxrecvamt3(new BigDecimal("0.0"));
                poinval.setTxrecvamt4(new BigDecimal("0.0"));
                poinval.setTxrecvamt5(new BigDecimal("0.0"));
                poinval.setTxexpsamt1(new BigDecimal("0.0"));
                poinval.setTxexpsamt2(new BigDecimal("0.0"));
                poinval.setTxexpsamt3(new BigDecimal("0.0"));
                poinval.setTxexpsamt4(new BigDecimal("0.0"));
                poinval.setTxexpsamt5(new BigDecimal("0.0"));
                poinval.setGlclearing("400600");
                poinval.setGlitem(poporl.getGlnonstkcr());
                poinval.setGlisposted((short) 1);
                poinval.setRqrectotal(new BigDecimal("1.0"));
                poinval.setSqrectotal(new BigDecimal("1.0"));
                poinval.setFcexttotal(new BigDecimal("0.0"));
                poinval.setScexttotal(new BigDecimal("0.0"));
                poinval.setStockitem(poporl.getStockitem());
                poinval.setPonumber(poporh1.getPonumber());
                poinval.setRcpnumber(nextReceiptNo());
                poinval.setDiscpct(new BigDecimal("0.0"));
                poinval.setFcdiscount(new BigDecimal("0.0"));
                poinval.setScdiscount(new BigDecimal("0.0"));
                poinval.setFcdisctot(new BigDecimal("0.0"));
                poinval.setScdisctot(new BigDecimal("0.0"));
                poinval.setValues(0);
                poinval.setTermdiscbl((short) 1);
                poinval.setRcplseq(maxsqlID());
                poinval.setInvlseq(maxInvSqlID());
                poinval.setFctaxrecv(new BigDecimal("0.0"));
                poinval.setSctaxrecv(new BigDecimal("0.0"));
                poinval.setFctaxexps(new BigDecimal("0.0"));
                poinval.setSctaxexps(new BigDecimal("0.0"));
                poinval.setFctaxamt1(new BigDecimal("0.0"));
                poinval.setFctaxamt2(new BigDecimal("0.0"));
                poinval.setFctaxamt3(new BigDecimal("0.0"));
                poinval.setFctaxamt4(new BigDecimal("0.0"));
                poinval.setFctaxamt5(new BigDecimal("0.0"));
                poinval.setComment("");
                poinval.setContract("");
                poinval.setProject("");
                poinval.setCcategory("");
                poinval.setCostclass((short) 0);
                poinval.setBilltype((short) 0);
                poinval.setBillrate(new BigDecimal("0.0"));
                poinval.setBillcurr("");
                poinval.setAritemno("");
                poinval.setArunit("");
                poinval.setRtgpercent(new BigDecimal("0.0"));
                poinval.setRtgdays((short) 0);
                poinval.setScrtgamt(new BigDecimal("0.0"));
                poinval.setFcrtgamtot(new BigDecimal("0.0"));
                poinval.setScrtgamtot(new BigDecimal("0.0"));
                poinval.setRtgdatedue(currentDate());
                poinval.setRtgamtover((short) 0);
                poinval.setRtgddtover((short) 0);
                poinval.setGloverhead("");
                poinval.setGllabor("");
                poinval.setFcovrhdamt(new BigDecimal("0.0"));
                poinval.setScovrhdamt(new BigDecimal("0.0"));
                poinval.setFclaboramt(new BigDecimal("0.0"));
                poinval.setSclaboramt(new BigDecimal("0.0"));
                poinval.setDfcunitcst(new BigDecimal("0.0"));
                poinval.setDscunitcst(new BigDecimal("0.0"));
                poinval.setDbillrate(new BigDecimal("0.0"));
                poinval.setPmtransnum(0);
                poinval.setRctaxallo(new BigDecimal("0.0"));
                poinval.setRctaxrecv(node.path("vatAmt").decimalValue());
                poinval.setRctaxexps(new BigDecimal("0.0"));
                poinval.setRctaxincl(new BigDecimal("0.0"));
                poinval.setTaramount1(node.path("vatAmt").decimalValue());
                poinval.setTaramount2(new BigDecimal("0.0"));
                poinval.setTaramount3(new BigDecimal("0.0"));
                poinval.setTaramount4(new BigDecimal("0.0"));
                poinval.setTaramount5(new BigDecimal("0.0"));
                poinval.setScraxallo(new BigDecimal("0.0"));
                poinval.setFcraxallo(new BigDecimal("0.0"));
                poinval.setScraxexps(new BigDecimal("0.0"));
                poinval.setFcraxexps(new BigDecimal("0.0"));
                poinval.setRfapallo(new BigDecimal("0.0"));
                poinval.setRfapexps(new BigDecimal("0.0"));
                poinval.setRfapamt1(node.path("qty").decimalValue());
                poinval.setRfapamt2(new BigDecimal("0.0"));
                poinval.setRfapamt3(new BigDecimal("0.0"));
                poinval.setRfapamt4(new BigDecimal("0.0"));
                poinval.setRfapamt5(new BigDecimal("0.0"));
                poinval.setDefextwght(new BigDecimal("0.0"));
                poinval.setTotdefexwt(new BigDecimal("0.0"));
                poinval.setDetailnum(node.path("itemSeq").shortValue());
                //Save
                poinvalRepository.save(poinval);

                PoinvnId poinvnId = new PoinvnId();
                poinvnId.setInvhseq(poinvh1Instance.getId().add(new BigDecimal(2)));
                poinvnId.setInvlseq(maxInvSqlID());
                Poinvn poinvn = new Poinvn();
                poinvn.setId(poinvnId);


                poinvn.setAudtdate(currentDate());
                poinvn.setAudttime(currentTime());
                poinvn.setAudtuser("ADMIN");
                poinvn.setAudtorg(company);
                poinvn.setItemdesc(sItem.getDesc());
                poinvn.setStockunit("");
                poinvn.setRcpunit("");
                poinvn.setRcpconv(new BigDecimal("1.0"));
                poinvn.setRcpdecml((short) 4);
                poinvn.setRqreceived(node.path("qty").decimalValue());
                poinvn.setSqreceived(node.path("qty").decimalValue());
                poinvn.setOqreceived(node.path("qty").decimalValue());
                poinvn.setUnitweight(new BigDecimal("0.0"));
                poinvn.setExtweight(new BigDecimal("0.0"));
                poinvn.setUnitcost(poporl.getUnitcost());
                poinvn.setExtended(node.path("taxblAmt").decimalValue());
                poinvn.setTaxbase1(node.path("taxblAmt").decimalValue());
                poinvn.setTaxbase2(new BigDecimal("0.0"));
                poinvn.setTaxbase3(new BigDecimal("0.0"));
                poinvn.setTaxbase4(new BigDecimal("0.0"));
                poinvn.setTaxbase5(new BigDecimal("0.0"));
                poinvn.setTaxinclud1((short) 0);
                poinvn.setTaxinclud2((short) 0);
                poinvn.setTaxinclud3((short) 0);
                poinvn.setTaxinclud4((short) 0);
                poinvn.setTaxinclud5((short) 0);
                poinvn.setTxbaseallo(node.path("taxblAmt").decimalValue());
                poinvn.setTxinclude1(new BigDecimal("0.0"));
                poinvn.setTxinclude2(new BigDecimal("0.0"));
                poinvn.setTxinclude3(new BigDecimal("0.0"));
                poinvn.setTxinclude4(new BigDecimal("0.0"));
                poinvn.setTxinclude5(new BigDecimal("0.0"));
                poinvn.setTxrecvamt1(node.path("vatAmt").decimalValue());
                poinvn.setTxrecvamt2(new BigDecimal("0.0"));
                poinvn.setTxrecvamt3(new BigDecimal("0.0"));
                poinvn.setTxrecvamt4(new BigDecimal("0.0"));
                poinvn.setTxrecvamt5(new BigDecimal("0.0"));
                poinvn.setTxexpsamt1(new BigDecimal("0.0"));
                poinvn.setTxexpsamt2(new BigDecimal("0.0"));
                poinvn.setTxexpsamt3(new BigDecimal("0.0"));
                poinvn.setTxexpsamt4(new BigDecimal("0.0"));
                poinvn.setTxexpsamt5(new BigDecimal("0.0"));
                poinvn.setTxalloamt1(new BigDecimal("0.0"));
                poinvn.setTxalloamt2(new BigDecimal("0.0"));
                poinvn.setTxalloamt3(new BigDecimal("0.0"));
                poinvn.setTxalloamt4(new BigDecimal("0.0"));
                poinvn.setTxalloamt5(new BigDecimal("0.0"));
                poinvn.setTfbaseallo(new BigDecimal(String.valueOf(node.path("taxblAmt").decimalValue())));
                poinvn.setTfinclude1(new BigDecimal("0.0"));
                poinvn.setTfinclude2(new BigDecimal("0.0"));
                poinvn.setTfinclude3(new BigDecimal("0.0"));
                poinvn.setTfinclude4(new BigDecimal("0.0"));
                poinvn.setTfinclude5(new BigDecimal("0.0"));
                poinvn.setTfalloamt1(new BigDecimal("0.0"));
                poinvn.setTfalloamt2(new BigDecimal("0.0"));
                poinvn.setTfalloamt3(new BigDecimal("0.0"));
                poinvn.setTfalloamt4(new BigDecimal("0.0"));
                poinvn.setTfalloamt5(new BigDecimal("0.0"));
                poinvn.setTfrecvamt1(node.path("vatAmt").decimalValue());
                poinvn.setTfrecvamt2(new BigDecimal("0.0"));
                poinvn.setTfrecvamt3(new BigDecimal("0.0"));
                poinvn.setTfrecvamt4(new BigDecimal("0.0"));
                poinvn.setTfrecvamt5(new BigDecimal("0.0"));
                poinvn.setTfexpsamt1(new BigDecimal("0.0"));
                poinvn.setTfexpsamt2(new BigDecimal("0.0"));
                poinvn.setTfexpsamt3(new BigDecimal("0.0"));
                poinvn.setTfexpsamt4(new BigDecimal("0.0"));
                poinvn.setTfexpsamt5(new BigDecimal("0.0"));
                poinvn.setDiscpct(new BigDecimal("0.0"));
                poinvn.setDiscount(new BigDecimal("0.0"));
                poinvn.setDiscountf(new BigDecimal("0.0"));
                poinvn.setFullyinv((short) 1);
                poinvn.setBillrate(new BigDecimal("0.0"));
                poinvn.setRtgpercent(new BigDecimal("0.0"));
                poinvn.setRtgdays((short) 0);
                poinvn.setRtgamount(new BigDecimal("0.0"));
                poinvn.setRtgdatedue(currentDate());
                poinvn.setRtgamtover((short) 0);
                poinvn.setRtgddtover((short) 0);
                poinvn.setTrinclude1(new BigDecimal("0.0"));
                poinvn.setTrinclude2(new BigDecimal("0.0"));
                poinvn.setTrinclude3(new BigDecimal("0.0"));
                poinvn.setTrinclude4(new BigDecimal("0.0"));
                poinvn.setTrinclude5(new BigDecimal("0.0"));
                poinvn.setTrrecvamt1(node.path("vatAmt").decimalValue());
                poinvn.setTrrecvamt2(new BigDecimal("0.0"));
                poinvn.setTrrecvamt3(new BigDecimal("0.0"));
                poinvn.setTrrecvamt4(new BigDecimal("0.0"));
                poinvn.setTrrecvamt5(new BigDecimal("0.0"));
                poinvn.setTrexpsamt1(new BigDecimal("0.0"));
                poinvn.setTrexpsamt2(new BigDecimal("0.0"));
                poinvn.setTrexpsamt3(new BigDecimal("0.0"));
                poinvn.setTrexpsamt4(new BigDecimal("0.0"));
                poinvn.setTrexpsamt5(new BigDecimal("0.0"));
                poinvn.setTralloamt1(new BigDecimal("0.0"));
                poinvn.setTralloamt2(new BigDecimal("0.0"));
                poinvn.setTralloamt3(new BigDecimal("0.0"));
                poinvn.setTralloamt4(new BigDecimal("0.0"));
                poinvn.setTralloamt5(new BigDecimal("0.0"));
                poinvn.setRaxbase1(new BigDecimal("0.0"));
                poinvn.setRaxbase2(new BigDecimal("0.0"));
                poinvn.setRaxbase3(new BigDecimal("0.0"));
                poinvn.setRaxbase4(new BigDecimal("0.0"));
                poinvn.setRaxbase5(new BigDecimal("0.0"));
                poinvn.setRxrecvamt1(new BigDecimal("0.0"));
                poinvn.setRxrecvamt2(new BigDecimal("0.0"));
                poinvn.setRxrecvamt3(new BigDecimal("0.0"));
                poinvn.setRxrecvamt4(new BigDecimal("0.0"));
                poinvn.setRxrecvamt5(new BigDecimal("0.0"));
                poinvn.setRxexpsamt1(new BigDecimal("0.0"));
                poinvn.setRxexpsamt2(new BigDecimal("0.0"));
                poinvn.setRxexpsamt3(new BigDecimal("0.0"));
                poinvn.setRxexpsamt4(new BigDecimal("0.0"));
                poinvn.setRxexpsamt5(new BigDecimal("0.0"));
                poinvn.setRxalloamt1(new BigDecimal("0.0"));
                poinvn.setRxalloamt2(new BigDecimal("0.0"));
                poinvn.setRxalloamt3(new BigDecimal("0.0"));
                poinvn.setRxalloamt4(new BigDecimal("0.0"));
                poinvn.setRxalloamt5(new BigDecimal("0.0"));
                poinvn.setWeightunit("");
                poinvn.setWeightconv(new BigDecimal("1.0"));
                poinvn.setDefuweight(new BigDecimal("0.0"));
                poinvn.setDefextwght(new BigDecimal("0.0"));
                //Save
                poinvnRepository.save(poinvn);
                //Lines
                Poinvl poinvl = new Poinvl();
                PoinvlId poinvlId = new PoinvlId();
                poinvlId.setInvhseq(poinvh1Instance.getId().add(new BigDecimal(2)));
                poinvlId.setInvlrev(new BigDecimal(1000));
                // Set embedded ID fields if applicable
                poinvl.setId(poinvlId);

                poinvl.setAudtdate(currentDate());
                poinvl.setAudttime(currentTime());
                poinvl.setAudtuser("ADMIN");
                poinvl.setAudtorg(company);
                poinvl.setInvlseq(poinvlInst);
                poinvl.setInvcseq(poinvlInst.add(new BigDecimal(1)));
                poinvl.setOeonumber("");
                poinvl.setIndbtable((short) 1);
                poinvl.setPostedtoic((short) 0);
                poinvl.setCompletion((short) 3);
                poinvl.setDtcomplete(new BigDecimal(0.0));
                poinvl.setRcphseq(maxRcpID.getId().add(new BigDecimal(2)));
                poinvl.setRcplseq(maxsqlID());
                poinvl.setItemexists((short) 1);
                poinvl.setItemno(sItem.getItemno());
                poinvl.setLocation("HQ");
                poinvl.setItemdesc(sItem.getDesc());
                poinvl.setVenditemno("");
                poinvl.setHascomment((short) 0);
                poinvl.setOrderunit(poporl.getOrderunit());
                poinvl.setOrderconv(new BigDecimal(1));
                poinvl.setOrderdecml((short) 4);
                poinvl.setRcpunit(poporl.getOrderunit());
                poinvl.setRcpconv(new BigDecimal(1));
                poinvl.setRcpdecml((short) 4);
                poinvl.setStockdecml((short) 4);
                poinvl.setRqreceived(node.path("qty").decimalValue());
                poinvl.setSqreceived(node.path("qty").decimalValue());
                poinvl.setOqreceived(new BigDecimal(0.0));
                poinvl.setUnitweight(new BigDecimal(0.0));
                poinvl.setExtweight(new BigDecimal(0.0));
                poinvl.setUnitcost(node.path("prc").decimalValue());
                poinvl.setExtended(node.path("taxblAmt").decimalValue());
                poinvl.setTaxbase1(node.path("taxblAmt").decimalValue());
                poinvl.setTaxbase2(new BigDecimal(0.0));
                poinvl.setTaxbase3(new BigDecimal(0.0));
                poinvl.setTaxbase4(new BigDecimal(0.0));
                poinvl.setTaxbase5(new BigDecimal(0.0));
                poinvl.setTaxclass1((short) 1);
                poinvl.setTaxclass2((short) 1);
                poinvl.setTaxclass3((short) 1);
                poinvl.setTaxclass4((short) 1);
                poinvl.setTaxclass5((short) 1);
                poinvl.setTaxrate1(new BigDecimal("16.0"));
                poinvl.setTaxrate2(new BigDecimal(0.0));
                poinvl.setTaxrate3(new BigDecimal(0.0));
                poinvl.setTaxrate4(new BigDecimal(0.0));
                poinvl.setTaxrate5(new BigDecimal(0.0));
                poinvl.setTaxinclud1((short) 0);
                poinvl.setTaxinclud2((short) 0);
                poinvl.setTaxinclud3((short) 0);
                poinvl.setTaxinclud4((short) 0);
                poinvl.setTaxinclud5((short) 0);
                poinvl.setTaxamount1(node.path("vatAmt").decimalValue());
                poinvl.setTaxamount2(new BigDecimal(0.0));
                poinvl.setTaxamount3(new BigDecimal(0.0));
                poinvl.setTaxamount4(new BigDecimal(0.0));
                poinvl.setTaxamount5(new BigDecimal(0.0));
                poinvl.setTxrecvamt1(node.path("vatAmt").decimalValue());
                poinvl.setTxrecvamt2(new BigDecimal(0.0));
                poinvl.setTxrecvamt3(new BigDecimal(0.0));
                poinvl.setTxrecvamt4(new BigDecimal(0.0));
                poinvl.setTxrecvamt5(new BigDecimal(0.0));
                poinvl.setTxexpsamt1(new BigDecimal(0.0));
                poinvl.setTxexpsamt2(new BigDecimal(0.0));
                poinvl.setTxexpsamt3(new BigDecimal(0.0));
                poinvl.setTxexpsamt4(new BigDecimal(0.0));
                poinvl.setTxexpsamt5(new BigDecimal(0.0));
                poinvl.setTxalloamt1(new BigDecimal(0.0));
                poinvl.setTxalloamt2(new BigDecimal(0.0));
                poinvl.setTxalloamt3(new BigDecimal(0.0));
                poinvl.setTxalloamt4(new BigDecimal(0.0));
                poinvl.setTxalloamt5(new BigDecimal(0.0));
                poinvl.setTxbaseallo(node.path("taxblAmt").decimalValue());
                poinvl.setTxincluded(new BigDecimal(0.0));
                poinvl.setTxexcluded(node.path("vatAmt").decimalValue());
                poinvl.setTaxamount(node.path("vatAmt").decimalValue());
                poinvl.setTxrecvamt(new BigDecimal(0.0));
                poinvl.setTxexpsamt(new BigDecimal(0.0));
                poinvl.setTxalloamt(new BigDecimal(0.0));
                poinvl.setTfbaseallo(node.path("taxblAmt").decimalValue());
                poinvl.setTfinclude1(new BigDecimal(0.0));
                poinvl.setTfinclude2(new BigDecimal(0.0));
                poinvl.setTfinclude3(new BigDecimal(0.0));
                poinvl.setTfinclude4(new BigDecimal(0.0));
                poinvl.setTfinclude5(new BigDecimal(0.0));
                poinvl.setTfalloamt1(new BigDecimal(0.0));
                poinvl.setTfalloamt2(new BigDecimal(0.0));
                poinvl.setTfalloamt3(new BigDecimal(0.0));
                poinvl.setTfalloamt4(new BigDecimal(0.0));
                poinvl.setTfalloamt5(new BigDecimal(0.0));
                poinvl.setTfrecvamt1(node.path("vatAmt").decimalValue());
                poinvl.setTfrecvamt2(new BigDecimal(0.0));
                poinvl.setTfrecvamt3(new BigDecimal(0.0));
                poinvl.setTfrecvamt4(new BigDecimal(0.0));
                poinvl.setTfrecvamt5(new BigDecimal(0.0));
                poinvl.setTfexpsamt1(new BigDecimal(0.0));
                poinvl.setTfexpsamt2(new BigDecimal(0.0));
                poinvl.setTfexpsamt3(new BigDecimal(0.0));
                poinvl.setTfexpsamt4(new BigDecimal(0.0));
                poinvl.setTfexpsamt5(new BigDecimal(0.0));
                poinvl.setTfalloamt1(new BigDecimal(0.0));
                poinvl.setTfalloamt2(new BigDecimal(0.0));
                poinvl.setTfalloamt3(new BigDecimal(0.0));
                poinvl.setTfalloamt4(new BigDecimal(0.0));
                poinvl.setTfalloamt5(new BigDecimal(0.0));
                poinvl.setFcextended(new BigDecimal(0.0));
                poinvl.setGlacexpens("");
                poinvl.setMprorated(new BigDecimal(0.0));
                poinvl.setStockitem(poporl.getStockitem());
                poinvl.setRcpnumber(nextReceiptNo());
                poinvl.setPorhseq(poporl.getId().getPorhseq());
                poinvl.setPorlseq(poporl.getPorlseq());
                poinvl.setPonumber(poporh1.getPonumber());
                poinvl.setGlnonstkcr("40070");
                poinvl.setManitemno("");
                poinvl.setDiscpct(new BigDecimal(0.0));
                poinvl.setDiscount(new BigDecimal(0.0));
                poinvl.setDiscountf(new BigDecimal(0.0));
                poinvl.setXrrqrecevd(node.path("qty").decimalValue());
                poinvl.setXirqrecevd(new BigDecimal(0.0));
                poinvl.setPvinvlines(0);
                poinvl.setFullyinv((short) 1);
                poinvl.setValues(0);
                poinvl.setTermdiscbl((short) 1);
                poinvl.setContract("");
                poinvl.setProject("");
                poinvl.setCcategory("");
                poinvl.setCostclass((short) 0);
                poinvl.setBilltype((short) 0);
                poinvl.setBillrate(new BigDecimal(0.0));
                poinvl.setBillcurr("");
                poinvl.setAritemno("");
                poinvl.setArunit("");
                poinvl.setRtgpercent(new BigDecimal(0.0));
                poinvl.setRtgdays((short) 0);
                poinvl.setRtgamount(new BigDecimal(0.0));
                poinvl.setRtgdatedue(new BigDecimal(0.0));
                poinvl.setRtgamtover((short) 0);
                poinvl.setRtgddtover((short) 0);
                poinvl.setTaramount1(node.path("vatAmt").decimalValue());
                poinvl.setTaramount2(new BigDecimal(0.0));
                poinvl.setTaramount3(new BigDecimal(0.0));
                poinvl.setTaramount4(new BigDecimal(0.0));
                poinvl.setTaramount5(new BigDecimal(0.0));
                poinvl.setTralloamt1(new BigDecimal("0.000"));
                poinvl.setTralloamt2(new BigDecimal("0.000"));
                poinvl.setTralloamt3(new BigDecimal("0.000"));
                poinvl.setTralloamt4(new BigDecimal("0.000"));
                poinvl.setTralloamt5(new BigDecimal("0.000"));

// Setting values for TRRECVAMT fields
                poinvl.setTrrecvamt1(node.path("vatAmt").decimalValue());
                poinvl.setTrrecvamt2(new BigDecimal("0.000"));
                poinvl.setTrrecvamt3(new BigDecimal("0.000"));
                poinvl.setTrrecvamt4(new BigDecimal("0.000"));
                poinvl.setTrrecvamt5(new BigDecimal("0.000"));
                poinvl.setTrexpsamt1(new BigDecimal("0.000"));
                poinvl.setTrexpsamt2(new BigDecimal("0.000"));
                poinvl.setTrexpsamt3(new BigDecimal("0.000"));
                poinvl.setTrexpsamt4(new BigDecimal("0.000"));
                poinvl.setTrexpsamt5(new BigDecimal("0.000"));
                poinvl.setRaxbase1(new BigDecimal("0.000"));
                poinvl.setRaxbase2(new BigDecimal("0.000"));
                poinvl.setRaxbase3(new BigDecimal("0.000"));
                poinvl.setRaxbase4(new BigDecimal("0.000"));
                poinvl.setRaxbase5(new BigDecimal("0.000"));
                poinvl.setRaxamount1(new BigDecimal("0.000"));
                poinvl.setRaxamount2(new BigDecimal("0.000"));
                poinvl.setRaxamount3(new BigDecimal("0.000"));
                poinvl.setRaxamount4(new BigDecimal("0.000"));
                poinvl.setRaxamount5(new BigDecimal("0.000"));
                poinvl.setRxrecvamt1(new BigDecimal("0.000"));
                poinvl.setRxrecvamt2(new BigDecimal("0.000"));
                poinvl.setRxrecvamt3(new BigDecimal("0.000"));
                poinvl.setRxrecvamt4(new BigDecimal("0.000"));
                poinvl.setRxrecvamt5(new BigDecimal("0.000"));
                poinvl.setRxexpsamt1(new BigDecimal("0.000"));
                poinvl.setRxexpsamt2(new BigDecimal("0.000"));
                poinvl.setRxexpsamt3(new BigDecimal("0.000"));
                poinvl.setRxexpsamt4(new BigDecimal("0.000"));
                poinvl.setRxexpsamt5(new BigDecimal("0.000"));
                poinvl.setRxalloamt1(new BigDecimal("0.000"));
                poinvl.setRxalloamt2(new BigDecimal("0.000"));
                poinvl.setRxalloamt3(new BigDecimal("0.000"));
                poinvl.setRxalloamt4(new BigDecimal("0.000"));
                poinvl.setRxalloamt5(new BigDecimal("0.000"));
                poinvl.setUcismanual((short) 1);
                poinvl.setWeightunit("");
                poinvl.setWeightconv(new BigDecimal("1.0000"));
                poinvl.setDefuweight(new BigDecimal("0.0000"));
                poinvl.setDefextwght(new BigDecimal("0.0000"));
                poinvl.setSerialqty(100);
                poinvl.setLotqty(new BigDecimal("0.0000"));
                poinvl.setSlitem((short) 1);
                poinvl.setDetailnum(node.path("itemSeq").shortValue());
                poinvl.setCaxable1((short) 1);
                poinvl.setCaxable2((short) 1);
                poinvl.setCaxable3((short) 1);
                poinvl.setCaxable4((short) 1);
                poinvl.setCaxable5((short) 1);
                poinvlRepository.save(poinvl);
            }
        }
    }*/
}

/*
               */