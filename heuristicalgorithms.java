import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;






/* class LinkDetails has the informtion of links between any two nodes  */
class LinkDetails
{
	private int hours;
	private int rFactor;
	
	/*constructor*/
	public LinkDetails(int hours,int rFactor)
	{
		this.hours=hours;
		this.rFactor=rFactor;
	}
	
	/* all methods*/
	
	public int getTime() 
	{ 
		return hours;
	}

	public int getrFactor() 
	{ 
		return rFactor;
	}
	
	 @Override
	 public String toString()
     {
       return "("+hours+";"+rFactor+")";
     }

}


class HeuristicDetails 
{
	private int hTime;
	private int hRisk;
	
	/*constructor*/
	public HeuristicDetails(int hTime,int hRisk)
	{
		this.hTime=hTime;
		this.hRisk=hRisk;
	}
	
	/* all methods*/
	
	public int getHTime() 
	{ 
		return hTime;
	}

	public int getHRisk() 
	{ 
		return hRisk;
	}
	
	 @Override
	 public String toString()
     {
       return "("+hTime+";"+hRisk+")";
     }

}


/* This class Search has all BFS,DFS,UCS,USbyriskconsideration methods in it*/
public class Main{
	
	/* class 'Node'has information of node */
	public static class Node
    {
		private String nodeName;
		private List<String> path;
		private int hTime;
		private int hRisk;
		private int pathCost;
		
		
		/* contructor*/
        public Node(String nodeName, List<String> path, int pathCost,int hTime,int hRisk)
        {
            this.nodeName = nodeName;
            this.path = path;
            this.hTime = hTime;
            this.hRisk = hRisk;
            this.pathCost = pathCost;
        }
        
        /* All methods */
        public String getName()
        {
            return nodeName;
        }
        
        public void setName(String nodeName)
        {
            this. nodeName = nodeName;
        }
     
       public List<String> getPath()
        {
        	return path;
        }
        
		
		public void setPath(List<String> path)
        {
			this.path = path;
        	
        }
		
		public void setHTime(int hTime)
		{
			this.hTime = hTime;
		}
		
		public int getHTime()
		{
			return hTime;
		}
		
		public void setHRisk(int hRisk)
		{
			this.hRisk = hRisk;
		}
		
		public int getHRisk()
		{
			return hRisk;
		}
		
		public void setPathCost(int pathCost)
		{
			this.pathCost = pathCost;
		}
		
		public int getPathCost()
		{
			return pathCost;
		}
		
		
		@Override
    	public boolean equals(Object o)
    	{
    		if(o==this) return true;
    		if(o==null || !(o instanceof Node)) return false;
    		Node myNode = Node.class.cast(o);
    		return nodeName.equals(myNode.nodeName) && (hTime == (myNode.hTime)) && (hRisk == (myNode.hRisk));
        }
		
		public String toString()
	    {
	        return "("+ nodeName +")" +"(" +  path + ")"+"("+Integer.toString(pathCost, 0) + ")"+ "(" + Integer.toString(hTime, 0) + ")" 
	        		+ "("+Integer.toString(hRisk, 0) + ")";
	    }
        
    }

	private Map<String, List<String>> mapRelation;
	private Map<Link, LinkDetails> mapLinkInfo;
	private Map<String, HeuristicDetails> mapHeuristicInfo;
	
	/***********************************************************************************/
	/* I used HashMap to store the Graph,there are two hash maps, 
	 * 'mapRelation' gives the information of which 2 nodes which are directly connected
	 * and 'mapLinkInfo' gives the information on each link ie. time in 
	 * hour and risk factor.
	/************************************************************************************/
	
