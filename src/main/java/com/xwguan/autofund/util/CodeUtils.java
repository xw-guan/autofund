package com.xwguan.autofund.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.xwguan.autofund.enums.StockExchangeEnum;
import com.xwguan.autofund.enums.SymbolFormatEnum;
import com.xwguan.autofund.exception.io.InvalidParamException;
import com.xwguan.autofund.exception.io.InvalidTickerSymbolException;

/**
 * 股票代码和基金代码工具, 用于验证格式和转换格式
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-02
 */
public class CodeUtils {

    private static final Map<SymbolFormatEnum, Pattern> symbolFormatPatternMap = new HashMap<>();

    private static final Map<SymbolFormatEnum, BijectionMap<StockExchangeEnum, String>> stockExchangeFormatMap = new HashMap<>();

    private static final Pattern FUND_CODE_PATTERN = Pattern.compile("^\\d{6}$");

    static {
        // init symbolFormatPatternMap
        for (SymbolFormatEnum s : SymbolFormatEnum.values()) {
            symbolFormatPatternMap.put(s, Pattern.compile(s.getRegex()));
        }
        // init stockExchangeFormatMap
        BijectionMap<StockExchangeEnum, String> bareMap = new BijectionMap<>();
        bareMap.put(StockExchangeEnum.SH, "");
        bareMap.put(StockExchangeEnum.SZ, "");
        stockExchangeFormatMap.put(SymbolFormatEnum.BARE, bareMap);
        BijectionMap<StockExchangeEnum, String> autofundMap = new BijectionMap<>();
        autofundMap.put(StockExchangeEnum.SH, ".SH");
        autofundMap.put(StockExchangeEnum.SZ, ".SZ");
        stockExchangeFormatMap.put(SymbolFormatEnum.AUTOFUND, autofundMap);
        BijectionMap<StockExchangeEnum, String> easymoneyMap = new BijectionMap<>();
        easymoneyMap.put(StockExchangeEnum.SH, "1");
        easymoneyMap.put(StockExchangeEnum.SZ, "2");
        stockExchangeFormatMap.put(SymbolFormatEnum.EASYMONEY, easymoneyMap);
        BijectionMap<StockExchangeEnum, String> neteaseMap = new BijectionMap<>();
        neteaseMap.put(StockExchangeEnum.SH, "0");
        neteaseMap.put(StockExchangeEnum.SZ, "1");
        stockExchangeFormatMap.put(SymbolFormatEnum.NETEASE, neteaseMap);
        BijectionMap<StockExchangeEnum, String> sinaMap = new BijectionMap<>();
        sinaMap.put(StockExchangeEnum.SH, "sh");
        sinaMap.put(StockExchangeEnum.SZ, "sz");
        stockExchangeFormatMap.put(SymbolFormatEnum.SINA, sinaMap);
    }

    /**
     * 验证股票代码是否符合pattern, 符合时返回true, 不符合或symbol或format为空是返回false
     * 
     * @param symbol 待验证的股票代码
     * @param format 股票代码应符合的格式
     * @return true: symbol符合Pattern; false: symbol不符合Pattern或symbol为空或format为null
     */
    public static boolean isSymbolMatchFormat(String symbol, SymbolFormatEnum format) {
        if (StringUtils.isEmpty(symbol) || format == null) {
            return false;
        }
        Pattern pattern = symbolFormatPatternMap.get(format);
        return pattern.matcher(symbol).matches();
    }
    
