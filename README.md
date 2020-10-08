# CrossServerCommunicationAPI
The API For all Cross Server communication work in the QuestCraft-Network

How to use: 

Development : Take a clone of the branch dev then and your all set

Production : Take a clone of the Trunk(Master)
```xml
  <dependancy>
    <groupId>net.questcraft</groupId>
    <artifactId>CrossServerCommunicationAPI</artifactId>
    <version>VERSION</version>
  </dependancy>
```
Latest Version : 1.0-SNAPSHOT


WebSockets: 

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
ChannelHandler handler = CSCAPI.getAPI().getChannelHandler(DefaultChannelType.SERVER_WS); 
//A client can only connect to a server, so you must make sure you
//have each type on either end(DefaultChannelType.CLIENT_WS and DefaultChannelType.SERVER_WS)
SocketPipeline.Builder builder = new SocketPipeline.Builder("/your-path", YourExampleClass.class);
builder.autoReconnect(true); //If you want the websocket to reconnect onClose

SocketPipeline pipeline = handler.registerPipeline(builder);
//Do something with the pipeline
```

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
Alright, Now we can send messages, Recieve messages and setup our Client and server, But what if you want to queue messages the second you get your pipeline? Well you can easily do this with the Queue messages method.
```Java
ChannelPipeline#queueMessage(Packet);
//or you can queue multiple messages with 
ChannelPipeline#queueMessages(Packet[]);
```
The Queue Messages Method will put all queued messages into a MessageBuffer that will be run through and sent onConnect, If you implement on Connect, make sure you have super.onConnect() Writtin as the first call in your overridden method.

Lastly, The AutoReconnect feature, If you want to reconnect your pipeline when its broken, enable AUTO_RECONNECT in your  ChannelPipeline Builder! And to change the delay of the reconnect which is by default 10MS Change the delay with the Setters in your WebSocketChannelHandler(Which can be cast from the ChannelHandler you recieve on regsitration)

TCP

Coming soon...

