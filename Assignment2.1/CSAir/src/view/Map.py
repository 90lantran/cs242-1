'''
Created on Feb 28, 2014
A small program to create an image
representing the possible routes for our airline: CSAir.
@author: Will
'''

class Map():
    '''  
    A function to create a URL leading to 
    the Great Circle Mapper website which
    will display the image for us.
    E.g. "http://www.gcmap.com/mapui?P=LIM-MEX,+LIM-BOG,+MEX-LAX"
    @input graph the given graph representation 
                 for our input
    '''  
    def create_URL(self,graph):
        url = "http://www.gcmap.com/mapui?P="
        
        routes = ""
        for edge in graph.edges:
            start = edge.get_source().get_data()
            end   = edge.get_target().get_data()
            routes += start['code'] + "-" + end['code'] + ",+"
        
        url += routes[:-2]
        return url 
            
    