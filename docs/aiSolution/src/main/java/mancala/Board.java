    package mancala;
    import java.util.ArrayList; 

    public class Board {
        private ArrayList<Pit> pits;
        private ArrayList<Store> stores;

        public Board(){
            this.pits = new ArrayList<>();
            this.stores = new ArrayList<>();
            this.initializeBoard();
        }


        public void resetBoard() {
            // Reset the board to the initial state
            this.pits.clear();
            this.stores.clear();
            this.initializeBoard();
        }

        public void setUpPits() { //used from AI
                for (int i = 0; i < 12; i++) {
                    this.pits.add(new Pit());
                }
            }

        public void setUpStores() {
                this.stores.add(new Store());
                this.stores.add(new Store());
            }


        public void initializeBoard() {
                this.setUpPits();
                this.setUpStores();
            }

        public ArrayList<Store> getStores() {
            return this.stores;
        }

        public ArrayList<Pit> getPits() {
                return this.pits;
        }

        public void registerPlayers(Player one, Player two) {
            // Register the players with their respective stores
            this.stores.get(0).setOwner(one);
            this.stores.get(1).setOwner(two);
        }

        public int moveStones(int startPit, Player player) {
            // Perform a move by distributing stones from a pit
            // Assumption: startPit is a valid index in the pits list
            int stones = this.pits.get(startPit).removeStones();
            return this.distributeStones(startPit + 1, stones, player);
        }

        public int distributeStones(int startingPoint, int stones, Player player) {
            // Assumption: startingPoint is a valid index in the pits list
            int i = startingPoint;
            while (stones > 0) {
                if (i >= this.pits.size()) {
                    i = 0;  // Loop back to the first pit
                }
                this.pits.get(i).addStone();
                stones--;
                i++;
            }
            return this.captureStones(i - 1, player);  // Capture stones if possible
        }

        public int captureStones(int stoppingPoint, Player player) {
            
            // Assumption: stoppingPoint is a valid index in the pits list
            if (this.pits.get(stoppingPoint).getStoneCount() == 1) {  
                int oppositePit = this.pits.size() - 1 - stoppingPoint;
                if (this.pits.get(oppositePit).getStoneCount() > 0) { 
                    int capturedStones = this.pits.get(oppositePit).removeStones();
                    capturedStones += this.pits.get(stoppingPoint).removeStones();
                    player.getStore().addStones(capturedStones);
                    return capturedStones;
                }
            }
            return 0;
        }

        public int getNumStones(int pitNum) {
            // Return the number of stones in a specific pit
            return this.pits.get(pitNum).getStoneCount();
        }

        public boolean isSideEmpty(int sideStartPitNum) {
            // Check if a side is empty (no stones in the pits)
            for (int i = sideStartPitNum; i < sideStartPitNum + 6; i++) {
                if (this.pits.get(i).getStoneCount() > 0) {
                    return false;
                }
            }
            return true;
        }
    }