package edu.brandeis.cs12b.pa10.compiler;

import java.util.ArrayList;
import java.util.List;

import edu.brandeis.cs12b.pa10.parser.ParseTreeNode;
import edu.brandeis.cs12b.vm.VMInstruction;

public class Compiler {
	
	List<ParseTreeNode> ptn;
	RAMController ram;
	
	public Compiler(List<ParseTreeNode> ptn) {
		this.ptn = ptn;
		ram = new RAMController();
	}

	/**
	 * Transforms a list of parse trees into instructions for the PitoScript VM
	 * @return the compiled list of VMInstructions
	 */
	public List<VMInstruction> compile() {
		// TODO Implement me!
		List<VMInstruction> l = new ArrayList<>();
		for (ParseTreeNode node : ptn) {
			l.addAll(evaluateNode(node.getRight()));
			l.addAll(ram.pop("r0"));
			l.addAll(ram.putList("r0", node.getLeft().getLexeme().getValueAsString()));
		}
		return l;

	}
	
	private List<VMInstruction> evaluateNode(ParseTreeNode node) {
		List<VMInstruction> l = new ArrayList<>();
		String value = node.getLexeme().getValueAsString();
		switch (node.getLexeme().getType()) {
		case NUMBER:
			l.add(ram.writeNum(value, "r0"));
			break;
		case USER_INPUT:
			l.add(ram.writeInput("r0"));
			break;
		case VARIABLE:
			l.addAll(ram.searchList(value, "r0"));
			break;
		case OPERATOR:
			l.addAll(evaluateNode(node.getLeft()));
			l.addAll(evaluateNode(node.getRight()));
			l.addAll(ram.pop("r2"));
			l.addAll(ram.pop("r0"));
			l.add(ram.calc("r0", "r2", value));
			break;
		default: return null;
		}
		l.addAll(ram.push("r0"));
		return l;
	}
}
