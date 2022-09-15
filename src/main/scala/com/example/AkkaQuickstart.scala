//#full-example
package com.example


import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.example.OrderProcessor.Order
import com.example.Shipper.Shipment



object Shipper{

  final case class Shipment(id:Int , product:String , quantity:Int)

  def apply(): Behavior[Shipment] = Behaviors.receive {
    (context , Message ) =>

      context.log.info(Message.toString())
      Behaviors.same
  }
}

object OrderProcessor{
  final case class Order(id:Int , product:String , number:Int)

  def apply(): Behavior[Order] = Behaviors.setup {
    (context)=>

      val ShipperRef = context.spawn(Shipper() , "shipper");

      Behaviors.receiveMessage{
        message=>
          // println(message.toString())
          context.log.info(message.toString())
          ShipperRef ! Shipment(id = message.id , product = message.product , quantity = message.number)
          
          Behaviors.same
      }

      

      

      
  }
}



//#main-class
object AkkaQuickstart extends App {
  //#actor-system
  val orderProcessor: ActorSystem[OrderProcessor.Order] = ActorSystem(OrderProcessor() , "orders")
  //#actor-system

  //#main-send-messages
  orderProcessor ! Order(id = 0 , product = "Jacket" , number = 2)
  orderProcessor ! Order(id = 1 , product = "Sneakers" , number = 1)
  orderProcessor ! Order(id = 2 , product = "Socks" , number = 5)
  orderProcessor ! Order(id = 3 , product = "Umbrella" , number = 3)
  orderProcessor ! Order(id = 4 , product = "Slippers" , number = 6)
  orderProcessor ! Order(id = 5 , product = "Shirt" , number = 8)
  orderProcessor ! Order(id = 6 , product = "Ball" , number = 6)
  orderProcessor ! Order(id = 7 , product = "Speaker", number = 4)
  
  //#main-send-messages
}
//#main-class
//#full-example
