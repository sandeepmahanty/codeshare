package com.sandeep.backend.controller;

import com.sandeep.backend.model.request.ExecuteCodeRequest;
import com.sandeep.backend.model.response.DefaultCodeResponse;
import com.sandeep.backend.model.response.ExecuteCodeResponse;
import com.sandeep.backend.service.CodeService;
import com.sandeep.backend.utils.StopWatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/")
@Slf4j
public class PlaygroundController {
    @Autowired
    CodeService codeService;

    @Value("${application.version}")
    public String VERSION;

    @RequestMapping(value = "version")
    public String getVersion() {
        return VERSION;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "default/{lang_id}", method = RequestMethod.GET)
    public ResponseEntity<DefaultCodeResponse> getDefaultCode(@PathVariable("lang_id") String languageId) {
        return new ResponseEntity<>(new DefaultCodeResponse(codeService.getDefaultCode(languageId), languageId), HttpStatus.OK);
    }

    @CrossOrigin(origins = "/")
    @PostMapping("/execute")
    public ResponseEntity<ExecuteCodeResponse> executeCode(@RequestBody ExecuteCodeRequest executeCodeRequest) throws Exception {
        log.info("received request for");
        StopWatch stopWatch = new StopWatch();
        //TODO: instead of language we need to map it to id to see a history of all runs
        System.out.println(executeCodeRequest);
        ExecuteCodeResponse response = codeService.executeCode(executeCodeRequest);
        response.setTime(stopWatch.elapsedTime());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