	public void populateGraph(String file1,String file2)
	{
		BufferedReader in=null;
		
		mapRelation = new HashMap<String, List<String>>();
		mapLinkInfo= new HashMap<Link,LinkDetails>();
	    mapHeuristicInfo = new HashMap<String,HeuristicDetails>();
		try
		{	
			/* reading the given text-file "social-network-updated.txt"*/
			File file = new File(file1);
			FileReader reader = new FileReader(file);
			in = new BufferedReader(reader);
			String string;
			String word = null;
			String friend = null;
			int hours = 0;
			int rFactor = 0 ;
			String delims = "[ ]+";
		        
			while ((string = in.readLine()) != null) 
			{
				String[] tokens = string.split(delims);
				word = tokens[0];
				friend = tokens[1];
				hours = Integer.parseInt(tokens[2]);
				rFactor = Integer.parseInt(tokens[3]);
				
				/*storing values into maps*/
				List<String> l1 = mapRelation.get(word);
				if (l1 == null)
				{
					mapRelation.put(word, l1=new ArrayList<String>());
				}
	            l1.add(friend);
	            mapLinkInfo.put(new Link(word,friend),new LinkDetails(hours,rFactor));
	             	
	            List<String> l2 = mapRelation.get(friend);
				if (l2 == null)
				{
					mapRelation.put(friend, l2=new ArrayList<String>());
				}
		        l2.add(word);
		        mapLinkInfo.put(new Link(friend,word),new LinkDetails(hours,rFactor));
	             
			}
		        
			/*for (String key : mapRelation.keySet()) 
			{
				System.out.println("------------------------------------------------");
				System.out.println("key: " + key + " value: " +mapRelation.get(key));
	      	   
			}
		    
			/*System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			
			for (Link key : mapLinkInfo.keySet()) 
			{
				System.out.println("------------------------------------------------");
				System.out.println("between keys :" + key + "Time and risk factor : " + mapLinkInfo.get(key));
		     
			}*/
		       
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if(in!=null)
				{
					in.close();
				}
			} 
		    catch (IOException e) 
		    {
		    	e.printStackTrace();
		    }
		        
		}
		
		
		try
		{	
			/* reading the given text-file "direct-time-risk.txt" */
			File directFile = new File(file2);
			FileReader reader = new FileReader(directFile);
			in = new BufferedReader(reader);
			String string;
			String name = null;
			int dHours = 0;
			int dRFactor = 0 ;
			String delims = "[ ]+";
		        
			while ((string = in.readLine()) != null) 
			{	
				String[] tokens = string.split(delims);
				name = tokens[0];
				dHours = Integer.parseInt(tokens[1]);
				dRFactor = Integer.parseInt(tokens[2]);
				
				/*storing values into maps*/
	            mapHeuristicInfo.put(new String(name),new HeuristicDetails(dHours,dRFactor));
	             
			}
		    
		/*	System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			
			for (String key : mapHeuristicInfo.keySet()) 
			{
				System.out.println("------------------------------------------------");
				System.out.println("between keys :" + key + "DTime and Drisk factor : " + mapHeuristicInfo.get(key));
		     
			}*/
		       
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally
		{
			try 
			{
				if(in!=null)
				{
					in.close();
				}
			} 
		    catch (IOException e) 
		    {
		    	e.printStackTrace();
		    }
		        
		}
		
	}
	
	/****************************************************************************/
	/*         Greedy search Using direct time as the heuristic                 */
	/****************************************************************************/
		
