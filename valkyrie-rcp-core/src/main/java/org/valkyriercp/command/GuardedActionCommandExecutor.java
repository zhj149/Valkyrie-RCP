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
package org.valkyriercp.command;

import org.valkyriercp.core.Guarded;

import java.beans.PropertyChangeListener;

/**
 * An {@link ActionCommandExecutor} that can be enabled or disabled, with optional listeners
 * for these state changes.
 *
 * @author Keith Donald
 */
public interface GuardedActionCommandExecutor extends Guarded, ActionCommandExecutor {

	/**
	 * Adds the given listener to the collection of listeners that will be notified
	 * when the command executor's enabled state changes.
	 *
	 * @param listener The listener to be added.
	 */
    public void addEnabledListener(PropertyChangeListener listener);

    /**
     * Removes the given listener from the collection of listeners that will be
     * notified when the command executor's enabled state changes.
     *
     * @param listener The listener to be removed.
     */
    public void removeEnabledListener(PropertyChangeListener listener);

}
