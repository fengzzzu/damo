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

package com.damo.service;

import com.alibaba.dubbo.config.annotation.Service;

@Service(
    version = "1.0.0", 
    application = "${dubbo.application.id}", 
    protocol = "${dubbo.protocol.id}", timeout=4000,
    registry = "${dubbo.registry.id}")
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name,Long time) {
        
        if(time>0){
            try {
                Thread.sleep(time);
                System.out.println("hello," + name +",time="+time);
            } catch (InterruptedException e) {
                 e.printStackTrace();
            }
        }
        return "Hello, " + name + " (from Spring Boot)";
    }

}