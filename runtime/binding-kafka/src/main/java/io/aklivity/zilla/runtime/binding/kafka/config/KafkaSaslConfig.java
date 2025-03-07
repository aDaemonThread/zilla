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
package io.aklivity.zilla.runtime.binding.kafka.config;


import java.util.function.Function;

public class KafkaSaslConfig
{
    public final String mechanism;
    public final String username;
    public final String password;

    public static KafkaSaslConfigBuilder<KafkaSaslConfig> builder()
    {
        return new KafkaSaslConfigBuilder<>(KafkaSaslConfig.class::cast);
    }

    public static <T> KafkaSaslConfigBuilder<T> builder(
        Function<KafkaSaslConfig, T> mapper)
    {
        return new KafkaSaslConfigBuilder<>(mapper);
    }

    KafkaSaslConfig(
        String mechanism,
        String username,
        String password)
    {
        this.mechanism = mechanism;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString()
    {
        return String.format("%s [username=%s]", mechanism, username);
    }
}
