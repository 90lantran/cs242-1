'''
Created on Feb 28, 2014

@author: Will
'''
from model.Graph import Graph
import sys

FILE = Graph.FILE
graph = Graph()

class Stats():
    
    # Creates the underlying graph for 
    # which we do statistics by parsing the file
    def initialize(self):
        graph = Graph()
        graph.parseFile(FILE);
        
    # Prints out a list of all the cities
    # that CSAir flies to. 
    def allCities(self):
        for city in graph.nodes:
            print city['name'] 
            
    # Gets the average of all the populations
    # of the given graph of cities
    #
    # @return the overall average city population
    def averagePopulation(self):
        nodes = graph.nodes
        acc = 0
        count = 0
        for city in nodes:
            acc += nodes[city]['population']
            count = count + 1
        
        average = acc/count
        return average

        
    # Prints out a list of continents served by 
    # CSAir and which cities are in them
    def continentGrouping(self):
        nodes = graph.nodes
        grouping = {}
        
        for city in nodes:
            grouping[nodes[city]['continent']] = []
            
        for city in nodes:
            grouping[nodes[city]['continent']].append(nodes[city]['name'])
        
        for continent in grouping:
            print str(continent)
            print grouping[continent]
            print "\n"
            
    # Finds the hub cities for the given graph
    # and displays them to the screen.
    # We take this to mean the top 5 airports
    # with the most direct connections
    def hubCities(self):
        nodes = graph.nodes
        hubs = []
        for code in nodes:
            if (len(hubs) < 5):
                hubs.append(code)
            else:
                self.updateHubs(code,hubs)
                
        print "Hub Cities:"
        for airport in hubs:
            print nodes[airport]['name']
          
    # A helper function for hubCities
    # that checks whether or not a given
    # airport can be added to the hubs 
    # array, and performs it if valid
    def updateHubs(self,node,hubs):
        nodes = graph.nodes
        for airport in hubs:
            airportRels = len(nodes[airport]['rels'])
            newRels = len(nodes[node]['rels'])
            if (newRels > airportRels):
                hubs.remove(airport)
                hubs.append(node)
                return

        
    # Prints out a list of specific information
    # about the city, such as the code, name,
    # country, etc. 
    #
    # @input the city to print information on
    def cityInfo(self,city):
        nodes = graph.nodes
        for code in nodes:
            if (nodes[code]['name'] == city):
                for field in nodes[code]:
                    print str(field) + ":  " + str(nodes[code][field])
                break
        
    
    # Gets the name of the biggest
    # city served by CSAir
    #
    # @return the biggest city name
    def biggestCity(self):
        nodes = graph.nodes
        biggest = ''
        maxVal = 0
        for city in nodes:
            curPop = nodes[city]['population']
            if (curPop > maxVal): 
                maxVal  = curPop
                biggest = nodes[city]['name']
        
        return biggest + " (" + str(maxVal) + ")"
        
    
    # Gets the name of the smallest city
    # served vt CSAir
    #
    # @return the smallest city name
    def smallestCity(self):
        nodes = graph.nodes
        smallest = ''
        minVal = sys.maxint
        for city in nodes:
            curPop = nodes[city]['population']
            if (curPop < minVal): 
                minVal  = curPop
                smallest = nodes[city]['name']
        
        return smallest + " (" + str(minVal) + ")"
    
    # Gets the longest flight flown by CSAir
    # including the start and destination
    #
    # @return the longest flight 
    def longestFlight(self):
        nodes = graph.nodes
        longest = 0
        start = ''
        end = ''
        for source in nodes:
            transition = nodes[source]['rels']
            for dest in transition:
                if (longest < transition[dest]):
                    longest = transition[dest]
                    start = nodes[source]['name']
                    end = nodes[dest]['name']
        
        return start + " to " + end + " (" + str(longest) + ")"
    
    # Gets the shortest flight flown by CSAir
    # including the source and destination
    #
    # @return the shortest flight
    def shortestFlight(self):
        nodes = graph.nodes
        shortest = sys.maxint
        start = ''
        end = ''
        for source in nodes:
            transition = nodes[source]['rels']
            for dest in transition:
                if (shortest > transition[dest]):
                    shortest = transition[dest]
                    start = nodes[source]['name']
                    end = nodes[dest]['name']
        
        return start + " to " + end + " (" + str(shortest) + ")"
                