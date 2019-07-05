package jadeutils.math;

public class DerivedFunction implements Function {

	private double delta = 0.0000000001;
	private Function function = null;
	private int idx = 0;

	public DerivedFunction(Function function, int idx, double delta) {
		this.delta = delta;
		this.function = function;
		this.idx = idx;
	}

	public DerivedFunction(Function function, int idx) {
		this.function = function;
		this.idx = idx;
	}

	public DerivedFunction(Function function) {
		this.function = function;
	}

	@Override
	public double eval(double... args) {
		double v2 = function.eval(args);
		args[idx] = args[idx] + delta;
		double v1 = function.eval(args);
		return (v1 - v2) / delta;
	}

	public static void main(String[] args) {
		Function func = new Function() {
			@Override
			public double eval(double... args) {
				return args[0] * args[0] + args[1] * args[1];
			}
		};
		Function standX = new Function() {
			@Override
			public double eval(double... args) {
				return args[0] * 2.0;
			}
		};

		Function standY = new Function() {
			@Override
			public double eval(double... args) {
				return args[1] * 2.0;
			}
		};
		double [][] params = 
			{{3.000, 2.000},  //
			 {2.400, 1.600},   //
			 {1.920, 1.280},   //
			 {1.536, 1.024},   //
			 {1.229, 0.819},   //
			 {0.983, 0.655},   //
			 {0.786, 0.524},   //
			 {0.629, 0.419},   //
			 {0.503, 0.336},   //
			 {0.403, 0.268},   //
			 {0.322, 0.215},   //
			 {0.258, 0.172},   //
			 {0.206, 0.137},   //
			 {0.165, 0.110},   //
			 {0.132, 0.088},   //
			 {0.106, 0.070},   //
			 {0.084, 0.056},   //
			 {0.068, 0.045},   //
			 {0.054, 0.036},   //
			 {0.043, 0.029},   //
			 {0.035, 0.023},   //
			 {0.028, 0.018},   //
			 {0.022, 0.015},   //
			 {0.018, 0.012},   //
			 {0.014, 0.009},   //
			 {0.011, 0.008},   //
			 {0.009, 0.006},   //
			 {0.007, 0.005},   //
			 {0.006, 0.004},   //
			 {0.005, 0.003},   //
			 {0.004, 0.002},   //
			 {0.003, 0.002},   //
			 {0.002, 0.002},   //
			 {0.002, 0.001},   //
			 {0.002, 0.001},   //
			 {0.001, 0.001},   //
			 {0.001, 0.001},   //
			 {0.001, 0.001},   //
			 {0.001, 0.000},   //
			 {0.000, 0.000}}   ;
		Function funcDx = new DerivedFunction(func, 0);
		Function funcDy = new DerivedFunction(func, 1);
		for(int i=0;i<params.length;i++) {
			double dzdx = funcDx.eval(params[i]);
			double dzdy = funcDy.eval(params[i]);
			double sx = standX.eval(params[i]);
			double sy = standY.eval(params[i]);
			double diffx = Math.abs(dzdx-sx);
			double diffy = Math.abs(dzdy-sy);
			System.out.println(String.format("%2.6f,%2.6f, %2.6f", dzdx, dzdy, Math.max(diffx, diffy)));
		}
	}
	
}
