'''
Created on Feb 25, 2014
A Graph class that represents the
underlying datastructure for our new airline: CSAir.
@author: Will
'''
from Node import Node
from Edge import Edge

class Graph():
    nodes = []
    edges = []
    
    '''
    Takes the data given and places it 
    in a new node. It then adds that node
    to the graph.
    @param data the data to initialize the node with 
    '''
    def add_node(self,data):
        node = Node()
        node.set_data(data)        
        self.nodes.append(node)
        
    '''
    Takes the given source and target nodes
    along with a weight. Initializes a new
    edge with the information, and adds it
    to the graph.
    @param source the source node of the edge
    @param target the target node of the edge
    @param weight the weight of the edge
    '''
    def add_edge(self,source,target,weight):
        edge = Edge()
        edge.set_info(source, target, weight)
        self.edges.append(edge)
 
    '''
    Looks for an edge in the graph given 
    two endpoints. Returns the edge, if it exists.
    @param first the first node
    @param second the second node
    @return the edge, if it exists.
            otherwise None
    '''
    def find_edge(self,first,second):
        for edge in self.edges:
            source = edge.get_source()
            target = edge.get_target()
            if ((first == source and second == target) or
                (first == target and second == source)):
                return edge
        return None
    
    '''
    Performs Dijikstra's algorithm on the given
    graph to find the shortest path between 
    the two given nodes (assuming it exists).
    @param start the start node
    @param end the target node
    @return the list of edges to follow in order
            to traverse the shortest path
    '''
    def dijikstra(self,start,end):
        self
        
    '''
    Checks whether or not a given list of vertices
    form a valid path (in order - otherwise this would
    take a very long time). 
    @param nodelist the list of vertices that form a path
    @return true if the path is valid, false otherwiise
    '''
    def is_valid_path(self,nodelist):
        for i in range(0,len(nodelist)-1):
            if (self.find_edge(nodelist[i],nodelist[i+1]) == None):
                return False
        return True