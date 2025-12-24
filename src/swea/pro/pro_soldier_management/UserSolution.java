package swea.pro.pro_soldier_management;

import java.util.*;

class Node{
	int id, version;
	Node next;
	public Node(int id, Node next, int version){
		this.id = id;
		this.next = next;
		this.version = version;
	}
}

class Team{
	Node[] head = new Node[6];
	Node[] tail = new Node[6];
	public Team(){
		for(int i = 1; i <= 5; i++){
			head[i] = new Node(0, null, 0);
			tail[i] = head[i];
		}
	}
}
class UserSolution
{
	final int MAX = 100_001;
	Team[] teams;
	Map<Integer, Boolean> isHired;
	int[] id2team;
	Node[] id2Node;
	public void init()
	{
		isHired = new HashMap<>();
		teams = new Team[6];
		id2team = new int[MAX];
		id2Node = new Node[MAX];
		for(int i = 1; i <= 5; i++){
			teams[i] = new Team();
		}
	}
	
	public void hire(int mID, int mTeam, int mScore)
	{
		int ver = id2Node[mID] == null ? 0 : id2Node[mID].version;
		Node newNode = new Node(mID, null, ++ver);
		id2Node[mID] = newNode;
		if(teams[mTeam].head[mScore].next == null){
			teams[mTeam].head[mScore].next = newNode;
		}
		teams[mTeam].tail[mScore].next = newNode;
		teams[mTeam].tail[mScore] = newNode;
		id2team[mID] = mTeam;
		isHired.put(mID, true);
	}
	
	public void fire(int mID)
	{
		isHired.put(mID, false);
	}

	public void updateSoldier(int mID, int mScore)
	{
		hire(mID, id2team[mID], mScore);
	}

	public void updateTeam(int mTeam, int mChangeScore)
	{
		for(int sc = 1; sc <= 5; sc++){


			if(mChangeScore < 0){
				int rawScore = sc + mChangeScore;
				int newScore = rawScore < 1 ? 1 : (Math.min(rawScore, 5));
				if(sc == newScore || teams[mTeam].head[sc].next == null) continue;

				teams[mTeam].tail[newScore].next = teams[mTeam].head[sc].next;
				teams[mTeam].tail[newScore] = teams[mTeam].tail[sc];
				teams[mTeam].head[sc].next = null;
				teams[mTeam].tail[sc] = teams[mTeam].head[sc];
			} else {
				int reverseOrder = 6 - sc;
				int rvsRawScore = reverseOrder + mChangeScore;
				int newScore = rvsRawScore < 1 ? 1 : (Math.min(rvsRawScore, 5));

				if(reverseOrder == newScore || teams[mTeam].head[reverseOrder].next == null) continue;

				teams[mTeam].tail[newScore].next = teams[mTeam].head[reverseOrder].next;
				teams[mTeam].tail[newScore] = teams[mTeam].tail[reverseOrder];
				teams[mTeam].head[reverseOrder].next = null;
				teams[mTeam].tail[reverseOrder] = teams[mTeam].head[reverseOrder];
			}
		}

	}
	
	public int bestSoldier(int mTeam)
	{
		int maxId = 0;
		for(int i = 5; i >= 1; i--){
			Node head = new Node(teams[mTeam].head[i].id, teams[mTeam].head[i].next, teams[mTeam].head[i].version);
			while (head.next != null){
				int id = head.next.id;
				int ver = head.next.version;
				if(!isHired.get(id) || ver != id2Node[id].version) {
					head = head.next;
					continue;
				}

				maxId = Math.max(maxId, id);
				head = head.next;
			}
			if(maxId != 0) break;
		}
		return maxId;
	}
}