package xyz.bobkinn.log4jutils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.util.KeyValuePair;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Plugin(name = "LoggerNameRewritePolicy", category = Core.CATEGORY_NAME, elementType = "rewritePolicy", printObject = true)
public class LoggerNameRewritePolicy implements RewritePolicy {

    @RequiredArgsConstructor
    public static class Selector {
        private final boolean isPackage;
        private final String oldName;
        private final String newName;
    }

    public static Selector createSelector(String oldName, String newName){
        return new Selector(oldName.endsWith("."), oldName, newName);
    }

    private final List<Selector> selectors;

    @PluginFactory
    public static LoggerNameRewritePolicy createPolicy(
            @PluginElement("KeyValuePair") final KeyValuePair[] map) {
        final List<Selector> selectors = new ArrayList<>();
        for (KeyValuePair pair : map){
            String[] oldNames = pair.getKey().split(",");
            for (String name : oldNames) {
                selectors.add(createSelector(name, pair.getValue()));
            }
        }
        return new LoggerNameRewritePolicy(selectors);
    }
    
    public Selector findSelector(String name){
        for (Selector sel : selectors){
            if (sel.isPackage && name.startsWith(sel.oldName)) {
                return sel;
            } else if (name.equals(sel.oldName)) {
                return sel;
            }
        }
        return null;
    }

    @Override
    public LogEvent rewrite(LogEvent event) {
        if (event.getLoggerName() == null) return event;
        final String name = event.getLoggerName();
        Selector selector = findSelector(name);
        if (selector == null) return event;
        return new Log4jLogEvent.Builder(event).setLoggerName(selector.newName).build();
    }
}
