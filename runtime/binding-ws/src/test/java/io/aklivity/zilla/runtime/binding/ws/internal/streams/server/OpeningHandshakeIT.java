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
package io.aklivity.zilla.runtime.binding.ws.internal.streams.server;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.rules.RuleChain.outerRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;

import io.aklivity.k3po.runtime.junit.annotation.Specification;
import io.aklivity.k3po.runtime.junit.rules.K3poRule;
import io.aklivity.zilla.runtime.engine.test.EngineRule;
import io.aklivity.zilla.runtime.engine.test.annotation.Configuration;

/**
 * RFC-6455, section 4.1 "Client-Side Requirements"
 * RFC-6455, section 4.2 "Server-Side Requirements"
 */
public class OpeningHandshakeIT
{
    private final K3poRule k3po = new K3poRule()
        .addScriptRoot("net", "io/aklivity/zilla/specs/binding/ws/streams/network/opening")
        .addScriptRoot("app", "io/aklivity/zilla/specs/binding/ws/streams/application/opening");

    private final TestRule timeout = new DisableOnDebug(new Timeout(10, SECONDS));

    private final EngineRule engine = new EngineRule()
        .directory("target/zilla-itests")
        .countersBufferCapacity(4096)
        .configurationRoot("io/aklivity/zilla/specs/binding/ws/config")
        .external("app0")
        .clean();

    @Rule
    public final TestRule chain = outerRule(engine).around(k3po).around(timeout);

    @Test
    @Configuration("server.yaml")
    @Specification({
        "${net}/connection.established/handshake.request",
        "${app}/connection.established/handshake.response" })
    public void shouldEstablishConnection() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Configuration("server.yaml")
    @Specification({
        "${net}/request.header.sec.websocket.protocol/handshake.request",
        "${app}/connection.established/handshake.response" })
    public void shouldEstablishConnectionWithRequestHeaderSecWebSocketProtocol() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Configuration("server.when.yaml")
    @Specification({
        "${net}/request.header.sec.websocket.protocol.negotiated/handshake.request",
        "${app}/connection.established.extension/handshake.response" })
    public void shouldEstablishConnectionWithRequestHeaderSecWebSocketProtocolNegotiated() throws Exception
    {
        k3po.finish();
    }
}
