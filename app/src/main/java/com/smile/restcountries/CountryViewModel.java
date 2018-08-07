package com.smile.restcountries;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.smile.restcountries.model.CountryModel;
import com.smile.restcountries.model.CountryParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CountryViewModel extends ViewModel {
    private MutableLiveData<List<CountryModel>> countryLiveData = new MutableLiveData<>();
    private List<CountryModel> countryModelList = new ArrayList<>();

    public CountryViewModel() {
        new CountryAsync(countryLiveData).execute();
    }

    public List<CountryModel> getCountryModelList() {
        return countryModelList;
    }

    public LiveData<List<CountryModel>> getCountryLiveData() {
        return countryLiveData;
    }

    private static class CountryAsync extends AsyncTask<Void, Void, Void> {
        MutableLiveData<List<CountryModel>> liveData;

        public CountryAsync(MutableLiveData<List<CountryModel>> liveData) {
            this.liveData = liveData;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<CountryModel> countryModels = null;
            try {
                InputStream stream = NetworkUtils.makeRequest(new URL(CountryParser.URL));
                CountryParser countryParser = new CountryParser();
                countryModels = countryParser.readJsonStream(stream);
            } catch (MalformedURLException ml) {

            } catch (IOException io) {

            }
            liveData.postValue(countryModels);
            return null;
        }
    }
}
