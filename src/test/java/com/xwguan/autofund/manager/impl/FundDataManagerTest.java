package com.xwguan.autofund.manager.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.fund.Fund;
import com.xwguan.autofund.entity.fund.FundCompany;
import com.xwguan.autofund.entity.fund.FundHistory;
import com.xwguan.autofund.entity.fund.FundManager;
import com.xwguan.autofund.manager.api.FundDataManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class FundDataManagerTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String fundCode = "000005";

    @SuppressWarnings("unused")
    private static final String[] toFix = { "000091", "000428", "000429", "000430", "000768", "000769", "000770",
        "003626", "004171", "004172", "004328", "004329", "004378", "004379", "004380", "004381", "005267", "005303",
        "005304", "150036", "150037", "150106", "150107", "150123", "150124", "150138", "150139", "150143", "150144",
        "150167", "150168", "150169", "150171", "150172", "150175", "150176", "150184", "150185", "150186", "150187",
        "150231", "150232", "150233", "150234", "150255", "150256", "150257", "150258", "150259", "150260", "150281",
        "150282", "150283", "150284", "150293", "150294", "150295", "150296", "150297", "150298", "150309", "150310",
        "150311", "150312", "150331", "150332", "160814", "161118", "161121", "161122", "161123", "161811", "165523",
        "165524", "165525", "501000", "502003", "502004", "502005", "502006", "502007", "502008", "502010", "502011",
        "502012", "502013", "502014", "502015", "502020", "502021", "502022", "502037", "502038", "502040", "502041",
        "502042", "502048", "502049", "502050", "502053", "502054", "502055", "519059" };

    @Autowired
    private FundDataManager manager;

    // @Test
    public void testListFundCompany() {
        try {
            List<FundCompany> fundCompanyList = manager.listFundCompany();
            assertTrue(CollectionUtils.isNotEmpty(fundCompanyList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // @Test
    public void testListFund() {
        try {
            List<Fund> fundList = manager.listFund();
            for (Fund f : fundList) {
                logger.info("fund: {}", f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // @Test
    public void testListFundManager() {
        try {
            List<FundManager> fundList = manager.listFundManager();
            assertTrue(CollectionUtils.isNotEmpty(fundList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // @Test
    public void testGetFundDetail() {
        try {
            Fund fund = manager.getFundDetail(fundCode);
            assertNotNull(fund);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void testGetFundHistory() {
        try {
            List<FundHistory> fundHistory = manager.getFundHistory("001441", LocalDate.of(2017, 12, 20),
                LocalDate.of(2017, 12, 29));
            for (FundHistory fh : fundHistory) {
                if (fh.getAccNav() == null || fh.getNav() == null) {
                    System.out.println(fh);
                }
                if (fh.getUnitMoney() != null && fh.getUnitMoney().length() > 0) {
                    System.out.println("--" + fh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // @Test
    public void testGetAssertAllocation() {
        try {
            manager.getAssetAllocation(fundCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // @Test
    public void testGetFundInfo() {
        try {
            manager.getFundInfo(fundCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGetRealTimeFundData() {
        try {
            System.out.println(manager.getRealTimeFundData(fundCode));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
