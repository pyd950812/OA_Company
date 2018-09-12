//package com.pengyd.util;
//
//import com.alibaba.fastjson.JSON;
//import com.jufan.install.common.RequestException;
//import com.jufan.install.common.ResCode;
//import io.netty.channel.ConnectTimeoutException;
//import org.apache.commons.collections4.MapUtils;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.time.StopWatch;
//import org.apache.http.*;
//import org.apache.http.client.HttpRequestRetryHandler;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpRequestBase;
//import org.apache.http.client.protocol.HttpClientContext;
//import org.apache.http.client.utils.URIBuilder;
//import org.apache.http.config.Registry;
//import org.apache.http.config.RegistryBuilder;
//import org.apache.http.conn.socket.ConnectionSocketFactory;
//import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
//import org.apache.http.conn.socket.PlainConnectionSocketFactory;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.entity.mime.content.InputStreamBody;
//import org.apache.http.entity.mime.content.StringBody;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.message.HeaderGroup;
//import org.apache.http.protocol.HttpContext;
//import org.apache.http.util.EntityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.net.ssl.SSLException;
//import javax.net.ssl.SSLHandshakeException;
//import java.io.*;
//import java.net.SocketTimeoutException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.UnknownHostException;
//import java.nio.charset.Charset;
//import java.util.*;
//
///**
// * http连接池工具类  支持http https协议
// *
// * @author dengfu
// */
//public class HttpClientPoolUtil {
//    public static final String DEFAULT_ENCODING = "UTF-8";
//
//    public static final String ENCODING_GZIP = "GZIP";
//    private static Logger logger = LoggerFactory.getLogger(HttpClientPoolUtil.class);
//
//    // http连接池管理类
//    private static final PoolingHttpClientConnectionManager poolConnectionManager;
//
//    // 最大连接数
//    private static final int maxTotal = 300;
//
//    // 路由的最大连接数
//    private static final int maxRoute = 150;
//
//    //从连接池获取连接超时的时间
//    private static final int reqTimeout = 6 * 1000;
//
//    // 连接超时时间
//    private static final int connTimeOut = 5 * 1000;
//
//    // 读取超时时间
//    private static final int readTimeOut = 6 * 1000;
//
//    static {
//        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
//        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
//        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
//                .register("http", plainsf).register("https", sslsf).build();
//
//        poolConnectionManager = new PoolingHttpClientConnectionManager(registry);
//        // 设置最大连接数
//        poolConnectionManager.setMaxTotal(maxTotal);
//        // 设置路由的最大连接数
//        poolConnectionManager.setDefaultMaxPerRoute(maxRoute);
//
//    }
//
//    private static CloseableHttpClient getHttpClient() {
//        // 请求重试处理
//        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
//            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
//                if (executionCount >= 5) {// 如果已经重试了5次，就放弃
//                    return false;
//                }
//                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
//                    return true;
//                }
//                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
//                    return false;
//                }
//                if (exception instanceof InterruptedIOException) {// 超时
//                    return false;
//                }
//                if (exception instanceof UnknownHostException) {// 目标服务器不可达
//                    return false;
//                }
//                if (exception instanceof SSLException) {// SSL握手异常
//                    return false;
//                }
//
//                HttpClientContext clientContext = HttpClientContext.adapt(context);
//                HttpRequest request = clientContext.getRequest();
//                // 如果请求是幂等的，就再次尝试
//                if (!(request instanceof HttpEntityEnclosingRequest)) {
//                    return true;
//                }
//                return false;
//            }
//        };
//
//        return HttpClients.custom().setConnectionManager(poolConnectionManager).setRetryHandler(httpRequestRetryHandler)
//                .build();
//    }
//
//    /**
//     * 设置配置信息
//     *
//     * @return
//     */
//    private static void setConfig(HttpRequestBase httpRequestBase, Integer cTimeOut, Integer rTimeOut) {
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setConnectionRequestTimeout(reqTimeout)
//                .setConnectTimeout(cTimeOut != null ? cTimeOut : connTimeOut)
//                .setSocketTimeout(rTimeOut != null ? rTimeOut : readTimeOut).build();
//        httpRequestBase.setConfig(requestConfig);
//    }
//
//    /**
//     * GET请求
//     *
//     * @param url
//     * @param params
//     * @return
//     * @throws Exception
//     */
//    public static String doGet(String url, Map<String, String> params) throws Exception {
//        return doGet(url, params, new HashMap<String, String>(), null, null);
//    }
//
//    public static String doGet(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
//        return doGet(url, params, getHeaders(headers), null, null);
//    }
//
//    public static String doGet(String url, Map<String, String> params, Map<String, String> headers, Integer cTimeOut,
//                               Integer rTimeOut) throws Exception {
//        return doGet(url, params, getHeaders(headers), cTimeOut, rTimeOut);
//    }
//
//    public static String doPost(String url, Map<String, String> params) throws Exception {
//        return doPost(url, params, new HashMap<String, String>(), null, null);
//    }
//
//    public static String doPost(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
//        return doPost(url, params, getHeaders(headers), null, null);
//    }
//
//    public static String doPost(String url, Map<String, String> params, Map<String, String> headers, Integer cTimeOut,
//                                Integer rTimeOut) throws Exception {
//        return doPost(url, params, getHeaders(headers), cTimeOut, rTimeOut);
//    }
//
//    public static String doPostByJson(String url, String json) throws Exception {
//        return doPostByJson(url, json, new HashMap<String, String>(), null, null);
//    }
//
//    public static String doPostByJson(String url, String json, Map<String, String> headers) throws Exception {
//        return doPostByJson(url, json, getHeaders(headers), null, null);
//    }
//
//    public static String doPostByJson(String url, String json, Map<String, String> headers, Integer cTimeOut,
//                                      Integer rTimeOut) throws Exception {
//        return doPostByJson(url, json, getHeaders(headers), cTimeOut, rTimeOut);
//    }
//
//    /**
//     * get方式提交请求
//     *
//     * @param url
//     * @param headers
//     * @param cTimeOut
//     * @param rTimeOut
//     * @return
//     * @throws Exception
//     */
//    private static String doGet(String url, Map<String, String> params, Header[] headers, Integer cTimeOut,
//                                Integer rTimeOut) throws Exception {
//        StopWatch sw = new StopWatch();
//        sw.start();
//        HttpGet httpGet = null;
//        CloseableHttpResponse response = null;
//        String rspValue = "";
//        try {
//            httpGet = new HttpGet(buildUri(url, params));
//            httpGet.addHeader("Content-Encoding", "UTF-8");
//            if (headers != null) {
//                httpGet.setHeaders(headers);
//            }
//            setConfig(httpGet, cTimeOut, rTimeOut);
//            response = getHttpClient().execute(httpGet);
//            int statusCode = response.getStatusLine().getStatusCode();
//            logger.info("http请求返回的状态码 statusCode= {}", statusCode);
//            if (statusCode == HttpStatus.SC_OK) {
//                HttpEntity entity = response.getEntity();
//                if (entity != null)
//                    rspValue = EntityUtils.toString(entity, "UTF-8");
//            }
//        } catch (SocketTimeoutException sx) {
//            logger.info("读取超时", sx);
//            throw sx;
//        } catch (Exception e) {
//            logger.error("doGet url is {},exception is {}", url, e);
//            throw new Exception(e.getMessage());
//        } finally {
//            closeConnection(httpGet, response);
//        }
//        sw.stop();
//        //logger.debug("doGet url is {}, response is {}, invoke time cost is {} ms.", url, rspValue, sw.getTime());
//        return rspValue;
//    }
//
//    /**
//     * 下载文件
//     *
//     * @param url      远程文件地址
//     * @param filePath 本地保存地址
//     * @param cTimeOut
//     * @param rTimeOut
//     * @throws Exception
//     */
//    public static void doGetImage(String url, String filePath, Integer cTimeOut, Integer rTimeOut) throws Exception {
//        StopWatch sw = new StopWatch();
//        sw.start();
//        HttpGet httpGet = null;
//        CloseableHttpResponse response = null;
//        try {
//            httpGet = new HttpGet(buildUri(url, null));
//            httpGet.addHeader("Content-Encoding", "UTF-8");
//            setConfig(httpGet, cTimeOut, rTimeOut);
//            response = getHttpClient().execute(httpGet);
//            int statusCode = response.getStatusLine().getStatusCode();
//            logger.info("http请求返回的状态码 statusCode= {}", statusCode);
//            if (statusCode == HttpStatus.SC_OK) {
//                InputStream inputStream = response.getEntity().getContent();
//                File file = new File(filePath);
//                File path = new File(file.getParent());
//                if (!path.exists()) {
//                    path.mkdirs();
//                }
//                FileOutputStream fos = new FileOutputStream(file);
//                byte[] data = new byte[1024];
//                int len = 0;
//                while ((len = inputStream.read(data)) != -1) {
//                    fos.write(data, 0, len);
//                }
//                inputStream.close();
//                fos.flush();
//                fos.close();
//            } else {
//                throw new Exception("http请求异常 " + statusCode);
//            }
//        } catch (SocketTimeoutException sx) {
//            logger.info("读取超时", sx);
//            throw sx;
//        } catch (Exception e) {
//            logger.error("doGet url is {},exception is {}", url, e);
//            throw new Exception(e.getMessage());
//        } finally {
//            closeConnection(httpGet, response);
//        }
//        sw.stop();
//    }
//
//    /**
//     * httpPost请求
//     *
//     * @param url
//     * @param param
//     * @param headers
//     * @param cTimeOut
//     * @param rTimeOut
//     * @return
//     * @throws Exception
//     */
//    private static String doPost(String url, Map<String, String> param, Header[] headers, Integer cTimeOut,
//                                 Integer rTimeOut) throws Exception {
//        StopWatch sw = new StopWatch();
//        sw.start();
//        HttpPost httpPost = null;
//        CloseableHttpResponse response = null;
//        String rspValue = "";
//        try {
//            httpPost = new HttpPost(url);
//            httpPost.addHeader("Content-Encoding", "UTF-8");
//            if (headers != null) {
//                httpPost.setHeaders(headers);
//            }
//            setConfig(httpPost, cTimeOut, rTimeOut);
//
//            List<NameValuePair> paraList = new ArrayList<NameValuePair>();
//            Charset charset = Charset.forName("UTF-8");
//            HttpEntity entity = null;
//            if (MapUtils.isNotEmpty(param)) {
//                for (String key : param.keySet()) {
//                    paraList.add(new BasicNameValuePair(key, param.get(key)));
//                }
//                entity = new UrlEncodedFormEntity(paraList, charset);
//            }
//            httpPost.setEntity(entity);
//            response = getHttpClient().execute(httpPost);
//            int statusCode = response.getStatusLine().getStatusCode();
//            logger.info("http请求返回的状态码 statusCode= {}", statusCode);
//            if (statusCode == HttpStatus.SC_OK) {
//                entity = response.getEntity();
//                if (entity != null)
//                    rspValue = EntityUtils.toString(entity, "UTF-8");
//            }
//        } catch (Exception e) {
//            logger.error("doPost url is {},exception is {}", url, e);
//            throw new Exception(e.getMessage());
//        } finally {
//            closeConnection(httpPost, response);
//        }
//        sw.stop();
//        logger.debug("doPost url is {}, response is {}, invoke time cost is {} ms.", url, rspValue, sw.getTime());
//        return rspValue;
//    }
//
//    /**
//     * httpPost请求 json方式
//     *
//     * @param url
//     * @param json
//     * @param headers
//     * @param cTimeOut
//     * @param rTimeOut
//     * @return
//     */
//    private static String doPostByJson(String url, String json, Header[] headers,
//                                       Integer cTimeOut, Integer rTimeOut)
//            throws Exception {
//        StopWatch sw = new StopWatch();
//        sw.start();
//        HttpPost httpPost = null;
//        CloseableHttpResponse response = null;
//        String rspValue = "";
//        try {
//            httpPost = new HttpPost(url);
//            httpPost.addHeader("Content-Encoding", "UTF-8");
//            if (headers != null) {
//                httpPost.setHeaders(headers);
//            }
//            httpPost.addHeader("Content-Type", "application/json");
//            setConfig(httpPost, cTimeOut, rTimeOut);
//            StringEntity requestEntity = new StringEntity(json, ContentType.create("application/json", Consts.UTF_8));
//            httpPost.setEntity(requestEntity);
//            response = getHttpClient().execute(httpPost);
//            int statusCode = response.getStatusLine().getStatusCode();
//            logger.info("http请求返回的状态码 statusCode= {}", statusCode);
//            if (response == null || statusCode != HttpStatus.SC_OK) {
//                throw new RequestException();
//            }
//            if (statusCode == HttpStatus.SC_OK) {
//                HttpEntity entity = response.getEntity();
//                if (entity != null)
//                    rspValue = EntityUtils.toString(entity, "UTF-8");
//            }
//        } catch (RequestException e) {
//            logger.error("doPost接口请求返回为NULL或者返回结果失败 url is {}, param is {}, exception is {}", url, json, e);
//            throw new RequestException(ResCode.OUTAPI_RESPONSE_ERROR.getCode(), ResCode.OUTAPI_RESPONSE_ERROR.getMsg());
//        } catch (SocketTimeoutException e) {
//            logger.error("doPost接口请求超时 url is {}, param is {}, exception is {}", url, json, e);
//            throw new RequestException(ResCode.OUTAPI_TIMEOUT_ERROR.getCode(), ResCode.OUTAPI_TIMEOUT_ERROR.getMsg());
//        } catch (ConnectTimeoutException e) {
//            logger.error("doPost接口请求链接超时 url is {}, param is {}, exception is {}", url, json, e);
//            throw new RequestException(ResCode.OUTAPI_TIMEOUT_ERROR.getCode(), ResCode.OUTAPI_TIMEOUT_ERROR.getMsg());
//        } catch (Exception e) {
//            logger.error("doPost url is {}, param is {}, exception is {}", url, json, e);
//            throw new Exception(e.getMessage());
//        } finally {
//            closeConnection(httpPost, response);
//        }
//        sw.stop();
//        logger.debug("doPost url is {}, param is {}, response is {}, invoke time cost is {} ms.", url, json, rspValue,
//                sw.getTime());
//        return rspValue;
//    }
//
//    /**
//     * httpPost请求通过流上传文件
//     *
//     * @param url
//     * @param param
//     * @param file
//     * @param headers
//     * @return
//     * @throws Exception
//     * @Title: uploadFile
//     * @date 2017年5月11日 下午4:20:09
//     * @author 邓夫
//     * @modifier
//     * @modifydate
//     */
//    public static String uploadFile(String url, Map<String, String> param, File file, Map<String, String> headers, Integer cTimeOut, Integer rTimeOut)
//            throws Exception {
//        StopWatch sw = new StopWatch();
//        sw.start();
//        HttpPost httpPost = null;
//        CloseableHttpResponse response = null;
//        String rspValue = "";
//        try {
//            httpPost = new HttpPost(url);
//            httpPost.addHeader("Content-Encoding", "UTF-8");
//            if (headers != null && headers.size() > 0) {
//                httpPost.setHeaders(getHeaders(headers));
//            }
//            List<NameValuePair> nameLst = new ArrayList<>();
//            for (String key : param.keySet()) {
//                nameLst.add(new BasicNameValuePair(key, param.get(key)));
//            }
//            FileBody fileBody = new FileBody(file);
//            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
//            for (NameValuePair nameValuePair : nameLst) {
//                multipartEntityBuilder.addPart(nameValuePair.getName(),
//                        new StringBody(nameValuePair.getValue(), ContentType.TEXT_PLAIN));
//            }
//            multipartEntityBuilder.addPart("file", fileBody);
//            HttpEntity httpEntity = multipartEntityBuilder.build();
//            httpPost.setEntity(httpEntity);
//
//            response = getHttpClient().execute(httpPost);
//            int statusCode = response.getStatusLine().getStatusCode();
//            logger.info("http请求返回的状态码 statusCode= {}", statusCode);
//            if (statusCode == HttpStatus.SC_OK) {
//                HttpEntity entity = response.getEntity();
//                if (entity != null)
//                    rspValue = EntityUtils.toString(entity, "UTF-8");
//            }
//        } catch (Exception e) {
//            logger.error("doPost url is {}, param is {}, exception is {}", url, JSON.toJSONString(param), e);
//            throw new Exception(e.getMessage());
//        } finally {
//            closeConnection(httpPost, response);
//        }
//        sw.stop();
//        logger.debug("doPost url is {}, param is {}, response is {}, invoke time cost is {} ms.", url,
//                JSON.toJSONString(param), rspValue, sw.getTime());
//        return rspValue;
//    }
//
//    /**
//     * 设置HTTP头
//     *
//     * @param headerMaps http头消息
//     * @return 头信息
//     */
//    public static Header[] getHeaders(Map<String, String> headerMaps) {
//        if (MapUtils.isNotEmpty(headerMaps)) {
//            HeaderGroup headerGroup = new HeaderGroup();
//            for (String key : headerMaps.keySet()) {
//                BasicHeader header = new BasicHeader(key, headerMaps.get(key));
//                headerGroup.addHeader(header);
//            }
//            return headerGroup.getAllHeaders();
//        }
//        return null;
//    }
//
//    /**
//     * 释放连接
//     *
//     * @param httpRequest
//     * @param httpResponse
//     */
//    private static void closeConnection(HttpRequestBase httpRequest, CloseableHttpResponse httpResponse) {
//        if (null != httpResponse) {
//            try {
//                EntityUtils.consume(httpResponse.getEntity());
//            } catch (IOException e) {
//                e.getStackTrace();
//            } finally {
//                if (httpResponse != null) {
//                    try {
//                        httpResponse.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//        if (null != httpRequest) {
//            if (null != httpRequest.getConfig()) {
//                int httpSocketTimeout = httpRequest.getConfig().getSocketTimeout();
//                int httpConnectionTimeout = httpRequest.getConfig().getConnectTimeout();
//                logger.debug("HttpConnectionTimeOut {}, HttpSockTimeOut {}", Integer.valueOf(httpConnectionTimeout), Integer.valueOf(httpSocketTimeout));
//            }
//            httpRequest.releaseConnection();
//        }
//    }
//
//    private static URI buildUri(String url, Map<String, String> paramMap) {
//        URI uri = null;
//        URIBuilder uriBuilder = null;
//        try {
//            uriBuilder = new URIBuilder(url);
//
//            if (paramMap != null && paramMap.size() > 0) {
//                Set<String> keySet = paramMap.keySet();
//                for (String key : keySet) {
//                    String value = paramMap.get(key);
//                    if (value != null) {
//                        uriBuilder.addParameter(key, value);
//                    }
//                }
//            }
//            uri = uriBuilder.build();
//        } catch (URISyntaxException localURISyntaxException) {
//        }
//        return uri;
//    }
//
//   /* public static String formUrlencodedPost(String url, Map<String, String> params) throws Exception {
//        URL u = null;
//        HttpURLConnection con = null;
//        // 构建请求参数
//        StringBuffer sb = new StringBuffer();
//        String sbStr = "";
//        if (params != null) {
//            for (Entry<String, String> e : params.entrySet()) {
//                sb.append(e.getKey());
//                sb.append("=");
//                sb.append(e.getValue());
//                sb.append("&");
//            }
//            sbStr = sb.substring(0, sb.length() - 1);
//        }
//        logger.info("url={}, send_data:{} ", url, sbStr);
//        // 尝试发送请求
//        try {
//            u = new URL(url);
//            con = (HttpURLConnection) u.openConnection();
//            //// POST 只能为大写，严格限制，post会不识别
//            con.setRequestMethod("POST");
//            con.setDoOutput(true);
//            con.setDoInput(true);
//            con.setUseCaches(false);
//            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
//            osw.write(sbStr);
//            osw.flush();
//            osw.close();
//        } catch (Exception e) {
//            logger.error("http请求报错", e);
//            throw new Exception(e);
//        } finally {
//            if (con != null) {
//                con.disconnect();
//            }
//        }
//        // 读取返回内容
//        StringBuffer buffer = new StringBuffer();
//        try {
//            //一定要有返回值，否则无法把请求发送给server端。
//            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
//            String temp;
//            while ((temp = br.readLine()) != null) {
//                buffer.append(temp);
//                buffer.append("\n");
//            }
//        } catch (Exception e) {
//            logger.error("读取内容报错", e);
//            throw new Exception(e);
//        }
//        logger.info("return str= {}", buffer.toString());
//        return buffer.toString();
//    }*/
//
//    /**
//     * post请求
//     * application/x-www-form-urlencoded方式
//     *
//     * @param url
//     * @param map
//     * @return
//     * @throws Exception
//     */
//    public static String formUrlencodedPost(String url, Map<String, String> map)
//            throws Exception {
//        return formUrlencodedPost(url, map, null, 7 * 1000, 30 * 1000);
//    }
//
//    public static String formUrlencodedPost(String url, Map<String, String> map, Header[] headers,
//                                            Integer cTimeOut, Integer rTimeOut) throws Exception {
//        logger.info("doPost url is {}, param is {}", url, map);
//        StopWatch sw = new StopWatch();
//        sw.start();
//        HttpPost httpPost = null;
//        CloseableHttpResponse response = null;
//        String rspValue = "";
//        try {
//            httpPost = new HttpPost(url);
//            httpPost.addHeader("Content-Encoding", "UTF-8");
//            if (headers != null) {
//                httpPost.setHeaders(headers);
//            }
//            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
//            setConfig(httpPost, cTimeOut, rTimeOut);
//            ArrayList<NameValuePair> pairList = new ArrayList<>();
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                BasicNameValuePair pair = new BasicNameValuePair(entry.getKey(),
//                        entry.getValue());
//                pairList.add(pair);
//            }
//            HttpEntity entity = new UrlEncodedFormEntity(pairList, "UTF-8");
//            httpPost.setEntity(entity);
//            response = getHttpClient().execute(httpPost);
//            int statusCode = response.getStatusLine().getStatusCode();
//            logger.info("http请求返回的状态码 statusCode= {}", statusCode);
//            if (statusCode != HttpStatus.SC_OK) {
//                throw new RequestException();
//            }
//            HttpEntity rspEntity = response.getEntity();
//            if (rspEntity != null)
//                rspValue = EntityUtils.toString(rspEntity, "UTF-8");
//            logger.info("post returnStr is {}", rspValue);
//            return rspValue;
//        } catch (RequestException e) {
//            logger.error("doPost接口请求返回为NULL或者返回结果失败 url is {}, param is {}, exception is {}", url, map, e);
//            throw new RequestException(ResCode.OUTAPI_RESPONSE_ERROR.getCode(), ResCode.OUTAPI_RESPONSE_ERROR.getMsg());
//        } catch (SocketTimeoutException e) {
//            logger.error("doPost接口请求超时 url is {}, param is {}, exception is {}", url, map, e);
//            throw new RequestException(ResCode.OUTAPI_TIMEOUT_ERROR.getCode(), ResCode.OUTAPI_TIMEOUT_ERROR.getMsg());
//        } catch (ConnectTimeoutException e) {
//            logger.error("doPost接口请求链接超时 url is {}, param is {}, exception is {}", url, map, e);
//            throw new RequestException(ResCode.OUTAPI_TIMEOUT_ERROR.getCode(), ResCode.OUTAPI_TIMEOUT_ERROR.getMsg());
//        } catch (Exception e) {
//            logger.error("doPost url is {}, param is {}, exception is {}", url, map, e);
//            throw new Exception(e.getMessage());
//        } finally {
//            closeConnection(httpPost, response);
//        }
//    }
//
//
//    public static String doFormData(String url, Map<String, Object> param) throws Exception {
//        return doFormData(url, param, null, null, null, null);
//    }
//
//    public static String doFormData(String url, Map<String, Object> param, File file, String fileName) throws Exception {
//        return doFormData(url, param, null, file, fileName, null);
//    }
//
//    public static String doFormData(String url, Map<String, Object> param, String fileName, InputStream inputStream) throws Exception {
//        return doFormData(url, param, null, null, fileName, inputStream);
//    }
//
//    /**
//     * form-data请求方式
//     *
//     * @param url
//     * @param param
//     * @param headers
//     * @param file        一个文件
//     * @param fileName
//     * @param inputStream 文件流
//     * @return
//     * @throws Exception
//     */
//    public static String doFormData(String url, Map<String, Object> param, Map<String, String> headers,
//                                    File file, String fileName, InputStream inputStream) throws Exception {
//        StopWatch sw = new StopWatch();
//        sw.start();
//        HttpPost httpPost = null;
//        CloseableHttpResponse response = null;
//        String rspValue = "";
//        try {
//            httpPost = new HttpPost(url);
//            httpPost.addHeader("Content-Encoding", "UTF-8");
//            if (headers != null && headers.size() > 0) {
//                httpPost.setHeaders(getHeaders(headers));
//            }
//            List<NameValuePair> nameLst = new ArrayList<>();
//            for (String key : param.keySet()) {
//                if (StringUtils.isBlank(param.get(key) == null ? null : param.get(key).toString())) {
//                    logger.info("param参数有空值");
//                    throw new RequestException("http请求参数异常");
//                }
//                nameLst.add(new BasicNameValuePair(key, param.get(key) == null ? null : param.get(key).toString()));
//            }
//            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
//            for (NameValuePair nameValuePair : nameLst) {
//                multipartEntityBuilder.addPart(nameValuePair.getName(),
//                        new StringBody(nameValuePair.getValue(), ContentType.create("text/plain", Consts.UTF_8)));
//            }
//            if (file != null) {
//                FileBody fileBody = new FileBody(file);
//                multipartEntityBuilder.addPart(fileName, fileBody);
//            }
//            if (inputStream != null) {
//                InputStreamBody inputStreamBody =
//                        new InputStreamBody(inputStream, fileName);
//                multipartEntityBuilder.addPart(fileName, inputStreamBody);
//            }
//
//            HttpEntity httpEntity = multipartEntityBuilder.build();
//            httpPost.setEntity(httpEntity);
//
//            response = getHttpClient().execute(httpPost);
//            int statusCode = response.getStatusLine().getStatusCode();
//            logger.info("http请求返回的状态码 statusCode= {}, response= {}", statusCode, response);
//            if (statusCode == HttpStatus.SC_OK) {
//                HttpEntity entity = response.getEntity();
//                if (entity != null)
//                    rspValue = EntityUtils.toString(entity, "UTF-8");
//            }
//        } catch (Exception e) {
//            logger.error("doPost url is {}, param is {}, exception is {}", url, JSON.toJSONString(param), e);
//            throw new Exception(e.getMessage());
//        } finally {
//            closeConnection(httpPost, response);
//        }
//        sw.stop();
//        logger.debug("doPost url is {}, param is {}, response is {}, invoke time cost is {} ms.", url,
//                JSON.toJSONString(param), rspValue, sw.getTime());
//        return rspValue;
//    }
//
//    public static String sendPostMessage(String requestURL, String jsonBody, Header... headers) {
//        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//        // 超时设置
//        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();    //设置请求和传输超时时间 30秒
//        httpClientBuilder.setDefaultRequestConfig(requestConfig);
//
//        String result = "";
//
//        try (CloseableHttpClient closeableHttpClient = httpClientBuilder.build()) {
//            HttpPost post = new HttpPost(requestURL);
//
//            HttpEntity entity = new StringEntity(jsonBody, DEFAULT_ENCODING);
//
//            post.setEntity(entity);
//
//            if (headers != null) {
//                post.setHeaders(headers);
//            }
//
//            HttpResponse resp = closeableHttpClient.execute(post);
//
//            result = convertResponseBytes2JsonObj(resp);
//
//        } catch (Exception ex) {
//
//            ex.printStackTrace();
//
//        }
//
//        return result;
//    }
//
//    private static String convertResponseBytes2JsonObj(HttpResponse resp) {
//        String result = "";
//        try {
//            InputStream respIs = resp.getEntity().getContent();
//            byte[] respBytes = IOUtils.toByteArray(respIs);
//            result = new String(respBytes, Charset.forName("UTF-8"));
//
//            if (result.length() == 0) {
//                // TODO
//                System.err.println("无响应");
//            } else {
//                /*System.out.println(result);*/
//                if (result.startsWith("{") && result.endsWith("}")) {
//                } else {
//                    // TODO
//                    System.err.println("不能转成JSON对象");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//}
