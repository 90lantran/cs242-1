'''
Created on Feb 28, 2014
A small program to create an image
representing the possible routes for our airline: CSAir.
@author: Will
'''
from model.Graph import Graph;
import webbrowser

FILE = Graph.FILE

class CircleMap():
    # A function to create a URL leading to 
    # the Great Circle Mapper website which
    # will display the image for us.
    #
    # E.g. "http://www.gcmap.com/mapui?P=LIM-MEX,+LIM-BOG,+MEX-LAX"
    #
    # @input graph the given graph representation 
    #              for our input
    def createURL(self,graph):
        url = "http://www.gcmap.com/mapui?P="
        
        routes = ""
        for start in graph.nodes:
            for end in graph.nodes[start]['rels'].keys():
                routes += start + "-" + end + ",+"
                
        
        url += routes[:-2]
        return url 
            

if __name__ == '__main__':
    map = CircleMap()
    graph = Graph()
    graph.parseFile(FILE)
    url = map.createURL(graph)
    webbrowser.open(url,new=2)
    