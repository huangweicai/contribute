package cn.myvollery.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import static cn.myvollery.test.VolleryHelp.*;

/**
 * vollery框架使用演示
 */
public class MainActivity extends AppCompatActivity {
    String imgUrl = "http://b.hiphotos.baidu.com/album/pic/item/caef76094b36acafe72d0e667cd98d1000e99c5f.jpg?psign=e72d0e667cd98d1001e93901213fb80e7aec54e737d1b867";
    String dataUrl = "http://b.hiphotos.baidu.com/album/pic/item/caef76094b36acafe72d0e667cd98d1000e99c5f.jpg?psign=e72d0e667cd98d1001e93901213fb80e7aec54e737d1b867";

    ImageView iv_netimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_netimage = (ImageView) findViewById(R.id.iv_netimage);
    }

    public void doClick(View v){


        VolleryHelp.getInstance().doJsonObjectGetRequest(dataUrl);





//---------------------------------------------------

        //使用原生控件加载图片
//        VolleryHelp volleryHelp = new VolleryHelp();
//        volleryHelp.getImgLoader(this, iv_netimage, imgUrl);


        //使用vollery封装控件加载图片，原理一样，原生控件的宽高在监听中控件，而封装的直接写就可以了
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        ImageLoader imageLoader = new ImageLoader(queue, new VolleryHelp.ImageLoaderCache());
//
//        NetworkImageView niv = (NetworkImageView) findViewById(R.id.networkImageView1);
//
//        niv.setDefaultImageResId(R.mipmap.img_loading);
//        niv.setErrorImageResId(R.mipmap.img_failed);
//        niv.setImageUrl(imgUrl, imageLoader);
    }


}
