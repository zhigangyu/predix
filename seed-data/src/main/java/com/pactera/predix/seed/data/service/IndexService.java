/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pactera.predix.seed.data.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexService {

	@Value("${application.message:Hello World}")
	private String message = "Hello World";
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getHelloMessage() {
		return "Hello ray" + message;
	}
	/*
	@Autowired
	RedisTemplate<Serializable, Serializable> redisTemplate;
	
	@RequestMapping(value = "/api/foo", method = RequestMethod.POST, produces = { "application/json" })
	public void saveFoo(@RequestBody Lathe lathe){
		redisTemplate.execute(new RedisCallback<Object>(){

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				//connection.set(redisTemplate.getStringSerializer().serialize("lathe.uid." + lathe.getId()),redisTemplate.getStringSerializer().serialize(lathe));
				return null;
			}});
	}*/

}
