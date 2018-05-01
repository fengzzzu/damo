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

package com.damo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.damo.service.HelloService;

@RestController
public class HelloController {
    @Reference(version = "1.0.0", 
        application = "${dubbo.application.id}", timeout=4000,
        url = "dubbo://localhost:12345")
    private HelloService helloService;
    

    /**
     * http://localhost:8090/sayHello?name=11&time=100
     * @param name
     * @return
     */
    @RequestMapping("/sayHello")
    public String sayHello(@RequestParam String name,@RequestParam(defaultValue = "0") Long time) {
        return helloService.sayHello(name,time);
    }
}