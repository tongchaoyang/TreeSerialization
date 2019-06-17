# TreeSerialization
This shows a simple implementation of tree serialization. 
Here are the assumptions: a tree node is required to have a non-negative integral numeric id,
unique within a tree instance. The serialization just does a pre-order traverse and serialize each node
recurively. 
During deserialization, it uses a stack to keep track of nodes to be derialized. The stack 
is initialized with a root node. scan the input string for next node's data, pop the node from stack and 
populate its data with data pulled out of the string. if the node's right child isn't null create a node
with right child's id and push it to stack. if the node's left child isn't null create a node with the id
read from the string and push stack.
