#include <bits/stdc++.h>
using namespace std;

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
