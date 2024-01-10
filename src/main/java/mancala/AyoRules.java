package mancala;

public class AyoRules extends GameRules {
    private int skipPoint;
    private int playerNum;
    private int midPoint = 6;
    private int endPoint = 12;
    
    /**
     * Moves stones from the specified pit and returns the number of stones added to the player's store.
     *
     * <p>Validates the move based on the specified starting pit, player number, and game state.
     * If the move is valid, it distributes stones across pits and captures stones if applicable.
     *
     * @param startPit The pit from which stones are to be moved.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public int moveStones(final int startPit, int playerNum)throws InvalidMoveException{
        if(startPit > 12 || startPit < 1){
            throw new InvalidMoveException("Invalid Pit");
        }

        int numStones = getDataStructure().getNumStones(startPit);

        if(numStones == 0){
            throw new InvalidMoveException("Pit is Empty. Invalid Move");
        }

        if(playerNum == 1 && startPit > 6){
            throw new InvalidMoveException("Pit does not belong to current player.");
        }
        if(playerNum == 2 && startPit < 7){
            throw new InvalidMoveException("Pit does not belong to current player.");
        }

        skipPoint = startPit;
        final int preStoneCount = getDataStructure().getStoreCount(playerNum);

        distributeStones(startPit);

        final int postStoreCount = getDataStructure().getStoreCount(playerNum);

        return postStoreCount - preStoneCount;
    
    }


    /**
     * Distributes stones from the specified starting point across pits and returns the total number of stones distributed.
     *
     * <p>Stones are distributed based on the rules of the Ayo game. Captures stones if necessary.
     *
     * @param startingPoint The pit from which stones are to be distributed.
     * @return The total number of stones distributed.
     */
    public int distributeStones(final int startingPoint){
        int numStones = getDataStructure().removeStones(startingPoint);

        final int count = numStones;

        
        return captureStones(distributeAcrossPits(startingPoint, numStones))+count;

    }


    /**
     * Distributes stones across pits starting from the specified point and returns the last pit reached.
     *
     * <p>The distribution process follows the Ayo game rules. Handles bonus moves and captures stones.
     *
     * @param startingPoint The pit from which stones are to be distributed.
     * @param numStones The number of stones to be distributed.
     * @return The last pit reached during the distribution.
     */
    private int distributeAcrossPits(final int startingPoint, int numStones){
        int currentPoint = startingPoint;
        playerNum = (startingPoint <= 6) ? 1 : 2;
        boolean isLastStore = false;
        setBonus(false);
        while (numStones > 0){
            isLastStore = false;
            if(playerNum == 1){
                if((currentPoint == midPoint)){
                    
                    getDataStructure().addToStore(playerNum, 1);
                    numStones--; 
                    if(numStones == 0){
                        isLastStore = true;
                    }
                }
                if(currentPoint == 12){
                    currentPoint = 0;
                }
            }else{
                if((currentPoint == endPoint)){
                    getDataStructure().addToStore(playerNum, 1);
                    numStones--;
                    if(numStones == 0){
                        isLastStore = true;
                    }
                    if(numStones != 0){
                       currentPoint = 0;
                    }
                    
                }
                
            }
            
            if(numStones > 0 ){
                getDataStructure().addStones(currentPoint+1, 1);
                numStones--;
                currentPoint++;
            }
            if(numStones == 0){
                if(!isLastStore && getDataStructure().getNumStones(currentPoint) != 1){
                    numStones = getDataStructure().removeStones(currentPoint);
                    System.out.println("-----------"+currentPoint+"------------" + numStones);
                    for (int i = 0; i < 12; i++) {
                        System.out.print(String.format("%2d ", i+1));
                    }
                    System.out.println();
                    for (int i = 1; i < 13; i++) {
                        System.out.print(String.format("%2d ", getDataStructure().getNumStones(i)));
                    }
                    System.out.println();
                }
                
            }
    
    
        }
    
        return currentPoint;
    }


    /**
     * Captures stones from the opponent's pit based on the stopping point and returns the number of stones captured.
     *
     * <p>The capturing process is influenced by the player's number and the opponent's pit state.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    public int captureStones(final int stoppingPoint){
        if(stoppingPoint == -1){
            return 0;
        }

        int finalValue = 0;
        int capturedStones = 0;

        if(playerNum == 1 && stoppingPoint < midPoint && getDataStructure().getNumStones(stoppingPoint) == 1){
            capturedStones = addingCapturedStones(stoppingPoint)-1;
            finalValue = capturedStones;

        }else if(playerNum == 2 && stoppingPoint >= midPoint && getDataStructure().getNumStones(stoppingPoint) == 1){
            capturedStones = addingCapturedStones(stoppingPoint)-1;
            finalValue = capturedStones;
        }

        return finalValue;
    }

/**
     * Adds the captured stones from the opponent's pit to the player's store and returns the number of stones captured.
     *
     * <p>The capturing process is based on the specified stopping point.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
private int addingCapturedStones(final int stoppingPoint){
    final int oppositePit = 11 - stoppingPoint;
    int capturedStones = 0;
    if (getDataStructure().getNumStones(oppositePit) > 0) {  // Opposite pit is not empty
        capturedStones = getDataStructure().removeStones(oppositePit);
        getDataStructure().addToStore(playerNum, capturedStones);
    }
    return capturedStones;
}


}