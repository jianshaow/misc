package com.test.benchmark.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.test.benchmark.BenchmarkTask;
import com.test.benchmark.ConcurrentBenchmark;

public class HttpBenchMark extends ConcurrentBenchmark {

    private CloseableHttpClient httpclient;

    private String url;

    public HttpBenchMark(int defaultThreadCount, long defaultLoopCount, String url) {
        super(defaultThreadCount, defaultLoopCount);
        this.url = url;
    }

    @Override
    protected void setUp() {
        httpclient = HttpClients.custom().setMaxConnPerRoute(super.getThreadCount())
                .setMaxConnTotal(super.getThreadCount())
                .build();
    }

    @Override
    protected BenchmarkTask createTask() {
        return new HttpRequestTask();
    }

    public class HttpRequestTask extends BenchmarkTask {

        public HttpRequestTask() {
        }

        @Override
        protected void execute(@SuppressWarnings("unused") int requestSequence) {
            final HttpGet get = new HttpGet(url);
            try {
                final CloseableHttpResponse response = httpclient.execute(get);
                final HttpEntity entity = response.getEntity();
                EntityUtils.consume(entity);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void tearDown() throws Exception {
        httpclient.close();
    }

    public static void main(String[] args) throws Exception {
        final String url = args[0];
        final HttpBenchMark benchMark = new HttpBenchMark(10, 100, url);
        benchMark.execute();
    }
}
