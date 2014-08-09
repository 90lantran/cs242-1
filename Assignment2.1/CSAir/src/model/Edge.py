'''
Created on Mar 2, 2014

@author: Will
'''
class Edge():
    global _source
    global _target 
    weight = 1
    
    '''
    Sets the information for a given edge.
    @param source the source node of the edge
    @param target the target node of the edge
    @param weight the weight of the edge
    '''
    def set_info(self,source,target,weight):
        self._source = source
        self._target = target
        self.weight = weight
        
    '''
    Gets the weight of a given edge
    @return the weight of the edge
    '''
    def get_weight(self):
        return self.weight
    
    '''
    Gets the source node of a given edge
    @return the source node of the edge
    '''
    def get_source(self):
        return self._source
   
    '''
     Gets the target node of a given edge
     @return the target node of an edge
     '''
    def get_target(self):
        return self._target
    