	public void greedyDirectTime(String s, String d)
	{
		try 
		{
			PrintStream out = new PrintStream(new FileOutputStream(
						"Greedy.time.result.txt"));
			int initCapacity = 20;
			/* Use of Priority Queue */
			PriorityQueue<Node> pQueue = new PriorityQueue<Node>(initCapacity, new Comparator<Node>()
			{
				public int compare(Node node1, Node node2)
				{
					return (new Integer(node1.getHTime()).compareTo(new Integer(node2.getHTime())));
				}			
			});

			Set<String> visited = new HashSet<String>();
			List<String> initialPath = new ArrayList<String>();
			HeuristicDetails initHeuristicDetails = mapHeuristicInfo.get(s);
			pQueue.add(new Node(s,initialPath,0,initHeuristicDetails.getHTime(),initHeuristicDetails.getHRisk()));
			visited.add(s);

			boolean pathFound = false;
			while (!pQueue.isEmpty()) 
			{
				Node currentNode = pQueue.remove();
				List<String> currentPath = currentNode.getPath();
				int currentPathCost = currentNode.getPathCost();

				if(currentNode.getName().equals(d))
				{
					currentPath.add(d);
					out.println(" Greedy search Using direct time as the heuristic :");
					out.println(convertPathToString(currentPath));
					out.println("");
					out.println("final path-time is = " + currentPathCost);

					pathFound = true;
					break;

				}
				else
				{
					List<String> newPath = new ArrayList<String>(currentPath);
					newPath.add(currentNode.getName());
					List<String> adj_u = mapRelation.get(currentNode.getName());

					if (adj_u != null) 
					{	
						for (String v : adj_u)
						{
							Link link = new Link(currentNode.getName(),v);

							LinkDetails linkDetails = mapLinkInfo.get(link);
							int newPathCost = linkDetails.getTime() + currentPathCost ;
							
							Node newNode = null;
							if(!v.equals(d))
							{
								HeuristicDetails newHDetails = 	mapHeuristicInfo.get(v);
								//System.out.println("newHDetails " + newHDetails + " v = "+ v);
								newNode = new Node(v,newPath,newPathCost,newHDetails.getHTime(),newHDetails.getHRisk());
							}
							else
							{
								newNode = new Node(v,newPath,newPathCost,0,0);
							}
							
							
							if(visited.contains(v)) 
							{
								Iterator<Node> itr = pQueue.iterator();
								while(itr.hasNext()) 
								{
									
									Node element = (Node) itr.next();
									if(element.getName().equals(newNode.getName()))
									{
										if(element.getPathCost() > newNode.getPathCost())
										{
											//out.println("Removing element = " + element);
											//System.out.println("Removing element = " + element);
											pQueue.remove(element);
											//out.println("Queue before adding = " + pQueue);
											pQueue.add(newNode);
											//out.println("Queue is = " + pQueue);
											break;
										}
									}

								}

							}
							else
							{
								pQueue.add(newNode);
								visited.add(v);
								//out.println("Queue is = " + pQueue);
							}

						}
					}
				}
			}
			if(pathFound == false)
			{
				out.println("");
				out.println("No path found between "+ s + " and " + d);
			}
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/****************************************************************************/
	/*         Greedy search Using direct risk as the heuristic                 */
	/****************************************************************************/
	public void greedyDirectRisk(String s, String d)
	{
		try 
		{
			PrintStream out = new PrintStream(new FileOutputStream(
					"Greedy.risk.result.txt"));
			int initCapacity = 30;
			PriorityQueue<Node> pQueue = new PriorityQueue<Node>(initCapacity, new Comparator<Node>() 
			{
				public int compare(Node node1, Node node2)
				{
					return (new Integer(node1.getHRisk()).compareTo(new Integer(node2.getHRisk())));
				}
				
			});

			Set<String> visited = new HashSet<String>();
			List<String> initialPath = new ArrayList<String>();
			HeuristicDetails initHeuristicDetails = mapHeuristicInfo.get(s);
			pQueue.add(new Node(s,initialPath,0,initHeuristicDetails.getHTime(),initHeuristicDetails.getHRisk()));
			visited.add(s);

			boolean pathFound = false;
			while (!pQueue.isEmpty()) 
			{
				Node currentNode = pQueue.remove();
				List<String> currentPath = currentNode.getPath();
				int currentRiskFactor = currentNode.getPathCost();

				if(currentNode.getName().equals(d))
				{
					currentPath.add(d);
					out.println("Reult of Greedy search Using direct risk as the heuristic :");
					out.println(convertPathToString(currentPath));
					out.println("");
					out.println("final path-risk is = " + currentRiskFactor);

					pathFound = true;
					break;

				}
				else
				{
					List<String> newPath = new ArrayList<String>(currentPath);
					newPath.add(currentNode.getName());
					List<String> adj_u = mapRelation.get(currentNode.getName());

					if (adj_u != null) 
					{	
						for (String v : adj_u)
						{
							Link link = new Link(currentNode.getName(),v);

							LinkDetails linkDetails = mapLinkInfo.get(link);
							int newRiskPath = linkDetails.getrFactor() + currentRiskFactor ;
							
							Node newNode = null;
							if(!v.equals(d))
							{
								HeuristicDetails newHDetails = 	mapHeuristicInfo.get(v);
								//System.out.println("newHDetails " + newHDetails + " v = "+ v);
								newNode = new Node(v,newPath,newRiskPath,newHDetails.getHTime(),newHDetails.getHRisk());
							}
							else
							{
								newNode = new Node(v,newPath,newRiskPath,0,0);
							}
							if(visited.contains(v)) 
							{
								Iterator<Node> itr = pQueue.iterator();
								while(itr.hasNext()) 
								{
									Node element = (Node) itr.next();
									if(element.getName().equals(newNode.getName()))
									{
										if(element.getPathCost() > newNode.getPathCost())
										{
											//out.println("Removing element = " + element);
											pQueue.remove(element);
											//out.println("Queue before adding = " + pQueue);
											pQueue.add(newNode);
											//out.println("Queue is = " + pQueue);
											break;
										}
									}

								}

							}
							else 
							{	
								//out.println("newNode is = " + newNode);
								pQueue.add(newNode);
								//out.println("Queue is = " + pQueue);
								//visited.add(v);
								//out.println("Queue is = " + pQueue);
							}

						}
					}
				}
			}
			
			if(pathFound == false)
			{
				out.println("");
				out.println("No path found between "+ s + " and " + d);
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	/****************************************************************************/
	/*         A* search Using direct time as the heuristic                 */
	/****************************************************************************/
	public void timeAStar(String s, String d)
	{
		try 
		{
			PrintStream out = new PrintStream(new FileOutputStream(
					"A-star.time.result.txt"));
			int initCapacity = 20;
			/* Use of Priority Queue */
			PriorityQueue<Node> pQueue = new PriorityQueue<Node>(initCapacity, new Comparator<Node>()
			{
				public int compare(Node node1, Node node2)
				{
					return (new Integer(node1.getPathCost()+node1.getHTime()).compareTo(new Integer(node2.getPathCost()+node2.getHTime())));
				}			
			});

			Set<String> visited = new HashSet<String>();
			List<String> initialPath = new ArrayList<String>();
			HeuristicDetails initHeuristicDetails = mapHeuristicInfo.get(s);
			pQueue.add(new Node(s,initialPath,0,initHeuristicDetails.getHTime(),initHeuristicDetails.getHRisk()));
			visited.add(s);

			boolean pathFound = false;
			while (!pQueue.isEmpty()) 
			{
				Node currentNode = pQueue.remove();
				List<String> currentPath = currentNode.getPath();
				int currentPathCost = currentNode.getPathCost();

				if(currentNode.getName().equals(d))
				{
					currentPath.add(d);
					out.println("Result of  A* search Using direct time as the heuristic :");
					out.println(convertPathToString(currentPath));
					out.println("");
					out.println("final path-time is = " + currentPathCost);

					pathFound = true;
					break;

				}
				else
				{
					List<String> newPath = new ArrayList<String>(currentPath);
					newPath.add(currentNode.getName());
					List<String> adj_u = mapRelation.get(currentNode.getName());

					if (adj_u != null) 
					{	
						for (String v : adj_u)
						{
							Link link = new Link(currentNode.getName(),v);
							LinkDetails linkDetails = mapLinkInfo.get(link);
							int newPathCost = linkDetails.getTime() + currentPathCost ;
							Node newNode = null;
							
							if(!v.equals(d))
							{
								HeuristicDetails newHDetails = 	mapHeuristicInfo.get(v);
								//System.out.println("newHDetails " + newHDetails + " v = "+ v);
								newNode = new Node(v,newPath,newPathCost,newHDetails.getHTime(),newHDetails.getHRisk());
							}
							else
							{
								newNode = new Node(v,newPath,newPathCost,0,0);
							}
							
							if(visited.contains(v)) 
							{
								Iterator<Node> itr = pQueue.iterator();
								while(itr.hasNext()) 
								{
									
									Node element = (Node) itr.next();
									if(element.getName().equals(newNode.getName()))
									{
										if((element.getPathCost()+element.getHTime())> (newNode.getPathCost()+newNode.getHTime()))
										{
											//out.println("Removing element = " + element);
											//System.out.println("Removing element = " + element);
											pQueue.remove(element);
											//out.println("Queue before adding = " + pQueue);
											pQueue.add(newNode);
											//out.println("Queue is = " + pQueue);
											break;
										}
									}

								}

							}
							else
							{
								pQueue.add(newNode);
								visited.add(v);
								//out.println("Queue is = " + pQueue);
							}

						}
					}
				}
			}
			if(pathFound == false)
			{
				out.println("");
				out.println("No path found between "+ s + " and " + d);
			}
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/****************************************************************************/
	/*         A* search Using direct Risk as the heuristic                     */
	/****************************************************************************/
	public void riskAStar(String s, String d)
	{
		try 
		{
			PrintStream out = new PrintStream(new FileOutputStream(
					"A-star.risk.result.txt"));
			int initCapacity = 20;
			PriorityQueue<Node> pQueue = new PriorityQueue<Node>(initCapacity, new Comparator<Node>() 
			{
				public int compare(Node node1, Node node2)
				{
					return (new Integer(node1.getPathCost()+node1.getHRisk()).compareTo(new Integer(node2.getPathCost()+node2.getHRisk())));
				}			
			});

			Set<String> visited = new HashSet<String>();
			List<String> initialPath = new ArrayList<String>();
			HeuristicDetails initHeuristicDetails = mapHeuristicInfo.get(s);
			pQueue.add(new Node(s,initialPath,0,initHeuristicDetails.getHTime(),initHeuristicDetails.getHRisk()));
			
			visited.add(s);

			boolean pathFound = false;
			while (!pQueue.isEmpty()) 
			{
				Node currentNode = pQueue.remove();
				List<String> currentPath = currentNode.getPath();
				int currentRiskFactor = currentNode.getPathCost();

				if(currentNode.getName().equals(d))
				{
					currentPath.add(d);
					out.println("Result of A* search Using direct Risk as the heuristic :");
					out.println(convertPathToString(currentPath));
					out.println("");
					out.println("final path-risk is = " + currentRiskFactor);

					pathFound = true;
					break;

				}
				else
				{
					List<String> newPath = new ArrayList<String>(currentPath);
					newPath.add(currentNode.getName());
					List<String> adj_u = mapRelation.get(currentNode.getName());

					if (adj_u != null) 
					{	
						for (String v : adj_u)
						{
							Link link = new Link(currentNode.getName(),v);
							LinkDetails linkDetails = mapLinkInfo.get(link);
							int newRiskPath = linkDetails.getrFactor() + currentRiskFactor ;
							
							Node newNode = null;
							if(!v.equals(d))
							{
								HeuristicDetails newHDetails = 	mapHeuristicInfo.get(v);
								//System.out.println("newHDetails " + newHDetails + " v = "+ v);
								newNode = new Node(v,newPath,newRiskPath,newHDetails.getHTime(),newHDetails.getHRisk());
							}
							else
							{
								newNode = new Node(v,newPath,newRiskPath,0,0);
							}
							
							
							if(visited.contains(v)) 
							{
								Iterator<Node> itr = pQueue.iterator();
								while(itr.hasNext()) 
								{
									Node element = (Node) itr.next();
									if(element.getName().equals(newNode.getName()))
									{
										if((element.getPathCost()+element.getHRisk()) > (newNode.getPathCost()+newNode.getHRisk()))
										{
											//out.println("Removing element = " + element);
											pQueue.remove(element);
											//out.println("Queue before adding = " + pQueue);
											pQueue.add(newNode);
											//out.println("Queue is = " + pQueue);
											break;
										}
									}

								}

							}
							else
							{
								pQueue.add(newNode);
								visited.add(v);
								//out.println("Queue is = " + pQueue);
							}

						}
					}
				}
			}
			
			if(pathFound == false)
			{
				out.println("");
				out.println("No path found between "+ s + " and " + d);
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	private String convertPathToString(List<String> currentPath)
	{
		String outputString = "";
		
		for(int i=0; i< currentPath.size() ; ++i)
		{
			if(i == (currentPath.size()-1))
			{
				outputString = outputString.concat(currentPath.get(i));
				
			}
			else
			{
				outputString = outputString.concat(currentPath.get(i)+"-");
		
			}
		}
		return outputString;
	}
		
	
	
	/*    'main' method  */
	public static void main(String[] args) 
	{
		
		if(args.length < 4)
		{
			System.out.print("Usage: <social-network-updated.txt> <direct-time-risk.txt> <start node> <end node>");
			return;
		}
		
		/* Object creation */
		Main myMain = new Main();
		/* Call methods */
		/*taking user input*/
		myMain.populateGraph(args[0],args[1]);
		
		myMain.greedyDirectTime(args[2], args[3]);
		
		myMain.greedyDirectRisk(args[2], args[3]);
		
		myMain.timeAStar(args[2], args[3]);
		
		myMain.riskAStar(args[2], args[3]);
		
	}
}

