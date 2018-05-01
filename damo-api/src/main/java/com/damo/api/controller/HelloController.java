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

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.damo.api.base.BaseResponse;
import com.damo.api.base.GlobalException;


@RestController
@RequestMapping(value = "/hello")
public class HelloController {
    

    @ApiOperation(value = "打个招呼", notes = "")
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public BaseResponse success() {
        BaseResponse result = BaseResponse.success();

        return result;
    }
    
    @ApiOperation(value = "打个招呼", notes = "")
    @RequestMapping(value = "/fail", method = RequestMethod.GET)
    public BaseResponse fail() {
        BaseResponse result = BaseResponse.fail();

        return result;
    }
    
    @ApiOperation(value = "打个招呼", notes = "")
    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    public BaseResponse exception() {
        throw new GlobalException(300,"一个异常");
    }
    
    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long" ,paramType="path")
    @RequestMapping(value = "/findone/{userId}", method = RequestMethod.GET)
    public BaseResponse getUser(@PathVariable Long userId) {
        BaseResponse result = BaseResponse.success();

        return result;
    }
    
//    com.up360.dubbo.student.service.DubboStudentService
    
    
//    static Map<String, User> users = Collections.synchronizedMap(new HashMap<String, User>());
//
//    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
//    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
//    @RequestMapping(value = "add", method = RequestMethod.POST)
//    @ResponseBody
//    public String postUser(@RequestBody User user) {
//        users.put(user.getUserId(), user);
//        return "success" + user.getRealName();
//    }
//
//    @ApiOperation(value = "获取用户列表", notes = "")
//    @RequestMapping(value = {"lists"}, method = RequestMethod.GET)
//    public List<User> getUserList() {
//        List<User> r = new ArrayList<User>(users.values());
//        return r;
//    }
//
//    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
//    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
//    @RequestMapping(value = "/findone/{id}", method = RequestMethod.GET)
//    public User getUser(@PathVariable String id) {
//        return users.get(id);
//    }
//
//    @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
//    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String"),
//        @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")})
//    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
//    public String putUser(@PathVariable String id, @RequestBody User user) {
//        User u = users.get(id);
//        u.setRealName(user.getRealName());
//        u.setGrade(user.getGrade());
//        users.put(id, u);
//        return "success";
//    }
//
//    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
//    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
//    public String deleteUser(@PathVariable String id) {
//        users.remove(id);
//        return "success";
//    }
}