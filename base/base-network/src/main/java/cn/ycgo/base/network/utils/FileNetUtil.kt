package cn.ycgo.base.network.utils

import cn.ycgo.base.common.storage.CommonSPEngine
import cn.ycgo.base.common.utils.LogUtils
import okhttp3.*
import okio.Buffer
import okio.BufferedSink
import okio.Okio
import okio.Source
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import kotlin.jvm.Throws


object FileNetUtil {

    fun loadFile(fileName: String, url: String, filePath: String, callback: FileLoadListener, listener: ProgressListener? = null) {
        val client = OkHttpClient.Builder().build()
        val request: Request = Request.Builder()
            .get()
            .url(url)
            .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                callback.onFail()
                LogUtils.e("LLxc","err "+e?.message)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response?) {
                val file = File(filePath)
                if (!file.exists()) {
                    file.mkdir()
                }
                if (response != null) {
                    val input: InputStream = response.body()!!.byteStream()
                    val loadFile = File("$filePath/$fileName")
                    val fos = FileOutputStream(loadFile)
                    var len = 0
                    var sum = 0L
                    val total = response.body()!!.contentLength()
                    val buffer = ByteArray(2048)
                    while (-1 != input.read(buffer).also { len = it }) {
                        fos.write(buffer, 0, len)
                        sum += len
                        //下载中更新进度条
                        listener?.onProgress(total,sum, sum == total)
                        LogUtils.e("LLxc", "progress  total=$total   sum = $sum")
                    }
                    fos.flush()
                    fos.close()
                    input.close()
                    callback.onSuccess(loadFile)
                    LogUtils.e("LLxc","success"+response.body().toString() )
                } else {
                    callback.onFail()
                }
            }
        })
    }

    fun upLoadImg(url: String, filePath: String, fileParameter: String, parameter: Map<String, String>?, callback: Callback, listener: ProgressListener? = null) {
        val file = UploadFromBase64.pathToFile(filePath)
        val client = OkHttpClient.Builder().build()
        // 设置文件以及文件上传类型封装
//        val requestBody = RequestBody.create(MediaType.parse("image/${getExtensionName(filePath)}"), file)
        val requestBody = createCustomRequestBody(MediaType.parse("image/${getExtensionName(filePath)}"), file, listener)
        val part = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(fileParameter, file.name, requestBody)
        val keys = parameter?.keys
        keys?.forEach {
            part.addFormDataPart(it, parameter[it].toString())
        }
        // 文件上传的请求体封装
        val multipartBody = part.build()

        LogUtils.d("OkHttp", "uploadUrl ->$url")
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer ${CommonSPEngine.getToken()}")
            .post(multipartBody)
            .build()
        val call = client.newCall(request)
        call.enqueue(callback)
    }

    fun upLoadImgs(url: String, filePath: List<String>, fileParameter: String, parameter: Map<String, String>?, callback: Callback, listener: ProgressListener? = null) {
        val client = OkHttpClient.Builder().build()
        // 设置文件以及文件上传类型封装
        val part = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
        val indexMap = HashMap<String, Int>()
        filePath.forEach {
            val file = UploadFromBase64.pathToFile(it)
//            val requestBody = RequestBody.create(MediaType.parse("image/${getExtensionName(it)}"), file)
            val lisTemp = object : ProgressListener {
                override fun onProgress(totalBytes: Long, remainingBytes: Long, done: Boolean) {
                    indexMap[this.toString()] = ((totalBytes.toFloat() - remainingBytes.toFloat()) / totalBytes.toFloat() * 100).toInt()
                    if (indexMap.size == filePath.size) {
                        countToIndex(indexMap, listener)
                    }
                }
            }
            indexMap[lisTemp.toString()] = 0
            val requestBody = createCustomRequestBody(MediaType.parse("image/${getExtensionName(it)}"), file, lisTemp)
            part.addFormDataPart(fileParameter, file.name, requestBody)
        }

        val keys = parameter?.keys
        keys?.forEach {
            part.addFormDataPart(it, parameter[it].toString())
        }
        // 文件上传的请求体封装
        val multipartBody = part.build()

        LogUtils.d("OkHttp", "uploadUrl ->$url")
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer ${CommonSPEngine.getToken()}")
            .post(multipartBody)
            .build()
        val call = client.newCall(request)
        call.enqueue(callback)
    }


    fun upLoadVideo(url: String, filePath: String, fileParameter: String, parameter: Map<String, String>?, callback: Callback, listener: ProgressListener? = null) {
        val file = File(filePath)
        val client = OkHttpClient.Builder().build()
        // 设置文件以及文件上传类型封装
//        val requestBody = RequestBody.create(MediaType.parse("video/${getExtensionName(filePath)}"), file)
        val requestBody = createCustomRequestBody(MediaType.parse("video/${getExtensionName(filePath)}"), file, listener)
        val part = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(fileParameter, file.name, requestBody)
        val keys = parameter?.keys
        keys?.forEach {
            part.addFormDataPart(it, parameter[it].toString())
        }
        // 文件上传的请求体封装
        val multipartBody = part.build()

        LogUtils.d("OkHttp", "uploadUrl ->$url")
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer ${CommonSPEngine.getToken()}")
            .post(multipartBody)
            .build()
        val call = client.newCall(request)
        call.enqueue(callback)
    }

    fun countToIndex(map: HashMap<String, Int>, listener: ProgressListener?) {
        var countS = 0
        map.forEach {
            countS += it.value
        }
        listener?.onProgress(((100 * map.size).toLong()), countS.toLong(), countS == (100 * map.size))
    }

    /*
    *获取文件扩展名
    * */
    fun getExtensionName(filename: String?): String? {
        if (filename != null && filename.isNotEmpty()) {
            val dot = filename.lastIndexOf('.')
            if (dot > -1 && dot < filename.length - 1) {
                return filename.substring(dot + 1)
            }
        }
        return filename
    }

    private fun createCustomRequestBody(contentType: MediaType?, file: File, listener: ProgressListener?): RequestBody {
        return object : RequestBody() {
            override fun contentType(): MediaType? {
                return contentType
            }

            override fun contentLength(): Long {
                return file.length()
            }

            @Throws(IOException::class)
            override fun writeTo(sink: BufferedSink) {
                val source: Source
                try {
                    source = Okio.source(file)
                    //sink.writeAll(source);
                    val buf = Buffer()
                    var remaining = contentLength()
                    var readCount: Long
                    while (source.read(buf, 2048).also { readCount = it } != -1L) {
                        sink.write(buf, readCount)
                        listener?.onProgress(contentLength(), readCount.let { remaining -= it; remaining }, remaining == 0L)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    interface ProgressListener {
        fun onProgress(totalBytes: Long, remainingBytes: Long, done: Boolean)
    }

    interface FileLoadListener {
        fun onFail()
        fun onSuccess(file: File)
    }
}