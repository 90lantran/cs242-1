'''
Created on Feb 28, 2014

@author: Will
'''
import unittest;
from view.Stats import Stats

class StatsTest(unittest.TestCase):
    def testSmallestCity(self):
        stats = Stats()
        stats.initialize();
        smallest = stats.smallestCity()
        self.assertEqual(smallest,'Essen (589900)')
    
    def testBiggestCity(self):
        stats = Stats()
        stats.initialize();
        biggest = stats.biggestCity()
        self.assertEqual(biggest,'Tokyo (34000000)')
        
    def testLongestFlight(self):
        stats = Stats()
        stats.initialize();
        longest = stats.longestFlight()
        self.assertEqual(longest,'Sydney to Los Angeles (12051)')
    
    def testShortestFlight(self):
        stats = Stats()
        stats.initialize();
        shortest = stats.shortestFlight()
        self.assertEqual(shortest,'New York to Washington (334)')
    
if __name__ == '__main__':
    unittest.main()