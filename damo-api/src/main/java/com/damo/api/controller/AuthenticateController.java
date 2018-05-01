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

package com.damo.api.controller;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.damo.api.base.BaseResponse;
import com.damo.api.base.Constants;
import com.damo.api.shiro.JwtPlayload;
import com.damo.api.shiro.JwtUtil;

/**
 * jwt认证中心。
 */
@RestController
@RequestMapping("/auth")
public class AuthenticateController {

    @ApiOperation(value = "获取token", notes = "提供客户端key")
    @RequestMapping(value = "/apply-token", method = RequestMethod.POST)
    public Map<String, Object> applyToken(@RequestParam(name = "clientKey") String clientKey, HttpSession session)
        throws Exception {
        // 签发一个Json Web Token
        // 令牌ID=uuid，用户=clientKey，签发者=clientKey
        // token有效期=1分钟，用户角色=null,用户权限=create,read,update,delete
        JwtPlayload playload = new JwtPlayload();
        playload.setAccount(clientKey);
        playload.setUserId(999L);
        playload.setRoleId(888L);
        JwtUtil jwt = new JwtUtil();
        String subject = JwtUtil.generalSubject(playload);
        String token = jwt.createJWT(Constants.JWT_ID, subject, Constants.JWT_TTL);

        BaseResponse result = BaseResponse.success();
        result.data(token);

        session.setAttribute("token", token);
        return result;
    }

    @ApiOperation(value = "test验证token", notes = "显示token的内容")
    @RequestMapping(value = "/issue-token", method = RequestMethod.POST)
    public String issueToken(@RequestParam(name = "token") String token) throws Exception {
        JwtUtil jwt = new JwtUtil();
        Claims claims = jwt.parseJWT(token);
        return claims.getSubject();
    }

    @ApiOperation(value = "test显示session", notes = "显示session的内容")
    @RequestMapping(value = "/show-session", method = RequestMethod.GET)
    public String issueToken(HttpSession session) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(session.toString());
        sb.append("{}");
        Enumeration<String> e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            sb.append(session.getAttribute(e.nextElement().toString()));
            sb.append("|");
        }
        return sb.toString();
    }
}