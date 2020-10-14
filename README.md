# CrossServerCommunicationAPI
The API For all Cross Server communication work in the QuestCraft-Network

How to use: 

Development : Take a clone of the branch dev then and your all set

Production : Take a clone of the Trunk(Master)
```xml
   <repositories>
        <repository>
            <id>cscapi-repo</id>
            <url>https://github.com/QuestCraft-Network/CrossServerCommunicationAPI?raw=true</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>net.questcraft</groupId>
            <artifactId>CrossServerCommunicationAPI</artifactId>
            <version>VERSION</version>
        </dependency>
   </dependencies>
```
Latest Version : 1.0-SNAPSHOT

# Table of Contents
- [Getting Started](#getting-started)
  * [WebSockets](#websockets)
  * [Http](#http)
    - [Server](#server)
    - [Http Client](#http-client)
    - [Spark Caching](#spark-caching-important)
  * [TCP](#tcp)

# Getting Started

## WebSockets

First, you need to create a SocketPipeline implementor, This is where you will get WebSocket calls
```Java
public class YourExampleClass extends SocketPipeline {
    public YourExampleClass(Builder builder) {
        super(builder);
    }

    @Override
    public void onMessage(Packet packet) {
       //Do something with this Packet
    }

    //There are additional Methods you can override, for example onConnect, 
    //onClose and onError(Check SocketPipeline for more info)
}
```

```Java
ChannelHandler handler = CSCAPI.getAPI().getChannelHandler(AsyncChannelType.SERVER_WS); 
//A client can only connect to a server, so you must make sure you
//have each type on either end(AsyncChannelType.CLIENT_WS and AsyncChannelType.SERVER_WS)
SocketPipeline.Builder builder = new SocketPipeline.Builder("/your-path", YourExampleClass.class);
builder.autoReconnect(true); //If you want the websocket to reconnect onClose

SocketPipeline pipeline = handler.registerPipeline(builder);
//Do something with the pipeline
```
**Messaging**
Now you may want to send Messages, the only messaging type supported through websockets is Custom Packets, So you cannot send a String, you must create a Wrapper for The String and send that instead. All byte serialization uses Kryo, If you are using 
classes like a UUID you must create a custom serializer for this, You can register that serializer Either by calling; `ChannelPipieline#registerSerializer(Class<?> cls, PacketSerializer serializer)` Or by calling; 
`ChannelHandler#registerSerializer(Class<?> cls, PacketSerializer serializer)`

Now we have to create a Custom packet
```Java

@SocketClassID("TestClass") //Your packet must have a @SocketClassID("ClassID") Or the serializer will not be able to deserialize it
public class TestClass implements WSPacket { //It must implement the Type Of packet it is, For example this is a WebSocket Packet, so it must implement WSPacket
    private int intOne;
    private Integer integerTwo;
    transient String string; //transient fields wont be serialized

    public TestClass(int intOne, Integer integerTwo, String string) {
        this.intOne = intOne;
        this.integerTwo = integerTwo;
        this.string = string;
    }

    public KryoTestClass() {
    }

    public int getIntOne() {
        return intOne;
    }

    public void setIntOne(int intOne) {
        this.intOne = intOne;
    }

    public Integer getIntegerTwo() {
        return integerTwo;
    }

    public void setIntegerTwo(Integer integerTwo) {
        this.integerTwo = integerTwo;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }


    @Override
    public String toString() {
        return "TestClass{" +
                "intOne=" + intOne +
                ", integerTwo=" + integerTwo +
                ", string='" + string + '\'' +
                '}';
    }
}
```

**Queueing**
Alright, Now we can send messages, Recieve messages and setup our Client and server, But what if you want to queue messages the second you get your pipeline? Well you can easily do this with the Queue messages method.
```Java
ChannelPipeline#queueMessage(Packet);
//or you can queue multiple messages with 
ChannelPipeline#queueMessages(Packet[]);
```
The Queue Messages Method will put all queued messages into a MessageBuffer that will be run through and sent onConnect, If you implement on Connect, make sure you have super.onConnect() Writtin as the first call in your overridden method.

Lastly, The AutoReconnect feature, If you want to reconnect your pipeline when its broken, enable AUTO_RECONNECT in your  ChannelPipeline Builder! And to change the delay of the reconnect which is by default 10MS Change the delay with the Setters in your WebSocketChannelHandler(Which can be cast from the ChannelHandler you recieve on regsitration)

## Http

### Server

First, you have to create your pipeline, In Http there are 2 types of pipelines; A server pipeline, and a client pipeline, Right now we will be going over the server.
```Java
public class ExampleHttpServerPipeline extends HttpServerPipeline { //Extend the HttpServerPipeline
    public ExampleHttpServerPipeline(Builder builder) {
        super(builder);
    }

    @Override
    public void innit() {
        registerGet("/example", ((packet, response) -> {
           return null; //null for now as we have not created any packets yet
        }));
    }
}
```
The `HttpServerPipeline#innit()` Works alot like SparkJava's Get registeration system. Once your pipeline has been fully registered the `innit()` method will be called. Inside this method you will define `GET` methods with `HttpServerPipeline#registerGet(String, HttpRoute)` which you will have to provide a path starting with `/` and a HttpRoute which can either be implemented by a seperate class(not recommended) or defined as a Lambda expresion where you recieve a `HttpPacket` and a `Response`, This mimics SparkJava's `Route` Functional interface, except instead of getting a request and a response you recieve a fully initiated HttpPacket. 

Returning, You must return a HttpPacket at the end of your Lambda, this will be serialized and passed back to the client.

**Create your ChannelHandler**

To register our Pipeline we will now need a ChannelHandler, We can get this by utilizing our very simple Utility class `CSCAPI`

```Java
HttpChannelHandler channelHandler = CSCAPI.getAPI().getChannelHandler(SyncChannelType.SERVER_HTTP);

ExampleHttpServerPipeline syncChannelPipeline = 
   channelHandler.registerPipeline(new HttpPipeline.Builder<>(ExampleHttpServerPipeline.class)); //The pipeline we just created
```

 **Packets**
Last but defintely not least we have to create some packets that can be sent cross server!

```Java
@HttpClassID("HttpPacketTester") //You MUST provide a ClassID so that your packet is serializable for Json over Http
public class HttpPacketTester implements HttpPacket { //Implement your HttpPacket which is merely a marker interface
    private String string; //Example member variable
    private int integer; //Example member variable

    public HttpPacketTester(String string, int integer) { //Constructer for the user
        this.string = string;
        this.integer = integer;
    }

    public HttpPacketTester() { //Empty constructor for Jackson to JSONify it
    }

   //Basic Getters and Setters
    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getInteger() {
        return integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }
}
```
And thats it for creating our Packet!

### Http Client

The Http Client is very similiar to the Server, the only difference is there is a Different of Pipeline you must create, and You will need a slightly different `ChannleHandler`. 

**HttpClientPipeline**
```Java
public class TestHttpClientPipeline extends HttpClientPipeline {
    public TestHttpClientPipeline(Builder builder) {
        super(builder);
    }
}
```
And thats all you need for your pipeline! Since its a client you cannot recieve messages(We dont suppose POSTS), A basic constructor to override its parent class. 

To Register your pipeline, you would get your ChannelHandler the exact same way except when requesting your ChannelHandler use `SyncChannelType.CLIENT_HTTP`.

**Sending Messages:**

Once you have your Pipeline, you can call the method `HttpClientPipeline#sendMessage(String, HttpPacket)` With a String Path and a HttpPacket(Creating Packets for the server is the same as doing it for the client) to send. This method will return an `HttpPacket` which is the response from your server.

**Note:**
The CrossServerCommunication is by no means meant to replace things like SparkJava, in fact you should **not** use it for basic GET routes and really even JS Side WebSockets as it deals with custom Byte Serialization and Custom Json Serializaiton(We use Kryo and Jackson but modify the end product to suit our needs). This is **only** meant to serve as a Cross Server(Java unless if more support comes eventually) communication API as said in the Name.

### Spark Caching IMPORTANT

CSCAPI Provides a unique Caching system so that you dont have to worry about how you Register your WebSockets and Http Pipelines. What this means, With Caching you dont have to worry about what order you register your pipelines(You cannot register WebSockets after GET routes have been defined in Generic Spark) until you Deploy them(Then ofcourse we still use underlying spark so you will not be able to register websockets anymore). 

How this works, Using the Static Utility Class `SparkCacheHandler` You can handle all User Caching.
```Java
//Enable Caching
SparkCacheHandler.cache(true); //Doesnt make sense to set it to false as that will be what it is by default

//Without setting caching to true the CSCAPI will register all pipeline with Spark immediatly when they are written into the cache.

//Deploying
SparkCacheHandler.deploy();

//After doing this you cannot register WebSocket Routes, If you wish to do this then you should be using TCP not WS.
```

## TCP

Coming Soon