    /**
     * 
     * @param bareSymbol
     * @param stockExchange
     * @param formatOut
     * @return
     * @throws InvalidParamException
     */
    public static String convert(String bareSymbol, StockExchangeEnum stockExchange, SymbolFormatEnum formatOut)
        throws InvalidParamException {
        if (stockExchange == null) {
            return bareSymbol;
        }
        BijectionMap<StockExchangeEnum, String> stockExchangeMap = stockExchangeFormatMap.get(formatOut);
        if (stockExchangeMap == null || stockExchangeMap.isEmpty()) {
            throw new InvalidParamException("The output SymbolFormatEnum " + formatOut + " is not supported");
        }
        StringBuilder sb = new StringBuilder();
        switch (formatOut.getStockExchangePosition()) {
        case SUFFIX:
            sb.append(bareSymbol).append(stockExchangeMap.getValue(stockExchange));
            break;
        case PREFIX:
            sb.append(stockExchangeMap.getValue(stockExchange)).append(bareSymbol);
            break;
        case EMPTY:
            sb.append(bareSymbol);
            break;
        default:
            throw new InvalidParamException("The output SymbolFormatEnum " + formatOut + " is not supported");
        }
        return sb.toString();
    }

    /**
     * 将输入的股票代码由一种格式转为另一种格式.
     * 输入格式为SymbolFormatEnum.BARE时, 只会输出SymbolFormatEnum.BARE格式
     * 
     * @param symbolIn 待转化的股票代码
     * @param formatIn 输入股票代码应符合的格式
     * @param formatOut 输出股票代码应符合的格式
     * @return 由输入股票代码转化成的指定输出格式
     * @throws InvalidParamException 输入的参数为空或symbol格式不符合
     */
    public static String convert(String symbolIn, SymbolFormatEnum formatIn, SymbolFormatEnum formatOut)
        throws InvalidParamException {
        if (StringUtils.isEmpty(symbolIn)) {
            throw new InvalidTickerSymbolException("A NOT EMPTY symbol is required");
        }
        if (formatIn == null || formatOut == null) {
            throw new InvalidParamException("A NOT NULL format is required");
        }
        Pattern pattern = symbolFormatPatternMap.get(formatIn);
        Matcher matcher = pattern.matcher(symbolIn);
        if (matcher.matches()) {
            BijectionMap<StockExchangeEnum, String> stockExchangeMap = stockExchangeFormatMap.get(formatIn);
            if (stockExchangeMap == null || stockExchangeMap.isEmpty()) {
                throw new InvalidParamException("The input SymbolFormatEnum " + formatIn + " is not supported");
            }
            String bareSymbol = null;
            StockExchangeEnum stockExchange = null;
            switch (formatIn.getStockExchangePosition()) {
            case SUFFIX:
                bareSymbol = matcher.group(1);
                stockExchange = stockExchangeMap.getKey(matcher.group(2));
                break;
            case PREFIX:
                bareSymbol = matcher.group(2);
                stockExchange = stockExchangeMap.getKey(matcher.group(1));
                break;
            case EMPTY:
                bareSymbol = matcher.group(0);
                break;
            default:
                throw new InvalidParamException("The input SymbolFormatEnum " + formatIn + " is not supported");
            }
            return convert(bareSymbol,  stockExchange, formatOut);
        } else {
            throw new InvalidTickerSymbolException(
                "Invalid Symbol " + symbolIn + ". The format must be like " + formatIn.getEg());
        }
    }

    /**
     * 判断是否为基金代码, 但不带交易所代码的股票代码也是和基金代码一样的6位数字格式, 也满足本方法的判断
     * 
     * @param fundCode
     * @return
     */
    public static boolean isFundCode(String fundCode) {
        return FUND_CODE_PATTERN.matcher(fundCode).matches();
    }

    /**
     * 如果两个格式都是全数字, 如NETEASE 0000001和EASYMONEY 0000011, 该方法可以返回任意的一个
     * 
     * @param symbol
     * @return
     */
    @SuppressWarnings("unused")
    @Deprecated
    private static SymbolFormatEnum autoMatchSymbolFormat(String symbol) {
        Set<SymbolFormatEnum> keySet = symbolFormatPatternMap.keySet();
        for (SymbolFormatEnum f : keySet) {
            if (isSymbolMatchFormat(symbol, f)) {
                return f;
            }
        }
        return null;
    }

}
