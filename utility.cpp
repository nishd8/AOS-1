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