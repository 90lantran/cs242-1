'''
Created on Mar 2, 2014

@author: Will
'''
import unittest;
from model.Edge import Edge
from model.Node import Node

class EdgeTest(unittest.TestCase):
    global edge
    def setUp(self):
        self.edge = Edge()
        self.edge.set_info("Source","Target",1337)
          
    def testSourceAssignment(self):
        source = self.edge.get_source()
        self.assertEquals(source,"Source")
    
    def testTargetAssignment(self):
        target = self.edge.get_target()
        self.assertEquals(target,"Target")
    
    def testWeightAssignment(self):
        weight = self.edge.get_weight()
        self.assertEquals(weight,1337)
        
if __name__ == '__main__':
    unittest.main()