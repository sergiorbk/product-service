package com.sergosoft.productservice.domain.product.price;

import java.util.Map;
import java.math.BigDecimal;

import lombok.Getter;

@Getter
public enum Currency {
    USD, AED, AFN, ALL, AMD, ANG, AOA, ARS, AUD, AWG, AZN, BAM, BBD, BDT, BGN, BHD,
    BIF, BMD, BND, BOB, BRL, BSD, BTN, BWP, BYN, BZD, CAD, CDF, CHF, CLP, CNY, COP,
    CRC, CUP, CVE, CZK, DJF, DKK, DOP, DZD, EGP, ERN, ETB, EUR, FJD, FKP, FOK, GBP,
    GEL, GGP, GHS, GIP, GMD, GNF, GTQ, GYD, HKD, HNL, HRK, HTG, HUF, IDR, ILS, IMP,
    INR, IQD, IRR, ISK, JEP, JMD, JOD, JPY, KES, KGS, KHR, KID, KMF, KRW, KWD, KYD,
    KZT, LAK, LBP, LKR, LRD, LSL, LYD, MAD, MDL, MGA, MKD, MMK, MNT, MOP, MRU, MUR,
    MVR, MWK, MXN, MYR, MZN, NAD, NGN, NIO, NOK, NPR, NZD, OMR, PAB, PEN, PGK, PHP,
    PKR, PLN, PYG, QAR, RON, RSD, RUB, RWF, SAR, SBD, SCR, SDG, SEK, SGD, SHP, SLE,
    SLL, SOS, SRD, SSP, STN, SYP, SZL, THB, TJS, TMT, TND, TOP, TRY, TTD, TVD, TWD,
    TZS, UAH, UGX, UYU, UZS, VES, VND, VUV, WST, XAF, XCD, XDR, XOF, XPF, YER, ZAR,
    ZMW, ZWL;

    private BigDecimal exchangeRate = BigDecimal.ONE;

    public static void updateExchangeRates(Map<String, BigDecimal> rates) {
        for (Map.Entry<String, BigDecimal> entry : rates.entrySet()) {
            try {
                Currency currency = Currency.valueOf(entry.getKey());
                currency.setExchangeRate(entry.getValue());
            } catch (IllegalArgumentException e) {
                System.err.println("Unsupported currency: " + entry.getKey());
            }
        }
    }

    private void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
