package eapli.base.infrastructure.grammar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eapli.base.Application;

/**
 * GrammarContext
 *
 * provides easy access to the grammar tools
 */
public final class GrammarContext {

    private static final Logger logger = LoggerFactory.getLogger(GrammarContext.class);

    private static GrammarToolFactory factory;

    private GrammarContext() {
        // ensure utility
    }

    public static GrammarToolFactory grammarTools() {
        if (factory == null) {
            final var factoryClassName = Application.settings().getGrammarToolsFactory();
            try {
                factory = (GrammarToolFactory) Class.forName(factoryClassName).newInstance();
            } catch (Exception e) {
                logger.error("Unable to dynamically load the Grammar Tools Factory", e);
                throw new IllegalStateException(
                        "Unable to dynamically load the Grammar Tools Factory: " + factoryClassName, e);
            }
        }

        return factory;
    }
}
