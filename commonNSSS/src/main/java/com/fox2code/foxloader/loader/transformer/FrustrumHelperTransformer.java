package com.fox2code.foxloader.loader.transformer;

import org.objectweb.asm.tree.*;

public class FrustrumHelperTransformer implements PreClassTransformer {
    private static final String FRUSTRUM = "com/mojang/minecraft/render/Frustrum";
    private static final String FRUSTRUM_HELPER = "com/fox2code/foxloader/client/FrustrumHelper";
    private static final String FRUSTRUM_HOOKS = "com/fox2code/foxloader/client/FrustrumHelper$Hooks";

    @Override
    public void transform(ClassNode classNode, String className) {
        if (!className.startsWith("com.mojang.minecraft.")) return;
        boolean entityRenderer = className.equals("com.mojang.minecraft.render.EntityRenderer");
        for (MethodNode methodNode : classNode.methods) {
            InsnList insnList = methodNode.instructions;
            for (AbstractInsnNode abstractInsnNode : methodNode.instructions) {
                if (abstractInsnNode.getOpcode() == INVOKESPECIAL) {
                    MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
                    if (methodInsnNode.owner.equals(FRUSTRUM) &&
                            methodInsnNode.name.equals("<init>")) {
                        AbstractInsnNode dup = methodInsnNode.getPrevious();
                        AbstractInsnNode anew = dup.getPrevious();
                        if (dup.getOpcode() == DUP && anew.getOpcode() == NEW) {
                            insnList.insert(methodInsnNode, new FieldInsnNode(
                                    GETSTATIC, FRUSTRUM_HELPER, "frustrum", "L" + FRUSTRUM + ";"));
                            insnList.remove(methodInsnNode);
                            insnList.remove(dup);
                            insnList.remove(anew);
                            System.out.println("Optimized frustrum: " + className);
                        }
                    }
                } else if (entityRenderer && abstractInsnNode.getOpcode() == INVOKEVIRTUAL) {
                    MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
                    if (methodInsnNode.owner.equals(FRUSTRUM) &&
                            methodInsnNode.name.equals("func_343_a")) {
                        insnList.insertBefore(methodInsnNode, new MethodInsnNode(INVOKESTATIC,
                                FRUSTRUM_HOOKS, "update", "(L" + FRUSTRUM + ";DDD)V", false));
                        insnList.remove(methodInsnNode);
                    }
                }
            }
        }
    }
}
