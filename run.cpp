#include <bits/stdc++.h>
using namespace std;

vector<string> split(string s, string delimiter)
{
    size_t pos = 0;
    vector<string> words;
    string token;
    while ((pos = s.find(delimiter)) != string::npos)
    {
        token = s.substr(0, pos);
        words.push_back(token);
        s.erase(0, pos + delimiter.length());
    }
    words.push_back(s);

    return words;
}

string vectorToString(vector<string> v)
{
    string s;
    for (auto i : v)
    {
        s = s + i + " ";
    }

    return s;
}

class Node
{
public:
    string hostName;
    int port;
    vector<string> neighbors;

    Node() {}

    Node(string h, int p)
    {
        hostName = h;
        port = p;
    }

    void printNode()
    {
        cout << "{\n    Hostname : " << hostName << ",\n    Port : " << port << ",\n    Neighbors : " << vectorToString(neighbors) << "\n  },";
    }
};

int main()
{
    ifstream config("config.txt");

    string firstLine;
    string otherLines;
    int nodeCount, minPerActive, maxPerActive;

    unordered_map<int, Node> nodeInformation;

    getline(config, firstLine);

    // assign global parameters
    nodeCount = stoi(split(firstLine, " ")[0]);
    minPerActive = stoi(split(firstLine, " ")[1]);
    maxPerActive = stoi(split(firstLine, " ")[2]);
    // assign global parameters

    // print global parameters
    cout << "Node Count : " << nodeCount << endl
         << "Min Per Active : " << minPerActive << endl
         << "Max Per Active : " << maxPerActive << endl;
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