package streaming.streaming_service.streaming.application.usecase;

import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.MalformedURLException;

public interface VideoFunction {
    ResourceRegion transmitVideo(HttpHeaders headers, String teamName) throws IOException;

}
