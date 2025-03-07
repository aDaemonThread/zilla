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
package io.aklivity.zilla.runtime.binding.amqp.internal.config;

import static java.util.stream.Collectors.toList;

import java.util.List;

import io.aklivity.zilla.runtime.binding.amqp.internal.types.AmqpCapabilities;
import io.aklivity.zilla.runtime.engine.config.BindingConfig;
import io.aklivity.zilla.runtime.engine.config.KindConfig;

public final class AmqpBindingConfig
{
    public final long id;
    public final String name;
    public final KindConfig kind;
    public final List<AmqpRouteConfig> routes;

    public AmqpBindingConfig(
        BindingConfig binding)
    {
        this.id = binding.id;
        this.name = binding.name;
        this.kind = binding.kind;
        this.routes = binding.routes.stream().map(AmqpRouteConfig::new).collect(toList());
    }

    public AmqpRouteConfig resolve(
        long authorization,
        String address,
        AmqpCapabilities capabilities)
    {
        return routes.stream()
            .filter(r -> r.authorized(authorization) && r.matches(address, capabilities))
            .findFirst()
            .orElse(null);
    }
}
