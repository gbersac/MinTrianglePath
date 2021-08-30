1- I made the assumption that the input was always correct and there is always at least one line. It simplify the code and help me focus on the core of the requested task: parsing from stdin and creating the algo to find the minimal path.

2- A line which contains more int than expected will be considered valid. The int which are not usefull will be discarded.

3- A line which contains less int than expected will be considered valid. The int which are missing will be replaced by an end of node. The missing int will be considered to be the childs of the right-most nodes of the parent line.

4- I decided that it was ok to have `println` in the test function. It help having an idea of how efficient is our program. 