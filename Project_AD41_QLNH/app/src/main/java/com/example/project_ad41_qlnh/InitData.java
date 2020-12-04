package com.example.project_ad41_qlnh;

import java.util.ArrayList;
import java.util.List;

import FoodOject.Food;
import Home.ImagesSlide;

public class InitData {
    public static List<Food> MAIN_MEALS(){
       List<Food>  sanPhamList = new ArrayList<Food>();
        sanPhamList.add(new Food(1,"Normal combo", "1 nồi nước lẩu + 1 bò + 1 gà + rau + các gia vị", 550, R.drawable.top1));
        sanPhamList.add(new Food(2, "Sea combo", "1 nồi nước lẩu + 1 Tôm + 1 bò + rau + các gia vị", 600, R.drawable.top2));
        sanPhamList.add(new Food(3, "Beef combo", "1 nồi nước lẩu + 2 bò + rau + các gia vị", 580, R.drawable.top3));
        sanPhamList.add(new Food(4, "Crab combo", "1 nồi nước lẩu + 1 cua + rau + các gia vị",550,R.drawable.top4 ));
        sanPhamList.add(new Food(5, "Fish Soup", "1 nồi nước lẩu + 1 cá hồi + 2 bò + rau + các gia vị",800,R.drawable.fish_soup ));
        sanPhamList.add(new Food(6, "Chicken combo", "Combo cơm cá",650,R.drawable.rice_normal ));

        return sanPhamList;

    }

    public static List<Food> DRINKS_TEA(){
        List<Food> drinktea = new ArrayList<Food>();
        drinktea.add(new Food(1,"Matcha", "Size L", 35, R.drawable.ts));
        drinktea.add(new Food(2, "Kim cương", "Size L", 45,R.drawable.ts_kim_cuong));
        drinktea.add(new Food(3, "Đường đen", "Size L", 40, R.drawable.ts3));
        drinktea.add(new Food(4, "Ô Long", "Size L", 50,R.drawable.olong2));

        return drinktea;
    }

    public static List<ImagesSlide> LIST_QUANG_CAO(){
        List<ImagesSlide> imageList = new ArrayList<ImagesSlide>();
        imageList.add(new ImagesSlide(R.drawable.top1, 1));
        imageList.add(new ImagesSlide(R.drawable.top2, 2));
        imageList.add(new ImagesSlide(R.drawable.top3, 3));
        imageList.add(new ImagesSlide(R.drawable.top4, 4));

        return imageList;
    }

    public static List<ImagesSlide> LIST_WELCOME(){
        List<ImagesSlide> imageList = new ArrayList<ImagesSlide>();
        imageList.add(new ImagesSlide(R.drawable.soup, 1));
        imageList.add(new ImagesSlide(R.drawable.location, 2));
        imageList.add(new ImagesSlide(R.drawable.shipment, 3));
        imageList.add(new ImagesSlide(R.drawable.search, 4));

        return imageList;
    }

}
