'''
Created on Mar 7, 2014

@author: Will
'''
import unittest;
from view.Map import Map
from model.AirGraph import AirGraph

class MapTest(unittest.TestCase):
    global map
    
    def setUp(self):
        self.map = Map()
        
    def testCreateURL(self):
        graph = AirGraph()
        graph.parse_file()
        url = self.map.create_URL(graph)
        correct_url = open("../../input/correct_url.txt","r").read()
        self.assertEquals(url,correct_url)
        