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
package io.aklivity.zilla.runtime.binding.pgsql.internal;

import java.net.URL;

import io.aklivity.zilla.runtime.engine.EngineContext;
import io.aklivity.zilla.runtime.engine.binding.Binding;

public final class PgsqlBinding implements Binding
{
    public static final String NAME = "pgsql";

    private final PgsqlConfiguration config;

    PgsqlBinding(
        PgsqlConfiguration config)
    {
        this.config = config;
    }

    @Override
    public String name()
    {
        return PgsqlBinding.NAME;
    }

    @Override
    public URL type()
    {
        return getClass().getResource("schema/pgsql.schema.patch.json");
    }

    @Override
    public PgsqlBindingContext supply(
        EngineContext context)
    {
        return new PgsqlBindingContext(config, context);
    }
}
