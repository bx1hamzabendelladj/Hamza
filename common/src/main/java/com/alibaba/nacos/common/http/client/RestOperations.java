package com.alibaba.nacos.common.http.client;

import com.alibaba.nacos.common.http.param.Header;
import com.alibaba.nacos.common.http.param.Query;
import com.alibaba.nacos.common.model.RestResult;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Interface specifying a basic set of RESTful operations.
 *
 * @author mai.jh
 * @date 2020/5/23
 */
public interface RestOperations {

    /**
     * http get
     *
     * @param url          url
     * @param responseType return type
     * @param header       http header param
     * @param query        http query param
     * @return the RestResult
     * @throws Exception ex
     */
    <T> RestResult<T> get(String url, Type responseType, Header header, Query query) throws Exception;

    /**
     * http get
     *
     * @param url          url
     * @param responseType return type
     * @param headers      headers
     * @param paramValues  paramValues
     * @return the RestResult
     * @throws Exception ex
     */
    <T> RestResult<T> get(String url, Type responseType, List<String> headers, Map<String, String> paramValues) throws Exception;

    /**
     * http get
     *
     * @param url          url
     * @param responseType return type
     * @param header       http header param
     * @param query        http query param
     * @return the RestResult
     * @throws Exception ex
     */
    <T> RestResult<T> get(String url, Header header, Query query, Type responseType) throws Exception;

    /**
     * get request, may be pulling a lot of data
     *
     * @param url          url
     * @param header       http header param
     * @param query        http query param
     * @param body         get with body
     * @param responseType return type
     * @return {@link RestResult <T>}
     * @throws Exception ex
     */
    <T> RestResult<T> getLarge(String url, Header header, Query query, Object body,
                               Type responseType) throws Exception;

    /**
     * http delete
     *
     * @param url          url
     * @param header       http header param
     * @param query        http query param
     * @param responseType return type
     * @return {@link RestResult <T>}
     * @throws Exception ex
     */
    <T> RestResult<T> delete(String url, Header header, Query query,
                             Type responseType) throws Exception;

    /**
     * http put
     *
     * @param url          url
     * @param header       http header param
     * @param query        http query param
     * @param body         http body param
     * @param responseType return type
     * @return {@link RestResult}
     * @throws Exception ex
     */
    <T> RestResult<T> put(String url, Header header, Query query, Object body,
                          Type responseType) throws Exception;


    /**
     * http put Json
     *
     * @param url          url
     * @param headers      http header param
     * @param paramValues  http query param
     * @param body         http body param
     * @param responseType return type
     * @return {@link RestResult}
     * @throws Exception ex
     */
    <T> RestResult<T> putJson(String url, List<String> headers, Map<String, String> paramValues, String body,
                              Type responseType) throws Exception;


    /**
     * http put from
     *
     * @param url          url
     * @param headers      http header param
     * @param paramValues  http query param
     * @param bodyValues   http body param
     * @param responseType return type
     * @return {@link RestResult}
     * @throws Exception ex
     */
    <T> RestResult<T> putFrom(String url, List<String> headers, Map<String, String> paramValues,
                              Map<String, String> bodyValues, Type responseType) throws Exception;


    /**
     * http post
     *
     * @param url          url
     * @param header       http header param
     * @param query        http query param
     * @param body         http body param
     * @param responseType return type
     * @return {@link RestResult}
     * @throws Exception ex
     */
    <T> RestResult<T> post(String url, Header header, Query query, Object body,
                           Type responseType) throws Exception;

    /**
     * http post Json
     *
     * @param url          url
     * @param headers      http header param
     * @param paramValues  http query param
     * @param body         http body param
     * @param responseType return type
     * @return {@link RestResult}
     * @throws Exception ex
     */
    <T> RestResult<T> postJson(String url, List<String> headers, Map<String, String> paramValues, String body,
                               Type responseType) throws Exception;


    /**
     * http post from
     *
     * @param url          url
     * @param headers      http header param
     * @param paramValues  http query param
     * @param bodyValues   http body param
     * @param responseType return type
     * @return {@link RestResult}
     * @throws Exception ex
     */
    <T> RestResult<T> postFrom(String url, List<String> headers, Map<String, String> paramValues,
                               Map<String, String> bodyValues, Type responseType) throws Exception;
}
