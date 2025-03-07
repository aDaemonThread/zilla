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
package io.aklivity.zilla.runtime.binding.http.internal.codec;

import static io.aklivity.zilla.runtime.binding.http.internal.codec.Http2FrameType.CONTINUATION;
import static io.aklivity.zilla.runtime.binding.http.internal.hpack.HpackLiteralHeaderFieldFW.LiteralType.INCREMENTAL_INDEXING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.junit.Test;

import io.aklivity.zilla.runtime.binding.http.internal.hpack.HpackContext;
import io.aklivity.zilla.runtime.binding.http.internal.hpack.HpackHeaderBlockFW;
import io.aklivity.zilla.runtime.binding.http.internal.hpack.HpackHeaderBlockFWTest;

public class Http2ContinuationFWTest
{

    @Test
    public void encode()
    {
        byte[] bytes = new byte[100];
        MutableDirectBuffer buf = new UnsafeBuffer(bytes);

        Http2ContinuationFW fw = new Http2ContinuationFW.Builder()
                .wrap(buf, 1, buf.capacity())   // non-zero offset
                .header(h -> h.indexed(2))      // :method: GET
                .header(h -> h.indexed(6))      // :scheme: http
                .header(h -> h.indexed(4))      // :path: /
                .header(h -> h.literal(l -> l.type(INCREMENTAL_INDEXING).name(1).value("www.example.com")))
                .endHeaders()
                .streamId(3)
                .build();

        assertEquals(20, fw.length());
        assertEquals(1, fw.offset());
        assertEquals(30, fw.limit());
        assertTrue(fw.endHeaders());
        assertEquals(CONTINUATION, fw.type());
        assertEquals(3, fw.streamId());

        Map<String, String> headers = new LinkedHashMap<>();
        HpackContext context = new HpackContext();
        HpackHeaderBlockFW block = new HpackHeaderBlockFW().wrap(
                fw.buffer(), fw.offset() + 9, fw.limit());
        block.forEach(HpackHeaderBlockFWTest.getHeaders(context, headers));

        assertEquals(4, headers.size());
        assertEquals("GET", headers.get(":method"));
        assertEquals("http", headers.get(":scheme"));
        assertEquals("/", headers.get(":path"));
        assertEquals("www.example.com", headers.get(":authority"));
    }

}
