package com.company;

import java.io.Console;
import java.util.Scanner;

public class Main {
    //1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС). Используйте wait/notify/notifyAll.
    static volatile char c = 'A';
    static Object mon = new Object();

    static class inAction implements Runnable {
        private char currentLetter;
        private char nextLetter;

        public inAction(char currentLetter, char nextLetter) {
            this.currentLetter = currentLetter;
            this.nextLetter = nextLetter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                synchronized (mon) {
                    try {
                        while (c != currentLetter)
                            mon.wait();
                        System.out.print(currentLetter);
                        c = nextLetter;
                        mon.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public static void main(String[] args) {
            new Thread(new inAction('A', 'B')).start();
            new Thread(new inAction('B', 'C')).start();
            new Thread(new inAction('C', 'A')).start();
        }
    }
}
