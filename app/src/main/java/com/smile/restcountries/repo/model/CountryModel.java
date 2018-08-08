package com.smile.restcountries.repo.model;

import java.util.ArrayList;
import java.util.List;

public class CountryModel {
    private String name;
    private List<Language> languages = new ArrayList<>();
    private List<Currency> currency = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages.clear();
        this.languages.addAll(languages);
    }

    public List<Currency> getCurrency() {
        return currency;
    }

    public void setCurrency(List<Currency> currency) {
        this.currency.clear();
        this.currency.addAll(currency);
    }

    public static class Language {
        private String iso639_1;
        private String iso639_2;
        private String name;
        private String nativeName;

        public String getIso639_1() {
            return iso639_1;
        }

        public void setIso639_1(String iso639_1) {
            this.iso639_1 = iso639_1;
        }

        public String getIso639_2() {
            return iso639_2;
        }

        public void setIso639_2(String iso639_2) {
            this.iso639_2 = iso639_2;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNativeName() {
            return nativeName;
        }

        public void setNativeName(String nativeName) {
            this.nativeName = nativeName;
        }
    }

    public static class Currency {
        private String code;
        private String name;
        private String symbol;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
    }
}
