package com.ing.task.apiuser.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;

public class ApplicationUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T mergePatchJson(T source, String patchJson, Class<T> clazz) throws JsonProcessingException, JsonPatchException {

        JsonNode sourceNode = objectMapper.valueToTree(source);
        JsonNode resultNode = objectMapper.readValue(patchJson, JsonMergePatch.class).apply(sourceNode);
        return objectMapper.treeToValue(resultNode, clazz);
    }
}
