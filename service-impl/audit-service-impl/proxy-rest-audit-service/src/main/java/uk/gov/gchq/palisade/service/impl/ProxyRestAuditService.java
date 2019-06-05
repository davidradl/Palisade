/*
 * Copyright 2018 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.palisade.service.impl;

import uk.gov.gchq.palisade.audit.service.AuditService;
import uk.gov.gchq.palisade.audit.service.request.AuditRequest;
import uk.gov.gchq.palisade.rest.ProxyRestService;
import uk.gov.gchq.palisade.service.Service;

import java.util.concurrent.CompletableFuture;

public class ProxyRestAuditService extends ProxyRestService implements AuditService {
    public ProxyRestAuditService() {
    }

    @Override
    protected Class<? extends Service> getServiceClass() {
        return AuditService.class;
    }

    public ProxyRestAuditService(final String baseUrl) {
        setBaseUrl(baseUrl);
    }

    @Override
    public CompletableFuture<Boolean> audit(final AuditRequest request) {
        return doPostAsync("audit", request, Boolean.class);
    }
}
