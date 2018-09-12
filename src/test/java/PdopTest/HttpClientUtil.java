package PdopTest;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;


public class HttpClientUtil {

    public static final String DEFAULT_ENCODING = "UTF-8";

    public static final String ENCODING_GZIP = "GZIP";

    public static String simulateUrlPostRequest(String requestURL,
                                                String charset)
            throws Exception {
        return simulateUrlPostRequest(requestURL, new String[0],
                new String[0], charset);
    }

    /**
     * 模拟表单发送POST形式
     *
     * @param requestURL  请求路径
     * @param params      参数
     * @param paramValues 参数值
     * @param charset     编码
     * @return
     */
    public static String simulateUrlPostRequest(String requestURL,
                                                List<String> params, List<String> paramValues, String charset)
            throws Exception {
        if (params.size() > 0) {
            return simulateUrlPostRequest(requestURL,
                    (String[]) params.toArray(new String[params.size()]),
                    (String[]) paramValues.toArray(new String[paramValues
                            .size()]), charset);
        } else {
            return simulateUrlPostRequest(requestURL, new String[0],
                    new String[0], charset);
        }
    }

    /**
     * 模拟表单发送POST形式
     *
     * @param requestURL  请求路径
     * @param params      参数
     * @param paramValues 参数值
     * @param charset     编码
     * @return
     */
    public static String simulateUrlPostRequest(String requestURL,
                                                String[] params, String[] paramValues, String charset)
            throws Exception {
        int paramCount = params.length;
        //
        NameValuePair[] parametersBody = new NameValuePair[paramCount];
        // 填入各个表单域的值
        for (int i = 0; i < paramCount; i++) {
            NameValuePair nvp = new NameValuePair(params[i], paramValues[i]);
            parametersBody[i] = nvp;
        }
        //
        return simulateUrlPostRequest(requestURL, parametersBody, charset);
    }

    /**
     * 模拟表单发送POST形式
     *
     * @param requestURL     请求路径
     * @param parametersBody 参数值对数组NameValuePair[]
     * @param charset        编码
     * @return
     */
    public static String simulateUrlPostRequest(String requestURL,
                                                List<NameValuePair> parametersBody, String charset)
            throws Exception {
        if (parametersBody != null && parametersBody.size() > 0) {
            return simulateUrlPostRequest(requestURL,
                    (NameValuePair[]) parametersBody
                            .toArray(new NameValuePair[parametersBody.size()]),
                    charset);
        } else {
            return simulateUrlPostRequest(requestURL, new NameValuePair[0],
                    charset);
        }
    }

    /**
     * 模拟表单发送POST形式
     *
     * @param requestURL     请求路径
     * @param parametersBody 参数值对数组NameValuePair[]
     * @param charset        编码
     * @return
     */
    public static String simulateUrlPostRequest(String requestURL,
                                                NameValuePair[] parametersBody, String charset) throws Exception {
        // 返回结果
        return simulateUrlPostRequest(requestURL, parametersBody, charset, true);
    }

    /**
     * 模拟表单发送POST形式
     *
     * @param requestURL     请求路径
     * @param parametersBody 参数值对数组NameValuePair[]
     * @param charset        编码
     * @return
     */
    public static String simulateUrlPostRequest(String requestURL,
                                                NameValuePair[] parametersBody, String charset, boolean checkStatus)
            throws Exception {
        // new httpclient
        HttpClient httpClient = new HttpClient();
        // set param prop
        httpClient.getParams().setParameter(
                HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
        // 超时设置
        httpClient.getHttpConnectionManager().getParams()
                .setConnectionTimeout(10000); // 链接超时，10秒
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(10000);// 读取超时,10秒
        // new postMethod
        PostMethod postMethod = new PostMethod(requestURL);
        // 将表单的值放入postMethod中
        if (parametersBody != null && parametersBody.length > 0) {
            postMethod.setRequestBody(parametersBody);
        }
        // 执行postMethod
        int statusCode = 0;
        String responseStr = ""; // 返回结果
        try {
            statusCode = httpClient.executeMethod(postMethod);
            // HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
            // 301或者302
            if (checkStatus) {
                if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
                        || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                    // 从头中取出转向的地址
                    Header locationHeader = postMethod
                            .getResponseHeader("location");
                    String location = null;
                    if (locationHeader != null) {
                        location = locationHeader.getValue();
                        // 递归
                        return simulateUrlPostRequest(location, parametersBody,
                                charset, false);
                    }
                }
            }
            responseStr = postMethod.getResponseBodyAsString();

        } catch (IOException e) {
            throw new Exception(e);
        } finally {
            // 释放连接
            postMethod.releaseConnection();
        }

        // 返回结果
        return responseStr;
    }

