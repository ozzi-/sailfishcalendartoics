package jollatoics;

public class Progress {
	static void updateProgress(double progressPercentage) {
		final int widthInChars = 50; 
		System.out.print("\r[");
		int i = 0;
		for (; i <= (int) (progressPercentage * widthInChars); i++) {
			System.out.print(".");
		}
		for (; i < widthInChars; i++) {
			System.out.print(" ");
		}
		System.out.print("]");
	}
}