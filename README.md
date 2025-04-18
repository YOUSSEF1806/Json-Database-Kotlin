A client-server application that allows the clients to store their data on the server in JSON format.

JSON is a ubiquitous format for exchanging data between web servers and browsers. Its simple design and flexibility make it easy to understand and use in the programming language of your choice.


<h2>Stage3:</h2>
To send a request to the server, the client should get all the information through command-line arguments. These arguments include the type of the request (set, get, or delete), the index of the cell, and, in the case of the set request, a text.

The arguments will be passed to the client in the following format:

<code>-t set -i 148 -m Here is some text to store on the server</code>

-t is the type of request, and -i is the index of the cell. -m is the value to save in the database: you only need it in case of a set request.