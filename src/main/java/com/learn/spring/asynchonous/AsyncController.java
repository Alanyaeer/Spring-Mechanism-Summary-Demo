package com.learn.spring.asynchonous;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

/**
 * 异步控制器
 *
 * @author wps
 * @date 2025/08/10
 */
@RestController("/async")
@Slf4j
public class AsyncController {
    /**
     * 报价
     *
     * @return {@link DeferredResult }<{@link String }>
     */
    @GetMapping("/quotes")
    @ResponseBody
    public DeferredResult<String> quotes(String param) {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        log.info("hellowrld: {}", param);
        new Thread(()->{
            try {
                Thread.sleep(10);
                deferredResult.setResult("3243223");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }).start();
        return deferredResult;
    }
    @GetMapping("/quotess")
    @ResponseBody
    public DeferredResult<String> quotess(String param) {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        log.info("hellowrld: {}", param);
        new Thread(()->{
            try {
                Thread.sleep(10);
                deferredResult.setResult("3243223");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }).start();
        return deferredResult;
    }
    @GetMapping("/quotesss")
    @ResponseBody
    public DeferredResult<String> quotesss(String param, String param2) {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        log.info("hellowrld: {}:{}", param, param2);
        new Thread(()->{
            try {
                Thread.sleep(10);
                deferredResult.setResult("3243223");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }).start();
        return deferredResult;
    }
        /**
         * 进程上传
         *
         * @param file 文件
         * @return {@link Callable }<{@link String }>
         */
        @PostMapping("/upload")
        public Callable<String> processUpload(final MultipartFile file) {
            return () -> "someView";
        }
    /**
     * 手柄
     *
     * @return {@link WebAsyncTask }<{@link String }>
     */
    @GetMapping("/callable")
    public WebAsyncTask<String> handle() {
        return new WebAsyncTask<String>(20000L,()->{
            Thread.sleep(10000); //simulate long-running task
            return "asynchronous request completed";
        });
    }

    /**
     * 发射器处理器
     *
     * @return {@link ResponseBodyEmitter }
     */
    @GetMapping("/events")
    public ResponseBodyEmitter emitterHandler() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        // Save the emitter somewhere..
        new Thread(()->{
            // In some other thread
            try {
                emitter.send("Hello once");
                Thread.sleep(1000);
                emitter.send("Hello again");

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

// and again later on

// and done at some point
            emitter.complete();
        }).start();

        return emitter;
    }

    /**
     * SseEmitter 发射器
     *
     * @return {@link SseEmitter }
     */
    @GetMapping(path="/baseclass/events", produces= MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sseEmitter() {
        SseEmitter emitter = new SseEmitter();
        // Save the emitter somewhere..
        new Thread(()->{
            // In some other thread
            try {
                emitter.send("Hello once");
                emitter.send("Hello again");
                int i = 1/ 0;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            emitter.complete();
        }).start();
        return emitter;
    }

    @GetMapping("/download")
    public ResponseEntity<StreamingResponseBody> downloadFileStream() throws IOException {
        Path path = Paths.get("D:\\gradle-6.8.3-all.zip");

        // 检查文件是否存在
        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", path.getFileName().toString());
        headers.setContentLength(Files.size(path));

        StreamingResponseBody responseBody = outputStream -> {
            try (InputStream inputStream = Files.newInputStream(path)) {
                byte[] buffer = new byte[8192];  // 使用更大的缓冲区(8KB)提高性能
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    Thread.sleep(8);
                }
                outputStream.flush();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        log.info("走到这里");
        return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
    }


    @GetMapping("/download/tradition")
    public void downloadFile(HttpServletResponse response) throws IOException {
        Path filePath = Paths.get("D:\\gradle-6.8.3-all.zip");
        response.setContentType("application/octet-stream");
        response.setContentLength((int) Files.size(filePath));
        response.setHeader("Content-Disposition", "attachment; filename=\"file.zip\"");
        log.info("xxxxuuyy");
        try (InputStream in = Files.newInputStream(filePath);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                Thread.sleep(8); // ✅ 在这里加 sleep，就能限速！
            }
            out.flush();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
