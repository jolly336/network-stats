package com.nelson.stats.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * HTTP 统计实体类
 *
 * Created by Nelson on 2019-11-29.
 */
@Entity(tableName = NetworkCallEntity.TABLE_NAME)
public class NetworkCallEntity {

    public static final String TABLE_NAME = "networkCall";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    // 当前用户 id
    @ColumnInfo(name = "user_id")
    public String userId;

    // 当前用户 name
    @ColumnInfo(name = "user_name")
    public String userName;

    // 请求链接
    @ColumnInfo(name = "url")
    public String url;

    // 请求域名
    @ColumnInfo(name = "domain")
    public String domain;

    // 请求类型 GET、POST
    @ColumnInfo(name = "method")
    public String method;

    // 网络请求/响应字节数
    @ColumnInfo(name = "total_bytes")
    public long totalBytes;

    // 网络请求字节数
    @ColumnInfo(name = "request_bytes")
    public long requestBytes;

    // 网络响应字节数
    @ColumnInfo(name = "response_bytes")
    public long responseBytes;

    // 网络响应状态码
    @ColumnInfo(name = "response_code")
    public int responseCode;

    // 请求时间戳
    @ColumnInfo(name = "timestamp")
    public long timestamp;

    // 请求格式化时间 yyyy-MM-dd HH:mm:ss
    @ColumnInfo(name = "format_ts")
    public String formatTimestamp;

    @Override
    public String toString() {
        return "NetworkCallEntity{user_id="
                + userId
                + ", user_name="
                + userName
                + ", url="
                + url
                + ", method="
                + method
                + ", total_bytes="
                + totalBytes
                + ", request_bytes="
                + requestBytes
                + ", response_bytes="
                + responseBytes
                + ", format_ts="
                + formatTimestamp
                + '}';
    }
}
