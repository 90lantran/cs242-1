'''
Created on Feb 28, 2014

@author: Will
'''
import unittest;
from view.Stats import Stats

class StatsTest(unittest.TestCase):
    global stats
    
    def setUp(self):
        self.stats = Stats()
        self.stats.initialize()
        
    def testSmallestCity(self):
        smallest = self.stats.smallest_city()
        self.assertEqual(smallest,'Smallest City: Essen has population 589900.')
    
    def testBiggestCity(self):
        biggest = self.stats.biggest_city()
        self.assertEqual(biggest,'Biggest City: Tokyo has population 34000000.')
        
    def testLongestFlight(self):
        longest = self.stats.longest_flight()
        self.assertEqual(longest,'Longest Flight: Sydney to Los Angeles has distance 12051.')
    
    def testShortestFlight(self):
        shortest = self.stats.shortest_flight()
        self.assertEqual(shortest,'Shortest Flight: Washington to New York has distance 334.')
    
    def testAllCityList(self):
        citylist = self.stats.all_cities()
        correctlist = [u'Santiago', u'Lima',         u'Mexico City', 
                       u'Bogota',   u'Buenos Aires', u'Sao Paulo', 
                       u'Lagos',    u'Kinshasa',     u'Johannesburg', 
                       u'Khartoum', u'Cairo',        u'Algiers', 
                       u'Madrid',   u'London',       u'Paris', 
                       u'Milan',    u'Essen',        u'St. Petersburg', 
                       u'Moscow',   u'Istanbul',     u'Bagdad', 
                       u'Tehrah',   u'Riyadh',       u'Karachi', 
                       u'Delhi',    u'Mumbai',       u'Chennai', 
                       u'Calcutta', u'Bangkok',      u'Hong Kong', 
                       u'Shanghai', u'Beijing',      u'Seoul', 
                       u'Tokyo',    u'Osaka',        u'Taipei',        u'Manila', 
                       u'Ho Chi Minh City',          u'Jakarta',       u'Sydney', 
                       u'Los Angeles',               u'San Francisco', u'Chicago', 
                       u'Atlanta',                   u'Miami',         u'Washington', 
                       u'New York', u'Toronto']
        self.assertEquals(citylist,correctlist)
    
    def testAvePopulation(self):
        average_pop = self.stats.average_population()
        self.assertEquals(average_pop,11796143)
    
    def testAveDistance(self):
        average_dist = self.stats.average_distance()
        self.assertEquals(average_dist,2300)
    
    def testCityInfo(self):
        info = self.stats.city_info("Lima")
        correct_info = {
            "code" : "LIM" ,
            "name" : "Lima" ,
            "country" : "PE" ,
            "continent" : "South America" ,
            "timezone" : -5 ,
            "coordinates" : {"S" : 12, "W" : 77} ,
            "population" : 9050000 ,
            "region" : 1
        }
        self.assertEquals(info,correct_info) 
    
    def testContinentGrouping(self):
        continents = self.stats.continent_grouping()
        correct_continents = {u'Europe':    [u'Madrid', u'London', u'Paris', 
                                             u'Milan', u'Essen', u'St. Petersburg', 
                                             u'Moscow', u'Istanbul'], 
                             
                              u'Australia': [u'Sydney'], 
                              
                              u'Africa':    [u'Lagos', u'Kinshasa', u'Johannesburg', 
                                             u'Khartoum', u'Cairo', u'Algiers'], 
                              
                              u'Asia':      [u'Bagdad', u'Tehrah', u'Riyadh', u'Karachi', 
                                             u'Delhi', u'Mumbai', u'Chennai', u'Calcutta', 
                                             u'Bangkok', u'Hong Kong', u'Shanghai', 
                                             u'Beijing', u'Seoul', u'Tokyo', u'Osaka', 
                                             u'Taipei', u'Manila', u'Ho Chi Minh City', 
                                             u'Jakarta'],
                               
                              u'North America': [u'Mexico City', u'Los Angeles', u'San Francisco', 
                                                 u'Chicago', u'Atlanta', u'Miami', u'Washington', 
                                                 u'New York', u'Toronto'], 
                              
                              u'South America': [u'Santiago', u'Lima', u'Bogota', u'Buenos Aires', 
                                                 u'Sao Paulo']}
        self.assertEquals(continents,correct_continents)
        
    def testHubCities(self):
        hub_cities = self.stats.hub_cities()
        correct_hubcities = [u'Mexico City', u'Istanbul', u'Paris', u'Shanghai', 
                             u'Manila', u'Chennai', u'Bagdad', u'Delhi', u'Bangkok', 
                             u'Hong Kong', u'Tehrah', u'Karachi', u'Chicago', u'Bogota', 
                             u'Cairo', u'Madrid']
        self.assertEquals(hub_cities,correct_hubcities)
        
    def testValidPathInfo(self):
        pathlist = ["Bogota", "Lima", "Mexico City", "Miami"]
        infostrings = self.stats.path_info(pathlist)
        self.assertEquals(infostrings[0],"The total distance of the route is: 8163.")
        self.assertEquals(infostrings[1],"The cost to fly the route is: 2440.2.")
        #self.assertEquals(infostrings[2],"The time it will take to travel is: ")
    
    def testInvalidPathInfo(self):
        pathlist = ["Bogota", "Chicago"]
        infostrings = self.stats.path_info(pathlist)
        self.assertEquals(infostrings,[])
        
if __name__ == '__main__':
    unittest.main()