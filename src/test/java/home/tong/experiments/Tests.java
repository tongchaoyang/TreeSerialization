package home.tong.experiments;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tests {
    private static Tree t;
    private static Logger logger = LoggerFactory.getLogger(Tests.class);
    
    @BeforeAll
    static void setup() {
        /** tree initialization
         *         0
         *        1  4
         *    2 3   5
         *         6
         */
        Node root = new Node(0, 100);
        root.value = 0;
        root.left = new Node(1, 11);
        root.left.left = new Node(2, 22);
        root.left.right = new Node(3, 33);
        root.right = new Node(4, 44);
        root.right.right = new Node(5, 55);
        root.right.right.left = new Node(6, 66);
        t = new Tree(root);
    }
    
    @AfterAll
    static void tearDown() {
        t = null;
    }
    
    @Test
    void testTreeSerialization() {
        logger.info("Serialize an empty tree");
        Tree et = new Tree();
        String stree = et.serialize();
        logger.info("Empty tree: " + stree);
        Tree t1 = Tree.deserialize(stree);
        Assertions.assertEquals(et, t1, "Deserialized empty tree doesn't match");
        
        stree = t.serialize();
        logger.info("Serilized a 7 node tree: " + stree);
        Tree t2 = Tree.deserialize(stree);
        logger.info("Deserialized it back and validate");
        Assertions.assertEquals(t, t2, "Tree serialization failed");
        String stree2 = t2.serialize();
        Assertions.assertEquals(stree, stree2, "Deserialization failure: " + stree);

        // serialize a one-node tree
        Node root = new Node(0, 100);
        Tree t3 = new Tree(root);
        stree = t3.serialize();
        logger.info("Serilized a node tree: " + stree);
        Tree t4 = Tree.deserialize(stree);
        logger.info("Deserialized it back and validate");
        Assertions.assertEquals(t3, t4, "Tree serialization failed");
        
        /*
         * serialize a tree like this: 0
         *                               1
         *                           2
         */
        root.left = new Node(1, 200);
        root.left.left = new Node(2, 300);
        stree = t3.serialize();
        logger.info("Serilized 3 nodes left linear tree: " + stree);
        t4 = Tree.deserialize(stree);
        logger.info("Deserialized it back and validate");
        Assertions.assertEquals(t3, t4, "Tree serialization failed");

        /*
         * serialize a tree like this: 0
         *                                  1
         *                                 2
         */
        root.left = null;
        root.right = new Node(2, 200);
        root.right.right = new Node(4, 400);
        stree = t3.serialize();
        logger.info("Serilized 3 nodes right linear tree: " + stree);
        t4 = Tree.deserialize(stree);
        logger.info("Deserialized it back and validate");
        Assertions.assertEquals(t3, t4, "Tree serialization failed");

        // serialize t's right sub tree
        t3 = new Tree(t.root.right);
        stree = t3.serialize();
        logger.info("Serilized t's right subtree: " + stree);
        t4 = Tree.deserialize(stree);
        logger.info("Deserialized it back and validate");
        Assertions.assertEquals(t3, t4, "Tree serialization failed");
    }
    
    @Test
    void testTreeValidation() {
        final String s = "{this is not a tree}";
        Assertions.assertThrows(IllegalArgumentException.class, 
                ()->Tree.deserialize(s));
        String s2 = "{{id=1;value=2;left=-1;right=}}";
        Assertions.assertThrows(IllegalArgumentException.class, 
                ()->Tree.deserialize(s2));
        String s3 = "{{id=1;value=2;left=-1;right=-}}";
        Assertions.assertThrows(IllegalArgumentException.class, 
                ()->Tree.deserialize(s3));
        String s4 = "{{id=1;value=2a;left=-1;right=-}}";
        Assertions.assertThrows(IllegalArgumentException.class, 
                ()->Tree.deserialize(s4));
    }
}
