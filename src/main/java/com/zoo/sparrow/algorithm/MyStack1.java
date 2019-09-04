package com.zoo.sparrow.algorithm;

import java.util.Stack;

/**
 * @author liudewei
 * @date 2019/4/23
 */
public class MyStack1 {

    private Stack<Integer> stackData;
    private Stack<Integer> stackMin;

    public MyStack1() {
        this.stackData = new Stack<Integer>();
        this.stackMin = new Stack<Integer>();
    }

    public static void main(String[] args) {
        MyStack1 stack1 = new MyStack1();
        stack1.push(3);
        System.out.println(stack1.getmin());
        stack1.push(4);
        System.out.println(stack1.getmin());
        stack1.push(1);
        System.out.println(stack1.stackData);
        System.out.println(stack1.getmin()); // 获取栈中最小元素
        System.out.println(stack1.pop()); // 弹出栈顶的元素，并从栈中删除这个元素
        System.out.println(stack1.getmin());
        System.out.println(stack1.stackData);
    }

    public void push(int newNum) {
        // 1、最小站为空，就放进去这个元素
        if (this.stackMin.isEmpty()) {
            this.stackMin.push(newNum);
            // 最小站不为空，如果放进来的元素小于栈中最小元素，则放进去
        } else if (newNum < this.getmin()) {
            this.stackMin.push(newNum);
        }
        else { //newNum >= this.getmin())压入this.stackMin.peek()
            // 如果放进来的元素大于最小站中的栈顶元素，就取最小站中的元素
            int newMin = this.stackMin.peek();
            this.stackMin.push(newMin);
        }
        this.stackData.push(newNum);
    }

    // 弹出栈顶元素，并删除这个元素
    public int pop() {
        if (this.stackData.isEmpty()) {
            throw new RuntimeException("Your stack is empty.");
        }
        this.stackMin.pop();
        return this.stackData.pop();
    }

    public int getmin() {
        if (this.stackMin.isEmpty()) {
            throw new RuntimeException("Your stack is empty.");
        }
        return this.stackMin.peek(); // 支持查看下栈顶的元素，但并不会删除这个元素
    }
}
