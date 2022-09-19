#include <bits/stdc++.h>
#include "utility.cpp"
#include "node.cpp"

using namespace std;

int main()
{
    ifstream config("config.txt");

    string firstLine;
    string otherLines;
    int nodeCount, minPerActive, maxPerActive, minSendDelay;

    unordered_map<int, Node> nodeInformation;

    getline(config, firstLine);

    // assign global parameters
    nodeCount = stoi(split(firstLine, " ")[0]);
    minPerActive = stoi(split(firstLine, " ")[1]);
    maxPerActive = stoi(split(firstLine, " ")[2]);
    minSendDelay = stoi(split(firstLine, " ")[3]);
    // assign global parameters

    // print global parameters
    cout << "Node Count : " << nodeCount << endl
         << "Min Per Active : " << minPerActive << endl
         << "Max Per Active : " << maxPerActive << endl
         << "Min Send Delay : " << minSendDelay << endl;
    // assign global parameters

    // create node map information
    for (int i = 0; i < nodeCount + 1; i++)
    {
        getline(config, otherLines);

        if (otherLines.length() > 0)
        {
            nodeInformation[stoi(split(otherLines, " ")[0])] = Node(split(otherLines, " ")[1], stoi(split(otherLines, " ")[2]));
        }
    }

    for (int i = 0; i < nodeCount + 1; i++)
    {
        getline(config, otherLines);

        if (otherLines.length() > 0)
        {
            nodeInformation[i - 1].neighbors = split(otherLines, " ");
        }
    }
    // create node map information

    // print node map information
    cout << "Node Map:" << endl;
    for (auto pair : nodeInformation)
    {
        cout << "{\n " << pair.first << ":";
        pair.second.printNode();
        cout << "\n},\n";
    }
    // print node map information
}