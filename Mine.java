public class Mine {
	private boolean hasMine;
	private int numAdjacentMines;
	private String outputValue;

	public Mine() // use for blank tiles
	{
		hasMine = false;
		numAdjacentMines = 0;
		outputValue = "";
		
	}

	public Mine(boolean hasMine_) // places a single mine
	{
		if(hasMine_ == true) {
			hasMine = true;
			numAdjacentMines = 0;
			outputValue = "m";
		} else {
			hasMine = false;
			numAdjacentMines = 0;
			outputValue = "";
		}
	}
	
	public void setHasMine(boolean hasMine_) {
		hasMine = hasMine_;
		outputValue = "m";
	}
	public void setNumAdjacentMines(int nm) {
		numAdjacentMines = nm;
	}
	
	public boolean getHasMine() {
		return hasMine;
	}
	public int getNumAdajacentMines() {
		return numAdjacentMines;
	}
	public String getOutputValue() {
		if(this.getHasMine() == false){
			if(numAdjacentMines != 0){
				return Integer.toString(numAdjacentMines);
			}
		}else{
			return outputValue;
		}
		return outputValue;
	}
}