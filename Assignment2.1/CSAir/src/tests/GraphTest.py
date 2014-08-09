'''
Created on Mar 3, 2014

@author: Will
'''
import unittest;
from model.Graph import Graph
from model.Node import Node
from model.Edge import Edge

class AirGraphTest(unittest.TestCase):
    global graph
    def setUp(self):
        self.graph = Graph()
        self.graph.nodes = []
        self.graph.nodes = []
    
    def testAddNode(self):
        data = "BLARGH"
        self.graph.add_node(data)
        node = self.graph.nodes[0]
        self.assertEquals(node.get_data(),"BLARGH")
    
    def testAddEdge(self):
        source = Node()
        source.set_data("BLECH")
        target = Node()
        target.set_data("ARGHH")
        self.graph.add_edge(source,target,1000)
        edge = self.graph.edges[0]
        self.assertEquals(edge.get_source().get_data(),"BLECH")
        self.assertEquals(edge.get_target().get_data(),"ARGHH")
    
    def testFindEdge(self):
        source = Node()
        source.set_data("BLACK")
        target = Node()
        target.set_data("WHITE")
        self.graph.add_edge(source,target,1)
        
        edge = self.graph.find_edge(source,target)
        sourcedata = edge.get_source().get_data()
        targetdata = edge.get_target().get_data()
        
        self.assertEquals(sourcedata,"BLACK")
        self.assertEquals(targetdata,"WHITE")
        
    '''
    This test is performed on a representation of the 
    graph in "CSAir/examples/dijikstra_example.png", where
    the nodes are labeled with red numbers. The result should 
    be 0->2->3->2->4, which is of length 2+1+1+4=8.
    '''
    def testDijikstra(self):
        nodes = self.graph.nodes
        edges = self.graph.edges
        for i in range (0,7):
            node = Node()
            node.set_data(str(i))
            self.graph.nodes.append(node)
        for i in range (1,7):
            edge = Edge()
            self.graph.edges.append(edge)
            
        edges[0].set_info(nodes[0],nodes[3],2)
        edges[1].set_info(nodes[0],nodes[2],6)
        edges[2].set_info(nodes[1],nodes[2],3)
        edges[3].set_info(nodes[1],nodes[3],1)
        edges[4].set_info(nodes[2],nodes[3],1)
        edges[5].set_info(nodes[2],nodes[4],4)
        edges[6].set_info(nodes[3],nodes[4],6)
        
        edgelist = self.graph.dijikstra(nodes[0],nodes[4])
        correct_edgelist = [edges[0],edges[3],edges[4],edges[5]]
        #self.assertEquals(edgelist,correct_edgelist)
        
        
    def testValidPath(self):
        for i in range(0,10):
            node = Node()
            node.set_data(str(i))
            self.graph.nodes.append(node)

        for i in range(0,8):
            edge = Edge()
            edge.set_info(self.graph.nodes[i],self.graph.nodes[i+1],i)
            self.graph.edges.append(edge)
            
        valid_path = self.graph.is_valid_path(self.graph.nodes[:-1])
        self.assertTrue(valid_path)
        
    def testInvalidPath(self):
        self.graph.nodes = []
        self.graph.edges = []
        for i in range(1,10):
            node = Node()
            self.graph.nodes.append(node)
        for i in range(1,8):
            edge = Edge()
            edge.set_info(self.graph.nodes[i],self.graph.nodes[i+1],i)
            self.graph.edges.append(edge)
            
        self.graph.edges.remove(self.graph.edges[5])
        valid_path = self.graph.is_valid_path(self.graph.nodes)
        self.assertFalse(valid_path)
        
if __name__ == '__main__':
    unittest.main()
