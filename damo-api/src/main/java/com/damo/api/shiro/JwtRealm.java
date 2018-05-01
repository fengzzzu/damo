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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;

/**
 * 认证操作
 */
public class JwtRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(JwtRealm.class);

    public Class<?> getAuthenticationTokenClass() {
        return JwtToken.class;// 此Realm只支持JwtToken
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.debug("doGetAuthenticationInfo = {} getName = {}", token, getName());

        JwtToken jwtToken = (JwtToken)token;
        String jwt = jwtToken.getJwt();
        JwtPlayload jwtPlayload;
        try {
            JwtUtil jwtUtil = new JwtUtil();
            Claims claims = jwtUtil.parseJWT(jwt);
            String json = claims.getSubject();
            logger.debug("claims.getSubject = {}", json);
            jwtPlayload = JSONObject.parseObject(json, JwtPlayload.class);
            logger.debug("claims.roles = {}", claims.get("roles"));
            logger.debug("claims.perms = {}", claims.get("perms"));

            // jwtPlayload.setId(claims.getId());
            // jwtPlayload.setUserId(claims.getSubject());// 用户名
            // jwtPlayload.setIssuer(claims.getIssuer());// 签发者
            // jwtPlayload.setIssuedAt(claims.getIssuedAt());// 签发时间
            // jwtPlayload.setAudience(claims.getAudience());// 接收方
            // jwtPlayload.setRoles(claims.get("roles", String.class));// 访问主张-角色
            // jwtPlayload.setPerms(claims.get("perms", String.class));// 访问主张-权限
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException("令牌过期:" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new AuthenticationException("令牌无效:" + e.getMessage());
        } catch (MalformedJwtException e) {
            throw new AuthenticationException("令牌格式错误:" + e.getMessage());
        } catch (SignatureException e) {
            throw new AuthenticationException("令牌签名无效:" + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new AuthenticationException("令牌参数异常:" + e.getMessage());
        } catch (Exception e) {
            throw new AuthenticationException("令牌错误:" + e.getMessage());
        }
        // 如果要使token只能使用一次，此处可以过滤并缓存jwtPlayload.getId()
        // 可以做签发方验证
        // 可以做接收方验证
        return new SimpleAuthenticationInfo(jwtPlayload, Boolean.TRUE, getName());
    }

    /**
     * 授权,JWT已包含访问主张只需要解析其中的主张定义就行了
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        JwtPlayload jwtPlayload = (JwtPlayload)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        logger.debug("doGetAuthorizationInfo playload:" + jwtPlayload);
        // 解析角色并设置
        Set<String> roles = Sets.newHashSet(StringUtils.split(jwtPlayload.getRoles(), ","));
        info.setRoles(roles);
        // 解析权限并设置
        Set<String> permissions = Sets.newHashSet(StringUtils.split(jwtPlayload.getPerms(), ","));
        info.setStringPermissions(permissions);
        return info;
    }
}