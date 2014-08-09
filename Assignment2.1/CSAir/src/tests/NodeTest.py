'''
Created on Mar 2, 2014

@author: Will
'''
import unittest;
from model.Node import Node

class NodeTest(unittest.TestCase):        
    def testAssignment(self):
        node = Node()
        node.set_data("BLARGH")
        self.assertEquals(node.get_data(),
                          "BLARGH","Assignment worked.")
    
    def testDoubleAssignment(self):
        node1 = Node()
        node2 = Node()
        node1.set_data("BLAHBLAH")
        node2.set_data("ARRRRGHH")
        self.assertNotEquals(node1.get_data(),node2.get_data(),
                             "Global variables don't cause issues.")
        
    
if __name__ == '__main__':
    unittest.main()