    /**
     * 模拟URL路径请求 GET形式
     */
    public static String simulateUrlGetRequest(String requestURL) throws Exception {
        return simulateUrlGetRequest(requestURL, DEFAULT_ENCODING);
    }

    /**
     * 模拟URL路径请求 GET形式
     *
     * @param requestURL 请求路径
     * @param charset    字符编码
     * @return
     */
    public static String simulateUrlGetRequest(String requestURL, String charset)
            throws Exception {
        return simulateUrlGetRequest(requestURL, charset, (Header[]) null);
    }

    public static String simulateUrlGetRequest(String requestURL, String charset, Header... headers) throws Exception {
        //
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setParameter(
                HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
        // 超时设置
        httpClient.getHttpConnectionManager().getParams()
                .setConnectionTimeout(10000); // 链接超时，10秒
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(10000);// 读取超时,10秒

        //
        GetMethod getMethod = new GetMethod(requestURL);

        if (headers != null) {
            for (Header header : headers) {
                getMethod.setRequestHeader(header);
            }
        }

        // 执行getMethod
        int statusCode = 0;
        String responseStr = null;

        try {
            statusCode = httpClient.executeMethod(getMethod);

            Header contentEncoding = getMethod.getResponseHeader("Content-Encoding");
            if (contentEncoding == null) {
                contentEncoding = getMethod.getResponseHeader("Content-Type");
            }

            if (contentEncoding != null) {
                String encoding = contentEncoding.getValue();
                if (StringUtil.isNotEmpty(encoding)) {
                    switch (encoding.toUpperCase()) {
                        case ENCODING_GZIP:
                            responseStr = new String(GzipUtil.uncompress(getMethod.getResponseBody()), DEFAULT_ENCODING);
                            break;
                        default:
                            InputStream is = getMethod.getResponseBodyAsStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            StringBuilder stringBuilder = new StringBuilder();
                            String str;
                            while ((str = br.readLine()) != null) {
                                stringBuilder.append(new String(str.getBytes(), DEFAULT_ENCODING));
                            }

                            responseStr = stringBuilder.toString();
                    }
                }
            }
        } catch (IOException e) {
            throw new Exception(e);
        } finally {
            // 释放连接
            getMethod.releaseConnection();
        }
        return responseStr;
    }

    /**
     * 发送post请求
     *
     * @param jsonData
     * @param url
     * @return
     */
    public static String sendPostMessage(String jsonData, String url) {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 超时设置
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();    //设置请求和传输超时时间 30秒
        httpClientBuilder.setDefaultRequestConfig(requestConfig);

        String result = "";

        try (CloseableHttpClient closeableHttpClient = httpClientBuilder.build()) {
            HttpPost post = new HttpPost(url);

            HttpEntity entity = new StringEntity(jsonData, DEFAULT_ENCODING);

            post.setEntity(entity);

            post.setHeader("Content-type", "application/json");

            HttpResponse resp = closeableHttpClient.execute(post);

            result = convertResponseBytes2JsonObj(resp);


        } catch (Exception ex) {

            ex.printStackTrace();

        }

        return result;

    }

    /**
     * 以json报文形式发送POST请求
     *
     * @param requestURL 请求url
     * @param jsonBody   json报文
     * @param headers    请求头部
     * @return 结果报文
     */
    public static String sendPostMessage(String requestURL, String jsonBody, org.apache.http.Header... headers) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 超时设置
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();    //设置请求和传输超时时间 30秒
        httpClientBuilder.setDefaultRequestConfig(requestConfig);

        String result = "";

        try (CloseableHttpClient closeableHttpClient = httpClientBuilder.build()) {
            HttpPost post = new HttpPost(requestURL);

            HttpEntity entity = new StringEntity(jsonBody, DEFAULT_ENCODING);

            post.setEntity(entity);

            if (headers != null) {
                post.setHeaders(headers);
            }

            HttpResponse resp = closeableHttpClient.execute(post);

            result = convertResponseBytes2JsonObj(resp);

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        return result;
    }

    private static String convertResponseBytes2JsonObj(HttpResponse resp) {
        String result = "";
        try {
            InputStream respIs = resp.getEntity().getContent();
            byte[] respBytes = IOUtils.toByteArray(respIs);
            result = new String(respBytes, Charset.forName("UTF-8"));

            if (result.length() == 0) {
                // TODO
                System.err.println("无响应");
            } else {
                /*System.out.println(result);*/
                if (result.startsWith("{") && result.endsWith("}")) {
                } else {
                    // TODO
                    System.err.println("不能转成JSON对象");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
