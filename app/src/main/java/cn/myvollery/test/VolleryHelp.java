package cn.myvollery.test;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 说明：Vollery框架帮助类
 * 作者：黄伟才 on 2016/3/24 13:37
 */
public class VolleryHelp {

    private static VolleryHelp volleryHelp;
    public static VolleryHelp getInstance(){
        if(null==volleryHelp)
            volleryHelp = new VolleryHelp();
        return volleryHelp;
    }

    /**
     * 三个参数，默认是Get请求获取字符串
     *
     * @param context
     * @param url
     */
    public void doDefaultStringGetRequest(Context context, String url) {
        //创建RequestQueue对象,一个activity创建一个对象即可
        RequestQueue mQueue = Volley.newRequestQueue(context);
        //创建get方法的json请求对象
        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("onResponse", "" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", "" + error.toString());
            }
        });
        //将请求对象加入到请求队列里
        mQueue.add(sr);
    }

    /**
     * 四个参数，GET请求获取字符串
     *
     * @param context
     * @param url
     */
    public void doStringGetRequest(Context context, String url) {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("onResponse", "" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", "" + error.toString());
            }
        });
        mQueue.add(sr);
    }

    /**
     * 四个参数，POST请求获取字符串
     *
     * @param url
     */
    public void doStringPostRequest(Context context, String url) {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("onResponse", "" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", "" + error.toString());
            }
        }) {
            /**
             * 《POST请求参数提交的地方》
             * 说明：这只是指定了HTTP请求方式是POST，那么我们要提交给服务器的参数又该怎么设置呢？
             * 很遗憾，StringRequest中并没有提供设置POST参数的方法，但是当发出POST请求的时候，
             * Volley会尝试调用StringRequest的父类——Request中的getParams()方法来获取POST参数，
             * 那么解决方法自然也就有了，我们只需要在StringRequest的匿名类中重写getParams()方法，
             * 在这里设置POST参数就可以了
             * @return
             * @throws AuthFailureError
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("UserName", "glblong");
                map.put("PassWord", "123456");
                return map;
            }
        };
        mQueue.add(sr);

//        sr.setTag("111");//给该请求添加标记
//        mQueue.cancelAll("111");//取消所有指定标记的请求，这里指定标记是111
    }

    /**
     * Get请求JSONObject
     * （比较少用）
     * @param url
     */
    public void doJsonObjectGetRequest(String url) {
        //Constructor which defaults to GET if jsonRequest is null, POST otherwise.
        //如果为null则为GET请求，反之则为POST请求
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
    }


    //=============================================================

    /**
     * 第三第四个参数分别用于指定允许图片最大的宽度和高度，
     * 如果指定的网络图片的宽度或高度大于这里的最大值，则会对图片进行压缩，
     * 指定成0的话就表示不管图片有多大，都不会进行压缩。
     *
     * @param url           图片地址
     *
     *  监听
     *  maxWidth      指定允许图片最大的宽度
     *  maxHeight     指定允许图片最大的高度
     *  decodeConfig  指定图片的颜色属性，Bitmap.Config下的几个常量.
     *  errorListener
     */
    public void getImgRequest(Context context, final ImageView iv, String url) {
        RequestQueue queue = Volley.newRequestQueue(context);
        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
            public void onResponse(Bitmap response) {
                iv.setImageBitmap(response);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });
        queue.add(ir);
    }

    //---------------------------------------------------------------

    /**
     * 加载图片
     * 说明：ImageLoader比ImageRequest更加高效，因为它不仅对图片进行缓存，还可以过滤掉重复的链接，避免重复发送请求
     * @param context
     * @param iv
     * @param url
     */
    public void getImgLoader(Context context, ImageView iv, String url) {
        RequestQueue queue = Volley.newRequestQueue(context);
        ImageLoader il = new ImageLoader(queue, new ImageLoaderCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(
                iv,                     //需要加载图片资源的iv控件
                R.mipmap.ic_launcher,   //正在加载图片资源时，控件显示的图片
                R.mipmap.ic_launcher); //加载失败，控件显示的图片
        //加载并限定图片宽度和高度并进行压缩（指定成0的话就表示不管图片有多大，都不会进行压缩）
        il.get(url, listener, 0, 0);
    }

    public static class ImageLoaderCache implements ImageLoader.ImageCache {
        private LruCache<String, Bitmap> mCache;

        public ImageLoaderCache() {
            int maxSize = 100 * 1024 * 1024;//设置缓存图片的大小为100M
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }
    }

    //=============================================================

}
