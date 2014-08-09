'''
Created on Feb 28, 2014

@author: Will
'''
from model.AirGraph import AirGraph
from view.Map import Map
import webbrowser

'''
A class used for calculating statistics
for a given graph represting our route
network for our airline: CSAir.
'''
class Stats():
    graph = AirGraph()
    
    '''
    Creates the underlying graph for 
    which we do statistics by parsing the file
    '''
    def initialize(self):
        self.graph = AirGraph()
        self.graph.parse_file();
    
    '''    
    Prints out a list of all the cities
    that CSAir flies to.
    '''   
    def all_cities(self):
        citylist = []
        for node in self.graph.nodes:
            city = node.get_data()['name']
            citylist.append(city)
        return citylist
    
    '''          
    Gets the average of all the populations
    of the given graph of cities
    @return the overall average city population
    '''  
    def average_population(self):
        _sum = 0
        count = 0
        for node in self.graph.nodes:
            population = node.get_data()['population']
            _sum   += population
            count += 1
        return _sum/count
    
    '''
    Gets the average of all the distances
    of the given routes of all cities
    @returns the overall average route distance
    '''
    def average_distance(self):
        _sum = 0
        count = 0
        for edge in self.graph.edges:
            distance = edge.get_weight()
            _sum   += distance
            count += 1
        return _sum/count
        
    '''      
    Returns a list of continents served by 
    CSAir and which cities are in them
    @return the continent groups dictionary
    '''  
    def continent_grouping(self):
        continents = {}
        for node in self.graph.nodes:
            data = node.get_data()
            continents[data['continent']] = []
        
        for node in self.graph.nodes:
            data = node.get_data()
            continents[data['continent']].append(data['name'])
            
        return continents
    
    '''          
    Finds the hub cities for the given graph
    and displays them to the screen.
    We take this to mean the top 5 airports
    with the most direct connections
    '''  
    def hub_cities(self):
        connection_counter = {}
        for node in self.graph.nodes:
            name = node.get_data()['name']
            connection_counter[name] = 0
            
        for edge in self.graph.edges:
            source = edge.get_source().get_data()['name']
            target = edge.get_target().get_data()['name']
            connection_counter[source] += 1
            connection_counter[target] += 1
        
        min_connections = 5
        hub_cities = []
        
        for entry in connection_counter:
            if connection_counter[entry] >= min_connections:
                hub_cities.append(entry)
                
        return hub_cities

    '''    
    Prints out a list of specific information
    about the city, such as the code, name,
    country, etc. 
    @input the city to print information on
    '''  
    def city_info(self,city):
        node = self.graph.find_city('name',city)
        if (node != None):
            return node.get_data()
        
    '''  
    Gets the name of the biggest
    city served by CSAir
    @return the biggest city name
    '''  
    def biggest_city(self):
        max_node = self.graph.nodes[0]
        max_pop = max_node.get_data()['population']
        for node in self.graph.nodes:
            pop = node.get_data()['population']
            if (max_pop < pop):
                max_pop = pop
                max_node = node
                
        cityname = max_node.get_data()['name']
        result_string = "Biggest City: " +  cityname
        result_string += " has population " + str(max_pop) + "."
        return result_string
        
    '''  
    Gets the name of the smallest city
    served vt CSAir
    @return the smallest city name
    '''  
    def smallest_city(self):
        min_node = self.graph.nodes[0]
        min_pop = min_node.get_data()['population']
        for node in self.graph.nodes:
            pop = node.get_data()['population']
            if (min_pop > pop):
                min_pop = pop
                min_node = node
                
        cityname = min_node.get_data()['name']
        result_string = "Smallest City: " +  cityname
        result_string += " has population " + str(min_pop) + "."
        return result_string
    
    '''  
    Gets the longest flight flown by CSAir
    including the start and destination
    @return the longest flight
    '''   
    def longest_flight(self):
        max_edge = self.graph.edges[0]
        max_weight = max_edge.get_weight()
        for edge in self.graph.edges:
            weight = edge.get_weight()
            if (max_weight < weight):
                max_weight = weight
                max_edge = edge
        
        sourcecity = max_edge.get_source().get_data()['name']
        targetcity = max_edge.get_target().get_data()['name']
        
        result_string =  "Longest Flight: " + sourcecity + " to " + targetcity
        result_string += " has distance "    + str(max_weight) + "."
        return result_string
    
    '''  
    Gets the shortest flight flown by CSAir
    including the source and destination
    @return the shortest flight
    '''  
    def shortest_flight(self):
        min_edge = self.graph.edges[0]
        min_weight = min_edge.get_weight()
        for edge in self.graph.edges:
            weight = edge.get_weight()
            if (min_weight > weight):
                min_weight = weight
                min_edge = edge
        
        sourcecity = min_edge.get_source().get_data()['name']
        targetcity = min_edge.get_target().get_data()['name']
        
        result_string =  "Shortest Flight: " + sourcecity + " to " + targetcity
        result_string += " has distance "    + str(min_weight) + "."
        return result_string
    
    '''  
    Displays the map representing the 
    current route network.
    '''  
    def display_map(self):
        circle_map = Map()
        url = circle_map.create_URL(self.graph)
        webbrowser.open(url,new=2)
        
    '''
    A function that returns statistics about
    a given path. If the path is invalid, no statistics
    are calculated.
    @param nodelist the list of nodes in the path
    @return a set of infostrings if the path is valid
            an empty array otherwise
    '''
    def path_info(self,citylist):
        nodelist = []
    
        for city in citylist:
            node = self.graph.find_city('name',city)
            nodelist.append(node)
            
        if (not(self.graph.is_valid_path(nodelist))):
            return []
        
        total_distance = 0
        total_cost = 0
        total_time = 0
        
        for i in range(0,len(nodelist)-1):
            edge = self.graph.find_edge(nodelist[i],nodelist[i+1])
            dist = edge.get_weight()
            total_distance += dist
            total_cost += (0.35 - (i*0.05))*dist
            
            outgoing = 0
            for edge in self.graph.edges:
                if (edge.get_target() == nodelist[i+1] or
                    edge.get_source() == nodelist[i+1]):
                    outgoing += 1
            
            total_time += dist/750;
            
        info_strings = []
        info_strings.append("The total distance of the route is: " + str(total_distance) + ".")
        info_strings.append("The cost to fly the route is: " + str(total_cost) + ".")
        info_strings.append("The time it will take to travel is: " + str(total_time) + " hours.")
        return info_strings
            