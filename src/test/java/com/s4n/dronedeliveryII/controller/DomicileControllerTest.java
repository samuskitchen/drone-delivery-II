package com.s4n.dronedeliveryII.controller;

import com.s4n.dronedeliveryII.model.Position;
import com.s4n.dronedeliveryII.model.enums.Sense;
import com.s4n.dronedeliveryII.service.DomicileService;
import com.s4n.dronedeliveryII.service.DroneService;
import java.io.File;
import java.util.Collections;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class DomicileControllerTest {

    File entryFile;
    List<String> routes;

    @MockBean
    DomicileController domicileController;

    @MockBean
    DomicileService domicileService;

    @MockBean
    DroneService droneService;

    @Before
    public void setUp() {
        entryFile = new File("src/file/entry/in.txt");
        routes = Collections.singletonList("AAAAIAAD, DDAIAD, AAIADAD");
    }

    @Test
    public void readFiles() {
        List<String> listResult = Collections.EMPTY_LIST;

        //when
        given(droneService.pathsPerFile(entryFile.getPath())).willReturn(listResult);

        //then
        domicileController.readFiles();
        assertNotEquals(listResult, (CoreMatchers.equalTo(routes)));
    }
}