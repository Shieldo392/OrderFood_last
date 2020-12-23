package com.example.project_ad41_qlnh;

import com.google.android.gms.maps.model.LatLng;
import com.here.sdk.core.GeoCoordinates;

import java.util.List;

import FoodOject.Food;

public  class DeFile {
    public static String Ancohol = "Beer and ancohol";
    public static String MAIN_MEAL = "Món chính";
    public static String MILK_TEA = "Trà sữa";
    public static String DISTANCE = "Khoảng cách";
    public static String PAY = "Thanh toán";
    public static float GOAL_LOCATION_Latitude = 20.982852718307267f;
    public static float GOAL_LOCATION_Longitude = 105.60436390977563f;
    public static LatLng GOAL_LOCATION = new LatLng(GOAL_LOCATION_Latitude, GOAL_LOCATION_Longitude);
    public static String API_KEY = "AIzaSyADvfeisKyFopUcxdm7NTJszJCXp-vL6Mk";
    public static String URL = "https://demo4110086.mockable.io/demoandroid41";
    public static int CODE_LISTFOOD = 999;
    public static List<Food> foodList;
    public static String URL_WEATHER = "api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid=2ae7e6a9303eec04e37be1839c808dd3";

    public static GeoCoordinates current_loc = null;

    public static String getURL_Weather(float lat, float lng){
        String URL_WEATHER ="api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon=" + lng +"&appid=2ae7e6a9303eec04e37be1839c808dd3";
        return URL_WEATHER;
    }
    public static String URL_MAIN_MEALS = "https://demo4110086.mockable.io/mainmeals";
    public static String URL_DRINK_TEA = "https://demo4110086.mockable.io/drinktea";
    public static String URL_LIST_FOOD = "https://demo4110086.mockable.io/listfood";
    public static String URL_ADVISETED = "https://demo4110086.mockable.io/adviseted";
    public static String URL_WELCOME = "https://demo4110086.mockable.io/welcome";
    public static void setListFood(List<Food> foods){
         foodList = foods;
    }
    public static String URL_VOUCHER = "https://demo4110086.mockable.io/bigsales";
    public static int FRAGMENT_HOME_CODE = 111;
}

//20.982852718307267, 105.60436390977563