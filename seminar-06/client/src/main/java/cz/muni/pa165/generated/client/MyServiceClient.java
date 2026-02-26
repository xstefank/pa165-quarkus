package cz.muni.pa165.generated.client;

import cz.muni.pa165.generated.client.api.MyServiceApi;
import cz.muni.pa165.generated.client.model.HelloResponse;
import io.quarkus.logging.Log;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@QuarkusMain
public class MyServiceClient implements QuarkusApplication {

    @Inject
    @RestClient
    MyServiceApi myServiceApi;

    @Override
    public int run(String... args) throws Exception {
        Log.info("running...");

        HelloResponse hello = myServiceApi.hello();
        Log.info("hello: " + hello);

        return 0;
    }
}
