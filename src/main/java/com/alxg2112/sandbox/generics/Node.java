package com.alxg2112.sandbox.generics;

/**
 * @author Alexander Gryshchenko
 */
public class Node<T> {

	public T data;

	public Node(T data) {
		this.data = data;
	}

	public void setData(T data) {
		System.out.println("Node.setData");
		this.data = data;
	}
}
