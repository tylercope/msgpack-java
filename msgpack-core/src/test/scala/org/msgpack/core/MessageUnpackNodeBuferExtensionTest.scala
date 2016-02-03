package org.msgpack.core

import java.nio.file.{Files, Paths, FileSystems}

class MessageUnpackNodeBufferExtensionTest extends MessagePackSpec {

  "MessageUnpacker" should {

    "unpack Node Buffer data packed using extension" taggedAs ("unpack") in {
      
      /* 
        Test data file generated with this node program:

        var msgpack = require('msgpack-js');
        var bops = require('bops')
        var fs = require('fs');

        // See http://probablyprogramming.com/2009/03/15/the-tiniest-gif-ever for the 26 byte GIF!
        var buffer = bops.from('R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs=', 'base64');
        var initial = {start: true, 'payload': buffer};
        var encoded = msgpack.encode(initial);

        var wstream = fs.createWriteStream('data.bin');
        wstream.write(encoded);

        console.log(encoded);
      */
      val resource = getClass.getResource("/data.bin")
      val path =  FileSystems.getDefault().getPath(resource.getPath())
      val byteArray = Files.readAllBytes(path)
      
      val unpacker = MessagePack.newDefaultUnpacker(byteArray);

      val value = unpacker.unpackValue()
      val valueType = value.getValueType()
      info(s"value: $value")
      info(s"value type: $valueType")
      val map = value.asMapValue.map()
    }
  }

}
