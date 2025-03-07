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
package io.aklivity.zilla.runtime.engine.internal.config;

import static io.aklivity.zilla.runtime.engine.config.KindConfig.SERVER;
import static java.util.function.Function.identity;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.time.Duration;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

import io.aklivity.zilla.runtime.engine.config.BindingConfig;
import io.aklivity.zilla.runtime.engine.config.ConfigAdapterContext;
import io.aklivity.zilla.runtime.engine.config.NamespaceConfig;
import io.aklivity.zilla.runtime.engine.config.VaultConfig;
import io.aklivity.zilla.runtime.engine.test.internal.catalog.config.TestCatalogOptionsConfig;
import io.aklivity.zilla.runtime.engine.test.internal.exporter.config.TestExporterOptionsConfig;
import io.aklivity.zilla.runtime.engine.test.internal.guard.config.TestGuardOptionsConfig;
import io.aklivity.zilla.runtime.engine.test.internal.vault.config.TestVaultOptionsConfig;

public class NamespaceConfigAdapterTest
{
    private static final String LOCALHOST_KEY = """
        -----BEGIN PRIVATE KEY-----
        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
        HiTCqXKPrP1QCU1/TXWVjEgnVnlruNTahA==
        -----END PRIVATE KEY-----
        -----BEGIN CERTIFICATE-----
        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
        -----END CERTIFICATE-----
        -----BEGIN CERTIFICATE-----
        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
        -----END CERTIFICATE-----
        """;

    private static final String SERVERCA_TRUST = """
        -----BEGIN CERTIFICATE-----
        MIIDbDCCAlSgAwIBAgIEEiflHDANBgkqhkiG9w0BAQsFADBeMREwDwYDVQQDEwhz
        ZXJ2ZXJjYTEUMBIGA1UECxMLRGV2ZWxvcG1lbnQxETAPBgNVBAoTCEFrbGl2aXR5
        MRMwEQYDVQQIEwpDYWxpZm9ybmlhMQswCQYDVQQGEwJVUzAeFw0yMTEyMTQwMDM1
        NTNaFw0zMTEyMTIwMDM1NTNaMF4xETAPBgNVBAMTCHNlcnZlcmNhMRQwEgYDVQQL
        EwtEZXZlbG9wbWVudDERMA8GA1UEChMIQWtsaXZpdHkxEzARBgNVBAgTCkNhbGlm
        b3JuaWExCzAJBgNVBAYTAlVTMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKC
        AQEAlmDVsfeWIEjypnw6qs0eVwTeM46KqHEvl5ElOyDoZZcqqZQN/jMW/VqzTbLc
        zjYE0HqpZNTbaW80kQ1O/VipDmnousimKHg7QtN5KIhsIelnZSQWq8cV2rtSTFDk
        rArE659GPWCPr/OeLT3Nbde0p9psz3uh1HJYVWAbZxWOe3GflSC8pGxu3PirU/kP
        g89RKRyO5UsF4feHdkJJqUJ92Th4n34DKQcHuwJ3iYxhB9hOlvI4ESIxM+4eWW89
        o4p2B2Ctwt8rpHDoBsNADophBD5kMT4mv6l5J3kVYy65QH7OfUIH22ApFjABdhGj
        lCMYtvSCN1Y1lDBU5M9xrBdERwIDAQABozIwMDAdBgNVHQ4EFgQUQqJ69wHA1kfl
        rVH510Y8/9mID5gwDwYDVR0TAQH/BAUwAwEB/zANBgkqhkiG9w0BAQsFAAOCAQEA
        a56t9nJWGJlZFa8T1pnf9vdAcoQoqZ8LgKcdcxvGDtGdr5QF8L6LOqoYKUvetHv0
        kdvht0fqv3AZivCVyDIpMw17E5mLu5vvdUQM4E+qLNF6SUhO3c/Elylt2/3YKNBM
        FgjV0OdepnPz7/0nGCFUJo1fV8obUQt005P/S/F8g6UsIubcb/V55hR9/9Pruvw8
        gqAWNjPJZ0+BlhTgI505K80JFJ7CWZCaseDSeAkXPhb+a29vP2cDsR6wKZeny4+f
        P+TPUku7wEo8v+Tr8L1Esmcoudn6Wq+N3ZBYFLH6T4kqP+0BkDoStFDonUFRWxXX
        5OQUWOWJqvzyJ8VIbBRDsQ==
        -----END CERTIFICATE-----
        """;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

