package org.valkyriercp.application.config.support;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.valkyriercp.application.ApplicationException;

import javax.swing.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Configurer for specifying global UIManager defaults.
 *
 * @author Keith Donald
 */
public class UIManagerConfigurer {

    private static final String CROSS_PLATFORM_LOOK_AND_FEEL_NAME = "crossPlatform";

    private static final String SYSTEM_LOOK_AND_FEEL_NAME = "system";

    public UIManagerConfigurer() {
        this(true);
    }

    public UIManagerConfigurer(boolean installPrePackagedDefaults) {
        if (installPrePackagedDefaults) {
            installPrePackagedUIManagerDefaults();
        }
        try {
            doInstallCustomDefaults();
        }
        catch (Exception e) {
            throw new ApplicationException("Unable to install subclass custom defaults", e);
        }
    }

    /**
     * Template method subclasses may override to install custom look and feels
     * or UIManager defaults.
     *
     * @throws Exception
     */
    protected void doInstallCustomDefaults() throws Exception {

    }

    /**
     * Initializes the UIManager defaults to values based on recommended,
     * best-practices user interface design. This should generally be called
     * once by an initializing application class.
     */
    private void installPrePackagedUIManagerDefaults() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Tree.line", "Angled");
            UIManager.put("Tree.leafIcon", null);
            UIManager.put("Tree.closedIcon", null);
            UIManager.put("Tree.openIcon", null);
            UIManager.put("Tree.rightChildIndent", new Integer(10));
        }
        catch (Exception e) {
            throw new ApplicationException("Unable to set defaults", e);
        }
    }

    public void setProperties(Properties properties) {
        Iterator i = properties.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            UIManager.put(entry.getKey(), entry.getValue());
        }
    }

    public void setInstallCustomLookAndFeels(String[] customLookAndFeels) {
        for (int i = 0; i < customLookAndFeels.length; i++) {
            String[] feels = StringUtils.commaDelimitedListToStringArray(customLookAndFeels[i]);
            Assert.isTrue(feels.length > 0, "LookAndFeelInfo definition should be in form: [name],<classname>");
            String name = null;
            String className;
            if (feels.length == 1) {
                className = feels[0];
            }
            else if (feels.length > 1) {
                name = feels[0];
                className = feels[1];
            }
            else {
                throw new RuntimeException("Should not happen");
            }
            UIManager.installLookAndFeel(name, className);
        }
    }

    public void setLookAndFeel(Class lookAndFeel) {
        setLookAndFeel(lookAndFeel.getName());
    }

    public void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
        }
        catch (Exception e) {
            throw new ApplicationException("Unable to set look and feel", e);
        }
    }

    public void setLookAndFeelWithName(String lookAndFeelName) {
        try {
            if (lookAndFeelName.equalsIgnoreCase(SYSTEM_LOOK_AND_FEEL_NAME)) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            else if (lookAndFeelName.equalsIgnoreCase(CROSS_PLATFORM_LOOK_AND_FEEL_NAME)) {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
            else {
                UIManager.LookAndFeelInfo[] feels = UIManager.getInstalledLookAndFeels();
                for (UIManager.LookAndFeelInfo feel : feels)
                {
                    if (feel.getName().equalsIgnoreCase(lookAndFeelName))
                    {
                        UIManager.setLookAndFeel(feel.getClassName());
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            throw new ApplicationException("Unable to set look and feel", e);
        }
    }
}