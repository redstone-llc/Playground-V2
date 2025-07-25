package llc.redstone.playground.action

import com.ezylang.evalex.data.EvaluationValue
//import com.github.shynixn.mccoroutine.minestom.launch
import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Entity
import net.minestom.server.entity.Player
import net.minestom.server.event.Event
import llc.redstone.playground.action.actions.Return
import llc.redstone.playground.action.actions.WaitAction
import llc.redstone.playground.feature.evalex.PGExpression
import llc.redstone.playground.database.Sandbox
import llc.redstone.playground.utils.minecraftServer

class ActionExecutor(
    private val entity: Entity, private val player: Player?, private val sandbox: Sandbox, private val event: Event?,
    private val actions: List<Action>
) {
    fun execute(expression: (String) -> PGExpression = { PGExpression(it, entity, player, sandbox, event) }): EvaluationValue? {
        var syncActions = actions.takeWhile { it !is WaitAction }
        var asyncActions = actions.drop(syncActions.size)

        for (action in syncActions) {
            try {
                val value = action.syncExecute(entity, player, sandbox, event, expression)
                if (value != null) {
                    return value
                }
            } catch (e: Exception) {
                MinecraftServer.LOGGER.error("Error executing action: ${action.name}", e)
            }
        }

//        minecraftServer.launch {
//            for (action in asyncActions) {
//                try {
//                    if (action is Return) {
//                        return@launch
//                    }
//
//                    action.execute(entity, player, sandbox, event, expression)
//                } catch (e: Exception) {
//                    MinecraftServer.LOGGER.error("Error executing action: ${action.name}", e)
//                    e.printStackTrace()
//                }
//            }
//        }

        return null
    }
}