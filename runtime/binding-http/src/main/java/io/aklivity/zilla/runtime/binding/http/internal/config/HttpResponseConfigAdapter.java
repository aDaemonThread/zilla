/*
 * Copyright 2021-2024 Aklivity Inc.
 *
 * Aklivity licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.aklivity.zilla.runtime.binding.http.internal.config;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import jakarta.json.bind.adapter.JsonbAdapter;

import io.aklivity.zilla.runtime.binding.http.config.HttpParamConfig;
import io.aklivity.zilla.runtime.binding.http.config.HttpResponseConfig;
import io.aklivity.zilla.runtime.engine.config.ModelConfig;
import io.aklivity.zilla.runtime.engine.config.ModelConfigAdapter;

public class HttpResponseConfigAdapter implements JsonbAdapter<HttpResponseConfig, JsonObject>
{
    private static final String STATUS_NAME = "status";
    private static final String CONTENT_TYPE_NAME = "content-type";
    private static final String HEADERS_NAME = "headers";
    private static final String CONTENT_NAME = "content";

    private final ModelConfigAdapter model = new ModelConfigAdapter();

    @Override
    public JsonObject adaptToJson(
        HttpResponseConfig response)
    {
        JsonObjectBuilder object = Json.createObjectBuilder();
        if (response.status != null)
        {
            if (response.status.size() == 1)
            {
                object.add(STATUS_NAME, Integer.parseInt(response.status.get(0)));
            }
            else
            {
                JsonArrayBuilder status = Json.createArrayBuilder();
                response.status.forEach(i -> status.add(Integer.parseInt(i)));
                object.add(STATUS_NAME, status);
            }
        }
        if (response.contentType != null)
        {
            JsonArrayBuilder contentType = Json.createArrayBuilder();
            response.contentType.forEach(contentType::add);
            object.add(CONTENT_TYPE_NAME, contentType);
        }
        if (response.headers != null)
        {
            JsonObjectBuilder headers = Json.createObjectBuilder();
            for (HttpParamConfig header : response.headers)
            {
                model.adaptType(header.model.model);
                headers.add(header.name, model.adaptToJson(header.model));
            }
            object.add(HEADERS_NAME, headers);
        }
        if (response.content != null)
        {
            model.adaptType(response.content.model);
            JsonValue content = model.adaptToJson(response.content);
            object.add(CONTENT_NAME, content);
        }
        return object.build();
    }

    @Override
    public HttpResponseConfig adaptFromJson(
        JsonObject object)
    {
        List<String> status = null;
        if (object.containsKey(STATUS_NAME))
        {
            JsonValue status0 = object.get(STATUS_NAME);
            if (status0.getValueType() == JsonValue.ValueType.NUMBER)
            {
                status = List.of(String.valueOf(((JsonNumber) status0).intValue()));
            }
            else if (status0.getValueType() == JsonValue.ValueType.ARRAY)
            {
                status = object.getJsonArray(STATUS_NAME).stream()
                    .map(JsonNumber.class::cast)
                    .map(JsonNumber::intValue)
                    .map(String::valueOf)
                    .collect(Collectors.toList());
            }
        }
        List<String> contentType = null;
        if (object.containsKey(CONTENT_TYPE_NAME))
        {
            contentType = object.getJsonArray(CONTENT_TYPE_NAME).stream()
                .map(JsonString.class::cast)
                .map(JsonString::getString)
                .collect(Collectors.toList());
        }
        List<HttpParamConfig> headers = null;
        if (object.containsKey(HEADERS_NAME))
        {
            JsonObject headersJson = object.getJsonObject(HEADERS_NAME);
            headers = new LinkedList<>();
            for (Map.Entry<String, JsonValue> entry : headersJson.entrySet())
            {
                HttpParamConfig header = HttpParamConfig.builder()
                    .name(entry.getKey())
                    .model(model.adaptFromJson(entry.getValue()))
                    .build();
                headers.add(header);
            }
        }
        ModelConfig content = null;
        if (object.containsKey(CONTENT_NAME))
        {
            JsonValue contentJson = object.get(CONTENT_NAME);
            content = model.adaptFromJson(contentJson);
        }
        return new HttpResponseConfig(status, contentType, headers, content);
    }
}
