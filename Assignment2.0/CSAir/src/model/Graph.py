'''
Created on Feb 25, 2014
A Graph class that represents the
underlying datastructure for our new airline: CSAir.
@author: Will
'''
import json

class Graph():
    nodes = {}
    
    FILE = '../../input/map_data.json'
    
    # Parses through a .json file to create a 
    # representation of the graph in memory.
    # Uses a dictionary to index by strings.
    #
    # @input filename the name of the JSON format file 
    #                 to create the graph model from
    def parseFile(self,filename):
        json_data = open(filename)
        data = json.load(json_data)
        
        for city in data['metros']:
            self.nodes[city['code']] = city
            self.nodes[city['code']]['rels'] = {}
            
        for route in data['routes']:
            start = route['ports'][0]
            end   = route['ports'][1]
            self.nodes[start]['rels'][end] = route['distance']
            self.nodes[end]['rels'][start] = route['distance']
            
        