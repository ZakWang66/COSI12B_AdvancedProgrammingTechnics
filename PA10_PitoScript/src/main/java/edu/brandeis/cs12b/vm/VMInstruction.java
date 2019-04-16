package edu.brandeis.cs12b.vm;

public class VMInstruction {
	private VMOp op;
	private String param1;
	private String param2;
	private String param3;
	private String comment;
	
	public VMInstruction(VMOp op) {
		this.op = op;
	}

	public VMInstruction(VMOp set, String... args) {
		this.op = set;
		
		if (args.length >= 1)
			param1 = args[0];
		
		if (args.length >= 2)
			param2 = args[1];
		
		if (args.length >= 3)
			param3 = args[2];
		
	}
	
	public VMInstruction(String comment, VMOp set, String... args) {
		this.comment = comment;
		this.op = set;
		
		if (args.length >= 1)
			param1 = args[0];
		
		if (args.length >= 2)
			param2 = args[1];
		
		if (args.length >= 3)
			param3 = args[2];
		
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}
	
	public VMOp getOp() {
		return op;
	}
	
	@Override
	public String toString() {
		return op + " " + param1 + " " + (param2 != null ? param2 : "") + " " + (param3 != null ? param3 : "") 
				+ "\t//" + (comment != null ? comment : "");
	}


}
