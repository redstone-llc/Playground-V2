package org.everbuild.celestia.orion.platform.minestom.api.utils

import net.minestom.server.Viewable
import net.minestom.server.adventure.audience.PacketGroupingAudience
import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Pos
import net.minestom.server.coordinate.Vec
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block
import net.minestom.server.network.packet.server.play.BlockBreakAnimationPacket
import net.minestom.server.network.packet.server.play.WorldEventPacket
import net.minestom.server.utils.block.BlockUtils
import net.minestom.server.utils.chunk.ChunkUtils
import kotlin.math.floor
import kotlin.math.roundToInt

fun Instance.blockUtilsAt(position: Pos): BlockUtils = BlockUtils(this, position)
fun Pos.blockUtilsIn(instance: Instance): BlockUtils = BlockUtils(instance, this)

fun PacketGroupingAudience.sendBlockDamage(destroyStage: Byte, point: Point, entityID: Int) {
    val packet = BlockBreakAnimationPacket(entityID, point, destroyStage)
    sendGroupedPacket(packet)
}

fun PacketGroupingAudience.sendBreakBlockEffect(point: Point, block: Block) {
    sendGroupedPacket(WorldEventPacket(2001 /*Block break + block break sound*/, point, block.stateId().toInt(), false))
}

fun Viewable.sendBlockDamage(destroyStage: Byte, point: Point, entityID: Int) {
    val packet = BlockBreakAnimationPacket(entityID, point, destroyStage)
    sendPacketToViewersAndSelf(packet)
}

fun Viewable.sendBreakBlockEffect(point: Point, block: Block) {
    sendPacketToViewersAndSelf(WorldEventPacket(2001 /*Block break + block break sound*/, point, block.stateId().toInt(), false))
}

fun Instance.chunksInRange(position: Pos, range: Int): List<Pair<Int, Int>> {

    val list = mutableListOf<Pair<Int, Int>>()

    for (x in -range..range) {
        for (z in -range..range) {
            list.add(position.chunkX() + x to position.chunkZ() + z)
        }
    }

    return list
}
fun Instance.isChunkLoaded(x: Double, y: Double): Boolean = ChunkUtils.isLoaded(this, x, y)

fun Vec.toBlockPosition(): Pos =
    Pos(this.x().roundToInt().toDouble(), this.y().roundToInt().toDouble(), this.z().roundToInt().toDouble())

fun Vec.toExactBlockPosition(): Pos =
    Pos(floor(this.x()).toInt().toDouble(), floor(this.y()).toInt().toDouble(), floor(this.z()).toInt().toDouble())