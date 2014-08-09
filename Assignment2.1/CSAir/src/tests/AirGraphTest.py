'''
Created on Feb 25, 2014
A small testing class.
@author: Will
'''
import unittest;
from model.AirGraph import AirGraph;

class AirGraphTest(unittest.TestCase):
    global graph
    def setUp(self):
        self.graph = AirGraph()
        self.graph.nodes = []
        self.graph.edges = []
        self.graph.parse_file()
    
    def testJSONFileOpen(self):
        data = self.graph.open_file()
        self.assertEquals(data['metros'][0]['code'],'SCL')
        self.assertEquals(data['routes'][0]['distance'],2453)
    
    def testJSONDoubleOpen(self):
        orig_nodes = len(self.graph.nodes)
        orig_edges = len(self.graph.edges)
        self.graph.parse_file()
        self.assertEquals(orig_nodes,len(self.graph.nodes))
        self.assertEquals(orig_edges,len(self.graph.edges))
    
    def testJSONAddFile(self):
        FILE = "../../input/additional_data.json"
        self.graph.parse_file(FILE)
        node = self.graph.find_city('name',"Roflcopter")
        data = node.get_data()
        orig_data = {
            "code" : "BLARGH" ,
            "name" : "Roflcopter" ,
            "country" : "USA" ,
            "continent" : "North America" ,
            "timezone" : 0 ,
            "coordinates" : {"S" : 10, "W" : 20} ,
            "population" : 1234567 ,
            "region" : 1
        } 
        self.assertEquals(data,orig_data)
    
    def testJSONSaveFile(self):
        FILE = "../../test.json"
        orig_data = self.graph.open_file()
        self.graph.save_file(FILE)
        data = self.graph.open_file(FILE)
        for field in data:
            self.assertEquals(data[field],orig_data[field])
        
    def testNodesInitialized(self):
        nodedata = self.graph.nodes[0].get_data()
        self.assertEquals(nodedata['code'],'SCL')
    
    def testEdgesInitialized(self):
        edgesource = self.graph.edges[0].get_source()
        sourcedata = edgesource.get_data()
        self.assertEquals(sourcedata['code'],'SCL')
    
    def testNoNodeDuplicates(self):
        count = 0
        for node in self.graph.nodes:
            if (node.get_data()['name'] == "Santiago"):
                count += 1
        self.assertEquals(count,1)
        
    def testNoEdgeDuplicates(self):
        count = 0
        for e in self.graph.edges:
            if (e.get_source().get_data()['name'] == 'Santiago' and 
                e.get_target().get_data()['name'] == 'Lima'):
                count += 1
        self.assertEquals(count,1)
          
    def testFindCity(self):
        node = self.graph.find_city('code','LIM')
        self.assertEqual(node.get_data()['code'],'LIM',
                         "Found the node with the correct city.")
        
    def testFindRoute(self):
        edge = self.graph.find_route("Santiago","Lima")
        sourcedata = edge.get_source().get_data()
        targetdata = edge.get_target().get_data()
        self.assertEquals(sourcedata['code'],'SCL',"Route 'Santiago - Lima' found.")
        self.assertEquals(targetdata['code'],'LIM')
    
    def testRemoveRoute(self):
        self.graph.remove_route("Santiago","Lima")
        edge = self.graph.find_route("Santiago","Lima")
        self.assertEquals(edge,None,"Route removed.")
       
    def testCityRemoved(self):
        node = self.graph.find_city('code',"SCL")
        self.assertEquals(node.get_data()['name'],"Santiago")
        self.graph.remove_city("Santiago")
        node = self.graph.find_city('code',"SCL")
        self.assertEquals(node,None,"Removed a city.")

    def testRemoveCityEdges(self):
        self.graph.remove_city("Mexico City")      
        
        edge = []
        edge.append(self.graph.find_route("Mexico City","Los Angeles"))
        edge.append(self.graph.find_route("Lima","Mexico City"))
        edge.append(self.graph.find_route("Mexico City","Miami"))
        edge.append(self.graph.find_route("Chicago","Mexico City"))
        edge.append(self.graph.find_route("Bogota","Mexico City"))
        
        for route in edge:
            self.assertEquals(route,None,"Removing a cities connected routes.")

        
if __name__ == '__main__':
    unittest.main()