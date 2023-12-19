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
package io.aklivity.zilla.runtime.engine.test.internal.catalog.config;

import java.util.function.Function;

import io.aklivity.zilla.runtime.engine.config.ConfigBuilder;
import io.aklivity.zilla.runtime.engine.config.OptionsConfig;

public final class TestCatalogOptionsConfigBuilder<T> extends ConfigBuilder<T, TestCatalogOptionsConfigBuilder<T>>
{
    private final Function<OptionsConfig, T> mapper;

    private String schema;
    private boolean embed;
    private boolean exclude;
    private int id;

    TestCatalogOptionsConfigBuilder(
        Function<OptionsConfig, T> mapper)
    {
        this.mapper = mapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<TestCatalogOptionsConfigBuilder<T>> thisType()
    {
        return (Class<TestCatalogOptionsConfigBuilder<T>>) getClass();
    }

    public TestCatalogOptionsConfigBuilder<T> schema(
        String schema)
    {
        this.schema = schema;
        return this;
    }

    public TestCatalogOptionsConfigBuilder<T> embed(
        boolean embed)
    {
        this.embed = embed;
        return this;
    }

    public TestCatalogOptionsConfigBuilder<T> exclude(
        boolean exclude)
    {
        this.exclude = exclude;
        return this;
    }

    public TestCatalogOptionsConfigBuilder<T> id(
        int id)
    {
        this.id = id;
        return this;
    }

    @Override
    public T build()
    {
        return mapper.apply(new TestCatalogOptionsConfig(id, schema, embed, exclude));
    }
}
