'''
Created on Mar 2, 2014

@author: Will
'''
import sys;

class Node():
    global _data
    value = sys.maxint
    
    '''
    Sets the data for
    a given node.
    @param data the data to add
    '''
    def set_data(self,data):
        self._data = data
    
    '''
    Gets the data for
    a given node
    @return the data of the node
    '''
    def get_data(self):
        return self._data
    