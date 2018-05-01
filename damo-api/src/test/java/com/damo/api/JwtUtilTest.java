/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.damo.api;

import io.jsonwebtoken.Claims;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.damo.api.base.Constants;
import com.damo.api.shiro.JwtPlayload;
import com.damo.api.shiro.JwtUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class JwtUtilTest {
    private static Logger logger = LoggerFactory.getLogger(JwtUtilTest.class);

    @Autowired
    JwtUtil jwtUtil;

    @Test
    public void jwt_token_Test() throws Exception {
        // JwtUtil jwtUtil = new JwtUtil();
         logger.info("jwtUtil.getProfiles() = {}",jwtUtil.getProfiles());
        JwtPlayload service_user = new JwtPlayload();
        service_user.setAccount("my account test");
        service_user.setUserId(333L);
        service_user.setRoleId(339L);

        String subject = JwtUtil.generalSubject(service_user);
        String token = jwtUtil.createJWT(Constants.JWT_ID, subject, Constants.JWT_TTL);
        logger.info("校验 token={}", token);
        Claims claims = jwtUtil.parseJWT(token);
        String json = claims.getSubject();
        logger.info("token = {}", json);
    }

    @Test
    public void jwt_过期_Test() throws Exception {
        // JwtUtil jwtUtil = new JwtUtil();

        logger.info("登录");
        JwtPlayload service_user = new JwtPlayload();
        service_user.setAccount("my account");
        service_user.setUserId(333L);
        service_user.setRoleId(339L);

        String subject = JwtUtil.generalSubject(service_user);
        String token = jwtUtil.createJWT(Constants.JWT_ID, subject, 10);
        logger.info("校验 token={}", token);
        try {
            Thread.sleep(20);
        } catch (Exception e) {
        }

        try {
            Claims claims = jwtUtil.parseJWT(token);
            String json = claims.getSubject();
            logger.info("token = {}", json);
        } catch (Exception e) {
            logger.info("校验出错", e);
        }

    }

    // @Test
    public void jwt_check() throws Exception {
        // JwtUtil jwtUtil = new JwtUtil();
        String token =
            "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1MTc1NTA1NTEsInJvbGVzIjoiUyxUIiwicGVybXMiOiJDLFUsUixEIiwic3ViIjoie1wiYWNjb3VudFwiOlwibXkgYWNjb3VudFwiLFwicm9sZUlkXCI6MzM5LFwidXNlcklkXCI6MzMzfSIsImV4cCI6MTUxNzU1NDE1MX0.wz0QP23m4Wm0izooxLO2m5EvI49Jm3Ma2mKGaC-ZRgc";
        Claims claims = jwtUtil.parseJWT(token);
        String json = claims.getSubject();
        logger.info("token = {}", json);
        logger.info("getExpiration = {}", claims.getExpiration());
    }
}