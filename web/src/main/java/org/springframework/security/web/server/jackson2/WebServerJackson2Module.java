/*
 * Copyright 2015-2018 the original author or authors.
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

package org.springframework.security.web.server.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.web.server.csrf.DefaultCsrfToken;

/**
 * Jackson module for spring-security-web-flux. This module register {@link DefaultCsrfServerTokenMixin}
 * If no default typing enabled by default then it'll enable it because typing info is needed to
 * properly serialize/deserialize objects.
 * In order to use this module just add this module into your ObjectMapper configuration.
 *
 * <pre>
 *     ObjectMapper mapper = new ObjectMapper();
 *     mapper.registerModule(new WebServerJackson2Module());
 * </pre>
 * <b>Note: use {@link SecurityJackson2Modules#getModules(ClassLoader)} to get list of all security modules.</b>
 *
 * @author Boris Finkelshteyn
 * @see SecurityJackson2Modules
 * @since 5.1
 */
public class WebServerJackson2Module extends SimpleModule {

	public WebServerJackson2Module() {
		super(WebServerJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
	}

	@Override
	public void setupModule(SetupContext context) {
		SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
		context.setMixInAnnotations(DefaultCsrfToken.class, DefaultCsrfServerTokenMixin.class);
	}
}