    @Mock
    private ConfigAdapterContext context;

    private Jsonb jsonb;


    @Before
    public void initJson()
    {
        JsonbConfig config = new JsonbConfig()
                .withAdapters(new NamespaceAdapter(context));
        jsonb = JsonbBuilder.create(config);
    }

    @Test
    public void shouldReadNamespace()
    {
        String text =
                "{" +
                    "\"name\": \"test\"," +
                    "\"vaults\":" +
                    "{" +
                    "}," +
                    "\"bindings\":" +
                    "{" +
                    "}," +
                    "\"references\":" +
                    "[" +
                    "]" +
                "}";

        NamespaceConfig config = jsonb.fromJson(text, NamespaceConfig.class);

        assertThat(config, not(nullValue()));
        assertThat(config.name, equalTo("test"));
        assertThat(config.bindings, emptyCollectionOf(BindingConfig.class));
        assertThat(config.vaults, emptyCollectionOf(VaultConfig.class));
    }

    @Test
    public void shouldWriteNamespace()
    {
        NamespaceConfig config = NamespaceConfig.builder()
            .name("test")
            .build();

        String text = jsonb.toJson(config);

        assertThat(text, not(nullValue()));
        assertThat(text, equalTo("{\"name\":\"test\"}"));
    }

    @Test
    public void shouldReadNamespaceWithBinding()
    {
        String text =
                "{" +
                    "\"name\": \"test\"," +
                    "\"vaults\":" +
                    "{" +
                    "}," +
                    "\"bindings\":" +
                    "{" +
                        "\"test\":" +
                        "{" +
                            "\"type\": \"test\"," +
                            "\"kind\": \"server\"" +
                        "}" +
                    "}" +
                "}";

        NamespaceConfig config = jsonb.fromJson(text, NamespaceConfig.class);

        assertThat(config, not(nullValue()));
        assertThat(config.name, equalTo("test"));
        assertThat(config.bindings, hasSize(1));
        assertThat(config.bindings.get(0).name, equalTo("test"));
        assertThat(config.bindings.get(0).type, equalTo("test"));
        assertThat(config.bindings.get(0).kind, equalTo(SERVER));
        assertThat(config.vaults, emptyCollectionOf(VaultConfig.class));
    }

    @Test
    public void shouldWriteNamespaceWithBinding()
    {
        NamespaceConfig config = NamespaceConfig.builder()
                .inject(identity())
                .name("test")
                .binding()
                    .name("test")
                    .type("test")
                    .kind(SERVER)
                    .build()
                .build();

        String text = jsonb.toJson(config);

        assertThat(text, not(nullValue()));
        assertThat(text, equalTo("{\"name\":\"test\",\"bindings\":{\"test\":{\"type\":\"test\",\"kind\":\"server\"}}}"));
    }

    @Test
    public void shouldReadNamespaceWithGuard()
    {
        String text =
                "{" +
                    "\"name\": \"test\"," +
                    "\"bindings\":" +
                    "{" +
                    "}," +
                    "\"guards\":" +
                    "{" +
                        "\"default\":" +
                        "{" +
                            "\"type\": \"test\"" +
                        "}" +
                    "}" +
                "}";

        NamespaceConfig config = jsonb.fromJson(text, NamespaceConfig.class);

        assertThat(config, not(nullValue()));
        assertThat(config.name, equalTo("test"));
        assertThat(config.guards, hasSize(1));
        assertThat(config.guards.get(0).name, equalTo("default"));
        assertThat(config.guards.get(0).type, equalTo("test"));
    }

    @Test
    public void shouldWriteNamespaceWithGuard()
    {
        NamespaceConfig config = NamespaceConfig.builder()
                .inject(identity())
                .name("test")
                .guard()
                    .name("default")
                    .type("test")
                    .options(TestGuardOptionsConfig::builder)
                        .credentials("token")
                        .lifetime(Duration.ofSeconds(10))
                        .build()
                    .build()
                .build();

        String text = jsonb.toJson(config);

        assertThat(text, not(nullValue()));
        assertThat(text, equalTo("{\"name\":\"test\",\"guards\":{\"default\":{\"type\":\"test\"," +
                "\"options\":{\"credentials\":\"token\",\"lifetime\":\"PT10S\"}}}}"));
    }

