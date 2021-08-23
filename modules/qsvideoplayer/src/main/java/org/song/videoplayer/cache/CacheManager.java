package org.song.videoplayer.cache;

import android.content.Context;
import com.danikula.videocache.HttpProxyCacheServer;
import java.util.Map;

/**
 * Author:Kgstt
 * Time: 21-4-29
 */
public class CacheManager {

    public static String buildCahchUrl(Context context, String rawUrl, Map<String, String> headers) {
        HttpProxyCacheServer proxy = Proxy.getProxy(context, headers);
        return proxy.getProxyUrl(rawUrl).replaceAll("file:///","/");
    }
}
