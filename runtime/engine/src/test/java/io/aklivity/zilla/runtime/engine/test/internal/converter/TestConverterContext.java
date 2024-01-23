/*
 * Copyright 2021-2023 Aklivity Inc.
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
package io.aklivity.zilla.runtime.engine.test.internal.converter;

import java.util.function.LongFunction;

import io.aklivity.zilla.runtime.engine.EngineContext;
import io.aklivity.zilla.runtime.engine.catalog.CatalogHandler;
import io.aklivity.zilla.runtime.engine.config.ConverterConfig;
import io.aklivity.zilla.runtime.engine.converter.ConverterContext;
import io.aklivity.zilla.runtime.engine.converter.ConverterHandler;
import io.aklivity.zilla.runtime.engine.test.internal.converter.config.TestConverterConfig;

public class TestConverterContext implements ConverterContext
{
    private final LongFunction<CatalogHandler> supplyCatalog;

    public TestConverterContext(
        EngineContext context)
    {
        this.supplyCatalog = context::supplyCatalog;
    }

    @Override
    public ConverterHandler supplyReadHandler(
        ConverterConfig config)
    {
        return new TestConverterHandler(TestConverterConfig.class.cast(config), supplyCatalog);
    }

    @Override
    public ConverterHandler supplyWriteHandler(
        ConverterConfig config)
    {
        return new TestConverterHandler(TestConverterConfig.class.cast(config), supplyCatalog);
    }
}
