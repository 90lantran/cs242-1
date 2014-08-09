'''
Created on Feb 25, 2014
A small testing class.
@author: Will
'''
import unittest;
from model.Graph import Graph;

TESTFILE = Graph.FILE

class GraphTest(unittest.TestCase):
    def testGraphNode(self):
        graph = Graph()
        graph.parseFile(TESTFILE)
        self.assertEqual(graph.nodes['SCL']['code'],'SCL')
        self.assertEqual(graph.nodes['SCL']['continent'],'South America')
        
    def testGraphRelations(self):
        graph = Graph()
        graph.parseFile(TESTFILE)
        self.assertEqual(graph.nodes['LIM']['rels'].keys(),[u'SCL',u'MEX',u'BOG'])
    
    def testGraphDistances(self):
        graph = Graph()
        graph.parseFile(TESTFILE)
        self.assertEqual(graph.nodes['LIM']['rels']['SCL'],2453)
    
if __name__ == '__main__':
    unittest.main()