'''
Created on Mar 2, 2014

@author: Will
'''
import json
from Graph import Graph

class AirGraph(Graph):
    
    global FILE
    FILE = '../../input/map_data.json'
    
    sources  = [
        "http://www.gcmap.com/" ,
        "http://www.theodora.com/country_digraphs.html" ,
        "http://www.citypopulation.de/world/Agglomerations.html" ,
        "http://www.mongabay.com/cities_urban_01.htm" ,
        "http://en.wikipedia.org/wiki/Urban_agglomeration" ,
        "http://www.worldtimezone.com/standard.html"
    ]
    
    '''
    Opens the global filename that 
    contains the graph data in JSON 
    format.
    @param filename the name of the JSON file to load
    @return the formatted JSON data
    '''
    def open_file(self,filename=None):
        if (filename == None):
            json_data = open(FILE)
        else:
            json_data = open(filename)
        ds = json.load(json_data)
        return ds
    
    '''
    Saves the current graph as a JSON file 
    with the given filename.
    @param filename the name to attribute to the new JSON file
    '''
    def save_file(self,filename=None):
        if (filename == None):
            filename = "../../input/default_output.json"
        
        data = {}
        data['data sources'] = self.sources
        data['metros'] = []
        for node in self.nodes:
            data['metros'].append(node.get_data())
        
        data['routes'] = []
        for edge in self.edges:
            source_code = edge.get_source().get_data()['code']
            target_code = edge.get_target().get_data()['code']
            distance = edge.get_weight()
            entry = {}
            entry['ports'] = [source_code,target_code]
            entry['distance'] = distance
            data['routes'].append(entry)
        
        
        outfile = open(filename,'w')
        json.dump(data,outfile,indent=4)
        
    '''
    Parses through a .json file to create a 
    representation of the graph in memory.
    Uses a dictionary to index by strings.
    @input filename the name of the JSON format file 
                    to create the graph model from
    '''  
    def parse_file(self,filename=None):
        data = self.open_file(filename)

        for city in data['metros']:
            duplicate = self.find_city('code',city['code'])
            
            if (duplicate == None):
                self.add_node(city)
        
        for route in data['routes']:
            source = self.find_city('code',route['ports'][0])
            target = self.find_city('code',route['ports'][1])
            weight = route['distance']
            
            source_name = source.get_data()['name']
            target_name = target.get_data()['name']
            duplicate = self.find_route(source_name,target_name)
            
            if (duplicate == None):
                self.add_edge(source,target,weight)
            
    '''
    A helper function for generating the graph.
    Finds the node in the graph's list of nodes
    with the given field equal to the value given.
    @param field the data field to check
    @param value the value to check against
    @return the matching node ('None' if no node exists)
    '''
    def find_city(self,field,value):
        for node in self.nodes:
            if (node.get_data()[field] == value):
                return node
        return None
        
    '''
    A helper function for removing edges.
    Finds an edge in the graph with the matching
    source and destination city names.
    @param city1 the first city in the route
    @param city2 the second city in the route
    @return the edge matching the two cities
            ('None' if no edge exists)
    '''
    def find_route(self,city1,city2):
        for edge in self.edges:
            if (self.edge_matches(edge,city1,city2)):
                return edge
        return None
    
    '''
    A helper function for find_route that checks
    if a given edge has matching source and
    destination nodes that correspond to the
    given city names.
    @param edge the edge to check
    @param city1 the name of the first city
    @param city2 the name of the second city
    @return true if the edge matches the two cities
            false otherwise
    '''
    def edge_matches(self,edge,city1,city2):
        source_data = edge.get_source().get_data()
        target_data = edge.get_target().get_data()
        if (source_data['name'] == city1 and 
            target_data['name'] == city2):
            return True
        if (source_data['name'] == city2 and
            target_data['name'] == city1):
            return True
        return False 
    
    '''
    A helper function that checks if a given city 
    name matches either the source or the destination
    of a given edge
    @param edge the edge to match with
    @param city the city for the query
    @return true if the city matches either the source or target
            false otherwise
    '''
    def edge_match(self,edge,city):
        source_name = edge.get_source().get_data()['name'] 
        target_name = edge.get_target().get_data()['name']
        if (source_name == city or target_name == city):
            return True
        return False
          
    '''
    Looks through the graph for an edge
    matching the given source and destination.
    If found, the edge is removed.
    Otherwise, nothing happens.
    @param source the name of the source city
    @param target the name of the target city
    '''
    def remove_route(self,source,dest):
        edge = self.find_route(source,dest)
        self.edges.remove(edge)
    
    '''
    Searches through the graph for a node
    matching the given city name.
    If found, the node is removed along with
    all the edges connecting to it.
    Otherwise, nothing happens.
    @param city the name of the city to remove
    '''
    def remove_city(self,city):
        node = self.find_city('name',city)
        self.nodes.remove(node)

        match_count = 0
        for e in self.edges:
            if (self.edge_match(e,city)): 
                match_count += 1
        
        while (match_count > 0):
            for edge in self.edges:
                if (self.edge_match(edge,city)):
                    self.edges.remove(edge)
                    match_count -= 1

                    