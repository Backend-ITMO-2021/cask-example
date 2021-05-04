package ru.ifmo.backend_2021.connections

import cask.{WsActor, WsChannelActor}
import cask.endpoints.WsHandler
import cask.util.Logger
import cask.util.Ws.Event

trait ConnectionPool {
  def getConnections: List[WsChannelActor]
  def send(event: Event): WsChannelActor => Unit
  def sendAll(event: Event): Unit
  def addConnection(connections: WsChannelActor)(implicit ac: castor.Context, log: Logger): WsActor
  def wsHandler(onConnect: WsChannelActor => Unit)(implicit ac: castor.Context, log: Logger): WsHandler
}
