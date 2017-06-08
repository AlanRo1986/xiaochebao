package com.example;


import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MyClass {

    public static void main(String[] args) {
        String string = "[{\"page_total\":1,\"page_size\":\"10\",\"page\":1}]";

        System.out.println(string.matches("^\\{(.+:.+,*){1,}\\}"));

    }

}

