package com.example.ismail.balikbilgisistemi;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ismail on 7.12.2017.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images= {R.drawable.b11460275,R.drawable.balik12,R.drawable.balik13,
            R.drawable.ag1909475,R.drawable.balik22,R.drawable.balik23,
            R.drawable.an1740556,R.drawable.balik32,R.drawable.balik33,
            R.drawable.as1923067,R.drawable.balik42,R.drawable.balik43,
            R.drawable.as1793971,R.drawable.balik52,R.drawable.balik53,
            R.drawable.hh1507088,R.drawable.balik62,R.drawable.balik63,
            R.drawable.ptz57810,R.drawable.balik72,R.drawable.balik73,};

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(View container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.custom_layout,null);
        ImageView ımageView=(ImageView)view.findViewById(R.id.imageView2);
        ımageView.setImageResource(images[position]);
        ViewPager vp=(ViewPager)container;
        vp.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ViewPager vp=(ViewPager) container;
        View view=(View)object;
        vp.removeView(view);
    }
}