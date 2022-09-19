#include <bits/stdc++.h>
using namespace std;

vector<string> split(string s, char del)
{
    vector<string> items;
    stringstream ss(s);
    string word;
    while (!ss.eof())
    {
        getline(ss, word, del);
        items.push_back(word);
    }
    return items;
}

template <typename K, typename V>
void printMap(unordered_map<K, V> const &m)
{
    for (auto const &pair : m)
    {
        cout << "{" << pair.first << endl;
    }
}

class nodeInformation
{

public:
    string hostName;
    int port;

    nodeInformation()
    {
    }

    nodeInformation(string hostName, int port)
    {
        hostName = hostName;
        port = port;
    }
};

int main()
{
    cout << "Initializing Variables....\n";
    ifstream config;
    string firstLine, nodeLine;
    int nodes, maxPerActive, minPerActive;
    unordered_map<int, nodeInformation> nodeMap;
    config.open("config.txt");

    // start get global parameters
    if (config.is_open())
    {
        getline(config, firstLine);
    }
    // end get global parameters

    // start assign global parameters
    nodes = stoi(split(firstLine, ' ')[0]);
    minPerActive = stoi(split(firstLine, ' ')[1]);
    maxPerActive = stoi(split(firstLine, ' ')[2]);
    // end assign global parameters

    if (config.is_open())
    {
        for (int i = 0; i < nodes; i++)
        {
            getline(config, nodeLine);
            vector<string> a = split(nodeLine, ' ');
        }
    }

    return 0;
}
