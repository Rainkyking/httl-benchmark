/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package httl.test.engines;

import httl.test.Benchmark;
import httl.test.util.DateTool;
import httl.util.Version;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * VelocityCase
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class Velocity implements Benchmark {

	private VelocityEngine engine;

	public Velocity() {
		Properties properties = new Properties();
		properties.put("resource.loader", "classpath");
		properties.put("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		properties.put("classpath.resource.loader.cache", "true");
		engine = new VelocityEngine(properties);
	}

	public String getVersion() {
		return Version.getVersion(VelocityEngine.class, "0.0.0");
	}

	public void execute(int times, String name, Map<String, Object> map, Object out) throws Exception {
		name += ".vm";
		if (out instanceof OutputStream) {
			out = new OutputStreamWriter((OutputStream) out);
		}
		VelocityContext context = new VelocityContext();
		context.put("date", new DateTool());
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}
		for (int i = times; i >= 0; i --) {
			engine.getTemplate(name).merge(context, (Writer) out);
		}
	}

}
