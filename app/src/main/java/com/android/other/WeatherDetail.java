package com.android.other;

import java.util.List;

public class WeatherDetail {

    private int error;
    private String status;
    private String date;
    private List<ResultsBean> results;
    public int getError() {
        return error;
    }
    public String getDate() {
        return date;
    }
    public List<ResultsBean> getResults() {
        return results;
    }

    public static class ResultsBean {
        private String currentCity;
        private String pm25;
        private List<IndexBean> index;
        private List<WeatherDataBean> weather_data;
        public String getCurrentCity() {
            return currentCity;
        }
        public List<IndexBean> getIndex() {
            return index;
        }
        public List<WeatherDataBean> getWeather_data() {
            return weather_data;
        }


        public static class IndexBean {
            private String des;
            private String tipt;
            private String title;
            private String zs;
            public String getDes() {
                return des;
            }
            public String getZs() {
                return zs;
            }
        }

        public static class WeatherDataBean {
            private String date;
            private String dayPictureUrl;
            private String nightPictureUrl;
            private String weather;
            private String wind;
            private String temperature;
            public String getDate() {
                return date;
            }
            public String getDayPictureUrl() {
                return dayPictureUrl;
            }
            public String getWeather() {
                return weather;
            }
            public String getWind() {
                return wind;
            }
            public String getTemperature() {
                return temperature;
            }
        }
    }
}
