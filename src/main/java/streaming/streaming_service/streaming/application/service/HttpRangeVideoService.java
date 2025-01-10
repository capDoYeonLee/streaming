package streaming.streaming_service.streaming.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.stereotype.Service;
import streaming.streaming_service.streaming.application.usecase.VideoFunction;

import java.io.IOException;
import java.util.Optional;

@Service
public class HttpRangeVideoService implements VideoFunction {

    @Value("${cloud.aws.videoPath}")
    private String videoPath;
    private long contentLength;
    private final long chunkSize = 2_000_000L;
    private final String videoFormat = ".MP4";

    @Override
    public ResourceRegion transmitVideo(HttpHeaders headers, String teamName) throws IOException {

        HttpRange httpRange;
        ResourceRegion resourceRegion;
        UrlResource video = new UrlResource("url:" + videoPath + teamName + videoFormat);
        Optional<HttpRange> optional = aboutVideoInfo(headers, video);

        if (optional.isPresent()) {
            httpRange = optional.get();
            long start = httpRange.getRangeStart(contentLength);
            long end = httpRange.getRangeEnd(contentLength);
            long rangeLength = Long.min(chunkSize, end - start + 1);

            resourceRegion = new ResourceRegion(video, start, rangeLength);
        } else {
            /*
            첫 요청의 HttpHear 내부 HttpRange 값은 0 ~ 특정 구간
            Return 0 ~ 특정 구간의 영상 데이터 리턴.
             */
            long rangeLength = Long.min(chunkSize, contentLength);
            resourceRegion = new ResourceRegion(video, 0, rangeLength);
        }
        return resourceRegion;
    }




    private Optional<HttpRange> aboutVideoInfo(HttpHeaders headers, UrlResource video) throws IOException {
        contentLength = video.contentLength();
        Optional<HttpRange> optional = headers.getRange().stream().findFirst();
        return optional;
    }
}
