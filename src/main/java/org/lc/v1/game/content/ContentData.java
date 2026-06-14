package org.lc.v1.game.content;

import java.util.*;

public class ContentData {

    private static final Map<Integer, List<Content>> NivelContenido = new HashMap<>();

    static {
        List<Content> level1 = new ArrayList<>();
        level1.add(new Lesson("/images/content/intro/level1_intro.png"));
        level1.add(new Lesson("/images/content/lessons/level1_lesson1.png"));
        level1.add(new Lesson("/images/content/lessons/level1_lesson2.png"));
        level1.add(new Example("/images/content/examples/level1_example.png"));
        level1.add(new Question(
                "images/content/questions/level1/q1.png",
                Arrays.asList(
                        "images/content/questions/level1/q1_a.png",
                        "images/content/questions/level1/q1_b.png",
                        "images/content/questions/level1/q1_c.png",
                        "images/content/questions/level1/q1_d.png"
                ), 0));
        level1.add(new Question(
                "images/content/questions/level1/q2.png",
                Arrays.asList(
                        "images/content/questions/level1/q2_a.png",
                        "images/content/questions/level1/q2_b.png",
                        "images/content/questions/level1/q2_c.png",
                        "images/content/questions/level1/q2_d.png"
                ), 1));
        level1.add(new Question(
                "images/content/questions/level1/q3.png",
                Arrays.asList(
                        "images/content/questions/level1/q3_a.png",
                        "images/content/questions/level1/q3_b.png",
                        "images/content/questions/level1/q3_c.png",
                        "images/content/questions/level1/q3_d.png"
                ), 2));

        List<Content> level2 = new ArrayList<>();
        level2.add(new Lesson("/images/content/intro/level2_intro.png"));
        level2.add(new Lesson("/images/content/lessons/level2_lesson1.png"));
        level2.add(new Lesson("/images/content/lessons/level2_lesson2.png"));
        level2.add(new Example("/images/content/examples/level2_example.png"));
        level2.add(new Question(
                "images/content/questions/level2/q1.png",
                Arrays.asList(
                        "images/content/questions/level2/q1_a.png",
                        "images/content/questions/level2/q1_b.png",
                        "images/content/questions/level2/q1_c.png",
                        "images/content/questions/level2/q1_d.png"
                ), 3));
        level2.add(new Question(
                "images/content/questions/level2/q2.png",
                Arrays.asList(
                        "images/content/questions/level2/q2_a.png",
                        "images/content/questions/level2/q2_b.png",
                        "images/content/questions/level2/q2_c.png",
                        "images/content/questions/level2/q2_d.png"
                ), 2));
        level2.add(new Question(
                "images/content/questions/level2/q3.png",
                Arrays.asList(
                        "images/content/questions/level2/q3_a.png",
                        "images/content/questions/level2/q3_b.png",
                        "images/content/questions/level2/q3_c.png",
                        "images/content/questions/level2/q3_d.png"
                ), 3));

        List<Content> level3 = new ArrayList<>();
        level3.add(new Lesson("/images/content/intro/level3_intro.png"));
        level3.add(new Lesson("/images/content/lessons/level3_lesson1.png"));
        level3.add(new Lesson("/images/content/lessons/level3_lesson1.png"));
        level3.add(new Example("/images/content/examples/level3_example.png"));
        level3.add(new Question(
                "images/content/questions/level3/q1.png",
                Arrays.asList(
                        "images/content/questions/level3/q1_a.png",
                        "images/content/questions/level3/q1_b.png",
                        "images/content/questions/level3/q1_c.png",
                        "images/content/questions/level3/q1_d.png"
                ), 1));
        level3.add(new Question(
                "images/content/questions/level3/q2.png",
                Arrays.asList(
                        "images/content/questions/level3/q2_a.png",
                        "images/content/questions/level3/q2_b.png",
                        "images/content/questions/level3/q2_c.png",
                        "images/content/questions/level3/q2_d.png"
                ), 2));
        level3.add(new Question(
                "images/content/questions/level3/q3.png",
                Arrays.asList(
                        "images/content/questions/level3/q3_a.png",
                        "images/content/questions/level3/q3_b.png",
                        "images/content/questions/level3/q3_c.png",
                        "images/content/questions/level3/q3_d.png"
                ), 0));

        List<Content> level4 = new ArrayList<>();
        level4.add(new Lesson("/images/content/intro/level4_intro.png"));
        level4.add(new Lesson("/images/content/lessons/level4_lesson1.png"));
        level4.add(new Lesson("/images/content/lessons/level4_lesson2.png"));
        level4.add(new Example("/images/content/examples/level4_example.png"));
        level4.add(new Question(
                "images/content/questions/level4/q1.png",
                Arrays.asList(
                        "images/content/questions/level4/q1_a.png",
                        "images/content/questions/level4/q1_b.png",
                        "images/content/questions/level4/q1_c.png",
                        "images/content/questions/level4/q1_d.png"
                ), 1));
        level4.add(new Question(
                "images/content/questions/level4/q2.png",
                Arrays.asList(
                        "images/content/questions/level4/q2_a.png",
                        "images/content/questions/level4/q2_b.png",
                        "images/content/questions/level4/q2_c.png",
                        "images/content/questions/level4/q2_d.png"
                ), 0));
        level4.add(new Question(
                "images/content/questions/level4/q3.png",
                Arrays.asList(
                        "images/content/questions/level4/q3_a.png",
                        "images/content/questions/level4/q3_b.png",
                        "images/content/questions/level4/q3_c.png",
                        "images/content/questions/level4/q3_d.png"
                ), 2));

        List<Content> level5 = new ArrayList<>();
        level5.add(new Lesson("/images/content/intro/level5_intro.png"));
        level5.add(new Lesson("/images/content/lessons/level5_lesson1.png"));
        level5.add(new Lesson("/images/content/lessons/level5_lesson2.png"));
        level5.add(new Example("/images/content/examples/level5_example.png"));
        level5.add(new Question(
                "images/content/questions/level5/q1.png",
                Arrays.asList(
                        "images/content/questions/level5/q1_a.png",
                        "images/content/questions/level5/q1_b.png",
                        "images/content/questions/level5/q1_c.png",
                        "images/content/questions/level5/q1_d.png"
                ), 3));
        level5.add(new Question(
                "images/content/questions/level5/q2.png",
                Arrays.asList(
                        "images/content/questions/level5/q2_a.png",
                        "images/content/questions/level5/q2_b.png",
                        "images/content/questions/level5/q2_c.png",
                        "images/content/questions/level5/q2_d.png"
                ), 2));
        level5.add(new Question(
                "images/content/questions/level5/q3.png",
                Arrays.asList(
                        "images/content/questions/level5/q3_a.png",
                        "images/content/questions/level5/q3_b.png",
                        "images/content/questions/level5/q3_c.png",
                        "images/content/questions/level5/q3_d.png"
                ), 0));

        NivelContenido.put(1, level1);
        NivelContenido.put(2, level2);
        NivelContenido.put(3, level3);
        NivelContenido.put(4, level4);
        NivelContenido.put(5, level5);
    }

    public static List<Content> getQuestions(int levelNumber) {
        return NivelContenido.getOrDefault(levelNumber, new ArrayList<>());
    }
}