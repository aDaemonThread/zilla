/*
 * Copyright 2021-2024 Aklivity Inc
 *
 * Licensed under the Aklivity Community License (the "License"); you may not use
 * this file except in compliance with the License.  You may obtain a copy of the
 * License at
 *
 *   https://www.aklivity.io/aklivity-community-license/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.aklivity.zilla.runtime.binding.openapi.internal;

import static io.aklivity.zilla.runtime.binding.openapi.internal.OpenapiConfiguration.OPENAPI_COMPOSITE_ROUTE_ID;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OpenapiConfigurationTest
{
    public static final String OPENAPI_COMPOSITE_ROUTE_ID_NAME = "zilla.binding.openapi.composite.route.id";

    @Test
    public void shouldVerifyConstants() throws Exception
    {
        assertEquals(OPENAPI_COMPOSITE_ROUTE_ID.name(), OPENAPI_COMPOSITE_ROUTE_ID_NAME);
    }
}
