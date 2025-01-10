package streaming.streaming_service.streaming.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import streaming.streaming_service.streaming.application.service.HttpRangeVideoService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class StreamingController {

    private final HttpRangeVideoService videoService;

    @GetMapping(value = "video/{teamName}")
    public ResponseEntity<ResourceRegion> transmit(@RequestHeader HttpHeaders headers, @PathVariable("teamName") String teamName) throws IOException {
        ResourceRegion video = videoService.transmitVideo(headers, teamName);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(video.getResource()).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(video);
    }
}
