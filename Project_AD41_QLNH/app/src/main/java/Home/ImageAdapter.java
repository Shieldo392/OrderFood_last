package Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.project_ad41_qlnh.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import data.AdversitedObject;

public class ImageAdapter extends PagerAdapter {
    List<AdversitedObject> imagesSlideList;
    private LayoutInflater layoutInflater;
    private int current_pos = 0;

    public ImageAdapter(Context context, List<AdversitedObject> imagesSlideList) {
        this.imagesSlideList = imagesSlideList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imagesSlideList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (current_pos >= imagesSlideList.size())
            current_pos = 0;
        AdversitedObject imagesSlide = imagesSlideList.get(current_pos);
        current_pos++;
        View view = layoutInflater.inflate(R.layout.item_viewpager, container, false);

        //LinearLayout lnPager = (LinearLayout) view.findViewById(R.id.ln_pager);
        ImageView imgSlide = (ImageView) view.findViewById(R.id.imgSlide);

//        ImagesSlide imagesSlide = imagesSlideList.get(position);

        //imgSlide.setImageResource(imagesSlide.getSrc());

        Picasso.get().load(imagesSlide.getSrc()).placeholder(R.drawable.server_1).into(imgSlide);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
