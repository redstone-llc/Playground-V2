package org.everbuild.celestia.orion.core.luckperms

import net.kyori.adventure.text.Component
import net.minestom.server.entity.Player
import org.everbuild.celestia.orion.core.packs.withEmojis
import org.everbuild.celestia.orion.core.util.globalOrion

data class LuckPermsSnapshot(
    val permissionsPrefix: String,
    val permissionsColor: String,
    val permissionsDisplayName: String,
    val permissionsWeight: Int,
    val permissionsGroupNameRaw: String,
    val playerName: String,
    val isChatImportant: Boolean
) {
    constructor(playerData: LuckPermsData) : this(
        playerData.permissionsPrefix.withEmojis(),
        playerData.permissionsColor.withEmojis(),
        playerData.permissionsDisplayName.withEmojis(),
        playerData.permissionsWeight,
        playerData.permissionsGroupNameRaw,
        playerData.playerName,
        playerData.isChatImportant
    )

    constructor(player: Player) : this(globalOrion.platform.getLuckPermsData(player))

    fun asComponent(): Component =
        Component.text(this.permissionsPrefix + this.permissionsColor + this.playerName)
}

fun LuckPermsData.takeSnapshot(): LuckPermsSnapshot = LuckPermsSnapshot(this)
fun Player.takeLuckPermsSnapshot() = LuckPermsSnapshot(this)