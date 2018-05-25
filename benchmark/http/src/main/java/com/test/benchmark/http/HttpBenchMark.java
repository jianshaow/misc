/*------------------------------------------------------------------------------
 * COPYRIGHT Ericsson 2015
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *----------------------------------------------------------------------------*/
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
        httpclient = HttpClients.custom().setMaxConnPerRoute(super.threadCount).setMaxConnTotal(super.threadCount)
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
        int threadCount = Integer.valueOf(args[0]);
        int loopCount = Integer.valueOf(args[1]);
        String url = args[2];
        final HttpBenchMark benchMark = new HttpBenchMark(threadCount, loopCount, url);
        benchMark.execute();
    }
}
