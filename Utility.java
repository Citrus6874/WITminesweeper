public class Utility {
	
	public Utility()
	{
		
	}
	
	public  Square[][] populateBoard(Square[][] attributes)
	{
		  //The '*' in the loop below represents the square currently being evaluated
        for(int y = 0; y < attributes.length; y++){
            for(int x = 0; x < attributes[y].length; x++)
            {
                if(x == 0 && y == 0){//top left
                    // _____________
                    // | * |0,1|   |
                    // |1,0|1,1|   |
                    // |   |   |   |
                    if(attributes[0][0].getHasMine())
                    {
                        continue;
                    }
                    int tempAdj=0;
                    if(attributes[0][1].getHasMine()){ tempAdj++; }
                    if(attributes[1][0].getHasMine()){ tempAdj++; }
                    if(attributes[1][1].getHasMine()){ tempAdj++; }
                    attributes[0][0].setNumAdjacentMines(tempAdj);
                    }
                    
                    
                    else if( y == attributes.length-1 && x == 0) // bottom left corner
                    {
                        if(attributes[attributes.length-1][0].getHasMine()){
                            continue;
                        }
                        int tempAdj=0;
                        if(attributes[attributes.length-2][0].getHasMine()){
                            tempAdj++;
                        }
                        if(attributes[attributes.length-2][1].getHasMine()){
                            tempAdj++;
                        }
                        if(attributes[attributes.length-1][1].getHasMine()){
                            tempAdj++;
                        }
                        attributes[attributes.length-1][0].setNumAdjacentMines(tempAdj);
                    }
                    else if( y == attributes.length-1 && x == attributes[y].length-1) // bottom right corner
                    {
                        if(attributes[attributes.length-1][attributes[y].length-1].getHasMine()){
                            continue;
                        }
                        int tempAdj=0;
                        if(attributes[attributes.length-2][attributes[y].length-2].getHasMine()){
                            tempAdj++;
                        }
                        if(attributes[attributes.length-2][attributes[y].length-1].getHasMine()){
                            tempAdj++;
                        }
                        if(attributes[attributes.length-1][attributes[y].length-2].getHasMine()){
                            tempAdj++;
                        }
                        attributes[attributes.length-1][attributes[y].length-1].setNumAdjacentMines(tempAdj);
                    }
                    else if( y == 0 && x == attributes[y].length) //top right
                    {
                        if(attributes[0][attributes[y].length].getHasMine()){
                            continue;
                        }
                        int tempAdj=0;
                        if(attributes[0][attributes[y].length-1].getHasMine()){
                            tempAdj++;
                        }
                        if(attributes[1][attributes[y].length-1].getHasMine()){
                            tempAdj++;
                        }
                        if(attributes[1][attributes[y].length].getHasMine()){
                            tempAdj++;
                        }
                        attributes[0][attributes[y].length].setNumAdjacentMines(tempAdj);
                    }
                    else if(y == 0)// top row
                    {
                        if(attributes[0][x].getHasMine()){
                            continue;
                        }
                        int tempAdj=0;
                        if(attributes[y][x-1].getHasMine()){
                            tempAdj++;
                        }
                        if(x != attributes[y].length -1){
                            if(attributes[y][x+1].getHasMine()){
                                tempAdj++;
                            }
                        }
                        if(attributes[y+1][x].getHasMine()){
                            tempAdj++;
                        }
                        if(attributes[y+1][x-1].getHasMine()){
                            tempAdj++;
                        }
                        if(x != attributes[y].length-1){
                            if(attributes[y+1][x+1].getHasMine()){
                                tempAdj++;
                            }
                        }
                        attributes[y][x].setNumAdjacentMines(tempAdj);
                    }
                    else if(x == 0)//left column
                    {
                        if(attributes[y][0].getHasMine()){
                            continue;
                        }
                        int tempAdj = 0;
                        if(attributes[y+1][x].getHasMine())/*beneath of current square*/{
                            tempAdj++;
                        }
                        if(attributes[y][x+1].getHasMine())/* right of current square*/{
                            tempAdj++;
                        }
                        if(attributes[y+1][x+1].getHasMine())/* South East diagonal of current square*/{
                            tempAdj++;
                        }
                        if(attributes[y-1][x].getHasMine()){ /* Directly above current square*/
                            tempAdj++;
                        }
                        if(attributes[y-1][x+1].getHasMine()){ // NE Diagonal of current square
                            tempAdj++;
                        }
                        attributes[y][x].setNumAdjacentMines(tempAdj);
                    }
                    else if(x == attributes[y].length)// right column
                    {
                        if(attributes[y][x].getHasMine()){
                            continue;
                        }
                        int tempAdj = 0;
                        if(attributes[y+1][x].getHasMine())/*beneath current square*/{
                            tempAdj++;
                        }
                        if(attributes[y][x-1].getHasMine())/* left of current square*/{
                            tempAdj++;
                        }
                        if(attributes[y+1][x-1].getHasMine())/* SW diagonal current square*/{
                            tempAdj++;
                        }
                        if(attributes[y-1][x].getHasMine()){ // directly above current square
                            tempAdj++;
                        }
                        if(attributes[y-1][x-1].getHasMine()){ // NW of current square
                            tempAdj++;
                        }
                        attributes[y][x].setNumAdjacentMines(tempAdj);
                    }
                    else if(y == attributes.length)//bottom row
                    {
                        if(attributes[y][x].getHasMine()){
                            continue;
                        }
                        int tempAdj=0;
                        if(attributes[y][x+1].getHasMine())/* right of bottom right*/{
                            tempAdj++;
                        }
                        if(attributes[y-1][x].getHasMine())/* top of bottom right*/{
                            tempAdj++;
                        }
                        if(attributes[y][x-1].getHasMine()){ // directly left of current square.
                            tempAdj++;
                        }
                        if(attributes[y-1][x-1].getHasMine()){ // NW of current square
                            tempAdj++;
                        }
                        if(attributes[y-1][x+1].getHasMine()){ //NE of current square
                            tempAdj++;
                        }
                        attributes[y][x].setNumAdjacentMines(tempAdj);
                    }
                    else
                    {
                        if(attributes[y][x].getHasMine()){
                            continue;
                        }
                        int tempAdj = 0;
                        
                        if(attributes[y-1][x].getHasMine()){ // top of bottom right
                            tempAdj++;
                        }
                        if(attributes[y][x-1].getHasMine()){ // directly left of current square.
                            tempAdj++;
                        }
                        if(x != attributes[y].length-1){
                            if(attributes[y][x+1].getHasMine()){ // directly right of current square.
                                tempAdj++;
                            }
                        }
                        if(attributes[y-1][x-1].getHasMine()){ // NW of current square
                            tempAdj++;
                        }
                        if(y != 0){
                            if((x+1) != attributes[y].length){
                                if(attributes[y-1][x+1].getHasMine()){ // NE of current square
                                    tempAdj++;
                                }    
                            }
                        }
                        if(x != 0 && y != attributes.length-1){
                            if(attributes[y+1][x-1].getHasMine()){ //West of current square
                                tempAdj++;
                            }
                        }
                        if(y != attributes.length-1){
                            if(attributes[y+1][x].getHasMine()){  //South of current square
                                tempAdj++;
                            }
                        }
                        if(y != attributes.length-1){
                            if(x != attributes[y].length-1){
                                if(attributes[y+1][x+1].getHasMine()){ // SE of current square
                                    tempAdj++;
                                }
                            }
                        }
                        attributes[y][x].setNumAdjacentMines(tempAdj);
                    }
                    
                }
            }
        
        return attributes;
	}
	
	
	public Square[][] initializeMineAttributes(Square[][] attributes, int verticalTiles, int horizontalTiles, int mines)
	{
		
        attributes = new Square[verticalTiles][horizontalTiles];
        int minesCounter = mines;
        
        for(int y = 0; y < attributes.length; y++){
            for( int x = 0; x < attributes[y].length; x++){
                attributes[y][x] = new Square(false);
            }
        }
        
        for(int i = 0; i < minesCounter; i++){
            double xtemp = (Math.random()* attributes[1].length);
            double ytemp = (Math.random()* attributes.length );
            int x = (int)xtemp;
            int y = (int)ytemp;
            
            System.out.print("y = " + (y+1) + ", ");
            System.out.print("x = " + (x+1) + " || ");
            
            if(attributes[y][x].getHasMine() == true){
                i--;
                continue;
            }
            attributes[y][x].setHasMine(true);
        }
        
        System.out.print("\n");
        attributes = populateBoard(attributes);
		
		
		
		return attributes;
	
	}
}