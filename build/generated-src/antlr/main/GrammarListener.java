// Generated from Grammar.g4 by ANTLR 4.9.3
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(GrammarParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(GrammarParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#type_declare}.
	 * @param ctx the parse tree
	 */
	void enterType_declare(GrammarParser.Type_declareContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#type_declare}.
	 * @param ctx the parse tree
	 */
	void exitType_declare(GrammarParser.Type_declareContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#reference_declare}.
	 * @param ctx the parse tree
	 */
	void enterReference_declare(GrammarParser.Reference_declareContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#reference_declare}.
	 * @param ctx the parse tree
	 */
	void exitReference_declare(GrammarParser.Reference_declareContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#attribute_declare}.
	 * @param ctx the parse tree
	 */
	void enterAttribute_declare(GrammarParser.Attribute_declareContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#attribute_declare}.
	 * @param ctx the parse tree
	 */
	void exitAttribute_declare(GrammarParser.Attribute_declareContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#table_declare}.
	 * @param ctx the parse tree
	 */
	void enterTable_declare(GrammarParser.Table_declareContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#table_declare}.
	 * @param ctx the parse tree
	 */
	void exitTable_declare(GrammarParser.Table_declareContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#main}.
	 * @param ctx the parse tree
	 */
	void enterMain(GrammarParser.MainContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#main}.
	 * @param ctx the parse tree
	 */
	void exitMain(GrammarParser.MainContext ctx);
}