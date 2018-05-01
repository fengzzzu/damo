/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.damo.api.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.damo.api.base.BaseResponse;

/**
 * 基于JWT标准的无状态认证过滤器
 */
public class JwtFilter extends AccessControlFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    public static final String DEFAULT_JWT_PARAM = "x-access-token";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
        throws Exception {
        HttpServletRequest rq = (HttpServletRequest)request;
        logger.debug("isAccessAllowed request:{}", rq.getRequestURL());

        if (null != getSubject(request, response) && getSubject(request, response).isAuthenticated()) {
            logger.debug("isAccessAllowed true {}", getSubject(request, response));
            return true;
        }
        logger.debug("isAccessAllowed false");
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest rq = (HttpServletRequest)request;
        logger.debug("onAccessDenied jwt request:{}", rq.getRequestURL());
        if (isJwtSubmission(request)) {
            AuthenticationToken token = createToken(request, response);
            try {
                Subject subject = getSubject(request, response);
                logger.debug("subject1 = {}", subject);
                logger.debug("subject1.isAuthenticated() = {}", subject.isAuthenticated());
                subject.login(token);
                ThreadContext.bind(subject);
                logger.debug("subject = {}", subject);
                logger.debug("subject.isAuthenticated() = {}", subject.isAuthenticated());
                logger.debug("subject getPrincipal = {}", subject.getPrincipal());
                logger.debug("subject getPrincipals = {}", subject.getPrincipals());
                logger.debug("subject getPreviousPrincipals = {}", subject.getPreviousPrincipals());
                return true;
            } catch (AuthenticationException e) {
                BaseResponse r = BaseResponse.build();
                r.code(403);
                r.message(e.getLocalizedMessage());
                HttpServletResponse rp = (HttpServletResponse)response;
                rp.setHeader("Content-type", "text/html;charset=UTF-8");  
                response.getWriter().print(JSONObject.toJSONString(r));
            }
        }
        return false;
    }

    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        HttpServletRequest rq = (HttpServletRequest)request;
        String jwt = rq.getHeader(DEFAULT_JWT_PARAM);
        String host = request.getRemoteHost();
        logger.debug("createToken AuthenticationToken jwt token:" + jwt);
        return new JwtToken(jwt, host);
    }

    protected boolean isJwtSubmission(ServletRequest request) {
        String jwt = request.getParameter(DEFAULT_JWT_PARAM);
        return (request instanceof HttpServletRequest) && StringUtils.isEmpty(jwt);
    }

}