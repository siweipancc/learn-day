package com.pancc.learn.jdks.vm;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import java.time.Instant;

/**
 * @author Siweipancc
 * <p>
 * VM: -Djol.magicFieldOffset=true
 */
public class EntityMm {
    public static void main(String[] args) {
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseClass(Entity.class).toPrintable());

        Entity entity = new Entity(1, "wll", Instant.now());
        System.out.println(STR."The shallow size of Entity is: \{VM.current().sizeOf(entity)}");
        System.out.println(GraphLayout.parseInstance(entity).toFootprint());
    }
}
