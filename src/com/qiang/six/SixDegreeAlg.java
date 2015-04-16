package com.qiang.six;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;



public class SixDegreeAlg {

	static int UserNum = 10;
	static int MAX_LEVEL = 6;
	int friends [][];
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SixDegreeAlg six = new SixDegreeAlg();
		six.init();
		//System.out.println(six.getUserFriendById(1));
		six.getPotentialFriendsByIdAndLevel(1, 12);
	}
	
	public void init(){
		friends = new int[UserNum][UserNum];
		// 初始化， 1 表示是好友，0 表示不是
		for(int i = 0; i < UserNum ; i++)
			for(int j = 0 ; j < UserNum ; j ++)
				friends [i][j] = 0;
		friends[0][1] = 1;
		friends[0][4] = 1;
		friends[1][2] = 1;
		friends[1][8] = 1;
		friends[1][5] = 1;
		friends[2][3] = 1;
		friends[3][7] = 1;
		friends[3][6] = 1;
		friends[4][5] = 1;
		friends[5][7] = 1;
		friends[8][9] = 1;
/*		for(int k = 0; k< UserNum ; k++)
			System.out.print(k+" ");
		System.out.println();*/
		for(int i = 0; i < UserNum ; i++){
			for(int j = 0 ; j < UserNum ; j ++){
				if(friends [i][j]==1) friends [j][i] = 1;
				System.out.print(friends[i][j]+" ");
			}
			System.out.println();
		}
			
	}
	// 获取好友列表
	/*
	 * 输入： 用户id
	 * 返回： 该用户的好友列表
	 */
	HashSet<Integer> getUserFriendById(int id){
		if(friends == null|| id< 0 || id >= UserNum) return null; 
		HashSet<Integer> friendList = new HashSet<Integer>();
		for(int i = 0;i < UserNum; i++){
			if(friends[id][i]==1) friendList.add(i);
		}
		return friendList;
	}
	// 推荐
	/*
	 *  注： 这里只是返回推荐列表，并没有对每个潜在“好友”的评分
	 *  输入： id 和 level  level表示几度推荐
	 *  返回： 相应level的推荐用户列表 
	 */
	public HashSet<Integer>  getPotentialFriendsByIdAndLevel(int id,int level){
		if(level > MAX_LEVEL || id < 0 || id >= UserNum) return null;
		ArrayList< HashSet<Integer>> potentialFriends = new ArrayList< HashSet<Integer>>();
		HashSet<Integer> tempPotentialFriends = null;
		HashSet<Integer> nextPotentialFriends = null;
		potentialFriends.add(getUserFriendById(id)); // id对应用户的一度好友列表
		System.out.println("level 1:"+getUserFriendById(id));
		for( int i = 1; i < level;i++){
			tempPotentialFriends = potentialFriends.get(i-1); // 获取上一度的推荐列表用于当前度的查找
			Iterator<Integer> it = tempPotentialFriends.iterator();
			nextPotentialFriends = new HashSet<Integer>();
			// 将上一度的好友的好友都加入到列表中
			while(it.hasNext()){
				nextPotentialFriends.addAll(getUserFriendById(it.next())); 
			}
			// 去除nextPotentialFriends 中和前面重合的好友
			for( int j = 0; j< i;j++){
				nextPotentialFriends.removeAll(potentialFriends.get(j));
			}
			nextPotentialFriends.remove(id);
			System.out.println("level:" + (i+1));
			System.out.println(nextPotentialFriends);
		}
		return potentialFriends.get(potentialFriends.size()-1);
	}
}
