package dev.eureka.banderas.system.util;

import java.util.Arrays;

public class Switch {
    private final Object object;

    public Switch(Object object) {
        this.object = object;
    }

    /**
     * @param runnable runs the specified code if at least one case is correct
     * @return CaseBuilder instance
     */
    public CaseBuilder anyCase(Runnable runnable) {
        return anyCase(runnable, new Object[0]);
    }

    /**
     * @param runnable runs the specified code if at least one case is correct
     * @param except adds object to the ignored list for anyCase runnable
     * @return CaseBuilder instance
     */
    public CaseBuilder anyCase(Runnable runnable, Object... except) {
        return new CaseBuilder(object, runnable, except);
    }

    /**
     * @return CaseBuilder instance, anyCase will be ignored and set up to empty
     */
    public CaseBuilder getBuilder() {
        return new CaseBuilder(object, () -> {});
    }

    public static class CaseBuilder {
        private final Object object;
        private final Runnable anyCase;
        private final Object[] except;

        private boolean isFinished;

        public CaseBuilder(Object object, Runnable anyCase, Object... except) {
            this.object = object;
            this.anyCase = anyCase;
            this.except = except;
            this.isFinished = false;
        }

        /**
         * @param object2 the object to which the parent object will be compared
         * @param runnable executes this code if the specified and parent object can be compared
         * @return CaseBuilder instance
         */
        public CaseBuilder newCase(Object object2, Runnable runnable) {
            if (object == object2 && !isFinished) {
                run(object2, runnable);
            }

            return this;
        }

        /**
         * @param object2 uses for check if you can run anyCase runnable
         * @param runnable executes this code
         */
        private void run(Object object2, Runnable runnable) {
            boolean allowCase = except.length == 0 || Arrays.stream(except).noneMatch((e) -> e == object2);
            this.isFinished = true;

            runnable.run();
            if (allowCase) anyCase.run();
        }
    }
}
