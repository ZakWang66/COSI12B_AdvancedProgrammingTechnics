package edu.brandeis.cs12b.pa10.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brandeis.cs12b.pa10.parser.ParseTreeNode;
import edu.brandeis.cs12b.vm.VMInstruction;
import edu.brandeis.cs12b.vm.VMOp;

public class RAMController {
	
	// r0-writtenData/lodedData r1-address r2-loadedData
	
	private Integer pStack;
	private Integer pList;
	private Map<String, Integer> varAddr;
	
	public RAMController() {
		pStack = -1;
		pList = 1000;
		varAddr = new HashMap<>();
	}
	
	public VMInstruction writeNum(String value, String reg) {
		return new VMInstruction(VMOp.SET, reg, value);
	}
	
	public VMInstruction writeInput(String reg) {
		return new VMInstruction(VMOp.PROMPT, reg);
	}
	
	public VMInstruction calc(String reg1, String reg2, String op) {
		switch (op) {
		case "+": return new VMInstruction(VMOp.ADD, "r0", "r0", "r2");
		case "-": return new VMInstruction(VMOp.SUB, "r0", "r0", "r2");
		case "*": return new VMInstruction(VMOp.MUL, "r0", "r0", "r2");
		case "/": return new VMInstruction(VMOp.DIV, "r0", "r0", "r2");
		case "^": return new VMInstruction(VMOp.POW, "r0", "r0", "r2");
		}	
		return null;
	}
	
	public List<VMInstruction> push(String sourceReg) {
		List<VMInstruction> l = new ArrayList<>();
		if (pStack + 1 >= pList) {
			System.out.println("Stack overflow");
			return null;
		}
		pStack++;
		l.add(new VMInstruction(VMOp.SET, "r1", pStack.toString()));
		l.add(new VMInstruction(VMOp.STORE, "r1", sourceReg));
		return l;
	}
	
	public List<VMInstruction> pop(String dataReg) {
		List<VMInstruction> l = new ArrayList<>();
		if (pStack < 0) {
			System.out.println("Stack underflow");
			return null;
		}
		l.add(new VMInstruction(VMOp.SET, "r1", pStack.toString()));
		l.add(new VMInstruction(VMOp.LOAD, dataReg, "r1"));
		pStack--;
		return l;
	}
	
	public List<VMInstruction> putList(String sourceReg, String varName) {
		List<VMInstruction> l = new ArrayList<>();
		if (pList - 1 <= pStack) {
			System.out.println("List overflow");
			return null;
		}
		pList--;
		l.add(new VMInstruction(VMOp.SET, "r1", pList.toString()));
		l.add(new VMInstruction(VMOp.STORE, "r1", sourceReg));	
		l.add(new VMInstruction(VMOp.OUTPUT, varName, "r1"));
		varAddr.put(varName, pList);
		return l;
	}
	
	public List<VMInstruction> searchList(String varName, String dataReg) {
		List<VMInstruction> l = new ArrayList<>();
		if (pList > 999) {
			System.out.println("List is empty");
			return null;
		}
		// No such var unchecked
		l.add(new VMInstruction(VMOp.SET, "r1", varAddr.get(varName).toString()));
		l.add(new VMInstruction(VMOp.LOAD, dataReg, "r1"));
		return l;
	}
	
}
