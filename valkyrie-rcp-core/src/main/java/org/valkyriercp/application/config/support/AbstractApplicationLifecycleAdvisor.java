/**
 * Copyright (C) 2015 Valkyrie RCP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.valkyriercp.application.config.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.valkyriercp.application.*;
import org.valkyriercp.application.config.ApplicationLifecycleAdvisor;
import org.valkyriercp.application.exceptionhandling.RegisterableExceptionHandler;
import org.valkyriercp.application.session.ApplicationSession;
import org.valkyriercp.application.session.ApplicationSessionInitializer;
import org.valkyriercp.application.support.SwingXStatusBar;
import org.valkyriercp.command.support.CommandGroup;

import javax.annotation.PostConstruct;

@Component
public abstract class AbstractApplicationLifecycleAdvisor implements ApplicationLifecycleAdvisor {
    @Autowired
    protected Application application;

    @Autowired
    protected ApplicationContext applicationContext;

    @Autowired
    protected ApplicationSession applicationSession;

	private ApplicationWindow openingWindow;

    protected PageDescriptor startingPageId;

    @Autowired
    private RegisterableExceptionHandler registerableExceptionHandler;

    @Autowired
    private ApplicationSessionInitializer applicationSessionInitializer;

    @Override
    public PageDescriptor getStartingPageDescriptor() {
        return startingPageId;
    }

    public void setStartingPageDescriptor(PageDescriptor startingPageId) {
        this.startingPageId = startingPageId;
    }

    @Override
    public void onPreStartup() {}

    @Override
    public void onPostStartup() {}

    @Override
    public void onWindowCreated(ApplicationWindow window) {}

    @Override
    public void onWindowOpened(ApplicationWindow window) {}

    @Override
    public boolean onPreWindowClose(ApplicationWindow window) { return true; }

    /**
	 * Hook called right before the application opens a window.
	 *
	 * @param configurer
	 */
	@Override
    public void onPreWindowOpen(ApplicationWindowConfigurer configurer) {
		configurer.setTitle(application.getName());
		configurer.setImage(application.getImage());
	}


    @PostConstruct
    public void registerExceptionHandler() {
        registerableExceptionHandler.registerExceptionHandler();
    }

    public ApplicationWindow getOpeningWindow() {
        return openingWindow;
    }

    public void setOpeningWindow(ApplicationWindow openingWindow) {
        this.openingWindow = openingWindow;
    }

    @Override
    public void onCommandsCreated(ApplicationWindow window) {
    }

    /**
	 * Create the menuBar for the application.
	 *
	 * @return a CommandGroup.
	 */
	public CommandGroup getMenuBarCommandGroup() {
		return new CommandGroup();
	}

	/**
	 * Create the toolBar for the application.
	 *
	 * @return a CommandGroup.
	 */
	public CommandGroup getToolBarCommandGroup() {
		return new CommandGroup();
	}

    @Override
    public CommandGroup getNavigationCommandGroup() {
        return new CommandGroup();
    }

    /**
	 * Create the statusBar for the application.
	 *
	 * @return a statusBar.
	 */
	public StatusBar getStatusBar() {
		return new SwingXStatusBar();
	}

    @Override
    public void onShutdown() { }

    public ApplicationSessionInitializer getApplicationSessionInitializer() {
        return applicationSessionInitializer;
    }

    @Override
    public RegisterableExceptionHandler getRegisterableExceptionHandler() {
        return registerableExceptionHandler;
    }
}