    @Test
    public void shouldReadNamespaceWithVault()
    {
        String text =
                "{" +
                    "\"name\": \"test\"," +
                    "\"bindings\":" +
                    "{" +
                    "}," +
                    "\"vaults\":" +
                    "{" +
                        "\"default\":" +
                        "{" +
                            "\"type\": \"test\"" +
                        "}" +
                    "}" +
                "}";

        NamespaceConfig config = jsonb.fromJson(text, NamespaceConfig.class);

        assertThat(config, not(nullValue()));
        assertThat(config.name, equalTo("test"));
        assertThat(config.vaults, hasSize(1));
        assertThat(config.vaults.get(0).name, equalTo("default"));
        assertThat(config.vaults.get(0).type, equalTo("test"));
    }

    @Test
    public void shouldWriteNamespaceWithVault()
    {
        NamespaceConfig config = NamespaceConfig.builder()
                .inject(identity())
                .name("test")
                .vault()
                    .name("default")
                    .type("test")
                    .options(TestVaultOptionsConfig::builder)
                        .key("localhost", LOCALHOST_KEY)
                        .trust("serverca", SERVERCA_TRUST)
                        .build()
                    .build()
                .build();

        String text = jsonb.toJson(config);

        assertThat(text, not(nullValue()));
        assertThat(text, equalTo("{\"name\":\"test\",\"vaults\":{\"default\":{\"type\":\"test\"," +
                "\"options\":{" +
                    "\"key\":{\"alias\":\"localhost\",\"entry\":\"%s\"},".formatted(LOCALHOST_KEY.replaceAll("\n", "\\\\n")) +
                    "\"trust\":{\"alias\":\"serverca\",\"entry\":\"%s\"}".formatted(SERVERCA_TRUST.replaceAll("\n", "\\\\n")) +
                "}}}}"));
    }

    @Test
    public void shouldWriteNamespaceWithCatalog()
    {
        NamespaceConfig config = NamespaceConfig.builder()
                .inject(identity())
                .name("test")
                .catalog()
                    .name("default")
                    .type("test")
                    .options(TestCatalogOptionsConfig::builder)
                        .schema("test")
                        .build()
                    .build()
                .build();

        String text = jsonb.toJson(config);

        assertThat(text, not(nullValue()));
        assertThat(text, equalTo("{\"name\":\"test\",\"catalogs\":{\"default\":{\"type\":\"test\"," +
                "\"options\":{\"schema\":\"test\"}}}}"));
    }

    @Test
    public void shouldReadNamespaceWithTelemetry()
    {
        String text =
                "{" +
                        "  \"name\": \"test\"," +
                        "  \"telemetry\": {\n" +
                        "    \"attributes\": {\n" +
                        "      \"test.attribute\": \"example\"\n" +
                        "    },\n" +
                        "    \"metrics\": [\n" +
                        "      \"test.counter\"\n" +
                        "    ]\n" +
                        "  }\n" +
                        "}";

        NamespaceConfig config = jsonb.fromJson(text, NamespaceConfig.class);

        assertThat(config, not(nullValue()));
        assertThat(config.name, equalTo("test"));
        assertThat(config.telemetry, not(nullValue()));
        assertThat(config.telemetry.attributes.get(0).name, equalTo("test.attribute"));
        assertThat(config.telemetry.attributes.get(0).value, equalTo("example"));
        assertThat(config.telemetry.metrics.get(0).name, equalTo("test.counter"));
    }

    @Test
    public void shouldWriteNamespaceWithTelemetry()
    {
        NamespaceConfig config = NamespaceConfig.builder()
                .inject(identity())
                .name("test")
                .telemetry()
                    .attribute()
                        .name("test.attribute")
                        .value("example")
                        .build()
                    .metric()
                        .group("test")
                        .name("test.counter")
                        .build()
                    .exporter()
                        .name("test0")
                        .type("test")
                        .options(TestExporterOptionsConfig::builder)
                            .mode("test42")
                            .build()
                        .build()
                    .build()
                .build();

        String text = jsonb.toJson(config);

        assertThat(text, not(nullValue()));
        assertThat(text, equalTo(
                "{\"name\":\"test\",\"telemetry\":" +
                "{\"attributes\":{\"test.attribute\":\"example\"}," +
                "\"metrics\":[\"test.counter\"]," +
                "\"exporters\":{\"test0\":{\"type\":\"test\",\"options\":{\"mode\":\"test42\"}}}}}"));
    }
}
