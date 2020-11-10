package com.unipock.unipaytool.http;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;


/**
 * 监听上传的数据
 * @author 被程序员耽误的音乐家
 */
public class UploadFileRequestBody extends RequestBody {

    private RequestBody mRequestBody;
    private IUpLoadMonitor mProgressListener;
    private BufferedSink bufferedSink;

    long percent = 0;

    public UploadFileRequestBody(File file, IUpLoadMonitor progressListener) {
        this.mRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        this.mProgressListener = progressListener;
    }

    public UploadFileRequestBody(RequestBody requestBody, IUpLoadMonitor progressListener) {
        this.mRequestBody = requestBody;
        this.mProgressListener = progressListener;
    }

    public UploadFileRequestBody(Map<String, Object> maps, IUpLoadMonitor progressListener) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (String key : maps.keySet()) {
            Object value = maps.get(key);
            if (value instanceof File) {
                File file = (File) maps.get(key);
                builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("image/jpg"), file));
            } else if (value instanceof List) {
                List<File> files = (List<File>) maps.get(key);
                for (int i = 0; i < files.size(); i++) {
                    builder.addFormDataPart(key, files.get(i).getName(), RequestBody.create(MediaType.parse("image/jpg"), files.get(i)));
                }
            } else {
                builder.addFormDataPart(key, value.toString() + "");
            }
        }
        this.mRequestBody = builder.build();
        this.mProgressListener = progressListener;
    }


    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        mRequestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    private Sink sink(Sink sink) {

        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调
                percent = (int) (bytesWritten * 100 / contentLength);
                if (percent > 100) {
                    percent = 100;
                }
                if (percent < 0) {
                    percent = 0;
                }
                mProgressListener.listener(percent);
            }

        };
    }

}
