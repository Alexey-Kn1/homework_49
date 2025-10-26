package ru.netology;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class HomeworkController {
    private final Counter requestsCounter;
    private List<Byte> bytes;

    public HomeworkController(MeterRegistry meterRegistry) {
        bytes = null;
        requestsCounter = meterRegistry.counter("requests_number_custom");
    }

    @GetMapping("memory/allocate")
    public void check(@RequestParam("bytes") int size) {
        requestsCounter.increment();
        bytes = new ArrayList<>(size);
    }

    @GetMapping("memory/clean")
    public void clean() {
        requestsCounter.increment();
        bytes = null;
        System.gc();
    }

    @GetMapping("codes/404")
    public void get404() {
        requestsCounter.increment();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("codes/500")
    public void get500() {
        requestsCounter.increment();
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("codes/200")
    public void get() {
        requestsCounter.increment();
    }
}
