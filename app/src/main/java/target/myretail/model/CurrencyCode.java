package target.myretail.model;

public enum CurrencyCode {

    USD, EUR, GBP, CAD, AUD, NZD, CHF, JPY, NOK, SEK, DKK, PLN, RUB, BRL, TRY, CNY, INR, ZAR, HKD, MYR, PHP, IDR;

    public static CurrencyCode getCurrencyCode(String currencyCode) {
        return CurrencyCode.valueOf(currencyCode);
    }
}
