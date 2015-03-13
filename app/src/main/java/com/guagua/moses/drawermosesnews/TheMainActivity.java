package com.guagua.moses.drawermosesnews;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.guagua.moses.drawermosesnews.contents.SettingActivity;
import com.guagua.moses.drawermosesnews.contents.imgs.ImageMainFragment;
import com.guagua.moses.drawermosesnews.contents.news.NewsMainPagerFragment;
import com.guagua.moses.drawermosesnews.contents.videos.VideoMainFragment;
import com.guagua.moses.drawermosesnews.contents.weather.WeatherActivity;

/**
 * Created by Moses on 2015.3.9
 */
public class TheMainActivity extends ActionBarActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerListView;
    private String[] mDrawerItems;
    private String itemTitleUp;
    public Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_main);

        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction().
                replace(R.id.content_frame, NewsMainPagerFragment.newInstance())
                .commit();
        itemTitleUp = "新闻";

        initView();
    }

    protected void initView() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setLogo(R.drawable.moses_news_icon);
        mToolbar.setTitle("News Sniper");// 标题的文字需在setSupportActionBar之前，不然会无效
        // mToolbar.setSubtitle("副标题");
        setSupportActionBar(mToolbar);
        /* 这些通过ActionBar来设置也是一样的，注意要在setSupportActionBar(toolbar);之后，不然就报错了 */
        // getSupportActionBar().setTitle("标题");
        // getSupportActionBar().setSubtitle("副标题");
        // getSupportActionBar().setLogo(R.drawable.ic_launcher);

        /* 菜单的监听可以在toolbar里设置，也可以像ActionBar那样，通过Activity的onOptionsItemSelected回调方法来处理 */
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });

        mDrawerItems = new String[]{"新闻", "视频", "图片", "天气", "设置", "地图"};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.string.drawer_open_content_desc,
                R.string.drawer_close_content_desc) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(getString(R.string.drawer_open_content_desc));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(itemTitleUp);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
        MenuListAdapter adapter = new MenuListAdapter(this, getSupportFragmentManager());
        adapter.setList(mDrawerItems);
        mDrawerListView.setAdapter(adapter);
        mDrawerListView.setItemChecked(0, true);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mDrawerListView.setItemChecked(position, true);
                switch (position) {
                    case 0:
                        setTitle(position);
                        itemTitleUp = mDrawerItems[position];
                        getSupportFragmentManager().popBackStack();
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.content_frame, NewsMainPagerFragment.newInstance())
                                .commit();
                        mDrawerLayout.closeDrawer(mDrawerListView);
                        break;
                    case 1:
                        setTitle(position);
                        itemTitleUp = mDrawerItems[position];
                        getSupportFragmentManager().popBackStack();
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.content_frame, VideoMainFragment.newInstance())
                                .commit();
                        mDrawerLayout.closeDrawer(mDrawerListView);
                        break;
                    case 2:
                        setTitle(position);
                        itemTitleUp = mDrawerItems[position];
                        getSupportFragmentManager().popBackStack();
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.content_frame, ImageMainFragment.newInstance()).
                                commit();
                        mDrawerLayout.closeDrawer(mDrawerListView);
                        break;
                    case 3:
                        startActivity(new Intent(TheMainActivity.this, WeatherActivity.class));
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mDrawerLayout.closeDrawer(mDrawerListView);
                        break;
                    case 4:
                        startActivity(new Intent(TheMainActivity.this, SettingActivity.class));
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mDrawerLayout.closeDrawer(mDrawerListView);
                        break;
                    case 5:
                        startActivity(new Intent(TheMainActivity.this, LocationActivity.class));
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mDrawerLayout.closeDrawer(mDrawerListView);
                        break;
                    default:
                        break;
                }
            }
        });

        getSupportActionBar().setLogo(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    public void setTitle(int position) {
        getSupportActionBar().setTitle(mDrawerItems[position]);

    }

    public class MenuListAdapter extends BaseAdapter {

        Context context;
        LayoutInflater inflater;
        FragmentManager fm;
        String[] mItems;

        public MenuListAdapter(Context context, FragmentManager fm) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.fm = fm;
        }

        public void setList(String[] items) {
            this.mItems = items;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mItems.length;
        }

        @Override
        public Object getItem(int position) {
            return mItems[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.menu_item_textview_layout, null);
                textView = (TextView) convertView.findViewById(R.id.menu_item_text_view);
                convertView.setTag(textView);
            } else {
                textView = (TextView) convertView.getTag();
            }

            textView.setText(mItems[position]);
            switch (position) {
                case 0:
                    textView.setCompoundDrawablesWithIntrinsicBounds(
                            getResources().getDrawable(R.drawable.sliding_navigation_tab_more),
                            null, null, null);
                    break;
                case 1:
                    textView.setCompoundDrawablesWithIntrinsicBounds(
                            getResources().getDrawable(R.drawable.sliding_navigation_tab_video),
                            null, null, null);
                    break;
                case 2:
                    textView.setCompoundDrawablesWithIntrinsicBounds(
                            getResources().getDrawable(R.drawable.sliding_navigation_tab_pics),
                            null, null, null);
                    break;
                case 3:
                    textView.setCompoundDrawablesWithIntrinsicBounds(
                            getResources().getDrawable(R.drawable.sliding_navigation_tab_weather),
                            null, null, null);
                    break;
                case 4:
                    textView.setCompoundDrawablesWithIntrinsicBounds(
                            getResources().getDrawable(R.drawable.sliding_navigation_tab_more),
                            null, null, null);
                    break;
                case 5:
                    textView.setCompoundDrawablesWithIntrinsicBounds(
                            getResources().getDrawable(R.drawable.sliding_navigation_tab_pics),
                            null, null, null);
                    break;
                default:
                    break;

            }

            return textView;
        }
    }

}
