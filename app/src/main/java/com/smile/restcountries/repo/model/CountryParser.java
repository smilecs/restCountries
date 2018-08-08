package com.smile.restcountries.repo.model;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CountryParser {
    public static final String URL = " https://restcountries.eu/rest/v2/all";
    private static final String COUNTRY_NAME_KEY = "name";
    private static final String CURRENCIES_KEY = "currencies";
    private static final String LANGUAGES_KEY = "languages";
    private static final String CODE_KEY = "code";
    private static final String CURRENCY_NAME_KEY = "name";
    private static final String SYMBOL_KEY = "symbol";
    private static final String ISO639_1_KEY = "iso639_1";
    private static final String ISO639_2_KEY = "iso639_2";
    private static final String LANGUAGE_NAME_KEY = "name";
    private static final String NATIVE_NAME_KEY = "nativeName";

    public List<CountryModel> readJsonStream(InputStream in) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"))) {
            return readCountryList(reader);
        }
    }

    private List<CountryModel> readCountryList(JsonReader reader) throws IOException {
        List<CountryModel> countryModels = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            countryModels.add(readCountry(reader));
        }
        reader.endArray();
        return countryModels;
    }

    private CountryModel readCountry(JsonReader reader) throws IOException {
        CountryModel countryModel = new CountryModel();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.peek() != JsonToken.NULL) {
                String name = reader.nextName();
                switch (name) {
                    case COUNTRY_NAME_KEY:
                        countryModel.setName(safeRead(reader));
                        break;
                    case LANGUAGES_KEY:
                        countryModel.setLanguages(readLanguages(reader));
                        break;
                    case CURRENCIES_KEY:
                        countryModel.setCurrency(readCurrencies(reader));

                        break;
                    default:
                        reader.skipValue();
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return countryModel;
    }

    private List<CountryModel.Language> readLanguages(JsonReader reader) throws IOException {
        List<CountryModel.Language> languages = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            languages.add(readLanguage(reader));
        }
        reader.endArray();
        return languages;
    }

    private CountryModel.Language readLanguage(JsonReader reader) throws IOException {
        CountryModel.Language language = new CountryModel.Language();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.peek() != JsonToken.NULL) {
                String name = reader.nextName();
                switch (name) {
                    case ISO639_1_KEY:
                        language.setIso639_1(safeRead(reader));
                        break;
                    case ISO639_2_KEY:
                        language.setIso639_2(safeRead(reader));
                        break;
                    case LANGUAGE_NAME_KEY:
                        language.setName(safeRead(reader));
                        break;
                    case NATIVE_NAME_KEY:
                        language.setNativeName(safeRead(reader));
                        break;
                    default:
                        reader.skipValue();
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return language;
    }

    private List<CountryModel.Currency> readCurrencies(JsonReader reader) throws IOException {
        List<CountryModel.Currency> currencies = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            currencies.add(readCurrency(reader));
        }
        reader.endArray();
        return currencies;
    }

    private CountryModel.Currency readCurrency(JsonReader reader) throws IOException {
        CountryModel.Currency currency = new CountryModel.Currency();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.peek() != JsonToken.NULL) {
                String name = reader.nextName();
                switch (name) {
                    case CURRENCY_NAME_KEY:
                        currency.setName(safeRead(reader));
                        break;
                    case CODE_KEY:
                        currency.setCode(safeRead(reader));
                        break;
                    case SYMBOL_KEY:
                        currency.setSymbol(safeRead(reader));
                        break;
                    default:
                        reader.skipValue();
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return currency;
    }

    private String safeRead(JsonReader reader) throws IOException {
        if (reader.peek() != JsonToken.NULL) {
            return reader.nextString();
        } else {
            return null;
        }
    }
}

