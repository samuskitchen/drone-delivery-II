package com.s4n.dronedeliveryII.service;

import java.io.File;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.ResourceUtils;

import static org.mockito.BDDMockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class DomicileServiceTest {

    String filePath;
    List<String> routes;
    String routeOutputFolder;
    Integer droneLimitLunch;

    @MockBean
    DomicileService domicileService;

    @Before
    public void setUp() throws Exception {
        filePath = String.format("%s%s" + "src/file/entry/in01.txt", System.getProperty("user.dir"), File.separator);
        routes = Collections.singletonList("AAAAIAAD");
        routeOutputFolder = "src/file/output/";
        droneLimitLunch = 10;
    }


    @Test
    public void moveDrone() {
    }

    @Test
    public void generateReport() {
    }
}