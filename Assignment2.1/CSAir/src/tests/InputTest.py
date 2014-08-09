'''
Created on Mar 6, 2014

@author: Will
'''
import unittest;
from control.Input import Input;

class InputTest(unittest.TestCase):
    global input
    
    def setUp(self):
        self.input = Input()
        self.input.stats.initialize()
        
    def testAddCity(self):
        def raw_input_mock_city(prompt):
            return {
                    "Code? ": "LOL",
                    "Country? " : "United States",
                    "Continent? ": "North America",
                    "Time zone? ": "-4",
                    "Degrees? ": "5",
                    "N or S? ": "N",
                    "E or W? ": "W",
                    "Population? ": "10000",
                    "Region? ": "5"
                    }[prompt]
        
        self.input.add_city("Moon",raw_input_mock_city)
        graph = self.input.stats.graph
        data = graph.find_city('name',"Moon").get_data()
        
        orig_data = {
                     'name': "Moon",
                     'code': "LOL",
                     'country': "United States",
                     'continent': "North America",
                     'timezone': -4,
                     'coordinates': {"N" : 5, "W" : 5},
                     'population': 10000,
                     'region': 5
                     }
        self.assertEquals(orig_data,data)
    
    def testAddRoute(self): 
        def raw_input_mock_route(prompt):
            return {
                    "First city name? " : "Chicago",
                    "Second city name? " : "Lima",
                    "Distance? " : "1000000"
                    }[prompt]
        
        self.input.add_route(raw_input_mock_route)
        graph = self.input.stats.graph
        edge = graph.find_route("Chicago","Lima")
        source = edge.get_source().get_data()
        target = edge.get_target().get_data()
        weight = edge.get_weight()
        
        self.assertEquals(source['name'],"Chicago")
        self.assertEquals(target['name'],"Lima")
        self.assertEquals(weight,1000000)
    
    def testRemoveRoute(self):
        def raw_input_mock_remove_route(prompt):
            return {
                    "First city name? " : "Santiago",
                    "Second city name? " : "Lima"
                    }[prompt]
        
        graph = self.input.stats.graph
        edge = graph.find_route("Santiago","Lima")
        self.assertNotEquals(edge.get_source(),None)
        self.input.remove_route(raw_input_mock_remove_route)
        edge = graph.find_route("Santiago","Lima")
        self.assertEquals(edge,None)
       
        
    def testRemoveCity(self):
        graph = self.input.stats.graph
        city = graph.find_city('name',"Lima")
        self.assertNotEquals(city,None)
        self.input.edit_commands("-rc Lima")
        city = graph.find_city('name',"Lima")
        self.assertEquals(city,None)
        
    
    def testEditCityName(self):
        def raw_input_mock_edit_name(prompt):
            return {
                    "What parameter should we edit? (Type 'exit' to stop.)\n" : 'name',
                    "" : "Happy Land"
                    }[prompt]
        
        graph = self.input.stats.graph
        self.input.edit_city("New York",raw_input_mock_edit_name)
        node = graph.find_city('name',"New York")
        self.assertEquals(node,None)
        node = graph.find_city('name',"Happy Land")
        city = node.get_data()['name']

        self.assertEquals(city,"Happy Land")
    
    def testEditCityCoords(self):
        def raw_input_mock_edit_coords(prompt):
            return {
                    "What parameter should we edit? (Type 'exit' to stop.)\n": 'coordinates',
                    "N or S? ": "S",
                    "Degrees? ": "10",
                    "E or W? ": "W"
                    }[prompt]
        
        graph = self.input.stats.graph
        self.input.edit_city("Toronto",raw_input_mock_edit_coords)
        node = graph.find_city('name',"Toronto")
        coords = node.get_data()['coordinates']
        self.assertEquals(coords,{'S':10,'W':10})
        
    def testPathInformation(self):
        def raw_input_mock_path(prompt):
            return {
                    "Enter a city name: ": ["Bogota", "Lima" "Mexico" "Miami"],
                    "Continue adding? (Type '\g' to finish.)": "\g"
                    }[prompt]
        
        #graph = self.input.stats.graph
        #infostrings = self.input.path_info("check path",raw_input_mock_path)
        #self.assertEquals(infostrings[0],"The total distance of the route is: ")
        #self.assertEquals(infostrings[1],"The cost to fly the route is: ")
        #self.assertEquals(infostrings[2],"The time it will take to travel is: ")
        

    def testShortestPath(self):
        def raw_input_mock_dijikstra(prompt):
            return {
                    "First city? ": "Bogota",
                    "Second city?": "Miami"
                    }
        #graph = self.input.stats.graph
        #bogota = graph.find_city('name',"Bogota")
        #miami  = graph.find_city('name',"Miami")
        #edgelist = graph.dijikstra(bogota,miami)
        #start = edgelist[0].get_source()
        #finish = edgelist[len(edgelist)-1].get_target()
        #infostrings = self.input.stats.path_info(start,finish)
            
        #self.assertEquals(infostrings[0],"The total distance of the route is: ")
        #self.assertEquals(infostrings[1],"The cost to fly the route is: ")
        #self.assertEquals(infostrings[2],"The time it will take to travel is: ")
        
        
       
            
            

            
        
        
        
        