package com.company;

import java.util.function.Consumer;

public class Main {
    public static void main (String[] args) {
        Consumer<Resource> doWork = resource -> {
            System.out.println("Operated on resource..");
        };

        Resource.use(doWork);
    }

}

class Resource {
    public Resource() {
        System.out.println("Resource crested ..");
    }
    private void cleanUp() {
        System.out.println("Resource cleaned up");
    }
    public static void use(Consumer<Resource> block) {
        Resource r = new Resource();
        try {
            block.accept(r);
        }
        finally {
            r.cleanUp();
        }
    }
//    @Override
//    public void finalize() {
//        cleanUp();
//    }
}

