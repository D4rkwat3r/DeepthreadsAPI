package ru.deepthreads.rest.contollers.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.web.bind.annotation.*;
import ru.deepthreads.rest.models.requests.NewThreadRequest;
import ru.deepthreads.rest.models.response.success.ThreadResponse;

@RestController @RequestMapping("/api/v2/threads")
public class ThreadActionController {

    ObjectMapper mapper = new ObjectMapper()
	.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
	.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
	.setSerializationInclusion(JsonInclude.Include.NON_NULL)
	.enable(SerializationFeature.INDENT_OUTPUT);

    @PostMapping("/new")
    public ThreadResponse newThread(
            @RequestHeader("AuthToken") String token,
            @RequestBody String body
    ) throws Exception {
        NewThreadRequest data = mapper.readValue(body, NewThreadRequest.class);

    }
}
