package com.test.benchmark.grpc;

import com.test.benchmark.BenchmarkTask;
import com.test.benchmark.ConcurrentBenchmark;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.test.benchmark.proto.EchoGrpc;
import org.test.benchmark.proto.Msg;
import org.test.benchmark.proto.Resp;

import java.util.concurrent.TimeUnit;

public class GrpcBenchMark extends ConcurrentBenchmark {

    private ManagedChannel channel = null;
    private EchoGrpc.EchoBlockingStub stub = null;

    public GrpcBenchMark(int defaultThreadCount, long defaultLoopCount) {
        super(defaultThreadCount, defaultLoopCount);
    }

    @Override
    protected void setUp() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50000).usePlaintext().build();
        stub = EchoGrpc.newBlockingStub(channel);
    }

    @Override
    protected void tearDown() throws Exception {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    @Override
    protected BenchmarkTask createTask() {
        return new GrpcRequestTask();
    }

    public class GrpcRequestTask extends BenchmarkTask {

        @Override
        public void execute(int requestSequence) {
            Resp resp = stub.echo(Msg.newBuilder().setMsg("hello").build());
            resp.getResp();
        }
    }

    public static void main(String[] args) throws Exception {
        final GrpcBenchMark benchMark = new GrpcBenchMark(10, 100);
        benchMark.execute();
    }
}
