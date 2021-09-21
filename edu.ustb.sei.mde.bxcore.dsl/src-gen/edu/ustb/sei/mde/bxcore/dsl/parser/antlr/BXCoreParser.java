/*
 * generated by Xtext 2.18.0.M3
 */
package edu.ustb.sei.mde.bxcore.dsl.parser.antlr;

import com.google.inject.Inject;
import edu.ustb.sei.mde.bxcore.dsl.parser.antlr.internal.InternalBXCoreParser;
import edu.ustb.sei.mde.bxcore.dsl.services.BXCoreGrammarAccess;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class BXCoreParser extends AbstractAntlrParser {

	@Inject
	private BXCoreGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalBXCoreParser createParser(XtextTokenStream stream) {
		return new InternalBXCoreParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "BXProgram";
	}

	public BXCoreGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(BXCoreGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}