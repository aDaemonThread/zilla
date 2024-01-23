/*
 * Copyright 2021-2023 Aklivity Inc
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
package io.aklivity.zilla.runtime.types.protobuf.internal;

import java.util.function.LongFunction;

import io.aklivity.zilla.runtime.engine.EngineContext;
import io.aklivity.zilla.runtime.engine.catalog.CatalogHandler;
import io.aklivity.zilla.runtime.engine.config.ConverterConfig;
import io.aklivity.zilla.runtime.engine.converter.ConverterContext;
import io.aklivity.zilla.runtime.engine.converter.ConverterHandler;
import io.aklivity.zilla.runtime.types.protobuf.config.ProtobufConverterConfig;

public class ProtobufConverterContext implements ConverterContext
{
    private final LongFunction<CatalogHandler> supplyCatalog;

    public ProtobufConverterContext(
        EngineContext context)
    {
        this.supplyCatalog = context::supplyCatalog;
    }

    @Override
    public ConverterHandler supplyReadHandler(
        ConverterConfig config)
    {
        return new ProtobufReadConverterHandler(ProtobufConverterConfig.class.cast(config), supplyCatalog);
    }

    @Override
    public ConverterHandler supplyWriteHandler(
        ConverterConfig config)
    {
        return new ProtobufWriteConverterHandler(ProtobufConverterConfig.class.cast(config), supplyCatalog);
    }
}
