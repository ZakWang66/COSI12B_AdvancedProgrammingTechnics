package edu.brandeis.cs12b.vm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VM {
	private double[] ram;

	private Map<String, Double> registers;
	private Map<String, Double> outputs;

	private Scanner sc;

	public VM() {
		ram = new double[1000];
		registers = new HashMap<String, Double>();
		registers.put("r0", 0.0);
		registers.put("r1", 0.0);
		registers.put("r2", 0.0);

		sc = new Scanner(System.in);

		outputs = new HashMap<String, Double>();

	}

	public Map<String, Double> evaluate(List<VMInstruction> program) {
		program.forEach(this::execute);

		// read the memory value of each output
		Map<String, Double> toR = new HashMap<String, Double>();
		
		outputs.entrySet().forEach(e -> {
			toR.put(e.getKey(), ram[(int) e.getValue().doubleValue()]);
		});
		
		return toR;
	}

	private void execute(VMInstruction i) {
		switch (i.getOp()) {
		case SET:
			storeToRegister(i.getParam1(), Double.valueOf(i.getParam2()));
			break;
		case LOAD:
			storeToRegister(i.getParam1(), ram[(int) readRegister(i.getParam2())]);
			break;
		case STORE:
			ram[(int)readRegister(i.getParam1())] = readRegister(i.getParam2());
			break;
		case OUTPUT:
			outputs.put(i.getParam1(), readRegister(i.getParam2()));
			break;
		case PROMPT:
			System.out.print("Input? ");
			storeToRegister(i.getParam1(), sc.nextDouble());
			break;





		case ADD:
			storeToRegister(i.getParam1(), readRegister(i.getParam2()) + readRegister(i.getParam3()));
			break;
		case DIV:
			storeToRegister(i.getParam1(), readRegister(i.getParam2()) / readRegister(i.getParam3()));
			break;
		case MUL:
			storeToRegister(i.getParam1(), readRegister(i.getParam2()) * readRegister(i.getParam3()));
			break;
		case POW:
			storeToRegister(i.getParam1(), Math.pow(readRegister(i.getParam2()), readRegister(i.getParam3())));
			break;
		case SUB:
			storeToRegister(i.getParam1(), readRegister(i.getParam2()) - readRegister(i.getParam3()));
			break;
		default:
			break;

		}
	}

	private void storeToRegister(String param1, double i) {
		if (param1.equals("r0") || param1.equals("r1") || param1.equals("r2")) {
			registers.put(param1, i);
			return;
		}

		throw new IllegalArgumentException("Register does not exist: " + param1);

	}

	private double readRegister(String param1) {
		if (param1.equals("r0") || param1.equals("r1") || param1.equals("r2"))
			return registers.get(param1);

		throw new IllegalArgumentException("Register does not exist: " + param1);

	}

	public static void main(String[] args) {
		VM vm = new VM();

		List<VMInstruction> prgm = new LinkedList<VMInstruction>();

		prgm.add(new VMInstruction(VMOp.SET, "r1", "5"));
		prgm.add(new VMInstruction(VMOp.SET, "r2", "6"));
		prgm.add(new VMInstruction(VMOp.ADD, "r0", "r1", "r2"));
		prgm.add(new VMInstruction(VMOp.SET, "r1", "0"));
		prgm.add(new VMInstruction(VMOp.STORE, "r1", "r0"));
		prgm.add(new VMInstruction(VMOp.OUTPUT, "x", "r1"));

		System.out.println(vm.evaluate(prgm));





	}
}
