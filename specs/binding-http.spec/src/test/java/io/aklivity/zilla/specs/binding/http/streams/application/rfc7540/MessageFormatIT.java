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
package io.aklivity.zilla.specs.binding.http.streams.application.rfc7540;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.rules.RuleChain.outerRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;

import io.aklivity.k3po.runtime.junit.annotation.Specification;
import io.aklivity.k3po.runtime.junit.rules.K3poRule;

public class MessageFormatIT
{
    private final K3poRule k3po = new K3poRule()
        .addScriptRoot("app", "io/aklivity/zilla/specs/binding/http/streams/application/rfc7540/message.format");

    private final TestRule timeout = new DisableOnDebug(new Timeout(10, SECONDS));

    @Rule
    public final TestRule chain = outerRule(k3po).around(timeout);

    @Test
    @Specification({
        "${app}/continuation.frames/client",
        "${app}/continuation.frames/server"
    })
    public void continuationFrames() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/dynamic.table.requests/client",
        "${app}/dynamic.table.requests/server"
    })
    public void dynamicTableRequests() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/server.max.frame.size/client",
        "${app}/server.max.frame.size/server"
    })
    public void serverMaxFrameSize() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/client.max.frame.size/client",
        "${app}/client.max.frame.size/server"
    })
    public void clientMaxFrameSize() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/max.frame.size.error/client",
        "${app}/max.frame.size.error/server"
    })
    public void maxFrameSizeError() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/priority.frame.size.error/client",
        "${app}/priority.frame.size.error/server"
    })
    public void priorityFrameSizeError() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/client.error.frame.with.request.payload/client",
        "${app}/client.error.frame.with.request.payload/server"
    })
    public void clientErrorFrameWithRequestPayload() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/rst.stream.frame.size.error/client",
        "${app}/rst.stream.frame.size.error/server"
    })
    public void rstStreamFrameSizeError() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/window.frame.size.error/client",
        "${app}/window.frame.size.error/server"
    })
    public void windowFrameSizeError() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/max.zilla.data.frame.size/client",
        "${app}/max.zilla.data.frame.size/server"
    })
    public void maxZillaDataFrameSize() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/connection.headers/client",
        "${app}/connection.headers/server"
    })
    public void connectionHeaders() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/connection.headers.override/client",
        "${app}/connection.headers.override/server"
    })
    public void connectionHeadersOverride() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/stream.id.order/client",
        "${app}/stream.id.order/server"
    })
    public void streamIdOrder() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/request.and.503.response/client",
        "${app}/request.and.503.response/server"
    })
    public void requestAnd503Response() throws Exception
    {
        k3po.finish();
    }
}
