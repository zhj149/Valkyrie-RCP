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
package org.valkyriercp.selection.dialog;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.valkyriercp.dialog.ApplicationDialog;
import org.valkyriercp.layout.TableLayoutBuilder;
import org.valkyriercp.rules.closure.Closure;

import javax.swing.*;
import java.awt.*;

/**
 * Base class for selection dialogs.
 * 
 * @author Peter De Bruycker
 */
public abstract class AbstractSelectionDialog extends ApplicationDialog {
	private String description;

	private Closure onSelectAction;
	private Closure onAboutToShow;

	public AbstractSelectionDialog(String title, Window parent) {
		super(title, parent);
	}

	protected JComponent createDialogContentPane() {
		TableLayoutBuilder builder = new TableLayoutBuilder();

		JComponent selectionComponent = createSelectionComponent();
		Assert.state(selectionComponent != null,
				"createSelectionComponent cannot return null");

		if (StringUtils.hasText(description)) {
			builder.cell(getApplicationConfig().componentFactory()
					.createLabelFor(description, selectionComponent));
			builder.relatedGapRow();
			builder.row();
		}

		builder.cell(selectionComponent);

		return builder.getPanel();
	}

	protected abstract JComponent createSelectionComponent();

	protected boolean onFinish() {
		onSelect(getSelectedObject());
		return true;
	}

	public void setDescription(String desc) {
		Assert.isTrue(!isControlCreated(),
				"Set the description before the control is created.");

		description = desc;
	}

	protected abstract Object getSelectedObject();

	protected void onSelect(Object selection) {
		if (onSelectAction != null) {
			onSelectAction.call(selection);
		} else {
			throw new UnsupportedOperationException(
					"Either provide an onSelectAction or override the onSelect method");
		}
	}

	public void setOnSelectAction(Closure onSelectAction) {
		this.onSelectAction = onSelectAction;
	}

	public void setOnAboutToShow(Closure onAboutToShow) {
		this.onAboutToShow = onAboutToShow;
	}

	protected void onAboutToShow() {
		if (onAboutToShow != null) {
			onAboutToShow.call(null);
		}
	}
}
