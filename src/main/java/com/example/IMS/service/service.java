package com.example.IMS.service;

import com.example.IMS.controller.PurchasesResponse;
import com.example.IMS.dto.*;
import com.example.IMS.entity.mssql.*;
import com.example.IMS.repository.mssql.*;
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
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final PorcpjRepository porcpjRepo;
    private final PorcpahRepository porcpahRepository ;
    private final PorcpalRepository porcpalRepository;
    private final PorcpnRepository porcpnRepository;
    private final Apven_repo apvenRepo;
    private final PoinvahRepository poinvahRepository;
    private final Poinvh1Repository poinvh1Repository;
    private final PoinvalRepository poinvalRepository;
    private final Poinvh2Repository poinvh2Repository;
    private final PoinvjRepository poinvjRepository;
    private final  PoinvnRepository poinvnRepository;

    public static Map<String,Integer> purchaseType =new HashMap<>();
    static {

    }
    public BigDecimal dayEndNo(){
      Poopt poopt =  pooptRepo.findByRatetype("SP");
      return poopt.getLgendayend();

    }

    public List<importItemListDTO> getImports(String refNumber) throws JsonProcessingException {

        selectImportDataDTO importDataDTO = new selectImportDataDTO();
        importDataDTO.setTpin(clientTpin);
        importDataDTO.setBhfId(branchId);
        importDataDTO.setLastReqDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        importDataDTO.setDclRefNum(refNumber);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<selectImportDataDTO> requestEntity = new HttpEntity<>(importDataDTO, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/zrasandboxvsdc/imports/selectImportItems", requestEntity, String.class);
        System.out.print(response);
        String jsonResponse = "{\"resultCd\":\"000\",\"resultMsg\":\"It is succeeded\",\"resultDt\":\"20231120194118\",\"data\":{\"itemList\":[{\"taskCd\":\"2239078\",\"dclDe\":\"-1\",\"itemSeq\":1,\"dclNo\":\"C3460-2019-TZDL\",\"hsCd\":\"20055900000\",\"itemNm\":\"BAKED BEANS\",\"imptItemsttsCd\":\"2\",\"orgnNatCd\":\"BR\",\"exptNatCd\":\"BR\",\"pkg\":2922,\"pkgUnitCd\":null,\"qty\":19946,\"qtyUnitCd\":\"KGM\",\"totWt\":19945.57,\"netWt\":19945.57,\"spplrNm\":\"ODERICH CONSERVA QUALIDADE\\nBRASIL\",\"agntNm\":\"BN METRO Ltd\",\"invcFcurAmt\":296865.6,\"invcFcurCd\":\"USD\",\"invcFcurExcrt\":929.79}]}}";

        if (response.getStatusCode() == HttpStatus.OK) {
            //String jsonResponse = response.getBody(); // Use actual response body here
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
    public BigDecimal maxID (){
        Porcph1 porcph1= porcph1Repo.findMaxId();
        return porcph1.getId().add(BigDecimal.valueOf(2));
    }
    public BigDecimal maxsqlID (){
       return porcplRepo.findByRcphseq();
    }
    private void createPurchaseOrderReceipt(JsonNode sale , String ponumber, List<postPOItemsDTO> items){

        Poporh1 poporh1 = poporh1Repo.findByPonumberContaining(ponumber);
        poporh1.setIscomplete((short) 1);
        poporh1.setDtcomplete(currentDate());
        poporh1.setLinescmpl(poporh1.getLines());
        poporh1.setRcpdate(currentDate());
        poporh1.setLastreceip(nextReceiptNo());
        poporh1.setRcpcount((short) 1);
        poporh1Repo.save(poporh1);
        Optional<Apven> apven = apvenRepo.findByVendorid(poporh1.getVdcode());
        Porcph1 maxID = porcph1Repo.findMaxId();;
        String inv =sale.path("spplrInvcNo").asText();
        BigDecimal totalQty = BigDecimal.ZERO;
        for (JsonNode node : sale.path("itemList")){
            BigDecimal qty = node.path("qty").decimalValue();
            totalQty = totalQty.add(qty);
        }

        List<Poporl> poporls = poporlRepo.findById(poporh1.getId());
        for (Poporl poporl :poporls){
Icitem icitem = icitemRepository.findByFmtitemno(poporl.getItemno());
            poporl.setCompletion((short)3);
            poporl.setDtcomplete(currentDate());
            poporlRepo.save(poporl);
            for (JsonNode node : sale.path("itemList")) {
                String itemno = "";
                for (int i = 0; i < items.size(); i++) {
                    postPOItemsDTO item = items.get(i);
                    String itemcode = item.getItemCd();
                    if (itemcode.equals(node.path("itemCd").asText())) {
                        itemno = item.getSageItemCode();
                    }
                }

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
                porcpn.setItemdesc(poporl.getItemdesc());
                porcpn.setStockunit("");
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
                porcpn.setTaxbase1(node.path("taxblAmt").decimalValue());
                porcpn.setTaxbase2(new BigDecimal("0.0"));
                porcpn.setTaxbase3(new BigDecimal("0.0"));
                porcpn.setTaxbase4(new BigDecimal("0.0"));
                porcpn.setTaxbase5(new BigDecimal("0.0"));
                porcpn.setTaxinclud1((short) 0);
                porcpn.setTaxinclud2((short) 0);
                porcpn.setTaxinclud3((short) 0);
                porcpn.setTaxinclud4((short) 0);
                porcpn.setTaxinclud5((short) 0);
                porcpn.setTxbaseallo(node.path("taxblAmt").decimalValue());
                porcpn.setTxinclude1(new BigDecimal("0.0"));
                porcpn.setTxinclude2(new BigDecimal("0.0"));
                porcpn.setTxinclude3(new BigDecimal("0.0"));
                porcpn.setTxinclude4(new BigDecimal("0.0"));
                porcpn.setTxinclude5(new BigDecimal("0.0"));
                porcpn.setTxrecvamt1(node.path("vatAmt").decimalValue());
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
                porcpn.setTfbaseallo(new BigDecimal(String.valueOf(node.path("taxblAmt").decimalValue())));
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
                porcpn.setTfrecvamt1(node.path("vatAmt").decimalValue());
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
                porcpn.setTrrecvamt1(node.path("vatAmt").decimalValue());
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
                porcpalId.setRcpalseq(porcpalRepository.findByRcpal());
                Porcpal porcpal = new Porcpal();
                porcpal.setId(porcpalId);
                porcpal.setAudtdate(currentDate());
                porcpal.setAudttime(currentTime());
                porcpal.setAudtuser("ADMIN");
                porcpal.setAudtorg(company);
                porcpal.setTranstype((short) 11);
                porcpal.setOeonumber("");
                porcpal.setItemexists((short) 1);
                porcpal.setItemno(poporl.getItemno());
                porcpal.setLocation("HQ");
                porcpal.setItemdesc(poporl.getDesc());
                porcpal.setCntlacct(icitem.getCategory());
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
                porcpal.setCostunit("");
                porcpal.setUnitcost(node.path("prc").decimalValue());
                porcpal.setPrunitcost(new BigDecimal("0.0"));
                porcpal.setLoadedcost(node.path("prc").decimalValue());
                porcpal.setRecentcost(node.path("prc").decimalValue());
                porcpal.setFcextended(node.path("totAmt").decimalValue());
                porcpal.setScextended(node.path("totAmt").decimalValue());
                porcpal.setFcbaseallo(node.path("taxblAmt").decimalValue());
                porcpal.setScbaseallo(node.path("taxblAmt").decimalValue());
                porcpal.setFctaxallo(new BigDecimal("0.0"));
                porcpal.setSctaxallo(new BigDecimal("0.0"));
                porcpal.setFcprorated(new BigDecimal("0.0"));
                porcpal.setScprorated(new BigDecimal("0.0"));
                porcpal.setFctaxincl(new BigDecimal("0.0"));
                porcpal.setSctaxincl(new BigDecimal("0.0"));
                porcpal.setFctaxexcl(node.path("vatAmt").decimalValue());
                porcpal.setSctaxexcl(node.path("vatAmt").decimalValue());
                porcpal.setFcmprorate(new BigDecimal("0.0"));
                porcpal.setScmprorate(new BigDecimal("0.0"));
                porcpal.setTaxbase1(node.path("taxblAmt").decimalValue());
                porcpal.setTaxbase2(new BigDecimal("0.0"));
                porcpal.setTaxbase3(new BigDecimal("0.0"));
                porcpal.setTaxbase4(new BigDecimal("0.0"));
                porcpal.setTaxbase5(new BigDecimal("0.0"));
                porcpal.setTaxinclud1((short) 1);
                porcpal.setTaxinclud2((short) 0);
                porcpal.setTaxinclud3((short) 1);
                porcpal.setTaxinclud4((short) 0);
                porcpal.setTaxinclud5((short) 1);
                porcpal.setTaxamount1(node.path("vatAmt").decimalValue());
                porcpal.setTaxamount2(new BigDecimal("0.0"));
                porcpal.setTaxamount3(new BigDecimal("0.0"));
                porcpal.setTaxamount4(new BigDecimal("0.0"));
                porcpal.setTaxamount5(new BigDecimal("0.0"));
                porcpal.setTxrecvamt1(node.path("vatAmt").decimalValue());
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
                porcpal.setFcexttotal(node.path("taxblAmt").decimalValue());
                porcpal.setScexttotal(node.path("taxblAmt").decimalValue());
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
                porcpal.setValues(1);
                porcpal.setRcplseq(maxsqlID());
                porcpal.setFctaxrecv(node.path("vatAmt").decimalValue());
                porcpal.setSctaxrecv(node.path("vatAmt").decimalValue());
                porcpal.setFctaxexps(new BigDecimal("0.0"));
                porcpal.setSctaxexps(new BigDecimal("0.0"));
                porcpal.setFctaxamt1(node.path("vatAmt").decimalValue());
                porcpal.setFctaxamt2(new BigDecimal("0.0"));
                porcpal.setFctaxamt3(new BigDecimal("0.0"));
                porcpal.setFctaxamt4(new BigDecimal("0.0"));
                porcpal.setFctaxamt5(new BigDecimal("0.0"));
                porcpal.setComment("");
                porcpal.setContract("");
                porcpal.setProject("");
                porcpal.setCcategory("");
                porcpal.setCostclass((short) 1);
                porcpal.setBilltype((short) 1);
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
                porcpal.setPmtransnum(1);
                porcpal.setRctaxallo(new BigDecimal("0.0"));
                porcpal.setRctaxrecv(node.path("vatAmt").decimalValue());
                porcpal.setRctaxexps(new BigDecimal("0.0"));
                porcpal.setRctaxincl(new BigDecimal("0.0"));
                porcpal.setTaramount1(node.path("vatAmt").decimalValue());
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
                porcpal.setDeltaconve(new BigDecimal("0.0"));
                porcpal.setDeltaunit(poporl.getOrderunit());
                porcpal.setDetailnum(node.path("itemSeq").shortValue());
                porcpalRepository.save(porcpal);
                // Create an instance of PorcplId
                PorcplId porcplId = new PorcplId();
                porcplId.setRcphseq(maxID.getId().add(BigDecimal.valueOf(2))); // Example value
                porcplId.setRcplrev(BigDecimal.valueOf(31)); // Example value

// Create an instance of Porcpl
                Porcpl porcpl = new Porcpl();
                porcpl.setId(porcplId);
                porcpl.setAudtdate(currentDate()); // Assuming currentDate() returns a BigDecimal
                porcpl.setAudttime(currentTime()); // Assuming currentTime() returns a BigDecimal
                porcpl.setAudtuser("ADMIN");
                porcpl.setAudtorg(company);
                porcpl.setRcplseq(maxsqlID()); // Example value
                porcpl.setRcpcseq(maxsqlID().add(new BigDecimal("2"))); // Example value
                porcpl.setOeonumber(""); // Set as needed
                porcpl.setIndbtable((short) 1);
                porcpl.setPostedtoic((short) 1);
                porcpl.setCompletion((short) 1);
                porcpl.setDtcomplete(currentDate()); // Assuming currentDate() returns a BigDecimal
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
                porcpl.setExtended(node.path("taxblAmt").decimalValue());
                porcpl.setTaxbase1(node.path("taxblAmt").decimalValue());
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
                porcpl.setTaxamount1(node.path("vatAmt").decimalValue());
                porcpl.setTaxamount2(new BigDecimal(0));
                porcpl.setTaxamount3(new BigDecimal(0));
                porcpl.setTaxamount4(new BigDecimal(0));
                porcpl.setTaxamount5(new BigDecimal(0));
                porcpl.setTxrecvamt1(node.path("vatAmt").decimalValue());
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
                porcpl.setTxbaseallo(node.path("taxblAmt").decimalValue());
                porcpl.setTxincluded(new BigDecimal(0));
                porcpl.setTxexcluded(node.path("vatAmt").decimalValue());
                porcpl.setTaxamount(node.path("vatAmt").decimalValue());
                porcpl.setTxrecvamt(node.path("vatAmt").decimalValue());
                porcpl.setTxexpsamt(new BigDecimal(0));
                porcpl.setTxalloamt(new BigDecimal(0));
                porcpl.setTfbaseallo(node.path("taxblAmt").decimalValue());
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
                porcpl.setTfrecvamt1(node.path("vatAmt").decimalValue());
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
                porcpl.setXinetxtend(node.path("taxblAmt").decimalValue());
                porcpl.setInvlines(1);
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
                porcpl.setTaramount1(node.path("vatAmt").decimalValue());
                porcpl.setTaramount2(new BigDecimal("0.000"));
                porcpl.setTaramount3(new BigDecimal("0.000"));
                porcpl.setTaramount4(new BigDecimal("0.000"));
                porcpl.setTaramount5(new BigDecimal("0.000"));
                porcpl.setTralloamt1(new BigDecimal("0.000"));
                porcpl.setTralloamt2(new BigDecimal("0.000"));
                porcpl.setTralloamt3(new BigDecimal("0.000"));
                porcpl.setTralloamt4(new BigDecimal("0.000"));
                porcpl.setTralloamt5(new BigDecimal("0.000"));
                porcpl.setTrrecvamt1(node.path("vatAmt").decimalValue());
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

// Save the instance
                porcplRepo.save(porcpl);
            }
        }

        BigDecimal porcpjSeq = porcpjRepo.findMaxProrateSeq();
        Porcpj porcpj = new Porcpj();
        porcpj.setId(maxID.getId().add(BigDecimal.valueOf(2)));
        porcpj.setAudtdate(currentDate());
        porcpj.setAudttime(currentTime());
        porcpj.setAudtuser("ADMIN");
        porcpj.setAudtorg(company);
        porcpj.setProrateseq(porcpjSeq.add(new BigDecimal("1")));
        porcpj.setPostdate(currentDate());
        porcpj.setScamount(sale.path("totAmt").decimalValue());
        porcpj.setFcamount(sale.path("totAmt").decimalValue());
        porcpj.setScdoctotal(sale.path("totAmt").decimalValue());
        porcpj.setIscomplete((short) 1);
        porcpj.setDtcomplete(currentDate());
        porcpj.setScrtgamt(new BigDecimal("0.0"));
        porcpj.setCaxamount(new BigDecimal("0.0"));
        porcpjRepo.save(porcpj);

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
        porcpah.setRcphseq(new BigDecimal(1));
        porcpah.setPostdate(currentDate());
        porcpah.setDayenddate(new BigDecimal(20240908));
        porcpah.setTransdate(currentDate());
        porcpah.setReference(poporh1.getReference());
        porcpah.setFiscyear("2024");
        porcpah.setFiscperiod((short) 9);
        porcpah.setDescriptio(poporh1.getDescriptio());
        porcpah.setTranstype((short) 1);
        porcpah.setVendor(poporh1.getVdcode());
        porcpah.setVendorname(poporh1.getVdname());
        porcpah.setTaxgroup(apven.get().getCodetaxgrp());
        porcpah.setTaxauth1("");
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
        porcpah.setRatedaterc(new BigDecimal(20240908));
        porcpah.setRatetyperc("SP");
        porcpah.setRateoperrc((short) 1);
        porcpah.setRatercover((short) 0);
        porcpah.setRcurndecml((short) 2);
        porcpah.setDatebus(new BigDecimal(20240908));
        porcpah.setVdacctset(apven.get().getIdacctset());
        porcpah.setCaxamount(new BigDecimal("0.0"));
        porcpahRepository.save(porcpah);

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
                .isinvoiced((short)1)
                .iscomplete((short)1)
                .dtcomplete(currentDate())
                .postdate(currentDate())
                .date(currentDate())
                .fiscyear(maxID.getFiscyear())
                .fiscperiod(maxID.getFiscperiod())
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
                .extended(sale.path("totTaxblAmt").decimalValue())
                .doctotal(sale.path("totAmt").decimalValue())
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
                .taxbase1(sale.path("taxablAmt").decimalValue())
                .taxbase2(new BigDecimal("0"))
                .taxbase3(new BigDecimal("0"))
                .taxbase4(new BigDecimal("0"))
                .taxbase5(new BigDecimal("0"))
                .txinclude1(new BigDecimal("0"))
                .txinclude2(new BigDecimal("0"))
                .txinclude3(new BigDecimal("0"))
                .txinclude4(new BigDecimal("0"))
                .txinclude5(new BigDecimal("0"))
                .txexclude1(sale.path("vatAmt").decimalValue())
                .txexclude2(new BigDecimal("0"))
                .txexclude3(new BigDecimal("0"))
                .txexclude4(new BigDecimal("0"))
                .txexclude5(new BigDecimal("0"))
                .taxamount1(sale.path("vatAmt").decimalValue())
                .taxamount2(new BigDecimal("0"))
                .taxamount3(new BigDecimal("0"))
                .taxamount4(new BigDecimal("0"))
                .taxamount5(new BigDecimal("0"))
                .txrecvamt1(sale.path("vatAmt").decimalValue())
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
                .txbaseallo(sale.path("taxblAmt").decimalValue())
                .txincluded(new BigDecimal("0.00"))
                .txexcluded(sale.path("vatAmt").decimalValue())
                .taxamount(sale.path("vatAmt").decimalValue())
                .txrecvamt(sale.path("vatAmt").decimalValue())
                .txexpsamt(new BigDecimal("0.00"))
                .txalloamt(new BigDecimal("0.00"))
                .mprorated(new BigDecimal("0.00"))
                .mtoprorate(new BigDecimal("0.00"))
                .scamount(sale.path("totAmt").decimalValue())
                .fcamount(sale.path("totAmt").decimalValue())
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
                .taramount1(sale.path("vatAmt").decimalValue())
                .taramount2(new BigDecimal("0.00"))
                .taramount3(new BigDecimal("0.00"))
                .taramount4(new BigDecimal("0.00"))
                .taramount5(new BigDecimal("0.00"))
                .trinclude1(new BigDecimal("0.00"))
                .trinclude2(new BigDecimal("0.00"))
                .trinclude3(new BigDecimal("0.00"))
                .trinclude4(new BigDecimal("0.00"))
                .trinclude5(new BigDecimal("0.00"))
                .trexclude1(sale.path("vatAmt").decimalValue())
                .trexclude2(new BigDecimal("0.00"))
                .trexclude3(new BigDecimal("0.00"))
                .trexclude4(new BigDecimal("0.00"))
                .trexclude5(new BigDecimal("0.00"))
                .trrecvamt1(sale.path("vatAmt").decimalValue())
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
                .id(maxID.getId().add(BigDecimal.valueOf(1)))
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





    }
    public void postPoToSage(int invnumber, String po_number, List<postPOItemsDTO> items) throws JsonProcessingException {

        String url = baseUrl + "/trnsPurchase/selectTrnsPurchaseSales";
        selectSales selectSales = new selectSales();
        selectSales.setTpin(clientTpin);
        selectSales.setBhfId(branchId);
        selectSales.setLastReqDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        Poporh1 poporh1 = poporh1Repo.findByPonumberContaining(po_number);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<selectSales> requestEntity = new HttpEntity<>(selectSales, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            String jsonResponse = "{\"resultCd\":\"000\",\"resultMsg\":\"It is succeeded\",\"resultDt\":\"20240510103403\",\"data\":{\"saleList\":[{\"spplrTpin\":\"1019249900                    \",\"spplrNm\":\"SMART SUPPLIER\",\"spplrBhfId\":\"000\",\"spplrInvcNo\":45,\"rcptTyCd\":\"S\",\"pmtTyCd\":\"01\",\"cfmDt\":\"2024-05-08 10:20:10\",\"salesDt\":\"20240502\",\"stockRlsDt\":null,\"totItemCnt\":1,\"totTaxblAmt\":86.2069,\"totTaxAmt\":13.7931,\"totAmt\":100,\"remark\":null,\"itemList\":[{\"itemSeq\":1,\"itemCd\":\"20044\",\"itemClsCd\":\"50102517\",\"itemNm\":\"ChickenWings\",\"bcd\":null,\"pkgUnitCd\":\"BA\",\"pkg\":0,\"qtyUnitCd\":\"BE\",\"qty\":1,\"prc\":100,\"splyAmt\":100,\"dcRt\":0,\"dcAmt\":0,\"vatCatCd\":\"A\",\"iplCatCd\":null,\"tlCatCd\":null,\"exciseTxCatCd\":null,\"vatTaxblAmt\":86.21,\"exciseTaxblAmt\":0,\"iplTaxblAmt\":0,\"tlTaxblAmt\":0,\"taxblAmt\":86.21,\"vatAmt\":13.79,\"iplAmt\":0,\"tlAmt\":0,\"exciseTxAmt\":0,\"totAmt\":100}]},{\"spplrTpin\":\"1019249900                    \",\"spplrNm\":\"SMART SUPPLIER\",\"spplrBhfId\":\"000\",\"spplrInvcNo\":46,\"rcptTyCd\":\"S\",\"pmtTyCd\":\"01\",\"cfmDt\":\"2024-05-08 10:20:10\",\"salesDt\":\"20240502\",\"stockRlsDt\":null,\"totItemCnt\":1,\"totTaxblAmt\":86.2069,\"totTaxAmt\":13.7931,\"totAmt\":100,\"remark\":null,\"itemList\":[{\"itemSeq\":1,\"itemCd\":\"20044\",\"itemClsCd\":\"50102517\",\"itemNm\":\"ChickenWings\",\"bcd\":null,\"pkgUnitCd\":\"BA\",\"pkg\":0,\"qtyUnitCd\":\"BE\",\"qty\":1,\"prc\":100,\"splyAmt\":100,\"dcRt\":0,\"dcAmt\":0,\"vatCatCd\":\"A\",\"iplCatCd\":null,\"tlCatCd\":null,\"exciseTxCatCd\":null,\"vatTaxblAmt\":86.21,\"exciseTaxblAmt\":0,\"iplTaxblAmt\":0,\"tlTaxblAmt\":0,\"taxblAmt\":86.21,\"vatAmt\":13.79,\"iplAmt\":0,\"tlAmt\":0,\"exciseTxAmt\":0,\"totAmt\":100}]}]}}";

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode saleList = rootNode.path("data").path("saleList");

            if(saleList != null) {
                for (JsonNode sale : saleList) {
                    int invoiceNumber = sale.path("spplrInvcNo").asInt();
                    if (invoiceNumber == invnumber) {
                        createPOInvoice(sale, po_number, items); // Now includes the other objects
                    }
                }
            } else {
                return;
            }
        }
    }


    public void createPOInvoice(JsonNode sale, String ponumber,List<postPOItemsDTO>items){
    Porcph1 maxID = porcph1Repo.findMaxId();
    Poporh1 poporh1 = poporh1Repo.findByPonumberContaining(ponumber);
    Optional<Apven> apven = apvenRepo.findByVendorid(poporh1.getVdcode());

    //Instance for Poinvh1
    Poinvh1 poinvh1Instance = poinvh1Repository.findMaxId();
    Poinvh1 poinvh1 = new Poinvh1();
    poinvh1.setId(poinvh1Instance.getId().add(new BigDecimal(2)));
    poinvh1.setAudtdate(currentDate());
    poinvh1.setAudttime(currentTime());
    poinvh1.setAudtuser("ADMIN");
    poinvh1.setAudtorg("company");
    poinvh1.setNextlseq(poinvh1Instance.getNextlseq().add(BigDecimal.valueOf(1)));
    poinvh1.setLines(sale.path("totItemCnt").asInt());
    poinvh1.setLinescmpl(sale.path("totItemCnt").asInt());
    poinvh1.setCosts(0);
    poinvh1.setCostscmpl(0);
    poinvh1.setPayments(1);
    poinvh1.setTaxlines(3);
    poinvh1.setTaxautocal((short) 1);
    poinvh1.setIscomplete((short) 1);
    poinvh1.setDtcomplete(currentDate());
    poinvh1.setPostdate(currentDate());
    poinvh1.setPorhseq(poporh1.getId());
    poinvh1.setPonumber(poporh1.getPonumber());
    poinvh1.setDate(currentDate());
    poinvh1.setFiscyear("2024");
    poinvh1.setFiscperiod((short) 5);
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
    poinvh1.setRetdate(currentDate());
    poinvh1.setDescriptio(sale.path("remarks").asText());
    poinvh1.setReference(sale.path("").asText());
    poinvh1.setComment("");
    poinvh1.setCurrency(poporh1.getCurrency());
    poinvh1.setRate(new BigDecimal(1.0));
    poinvh1.setSpread(new BigDecimal(0.0));
    poinvh1.setRatetype("SP");
    poinvh1.setRatematch((short) 1);
    poinvh1.setRatedate(currentDate());
    poinvh1.setRateoper((short) 1);
    poinvh1.setRateover((short) 0);
    poinvh1.setScurndecml((short) 2);
    poinvh1.setDtpymtasof(currentDate());
    poinvh1.setTermdueamt(new BigDecimal(0.0));
    poinvh1.setExtweight(new BigDecimal(0.0));
    poinvh1.setExtended(new BigDecimal(0.0));
    poinvh1.setDoctotal(new BigDecimal(0.0));
    poinvh1.setAmount(new BigDecimal(0.0));
    poinvh1.setRqreceived(new BigDecimal(0.0));
    poinvh1.setTaxgroup("VATZMW01");
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
    poinvh1.setTaxbase1(new BigDecimal(0.0));
    poinvh1.setTaxbase2(new BigDecimal(0.0));
    poinvh1.setTaxbase3(new BigDecimal(0.0));
    poinvh1.setTaxbase4(new BigDecimal(0.0));
    poinvh1.setTaxbase5(new BigDecimal(0.0));
    poinvh1.setTxinclude1(new BigDecimal(0.0));
    poinvh1.setTxinclude2(new BigDecimal(0.0));
    poinvh1.setTxinclude3(new BigDecimal(0.0));
    poinvh1.setTxinclude4(new BigDecimal(0.0));
    poinvh1.setTxinclude5(new BigDecimal(0.0));
    poinvh1.setTxexclude1(new BigDecimal(0.0));
    poinvh1.setTxexclude2(new BigDecimal(0.0));
    poinvh1.setTxexclude3(new BigDecimal(0.0));
    poinvh1.setTxexclude4(new BigDecimal(0.0));
    poinvh1.setTxexclude5(new BigDecimal(0.0));
    poinvh1.setTaxamount1(new BigDecimal(0.0));
    poinvh1.setTaxamount2(new BigDecimal(0.0));
    poinvh1.setTaxamount3(new BigDecimal(0.0));
    poinvh1.setTaxamount4(new BigDecimal(0.0));
    poinvh1.setTaxamount5(new BigDecimal(0.0));
    poinvh1.setTxrecvamt1(new BigDecimal(0.0));
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
    poinvh1.setTxbaseallo(new BigDecimal(0.0));
    poinvh1.setTxincluded(new BigDecimal(0.0));
    poinvh1.setTxexcluded(new BigDecimal(0.0));
    poinvh1.setTaxamount(new BigDecimal(0.0));
    poinvh1.setTxrecvamt(new BigDecimal(0.0));
    poinvh1.setTxexpsamt(new BigDecimal(0.0));
    poinvh1.setTxalloamt(new BigDecimal(0.0));
    poinvh1.setF1099class("");
    poinvh1.setF1099amt(new BigDecimal(0.0));
    poinvh1.setMprorated(new BigDecimal(0.0));
    poinvh1.setMtoprorate(new BigDecimal(0.0));
    poinvh1.setMexpensed(new BigDecimal(0.0));
    poinvh1.setScamount(new BigDecimal(0.0));
    poinvh1.setFcamount(new BigDecimal(0.0));
    poinvh1.setMultircp((short) 0);
    poinvh1.setRcps(0);
    poinvh1.setVdemail("");
    poinvh1.setVdphonec("");
    poinvh1.setVdfaxc("");
    poinvh1.setVdemailc("");
    poinvh1.setDiscpct(new BigDecimal(0.0));
    poinvh1.setDiscount(new BigDecimal(0.0));
    poinvh1.setValues(0);
    poinvh1.setOnhold((short) 0);
    poinvh1.setTermdbwt(new BigDecimal(0.0));
    poinvh1.setTermdbnt(new BigDecimal(0.0));
    poinvh1.setVerprorate((short) 0);
    poinvh1.setHasrtg((short) 0);
    poinvh1.setRtgrate((short) 0);
    poinvh1.setRtgbase((short) 0);
    poinvh1.setRtgterms("");
    poinvh1.setRtgamount(new BigDecimal(0.0));
    poinvh1.setJoblines(0);
    poinvh1.setJobcosts(0);
    poinvh1.setTrcurrency("ZMW");
    poinvh1.setRaterc(new BigDecimal(0.0));
    poinvh1.setSpreadrc(new BigDecimal(0.0));
    poinvh1.setRatetyperc("SP");
    poinvh1.setRatemtchrc((short) 1);
    poinvh1.setRatedaterc(currentDate());
    poinvh1.setRateoperrc((short)1);
    poinvh1.setRatercover((short)0);
    poinvh1.setRcurndecml((short)2);
    poinvh1.setTaramount1(new BigDecimal(0.0));
    poinvh1.setTaramount2(new BigDecimal(0.0));
    poinvh1.setTaramount3(new BigDecimal(0.0));
    poinvh1.setTaramount4(new BigDecimal(0.0));
    poinvh1.setTaramount5(new BigDecimal(0.0));
    poinvh1.setTrinclude1(new BigDecimal(0.0));
    poinvh1.setTrinclude2(new BigDecimal(0.0));
    poinvh1.setTrinclude3(new BigDecimal(0.0));
    poinvh1.setTrinclude4(new BigDecimal(0.0));
    poinvh1.setTrinclude5(new BigDecimal(0.0));
    poinvh1.setTrexclude1(new BigDecimal(0.0));
    poinvh1.setTrexclude2(new BigDecimal(0.0));
    poinvh1.setTrexclude3(new BigDecimal(0.0));
    poinvh1.setTrexclude4(new BigDecimal(0.0));
    poinvh1.setTrexclude5(new BigDecimal(0.0));
    poinvh1.setTrrecvamt1(new BigDecimal(0.0));
    poinvh1.setTrrecvamt2(new BigDecimal(0.0));
    poinvh1.setTrrecvamt3(new BigDecimal(0.0));
    poinvh1.setTrrecvamt4(new BigDecimal(0.0));
    poinvh1.setTrrecvamt5(new BigDecimal(0.0));
    poinvh1.setTrexpsamt1(new BigDecimal(0.0));
    poinvh1.setTrexpsamt2(new BigDecimal(0.0));
    poinvh1.setTrexpsamt3(new BigDecimal(0.0));
    poinvh1.setTrexpsamt4(new BigDecimal(0.0));
    poinvh1.setTrexpsamt5(new BigDecimal(0.0));
    poinvh1.setTralloamt1(new BigDecimal(0.0));
    poinvh1.setTralloamt2(new BigDecimal(0.0));
    poinvh1.setTralloamt3(new BigDecimal(0.0));
    poinvh1.setTralloamt4(new BigDecimal(0.0));
    poinvh1.setTralloamt5(new BigDecimal(0.0));
    poinvh1.setRtgtaxrep(new BigDecimal(0));
    poinvh1.setTaxversion(new BigDecimal(2));
    poinvh1.setRaxbase1(new BigDecimal(0.0));
    poinvh1.setRaxbase2(new BigDecimal(0.0));
    poinvh1.setRaxbase3(new BigDecimal(0.0));
    poinvh1.setRaxbase4(new BigDecimal(0.0));
    poinvh1.setRaxbase5(new BigDecimal(0.0));
    poinvh1.setRaxamount1(new BigDecimal(0.0));
    poinvh1.setRaxamount2(new BigDecimal(0.0));
    poinvh1.setRaxamount3(new BigDecimal(0.0));
    poinvh1.setRaxamount4(new BigDecimal(0.0));
    poinvh1.setRaxamount5(new BigDecimal(0.0));
    poinvh1.setRxrecvamt1(new BigDecimal(0.0));
    poinvh1.setRxrecvamt2(new BigDecimal(0.0));
    poinvh1.setRxrecvamt3(new BigDecimal(0.0));
    poinvh1.setRxrecvamt4(new BigDecimal(0.0));
    poinvh1.setRxrecvamt5(new BigDecimal(0.0));
    poinvh1.setRxexpsamt1(new BigDecimal(0.0));
    poinvh1.setRxexpsamt2(new BigDecimal(0.0));
    poinvh1.setRxexpsamt3(new BigDecimal(0.0));
    poinvh1.setRxexpsamt4(new BigDecimal(0.0));
    poinvh1.setRxexpsamt5(new BigDecimal(0.0));
    poinvh1.setTxalloamt1(new BigDecimal(0.0));
    poinvh1.setTxalloamt2(new BigDecimal(0.0));
    poinvh1.setTxalloamt3(new BigDecimal(0.0));
package com.example.IMS.service;

import com.example.IMS.controller.PurchasesResponse;
import com.example.IMS.dto.*;
import com.example.IMS.entity.mssql.*;
import com.example.IMS.repository.mssql.*;
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
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
            private final PorcpjRepository porcpjRepo;
            private final PorcpahRepository porcpahRepository ;
            private final PorcpalRepository porcpalRepository;
            private final PorcpnRepository porcpnRepository;
            private final Apven_repo apvenRepo;
            private final PoinvahRepository poinvahRepository;
            private final Poinvh1Repository poinvh1Repository;
            private final PoinvalRepository poinvalRepository;
            private final Poinvh2Repository poinvh2Repository;
            private final PoinvjRepository poinvjRepository;
            private final  PoinvnRepository poinvnRepository;

            public static Map<String,Integer> purchaseType =new HashMap<>();
            static {

            }
            public BigDecimal dayEndNo(){
                Poopt poopt =  pooptRepo.findByRatetype("SP");
                return poopt.getLgendayend();

            }

            public List<importItemListDTO> getImports(String refNumber) throws JsonProcessingException {

                selectImportDataDTO importDataDTO = new selectImportDataDTO();
                importDataDTO.setTpin(clientTpin);
                importDataDTO.setBhfId(branchId);
                importDataDTO.setLastReqDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
                importDataDTO.setDclRefNum(refNumber);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<selectImportDataDTO> requestEntity = new HttpEntity<>(importDataDTO, headers);
                ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/zrasandboxvsdc/imports/selectImportItems", requestEntity, String.class);
                System.out.print(response);
                String jsonResponse = "{\"resultCd\":\"000\",\"resultMsg\":\"It is succeeded\",\"resultDt\":\"20231120194118\",\"data\":{\"itemList\":[{\"taskCd\":\"2239078\",\"dclDe\":\"-1\",\"itemSeq\":1,\"dclNo\":\"C3460-2019-TZDL\",\"hsCd\":\"20055900000\",\"itemNm\":\"BAKED BEANS\",\"imptItemsttsCd\":\"2\",\"orgnNatCd\":\"BR\",\"exptNatCd\":\"BR\",\"pkg\":2922,\"pkgUnitCd\":null,\"qty\":19946,\"qtyUnitCd\":\"KGM\",\"totWt\":19945.57,\"netWt\":19945.57,\"spplrNm\":\"ODERICH CONSERVA QUALIDADE\\nBRASIL\",\"agntNm\":\"BN METRO Ltd\",\"invcFcurAmt\":296865.6,\"invcFcurCd\":\"USD\",\"invcFcurExcrt\":929.79}]}}";

                if (response.getStatusCode() == HttpStatus.OK) {
                    //String jsonResponse = response.getBody(); // Use actual response body here
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
            public BigDecimal maxID (){
                Porcph1 porcph1= porcph1Repo.findMaxId();
                return porcph1.getId().add(BigDecimal.valueOf(2));
            }
            public BigDecimal maxsqlID (){
                return porcplRepo.findByRcphseq();
            }
            private void createPurchaseOrderReceipt(JsonNode sale , String ponumber, List<postPOItemsDTO> items){

                Poporh1 poporh1 = poporh1Repo.findByPonumberContaining(ponumber);
                poporh1.setIscomplete((short) 1);
                poporh1.setDtcomplete(currentDate());
                poporh1.setLinescmpl(poporh1.getLines());
                poporh1.setRcpdate(currentDate());
                poporh1.setLastreceip(nextReceiptNo());
                poporh1.setRcpcount((short) 1);
                poporh1Repo.save(poporh1);
                Optional<Apven> apven = apvenRepo.findByVendorid(poporh1.getVdcode());
                Porcph1 maxID = porcph1Repo.findMaxId();;
                String inv =sale.path("spplrInvcNo").asText();
                BigDecimal totalQty = BigDecimal.ZERO;
                for (JsonNode node : sale.path("itemList")){
                    BigDecimal qty = node.path("qty").decimalValue();
                    totalQty = totalQty.add(qty);
                }

                List<Poporl> poporls = poporlRepo.findById(poporh1.getId());
                for (Poporl poporl :poporls){
                    Icitem icitem = icitemRepository.findByFmtitemno(poporl.getItemno());
                    poporl.setCompletion((short)3);
                    poporl.setDtcomplete(currentDate());
                    poporlRepo.save(poporl);
                    for (JsonNode node : sale.path("itemList")) {
                        String itemno = "";
                        for (int i = 0; i < items.size(); i++) {
                            postPOItemsDTO item = items.get(i);
                            String itemcode = item.getItemCd();
                            if (itemcode.equals(node.path("itemCd").asText())) {
                                itemno = item.getSageItemCode();
                            }
                        }

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
                        porcpn.setItemdesc(poporl.getItemdesc());
                        porcpn.setStockunit("");
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
                        porcpn.setTaxbase1(node.path("taxblAmt").decimalValue());
                        porcpn.setTaxbase2(new BigDecimal("0.0"));
                        porcpn.setTaxbase3(new BigDecimal("0.0"));
                        porcpn.setTaxbase4(new BigDecimal("0.0"));
                        porcpn.setTaxbase5(new BigDecimal("0.0"));
                        porcpn.setTaxinclud1((short) 0);
                        porcpn.setTaxinclud2((short) 0);
                        porcpn.setTaxinclud3((short) 0);
                        porcpn.setTaxinclud4((short) 0);
                        porcpn.setTaxinclud5((short) 0);
                        porcpn.setTxbaseallo(node.path("taxblAmt").decimalValue());
                        porcpn.setTxinclude1(new BigDecimal("0.0"));
                        porcpn.setTxinclude2(new BigDecimal("0.0"));
                        porcpn.setTxinclude3(new BigDecimal("0.0"));
                        porcpn.setTxinclude4(new BigDecimal("0.0"));
                        porcpn.setTxinclude5(new BigDecimal("0.0"));
                        porcpn.setTxrecvamt1(node.path("vatAmt").decimalValue());
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
                        porcpn.setTfbaseallo(new BigDecimal(String.valueOf(node.path("taxblAmt").decimalValue())));
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
                        porcpn.setTfrecvamt1(node.path("vatAmt").decimalValue());
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
                        porcpn.setTrrecvamt1(node.path("vatAmt").decimalValue());
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
                        porcpalId.setRcpalseq(porcpalRepository.findByRcpal());
                        Porcpal porcpal = new Porcpal();
                        porcpal.setId(porcpalId);
                        porcpal.setAudtdate(currentDate());
                        porcpal.setAudttime(currentTime());
                        porcpal.setAudtuser("ADMIN");
                        porcpal.setAudtorg(company);
                        porcpal.setTranstype((short) 11);
                        porcpal.setOeonumber("");
                        porcpal.setItemexists((short) 1);
                        porcpal.setItemno(poporl.getItemno());
                        porcpal.setLocation("HQ");
                        porcpal.setItemdesc(poporl.getDesc());
                        porcpal.setCntlacct(icitem.getCategory());
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
                        porcpal.setCostunit("");
                        porcpal.setUnitcost(node.path("prc").decimalValue());
                        porcpal.setPrunitcost(new BigDecimal("0.0"));
                        porcpal.setLoadedcost(node.path("prc").decimalValue());
                        porcpal.setRecentcost(node.path("prc").decimalValue());
                        porcpal.setFcextended(node.path("totAmt").decimalValue());
                        porcpal.setScextended(node.path("totAmt").decimalValue());
                        porcpal.setFcbaseallo(node.path("taxblAmt").decimalValue());
                        porcpal.setScbaseallo(node.path("taxblAmt").decimalValue());
                        porcpal.setFctaxallo(new BigDecimal("0.0"));
                        porcpal.setSctaxallo(new BigDecimal("0.0"));
                        porcpal.setFcprorated(new BigDecimal("0.0"));
                        porcpal.setScprorated(new BigDecimal("0.0"));
                        porcpal.setFctaxincl(new BigDecimal("0.0"));
                        porcpal.setSctaxincl(new BigDecimal("0.0"));
                        porcpal.setFctaxexcl(node.path("vatAmt").decimalValue());
                        porcpal.setSctaxexcl(node.path("vatAmt").decimalValue());
                        porcpal.setFcmprorate(new BigDecimal("0.0"));
                        porcpal.setScmprorate(new BigDecimal("0.0"));
                        porcpal.setTaxbase1(node.path("taxblAmt").decimalValue());
                        porcpal.setTaxbase2(new BigDecimal("0.0"));
                        porcpal.setTaxbase3(new BigDecimal("0.0"));
                        porcpal.setTaxbase4(new BigDecimal("0.0"));
                        porcpal.setTaxbase5(new BigDecimal("0.0"));
                        porcpal.setTaxinclud1((short) 1);
                        porcpal.setTaxinclud2((short) 0);
                        porcpal.setTaxinclud3((short) 1);
                        porcpal.setTaxinclud4((short) 0);
                        porcpal.setTaxinclud5((short) 1);
                        porcpal.setTaxamount1(node.path("vatAmt").decimalValue());
                        porcpal.setTaxamount2(new BigDecimal("0.0"));
                        porcpal.setTaxamount3(new BigDecimal("0.0"));
                        porcpal.setTaxamount4(new BigDecimal("0.0"));
                        porcpal.setTaxamount5(new BigDecimal("0.0"));
                        porcpal.setTxrecvamt1(node.path("vatAmt").decimalValue());
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
                        porcpal.setFcexttotal(node.path("taxblAmt").decimalValue());
                        porcpal.setScexttotal(node.path("taxblAmt").decimalValue());
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
                        porcpal.setValues(1);
                        porcpal.setRcplseq(maxsqlID());
                        porcpal.setFctaxrecv(node.path("vatAmt").decimalValue());
                        porcpal.setSctaxrecv(node.path("vatAmt").decimalValue());
                        porcpal.setFctaxexps(new BigDecimal("0.0"));
                        porcpal.setSctaxexps(new BigDecimal("0.0"));
                        porcpal.setFctaxamt1(node.path("vatAmt").decimalValue());
                        porcpal.setFctaxamt2(new BigDecimal("0.0"));
                        porcpal.setFctaxamt3(new BigDecimal("0.0"));
                        porcpal.setFctaxamt4(new BigDecimal("0.0"));
                        porcpal.setFctaxamt5(new BigDecimal("0.0"));
                        porcpal.setComment("");
                        porcpal.setContract("");
                        porcpal.setProject("");
                        porcpal.setCcategory("");
                        porcpal.setCostclass((short) 1);
                        porcpal.setBilltype((short) 1);
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
                        porcpal.setPmtransnum(1);
                        porcpal.setRctaxallo(new BigDecimal("0.0"));
                        porcpal.setRctaxrecv(node.path("vatAmt").decimalValue());
                        porcpal.setRctaxexps(new BigDecimal("0.0"));
                        porcpal.setRctaxincl(new BigDecimal("0.0"));
                        porcpal.setTaramount1(node.path("vatAmt").decimalValue());
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
                        porcpal.setDeltaconve(new BigDecimal("0.0"));
                        porcpal.setDeltaunit(poporl.getOrderunit());
                        porcpal.setDetailnum(node.path("itemSeq").shortValue());
                        porcpalRepository.save(porcpal);
                        // Create an instance of PorcplId
                        PorcplId porcplId = new PorcplId();
                        porcplId.setRcphseq(maxID.getId().add(BigDecimal.valueOf(2))); // Example value
                        porcplId.setRcplrev(BigDecimal.valueOf(31)); // Example value

// Create an instance of Porcpl
                        Porcpl porcpl = new Porcpl();
                        porcpl.setId(porcplId);
                        porcpl.setAudtdate(currentDate()); // Assuming currentDate() returns a BigDecimal
                        porcpl.setAudttime(currentTime()); // Assuming currentTime() returns a BigDecimal
                        porcpl.setAudtuser("ADMIN");
                        porcpl.setAudtorg(company);
                        porcpl.setRcplseq(maxsqlID()); // Example value
                        porcpl.setRcpcseq(maxsqlID().add(new BigDecimal("2"))); // Example value
                        porcpl.setOeonumber(""); // Set as needed
                        porcpl.setIndbtable((short) 1);
                        porcpl.setPostedtoic((short) 1);
                        porcpl.setCompletion((short) 1);
                        porcpl.setDtcomplete(currentDate()); // Assuming currentDate() returns a BigDecimal
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
                        porcpl.setExtended(node.path("taxblAmt").decimalValue());
                        porcpl.setTaxbase1(node.path("taxblAmt").decimalValue());
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
                        porcpl.setTaxamount1(node.path("vatAmt").decimalValue());
                        porcpl.setTaxamount2(new BigDecimal(0));
                        porcpl.setTaxamount3(new BigDecimal(0));
                        porcpl.setTaxamount4(new BigDecimal(0));
                        porcpl.setTaxamount5(new BigDecimal(0));
                        porcpl.setTxrecvamt1(node.path("vatAmt").decimalValue());
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
                        porcpl.setTxbaseallo(node.path("taxblAmt").decimalValue());
                        porcpl.setTxincluded(new BigDecimal(0));
                        porcpl.setTxexcluded(node.path("vatAmt").decimalValue());
                        porcpl.setTaxamount(node.path("vatAmt").decimalValue());
                        porcpl.setTxrecvamt(node.path("vatAmt").decimalValue());
                        porcpl.setTxexpsamt(new BigDecimal(0));
                        porcpl.setTxalloamt(new BigDecimal(0));
                        porcpl.setTfbaseallo(node.path("taxblAmt").decimalValue());
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
                        porcpl.setTfrecvamt1(node.path("vatAmt").decimalValue());
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
                        porcpl.setXinetxtend(node.path("taxblAmt").decimalValue());
                        porcpl.setInvlines(1);
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
                        porcpl.setTaramount1(node.path("vatAmt").decimalValue());
                        porcpl.setTaramount2(new BigDecimal("0.000"));
                        porcpl.setTaramount3(new BigDecimal("0.000"));
                        porcpl.setTaramount4(new BigDecimal("0.000"));
                        porcpl.setTaramount5(new BigDecimal("0.000"));
                        porcpl.setTralloamt1(new BigDecimal("0.000"));
                        porcpl.setTralloamt2(new BigDecimal("0.000"));
                        porcpl.setTralloamt3(new BigDecimal("0.000"));
                        porcpl.setTralloamt4(new BigDecimal("0.000"));
                        porcpl.setTralloamt5(new BigDecimal("0.000"));
                        porcpl.setTrrecvamt1(node.path("vatAmt").decimalValue());
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

// Save the instance
                        porcplRepo.save(porcpl);
                    }
                }

                BigDecimal porcpjSeq = porcpjRepo.findMaxProrateSeq();
                Porcpj porcpj = new Porcpj();
                porcpj.setId(maxID.getId().add(BigDecimal.valueOf(2)));
                porcpj.setAudtdate(currentDate());
                porcpj.setAudttime(currentTime());
                porcpj.setAudtuser("ADMIN");
                porcpj.setAudtorg(company);
                porcpj.setProrateseq(porcpjSeq.add(new BigDecimal("1")));
                porcpj.setPostdate(currentDate());
                porcpj.setScamount(sale.path("totAmt").decimalValue());
                porcpj.setFcamount(sale.path("totAmt").decimalValue());
                porcpj.setScdoctotal(sale.path("totAmt").decimalValue());
                porcpj.setIscomplete((short) 1);
                porcpj.setDtcomplete(currentDate());
                porcpj.setScrtgamt(new BigDecimal("0.0"));
                porcpj.setCaxamount(new BigDecimal("0.0"));
                porcpjRepo.save(porcpj);

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
                porcpah.setRcphseq(new BigDecimal(1));
                porcpah.setPostdate(currentDate());
                porcpah.setDayenddate(new BigDecimal(20240908));
                porcpah.setTransdate(currentDate());
                porcpah.setReference(poporh1.getReference());
                porcpah.setFiscyear("2024");
                porcpah.setFiscperiod((short) 9);
                porcpah.setDescriptio(poporh1.getDescriptio());
                porcpah.setTranstype((short) 1);
                porcpah.setVendor(poporh1.getVdcode());
                porcpah.setVendorname(poporh1.getVdname());
                porcpah.setTaxgroup(apven.get().getCodetaxgrp());
                porcpah.setTaxauth1("");
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
                porcpah.setRatedaterc(new BigDecimal(20240908));
                porcpah.setRatetyperc("SP");
                porcpah.setRateoperrc((short) 1);
                porcpah.setRatercover((short) 0);
                porcpah.setRcurndecml((short) 2);
                porcpah.setDatebus(new BigDecimal(20240908));
                porcpah.setVdacctset(apven.get().getIdacctset());
                porcpah.setCaxamount(new BigDecimal("0.0"));
                porcpahRepository.save(porcpah);

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
                        .isinvoiced((short)1)
                        .iscomplete((short)1)
                        .dtcomplete(currentDate())
                        .postdate(currentDate())
                        .date(currentDate())
                        .fiscyear(maxID.getFiscyear())
                        .fiscperiod(maxID.getFiscperiod())
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
                        .extended(sale.path("totTaxblAmt").decimalValue())
                        .doctotal(sale.path("totAmt").decimalValue())
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
                        .taxbase1(sale.path("taxablAmt").decimalValue())
                        .taxbase2(new BigDecimal("0"))
                        .taxbase3(new BigDecimal("0"))
                        .taxbase4(new BigDecimal("0"))
                        .taxbase5(new BigDecimal("0"))
                        .txinclude1(new BigDecimal("0"))
                        .txinclude2(new BigDecimal("0"))
                        .txinclude3(new BigDecimal("0"))
                        .txinclude4(new BigDecimal("0"))
                        .txinclude5(new BigDecimal("0"))
                        .txexclude1(sale.path("vatAmt").decimalValue())
                        .txexclude2(new BigDecimal("0"))
                        .txexclude3(new BigDecimal("0"))
                        .txexclude4(new BigDecimal("0"))
                        .txexclude5(new BigDecimal("0"))
                        .taxamount1(sale.path("vatAmt").decimalValue())
                        .taxamount2(new BigDecimal("0"))
                        .taxamount3(new BigDecimal("0"))
                        .taxamount4(new BigDecimal("0"))
                        .taxamount5(new BigDecimal("0"))
                        .txrecvamt1(sale.path("vatAmt").decimalValue())
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
                        .txbaseallo(sale.path("taxblAmt").decimalValue())
                        .txincluded(new BigDecimal("0.00"))
                        .txexcluded(sale.path("vatAmt").decimalValue())
                        .taxamount(sale.path("vatAmt").decimalValue())
                        .txrecvamt(sale.path("vatAmt").decimalValue())
                        .txexpsamt(new BigDecimal("0.00"))
                        .txalloamt(new BigDecimal("0.00"))
                        .mprorated(new BigDecimal("0.00"))
                        .mtoprorate(new BigDecimal("0.00"))
                        .scamount(sale.path("totAmt").decimalValue())
                        .fcamount(sale.path("totAmt").decimalValue())
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
                        .taramount1(sale.path("vatAmt").decimalValue())
                        .taramount2(new BigDecimal("0.00"))
                        .taramount3(new BigDecimal("0.00"))
                        .taramount4(new BigDecimal("0.00"))
                        .taramount5(new BigDecimal("0.00"))
                        .trinclude1(new BigDecimal("0.00"))
                        .trinclude2(new BigDecimal("0.00"))
                        .trinclude3(new BigDecimal("0.00"))
                        .trinclude4(new BigDecimal("0.00"))
                        .trinclude5(new BigDecimal("0.00"))
                        .trexclude1(sale.path("vatAmt").decimalValue())
                        .trexclude2(new BigDecimal("0.00"))
                        .trexclude3(new BigDecimal("0.00"))
                        .trexclude4(new BigDecimal("0.00"))
                        .trexclude5(new BigDecimal("0.00"))
                        .trrecvamt1(sale.path("vatAmt").decimalValue())
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
                        .id(maxID.getId().add(BigDecimal.valueOf(1)))
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





            }
            public void postPoToSage(int invnumber, String po_number, List<postPOItemsDTO> items) throws JsonProcessingException {

                String url = baseUrl + "/trnsPurchase/selectTrnsPurchaseSales";
                selectSales selectSales = new selectSales();
                selectSales.setTpin(clientTpin);
                selectSales.setBhfId(branchId);
                selectSales.setLastReqDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
                Poporh1 poporh1 = poporh1Repo.findByPonumberContaining(po_number);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<selectSales> requestEntity = new HttpEntity<>(selectSales, headers);

                ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    String jsonResponse = "{\"resultCd\":\"000\",\"resultMsg\":\"It is succeeded\",\"resultDt\":\"20240510103403\",\"data\":{\"saleList\":[{\"spplrTpin\":\"1019249900                    \",\"spplrNm\":\"SMART SUPPLIER\",\"spplrBhfId\":\"000\",\"spplrInvcNo\":45,\"rcptTyCd\":\"S\",\"pmtTyCd\":\"01\",\"cfmDt\":\"2024-05-08 10:20:10\",\"salesDt\":\"20240502\",\"stockRlsDt\":null,\"totItemCnt\":1,\"totTaxblAmt\":86.2069,\"totTaxAmt\":13.7931,\"totAmt\":100,\"remark\":null,\"itemList\":[{\"itemSeq\":1,\"itemCd\":\"20044\",\"itemClsCd\":\"50102517\",\"itemNm\":\"ChickenWings\",\"bcd\":null,\"pkgUnitCd\":\"BA\",\"pkg\":0,\"qtyUnitCd\":\"BE\",\"qty\":1,\"prc\":100,\"splyAmt\":100,\"dcRt\":0,\"dcAmt\":0,\"vatCatCd\":\"A\",\"iplCatCd\":null,\"tlCatCd\":null,\"exciseTxCatCd\":null,\"vatTaxblAmt\":86.21,\"exciseTaxblAmt\":0,\"iplTaxblAmt\":0,\"tlTaxblAmt\":0,\"taxblAmt\":86.21,\"vatAmt\":13.79,\"iplAmt\":0,\"tlAmt\":0,\"exciseTxAmt\":0,\"totAmt\":100}]},{\"spplrTpin\":\"1019249900                    \",\"spplrNm\":\"SMART SUPPLIER\",\"spplrBhfId\":\"000\",\"spplrInvcNo\":46,\"rcptTyCd\":\"S\",\"pmtTyCd\":\"01\",\"cfmDt\":\"2024-05-08 10:20:10\",\"salesDt\":\"20240502\",\"stockRlsDt\":null,\"totItemCnt\":1,\"totTaxblAmt\":86.2069,\"totTaxAmt\":13.7931,\"totAmt\":100,\"remark\":null,\"itemList\":[{\"itemSeq\":1,\"itemCd\":\"20044\",\"itemClsCd\":\"50102517\",\"itemNm\":\"ChickenWings\",\"bcd\":null,\"pkgUnitCd\":\"BA\",\"pkg\":0,\"qtyUnitCd\":\"BE\",\"qty\":1,\"prc\":100,\"splyAmt\":100,\"dcRt\":0,\"dcAmt\":0,\"vatCatCd\":\"A\",\"iplCatCd\":null,\"tlCatCd\":null,\"exciseTxCatCd\":null,\"vatTaxblAmt\":86.21,\"exciseTaxblAmt\":0,\"iplTaxblAmt\":0,\"tlTaxblAmt\":0,\"taxblAmt\":86.21,\"vatAmt\":13.79,\"iplAmt\":0,\"tlAmt\":0,\"exciseTxAmt\":0,\"totAmt\":100}]}]}}";

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(jsonResponse);
                    JsonNode saleList = rootNode.path("data").path("saleList");

                    if(saleList != null) {
                        for (JsonNode sale : saleList) {
                            int invoiceNumber = sale.path("spplrInvcNo").asInt();
                            if (invoiceNumber == invnumber) {
                                createPOInvoice(sale, po_number, items); // Now includes the other objects
                            }
                        }
                    } else {
                        return;
                    }
                }
            }


            public void createPOInvoice(JsonNode sale, String ponumber,List<postPOItemsDTO>items){
                Porcph1 maxID = porcph1Repo.findMaxId();
                Poporh1 poporh1 = poporh1Repo.findByPonumberContaining(ponumber);
                Optional<Apven> apven = apvenRepo.findByVendorid(poporh1.getVdcode());

                //Instance for Poinvh1
                Poinvh1 poinvh1Instance = poinvh1Repository.findMaxId();
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
                poinvh1.setPayments(1);
                poinvh1.setTaxlines(3);
                poinvh1.setTaxautocal((short) 1);
                poinvh1.setIscomplete((short) 1);
                poinvh1.setDtcomplete(currentDate());
                poinvh1.setPostdate(currentDate());
                poinvh1.setPorhseq(poporh1.getId());
                poinvh1.setPonumber(poporh1.getPonumber());
                poinvh1.setDate(currentDate());
                poinvh1.setFiscyear("2024");
                poinvh1.setFiscperiod((short) 5);
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
                poinvh1.setRetdate(currentDate());
                poinvh1.setDescriptio(sale.path("remarks").asText());
                poinvh1.setReference(sale.path("").asText());
                poinvh1.setComment("");
                poinvh1.setCurrency(poporh1.getCurrency());
                poinvh1.setRate(new BigDecimal(1.0));
                poinvh1.setSpread(new BigDecimal(0.0));
                poinvh1.setRatetype("SP");
                poinvh1.setRatematch((short) 1);
                poinvh1.setRatedate(currentDate());
                poinvh1.setRateoper((short) 1);
                poinvh1.setRateover((short) 0);
                poinvh1.setScurndecml((short) 2);
                poinvh1.setDtpymtasof(currentDate());
                poinvh1.setTermdueamt(new BigDecimal(0.0));
                poinvh1.setExtweight(new BigDecimal(0.0));
                poinvh1.setExtended(new BigDecimal(0.0));
                poinvh1.setDoctotal(new BigDecimal(0.0));
                poinvh1.setAmount(new BigDecimal(0.0));
                poinvh1.setRqreceived(new BigDecimal(0.0));
                poinvh1.setTaxgroup("VATZMW01");
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
                poinvh1.setTaxbase1(new BigDecimal(0.0));
                poinvh1.setTaxbase2(new BigDecimal(0.0));
                poinvh1.setTaxbase3(new BigDecimal(0.0));
                poinvh1.setTaxbase4(new BigDecimal(0.0));
                poinvh1.setTaxbase5(new BigDecimal(0.0));
                poinvh1.setTxinclude1(new BigDecimal(0.0));
                poinvh1.setTxinclude2(new BigDecimal(0.0));
                poinvh1.setTxinclude3(new BigDecimal(0.0));
                poinvh1.setTxinclude4(new BigDecimal(0.0));
                poinvh1.setTxinclude5(new BigDecimal(0.0));
                poinvh1.setTxexclude1(new BigDecimal(0.0));
                poinvh1.setTxexclude2(new BigDecimal(0.0));
                poinvh1.setTxexclude3(new BigDecimal(0.0));
                poinvh1.setTxexclude4(new BigDecimal(0.0));
                poinvh1.setTxexclude5(new BigDecimal(0.0));
                poinvh1.setTaxamount1(new BigDecimal(0.0));
                poinvh1.setTaxamount2(new BigDecimal(0.0));
                poinvh1.setTaxamount3(new BigDecimal(0.0));
                poinvh1.setTaxamount4(new BigDecimal(0.0));
                poinvh1.setTaxamount5(new BigDecimal(0.0));
                poinvh1.setTxrecvamt1(new BigDecimal(0.0));
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
                poinvh1.setTxbaseallo(new BigDecimal(0.0));
                poinvh1.setTxincluded(new BigDecimal(0.0));
                poinvh1.setTxexcluded(new BigDecimal(0.0));
                poinvh1.setTaxamount(new BigDecimal(0.0));
                poinvh1.setTxrecvamt(new BigDecimal(0.0));
                poinvh1.setTxexpsamt(new BigDecimal(0.0));
                poinvh1.setTxalloamt(new BigDecimal(0.0));
                poinvh1.setF1099class("");
                poinvh1.setF1099amt(new BigDecimal(0.0));
                poinvh1.setMprorated(new BigDecimal(0.0));
                poinvh1.setMtoprorate(new BigDecimal(0.0));
                poinvh1.setMexpensed(new BigDecimal(0.0));
                poinvh1.setScamount(new BigDecimal(0.0));
                poinvh1.setFcamount(new BigDecimal(0.0));
                poinvh1.setMultircp((short) 0);
                poinvh1.setRcps(0);
                poinvh1.setVdemail("");
                poinvh1.setVdphonec("");
                poinvh1.setVdfaxc("");
                poinvh1.setVdemailc("");
                poinvh1.setDiscpct(new BigDecimal(0.0));
                poinvh1.setDiscount(new BigDecimal(0.0));
                poinvh1.setValues(0);
                poinvh1.setOnhold((short) 0);
                poinvh1.setTermdbwt(new BigDecimal(0.0));
                poinvh1.setTermdbnt(new BigDecimal(0.0));
                poinvh1.setVerprorate((short) 0);
                poinvh1.setHasrtg((short) 0);
                poinvh1.setRtgrate((short) 0);
                poinvh1.setRtgbase((short) 0);
                poinvh1.setRtgterms("");
                poinvh1.setRtgamount(new BigDecimal(0.0));
                poinvh1.setJoblines(0);
                poinvh1.setJobcosts(0);
                poinvh1.setTrcurrency("ZMW");
                poinvh1.setRaterc(new BigDecimal(0.0));
                poinvh1.setSpreadrc(new BigDecimal(0.0));
                poinvh1.setRatetyperc("SP");
                poinvh1.setRatemtchrc((short) 1);
                poinvh1.setRatedaterc(currentDate());
                poinvh1.setRateoperrc((short)1);
                poinvh1.setRatercover((short)0);
                poinvh1.setRcurndecml((short)2);
                poinvh1.setTaramount1(new BigDecimal(0.0));
                poinvh1.setTaramount2(new BigDecimal(0.0));
                poinvh1.setTaramount3(new BigDecimal(0.0));
                poinvh1.setTaramount4(new BigDecimal(0.0));
                poinvh1.setTaramount5(new BigDecimal(0.0));
                poinvh1.setTrinclude1(new BigDecimal(0.0));
                poinvh1.setTrinclude2(new BigDecimal(0.0));
                poinvh1.setTrinclude3(new BigDecimal(0.0));
                poinvh1.setTrinclude4(new BigDecimal(0.0));
                poinvh1.setTrinclude5(new BigDecimal(0.0));
                poinvh1.setTrexclude1(new BigDecimal(0.0));
                poinvh1.setTrexclude2(new BigDecimal(0.0));
                poinvh1.setTrexclude3(new BigDecimal(0.0));
                poinvh1.setTrexclude4(new BigDecimal(0.0));
                poinvh1.setTrexclude5(new BigDecimal(0.0));
                poinvh1.setTrrecvamt1(new BigDecimal(0.0));
                poinvh1.setTrrecvamt2(new BigDecimal(0.0));
                poinvh1.setTrrecvamt3(new BigDecimal(0.0));
                poinvh1.setTrrecvamt4(new BigDecimal(0.0));
                poinvh1.setTrrecvamt5(new BigDecimal(0.0));
                poinvh1.setTrexpsamt1(new BigDecimal(0.0));
                poinvh1.setTrexpsamt2(new BigDecimal(0.0));
                poinvh1.setTrexpsamt3(new BigDecimal(0.0));
                poinvh1.setTrexpsamt4(new BigDecimal(0.0));
                poinvh1.setTrexpsamt5(new BigDecimal(0.0));
                poinvh1.setTralloamt1(new BigDecimal(0.0));
                poinvh1.setTralloamt2(new BigDecimal(0.0));
                poinvh1.setTralloamt3(new BigDecimal(0.0));
                poinvh1.setTralloamt4(new BigDecimal(0.0));
                poinvh1.setTralloamt5(new BigDecimal(0.0));
                poinvh1.setRtgtaxrep(new BigDecimal(0));
                poinvh1.setTaxversion(new BigDecimal(2));
                poinvh1.setRaxbase1(new BigDecimal(0.0));
                poinvh1.setRaxbase2(new BigDecimal(0.0));
                poinvh1.setRaxbase3(new BigDecimal(0.0));
                poinvh1.setRaxbase4(new BigDecimal(0.0));
                poinvh1.setRaxbase5(new BigDecimal(0.0));
                poinvh1.setRaxamount1(new BigDecimal(0.0));
                poinvh1.setRaxamount2(new BigDecimal(0.0));
                poinvh1.setRaxamount3(new BigDecimal(0.0));
                poinvh1.setRaxamount4(new BigDecimal(0.0));
                poinvh1.setRaxamount5(new BigDecimal(0.0));
                poinvh1.setRxrecvamt1(new BigDecimal(0.0));
                poinvh1.setRxrecvamt2(new BigDecimal(0.0));
                poinvh1.setRxrecvamt3(new BigDecimal(0.0));
                poinvh1.setRxrecvamt4(new BigDecimal(0.0));
                poinvh1.setRxrecvamt5(new BigDecimal(0.0));
                poinvh1.setRxexpsamt1(new BigDecimal(0.0));
                poinvh1.setRxexpsamt2(new BigDecimal(0.0));
                poinvh1.setRxexpsamt3(new BigDecimal(0.0));
                poinvh1.setRxexpsamt4(new BigDecimal(0.0));
                poinvh1.setRxexpsamt5(new BigDecimal(0.0));
                poinvh1.setTxalloamt1(new BigDecimal(0.0));
                poinvh1.setTxalloamt2(new BigDecimal(0.0));
                poinvh1.setTxalloamt3(new BigDecimal(0.0));
                poinvh1.setTxalloamt4(new BigDecimal(0.0));
                poinvh1.setTxalloamt5(new BigDecimal(0.0));

                //Save the instance
                poinvh1Repository.save(poinvh1Instance);

                // Print to verify values (optional)
                System.out.println(poinvh1);


// Instantiate the Poinvah object
                // Create the composite key object
                PoinvahId id = new PoinvahId();
                Poinvah poinvah = new Poinvah();
                // Set properties on the Poinvah object
                poinvah.setId(id);
                poinvah.setAudtdate(currentDate());
                poinvah.setAudttime(currentTime());
                poinvah.setAudtuser("ADMIN");
                poinvah.setAudtorg(company);
                poinvah.setIsprinted((short) 0);
                poinvah.setInvhseq(new BigDecimal(1));
                poinvah.setPostdate(currentDate());
                poinvah.setDayenddate(new BigDecimal(20240909));
                poinvah.setTransdate(currentDate());
                poinvah.setReference(poporh1.getReference());
                poinvah.setFiscyear("2024");
                poinvah.setFiscperiod((short) 5);
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
                poinvah.setPgmver("69A");
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
                poinvah.setRatedaterc(new BigDecimal("2024-09-10"));
                poinvah.setRatetyperc("SP");
                poinvah.setRateoperrc((short) 1);
                poinvah.setRatercover((short) 0);
                poinvah.setRcurndecml((short) 2);
                poinvah.setDatebus(new BigDecimal("2024-09-10"));
                poinvah.setVdacctset(apven.get().getIdacctset());
                poinvah.setIdn("0");
                poinvah.setCaxamount(new BigDecimal("0.0"));

                //Save the instance
                poinvahRepository.save(poinvah);

                // Print to verify values (optional)
                System.out.println(poinvah);


//Instance for Poinvh2
                Poinvh2 poinvh2 = new Poinvh2();

                poinvh2.setId(maxID.getId().add(BigDecimal.valueOf(1)));
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

                poinvh2.setPdrate(new BigDecimal("1.0"));
                poinvh2.setPdratetype("SP");
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

                poinvh2.setPdraterc(new BigDecimal("1.0"));
                poinvh2.setPdrattyprc("SP");
                poinvh2.setPdratedtrc(currentDate());
                poinvh2.setPdrateoprc((short) 1);
                poinvh2.setPdratercov((short) 0);

                poinvh2.setVdacctset("ZMW");
                poinvh2.setDatebus(currentDate());
                poinvh2.setEnteredby("ADMIN");
                poinvh2.setDetailnext((short) 2);
                poinvh2.setIdn("0");

                poinvh2.setCaxbase1(new BigDecimal("0.0"));
                poinvh2.setCaxbase2(new BigDecimal("0.0"));
                poinvh2.setCaxbase3(new BigDecimal("0.0"));
                poinvh2.setCaxbase4(new BigDecimal("0.0"));
                poinvh2.setCaxbase5(new BigDecimal("0.0"));

                poinvh2.setCaxdtamt1(new BigDecimal("0"));
                poinvh2.setCaxdtamt2(new BigDecimal("0"));
                poinvh2.setCaxdtamt3(new BigDecimal("0"));
                poinvh2.setCaxdtamt4(new BigDecimal("0"));
                poinvh2.setCaxdtamt5(new BigDecimal("0"));

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

                //Save the instance
                poinvh2Repository.save(poinvh2);

                // Print to verify values (optional)
                System.out.println(poinvh2);

            }
            //Select purchases
            @Retryable( backoff = @Backoff(delay = 2000))
            public PurchasesResponse selectPurchase() {
                String url = baseUrl + "/trnsPurchase/selectTrnsPurchaseSales";
                selectSales selectSales = new selectSales();
                selectSales.setTpin(clientTpin);
                selectSales.setBhfId(branchId);
                selectSales.setLastReqDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<selectSales> requestEntity = new HttpEntity<>(selectSales, headers);

                ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
                String jsonResponse = "{\"resultCd\":\"000\",\"resultMsg\":\"It is succeeded\",\"resultDt\":\"20240510103403\",\"data\":{\"saleList\":[{\"spplrTpin\":\"1001733250                    \",\"spplrNm\":\"CFAO MOTORS (ZAMBIA) LIMITED                                \",\"spplrBhfId\":\"000\",\"spplrInvcNo\":45,\"rcptTyCd\":\"S\",\"pmtTyCd\":\"01\",\"cfmDt\":\"2024-05-08 10:20:10\",\"salesDt\":\"20240502\",\"stockRlsDt\":null,\"totItemCnt\":1,\"totTaxblAmt\":86.2069,\"totTaxAmt\":13.7931,\"totAmt\":100,\"remark\":null,\"itemList\":[{\"itemSeq\":1,\"itemCd\":\"20044\",\"itemClsCd\":\"50102517\",\"itemNm\":\"ChickenWings\",\"bcd\":null,\"pkgUnitCd\":\"BA\",\"pkg\":0,\"qtyUnitCd\":\"BE\",\"qty\":1,\"prc\":100,\"splyAmt\":100,\"dcRt\":0,\"dcAmt\":0,\"vatCatCd\":\"A\",\"iplCatCd\":null,\"tlCatCd\":null,\"exciseTxCatCd\":null,\"vatTaxblAmt\":86.21,\"exciseTaxblAmt\":0,\"iplTaxblAmt\":0,\"tlTaxblAmt\":0,\"taxblAmt\":86.21,\"vatAmt\":13.79,\"iplAmt\":0,\"tlAmt\":0,\"exciseTxAmt\":0,\"totAmt\":100}]},{\"spplrTpin\":\"1019249900                    \",\"spplrNm\":\"SMART SUPPLIER\",\"spplrBhfId\":\"000\",\"spplrInvcNo\":46,\"rcptTyCd\":\"S\",\"pmtTyCd\":\"01\",\"cfmDt\":\"2024-05-08 10:20:10\",\"salesDt\":\"20240502\",\"stockRlsDt\":null,\"totItemCnt\":1,\"totTaxblAmt\":86.2069,\"totTaxAmt\":13.7931,\"totAmt\":100,\"remark\":null,\"itemList\":[{\"itemSeq\":1,\"itemCd\":\"20044\",\"itemClsCd\":\"50102517\",\"itemNm\":\"ChickenWings\",\"bcd\":null,\"pkgUnitCd\":\"BA\",\"pkg\":0,\"qtyUnitCd\":\"BE\",\"qty\":1,\"prc\":100,\"splyAmt\":100,\"dcRt\":0,\"dcAmt\":0,\"vatCatCd\":\"A\",\"iplCatCd\":null,\"tlCatCd\":null,\"exciseTxCatCd\":null,\"vatTaxblAmt\":86.21,\"exciseTaxblAmt\":0,\"iplTaxblAmt\":0,\"tlTaxblAmt\":0,\"taxblAmt\":86.21,\"vatAmt\":13.79,\"iplAmt\":0,\"tlAmt\":0,\"exciseTxAmt\":0,\"totAmt\":100},{\"itemSeq\":1,\"itemCd\":\"20044\",\"itemClsCd\":\"50102517\",\"itemNm\":\"ChickenWings\",\"bcd\":null,\"pkgUnitCd\":\"BA\",\"pkg\":0,\"qtyUnitCd\":\"BE\",\"qty\":1,\"prc\":100,\"splyAmt\":100,\"dcRt\":0,\"dcAmt\":0,\"vatCatCd\":\"A\",\"iplCatCd\":null,\"tlCatCd\":null,\"exciseTxCatCd\":null,\"vatTaxblAmt\":86.21,\"exciseTaxblAmt\":0,\"iplTaxblAmt\":0,\"tlTaxblAmt\":0,\"taxblAmt\":86.21,\"vatAmt\":13.79,\"iplAmt\":0,\"tlAmt\":0,\"exciseTxAmt\":0,\"totAmt\":100}]}]}}";
                if (response.getStatusCode() == HttpStatus.OK) {
                    //String jsonResponse = response.getBody(); // Use actual response body here
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.getString("resultCd").equals("000")) {
                        JSONArray saleList = jsonObject.getJSONObject("data").getJSONArray("saleList");
                        List<selectPurchasesDTO> invoiceDTOList = new ArrayList<>();
                        List<supplierPurchaseOrderDTO> supplierPurchaseOrderDTOS = new ArrayList<>();

                        for (int i = 0; i < saleList.length(); i++) {
                            JSONObject sale = saleList.getJSONObject(i);
                            selectPurchasesDTO invoiceDTO = new selectPurchasesDTO();
                            String vendor = getVendorFromTpin(sale.getString("spplrTpin"),sale.getString("spplrNm"));

                            List<Poporh1> poporh1s = poporh1Repo.findByVdcodeAndIscomplete(vendor, (short) 0);
                            for (Poporh1 poporh1 : poporh1s) {
                                supplierPurchaseOrderDTO purchaseOrderDTO = new supplierPurchaseOrderDTO();
                                List<supplierPurchaseOrderItems> supplierPurchaseOrderItems = new ArrayList<>();
                                List<Poporl> poporls = poporlRepo.findById(poporh1.getId());

                                purchaseOrderDTO.setPoNumber(poporh1.getPonumber());
                                purchaseOrderDTO.setVendorName(poporh1.getVdname());
                                purchaseOrderDTO.setVendorCode(poporh1.getVdcode());
                                purchaseOrderDTO.setTpin(sale.getString("spplrTpin"));
                                purchaseOrderDTO.setTaxableAmount(poporh1.getTaxbase1());
                                purchaseOrderDTO.setTax(poporh1.getTaxamount1());
                                purchaseOrderDTO.setTotalAmount(poporh1.getExtended());

                                for (Poporl poporl : poporls) {
                                    supplierPurchaseOrderItems purchaseOrderItem = new supplierPurchaseOrderItems();
                                    purchaseOrderItem.setItemCode(poporl.getItemno());
                                    purchaseOrderItem.setItemName(poporl.getItemdesc());
                                    purchaseOrderItem.setPrice(poporl.getUnitcost());
                                    purchaseOrderItem.setTaxableAmount(poporl.getTaxbase1());
                                    purchaseOrderItem.setTaxAmount(poporl.getTaxamount1());
                                    purchaseOrderItem.setTotalAmount(poporl.getExtended());
                                    purchaseOrderItem.setQuantity(poporl.getOqordered());
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


            public String nextReceiptNo(){
                Poopt pt = pooptRepo.findByRatetype("SP");
                return pt.getRcpprefixd().replace(" ","") + pt.getRcpbodyd().replace(" ","");
            }
        }


    //Save the instance
         poinvh1Repository.save(poinvh1Instance);


// Instantiate the Poinvah object
        // Create the composite key object
        PoinvahId id = new PoinvahId();
        Poinvah poinvah = new Poinvah();
        // Set properties on the Poinvah object
        poinvah.setId(id);
        poinvah.setAudtdate(currentDate());
        poinvah.setAudttime(currentTime());
        poinvah.setAudtuser("ADMIN");
        poinvah.setAudtorg("company");
        poinvah.setIsprinted((short) 0);
        poinvah.setInvhseq(new BigDecimal(1));
        poinvah.setPostdate(currentDate());
        poinvah.setDayenddate(new BigDecimal(20240909));
        poinvah.setTransdate(currentDate());
        poinvah.setReference(poporh1.getReference());
        poinvah.setFiscyear("2024");
        poinvah.setFiscperiod((short) 5);
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
        poinvah.setPgmver("69A");
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
        poinvah.setRatedaterc(new BigDecimal("2024-09-10"));
        poinvah.setRatetyperc("SP");
        poinvah.setRateoperrc((short) 1);
        poinvah.setRatercover((short) 0);
        poinvah.setRcurndecml((short) 2);
        poinvah.setDatebus(new BigDecimal("2024-09-10"));
        poinvah.setVdacctset(apven.get().getIdacctset());
        poinvah.setIdn("0");
        poinvah.setCaxamount(new BigDecimal("0.0"));

        //Save the instance
        poinvahRepository.save(poinvah);

        // Print to verify values (optional)
        System.out.println(poinvah);


//Instance for Poinvh2
        Poinvh2 poinvh2 = new Poinvh2();

        poinvh2.setId(maxID.getId().add(BigDecimal.valueOf(1)));
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

        poinvh2.setPdrate(new BigDecimal("1.0"));
        poinvh2.setPdratetype("SP");
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

        poinvh2.setPdraterc(new BigDecimal("1.0"));
        poinvh2.setPdrattyprc("SP");
        poinvh2.setPdratedtrc(currentDate());
        poinvh2.setPdrateoprc((short) 1);
        poinvh2.setPdratercov((short) 0);

        poinvh2.setVdacctset("ZMW");
        poinvh2.setDatebus(currentDate());
        poinvh2.setEnteredby("ADMIN");
        poinvh2.setDetailnext((short) 2);
        poinvh2.setIdn("0");

        poinvh2.setCaxbase1(new BigDecimal("0.0"));
        poinvh2.setCaxbase2(new BigDecimal("0.0"));
        poinvh2.setCaxbase3(new BigDecimal("0.0"));
        poinvh2.setCaxbase4(new BigDecimal("0.0"));
        poinvh2.setCaxbase5(new BigDecimal("0.0"));

        poinvh2.setCaxdtamt1(new BigDecimal("0"));
        poinvh2.setCaxdtamt2(new BigDecimal("0"));
        poinvh2.setCaxdtamt3(new BigDecimal("0"));
        poinvh2.setCaxdtamt4(new BigDecimal("0"));
        poinvh2.setCaxdtamt5(new BigDecimal("0"));

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

        //Save the instance
        poinvh2Repository.save(poinvh2);

        // Print to verify values (optional)
        System.out.println(poinvh2);



        // Create an instance of PoinvalId
        PoinvalId poinvalId = new PoinvalId();
// Set values for the PoinvalId fields
        poinvalId.setField1("value1");
        poinvalId.setField2("value2");

// Create an instance of Poinval
        Poinval poinval = new Poinval();
        poinval.setId(poinvalId);
        poinvalId.setDayendseq(dayEndNo());
        poinval.setInvahseq((short) 1);
        poinval.setinvalseq(new BigDecimal("0"));
        poinval.setAudtdate(currentDate());
        poinval.setAudttime(currentTime());
        poinval.setAudtuser("ADMIN");
        poinval.setAudtorg(company);
        poinval.setTranstype((short) 11);
        poinval.setOeonumber("oeo12345678901234567890");
        poinval.setItemexists((short) 1);
        poinval.setItemno("");
        poinval.setLocation("HQ");
        poinval.setItemdesc(poporl.getDesc());
        poinval.setCntlacct(icitem.getCategory());
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
        poinval.setRcpnumber("rcp12345678901234567890");
        poinval.setDiscpct(new BigDecimal("0.12345"));
        poinval.setFcdiscount(new BigDecimal("12.345"));
        poinval.setScdiscount(new BigDecimal("12.345"));
        poinval.setFcdisctot(new BigDecimal("12.345"));
        poinval.setScdisctot(new BigDecimal("12.345"));
        poinval.setValues(12345);
        poinval.setTermdiscbl((short) 1);
        poinval.setRcplseq(new BigDecimal("0.0"));
        poinval.setInvlseq(new BigDecimal("0.0"));
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
        poinval.setDfcunitcst("prc").decimalValue());
        poinval.setDscunitcst("prc").decimalValue());
        poinval.setDbillrate(new BigDecimal("0.0"));
        poinval.setPmtransnum(0);
        poinval.setRctaxallo(new BigDecimal("0.0"));
        poinval.setRctaxrecv(node.path("vatAmt").decimalValue());
        poinval.setRctaxexps(new BigDecimal("0.0"));
        poinval.setRctaxincl(new BigDecimal("0.0"));
        poinval.setTaramount1(node.path("vatAmt").decimalValue());
        poincal.setTaramount2(new BigDecimal("0.0"));
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

        // Print to verify values (optional)
        System.out.println(poinval);



        // Instantiate the Poinvj object
        Poinvj poinvj = new Poinvj();

        // Set the properties
        BigDecimal poinvjSeq = poinvjRepo.findMaxProrateSeq();
        poinvj.setId(maxID.getId().add(BigDecimal.valueOf(2)));
        poinvj.setAudtdate(currentDate());
        poinvj.setAudttime(currentTime());
        poinvj.setAudtuser("ADMIN");
        poinvj.setAudtorg(company);
        poinvj.setProrateseq(porcpjSeq.add(new BigDecimal("1")));
        poinvj.setPostdate(currentDate());
        poinvj.setScamount(sale.path("totAmt").decimalValue());
        poinvj.setFcamount(sale.path("totAmt").decimalValue());
        poinvj.setScdoctotal(sale.path("totAmt").decimalValue());
        poinvj.setIscomplete((short) 1);                 // example iscomplete flag
        poinvj.setDtcomplete(currentDate());
        poinvj.setScrtgamt(new BigDecimal("0.0"));
        poinvj.setCaxamount(new BigDecimal("0.0"));

        //Save
        poinvjRepository.save(poinvj);

        // Print to verify values (optional)
        System.out.println(poinvj);





//Instantiate Poinvl
    Poinvl poinvl = new Poinvl();
        PoinvlId poinvlId = new PoinvlId();
        poinvl.setId(poinvlId);

        poinvl.setAudtdate(currentDate());
        poinvl.setAudttime(currentTime());
        poinvl.setAudtuser("ADMIN");
        poinvl.setAudtorg(company);
        poinvl.setInvlseq(maxsqlID());
        poinvl.setInvcseq(maxsqlID());
        poinvl.setOeonumber("");
        poinvl.setIndbtable((short) 1);
        poinvl.setPostedtoic((short) 0);
        poinvl.setCompletion((short) 3);
        poinvl.setDtcomplete(currentDate());
        poinvl.setRcphseq(poporl.getId().getPorhseq());
        poinvl.setRcplseq(poporl.getId().getPorhseq());
        poinvl.setItemexists((short) 1);
        poinvl.setItemno(poporl.getItemno());
        poinvl.setLocation("HQ");
        poinvl.setItemdesc(poporl.getItemdesc());
        poinvl.setVenditemno("");
        poinvl.setHascomment((short) 0);
        poinvl.setOrderunit(poporl.getOrderunit());
        poinvl.setOrderconv(new BigDecimal(1));
        poinvl.setOrderdecml((short) 4);
        poinvl.setRcpunit(poporl.getOrderunit());
        poinvl.setRcpconv(new BigDecimal("1.0"));
        poinvl.setRcpdecml((short) 4);
        poinvl.setStockdecml((short) 4);
        poinvl.setRqreceived(node.path("qty").decimalValue());
        poinvl.setSqreceived(new BigDecimal("1.0"));
        poinvl.setOqreceived(new BigDecimal("0.0"));
        poinvl.setUnitweight(new BigDecimal("0.0"));
        poinvl.setExtweight(new BigDecimal("0.0"));
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
        poinvl.setTaxrate1(new BigDecimal("5.00000"));
        poinvl.setTaxrate2(new BigDecimal("0.00000"));
        poinvl.setTaxrate3(new BigDecimal("0.00000"));
        poinvl.setTaxrate4(new BigDecimal("0.00000"));
        poinvl.setTaxrate5(new BigDecimal("0.00000"));
        poinvl.setTaxinclud1(new BigDecimal("16.0"));
        poinvl.setTaxinclud2(new BigDecimal("0.0"));
        poinvl.setTaxinclud3(new BigDecimal("0.0"));
        poinvl.setTaxinclud4(new BigDecimal("0.0"));
        poinvl.setTaxinclud5(new BigDecimal("0.0"));
        poinvl.setTaxamount1((short) 0);
        poinvl.setTaxamount2((short) 0);
        poinvl.setTaxamount3((short) 0);
        poinvl.setTaxamount4((short) 0);
        poinvl.setTaxamount5((short) 0);
        poinvl.setTxrecvamt1(node.path("vatAmt").decimalValue());
        poinvl.setTxrecvamt2(new BigDecimal(0));
        poinvl.setTxrecvamt3(new BigDecimal(0));
        poinvl.setTxrecvamt4(new BigDecimal(0));
        poinvl.setTxrecvamt5(new BigDecimal(0));
        poinvl.setTxexpsamt1(new BigDecimal(0));
        poinvl.setTxexpsamt2(new BigDecimal(0));
        poinvl.setTxexpsamt3(new BigDecimal(0));
        poinvl.setTxexpsamt4(new BigDecimal(0));
        poinvl.setTxexpsamt5(new BigDecimal(0));
        poinvl.setTxalloamt1(new BigDecimal(0));
        poinvl.setTxalloamt2(new BigDecimal(0));
        poinvl.setTxalloamt3(new BigDecimal(0));
        poinvl.setTxalloamt4(new BigDecimal(0));
        poinvl.setTxalloamt5(new BigDecimal(0));
        poinvl.setTxbaseallo(new BigDecimal(0));
        poinvl.setTxincluded(new BigDecimal(0));
        poinvl.setTxexcluded(node.path("vatAmt").decimalValue());
        poinvl.setTaxamount(node.path("vatAmt").decimalValue());
        poinvl.setTxrecvamt(node.path("vatAmt").decimalValue());
        poinvl.setTxexpsamt(new BigDecimal(0));
        poinvl.setTxalloamt(new BigDecimal(0));
        poinvl.setTfbaseallo(node.path("taxblAmt").decimalValue());
        poinvl.setTfinclude1(new BigDecimal("0.000"));
        poinvl.setTfinclude2(new BigDecimal("0.000"));
        poinvl.setTfinclude3(new BigDecimal("0.000"));
        poinvl.setTfinclude4(new BigDecimal("0.000"));
        poinvl.setTfinclude5(new BigDecimal("0.000"));
        poinvl.setTfrecvamt1(node.path("vatAmt").decimalValue());
        poinvl.setTfrecvamt2(new BigDecimal("0.000"));
        poinvl.setTfrecvamt3(new BigDecimal("0.000"));
        poinvl.setTfrecvamt4(new BigDecimal("0.000"));
        poinvl.setTfrecvamt5(new BigDecimal("0.000"));
        poinvl.setTfexpsamt1(new BigDecimal("0.000"));
        poinvl.setTfexpsamt2(new BigDecimal("0.000"));
        poinvl.setTfexpsamt3(new BigDecimal("0.000"));
        poinvl.setTfexpsamt4(new BigDecimal("0.000"));
        poinvl.setTfexpsamt5(new BigDecimal("0.000"));
        poinvl.setGlacexpens("");
        poinvl.setMprorated(new BigDecimal("0.000"));
        poinvl.setStockitem((Short)0);
        poinvl.setGlnonstkcr("74070-200");
        poinvl.setManitemno("");
        poinvl.setDiscpct(new BigDecimal("0.000"));
        poinvl.setDiscount(new BigDecimal("0.000"));
        poinvl.setDiscountf(new BigDecimal("0.000"));
        poinvl.setXirqrecevd(node.path("qty").decimalValue());
        poinvl.setXiextwght(new BigDecimal("0.0000"));
        poinvl.setXinetxtend(node.path("taxblAmt").decimalValue());
        poinvl.setInvlines(1);
        poinvl.setValues(0);
        poinvl.setContract("");
        poinvl.setProject("");
        poinvl.setCcategory("");
        poinvl.setCostclass((short) 0);
        poinvl.setBilltype((short) 0);
        poinvl.setBillrate(new BigDecimal("0.000"));
        poinvl.setBillcurr("");
        poinvl.setAritemno("");
        poinvl.setArunit("");
        poinvl.setBillrate(new BigDecimal("0.000"));
        poinvl.setRtgpercent(new BigDecimal("0.0"));
        poinvl.setRtgdays((short) 0);
        poinvl.setRtgamount(new BigDecimal("0.000"));
        poinvl.setRtgamount(new BigDecimal("0.000"));
        poinvl.setRtgdatedue(currentDate());
        poinvl.setRtgddtover((short) 0);
        poinvl.setTaramount1(node.path("vatAmt").decimalValue());
        poinvl.setTaramount2(new BigDecimal("0.000"));
        poinvl.setTaramount3(new BigDecimal("0.000"));
        poinvl.setTaramount4(new BigDecimal("0.000"));
        poinvl.setTaramount5(new BigDecimal("0.000"));
        poinvl.setTralloamt1(new BigDecimal("0.000"));
        poinvl.setTralloamt2(new BigDecimal("0.000"));
        poinvl.setTralloamt3(new BigDecimal("0.000"));
        poinvl.setTralloamt4(new BigDecimal("0.000"));
        poinvl.setTralloamt5(new BigDecimal("0.000"));
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
        poinvl.setWeightconv(new BigDecimal("1.0"));
        poinvl.setDefuweight(new BigDecimal("0.0000"));
        poinvl.setDefextwght(new BigDecimal("0.0000"));
        poinvl.setXidefextwt(new BigDecimal("0.0000"));
        poinvl.setSerialqty(0);
        poinvl.setLotqty(new BigDecimal("0.0000"));
        poinvl.setSlitem((short) 0);
        poinvl.setDetailnum((short) 1);
        poinvl.setCaxable1((short) 0);
        poinvl.setCaxable2((short) 0);
        poinvl.setCaxable3((short) 0);
        poinvl.setCaxable4((short) 0);
        poinvl.setCaxable5((short) 0);

        //Save
        poinvlRepository.save(poinval);
        // Print to verify values (optional)
        System.out.println(poinvl);





    }
    //Select purchases
    @Retryable( backoff = @Backoff(delay = 2000))
    public PurchasesResponse selectPurchase() {
        String url = baseUrl + "/trnsPurchase/selectTrnsPurchaseSales";
        selectSales selectSales = new selectSales();
        selectSales.setTpin(clientTpin);
        selectSales.setBhfId(branchId);
        selectSales.setLastReqDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<selectSales> requestEntity = new HttpEntity<>(selectSales, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        String jsonResponse = "{\"resultCd\":\"000\",\"resultMsg\":\"It is succeeded\",\"resultDt\":\"20240510103403\",\"data\":{\"saleList\":[{\"spplrTpin\":\"1001733250                    \",\"spplrNm\":\"CFAO MOTORS (ZAMBIA) LIMITED                                \",\"spplrBhfId\":\"000\",\"spplrInvcNo\":45,\"rcptTyCd\":\"S\",\"pmtTyCd\":\"01\",\"cfmDt\":\"2024-05-08 10:20:10\",\"salesDt\":\"20240502\",\"stockRlsDt\":null,\"totItemCnt\":1,\"totTaxblAmt\":86.2069,\"totTaxAmt\":13.7931,\"totAmt\":100,\"remark\":null,\"itemList\":[{\"itemSeq\":1,\"itemCd\":\"20044\",\"itemClsCd\":\"50102517\",\"itemNm\":\"ChickenWings\",\"bcd\":null,\"pkgUnitCd\":\"BA\",\"pkg\":0,\"qtyUnitCd\":\"BE\",\"qty\":1,\"prc\":100,\"splyAmt\":100,\"dcRt\":0,\"dcAmt\":0,\"vatCatCd\":\"A\",\"iplCatCd\":null,\"tlCatCd\":null,\"exciseTxCatCd\":null,\"vatTaxblAmt\":86.21,\"exciseTaxblAmt\":0,\"iplTaxblAmt\":0,\"tlTaxblAmt\":0,\"taxblAmt\":86.21,\"vatAmt\":13.79,\"iplAmt\":0,\"tlAmt\":0,\"exciseTxAmt\":0,\"totAmt\":100}]},{\"spplrTpin\":\"1019249900                    \",\"spplrNm\":\"SMART SUPPLIER\",\"spplrBhfId\":\"000\",\"spplrInvcNo\":46,\"rcptTyCd\":\"S\",\"pmtTyCd\":\"01\",\"cfmDt\":\"2024-05-08 10:20:10\",\"salesDt\":\"20240502\",\"stockRlsDt\":null,\"totItemCnt\":1,\"totTaxblAmt\":86.2069,\"totTaxAmt\":13.7931,\"totAmt\":100,\"remark\":null,\"itemList\":[{\"itemSeq\":1,\"itemCd\":\"20044\",\"itemClsCd\":\"50102517\",\"itemNm\":\"ChickenWings\",\"bcd\":null,\"pkgUnitCd\":\"BA\",\"pkg\":0,\"qtyUnitCd\":\"BE\",\"qty\":1,\"prc\":100,\"splyAmt\":100,\"dcRt\":0,\"dcAmt\":0,\"vatCatCd\":\"A\",\"iplCatCd\":null,\"tlCatCd\":null,\"exciseTxCatCd\":null,\"vatTaxblAmt\":86.21,\"exciseTaxblAmt\":0,\"iplTaxblAmt\":0,\"tlTaxblAmt\":0,\"taxblAmt\":86.21,\"vatAmt\":13.79,\"iplAmt\":0,\"tlAmt\":0,\"exciseTxAmt\":0,\"totAmt\":100},{\"itemSeq\":1,\"itemCd\":\"20044\",\"itemClsCd\":\"50102517\",\"itemNm\":\"ChickenWings\",\"bcd\":null,\"pkgUnitCd\":\"BA\",\"pkg\":0,\"qtyUnitCd\":\"BE\",\"qty\":1,\"prc\":100,\"splyAmt\":100,\"dcRt\":0,\"dcAmt\":0,\"vatCatCd\":\"A\",\"iplCatCd\":null,\"tlCatCd\":null,\"exciseTxCatCd\":null,\"vatTaxblAmt\":86.21,\"exciseTaxblAmt\":0,\"iplTaxblAmt\":0,\"tlTaxblAmt\":0,\"taxblAmt\":86.21,\"vatAmt\":13.79,\"iplAmt\":0,\"tlAmt\":0,\"exciseTxAmt\":0,\"totAmt\":100}]}]}}";
        if (response.getStatusCode() == HttpStatus.OK) {
            //String jsonResponse = response.getBody(); // Use actual response body here
            JSONObject jsonObject = new JSONObject(jsonResponse);
            if (jsonObject.getString("resultCd").equals("000")) {
                JSONArray saleList = jsonObject.getJSONObject("data").getJSONArray("saleList");
                List<selectPurchasesDTO> invoiceDTOList = new ArrayList<>();
                List<supplierPurchaseOrderDTO> supplierPurchaseOrderDTOS = new ArrayList<>();

                for (int i = 0; i < saleList.length(); i++) {
                    JSONObject sale = saleList.getJSONObject(i);
                    selectPurchasesDTO invoiceDTO = new selectPurchasesDTO();
                    String vendor = getVendorFromTpin(sale.getString("spplrTpin"),sale.getString("spplrNm"));

                    List<Poporh1> poporh1s = poporh1Repo.findByVdcodeAndIscomplete(vendor, (short) 0);
                    for (Poporh1 poporh1 : poporh1s) {
                        supplierPurchaseOrderDTO purchaseOrderDTO = new supplierPurchaseOrderDTO();
                        List<supplierPurchaseOrderItems> supplierPurchaseOrderItems = new ArrayList<>();
                        List<Poporl> poporls = poporlRepo.findById(poporh1.getId());

                        purchaseOrderDTO.setPoNumber(poporh1.getPonumber());
                        purchaseOrderDTO.setVendorName(poporh1.getVdname());
                        purchaseOrderDTO.setVendorCode(poporh1.getVdcode());
                        purchaseOrderDTO.setTpin(sale.getString("spplrTpin"));
                        purchaseOrderDTO.setTaxableAmount(poporh1.getTaxbase1());
                        purchaseOrderDTO.setTax(poporh1.getTaxamount1());
                        purchaseOrderDTO.setTotalAmount(poporh1.getExtended());

                        for (Poporl poporl : poporls) {
                            supplierPurchaseOrderItems purchaseOrderItem = new supplierPurchaseOrderItems();
                            purchaseOrderItem.setItemCode(poporl.getItemno());
                            purchaseOrderItem.setItemName(poporl.getItemdesc());
                            purchaseOrderItem.setPrice(poporl.getUnitcost());
                            purchaseOrderItem.setTaxableAmount(poporl.getTaxbase1());
                            purchaseOrderItem.setTaxAmount(poporl.getTaxamount1());
                            purchaseOrderItem.setTotalAmount(poporl.getExtended());
                            purchaseOrderItem.setQuantity(poporl.getOqordered());
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


    public String nextReceiptNo(){
        Poopt pt = pooptRepo.findByRatetype("SP");
        return pt.getRcpprefixd().replace(" ","") + pt.getRcpbodyd().replace(" ","");
    }
}
