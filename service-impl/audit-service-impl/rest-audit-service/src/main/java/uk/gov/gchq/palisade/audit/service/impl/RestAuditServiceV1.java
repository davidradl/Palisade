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

package uk.gov.gchq.palisade.audit.service.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.gov.gchq.palisade.audit.service.AuditService;
import uk.gov.gchq.palisade.audit.service.request.AuditRequest;
import uk.gov.gchq.palisade.config.service.ConfigUtils;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

//import uk.gov.gchq.palisade.policy.service.MultiPolicy;
//import uk.gov.gchq.palisade.policy.service.PolicyService;
//import uk.gov.gchq.palisade.policy.service.request.CanAccessRequest;
//import uk.gov.gchq.palisade.policy.service.request.GetPolicyRequest;
//import uk.gov.gchq.palisade.policy.service.request.SetResourcePolicyRequest;
//import uk.gov.gchq.palisade.policy.service.request.SetTypePolicyRequest;
//import uk.gov.gchq.palisade.policy.service.response.CanAccessResponse;

@Path("/")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Api(value = "/")
public class RestAuditServiceV1 implements AuditService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestAuditServiceV1.class);
    private final AuditService delegate;

    private static AuditService auditService;

    @Inject
    public RestAuditServiceV1(final AuditService delegate) {
        requireNonNull(delegate, "delegate");
        this.delegate = delegate;
    }

    static synchronized AuditService createService(final String serviceConfigPath) {
        if (auditService == null) {
            auditService = ConfigUtils.createService(RestAuditServiceV1.class, serviceConfigPath, AuditService.class);
        }
        return auditService;
    }

    static synchronized void setDefaultDelegate(final AuditService auditService) {
        requireNonNull(auditService, "auditService");
        RestAuditServiceV1.auditService = auditService;
    }

    @POST
    @Path("/audit")
    @ApiOperation(value = "Returns a Boolean response",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Something went wrong in the server")
    })

    @Override
    public CompletableFuture<Boolean> audit(
            @ApiParam(value = "The request") final AuditRequest request) {
        LOGGER.debug("Invoking audit: {}", request);
        return delegate.audit(request);
    }

    protected AuditService getDelegate() {
        return delegate;
    }
}
