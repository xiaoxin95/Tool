package com.unipock.unipaytool.http

class HttpResponse<T> {

    var code: Int = -1
    var msg: String? = null
    var value: T? = null

    override fun toString(): String {
        return "{" +
                "\"code\":" + code +
                ", \"msg\":\"" + msg + "\"" +
                ", \"value\":" + value +
                '}'.toString()
    }


}
