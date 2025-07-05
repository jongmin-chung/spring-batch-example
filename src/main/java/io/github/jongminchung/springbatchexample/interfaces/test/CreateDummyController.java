package io.github.jongminchung.springbatchexample.interfaces.test;

import io.github.jongminchung.springbatchexample.application.dummy.DummyFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@RequestMapping("/test-dummy")
@RequiredArgsConstructor
public class CreateDummyController {

    private final DummyFacade dummyFacade;

    @PostMapping("/size/{size}")
    public void createDummyOrder(@PathVariable Integer size) {
        IntStream.range(1, size + 1).mapToLong(it -> it).forEach(dummyFacade::createOrder);
    }
}
