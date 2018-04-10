package com.xwguan.autofund.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.enums.UpdateStateEnum;
import com.xwguan.autofund.service.api.FundService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class FundServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private FundService fundService;

    // @Test
    public void testSearchFund() {
        fail("Not yet implemented"); // TODO
    }

    // @Test
    public void testGetFundById() {
        fail("Not yet implemented"); // TODO
    }

    // @Test
    public void testListFundByType() {
        fail("Not yet implemented"); // TODO
    }

    // @Test
    public void testGetAssetAllocationByFundId() {
        fail("Not yet implemented"); // TODO
    }

    // @Test
    public void testCountFund() {
        fail("Not yet implemented"); // TODO
    }

    // @Test
    public void testCountUpdateRequired() {
        fail("Not yet implemented"); // TODO
    }

    // @Test
    public void testInitFundTable() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public void testUpdateFundTable() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        UpdateStateEnum updateState = fundService.updateFund();
        logger.info("==========================Update State: {}", updateState);
    }

//     @Test
    public void testUpdateFundCompanyTable() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        UpdateStateEnum updateState = fundService.updateFundCompany();
        logger.info("==========================Update State: {}", updateState);
    }

//     @Test
    public void testUpdateFundManagerTable() throws MalformedURLException, UnsupportedEncodingException, IOException, InterruptedException, ExecutionException, TimeoutException {
        UpdateStateEnum updateState = fundService.updateFundManager();
        logger.info("==========================Update State: {}", updateState);
    }

//     @Test 
    public void testUpdateAssetAllocationTable() {
//        Future<?> submit1 = executor.submit(()->{
//            UpdateStateEnum updateState;
//            try {
//                updateState = fundService.updateAssetAllocationTable();
//                logger.info("==========================Update State: {}", updateState);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        Future<?> submit2 = executor.submit(()->{
//            UpdateStateEnum updateState;
//            try {
//                updateState = fundService.updateAssetAllocationTable();
//                logger.info("==========================Update State: {}", updateState);
//            } catch (IOException | InterruptedException | ExecutionException | TimeoutException e) {
//                e.printStackTrace();
//            }
//        });
//        try {
//            submit1.get();
//          submit2.get();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            UpdateStateEnum updateState = fundService.updateAssetAllocation();
            logger.info("==========================Update State: {}", updateState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     @Test
    public void testUpdateFundHistory() {
        try {
            boolean useMultiThread = false;
            LocalTime start = LocalTime.now();
            Map<Integer, UpdateStateEnum> updateFundHistory = fundService.updateFundHistory(useMultiThread);
            LocalTime end = LocalTime.now();
            long timeCost = start.until(end, ChronoUnit.SECONDS);
            logger.info("************Time Cost************ ={} s", timeCost);
            logger.info("==========================Update State: {}", updateFundHistory);
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    // @Test
    public void testDeleteFund() {
        fail("Not yet implemented"); // TODO
    }

}
