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
package io.aklivity.zilla.runtime.binding.http.config;

import static java.util.function.Function.identity;

import java.util.List;

import io.aklivity.zilla.runtime.engine.config.ModelConfig;

public class HttpResponseConfig
{
    public final List<String> status;
    public final List<String> contentType;
    public final List<HttpParamConfig> headers;
    public final ModelConfig content;

    public HttpResponseConfig(
        List<String> status,
        List<String> contentType,
        List<HttpParamConfig> headers,
        ModelConfig content)
    {
        this.status = status;
        this.contentType = contentType;
        this.headers = headers;
        this.content = content;
    }

    public static HttpResponseConfigBuilder<HttpResponseConfig> builder()
    {
        return new HttpResponseConfigBuilder<>(identity());
    }
}
