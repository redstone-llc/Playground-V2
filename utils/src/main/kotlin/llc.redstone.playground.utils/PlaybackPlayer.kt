package llc.redstone.playground.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.minestom.server.entity.*
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket
import net.minestom.server.network.packet.server.play.PlayerInfoRemovePacket
import net.minestom.server.network.packet.server.play.PlayerInfoUpdatePacket
import net.minestom.server.network.packet.server.play.PlayerInfoUpdatePacket.Entry
import net.minestom.server.network.packet.server.play.PlayerInfoUpdatePacket.Property


class PlaybackPlayer(
    private val username: String,
    private val skinTexture: String?,
    private val skinSignature: String?
) : EntityCreature(EntityType.PLAYER) {

    init {
    }

    override fun updateNewViewer(player: Player) {
        val properties = mutableListOf<Property>()
        if (skinTexture != null && skinSignature != null) {
            properties.add(Property("textures", skinTexture, skinSignature))
        }
        val entry = Entry(
            uuid,
            removeColor(username),
            properties,
            false,
            0,
            GameMode.SURVIVAL,
            Component.text("Minestom", TextColor.color(0xff6c33)),
            null,
            1
        )
        player.sendPacket(PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.ADD_PLAYER, entry))
        // Spawn the player entity
        super.updateNewViewer(player)
        // Enable skin layers
        player.sendPackets(EntityMetaDataPacket(entityId, mapOf(
            3 to Metadata.Boolean(true),
        )))
    }

    override fun updateOldViewer(player: Player) {
        super.updateOldViewer(player)
        player.sendPacket(PlayerInfoRemovePacket(uuid))
    }
}