# Round-the-world-Trip

## Problem Statement:

Create a `Tour Plan` to visit all continents of the world. But only one city per continent and return back to the Origin. This has to be done in with MINIMUM distance, and in any order. 

The trip should start and end at the origin city which will be the input of the  program.

Example:

BOM (Bombay, Asia) → PAR (Paris, Europe) → CAI (Cairo, Africa)→ NYC (New York, North America) → BOG (Bogota, South America) →SYD (Sydney, Oceania) → BOM (Back to Bombay)

Distance travelled: 34981 KMS

You are provided with a [JSON containing cities](https://raw.githubusercontent.com/UjjwalPandey/Round-the-world-Trip/master/src/main/resources/cities.json).

##### CONSTRANTS:
Your program should output the best solution it can find `within 60 seconds`.

It shouldn't use more than `250MB of memory`. (basically, it should run with the -Xmx 250m command)

The program will be `tested on a regular work laptop`.

**Input**

Any origin city ID

eg: BOM

(Bombay)

**Output**

List of cities in the order to be visited and the distance traveled.



## My Approach

Algorithm used **DFS Backtracking with Pruning**.

Still, the difference in result for Minimum Distance for Round Trip across the globe may `vary upto 1%`. Explanation given below.

### Preprocessing Steps:
- Objects of `City` class is mapped to their respective `Continents` in a `Hashmap`.
- Each city on the other hand is connected with other cities of which do not belong to it's own continent, stored as an `ArrayList` of `NeighbouringCity` object. It does store the distance between these two cities.
- For each city, `sorted` the linked neighbouring cities within their own continent, based on distance calculated.

Thus, now we have a Hashmap where 'continent name' as the Key and Value being the ArrayList of cities within that continent. Also, for each city, their distances from cities of neighboring continents are calculated and stored in sorted order within that continent.


### DFS:
####Purpose:
Used because we need to look for every possible combination of cities, starting and ending at given input city, to get the Minimum Distance.


### Pruning:
#### Purpose
As the DFS will be of Order `O(a*b*c*d*e*f)` where a,b,c,d,e,f are the number of cities in each continent. Thus, to optimise it we need to shelve certain permutations: 

- We have the **_neighboring cities sorted in ascending order of distance_**. Thus, if there comes a case where the distance summation till that point exceeds the minimum distance calculated so far. Then, we can simply leave to test other neighbours of that city with grater distance.

Even then also the time taken will be far more than the given constraint of 1 minute.

Thus, here I am taking an **_assumption_** (inspired, but not entirely conforming) on **_Greedy approach_**:
- From every chosen city, instead of checking for all the permutations of its neighbours , we can just pick the `TOP` **_x% neighbours_** (in PERCENTAGE TERMS) or **_y neighbours_** (in ABSOLUTE TERMS). 
- This, will reduce the number of permutations drastically and hence will reduce the time complexity.


On the other hand this may lead to few **_'misses'_**, i.e optimal solution of minimum distance may go through the pruned neighbours. But there is not much to worry because:
- Difference in actual Minimum Distance and the one calculated through pruning, vary roughly around 1%. As, cities mentioned in 'Optimal answer' will be geographically close to the cities coming in 'Actual answer'.
- The order of continents to be visited would roughly remain the same.
- Drastic reduction in processing time.

Further, we can `optimise for the values of x & y`. eg. having x = 90 or y = 0.2 will give next to accurate result in less than 5 minutes. 

Please check the various [**test cases**](https://github.com/UjjwalPandey/Round-the-world-Trip/tree/master/src/results) that I tried.   

### Code Execution:
1. _Data Preparation phase:_ Where all HashMap, Objects and related Data structures are formed, **_primarily for the purpose of memoization_**
2. Displaying number of cities in each continent.
3. Taking input for `(Minimum of two will be selected)`:
    - Numbers of Absolute neighbour you want to test. Enter any value > 1200 if you want to check for all neighbours to get the accurate result.
    - Percentage selection of neighbour you want to test. **_Input should be in range of (0 to 1)_**.  Enter 1, if you want to check for all neighbours to get the accurate result.
    
4. Enter the number of Test cases you want to check.
5. For each test case, enter the IATA code.
6. You will be get the output containing:
- `Path` to traverse for minimum distance starting and ending from given input. (Cities, along with their Continent.)
- Minimum `Distance` calculated in Kilometers.
- Total `processing time` in seconds.