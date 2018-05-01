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

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 扩展自DefaultWebSubjectFactory,对于无状态的TOKEN 类型不创建session
 */
public class AgileSubjectFactory extends DefaultWebSubjectFactory {

    private static Logger logger = LoggerFactory.getLogger(AgileSubjectFactory.class);

    public Subject createSubject(SubjectContext context) {
        logger.debug("AgileSubjectFactory createSubject");
        AuthenticationToken token = context.getAuthenticationToken();
        logger.debug("AgileSubjectFactory token {} session {}", token, context.getSession());
        if ((token instanceof JwtToken)) {
            // 当token为JwtToken时， 不创建 session
            logger.debug("AgileSubjectFactory setSessionCreationEnabled false");
            context.setSessionCreationEnabled(false);
        }
        return super.createSubject(context);
    }
}